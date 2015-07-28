package za.ttd.tests;

import org.junit.Test;
import za.ttd.mapgen.Coordinate;
import za.ttd.mapgen.Point;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/07/27.
 */
public class CoordinateTest {

    @Test
    public void testAddChild() throws Exception {
        Coordinate p = new Coordinate(0, 0);
        Coordinate c1 = new Coordinate(0, 1, p);
        p.addChild(c1);
        assertTrue(p.getChildren().contains(c1));
    }

    @Test
    public void testNegateCol() throws Exception {
        Coordinate c1 = new Coordinate(1,1);
        Coordinate c2 = new Coordinate(1,-1);
        assertNotEquals(c1,c2);
        c2.negateCol();
        assertEquals(c1,c2);
    }

    @Test
    public void testSwitchRowCol() throws Exception {
        Coordinate c1 = new Coordinate(1,9);
        Coordinate c2 = new Coordinate(9,1);
        assertNotEquals(c1,c2);
        c2.switchRowCol();
        assertEquals(c1,c2);
    }

    @Test
    public void testGetAbsoluteValue() throws Exception {
        Coordinate p = new Coordinate(1,1);
        Coordinate c1 = new Coordinate(-1, -1, p);
        Coordinate c2 = new Coordinate(0, 0);
        assertEquals(new Point(0,0), c1.getAbsoluteValue());
        assertEquals(c1.getAbsoluteValue(), c2.getAbsoluteValue());
    }

    @Test
    public void testEquals() throws Exception {
        Coordinate c1 = new Coordinate(1,2);
        Coordinate c2 = new Coordinate(1,1);
        Coordinate c3 = new Coordinate(0, 1, c2);
        c2.addChild(c3);
        Coordinate c4 = new Coordinate(3,1, c2);
        c2.addChild(c4);
        Point p1 = new Point(1,2);
        assertNotEquals(p1,c1);
        assertNotEquals(c1, c2);
        assertNotEquals(c2, c3);
        assertEquals(c1, c3);
        c2.r = 1;
        c2.c = 2;
        assertEquals(c1, c2);
    }
}