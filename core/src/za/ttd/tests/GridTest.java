package za.ttd.tests;

import org.junit.Test;
import za.ttd.mapgen.Block;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Shape;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/07/28.
 */
public class GridTest {

    @Test
    public void testConstructor() throws Exception {
        Grid grid = new Grid(8, 5, 123456);
    }

    @Test
    public void testSetRandomOrigin() throws Exception {
        Grid g1 = new Grid(0,0, 123456);
        Grid g2 = new Grid(0,0, 123456);
        Block b1 = new Block(0,0,Shape.L);
        Block b2 = new Block(1,1,Shape.L);
        Block b3 = new Block(0,0,Shape.L);
        assertNotEquals(b1,b2);
        assertEquals(b1,b3);
        g1.setRandomOrigin(b1);
        g2.setRandomOrigin(b2);
        assertEquals(b1,b2);
    }

    @Test
    public void testIsCollision() throws Exception {
        Grid grid = new Grid(8, 5, 123456);
        Block b1 = new Block(4, 0, Shape.I);
        Block b2 = new Block(3, 0, Shape.I);
        assertTrue(grid.isCollision(b1));
        assertFalse(grid.isCollision(b2));
        b2.rotate();
        assertTrue(grid.isCollision(b2));
    }

    @Test
    public void testPopulateGrid() throws Exception {
        int row = 8, col = 5;
        Grid g1 = new Grid(row,col,123456);
        g1.populateGrid();
        assertTrue(g1.getBlocks().size() > 1);
    }

    @Test
    public void testUseAvailable() {
        Grid g1 = new Grid(5,3, 12);
        g1.populateGrid();
        assertTrue(g1.getAvailable().size() == 0);
    }

    @Test
    public void testDrawEdges() {
        Grid g1 = new Grid(8,8,1);
        g1.populateGrid();
        g1.drawEdges();
        g1.getMap().verticalMirror();
        g1.getMap().displayMap();
    }
}