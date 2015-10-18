package za.ttd.tests;

import org.junit.Test;
import za.ttd.mapgen.Map;
import za.ttd.mapgen.Point;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author minnaar
 * @since 2015/07/30.
 */
public class MapTest {

    @Test
    public void testDrawRightEdge() throws Exception {
        Map map = new Map(2, 2, 2);
        Point p1 = new Point(0, 0);
        map.drawRightEdge(p1);
        assertArrayEquals(
                new int[][]{
                        new int[]{Map.WALL, Map.PATH, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.PATH, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.WALL},
                },
                map.getMap()
        );
    }

    @Test
    public void testDrawBottomEdge() throws Exception {
        Map map = new Map(2, 2, 2);
        Point p1 = new Point(0, 1);
        map.drawBottomEdge(p1);
        assertArrayEquals(
                new int[][]{
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.WALL, Map.PATH, Map.PATH},
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.WALL},
                },
                map.getMap()
        );
    }

    @Test
    public void testDrawLeftEdge() throws Exception {
        Map map = new Map(2, 2, 2);
        Point p1 = new Point(1, 1);
        map.drawLeftEdge(p1);
        assertArrayEquals(
                new int[][]{
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.WALL, Map.PATH, Map.WALL},
                        new int[]{Map.WALL, Map.WALL, Map.PATH, Map.WALL},
                },
                map.getMap()
        );
    }

    @Test
    public void testDrawRightBottomCell() throws Exception {
        Map map = new Map(2, 2, 2);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(0,0);
        map.drawRightBottomCell(p1);
        map.drawRightBottomCell(p2);
        assertArrayEquals(
                new int[][]{
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.PATH, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.WALL},
                        new int[]{Map.WALL, Map.WALL, Map.WALL, Map.PATH},
                },
                map.getMap()
        );
    }
}