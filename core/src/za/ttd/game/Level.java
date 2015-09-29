package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.TelegramProvider;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Color;
import za.ttd.characters.*;
import za.ttd.characters.objects.Direction;
import za.ttd.characters.objects.Movement;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.MessageType;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Map;
import za.ttd.pathfinding.PathFinder;
import za.ttd.renderers.CharacterRenderer;
import za.ttd.renderers.HudRenderer;
import za.ttd.renderers.MazeRenderer;
import za.ttd.renderers.Renderable;

import java.util.*;

/**
 *
 * The Level class contains references to all the characters in a level.
 *
 * @author minnaar
 * @since 2015/08/17.
 */
public class Level implements
        TelegramProvider,
        Telegraph {
    private Map map;

    private Random random;

    private java.util.Map<Position, InGameObject> gameItems;
    private ArrayList<Enemy> enemies;
    private PathFinder pathFinder;

    private MazeRenderer mazeRenderer;
    private CharacterRenderer charRenderer;
    private HudRenderer hudRenderer;

    private Player player;
    private Thomas thomas;
    private  Toothbrush benny;

    private Movement movement;
    private Controls controls;

    private final int imgScale = 32;

    public Level(Player player) {
        this.player = player;
        random = new Random();

        map = Grid.generateMap(12, 4, player.getHighestLevel());
        gameItems = new HashMap<>();
        enemies = new ArrayList<>();

        pathFinder = new PathFinder(map);
        movement = new Movement(map);

        registerSelfAsProvider();
        registerSelfAsListener();

        initGameObjects();

        float r, g, b;

        r = random.nextFloat();
        g= random.nextFloat();
        b = random.nextFloat();

        Color color = new Color(r, g, b, 1);
        color.luminanceAlpha(1.0f, 1.0f);

        mazeRenderer = new MazeRenderer(map.getMap(), imgScale, color);
        charRenderer = new CharacterRenderer(map.getMap(), imgScale);
        hudRenderer = new HudRenderer();
        controls = new Controls();
    }

    public void render(){
        mazeRenderer.render();
        hudRenderer.render(player);
        charRenderer.render(getRenderables(gameItems.values()));
        update();
    }

    private void update() {
        MessageManager.getInstance().update(Gdx.graphics.getDeltaTime());
        controls.processKeys();
        thomas.setDirection(movement.move(thomas, controls.getDirection()));
        thomas.update();

        for (Enemy enemy:enemies) {
            if (controls.keyPressed())
                movement.move(enemy, Direction.NONE);
            enemy.update();
        }
    }

    private void registerSelfAsProvider() {
        MessageManager.getInstance().addProviders(this,
                MessageType.SEND_PATHFINDER,
                MessageType.SEND_THOMAS,
                MessageType.SEND_ITEMS,
                MessageType.LEVEL_PAUSED,
                MessageType.LEVEL_STARTED,
                MessageType.SEND_TOOTHDECAY);
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListener(this,
                MessageType.LEVEL_RESET);
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
                    thomas = new Thomas(position, .07f);
                    MessageManager.getInstance().addListener(thomas,
                            MessageType.SEND_ITEMS);
                    ++c;
                }

                if (map.isType(c, r, Map.BAD_BREATH)) {
                    Enemy badBreath = new BadBreath(position, .07f, "BadBreath");
                    enemies.add(badBreath);
                }

                if (map.isType(c, r, Map.MOUTHWASH)) {
                    gameItems.put(position, new Mouthwash(position));
                }

                if (map.isType(c, r, Map.BRUSH)) {
                    position.setX(position.getX() + .5f);
                    benny = new Toothbrush(position);
                    gameItems.put(position, benny);
                    ++c;
                }
            }
        }

        for(Enemy enemy : enemies) {
            MessageManager.getInstance().addListeners(enemy,
                    MessageType.SEND_PATHFINDER,
                    MessageType.SEND_ITEMS,
                    MessageType.SEND_THOMAS,
                    MessageType.TOOTHBRUSH_COLLECTED,
                    MessageType.MOUTHWASH_COLLECTED,
                    MessageType.MOUTHWASH_EXPIRED,
                    MessageType.LEVEL_RESET
                    );
        }
    }

    private void reset() {
        controls.reset();
        thomas.revive();
        gameItems.put(benny.getPosition(), benny);

        for (Enemy enemy:enemies) {
            enemy.revive();
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
            case MessageType.SEND_ITEMS:
                if(receiver instanceof Actor) {
                    return gameItems;
                }
                break;
            case MessageType.SEND_THOMAS:
                if(receiver instanceof Enemy) {
                    return thomas;
                }
                break;
            case MessageType.SEND_PATHFINDER:
                if(receiver instanceof Enemy) {
                    return pathFinder;
                }
                break;
            case MessageType.SEND_TOOTHDECAY:
                if(receiver instanceof BadBreath) {
                    return getToothDecay();
                }
                break;
        }
        return null;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.LEVEL_RESET:
                reset();
                return true;
        }
        return false;
    }

    public ToothDecay getToothDecay() {
        for(Enemy enemy : enemies) {
            if(enemy instanceof ToothDecay)
                return (ToothDecay) enemy;
        }
        return null;
    }
}
