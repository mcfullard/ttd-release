package za.ttd.level;

import com.badlogic.gdx.utils.TimeUtils;
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
    private final long powerTime = 30;
    private long startPowerTime;

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

        scoring = new ScoringSystem(0);
        hudRenderer = new HudRenderer();

        controls = new Controls();
    }

    public void render(){
        mazeRenderer.render();
        update();
        hudRenderer.render(scoring.getLvlScore(), scoring.getElapsedTime());
        charRendered.render(getRenderables(gameObjects.values()));
    }

    private void update() {
        controls.update();

        thomas.setDirection(movement.Move(thomas.getPosition(), thomas.getMovementSpeed(), thomas.getDirection(), controls.getDirection()));

        for (Actor actor : getActors(gameObjects.values())) {
            actor.update();
        }
        checkVulnerability();
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
                    gameObjects.put(position, new ToothDecay(position, .07f));
                    ++c;
                }
                else if (map.isType(c, r, Map.PATH))
                    gameObjects.put(position, new Plaque(position));
                if (map.isType(c, r, Map.THOMAS)) {
                    position.setX(position.getX() + .5f);
                    thomas = new Player(position, .07f);
                    gameObjects.put(position, thomas);
                    ++c;
                }
                if (map.isType(c, r, map.BAD_BREATH)) {
                    gameObjects.put(position, new BadBreath(position, .07f, "BadBreath"));
                }
                if (map.isType(c, r, map.MOUTHWASH)) {
                        gameObjects.put(position, new Mouthwash(position));
                }
                if (map.isType(c, r, map.TOOTH_DECAY)) {
                    position.setX(position.getX()+.5f);
                    gameObjects.put(position, new ToothDecay(position, .07f));
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

    /*
    * Check Thomas' collisions with the rest of the game objects
    * Depending on who or what he collides with, do the related methods*/
    private void checkCollisions() {

        Position position = thomas.getPosition();

        if (gameObjects.get(position) instanceof Plaque) {
            gameObjects.remove(position);
            scoring.collectibleFound();
        }

        if (gameObjects.get(position) instanceof Mouthwash) {
            gameObjects.remove(position);
            scoring.powerUsed();
            powerUp();
        }

        if (gameObjects.get(position) instanceof Toothbrush){
            gameObjects.remove(position);
            toothBrushPower();
        }

        if (gameObjects.get(position) instanceof BadBreath) {
            BadBreath badBreath = (BadBreath)gameObjects.get(position);

            if (badBreath.getVulnerability()) {
                gameObjects.remove(position);
                scoring.killedBadBreath();
            }
            else if(scoring.getTotLivesUsed() <= 3) {
                scoring.lifeUsed();
                reset();
            }
            else {
                //Thomas is dead and the level needs to be redone
                //need to do this in the game loop
            }
        }

        if (gameObjects.get(position) instanceof ToothDecay) {
            ToothDecay toothDecay = (ToothDecay)gameObjects.get(position);

            if (toothDecay.getVulnerability()) {
                gameObjects.remove(position);
                //scoring.killedToothDecay();
            }
            else if(scoring.getTotLivesUsed() <= 3) {
                scoring.lifeUsed();
                reset();
            }
            else {
                //Thomas is dead and the level needs to be redone
                //need to do this in the game loop
            }
        }

        /*
        * Check whether enemies are walking over plaque, if so increase there speed
        * else set there speed to the normal speed*/
        List<BadBreath> badBreaths = getBadBreath(gameObjects.values());
        for (BadBreath bad : badBreaths) {
            if (gameObjects.get(bad.getPosition()) instanceof Plaque) {
                bad.speedUp();
            }
            else
                bad.normalSpeed();
        }

        ToothDecay toothDecay = getToothDecay(gameObjects.values());
        if (gameObjects.get(toothDecay.getPosition()) instanceof Plaque)
            toothDecay.speedUp();
        else
            toothDecay.normalSpeed();
    }

    /*
    * Thomas has picked up a minty mouthwash item
    * Hinder the applicable characters*/
    private void toothBrushPower() {
        for(BadBreath badBreath : getBadBreath(gameObjects.values())) {
            badBreath.slow();
            //Change their states so that they protect tooth-decay and try to kill thomas
            //Do this by making two find the shortest path to tooth decay and circle that area
            //and the other two work on killing thomas
        }

        ToothDecay toothDecay = getToothDecay(gameObjects.values());
        toothDecay.setVulnerable(true);
    }

    /*
    * Thomas has picked up a minty mouthwash item
    * Hinder the applicable characters*/
    private void powerUp() {

        startPowerTime = TimeUtils.millis();

        for(BadBreath badBreath : getBadBreath(gameObjects.values()))
            badBreath.setVulnerable(true);

        ToothDecay toothDecay = getToothDecay(gameObjects.values());
        toothDecay.slow();

    }

    /*
    * Time is up for the power used therefore change enemies back to original states*/
    private void powerDown() {
        for(BadBreath badBreath : getBadBreath(gameObjects.values()))
            badBreath.setVulnerable(false);

        ToothDecay toothDecay = getToothDecay(gameObjects.values());
        toothDecay.normalSpeed();
    }

    private void checkVulnerability() {

        long elapsedTime = TimeUtils.timeSinceMillis(startPowerTime);

        if (elapsedTime >= powerTime) {
            powerDown();
            startPowerTime = 0;
        }

    }

    private void reset() {
        thomas.reset();
        controls.reset();

        List<BadBreath> badBreaths = getBadBreath(gameObjects.values());
        for (BadBreath bad : badBreaths) {
            bad.reset();
        }

        ToothDecay toothDecay = getToothDecay(gameObjects.values());
        toothDecay.reset();
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
