Internet Resource: 
Dikstra’s Algorithm - Geeks for Geeks 
Source: 
https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/

Haversine Formula - Geeks for Geeks 
Source:
https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/

Project Synopsis:
The project employs graph theory concepts, utilizing classes such as Edge, Node, and Graph to represent connections between intersections and find the shortest paths. The MapDrawer class provides a Swing-based graphical user interface for visually representing maps, incorporating a rotated coordinate system and Haversine formula-based projections for accurate geographical rendering. The StreetMap class acts as the main entry point for the command-line application, allowing users to display maps, find directions between intersections using Dijkstra's algorithm, and visualize paths on the map. The application's functionality includes map visualization, pathfinding, and interactive navigation. Users can display the entire map or request directions between specific intersections, with the application outputting the shortest path and total distance. Overall, this project provides a comprehensive tool for navigating and understanding maps through an intuitive graphical interface and efficient pathfinding algorithms.

Obstacles I Overcame, and how I overcame them:
The two greater obstacles I encountered was having to use the Haversine formula to convert latitude and longitude coordinates to Cartesian coordinates. I had to understand the concepts behind the formula first in order to execute the mathematical transformations. Another obstacle I encountered was having to implement Dijkstra's algorithm. Although the internet provided many ways to implement, along with the codes, I had trouble making the codes on the internet work with mine. Another one of the obstacles I encountered, actually the greatest one while working on the project, was trying to figure out how to get the roads onto my window. There was nothing wrong with my graphing classes. Turns out the issue was in my Node class. It was a simple mistake I didn’t catch — writing longitude and latitude with uppercase L letters instead of lowercase letters which caused the coordinates to turn up as zero because it was using the wrong variables. I ended up finding the mistake after almost three days of debugging, trying to figure out how to make the roads appear on my window. 

Runtime Analysis of Code:
Runtime of plotting the map – 
public void Dijkstra(Node source, Node destination) {
    PriorityQueue<Node> unvisitedQueue = new
    PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));
    source.setDistance(0);
    unvisitedQueue.add(source);

    while (!unvisitedQueue.isEmpty()) {
// while loop runs “n” times → O(n)
       Node currentNode = unvisitedQueue.poll();


       if (currentNode.equals(destination)) {
// if statement runs in constant time → O(1)
           break;
       }


       for (Node adj : adj_list.get(currentNode)) {
// for loop runs “n” times → O(n)
           double edgeWeight = currentNode.calculateDistance(adj);
           double altDistance = currentNode.getDistance() + edgeWeight;


           if (altDistance < adj.getDistance()) {
// if statement runs in constant time → O(1)
               adj.setDistance(altDistance);
               adj.setPrevious(currentNode);


               unvisitedQueue.add(adj);
           }
       }
   }
}
A: O(n) * O(1) * O(n) * O(1) = O(n^2)


Runtime of finding the shortest path between two intersections – 
public double calculateDistance(Node otherNode) {
   double longitude1 = this.getLatitude();
   double latitude1 = this.getLongitude();


   double longitude2 = otherNode.getLatitude();
   double latitude2 = otherNode.getLongitude();


   double deltaLongitude = longitude1 - longitude2;


   return Math.acos(
           Math.sin((latitude1 * Math.PI / 180.0)) * Math.sin((latitude2 * Math.PI / 180.0)) +
               Math.cos((latitude1 * Math.PI / 180.0)) * Math.cos((latitude2 * Math.PI / 180.0)) *
                   Math.cos((deltaLongitude * Math.PI / 180.0))) * 180.0 / Math.PI * 60 * 1.1515;
}
Constant time complexity of O(1) for each line of code
A: O(1) 


Project Structure & File Information: 
Edge.java - 
The Edge class represents a connection between two nodes in a graph. It has private attributes source and destination, which are the nodes connected by the edge. The class provides a constructor to initialize these attributes and getter methods to retrieve the source and destination nodes.


Node.java  - 
The Node class represents a node in a graph. Each node has a unique identifier (id), geographical coordinates (longitude and latitude), a reference to a previous node (previous), and a distance value used in algorithms like Dijkstra's. The class includes methods to access and modify these attributes, as well as a method for calculating the distance between two nodes using the Haversine formula. It implements the Comparable interface to compare nodes based on their distance values.


Graph.java - 
The Graph class represents a graph using an adjacency list. It has a private attribute adj_list, which is a HashMap where each node is associated with a list of adjacent nodes. The class includes a constructor that takes a list of edges to initialize the adjacency list. Additionally, it provides methods to access the adjacency list, perform Dijkstra's algorithm to find the shortest path between two nodes, and retrieve a node based on its identifier. The class utilizes priority queues for efficient Dijkstra's algorithm implementation.


MapDrawer.java - 
The MapDrawer class is a Swing-based graphical user interface that visualizes a graph and its paths on a map. It extends JFrame and contains a nested MapPanel class responsible for drawing. The MapPanel class handles the actual drawing, including edges between nodes and the visualization of the shortest path in red. The code includes methods for scaling and adjusting coordinates based on minimum and maximum values, offering an interactive map representation of the provided graph.


StreetMap.java - 
The StreetMap class is a command-line utility for working with geographical maps. It reads input parameters to determine whether to display the map, provide directions between two intersections, or both. The program loads map data from a specified file, creates a graph representation using the Graph class, and utilizes Dijkstra's algorithm to find the shortest path between specified intersections. The MapDrawer class is then employed to visualize the map and, if requested, display the shortest path. 


README - 
This file contains more details about the project, and details for operating the code. 


Command Lines: 
Make sure the file is in the correct directory before executing the following commands. 
javac *.java
java StreetMap map.txt [--show] [--directions startIntersection endIntersection]
