package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import za.ttd.characters.states.MessageType;
import za.ttd.screens.*;

public class Game extends com.badlogic.gdx.Game
        implements Telegraph
{
    private Level level;
    private Player player;
    private Assets assets;
    private boolean playerLoaded;

    private MainMenuScreen mainMenuScreen;

    private Json json = new Json();
	public static final String TITLE = "The Wrath of Thomas the Dentist";
	public static final int WIDTH = 600, HEIGHT = 800;

    public Game() {
        registerSelfAsListener();
    }

	@Override
	public void create() {
        mainMenuScreen = new MainMenuScreen(this, playerLoaded);
		setScreen(new SplashScreen(this));
        assets = Assets.getInstance();
        assets.Load();

	}

    public void newGame() {
        player.setLives(3);
        player.setHighestLevel(1);
        createGame();
    }

    //Creates a new game depending on the players level
    public void createGame() {
        setLevel(new Level(player));
        setScreen(new GameScreen(this));
    }

    public Level getLevel() {
        return level;
    }

    private void gameOver() {
        player.setLives(3);
        player.setHighestLevel(1);
        setScreen(new MainMenuScreen(this, false));
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListeners(this,
                MessageType.GAME_OVER,
                MessageType.NEXT_LEVEL
        );
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.GAME_OVER:
                gameOver();
                return true;
            case MessageType.NEXT_LEVEL:
                player.incHighestLevel();
                createGame();
                return true;
        }
        return false;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player loadPlayer(String playerName) {
        Json json = new Json();
        Player object = null;
        FileHandle fin = Gdx.files.internal(String.format(
                "core/assets/data/%s.player",
                playerName
        ));
        try {
            String data = fin.readString();
            object = json.fromJson(Player.class, data);
        } catch (GdxRuntimeException e) {
            Gdx.app.log("GAME", e.getMessage());
        }
        return object;
    }

    public void savePlayer() {
        FileHandle fout = Gdx.files.internal(String.format(
                "core/assets/data/%s.player",
                player.getName()
        ));
        Json json = new Json();
        String data = json.prettyPrint(player);
        fout.writeString(data, false);
    }

    public int getPlayerID() {
        return this.player.getPlayerID();
    }
}
