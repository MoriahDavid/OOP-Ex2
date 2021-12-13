package api;

import java.util.Random;

public class GraphGenerator {

    int nodes, edges;
    int max_x_location, max_y_location;
    double min_weight, max_weight;
    int last_key;
    Random r;

    /**
     * Class for generating graphs.
     * @param seed - seed for random.
     * @param nodes - how many nodes will create.
     * @param edges - how many edges create for each node.
     * @param min_weight - the min weight of edges.
     * @param max_weight - the max weight of edges.
     */
    public GraphGenerator(long seed, int nodes, int edges, double min_weight, double max_weight){
        this.r = new Random(seed* 123456L);
        this.nodes = nodes;
        this.edges = edges;
        this.min_weight = min_weight;
        this.max_weight = max_weight;
        this.last_key = 0;
    }

    /**
     *
     * @param seed - seed for random.
     * @param nodes - how many nodes will create.
     * @param edges - how many edges create for each node.
     * @param min_weight - the min weight of edges.
     * @param max_weight - the max weight of edges.
     * @param max_x_location - the max x for location.
     * @param max_y_location - the max y for location.
     */
    public GraphGenerator(long seed, int nodes, int edges, double min_weight, double max_weight, int max_x_location, int max_y_location){
        this(seed, nodes, edges, min_weight, max_weight);
        this.max_x_location = max_x_location;
        this.max_y_location = max_y_location;
    }
    private double get_rand_weight(){
        return map(this.r.nextDouble(), 0.0, 1.0, this.min_weight, this.max_weight);
    }
    private GeoLocation get_rand_location(){
        if(this.max_x_location == 0 || this.max_y_location == 0)
            return new BaseGeoLocation(0, 0);

        return new BaseGeoLocation(this.r.nextInt(max_x_location), this.r.nextInt(max_y_location));
    }
    private NodeData get_rand_node(int key){
        return new BaseNodeData(key,0, "", 0, get_rand_location());
    }
    private int get_rand_key(){
        return this.r.nextInt(this.nodes);
    }
    public DirectedWeightedGraph get_rand_graph(){
        DirectedWeightedGraph g = new BaseDirectedWeightedGraph();
        for(int i = 0; i < this.nodes; i++){ // Create nodes
            g.addNode(this.get_rand_node(i));
        }
        for(int src = 0; src < this.nodes; src++){ // Create Edges
            for(int j = 0; j < this.edges; j++){
                int dest = this.get_rand_key();

                if (dest == src || g.getEdge(src, dest)!=null){
                    j--;
                    continue;
                }

                g.connect(src, dest, this.get_rand_weight());
            }
        }
        return g;
    }

    private static double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static void main(String[] args) {
        GraphGenerator g = new GraphGenerator(2, 10,5, 0.5, 7.0, 20,20);

        DirectedWeightedGraph graph = g.get_rand_graph();
        System.out.println("Created nodes: "+graph.nodeSize());
        System.out.println("Created edges: "+graph.edgeSize());
        graph.edgeIter().forEachRemaining((n) -> System.out.println(n.getSrc() + " -> "+n.getDest() + " ("+n.getWeight()+")"));
    }

}
