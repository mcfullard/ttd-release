package za.ttd.tests;

import za.ttd.mapgen.Grid;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/07/27.
 */
public class GridTest {
    @org.junit.Test
    public void testPopulateGrid() throws Exception {
        Grid grid = new Grid(8, 5, 123456);
        grid.populateGrid();
    }
}