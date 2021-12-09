package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseGeoLocationTest {

    GeoLocation l1 = new BaseGeoLocation(1,2,3);
    GeoLocation l2 = new BaseGeoLocation(1,2);
    GeoLocation l3 = new BaseGeoLocation("1.0,2.0,3.0");
    GeoLocation l4 = new BaseGeoLocation(1,3);

    @Test
    void x() {
        assertEquals(1, l1.x());
        assertEquals(1, l2.x());
        assertEquals(1, l3.x());
    }

    @Test
    void y() {
        assertEquals(2, l1.y());
        assertEquals(2, l2.y());
        assertEquals(2, l3.y());
    }

    @Test
    void z() {
        assertEquals(3, l1.z());
        assertEquals(0, l2.z());
        assertEquals(3, l3.z());
    }

    @Test
    void distance() {
        assertEquals(0, l1.distance(l3));
        assertEquals(1, l2.distance(l4));
    }

    @Test
    void BaseGeoLocation(){
        assertThrows(NumberFormatException.class, () -> new BaseGeoLocation("1,2"));
        assertThrows(NumberFormatException.class, () -> new BaseGeoLocation(""));
        assertThrows(NumberFormatException.class, () -> new BaseGeoLocation("1,sds,5"));
    }
}