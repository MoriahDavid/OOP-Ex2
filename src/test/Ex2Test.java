import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ex2Test {

    @Test
    void getGrapg() {
        DirectedWeightedGraph g = Ex2.getGrapg("data/G1.json");
        assertNotNull(g);
        assertEquals(17,g.nodeSize());
    }

    @Test
    void getGrapgAlgo() {
        DirectedWeightedGraphAlgorithms a = Ex2.getGrapgAlgo("data/G1.json");
        assertNotNull(a);
        assertNotNull(a.getGraph());
        assertEquals(17,a.getGraph().nodeSize());
    }
}