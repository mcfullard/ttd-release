package za.ttd.tests;

import org.junit.Test;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Map;
import za.ttd.pathfinding.PathFinder;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/09/10.
 */
public class PathFinderTest {
    @Test
    public void testConstructor() throws Exception {
        Map map = Grid.generateMap(15,5,1);
        PathFinder pf = new PathFinder(map);
    }

}