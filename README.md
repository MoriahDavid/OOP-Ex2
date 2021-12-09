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

### Algorithms Results
Graph with 1000 nodes:
Graph with 10,000 nodes:
Graph with 100,000 nodes:
Graph with 1,000,000 nodes:

### GUI:
Download-
Run-
Use-
