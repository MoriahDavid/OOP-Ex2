package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseDirectedWeightedGraphAlgoTest {

    BaseDirectedWeightedGraphAlgo algoG3, algoG2, algoG1;


    BaseDirectedWeightedGraph getNewGraph() {
        BaseDirectedWeightedGraph g = new BaseDirectedWeightedGraph();
        g.addNode(new BaseNodeData(1,0,"", 0, new BaseGeoLocation(0,0,0)));
        g.addNode(new BaseNodeData(2,0,"", 0, new BaseGeoLocation(0,0,0)));
        g.connect(1,2,20);
        return g;
    }

    BaseDirectedWeightedGraphAlgo getNewGraphAlgo(){
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        BaseDirectedWeightedGraph g = getNewGraph();
        algo.init(g);
        return algo;
    }

    @BeforeEach
    public void setupUp() {
        algoG1 = new BaseDirectedWeightedGraphAlgo();
        algoG1.load("data\\G1.json");

        this.algoG2 = new BaseDirectedWeightedGraphAlgo();
        algoG2.load("data\\G2.json");

        this.algoG3 = new BaseDirectedWeightedGraphAlgo();
        algoG3.load("data\\G3.json");
    }

    @Test
    void init() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        BaseDirectedWeightedGraph g = getNewGraph();
        algo.init(g);

        assertEquals(g, algo.getGraph());
    }

    @Test
    void getGraph() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        BaseDirectedWeightedGraph g = getNewGraph();
        algo.init(g);

        assertEquals(g, algo.getGraph());
    }

    @Test
    void copy() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        BaseDirectedWeightedGraph g = getNewGraph();
        algo.init(g);

        BaseDirectedWeightedGraph g_copy = algo.copy();

        assertNotEquals(g_copy, g);
        assertEquals(g, algo.getGraph());
        assertNotEquals(g_copy, algo.getGraph());
    }

    @Test
    void isConnected() {
        BaseDirectedWeightedGraphAlgo algo = getNewGraphAlgo();
        assertFalse(algo.isConnected());

        algo.getGraph().connect(2,1,7);
        assertTrue(algo.isConnected());

        algo.getGraph().removeEdge(1,2);
        algo.getGraph().removeEdge(2,1);
        assertFalse(algo.isConnected()); // Empty graph
    }

    @Test
    void isConnectedG1() {
        assertTrue(algoG1.isConnected());
    }
    @Test
    void isConnectedG2() {
        assertTrue(algoG2.isConnected());
    }
    @Test
    void isConnectedG3() {
        assertTrue(algoG3.isConnected());
    }

    @Test
    void isConnected1000() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        algo.load("data\\1000Nodes.json");
        assertTrue(algo.isConnected());
    }

//    @Test
//    void isConnected10000() {
//        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
//        algo.load("data\\10000Nodes.json");
//        assertTrue(algo.isConnected());
//    }

    @Test
    void shortestPathDist() {
        BaseDirectedWeightedGraphAlgo algo = getNewGraphAlgo();
        assertEquals(20, algo.shortestPathDist(1,2));
        algo.getGraph().addNode(new BaseNodeData(3, 0,"",5, new BaseGeoLocation(0,0,0)));
        algo.getGraph().connect(3,1,7);
        algo.getGraph().connect(3,2,50);
        assertEquals(27, algo.shortestPathDist(3,2));
    }

    @Test
    void shortestPath() {
        BaseDirectedWeightedGraphAlgo algo = getNewGraphAlgo();
        assertEquals(20, algo.shortestPathDist(1,2));
        algo.getGraph().addNode(new BaseNodeData(3, 0,"",5, new BaseGeoLocation(0,0,0)));
        algo.getGraph().connect(3,1,7);
        algo.getGraph().connect(3,2,50);

        List<NodeData> l = algo.shortestPath(3,2);

        assertEquals(3, l.size());

        assertEquals(3, l.get(0).getKey());
        assertEquals(1, l.get(1).getKey());
        assertEquals(2, l.get(2).getKey());
    }

    @Test
    void centerG1() {
        assertEquals(8, algoG1.center().getKey());
    }
    @Test
    void centerG2() {
        assertEquals(0, algoG2.center().getKey());
    }
    @Test
    void centerG3() {
        assertEquals(40, algoG3.center().getKey());
    }

    @Test
    void tsp() {
    }

    @Test
    void tspG1() {
        List<NodeData> l = new ArrayList<>();
        Iterator<NodeData> it = algoG1.getGraph().nodeIter();
        it.forEachRemaining((n) -> l.add(n));

        algoG1.tsp(l);
    }
    @Test
    void tspG2() {
        List<NodeData> l = new ArrayList<>();
        Iterator<NodeData> it = algoG2.getGraph().nodeIter();
        it.forEachRemaining((n) -> l.add(n));

        algoG2.tsp(l);
    }
    @Test
    void tspG3() {
        List<NodeData> l = new ArrayList<>();
        Iterator<NodeData> it = algoG3.getGraph().nodeIter();
        it.forEachRemaining((n) -> l.add(n));

        algoG3.tsp(l);
    }
    @Test
    void tsp1000() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        algo.load("data\\1000Nodes.json");
        List<NodeData> l = new ArrayList<>();
        Iterator<NodeData> it = algo.getGraph().nodeIter();
        it.forEachRemaining((n) -> l.add(n));

        algo.tsp(l);
    }
    @Test
    void tsp10000() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        algo.load("data\\10000Nodes.json");
        List<NodeData> l = new ArrayList<>();
        Iterator<NodeData> it = algo.getGraph().nodeIter();
        it.forEachRemaining((n) -> l.add(n));

        System.out.println(algo.pathWeight(algo.tsp(l)));
    }

    @Test
    void pathWeight() {
        BaseDirectedWeightedGraphAlgo algo = getNewGraphAlgo();
        assertEquals(20, algo.shortestPathDist(1,2));
        algo.getGraph().addNode(new BaseNodeData(3, 0,"",5, new BaseGeoLocation(0,0,0)));
        algo.getGraph().connect(3,1,7);
        algo.getGraph().connect(3,2,50);

        List<NodeData> l = new ArrayList<NodeData>();
        l.add(algo.getGraph().getNode(3));
        l.add(algo.getGraph().getNode(1));
        l.add(algo.getGraph().getNode(2));

        assertEquals(27, algo.pathWeight(l));
    }
}