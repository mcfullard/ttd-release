package za.ttd.tests;

import org.junit.Test;
import za.ttd.characters.objects.Position;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Map;
import za.ttd.pathfinding.Edge;
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
        java.util.Map<Position, Node> nodes = pf.getNodes();
        assertEquals(new Position(1,1), nodes.get(new Position(1,1)).getOrigin());
        assertEquals(new Position(1,9), nodes.get(new Position(1,9)).getOrigin());
    }

    @Test
    public void testGetAdjacentNodes() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
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
        node = new Node(new Position(3,3));
        left = pf.getNodes().get(new Position(2,3));
        bottom = pf.getNodes().get(new Position(3,4));
        adjacent.clear();
        adjacent.add(left);
        adjacent.add(bottom);
        assertEquals(adjacent, pf.getAdjacentNodes(node));
    }

    @Test
    public void testAttachEdges() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        Position p1 = new Position(3,3);
        Position p2 = new Position(2,3);
        Position p3 = new Position(3,4);
        Node node = pf.getNodes().get(p1);
        Set<Edge> edges = node.getEdges();
        int match = 0;
        for(Edge edge: edges){
            if(edge.getAdjacent(node).getOrigin().equals(p2)) {
                match++;
            }
            else if(edge.getAdjacent(node).getOrigin().equals(p3)){
                match++;
            }
        }
        assertEquals(2, match);
        assertEquals(match, edges.size());

        p1 = new Position(2,3);
        p2 = new Position(1,3);
        p3 = new Position(3,3);
        node = pf.getNodes().get(p1);
        edges = node.getEdges();
        match = 0;
        for(Edge edge: edges){
            if(edge.getAdjacent(node).getOrigin().equals(p2)) {
                match++;
            }
            else if(edge.getAdjacent(node).getOrigin().equals(p3)){
                match++;
            }
        }
        assertEquals(2, match);
        assertEquals(match, edges.size());

        p1 = new Position(1,5);
        p2 = new Position(1,4);
        p3 = new Position(1,6);
        Position p4 = new Position(2,5);
        node = pf.getNodes().get(p1);
        edges = node.getEdges();
        match = 0;
        for(Edge edge: edges) {
            if(edge.getAdjacent(node).getOrigin().equals(p2)) {
                match++;
            } else if(edge.getAdjacent(node).getOrigin().equals(p3)){
                match++;
            } else if(edge.getAdjacent(node).getOrigin().equals(p4)){
                match++;
            }
        }
        assertEquals(3, match);
        assertEquals(match, edges.size());
    }

    @Test
    public void getWithinRadiusOfTest() throws Exception {
        Map map = Grid.generateMap(15,5,1264);
        PathFinder pf = new PathFinder(map);
        Position origin = new Position(1,1);
        Position p1 = new Position(1,2);
        Position p2 = new Position(1,3);
        Position p3 = new Position(2,3);
        Position p4 = new Position(3,3);
        Position p5 = new Position(2,1);
        Position p6 = new Position(3,1);
        Set<Position> s1 = new HashSet<>();
        Set<Position> s2 = new HashSet<>();
        s1.add(p1);
        s1.add(p5);
        s2.add(p1);
        s2.add(p2);
        s2.add(p3);
        s2.add(p4);
        s2.add(p5);
        s2.add(p6);
        assertEquals(s1, pf.getWithinRadiusOf(origin, 1));
        assertEquals(s2, pf.getWithinRadiusOf(origin, 2));
    }

}