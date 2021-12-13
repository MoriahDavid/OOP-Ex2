## :wavy_dash: :white_medium_small_square: :white_small_square: Directed Weighted Graphs :white_small_square: :white_medium_small_square: :wavy_dash:
<br />

### :large_blue_diamond: Main Classes:
BaseNodeData- the main class for the nodes. <br />
On this class we save for every node: key, tag, info, weight, location. <br />
We create sets and gets methods. <br />

#### :black_medium_square: BaseEdgeData- the main class for the edges.
On this class we save for every edge: tag, info, source-node, destination-node, weight. <br />
We create sets and gets methods.

#### :black_medium_square: BaseGeoLocation
In this class the X, Y, Z coordinates are displayed. <br />
The instruction was that Z is always zero. <br />
We have gets-methods in this class. <br />

#### :black_medium_square: BaseDirectedWeightedGraph
This class represents our directed weighted graph. <br />
We create two HashMaps in HashMap and one regular HashMap: <br />

The first is -edges_src- represents the edges from source to destination. <br />
For every node-key from source, we keep in internal HashMap the node-key from dest and the appropriate edge (from src to dest). <br />
The second is -edges_dest- represents edges from destination to source. <br />
For every node-key from destination, we keep in internal HashMap the node-key from source and the appropriate edge (from dest to source). <br />
We made this method in order to makes some methods easier to implements. <br />
In addition we creats regular HashMap -nodes- that represents the nodes on this graph. <br />
The first one is -nodes- represents our nodes on the graph. <br />

In this class we implement these methods: transpose, getNode, getEdge, addNode, connect, nodeIter, edgeIter, removeNode, removeEdgesForNode, rempveEdge, nodeSize, edgeSize, getMC. <br />
We also made three classes for Iterator: nodeIterator, edgeIterator- for specific node and AllEdgesIterator- for all the nodes on the graph. <br />

#### :black_medium_square: BaseDirectedWeightedGraphAlgo
In this class we have the main algorithms for graph. <br />
The methods: <br />

**init-** Initializes a new graph. <br />
**getGraph-** Return the graph. <br />
**copy-** Make a deep copy for a graph. <br />
**Dijkstra-** Dijkstra's algorithm found the shortest path between two given nodes. Dijkstra algorithm- https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm <br />
**shortestPath-** Return list that represents the shortest path between two nodes. In this method we use the Dijkstra algorithm. <br />
**shortestPathDist-** Return the dist (weight) of the shortest path between two nodes by using Dijkstra method. <br />
**isConnected-** Check for one node on graph, if there is a path between it and rest of the vertices by using the Dijkstra algorithm. Then, we do transpose to the graph and check again. <br />
**reset_nodes-** Inserts values (tag and weight) to the nodes. <br />
**center-** Checks for every node who is the furthest node (with the highest weight) and save its weight. After that it return the node with the smallest weight. <br />
**tsp-** Return a list that represents the shortest path (with the smallest weight)by using greedy algorithm. We reset the nodes tag to zero. It start by choosing random node and every time, we search for the closest node from this node with tag 0. When we find the closest node we change its tag to 1 and add this node to a new list. We will continue to do this as long as there is a node with data 0. If the algorithms cant find a node with tag 0 --> going back one node. Return the new list. <br />
**pathWeight-** Return the total weight of a path (represents with list). <br />
**getRandomNode-** Return node in random. <br />
**closestNode-** Return the closest node to some node by passing all the nodes in the list and calculate the total weight. <br />

 <br />

### :large_blue_diamond: Algorithms Results:
<br />

Graph          | Shorted path | Is Connected | Center  | TSP |
-------------- | ------------ | -------------| --------|-----|
   G1          |              |              |         |     |
   G2          |              |              |         |     |
   G3          |              |              |         |     |
   1000 nodes  |              |              |         |     |
   10,000 nodes|              |              |         |     |
   100,000 nodes|              |              |         |     |
   1,000,000 nodes  |              |              |         |     |

<br />

### :large_blue_diamond: GUI: <br />

This Gui contains graphic ui and we can you see the graph and modified it. every node has its key and every edge has its weight on it. <br />
<br />
![image](https://user-images.githubusercontent.com/64011788/145840065-84aaeba0-2fef-4bb6-bd6b-c7d2789d11b9.png)
<br />

**Download-** for downloading clone the repo <br />
**Run-** java -jar Ex2.jar {graph_json_path}. <br />
**Use-** explain about the menu on the GUI: <br />

**File Menu**      | Explain      |
-------------- | ------------ |                               
   Load Graph  |      load graph from json file      | 
   Save Graph  |     save this graph to json file    | 
   Exit        |        close the program            |

**Graph Menu**     | Explain                                   | 
-------------- | -------------------------------------------|                               
   Add Node  |      Add node to the graph                   | 
   Add Edge  |    Add edge to the graph                     | 
   Clear Marked Edges       |                               |
   Clear Marked Nodees       |                              |
   Add all for TSP    |   Select all the nodes for TSP algorithm |
   Remove all for TSP |   Remvoe selection for all nodes for TSP algorithm      |
   Enable Drag Nodes  |    Enable to move node possition    | 
   Show Edges Weight  |   Hide/how the weight               | 
   
   **Algorithms Menu**      | Explain                                   | 
-------------- | -------------------------------------------|                               
   Is Connected  |      Run the algorithm and displays if the graph is connected                  | 
   Shorted Path  |    Run the algorithm between two chosen nodes in the graph and displays this path      | 
   Center       |     Run the algorithm and displays the center node                       |
   TSP          |    Run the algorithm in order to find the shortest path for all the nodes        |
   
   **Shortcuts**      |       |
-------------- | ------------ |                               
   Delete edge  |     Right click on an edge      |
   Delete node  |     Right click on a node      | 
   Select src and dest nodes | Right click on a node      | 
   Add node for TSP | Right click on a node      | 
   Add Node |      Right click on the screen      |
   Move Node |      Drag the node      |
   <br />
In right- we chose the yellow node for src and the green for dest. we run the ShortedPath algorithm and in blue its the algorithm result.
In left- shorcuts for node.
<br />
![image](https://user-images.githubusercontent.com/64011788/145840585-459f3131-4e50-44b0-a841-73dab37bcde1.png)

<br />

### :large_blue_diamond: Diagram:
