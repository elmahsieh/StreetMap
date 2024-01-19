import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class StreetMap {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = new ArrayList<>();
        for (int i=0; i<args.length; i++) {
            input.add(args[i]);
        }
        String fileName = input.get(0);
        input.remove(fileName);

        boolean showMap, directions;
        String startIntersection = "";
        String endIntersection = "";

        //Check if Show Map
        if (input.size() > 0 && input.contains("--show")) {
            showMap = true;
            input.remove("--show");
        } else {
            showMap = false;
        }

        //Check if Directions
        if (input.size() > 0 && input.contains("--directions")) {
            directions = true;
            startIntersection = input.get(1);
            endIntersection = input.get(2);
        } else {
            directions = false;
        }

        Graph g = convertFileToGraph(fileName);
        if (directions) {
            Node start = g.getNode(startIntersection);
            Node end = g.getNode(endIntersection);
            g.Dijkstra(start, end);

            double distance = end.getDistance();

            Node temp = end;
            ArrayList<String> path = new ArrayList<>();
            while (temp != null && temp != start) {
                path.add(temp.getId());
                temp = temp.previous;
            }

            Collections.reverse(path);
            if (showMap) {
                MapDrawer map = new MapDrawer(g, start, end);
            }

            for (String s : path) {
                System.out.println(s);
            }

            System.out.println("Total Distance: " + distance);

        } else if (showMap) {
            MapDrawer map = new MapDrawer(g);
        }
    }

    public static Graph convertFileToGraph(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        ArrayList<Edge> edges = new ArrayList<>();
        HashMap<String, Node> nodes = new HashMap<>();

        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split("\t");

            // An intersection
            if (tokens[0].equals("i")) {
                String id = tokens[1];
                double latitude = Double.parseDouble(tokens[2]);
                double longitude = Double.parseDouble(tokens[3]);
                Node node = new Node(id, latitude, longitude);

                nodes.put(id, node);

            // A road
            } else {
                String sourceId = tokens[2];
                String destinationId = tokens[3];

                Node sourceNode = nodes.get(sourceId);
                Node destinationNode = nodes.get(destinationId);

                Edge edge = new Edge(sourceNode, destinationNode);
                edges.add(edge);
            }
        }

        return new Graph(edges);
    }
}
