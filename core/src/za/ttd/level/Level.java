package za.ttd.level;

import za.ttd.InGameObjects.Actor;
import za.ttd.InGameObjects.InGameObject;
import za.ttd.InGameObjects.Player;
import za.ttd.InGameObjects.Position;
import za.ttd.Renderers.CharacterRenderer;
import za.ttd.Renderers.MazeRenderer;
import za.ttd.Renderers.Renderable;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Map;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * The Level class contains references to all the characters in a level.
 *
 * @author minnaar
 * @since 2015/08/17.
 */
public class Level implements Actor.TryUpdateListener {
    private Map map;
    private long seed;
    private java.util.Map<Position, InGameObject> gameObjects;

    private int imgScale;
    private MazeRenderer mazeRenderer;
    private CharacterRenderer charRendered;

    public Level() {
        this.imgScale = 64;
        this.seed = 666;
        map = Grid.generateMap(15,5,seed);
        gameObjects = new HashMap<>();
        mazeRenderer = new MazeRenderer(map.getMap(), imgScale);
        charRendered = new CharacterRenderer(map.getMap(), imgScale);
        initGameObjects();
    }

    public void render(){
        mazeRenderer.render();
        charRendered.render(getRenderables(gameObjects.values()));
    }

    private List<Renderable> getRenderables(Collection<InGameObject> characters) {
        List<Renderable> renderables = new LinkedList<>();
        for(InGameObject character : characters) {
            if(character instanceof Renderable) {
                renderables.add((Renderable)character);
            }
        }
        return renderables;
    }

    /**
     * This should be replaced by a reading procedure where initial data is read from a json file or something
     */
    private void initGameObjects() {
        Player thomas = new Player(new Position(19,(float)10.5), this, 1);
        gameObjects.put(thomas.getPosition(), thomas);
    }

    @Override
    public boolean tryUpdate(Position before, Position after) {
        return true;
    }
}
