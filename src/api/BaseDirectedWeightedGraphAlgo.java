package api;

import com.google.gson.*;

import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
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
    public BaseDirectedWeightedGraph copy() {
        BaseDirectedWeightedGraph new_g = new BaseDirectedWeightedGraph(this.graph);
        return new_g;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {

        BaseDirectedWeightedGraph g = this.copy();

        Iterator<NodeData> it = g.nodeIter();
        NodeData start_node = it.next();

        while (it.hasNext()) {
            NodeData node = it.next();
            // check if already found route from start_node to this node.
            if(node.getWeight() > Double.MAX_VALUE)
                continue;

            if(this.shortestPathDist(g, start_node.getKey(), node.getKey()) == -1)
                return false;
        }

        g.transpose(); // Transpose the graph and check choose node and check again.

        this.reset_nodes(g);

        it = g.nodeIter();
        start_node = it.next();

        while (it.hasNext()) {
            NodeData node = it.next();
            // check have route from start_node to node
            if(node.getWeight() > Double.MAX_VALUE)
                continue;

            if(this.shortestPathDist(g, start_node.getKey(), node.getKey()) == -1)
                return false;
        }

        return true;
    }

    private void reset_nodes(DirectedWeightedGraph g, int tag, double weight){
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData node = it.next();
            node.setTag(tag);
            node.setWeight(weight);
        }
    }
    private void reset_nodes(DirectedWeightedGraph g){
        this.reset_nodes(g,0,0);
    }
    private void reset_nodes(List<NodeData> nodes, int tag, double weight){
        for(int i = 0; i < nodes.size(); i++){ //Set 0 in the node tag.
            nodes.get(i).setTag(tag);
            nodes.get(i).setWeight(weight);
        }
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

        return shortestPathDist(this.copy(), src, dest);
    }

    public double shortestPathDist(DirectedWeightedGraph g, int src, int dest) {
        // TODO: validate the inputs
        Dijkstra(g, src);
        NodeData d = g.getNode(dest);

        if(d.getWeight() == Double.MAX_VALUE) return -1;

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

        DirectedWeightedGraph g = this.copy();

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

            l.add(0, this.graph.getNode(n.getKey()));
        }

        return l;
    }

    /**
     * TODO:
     * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
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
        q.remove(g.getNode(src));
        q.add(g.getNode(src));

        while (!q.isEmpty()){
            NodeData u = q.poll();
            Iterator<EdgeData> it_e = g.edgeIter(u.getKey());
            while (it_e.hasNext()){
                EdgeData e = it_e.next();
                NodeData v = g.getNode(e.getDest());
                double alt = u.getWeight() + e.getWeight();
                if(alt < v.getWeight()){
                    v.setWeight(alt);
                    if(q.remove(v)) // In order to update the order of the queue.
                        q.add(v);
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
        if(!isConnected()){ // if the graph is not connected we can't calculate the center
            return null;
        }
        DirectedWeightedGraph g = this.copy();
        Iterator<NodeData> i = g.nodeIter();

        while(i.hasNext()){
            NodeData n_src = i.next();
            Iterator<NodeData> j = g.nodeIter();
            double max_w = Double.MIN_VALUE;
            while (j.hasNext()){
                NodeData n_dst = j.next();
                if(i==j) continue;
                double w = this.shortestPathDist(this.copy(), n_src.getKey(), n_dst.getKey());
                if(w > max_w){
                    max_w = w;
                    n_src.setWeight(w);
                }
            }
        }

        Iterator<NodeData> it = g.nodeIter();
        NodeData min_node = it.next();;
        while (it.hasNext()){
            NodeData n = it.next();
            if(n.getWeight() < min_node.getWeight()){
                min_node = n;
            }
        }

        return this.graph.getNode(min_node.getKey());
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
        List<NodeData> path_nodes = new ArrayList<>();
        if(cities.size() == 0){
            return null;
        }

        this.reset_nodes(cities, 0, -1); //Set 0 in the node tag and -1 for weight.
        NodeData rand_n = getRandomNode(cities); //Choose node in random.

        rand_n.setTag(1);
        path_nodes.add(rand_n); //Add to the final list.

        int visited_nodes = 1;

        NodeData n = rand_n;
        while(visited_nodes < cities.size()){
            NodeData next = closestNode(n, cities);
            if (next == null){
                next = this.graph.getNode((int)n.getWeight());
            }
            else{
                next.setWeight(n.getKey());
            }
            n = next;
            if(n.getTag()!=1){
                n.setTag(1);
                visited_nodes++;
            }
            path_nodes.add(n);
        }
        this.reset_nodes(cities, 0, 0); // Set 0 in the node tag and 0 for weight.

        return path_nodes;
    }
    public double pathWeight(List<NodeData> l){
        double total_w=0;
        for(int i=0; i<l.size()-1;i++){
            EdgeData e = this.getGraph().getEdge(l.get(i).getKey(), l.get(i+1).getKey());
            if(e==null){
                e = this.getGraph().getEdge(l.get(i+1).getKey(), l.get(i).getKey());
            }
            total_w += e.getWeight();
        }
        return total_w;
    }

    private NodeData getRandomNode(List<NodeData> ln){
        if(ln.size() == 0){
            return null;
        }
        int x = new Random().nextInt(ln.size());
        return ln.get(x);
    }

    private NodeData closestNode(NodeData n1, List<NodeData> ln){
        double c = Integer.MAX_VALUE;
        NodeData closestNode = null; // The closest node to n1.
        for(int i = 0; i < ln.size(); i++){
            EdgeData e = this.graph.getEdge(n1.getKey(), ln.get(i).getKey());
            if(e != null && ln.get(i).getTag()==0) { // If this edge exist and if we visit this node before.
                if (e.getWeight() < c) { // If the weight (dist) is smaller.
                    c = e.getWeight();
                    closestNode = ln.get(i);
                }
            }
        }
        return closestNode;
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
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            GsonBuilder gsonBuilder = new GsonBuilder();

            gsonBuilder.registerTypeAdapter(BaseDirectedWeightedGraph.class, graphDeserializer);

            Gson customGson = gsonBuilder.create();
            this.graph = customGson.fromJson(br, BaseDirectedWeightedGraph.class);
            return true;

        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            e.printStackTrace();
            return false;
        }

    }

    // change serialization for specific types
    JsonDeserializer<DirectedWeightedGraph> graphDeserializer = new JsonDeserializer<DirectedWeightedGraph>() {
        @Override
        public DirectedWeightedGraph deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            BaseDirectedWeightedGraph graph = new BaseDirectedWeightedGraph();

            JsonArray j_n = jsonObject.getAsJsonArray("Nodes");
            for (JsonElement n: j_n) {
                graph.addNode(new BaseNodeData(n.getAsJsonObject().get("id").getAsInt(), 0,"",0,
                              new BaseGeoLocation(n.getAsJsonObject().get("pos").getAsString())));
            }

            JsonArray j_e = jsonObject.getAsJsonArray("Edges");
            for (JsonElement e: j_e) {
                graph.connect(e.getAsJsonObject().get("src").getAsInt(),
                        e.getAsJsonObject().get("dest").getAsInt(),
                        e.getAsJsonObject().get("w").getAsDouble());
            }

            return graph;
        }
    };

    public static void main(String[] args){
        BaseDirectedWeightedGraphAlgo a = new BaseDirectedWeightedGraphAlgo();

        a.load("data\\G2.json");

        System.out.println(a.shortestPath(0,4));
        System.out.println(a.center().getKey());
        System.out.println(a.isConnected());

        List<NodeData> l = new ArrayList<>();
        Iterator<NodeData> it = a.getGraph().nodeIter();
        while (it.hasNext()){
            l.add(it.next());
        }
        System.out.println("Total Nodes in graph: " + l.size());
        List<NodeData> r = a.tsp(l);
        System.out.println("Total Nodes in tsp: " + r.size());
        for (NodeData n: r){
            System.out.print(n.getKey() + " -> ");
        }
        System.out.println();

        System.out.println("Total w: "+a.pathWeight(r));
    }
}
