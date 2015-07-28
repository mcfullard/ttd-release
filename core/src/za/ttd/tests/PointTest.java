package za.ttd.tests;

import org.junit.Test;
import za.ttd.mapgen.Point;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/07/27.
 */
public class PointTest {
    @Test
    public void testEquals() throws Exception {
        Point p1 = new Point(0,0);
        Point p2 = new Point(1,2);
        Point p3 = new Point(0,0);
        Point p4 = new Point(2,1);
        assertNotEquals(p1,p2);
        assertNotEquals(p2,p4);
        assertEquals(p1,p3);
        p1.r = 1;
        p1.c = 2;
        assertEquals(p1,p2);
    }
}