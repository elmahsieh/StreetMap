import java.util.*;

public class Graph {
    private final HashMap<Node, ArrayList<Node>> adj_list = new HashMap<>();

    public Graph(ArrayList<Edge> edges) {
        //Adjacency list memory allocation
        for (Edge e : edges) {
            Node n1 = e.getSource();
            Node n2 = e.getDestination();

            if (!adj_list.containsKey(n1)) {
                adj_list.put(n1, new ArrayList<>());
            }
            adj_list.get(n1).add(n2);

            if (!adj_list.containsKey(n2)) {
                adj_list.put(n2, new ArrayList<>());
            }
            adj_list.get(n2).add(n1);
        }
    }
    public HashMap<Node, ArrayList<Node>> getAdjacencyList() {
        return adj_list;
    }

    /** GeeksforGeeks - Dijkstra's algorithm
     *  Link to source:
     *  <a href="https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/">...</a>
     */
    public void Dijkstra(Node source, Node destination) {
        PriorityQueue<Node> unvisitedQueue = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));
        source.setDistance(0);
        unvisitedQueue.add(source);

        while (!unvisitedQueue.isEmpty()) {
            Node currentNode = unvisitedQueue.poll();

            if (currentNode.equals(destination)) {
                break;
            }

            for (Node adj : adj_list.get(currentNode)) {
                double edgeWeight = currentNode.calculateDistance(adj);
                double altDistance = currentNode.getDistance() + edgeWeight;

                if (altDistance < adj.getDistance()) {
                    adj.setDistance(altDistance);
                    adj.setPrevious(currentNode);

                    unvisitedQueue.add(adj);
                }
            }
        }
    }

    public Node getNode(String id) {
        for (Node n: adj_list.keySet()) {
            if (n.getId().equals(id)) {
                return n;
            }
        }
        return null;
    }
}
