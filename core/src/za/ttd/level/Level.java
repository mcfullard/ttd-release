package za.ttd.level;

import za.ttd.characters.*;
import za.ttd.characters.objects.Movement;
import za.ttd.characters.objects.Position;
import za.ttd.game.Controls;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Map;
import za.ttd.renderers.CharacterRenderer;
import za.ttd.renderers.HudRenderer;
import za.ttd.renderers.MazeRenderer;
import za.ttd.renderers.Renderable;

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
    private HudRenderer hudRenderer;

    private Movement movement;

    private Controls controls;

    public Level() {
        this.imgScale = 32;
        this.seed = 1264;
        map = Grid.generateMap(12,4,seed);
        gameObjects = new HashMap<>();
        mazeRenderer = new MazeRenderer(map.getMap(), imgScale);
        charRendered = new CharacterRenderer(map.getMap(), imgScale);

        movement = new Movement(map);
        initGameObjects();

        scoring = new ScoringSystem(0, 3);
        hudRenderer = new HudRenderer();

        controls = new Controls();
    }

    public void render(){
        mazeRenderer.render();
        hudRenderer.render(scoring.getLvlScore(), scoring.getElapsedTime());
        update();
        charRendered.render(getRenderables(gameObjects.values()));
    }

    private void update() {
        controls.update();

        thomas.setDirection(movement.Move(thomas.getPosition(), thomas.getMovementSpeed(), thomas.getDirection(), controls.getDirection()));

        for (Actor actor : getActors(gameObjects.values())) {
            actor.update();
        }

        checkCollisions();
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
                Position position = new Position(c, r);

                if (r == 1 && c == (map.getMap()[0].length / 2)-1) {
                    position.setX(position.getX() + .5f);
                    gameObjects.put(position, new ToothDecay(position, .1f));
                    ++c;
                }
                else if (map.isType(c, r, Map.PATH))
                    gameObjects.put(position, new Plaque(position));
                if (map.isType(c, r, Map.THOMAS)) {
                    position.setX(position.getX() + .5f);
                    thomas = new Player(position, .1f);
                    gameObjects.put(position, thomas);
                    ++c;
                }
                if (map.isType(c, r, map.BAD_BREATH)) {
                    gameObjects.put(position, new BadBreath(position, .1f, "BadBreath"));
                }
                if (map.isType(c, r, map.MOUTHWASH)) {
                        gameObjects.put(position, new Mouthwash(position));
                }
                if (map.isType(c, r, map.TOOTH_DECAY)) {
                    position.setX(position.getX()+.5f);
                    gameObjects.put(position, new ToothDecay(position, .1f));
                    ++c;
                    break;
                }
                if (map.isType(c, r, map.BRUSH)) {
                    position.setX(position.getX() + .5f);
                    gameObjects.put(position, new Toothbrush(position));
                    ++c;
                }
            }
        }
    }

    private void checkCollisions() {

        Position position = new Position(thomas.getIntX(), thomas.getIntY());

        if (gameObjects.get(position) instanceof Plaque) {
            gameObjects.remove(position);
            scoring.collectibleFound();
        }

        if (gameObjects.get(position) instanceof Mouthwash) {
            gameObjects.remove(position);
            scoring.powerUsed();
            powerUp();
        }

        if (gameObjects.get(position) instanceof BadBreath) {
            BadBreath badBreath = (BadBreath)gameObjects.get(position);

            if (badBreath.getVulnerability()) {
                gameObjects.remove(position);
                scoring.killedPlaque();
            }
            else if(scoring.getLives() > 1) {
                scoring.lifeUsed();
                thomas.resetPositions();
                List<BadBreath> badBreaths = getBadBreath(gameObjects.values());
                for (BadBreath bad : badBreaths) {
                    bad.resetPositions();
                }
            }
            else {
                //Thomas is dead and the level needs to be redone
            }
        }

        if (gameObjects.get(position) instanceof Toothbrush) {
            ToothDecay toothDecay = (ToothDecay)gameObjects.get(position);

            if (toothDecay.getVulnerability()) {
                gameObjects.remove(position);
            }
        }
    }


    private void powerUp() {
        for(BadBreath badBreath : getBadBreath(gameObjects.values()))
            badBreath.setVulnerable(true);

        ToothDecay toothDecay = getToothDecay(gameObjects.values());
        toothDecay.slow();

    }

    private List<BadBreath> getBadBreath(Collection<InGameObject> characters) {
        List<BadBreath> badBreath = new LinkedList<>();
        for(InGameObject character: characters) {
            if(character instanceof BadBreath)
                badBreath.add((BadBreath)character);
        }
        return badBreath;
    }

    private ToothDecay getToothDecay(Collection<InGameObject> characters) {
        for(InGameObject character:characters)
            if (character instanceof ToothDecay)
                return (ToothDecay)character;

        return null;
    }
}
