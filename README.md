## Directed Weighted Graphs


### Main Classes:
BaseNodeData- the main class for the nodes.
On this class we save for every node: key, tag, info, weight, location.
We create sets and gets methods.

#### BaseEdgeData- the main class for the edges.
On this class we save for every edge: tag, info, source-node, destination-node, weight.
We create sets and gets methods.

#### BaseGeoLocation
In this class the X, Y, Z coordinates are displayed.
The instruction was that Z is always zero.
We have gets methods in this class.

#### BaseDirectedWeightedGraph
This class represents our directed weighted graph.
We created two HashMaps in HashMap and one regular HashMap:

The first is -edges_src- represents the edges from source to destination. 
For every node-key from source, we keep in internal HashMap the node-key from dest and the appropriate edge (from src to dest). 

The second is -edges_dest- represents edges from destination to source. 
For every node-key from destination, we keep in internal HashMap the node-key from source and the appropriate edge (from dest to source). 
We made this method in order to makes some methods easier to implements.

In addition we creats regular HashMap -nodes- that represents the nodes on this graph.
The first one is -nodes- represens our nodes on the graph.

In this class we implements these methods: transpose, getNode, getEdge, addNode, connect, nodeIter, edgeIter, removeNode, removeEdgesForNode, rempveEdge, nodeSize, edgeSize, getMC.
We also made three classes for Iterator: nodeIterator, edgeIterator- for specific node and AllEdgesIterator- for all the nodes on the graph.

#### BaseDirectedWeightedGraphAlgo
In this class we have the main algorithms for graph.
the methods:

init- Initializes a new graph.
getGraph- Return the graph.
copy- Make a deep copy for a graph.
Dijkstra- Dijkstra's algorithm found the shortest path between two given nodes. Dijkstra algorithm- https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
shortestPath- Return list that represents the shortest path between two nodes. In this method we use the Dijkstra algorithm.
shortestPathDist- Return the dist (weight) of the shortest path between two nodes by using Dijkstra method.
isConnected- We check for every node in the graph, if there is a path between it and rest of the vertices by using the method shortestPathDist. Then, we do transpose to the graph and check again.
reset_nodes- Inserts values (tag and weight) to the nodes.
center- Checks for every node who is the furthest node (with the highest weight) and save its weight. After that it return the node with the smallest weight.
tsp- Return a list that represents the shortest path (with the smallest weight)by using greedy algorithm. We reset the nodes tag to zero. It start by choosing random node and every time we search for the closest node from this node with tag 0. When we find the closest node we change its tag to 1 and add this node to a new list. We will continue to do this as long as there is a node with data 0. Return the new list.
pathWeight- Return the total weight of a path (represent with list). 
getRandomNode- Return node in random.
closestNode- Return the closest node to some node by passing all the nodes in the list and calculate the total weight.

save-
load- 
graphDeserializer- 

### Algorithms Results
Graph with 1000 nodes:
Graph with 10,000 nodes:
Graph with 100,000 nodes:
Graph with 1,000,000 nodes:

### GUI:
Download-
Run-
Use-
