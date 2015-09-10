package za.ttd.tests;

import org.junit.Test;
import za.ttd.characters.objects.Position;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/09/10.
 */
public class PositionTest {

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
}