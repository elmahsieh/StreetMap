# StreetMap
## Internet Resources
Dikstra’s Algorithm - Geeks for Geeks 
Source: 
https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/

Haversine Formula - Geeks for Geeks 
Source:
https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/

## Project Synopsis
The project employs graph theory concepts, utilizing classes such as Edge, Node, and Graph to represent connections between intersections and find the shortest paths. The MapDrawer class provides a Swing-based graphical user interface for visually representing maps, incorporating a rotated coordinate system and Haversine formula-based projections for accurate geographical rendering. The StreetMap class acts as the main entry point for the command-line application, allowing users to display maps, find directions between intersections using Dijkstra's algorithm, and visualize paths on the map. The application's functionality includes map visualization, pathfinding, and interactive navigation. Users can display the entire map or request directions between specific intersections, with the application outputting the shortest path and total distance. Overall, this project provides a comprehensive tool for navigating and understanding maps through an intuitive graphical interface and efficient pathfinding algorithms.

## Project Structure & File Information
### Edge.java
The Edge class represents a connection between two nodes in a graph. It has private attributes source and destination, which are the nodes connected by the edge. The class provides a constructor to initialize these attributes and getter methods to retrieve the source and destination nodes.
### Node.java
The Node class represents a node in a graph. Each node has a unique identifier (id), geographical coordinates (longitude and latitude), a reference to a previous node (previous), and a distance value used in algorithms like Dijkstra's. The class includes methods to access and modify these attributes, as well as a method for calculating the distance between two nodes using the Haversine formula. It implements the Comparable interface to compare nodes based on their distance values.
### Graph.java
The Graph class represents a graph using an adjacency list. It has a private attribute adj_list, which is a HashMap where each node is associated with a list of adjacent nodes. The class includes a constructor that takes a list of edges to initialize the adjacency list. Additionally, it provides methods to access the adjacency list, perform Dijkstra's algorithm to find the shortest path between two nodes, and retrieve a node b##ased on its identifier. The class utilizes priority queues for efficient Dijkstra's algorithm implementation.
### MapDrawer.java
The MapDrawer class is a Swing-based graphical user interface that visualizes a graph and its paths on a map. It extends JFrame and contains a nested MapPanel class responsible for drawing. The MapPanel class handles the actual drawing, including edges between nodes and the visualization of the shortest path in red. The code includes methods for scaling and adjusting coordinates based on minimum and maximum values, offering an interactive map representation of the provided graph.
### StreetMap.java
The StreetMap class is a command-line utility for working with geographical maps. It reads input parameters to determine whether to display the map, provide directions between two intersections, or both. The program loads map data from a specified file, creates a graph representation using the Graph class, and utilizes Dijkstra's algorithm to find the shortest path between specified intersections. The MapDrawer class is then employed to visualize the map and, if requested, display the shortest path. 

## Command Lines
Make sure the file is in the correct directory before executing the following commands. 

javac *.java

java StreetMap map.txt [--show] [--directions startIntersection endIntersection]
