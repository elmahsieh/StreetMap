import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

public class MapDrawer extends JFrame {
    public MapPanel panel;
    private Graph graph;
    private Node start;
    private Node end;

    public MapDrawer(Graph graph) {
        this.graph = graph;
        panel = new MapPanel();
        panel.setGraph(graph);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public MapDrawer(Graph graph, Node startNode, Node endNode) {
        this.graph = graph;
        this.start = startNode;
        this.end = endNode;

        panel = new MapPanel();
        panel.setGraph(graph, start, end);
        panel.setStartAndEndNodes(startNode, endNode, graph);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static class MapPanel extends JPanel {
        private Graph graph;
        private Node start;
        private Node end;

        private double xAxisMax;
        private double xAxisMin;
        private double yAxisMax;
        private double yAxisMin;

        public void setGraph(Graph graph) {
            this.graph = graph;
            this.start = null;
            this.end = null;
            this.setPreferredSize(new Dimension(600, 600));
            repaint();
        }
        public void setGraph(Graph graph, Node start, Node end) {
            this.graph = graph;
            this.start = start;
            this.end = end;
            this.setPreferredSize(new Dimension(600, 600));
            repaint();
        }

        public void setStartAndEndNodes(Node start, Node end, Graph graph) {
            this.graph = graph;
            this.start = start;
            this.end = end;
            this.setPreferredSize(new Dimension(600, 600));
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2D = (Graphics2D) g;
            g2D.rotate(Math.toRadians(-90));
            Dimension size = this.getSize();

            HashMap<Node, ArrayList<Node>> adjacencyList = graph.getAdjacencyList();

            calculateMinMaxAxes(adjacencyList);
            drawEdges(adjacencyList, size, g2D);
//            System.out.println("draw edges");

            if (start != null && end != null) {
                drawShortestPath(size, g2D);
            }
        }

        private void calculateMinMaxAxes(HashMap<Node, ArrayList<Node>> adjacencyList) {
            ArrayList<Double> xAxises = new ArrayList<>();
            ArrayList<Double> yAxises = new ArrayList<>();

            for (Node n : adjacencyList.keySet()) {
//                System.out.println(n.getLatitude());
//                System.out.println(n.getLongitude());
                double xAxis = xAxisProjection(n.getLongitude());
                double yAxis = yAxisProjection(n.getLatitude());

                xAxises.add(xAxis);
                yAxises.add(yAxis);
            }

            double xAxisMax = Collections.max(xAxises);
            double xAxisMin = Collections.min(xAxises);
            double yAxisMax = Collections.max(yAxises);
            double yAxisMin = Collections.min(yAxises);

            this.xAxisMax = xAxisMax;
            this.xAxisMin = xAxisMin;
            this.yAxisMax = yAxisMax;
            this.yAxisMin = yAxisMin;
        }

        private void drawEdges(HashMap<Node, ArrayList<Node>> adjacencyList, Dimension size, Graphics2D g2D) {
            for (Node n : adjacencyList.keySet()) {
                double xAxis = scaleXaxis(xAxisProjection(n.getLongitude()), xAxisMin, xAxisMax, size.height);
                double yAxis = scaleYaxis(yAxisProjection(n.getLatitude()), yAxisMin, yAxisMax, size.width);

                // Draws map - black lines
                for (Node adj : adjacencyList.get(n)) {
                    double xAxisAdj = scaleXaxis(xAxisProjection(adj.getLongitude()), xAxisMin, xAxisMax, size.height);
                    double yAxisAdj = scaleYaxis(yAxisProjection(adj.getLatitude()), yAxisMin, yAxisMax, size.width);

                    g2D.drawLine((int) xAxis, (int) yAxis, (int) xAxisAdj, (int) yAxisAdj);
                }
            }
        }

        private void drawShortestPath(Dimension size, Graphics2D g2D) {
            Node temp = end;
            while (temp != start) {
                double xAxis = scaleXaxis(xAxisProjection(temp.getLongitude()), xAxisMin, xAxisMax, size.height);
                double yAxis = scaleYaxis(yAxisProjection(temp.getLatitude()), yAxisMin, yAxisMax, size.width);

                double xAxisAdj = scaleXaxis(xAxisProjection(temp.previous.getLongitude()), xAxisMin, xAxisMax, size.height);
                double yAxisAdj = scaleYaxis(yAxisProjection(temp.previous.getLatitude()), yAxisMin, yAxisMax, size.width);

                g2D.setColor(Color.RED);
                g2D.drawLine((int) xAxis, (int) yAxis, (int) xAxisAdj, (int) yAxisAdj);

                temp = temp.previous;
            }
        }

        private double scaleXaxis(double Xaxis, double min, double max, double size) {
            Xaxis = Xaxis - min;

            // Check if (max - min) is zero to avoid division by zero
            double divisor = (max - min) == 0 ? 1 : (max - min);

            Xaxis = Xaxis / divisor;
            return Xaxis * size - size;

        }

        private double scaleYaxis(double Yaxis, double min, double max, double size) {
            Yaxis = Yaxis - min;

            // Check if (max - min) is zero to avoid division by zero
            double divisor = (max - min) == 0 ? 1 : (max - min);

            Yaxis = Yaxis / divisor;
            return Yaxis * size;
        }

        public static double xAxisProjection(double input) {
            return Math.toRadians(input) * 6378137.0;
        }

        public static double yAxisProjection(double input) {
            return Math.log(Math.tan(Math.PI / 4 + Math.toRadians(input) / 2)) * 6378137.0;
        }
    }
}
