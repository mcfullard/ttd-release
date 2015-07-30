package za.ttd.tests;

import org.junit.Test;
import za.ttd.mapgen.Block;
import za.ttd.mapgen.Coordinate;
import za.ttd.mapgen.Point;
import za.ttd.mapgen.Shape;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/07/27.
 */
public class BlockTest {

    @Test
    public void testConstructor() throws Exception {
        Block b1 = new Block(0, 1, Shape.L);
    }

    @Test
    public void testGetHasRight() throws Exception {
        Block b1 = new Block(0,0,Shape.CORNER);
        Coordinate c1 = new Coordinate(0,0);
        Set<Coordinate> s1 = new HashSet<>();
        s1.add(c1);
        assertEquals(s1, b1.getHasRight());
        b1.rotate();
        Coordinate c2 = new Coordinate(1,-1);
        Set<Coordinate> s2 = new HashSet<>();
        s2.add(c2);
        assertEquals(c2, b1.getHasRight());
    }

    @Test
    public void testGetHasDown() throws Exception {

    }

    @Test
    public void testRotate() throws Exception {
        Block b1 = new Block(0, 1, Shape.L);
        Block b2 = new Block(0, 1, Shape.L);
        assertEquals(b1, b2);
        b2.rotate();
        assertNotEquals(b1,b2);
        for(int i = 0; i < 3; i++)
            b2.rotate();
        assertEquals(b1, b2);
    }

    @Test
    public void testGetPositions() throws Exception {
        Set<Point> p = new HashSet<>();
        p.add(new Point(0, 0));
        p.add(new Point(0, 1));
        p.add(new Point(1, 1));
        Block b = new Block(0,0,Shape.CORNER);
        assertEquals(p, b.getPositions());
    }

    @Test
    public void testEquals() throws Exception {
        Block b1 = new Block(0,0, Shape.L);
        Block b2 = new Block(0,0, Shape.L);
        Block b3 = new Block(0,1, Shape.L);
        assertEquals(b1, b2);
        assertNotEquals(b1, b3);
        b1.getOrigin().c = 1;
        assertEquals(b1, b3);
    }
}