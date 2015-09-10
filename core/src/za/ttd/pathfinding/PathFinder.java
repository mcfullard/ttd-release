package za.ttd.pathfinding;

import java.util.HashMap;
import java.util.Map;

import za.ttd.characters.objects.Position;

/**
 * This class encapsulates all path generation and position finding based thereon.
 *
 * @author minnaar
 * @since 2015/09/09.
 */
public class PathFinder {
    private za.ttd.mapgen.Map map;
    private Map<Position, Node> nodes;

    public PathFinder(za.ttd.mapgen.Map map) {
        this.map = map;
    }

    private void populateNodes() {
        nodes = new HashMap<>();

    }
}
