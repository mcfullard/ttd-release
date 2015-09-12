package za.ttd.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import za.ttd.characters.objects.Position;
import za.ttd.exceptions.NodeNotIncidentOnEdgeException;
import za.ttd.pathfinding.Edge;
import za.ttd.pathfinding.Node;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/09/12.
 */
public class EdgeTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetAdjacent() throws Exception {
        Node n1 = new Node(new Position(1,1));
        Node n2 = new Node(new Position(1,2));
        Node n3 = new Node(new Position(1,3));
        Edge e1 = new Edge(n1, n2);
        assertEquals(n2, e1.getAdjacent(n1));
        assertEquals(n1, e1.getAdjacent(n2));
        exception.expect(NodeNotIncidentOnEdgeException.class);
        e1.getAdjacent(n3);
    }

    @Test
    public void testSetAdjacent() throws Exception {
        Node n1 = new Node(new Position(1,1));
        Node n2 = new Node(new Position(1,2));
        Node n3 = new Node(new Position(1,3));
        Edge e1 = new Edge(n1, n2);
        assertEquals(n2, e1.getAdjacent(n1));
        e1.setAdjacent(n1, n3);
        assertEquals(n3, e1.getAdjacent(n1));
        exception.expect(NodeNotIncidentOnEdgeException.class);
        e1.getAdjacent(n2);
    }

    @Test
    public void testGetAdjacentPosition() throws Exception {
        Node n1 = new Node(new Position(1,1));
        Node n2 = new Node(new Position(1,2));
        Node n3 = new Node(new Position(1,3));
        Edge e1 = new Edge(n1, n2);
        assertEquals(n2.getOrigin(), e1.getAdjacentPosition(n1));
        assertEquals(n1.getOrigin(), e1.getAdjacentPosition(n2));
        exception.expect(NodeNotIncidentOnEdgeException.class);
        e1.getAdjacentPosition(n3);
    }

    @Test
    public void testClone() throws Exception {
        Node n1 = new Node(new Position(1,1));
        Node n2 = new Node(new Position(1,2));
        Edge e1 = new Edge(n1,n2);
        Edge clone = e1.clone();
        assertEquals(n1, clone.getSource());
        assertEquals(n2, clone.getDestination());
        assertEquals(e1.getWeight(), clone.getWeight());
    }
}