package za.ttd.level;

import za.ttd.InGameObjects.InGameObject;
import za.ttd.InGameObjects.Position;
import za.ttd.MapDraw.MazeRenderer;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Map;

/**
 *
 * The Level class contains references to all the characters in a level.
 *
 * @author minnaar
 * @since 2015/08/17.
 */
public class Level{
    private Map map;
    private long seed;
    private java.util.Map<Position, InGameObject> gameObjects;

    int imgScale;
    private MazeRenderer mazeRenderer;

    public Level() {
        this.imgScale = 64;
        this.seed = 3;
        mazeRenderer = new MazeRenderer(Grid.generateMap(15,5,seed), imgScale);
    }

    public void render() {
        mazeRenderer.render();
    }


}
