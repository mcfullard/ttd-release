package za.ttd.level;

import za.ttd.characters.*;
import za.ttd.characters.objects.Position;
import za.ttd.renderers.CharacterRenderer;
import za.ttd.renderers.MazeRenderer;
import za.ttd.renderers.Renderable;
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
public class Level {
    private Map map;
    private long seed;
    private java.util.Map<Position, InGameObject> gameObjects;


    private int imgScale;
    private MazeRenderer mazeRenderer;
    private CharacterRenderer charRendered;

    private Player thomas;
    private ScoringSystem scoring;

    public Level() {
        this.imgScale = 64;
        this.seed = 1264;
        map = Grid.generateMap(15,5,seed);
        gameObjects = new HashMap<>();
        mazeRenderer = new MazeRenderer(map.getMap(), imgScale);
        charRendered = new CharacterRenderer(map.getMap(), imgScale);
        initGameObjects();
        scoring = new ScoringSystem();
    }

    public void render(){
        mazeRenderer.render();
        update();
        charRendered.render(getRenderables(gameObjects.values()));
    }

    private void update() {
        for(Actor actor : getActors(gameObjects.values()))
            actor.update();
        checkCollection();
    }

    private List<Actor> getActors(Collection<InGameObject> characters) {
        List<Actor> actors = new LinkedList<>();
        for(InGameObject character: characters) {
            if(character instanceof Actor)
                actors.add((Actor)character);
        }
        return actors;
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


    /*
    * Create initial values for in game objects that will be placed in the level
    * Work through map to figure out initial positions of collectibles
    * */
    private void initGameObjects() {
        //Place collectibles
        for (int r = 0; r < map.getMap().length; r++) {
            for (int c = 0; c < map.getMap()[0].length; c++) {
                if (map.isPath(c,r)) {
                    Position position = new Position(c,r);
                    //NB Change map data so that it accommodates where pac-man will be placed as well as power up's, boss and ghosts
                    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    gameObjects.put(position, new Plaque(position));
                }
            }
        }

        thomas = new Player(new Position(1, 1), .1f);
        thomas.setMovementMap(map);
        gameObjects.put(thomas.getPosition(), thomas);

    }

    private void checkCollection() {



        try {
            Position position = new Position(thomas.getPosition().getIntX(), thomas.getPosition().getIntY());

            if (gameObjects.get(position).getClass() == Plaque.class) {
                gameObjects.remove(position);
                scoring.incScoreCollectible();
            }

            if (gameObjects.get(position).getClass() == Mouthwash.class) {
                gameObjects.remove(position);

            }

        } catch (Exception e) {}

    }
}
