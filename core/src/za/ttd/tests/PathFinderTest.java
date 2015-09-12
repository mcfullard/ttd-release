package za.ttd.tests;

import org.junit.Test;
import za.ttd.characters.objects.Position;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Map;
import za.ttd.pathfinding.Node;
import za.ttd.pathfinding.PathFinder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/09/10.
 */
public class PathFinderTest {
    @Test
    public void testConstructor() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        Position p1 = new Position(1,1);
        Position p2 = new Position(1,2);
        Position p3 = new Position(1,3);
        Position p4 = new Position(2,3);
        Position p5 = new Position(3,3);
        assertEquals(p2, pf.getNodes().get(p1).shortestPathTo(p5));
        assertEquals(p3, pf.getNodes().get(p2).shortestPathTo(p5));
        assertEquals(p4, pf.getNodes().get(p3).shortestPathTo(p5));
        assertEquals(p5, pf.getNodes().get(p4).shortestPathTo(p5));
    }

    @Test
    public void testGetLeftPosition() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        Position p1 = new Position(1,1);
        Position p2 = new Position(2,1);
        Position p3 = new Position(5,2);
        assertEquals(p1, pf.getLeftPosition(p2));
        assertNull(pf.getLeftPosition(p1));
        assertNull(pf.getLeftPosition(p3));
    }

    @Test
    public void testGetRightPosition() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        Position p1 = new Position(1,1);
        Position p2 = new Position(2,1);
        Position p3 = new Position(20,1);
        Position p4 = new Position(1,2);
        assertEquals(p2, pf.getRightPosition(p1));
        assertNull(pf.getRightPosition(p3));
        assertNull(pf.getRightPosition(p4));
    }

    @Test
    public void testGetUpPosition() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        Position p1 = new Position(1,1);
        Position p2 = new Position(1,2);
        Position p3 = new Position(4,5);
        assertNull(pf.getUpPosition(p1));
        assertEquals(p1, pf.getUpPosition(p2));
        assertNull(pf.getUpPosition(p3));
    }

    @Test
    public void testGetDownPosition() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        Position p1 = new Position(1,1);
        Position p2 = new Position(2,1);
        Position p3 = new Position(20,1);
        Position p4 = new Position(1,2);
        Position p5 = new Position(20,2);
        Position p6 = new Position(1,31);
        assertEquals(p4, pf.getDownPosition(p1));
        assertNull(pf.getDownPosition(p2));
        assertEquals(p5, pf.getDownPosition(p3));
        assertNull(pf.getDownPosition(p6));
    }

    @Test
    public void testGetNodesFromMap() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        pf.populateNodes();
        java.util.Map<Position, Node> nodes = pf.getNodes();
        assertEquals(new Position(1,1), nodes.get(new Position(1,1)).getOrigin());
        assertEquals(new Position(1,9), nodes.get(new Position(1,9)).getOrigin());
    }

    @Test
    public void testGetAdjacentNodes() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        pf.populateNodes();
        Node node = new Node(new Position(3,5));
        Node top = pf.getNodes().get(new Position(3,4));
        Node bottom = pf.getNodes().get(new Position(3,6));
        Node left = pf.getNodes().get(new Position(2,5));
        Node right = pf.getNodes().get(new Position(4,5));
        Set<Node> adjacent = new HashSet<>();
        adjacent.add(top);
        adjacent.add(bottom);
        adjacent.add(left);
        adjacent.add(right);
        assertEquals(adjacent, pf.getAdjacentNodes(node));
    }

    @Test
    public void testAttachEdges() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        pf.populateNodes();
        pf.attachEdges();
        fail();
    }

}