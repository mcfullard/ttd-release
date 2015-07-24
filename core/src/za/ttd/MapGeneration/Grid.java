package za.ttd.MapGeneration;

/**
 * @author minnaar
 * @since 2015/07/21.
 *
 * This is the base class for randomized map generation
 *
 */
public class Grid {
    Block[][] cells;

    public Grid(int rows, int cols) {
        cells = new Block[rows][cols];
    }
}
