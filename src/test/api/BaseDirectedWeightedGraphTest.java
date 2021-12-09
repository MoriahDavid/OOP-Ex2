package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseDirectedWeightedGraphTest {

    BaseDirectedWeightedGraph getGraph() {
        BaseDirectedWeightedGraph g = new BaseDirectedWeightedGraph();
        g.addNode(new BaseNodeData(1,0,"", 0, new BaseGeoLocation(0,0,0)));
        g.addNode(new BaseNodeData(2,0,"", 0, new BaseGeoLocation(0,0,0)));
        g.connect(1,2,20);
        return g;
    }

    @Test
    void transpose() {
        BaseDirectedWeightedGraph g = getGraph();
        assertNotNull(g.getEdge(1,2));
        assertNull(g.getEdge(2,1));
        g.transpose();
        assertNotNull(g.getEdge(2,1));
        assertNull(g.getEdge(1,2));
    }

    @Test
    void getNode() {
        BaseDirectedWeightedGraph g = getGraph();
        assertNotNull(g.getNode(1));
        assertInstanceOf(BaseNodeData.class, g.getNode(1));
        assertNull(g.getNode(3));
    }

    @Test
    void getEdge() {
    }

    @Test
    void addNode() {
    }

    @Test
    void connect() {
    }

    @Test
    void nodeIter() {
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }
}