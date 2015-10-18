package za.ttd.tests;

import org.junit.Test;
import za.ttd.characters.objects.Direction;
import za.ttd.characters.objects.Position;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/09/10.
 */
public class PositionTest {

    @Test
    public void testGetDirection() throws Exception {
        Position p1 = new Position(1,1);
        Position p2 = new Position(1,2);
        Position p3 = new Position(1.3f, 2);
        Position p4 = new Position(4,2);
        assertEquals(Direction.DOWN, p1.getDirectionTo(p2));
        assertEquals(Direction.UP, p2.getDirectionTo(p1));
        assertEquals(Direction.RIGHT, p2.getDirectionTo(p4));
        assertEquals(Direction.LEFT, p4.getDirectionTo(p2));
        assertEquals(Direction.RIGHT, p2.getDirectionTo(p3));
        assertEquals(Direction.LEFT, p3.getDirectionTo(p2));
    }

    @Test
    public void testGetIntX() throws Exception {

    }

    @Test
    public void testGetIntY() throws Exception {

    }

    @Test
    public void testGetFloorX() throws Exception {

    }

    @Test
    public void testGetChangedFloorX() throws Exception {

    }

    @Test
    public void testGetFloorY() throws Exception {

    }

    @Test
    public void testGetChangedFloorY() throws Exception {

    }

    @Test
    public void testGetCeilX() throws Exception {

    }

    @Test
    public void testGetChangedCeilX() throws Exception {

    }

    @Test
    public void testGetCeilY() throws Exception {

    }

    @Test
    public void testGetChangedCeilY() throws Exception {

    }

    @Test
    public void testIncreaseX() throws Exception {

    }

    @Test
    public void testIncreaseY() throws Exception {

    }

    @Test
    public void testClone() throws Exception {
        Position p1 = new Position(1, 2);
        Position p2 = p1.clone();
        assertEquals(p1, p2);
    }

    @Test
    public void testEquals() throws Exception {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        Position p3 = new Position(1, 1);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p2, p3);
    }

    @Test
    public void testHashCode() throws Exception {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        Position p3 = new Position(2, 2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    public void testGetDistanceTo() throws Exception {
        Position p1 = new Position(1,1);
        Position p2 = new Position(1,5);
        Position p3 = new Position(5,5);
        assertEquals(4f, p1.getDistanceTo(p2), 0.001);
        assertEquals(4f, p2.getDistanceTo(p1), 0.001);
        assertEquals(Math.sqrt(32), p1.getDistanceTo(p3), 0.001);
        assertEquals(Math.sqrt(32), p3.getDistanceTo(p1), 0.001);
    }
}