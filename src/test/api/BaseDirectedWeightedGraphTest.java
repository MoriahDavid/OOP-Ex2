package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseDirectedWeightedGraphTest {

    BaseDirectedWeightedGraph getNewGraph() {
        BaseDirectedWeightedGraph g = new BaseDirectedWeightedGraph();
        g.addNode(new BaseNodeData(1,0,"", 0, new BaseGeoLocation(0,0,0)));
        g.addNode(new BaseNodeData(2,0,"", 0, new BaseGeoLocation(0,0,0)));
        g.connect(1,2,20);
        return g;
    }

    @Test
    void transpose() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertNotNull(g.getEdge(1,2));
        assertNull(g.getEdge(2,1));
        g.transpose();
        assertNotNull(g.getEdge(2,1));
        assertNull(g.getEdge(1,2));
    }

    @Test
    void getNode() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertNotNull(g.getNode(1));
        assertInstanceOf(BaseNodeData.class, g.getNode(1));
        assertNull(g.getNode(3));
    }

    @Test
    void getEdge() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertNotNull(g.getEdge(1,2));
        assertNull(g.getEdge(2,1));
    }

    @Test
    void addNode() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertNull(g.getNode(45));
        g.addNode(new BaseNodeData(45, 0, "", 22, new BaseGeoLocation(10,20,0)));
        assertNotNull(g.getNode(45));
    }

    @Test
    void connect() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertNull(g.getEdge(2, 1));
        g.connect(2,1,80.0);
        assertNotNull(g.getEdge(2, 1));
    }

    @Test
    void removeNode() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertNotNull(g.getNode(1));
        assertNotNull(g.getEdge(1, 2));
        g.removeNode(1);
        assertNull(g.getNode(1));
        assertNull(g.getEdge(1, 2));
    }

    @Test
    void removeEdge() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertNotNull(g.getNode(1));
        assertNotNull(g.getEdge(1, 2));
        g.removeEdge(1, 2);
        assertNotNull(g.getNode(1));
        assertNull(g.getEdge(1, 2));
    }

    @Test
    void nodeSize() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertEquals(2, g.nodeSize());
    }

    @Test
    void edgeSize() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertEquals(1, g.edgeSize());
    }

    @Test
    void getMC() {
        BaseDirectedWeightedGraph g = getNewGraph();
        assertEquals(3, g.getMC());
    }
}