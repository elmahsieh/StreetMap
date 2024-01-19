import javax.swing.*;

public class MapFrame extends JFrame {

    public MapPanel panel;

    public MapFrame(Graph g) {
        panel = new MapPanel(g);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public MapFrame(Graph g, Node start, Node end) {
        panel = new MapPanel(g, start, end);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}