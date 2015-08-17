package za.ttd.level;

import za.ttd.InGameObjects.InGameObject;
import za.ttd.InGameObjects.Position;
import za.ttd.mapgen.Map;

/**
 *
 * The Level class contains references to all the characters in a level.
 *
 * @author minnaar
 * @since 2015/08/17.
 */
public class Level {
    private Map map;
    private long seed;
    private java.util.Map<Position, InGameObject> gameObjects;

}
