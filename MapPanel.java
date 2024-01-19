import javax.swing.JPanel;
import java.awt.*;
import java.util.*;
import java.awt.geom.AffineTransform;

public class MapPanel extends JPanel {
    private Graph graph;
    private Node start;
    private Node end;

    public MapPanel(Graph graph) {
        this.graph = graph;
        this.start = null;
        this.end = null;
        this.setPreferredSize(new Dimension(300, 300));
    }
    public MapPanel(Graph graph, Node start, Node end) {
        this.graph = graph;
        this.start = start;
        this.end = end;
        this.setPreferredSize(new Dimension(300, 300));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.rotate(Math.toRadians(-90));
        Dimension size = this.getSize();

        HashMap<Node, ArrayList<Node>> adjacencyList = graph.getAdjacencyList();

        // calculateMinMaxAxes

        ArrayList<Double> xAxises = new ArrayList<>();
        ArrayList<Double> yAxises = new ArrayList<>();

        for (Node n: adjacencyList.keySet()) {
            double xAxis = xAxisProjection(n.getLongitude());
            double yAxis = yAxisProjection(n.getLatitude());

            xAxises.add(xAxis);
            yAxises.add(yAxis);
        }

        double xAxisMax = Collections.max(xAxises);
        double xAxisMin = Collections.min(xAxises);
        double yAxisMax = Collections.max(yAxises);
        double yAxisMin = Collections.min(yAxises);

        for (Node n: adjacencyList.keySet()) {
            double xAxis = xAxisProjection(n.getLongitude());
            double yAxis = yAxisProjection(n.getLatitude());

            //Scale the axises
            xAxis = xAxis - xAxisMin;
            yAxis = yAxis - yAxisMin;

            xAxis = xAxis / (xAxisMax - xAxisMin);
            yAxis = yAxis / (yAxisMax - yAxisMin);

            xAxis = xAxis * size.height - size.height;
            yAxis = yAxis * size.width;

            for (Node adj: adjacencyList.get(n)) {
                double xAxisAdj = xAxisProjection(adj.getLongitude());
                double yAxisAdj = yAxisProjection(adj.getLatitude());

                //Scale the axises
                //Scale the axises
                xAxisAdj = xAxisAdj - xAxisMin;
                yAxisAdj = yAxisAdj - yAxisMin;

                xAxisAdj = xAxisAdj / (xAxisMax - xAxisMin);
                yAxisAdj = yAxisAdj / (yAxisMax - yAxisMin);

                xAxisAdj = xAxisAdj * size.height - size.height;
                yAxisAdj = yAxisAdj * size.width;

                g2D.drawLine((int)xAxis, (int)yAxis, (int)xAxisAdj, (int)yAxisAdj);
            }
        }

        if (start != null && end != null) {
            Node temp = end;
            while (temp != start) {
                double xAxis = xAxisProjection(temp.getLongitude());
                double yAxis = yAxisProjection(temp.getLatitude());

                //Scale the axises
                xAxis = xAxis - xAxisMin;
                yAxis = yAxis - yAxisMin;

                xAxis = xAxis / (xAxisMax - xAxisMin);
                yAxis = yAxis / (yAxisMax - yAxisMin);

                xAxis = xAxis * size.height - size.height;
                yAxis = yAxis * size.width;

                double xAxisAdj = xAxisProjection(temp.previous.getLongitude());
                double yAxisAdj = yAxisProjection(temp.previous.getLatitude());

                //Scale the axises
                xAxisAdj = xAxisAdj - xAxisMin;
                yAxisAdj = yAxisAdj - yAxisMin;

                xAxisAdj = xAxisAdj / (xAxisMax - xAxisMin);
                yAxisAdj = yAxisAdj / (yAxisMax - yAxisMin);

                xAxisAdj = xAxisAdj * size.height - size.height;
                yAxisAdj = yAxisAdj * size.width;

                g2D.setColor(Color.RED);
                g2D.drawLine((int)xAxis, (int)yAxis, (int)xAxisAdj, (int)yAxisAdj);

                temp = temp.previous;
            }
        }

    }

    public static double xAxisProjection(double input) {
        System.out.println("X Axis: " + input);
        return Math.toRadians(input) * 6378137.0;
    }

    public static double yAxisProjection(double input) {
        System.out.println("Y Axis: " + input);
        return Math.log(Math.tan(Math.PI / 4 + Math.toRadians(input) / 2)) * 6378137.0;
    }
}
