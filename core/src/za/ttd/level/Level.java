package za.ttd.level;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.TelegramProvider;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.utils.TimeUtils;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Gamer;
import za.ttd.gameInterfaces.EndLevelListener;
import za.ttd.gameInterfaces.LevelLoadingListener;
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
public class Level implements TelegramProvider {
    private Map map;

    private EndLevelListener endLevelListener;
    private LevelLoadingListener levelLoadingListener;

    private java.util.Map<Position, InGameObject> gameItems;
    private ArrayList<Enemy> enemies;
    private PathFinder pathFinder;
    private int imgScale;
    private MazeRenderer mazeRenderer;
    private CharacterRenderer charRenderer;
    private Gamer gamer;
    private Player thomas;
    private HudRenderer hudRenderer;
    private final long powerTime = 15;
    private long startPowerTime;

    private Movement movement;
    private Controls controls;
    private ttd game;

    public Level(ttd game, Gamer gamer) {
        this.game = game;
        this.gamer = gamer;

        levelLoadingListener = game;
        this.imgScale = 32;
        map = Grid.generateMap(12, 4, gamer.getHighestLevel());
        gameItems = new HashMap<>();
        enemies = new ArrayList<>();
        mazeRenderer = new MazeRenderer(map.getMap(), imgScale);
        charRenderer = new CharacterRenderer(map.getMap(), imgScale);

        movement = new Movement(map);
        pathFinder = new PathFinder(map);
        initGameObjects();

        hudRenderer = new HudRenderer();
        controls = new Controls();
        endLevelListener = game;
    }

    public void render(){
        mazeRenderer.render();
        hudRenderer.render(gamer);
        charRenderer.render(getRenderables(gameItems.values()));
        levelLoadingListener.LevelLoadingListener(true);
        update();
    }

    private void update() {
        controls.processKeys();
        thomas.setDirection(movement.move(thomas, controls.getDirection()));
        thomas.update();

        for (Enemy enemy:enemies) {
            if (controls.keyPressed()) {
                enemy.setDirection(pathFinder.shortestPathTo(enemy.getPosition(), thomas.getPosition()));
                movement.move(enemy, Direction.NONE);
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
                if (map.isType(c, r, Map.BAD_BREATH)) {
                    Enemy badBreath = new BadBreath(position, .07f, "BadBreath");
                    MessageManager.getInstance().addListener(badBreath,
                            MessageType.BROADCAST_ENEMIES);
                    enemies.add(badBreath);
                }
                if (map.isType(c, r, Map.MOUTHWASH)) {
                    gameItems.put(position, new Mouthwash(position));
                }
                if (map.isType(c, r, Map.TOOTH_DECAY)) {
                    position.setX(position.getX()+.5f);
                    enemies.add(new ToothDecay(position, .07f));
                    ++c;
                    break;
                }
                if (map.isType(c, r, Map.BRUSH)) {
                    position.setX(position.getX() + .5f);
                    gameItems.put(position, new Toothbrush(position));
                    ++c;
                }
            }
        }

        for(Enemy enemy : enemies) {
            MessageManager.getInstance().addListeners(enemy,
                    MessageType.BROADCAST_ITEMS,
                    MessageType.BROADCAST_THOMAS,
                    MessageType.TOOTHBRUSH_COLLECTED,
                    MessageType.MOUTHWASH_COLLECTED,
                    MessageType.MOUTHWASH_EXPIRED
                    );
        }
    }

    /*
    * Check Thomas' collisions with the rest of the game objects
    * Depending on who or what he collides with, do the related methods*/
    private void checkCollisions() {
        Position thomPos = thomas.getPosition();

        if (gameItems.get(thomPos) instanceof Plaque) {
            gameItems.remove(thomPos);
            gamer.scoring.collectibleFound();
        }

        if (gameItems.get(thomPos) instanceof Mouthwash) {
            gameItems.remove(thomPos);
            gamer.scoring.powerUsed();
            powerUp(true);
        }

        if (gameItems.get(thomPos) instanceof Toothbrush){
            gameItems.remove(thomPos);
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
                        gamer.scoring.killedBadBreath();
                    else {
                        gamer.scoring.killedToothDecay();
                        endLevelListener.EndLevelListener(true);
                    }
                }
                else {
                    if (gamer.getLives() > 0 ) {
                        gamer.scoring.lifeUsed();
                        reset();
                    }
                    else {
                        endLevelListener.EndLevelListener(false);
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
    private void powerUp(boolean powered) {
        if (powered) {
            startPowerTime = TimeUtils.millis();
        }

        for (Enemy enemy : enemies) {
            if (enemy instanceof BadBreath) {
                enemy.setVulnerable(powered);
                enemy.setKillable(powered);
            } else
                enemy.setVulnerable(powered);
        }
    }

    private void checkVulnerability() {
        if (startPowerTime == 0)
            return;

        long elapsedTime = TimeUtils.timeSinceMillis(startPowerTime)/1000;

        if (elapsedTime >= powerTime) {
            powerUp(false);
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

    @Override
    public Object provideMessageInfo(int msg, Telegraph receiver) {
        switch (msg) {
            case MessageType.BROADCAST_ITEMS:
                if(receiver instanceof Actor) {
                    return gameItems;
                }
                break;
            case MessageType.BROADCAST_THOMAS:
                if(receiver instanceof Enemy) {
                    return thomas;
                }
        }
        return null;
    }
}
