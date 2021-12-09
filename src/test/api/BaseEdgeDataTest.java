package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseEdgeDataTest {

    BaseNodeData n1 = new BaseNodeData(10, 5, "Bla", 0.555, new BaseGeoLocation(1,2,0));
    BaseNodeData n2 = new BaseNodeData(11, 5, "BlaBla", 10.9, new BaseGeoLocation(10,21,0));
    BaseEdgeData e1 = new BaseEdgeData(24.24, n1, n2, 1, "eBla");

    @Test
    void getSrc() {
        assertEquals(n1.getKey(), e1.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(n2.getKey(), e1.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(24.24, e1.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("eBla", e1.getInfo());
    }

    @Test
    void setInfo() {
        BaseNodeData n1 = new BaseNodeData(10, 5, "Bla", 0.555, new BaseGeoLocation(1,2,0));
        BaseNodeData n2 = new BaseNodeData(11, 5, "BlaBla", 10.9, new BaseGeoLocation(10,21,0));
        BaseEdgeData e1 = new BaseEdgeData(24.24, n1, n2, 1, "eBla");
        e1.setInfo("BlaBlaBla");
        assertEquals("BlaBlaBla", e1.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(1, e1.getTag());
    }

    @Test
    void setTag() {
        BaseNodeData n1 = new BaseNodeData(10, 5, "Bla", 0.555, new BaseGeoLocation(1,2,0));
        BaseNodeData n2 = new BaseNodeData(11, 5, "BlaBla", 10.9, new BaseGeoLocation(10,21,0));
        BaseEdgeData e1 = new BaseEdgeData(24.24, n1, n2, 1, "eBla");
        e1.setTag(0);
        assertEquals(0, e1.getTag());
    }
}