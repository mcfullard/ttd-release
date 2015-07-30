package za.ttd.tests;

import org.junit.Test;
import za.ttd.mapgen.Block;
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
    public void testPopulateDirectionPoints() {
        Block b1 = new Block(0,0,Shape.L);
        Set<Point> expectedDown = new HashSet<>();
        Set<Point> expectedRight = new HashSet<>();
        Set<Point> expectedBoth = new HashSet<>();
        Set<Point> expectedNeither = new HashSet<>();
        Set<Point> actualDown = new HashSet<>();
        Set<Point> actualRight = new HashSet<>();
        Set<Point> actualBoth = new HashSet<>();
        Set<Point> actualNeither = new HashSet<>();
        expectedRight.add(new Point(0,0));
        expectedRight.add(new Point(0,1));
        expectedDown.add(new Point(0,2));
        expectedNeither.add(new Point(1,2));
        b1.populateDirectionPoints(actualRight, actualDown, actualBoth, actualNeither);
        assertEquals(expectedBoth, actualBoth);
        assertEquals(expectedDown, actualDown);
        assertEquals(expectedRight, actualRight);
        assertEquals(expectedNeither, actualNeither);

        Block b2 = new Block(0,0,Shape.T);
        expectedDown = new HashSet<>();
        expectedRight = new HashSet<>();
        expectedBoth = new HashSet<>();
        expectedNeither = new HashSet<>();
        actualDown = new HashSet<>();
        actualRight = new HashSet<>();
        actualBoth = new HashSet<>();
        actualNeither = new HashSet<>();
        expectedRight.add(new Point(0,0));
        expectedBoth.add(new Point(0,1));
        expectedNeither.add(new Point(0,2));
        expectedNeither.add(new Point(1,1));
        b2.populateDirectionPoints(actualRight, actualDown, actualBoth, actualNeither);
        assertEquals(expectedBoth, actualBoth);
        assertEquals(expectedDown, actualDown);
        assertEquals(expectedRight, actualRight);
        assertEquals(expectedNeither, actualNeither);
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