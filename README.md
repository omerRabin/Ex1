# Ex1
//My ReadME:
Hey,
My project is about representing weighted and unintended graphs and running algorithms on them.
An undirectional weighted graph is a graph with vertices and edges.The nodes numbered by uniques keys. Each edge in the graph has a weight. Each edge connects 2 vertices in a way that can be moved on the edge in both directions. 
My project hierarchy is structured as follows: Representation of a node(no access to this object)->representation of a graph->representation of algorithms and methods on graph.
I decided to build the graph using a data structure: Hash-Map, because it is very efficient in terms of runtimes and very suitable for the structure of a graph, and the operations performed on it.
My project supports many functions on the graphs including: get a node in the graph, check if edge exists, get a specific edge, add node to the graph, connect between two nodes-make an edge, retrieval of the whole graph, retrieval neighbor group of specific node(all nodes that have a common edge with this node), remove node, remove edge and functions that get general information on the graph.
In addition, my project contains many functions that implement sophisticated algorithms like bfs algorithm and dijkstra's algorithms. functions like: init the graph, copy the graph, check if the graph is connected(what means that there is a path from each node in the graph to each node in the graph), compute the shortest path in the graph between two given node and return the path's length, and option to save and load a specific graph to file.
