package api;

import java.util.*;

public class BaseDirectedWeightedGraphAlgo implements api.DirectedWeightedGraphAlgorithms{
    private DirectedWeightedGraph graph;

    /**
     * Inits the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    /**
     * Returns the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    /**
     * Computes a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph new_g = new BaseDirectedWeightedGraph(); // TODO: create generic
        return null;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {

        DirectedWeightedGraph g = this.copy();
        this.reset_nodes(g);

        Iterator<NodeData> it = g.nodeIter();
        NodeData start_node = it.next();

        while (it.hasNext()) {
            NodeData node = it.next();
            // check have route from start_node to node
            if(node.getTag() == 1)
                continue;

            if (!exist_path(g, start_node, node))
                return false;
        }
        this.transpose_graph(g);

        this.reset_nodes(g);

        it = g.nodeIter();
        start_node = it.next();

        while (it.hasNext()) {
            NodeData node = it.next();
            // check have route from start_node to node
            if(node.getTag() == 1)
                continue;

            if (!exist_path(g, start_node, node))
                return false;
        }

        return true;
    }

    private void transpose_graph(DirectedWeightedGraph g){
        // TODO: Implement - Should use only yhe interface? how can to create new instance of unknown Class.
    }

    private void reset_nodes(DirectedWeightedGraph g){
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData node = it.next();
            node.setTag(0);
            node.setWeight(0);
        }
    }

    private boolean exist_path(DirectedWeightedGraph g, NodeData src, NodeData dst){
        if(g.getEdge(src.getKey(), dst.getKey()) != null){
            dst.setTag(1);
            return true;
        }
        Iterator<EdgeData> it = g.edgeIter(src.getKey());
        while(it.hasNext()){
            EdgeData e = it.next();
            if(exist_path(g, g.getNode(e.getDest()), dst)){
                return true;
            }
        }
        return false;
    }

    /**
     * Computes the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        // TODO: validate the inputs
        Dijkstra(this.graph, src);
        NodeData d = this.graph.getNode(dest);

        if(d.getTag() == Integer.MIN_VALUE) return -1;

        this.reset_nodes(this.graph);

        return d.getWeight();
    }

    /**
     * Computes the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        // TODO: validate the inputs

        DirectedWeightedGraph g = this.graph;

        this.Dijkstra(g, src);

        NodeData n = g.getNode(dest);
        List<NodeData> l = new ArrayList<NodeData>();

        if(n.getTag() == Integer.MIN_VALUE) // There is no path from src to dst
            return null;

        l.add(n);

        while (n != g.getNode(src)) {
            n = g.getNode(n.getTag());
            if(n == null)
                throw new RuntimeException(); // TODO: Change to more indicative exception.

            l.add(0, n);
        }

        this.reset_nodes(g);

        return l;
    }

    /**
     * TODO:
     * @param g
     * @param src
     */
    private void Dijkstra(DirectedWeightedGraph g, int src){
        PriorityQueue<NodeData> q = new PriorityQueue<>(
                Comparator.comparingDouble(NodeData::getWeight));

        Iterator<NodeData> it_n = g.nodeIter();
        while (it_n.hasNext()){
            NodeData n = it_n.next();
            n.setWeight(Double.MAX_VALUE);
            n.setTag(Integer.MIN_VALUE);
            q.add(n);
        }

        g.getNode(src).setWeight(0);

        while (!q.isEmpty()){
            NodeData u = q.poll();
            Iterator<EdgeData> it_e = g.edgeIter(u.getKey());
            while (it_e.hasNext()){
                EdgeData e = it_e.next();
                NodeData v = g.getNode(e.getDest());
                double alt = u.getWeight() + e.getWeight();
                if(alt < v.getWeight()){
                    v.setWeight(alt);
                    v.setTag(u.getKey()); // Save the key of the previous node.
                }
            }
        }
    }

    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        return null;
    }

    /**
     * Computes a list of consecutive nodes which go over all the nodes in cities.
     * the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution -
     * the lower the better.
     * See: https://en.wikipedia.org/wiki/Travelling_salesman_problem
     *
     * @param cities
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        return false;
    }

    /**
     * This method loads a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        return false;
    }
}
