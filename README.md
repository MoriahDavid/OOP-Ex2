## :wavy_dash: :white_medium_small_square: :white_small_square: Directed Weighted Graphs :white_small_square: :white_medium_small_square: :wavy_dash:
<br />

### :large_blue_diamond: Main Classes:

#### :black_medium_square: BaseNodeData
On this class we save for every node: key, tag, info, weight, location. <br />
We create sets and gets methods. <br />

#### :black_medium_square: BaseEdgeData
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

We run preformes tests to the algorithms. for every algorithm test we define 15 min for timeout. <br />
<br />

Graph          | Shorted path | Is Connected | Center  | TSP |
-------------- | ------------ | -------------| --------|-----|
   G1          |      1 ms    |   1 ms       |   1 ms  | 15 ms |
   G2          |         1 ms |    1 ms      |16 ms    |    16 ms |
   G3          |        1 ms  |      1 ms    |    1 ms |  16 ms |
   1000 nodes  |       15 ms  |     125 ms   | 3,078 ms |2,275 ms |
   10,000 nodes|     393 ms   |   548 ms     |Timed Out|Timed Out|
   100,000 nodes|    12,649 ms |     32,570 ms|Timed Out|Timed Out|
   1,000,000 nodes  | Timed Out|Timed Out  |Timed Out| Timed Out |

<br />

### :large_blue_diamond: GUI: <br />
The user interface has been designed with user experience and convenience in mind in order to allow the use of all the features of the system. 
In this GUI we can see the graph and modified it. every node has its key and every edge has its weight on it. <br />
<br />
![image](https://user-images.githubusercontent.com/64011788/145840065-84aaeba0-2fef-4bb6-bd6b-c7d2789d11b9.png)
<br />

**Download-** for downloading clone the repo <br />
**Run-** java -jar Ex2.jar {graph_json_path}. <br />
**Use-** explain about the menu on the GUI: <br />

 <img align="right" width="185" src="https://user-images.githubusercontent.com/64011788/145844006-3c1792bd-f0ae-4c55-8166-037122731632.png">

**File Menu**      | Explain      |
-------------- | ------------ |                               
   Load Graph  |      load graph from json file      | 
   Save Graph  |     save this graph to json file    | 
   Exit        |        close the program            |


 <img align="right" width="185" src="https://user-images.githubusercontent.com/64011788/145841573-eb6d6ae4-36c4-44e6-949b-0ebbe3d2d7b3.png">

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
   
   
<img align="right" width="185" src="https://user-images.githubusercontent.com/64011788/145843053-96e56c54-99f6-4fbe-ac1a-d0f96c624e11.png">
   
   
   **Algorithms Menu**      | Explain                       |
-------------- | -------------------------------------------|
   Is Connected  |      Run the algorithm and displays if the graph is connected           |
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
   
**In right-** we chose the yellow node for src and the green for dest. we run the ShortedPath algorithm and in blue its the algorithm result. <br />
**In left-** right click on a node. <br />
<br />

![image](https://user-images.githubusercontent.com/64011788/145840585-459f3131-4e50-44b0-a841-73dab37bcde1.png)

<br />

### :large_blue_diamond: Diagram:
API - 

![api2](https://user-images.githubusercontent.com/64011788/145889095-66eaa95d-e521-4505-be0a-350caad547d7.png)
