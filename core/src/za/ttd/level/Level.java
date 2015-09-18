package za.ttd.level;

import com.badlogic.gdx.utils.TimeUtils;
import za.ttd.Interfaces.EndLevelListener;
import za.ttd.Interfaces.LevelLoadingListener;
import za.ttd.characters.*;
import za.ttd.characters.objects.Direction;
import za.ttd.characters.objects.Movement;
import za.ttd.characters.objects.Position;
import za.ttd.game.Controls;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Map;
import za.ttd.pathfinding.PathFinder;
import za.ttd.renderers.CharacterRenderer;
import za.ttd.renderers.HudRenderer;
import za.ttd.renderers.MazeRenderer;
import za.ttd.renderers.Renderable;
import za.ttd.ttd;

import java.util.*;

/**
 *
 * The Level class contains references to all the characters in a level.
 *
 * @author minnaar
 * @since 2015/08/17.
 */
public class Level {
    private Map map;

    private EndLevelListener endLevelListener;
    private LevelLoadingListener levelLoadingListener;

    private java.util.Map<Position, InGameObject> gameItems;
    private ArrayList<Enemy> enemies;
    private PathFinder pathFinder;
    private int imgScale;
    private MazeRenderer mazeRenderer;
    private CharacterRenderer charRenderer;

    private Player thomas;
    private ScoringSystem scoring;
    private HudRenderer hudRenderer;
    private final long powerTime = 15;
    private long startPowerTime;
    private int lives;

    private Movement movement;

    private Controls controls;

    private ttd game;

    public Level(long seed, int totScore, ttd game, int lives) {
        this.game = game;
        levelLoadingListener = game;
        this.imgScale = 32;
        map = Grid.generateMap(12,4,seed);
        gameItems = new HashMap<>();
        enemies = new ArrayList<>();
        mazeRenderer = new MazeRenderer(map.getMap(), imgScale);
        charRenderer = new CharacterRenderer(map.getMap(), imgScale);

        movement = new Movement(map);
        pathFinder = new PathFinder(map);
        initGameObjects();
        this.lives = lives;

        scoring = new ScoringSystem(totScore);
        hudRenderer = new HudRenderer();
        controls = new Controls();
        endLevelListener = game;
    }

    private void pause() {

    }

    public int getLives() {
        return lives;
    }

    public void render(){
        mazeRenderer.render();
        hudRenderer.render(scoring.getLvlScore(), scoring.getElapsedTime(), game.getLevelNumber());
        charRenderer.render(getRenderables(gameItems.values()));
        levelLoadingListener.LevelLoadingListener(true);
        update();
    }

    private void update() {
        controls.update();
        thomas.setDirection(movement.Move(thomas.getPosition(), thomas.getMovementSpeed(), thomas.getDirection(), controls.getDirection()));
        thomas.update();

        for (Enemy enemy:enemies) {
            if (controls.keyPressed() && enemy instanceof Enemy) {
                enemy.setDirection(pathFinder.shortestPathTo(enemy.getPosition(), thomas.getPosition()));
                movement.Move(enemy.getPosition(), enemy.getMovementSpeed(), enemy.getDirection(), Direction.NONE);
            }
            enemy.update();
        }

        checkVulnerability();
        checkCollisions();
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
                    enemies.add(new ToothDecay(position, .07f));
                    ++c;
                }
                else if (map.isType(c, r, Map.PATH))
                    gameItems.put(position, new Plaque(position));

                if (map.isType(c, r, Map.THOMAS)) {
                    position.setX(position.getX() + .5f);
                    thomas = new Player(position, .07f);
                    ++c;
                }
                if (map.isType(c, r, map.BAD_BREATH)) {
                    enemies.add(new BadBreath(position, .07f, "BadBreath"));
                }
                if (map.isType(c, r, map.MOUTHWASH)) {
                        gameItems.put(position, new Mouthwash(position));
                }
                if (map.isType(c, r, map.TOOTH_DECAY)) {
                    position.setX(position.getX()+.5f);
                    enemies.add(new ToothDecay(position, .07f));
                    ++c;
                    break;
                }
                if (map.isType(c, r, map.BRUSH)) {
                    position.setX(position.getX() + .5f);
                    gameItems.put(position, new Toothbrush(position));
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

        if (gameItems.get(position) instanceof Plaque) {
            gameItems.remove(position);
            scoring.collectibleFound();
        }

        if (gameItems.get(position) instanceof Mouthwash) {
            gameItems.remove(position);
            scoring.powerUsed();
            powerUp();
        }

        if (gameItems.get(position) instanceof Toothbrush){
            gameItems.remove(position);
            toothBrushPower();
        }

        for (Enemy enemy:enemies) {

            /* Check whether enemies are walking over plaque, if so increase there speed
            * else set there speed to the normal speed*/
            if (gameItems.get(enemy.getPosition()) instanceof Plaque)
                enemy.speedUp();
            else
                enemy.normalSpeed();

            if (enemy.collided(thomas.getPosition())) {
                if (enemy.getKillable()) {
                    enemy.kill();
                    if (enemy instanceof BadBreath)
                        scoring.killedBadBreath();
                    else {
                        scoring.killedToothDecay();
                        endLevelListener.EndGameListener(true);
                    }
                }
                else {
                    if (scoring.getTotLivesUsed() < lives) {
                        scoring.lifeUsed();
                        reset();
                    }
                    else {
                        endLevelListener.EndGameListener(false);
                    }
                }
            }
        }
    }

    /*
    * Thomas has picked up a minty mouthwash item
    * Hinder the applicable characters*/
    private void toothBrushPower() {
        for(Enemy enemy:enemies) {
            if (enemy instanceof ToothDecay)
                enemy.setKillable(true);
            enemy.setVulnerable(true);
        }
    }

    /*
    * Thomas has picked up a minty mouthwash item
    * Hinder the applicable characters*/
    private void powerUp() {
        startPowerTime = TimeUtils.millis();

        for(Enemy enemy : enemies) {
            if (enemy instanceof BadBreath) {
                enemy.setVulnerable(true);
                enemy.setKillable(true);
            }
            else
                enemy.setVulnerable(true);
        }
    }

    /*
    * Time is up for the power used therefore change enemies back to original states*/
    private void powerDown() {
        for(Enemy enemy:enemies) {
            if (enemy instanceof BadBreath) {
                enemy.setVulnerable(false);
                enemy.setKillable(false);
            }
            else
                enemy.setVulnerable(false);
        }
    }

    private void checkVulnerability() {
        if (startPowerTime == 0)
            return;

        long elapsedTime = TimeUtils.timeSinceMillis(startPowerTime)/1000;

        if (elapsedTime >= powerTime) {
            powerDown();
            startPowerTime = 0;
        }
    }

    private void reset() {
        thomas.reset();
        controls.reset();

        for (Enemy enemy:enemies) {
            enemy.reset();
        }
    }

    public int getTotLevelScore() {
        return scoring.getLvlTotScore();
    }

    private List<Collectible> getItems(Collection<InGameObject> items) {
        List<Collectible> collectibles = new LinkedList<>();
        for(InGameObject item: items) {
            if(item instanceof Collectible)
                collectibles.add((Collectible)item);
        }
        return collectibles;
    }

    private List<Renderable> getRenderables(Collection<InGameObject> characters) {
        List<Renderable> renderables = new LinkedList<>();
        for(InGameObject character : characters) {
            if(character instanceof Renderable) {
                renderables.add((Renderable)character);
            }
        }

        for (Enemy enemy:enemies)
            renderables.add(enemy);

        renderables.add(thomas);

        return renderables;
    }
}
