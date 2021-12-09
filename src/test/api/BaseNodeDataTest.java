package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseNodeDataTest {

    BaseNodeData n1 = new BaseNodeData(10, 5, "Bla", 0.555, new BaseGeoLocation(1,2,0));

    @Test
    void getKey() {
        assertEquals(10, n1.getKey());
    }

    @Test
    void getLocation() {
        GeoLocation g = n1.getLocation();
        assertEquals(1, g.x());
        assertEquals(2, g.y());
        assertEquals(0, g.z());
    }

    @Test
    void setLocation() {
        BaseNodeData n2 = new BaseNodeData(10, 5, "Bla", 0.555, new BaseGeoLocation(1,2,0));
        n2.setLocation(new BaseGeoLocation(4,5,6));

        GeoLocation g = n2.getLocation();
        assertEquals(4, g.x());
        assertEquals(5, g.y());
        assertEquals(6, g.z());
    }

    @Test
    void getWeight() {
        assertEquals(0.555, n1.getWeight());
    }

    @Test
    void setWeight() {
        BaseNodeData n2 = new BaseNodeData(10, 5, "Bla", 0.555, new BaseGeoLocation(1,2,0));
        n2.setWeight(24);
        assertEquals(24, n2.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("Bla", n1.getInfo());
    }

    @Test
    void setInfo() {
        BaseNodeData n2 = new BaseNodeData(10, 5, "Bla", 0.555, new BaseGeoLocation(1,2,0));
        n2.setInfo("BlaBla");
        assertEquals("BlaBla", n2.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(1, n1.getTag());
    }

    @Test
    void setTag() {
        BaseNodeData n2 = new BaseNodeData(10, 5, "Bla", 0.555, new BaseGeoLocation(1,2,0));
        n2.setTag(0);
        assertEquals(0, n2.getTag());
    }
}