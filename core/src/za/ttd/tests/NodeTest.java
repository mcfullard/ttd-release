package za.ttd.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import za.ttd.characters.objects.Position;
import za.ttd.exceptions.AllPathsNotExploredException;
import za.ttd.pathfinding.Edge;
import za.ttd.pathfinding.EdgeContainer;
import za.ttd.pathfinding.Node;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/09/15.
 */
public class NodeTest {
    private Node n1, n2, n3, n4, n5, n6;
    private Edge e1, e2, e3, e4, e5, e6;
    private List<Node> nodes;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void initObjects() {
        n1 = new Node(new Position(1,1));
        n2 = new Node(new Position(2,1));
        n3 = new Node(new Position(3,1));
        n4 = new Node(new Position(3,2));
        n5 = new Node(new Position(2,2));
        n6 = new Node(new Position(1,2));
        e1 = new Edge(n1, n2);
        e2 = new Edge(n2, n3);
        e3 = new Edge(n3, n4);
        e4 = new Edge(n4, n5);
        e5 = new Edge(n5, n6);
        e6 = new Edge(n6, n1);
        nodes = Arrays.asList(n1,n2,n3,n4,n5,n6);
    }

    @Test
    public void testAddEdges() throws Exception {
        assertFalse(n1.getEdges().contains(e1));
        n1.addEdges(e1, e6);
        assertTrue(n1.getEdges().contains(e1));
        assertTrue(n1.getEdges().contains(e6));
        assertEquals(2, n1.getEdges().size());
    }

    @Test
    public void testInitDistanceVector() throws Exception {
        n1.addEdges(e1, e6);
        assertEquals(2, n1.getEdges().size());
        assertNull(n1.getDistanceVector().get(n2.getOrigin()));
        n1.initDistanceVector(nodes);
        assertEquals(EdgeContainer.LARGE, n1.getDistanceVector().get(n3.getOrigin()).distance.intValue());
        assertEquals(EdgeContainer.LARGE, n1.getDistanceVector().get(n4.getOrigin()).distance.intValue());
        assertEquals(new Integer(1), n1.getDistanceVector().get(n2.getOrigin()).distance);
        assertEquals(new Integer(1), n1.getDistanceVector().get(n6.getOrigin()).distance);
        assertEquals(n1, n1.getDistanceVector().get(n6.getOrigin()).edge.getAdjacent(n6));
        assertEquals(n2, n1.getDistanceVector().get(n2.getOrigin()).edge.getAdjacent(n1));
        assertNull(n2.getDistanceVector().get(n1.getOrigin()));
        assertNull(n2.getDistanceVector().get(n3.getOrigin()));
    }

    @Test
    public void testUpdateDistanceVector() throws Exception {
        n1.addEdges(e1, e6);
        n2.addEdges(e1, e2);
        n3.addEdges(e2, e3);
        n4.addEdges(e3, e4);
        n5.addEdges(e4, e5);
        n6.addEdges(e5, e6);
        for(Node node : nodes) {
            node.initDistanceVector(nodes);
        }
        for(Node node : nodes) {
            node.updateDistanceVector(nodes);
        }
        assertEquals(2, n1.getDistanceVector().get(n3.getOrigin()).distance.intValue());
        assertEquals(n2, n1.getDistanceVector().get(n3.getOrigin()).edge.getAdjacent(n1));
        assertEquals(null, n1.getDistanceVector().get(n4.getOrigin()).edge);
    }

    @Test
    public void testShortestPathTo() throws Exception {
        n1.addEdges(e1, e6);
        n2.addEdges(e1, e2);
        n3.addEdges(e2, e3);
        n4.addEdges(e3, e4);
        n5.addEdges(e4, e5);
        n6.addEdges(e5, e6);
        for(Node node : nodes) {
            node.initDistanceVector(nodes);
        }
        for(Node node : nodes) {
            node.updateDistanceVector(nodes);
        }
        assertEquals(n2.getOrigin(), n1.shortestPathTo(n3.getOrigin()));
        exception.expect(AllPathsNotExploredException.class);
        n1.shortestPathTo(n4.getOrigin());
    }
}