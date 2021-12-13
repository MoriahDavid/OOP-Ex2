package api;

import java.util.*;

public class BaseDirectedWeightedGraph implements api.DirectedWeightedGraph {
    private HashMap<Integer, NodeData> nodes;
    private HashMap<Integer, HashMap<Integer, EdgeData>> edges_src, edges_dest;
    private int e_counter = 0;
    private int mc_counter = 0;

    public BaseDirectedWeightedGraph() {
        this.nodes = new HashMap<Integer, NodeData>();
        this.edges_src = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        this.edges_dest = new HashMap<Integer, HashMap<Integer, EdgeData>>();
    }

    public BaseDirectedWeightedGraph(DirectedWeightedGraph g) {
        this();
        Iterator<NodeData> it_n = g.nodeIter();
        while (it_n.hasNext()) {
            NodeData n = it_n.next();
            this.addNode(new BaseNodeData(n.getKey(), n.getTag(), "" + n.getInfo(), n.getWeight(), n.getLocation()));
        }
        Iterator<EdgeData> it_e = g.edgeIter();
        while (it_e.hasNext()) {
            EdgeData e = it_e.next();
            this.connect(e.getSrc(), e.getDest(), e.getWeight());
            EdgeData e_new = this.getEdge(e.getSrc(), e.getDest());
            if(e_new != null) {
                e_new.setInfo("" + e.getInfo());
                e_new.setTag(e.getTag());
            }
        }
    }

    public void transpose() {
        List<EdgeData> old_edges = new ArrayList<>();
        Iterator<EdgeData> it = this.edgeIter();
        while (it.hasNext()) {
            EdgeData e = it.next();
            old_edges.add(e);
            it.remove();
        }
        for (EdgeData e : old_edges) {
            this.connect(e.getDest(), e.getSrc(), e.getWeight());
        }
    }

    /**
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public NodeData getNode(int key) {
        return this.nodes.getOrDefault(key, null);
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public EdgeData getEdge(int src, int dest) {
        if (this.edges_src.containsKey(src)) {
            return this.edges_src.get(src).getOrDefault(dest, null);
        }
        return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(NodeData n) {
        if(n == null) return;

        this.nodes.put(n.getKey(), n);

        HashMap<Integer, EdgeData> src_e = new HashMap<Integer, EdgeData>();
        this.edges_src.put(n.getKey(), src_e); // Create new HashMap for this src node.

        HashMap<Integer, EdgeData> dest_e = new HashMap<Integer, EdgeData>();
        this.edges_dest.put(n.getKey(), dest_e); // Create new HashMap for this dest node.

        this.mc_counter++;
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (this.getNode(src) == null || this.getNode(dest) == null || w < 0) {
            return;
        }

        HashMap<Integer, EdgeData> src_e = this.edges_src.getOrDefault(src, null);
        HashMap<Integer, EdgeData> dest_e = this.edges_dest.getOrDefault(dest, null);
        // inc the edge counter only if this is new edge and not replace exist one
        if(this.getEdge(src, dest) == null)
            this.e_counter++;

        EdgeData e = new BaseEdgeData(w, this.getNode(src), this.getNode(dest));
        src_e.put(dest, e);
        dest_e.put(src, e);
        this.mc_counter++;
    }

    /**
     * This method returns an Iterator for the
     * collection representing all the nodes in the graph.
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<node_data>
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        return new NodeIterator();
    }

    /**
     * This method returns an Iterator for all the edges in this graph.
     * Note: if any of the edges going out of this node were changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter() {
        return new AllEdgesIterator();
    }

    /**
     * This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node).
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @param node_id
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new EdgeIterator(node_id);
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public NodeData removeNode(int key) {
        if (!this.nodes.containsKey(key)) {
            return null;
        }
        removeEdgesForNode(key);
        this.mc_counter++;
        return this.nodes.remove(key);
    }

    private void removeEdgesForNode(int key) {
        if (this.edges_src.containsKey(key)) {
            e_counter = e_counter - this.edges_src.get(key).size();
            this.edges_src.remove(key);
            this.edges_dest.remove(key);
        }
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (this.getEdge(src, dest) == null) {
            return null;
        }
        this.edges_src.get(src).remove(dest);
        this.e_counter--;
        this.mc_counter++;
        return this.edges_dest.get(dest).remove(src);
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.e_counter;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.mc_counter;
    }

    private class NodeIterator implements Iterator<NodeData> {
        int mc;
        NodeData curr;
        Iterator<NodeData> it;

        public NodeIterator() {
            this.mc = mc_counter;
            this.it = nodes.values().iterator();
            this.curr = null;
        }

        @Override
        public boolean hasNext() {
            return this.it.hasNext();
        }

        @Override
        public NodeData next() {
            this.check_mc();
            this.curr = this.it.next();
            return this.curr;
        }

        @Override
        public void remove() {
            this.check_mc();
            removeEdgesForNode(this.curr.getKey()); // Delete the edges of the node.
            it.remove(); // Delete the node with the HashMap iterator.
            this.mc++; // Increase the iterator changes counter.

            mc_counter++; // Increase the graph changes counter.
        }

        private void check_mc() {
            if (this.mc != mc_counter) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class EdgeIterator implements Iterator<EdgeData> {
        int mc;
        EdgeData curr;
        Iterator<EdgeData> it;

        public EdgeIterator(int node_key) {
            this.mc = mc_counter;
            this.it = edges_src.get(node_key).values().iterator();
            this.curr = null;
        }

        @Override
        public boolean hasNext() {
            return this.it.hasNext();
        }

        @Override
        public EdgeData next() {
            this.check_mc();
            this.curr = this.it.next();
            return this.curr;
        }

        @Override
        public void remove() {
            this.check_mc();
            it.remove(); // Delete the edge with the HashMap iterator from the edges_src.
            this.mc++; // Increase the iterator changes counter.
            mc_counter++; // Increase the graph changes counter.
            edges_dest.get(this.curr.getDest()).remove(this.curr.getSrc());
        }

        private void check_mc() {
            if (this.mc != mc_counter) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class AllEdgesIterator implements Iterator<EdgeData> {
        int mc;
        int curr_it;
        Iterator<EdgeData> it;
        EdgeData curr_edge;
        ArrayList<Integer> nodes;

        public AllEdgesIterator() {
            this.mc = mc_counter;
            this.curr_edge = null;
            this.nodes = new ArrayList<>(edges_src.keySet());

            this.curr_it = 0;
            if(this.nodes.size() > 0)
                this.it = edgeIter(this.nodes.remove(0));
        }

        @Override
        public boolean hasNext() {
            if(it==null) return false;

            while (!this.nodes.isEmpty() && !it.hasNext()) {
                this.it = edgeIter(this.nodes.remove(0));
            }
            return this.it.hasNext();
        }

        @Override
        public EdgeData next() {
            this.check_mc();
            if(it==null) throw new NoSuchElementException();

            while (!this.nodes.isEmpty() && !it.hasNext()) {
                this.it = edgeIter(this.nodes.remove(0));
            }

            this.curr_edge = this.it.next();
            return this.curr_edge;
        }

        @Override
        public void remove() {
            this.check_mc();
            if(this.curr_edge==null || it ==null) throw new IllegalStateException();

            this.it.remove(); // Delete the edge with the HashMap iterator from the edges_src.
            this.curr_edge = null;
            this.mc++; // Increase the iterator changes counter.
        }

        private void check_mc() {
            if (this.mc != mc_counter) {
                throw new ConcurrentModificationException();
            }
        }
    }


}