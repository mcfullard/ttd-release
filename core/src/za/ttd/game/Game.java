package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import za.ttd.characters.states.MessageType;
import za.ttd.screens.GameScreen;
import za.ttd.screens.MainMenu;
import za.ttd.screens.SplashScreen;

public class Game extends com.badlogic.gdx.Game
        implements Telegraph
{
    private Level level;
    private Player player;
    private Assets assets;

    private Json json = new Json();
	public static final String TITLE = "The Wrath of Thomas the Dentist";
	public static final int WIDTH = 600, HEIGHT = 800;

    public Game() {
        registerSelfAsListener();
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListeners(this,
                MessageType.THOMAS_DEAD,
                MessageType.TOOTHDECAY_DEAD,
                MessageType.LEVEL_LOADED,
                MessageType.LOAD_LEVEL
        );
    }

	@Override
	public void create() {
		setScreen(new SplashScreen(this));
        assets = Assets.getInstance();
        assets.Load();
	}

    public void newGame() {
        setLevel(new Level(player));
        while(assets.loading() || level.loading()) {

        }
        setScreen(new GameScreen(this));
    }

    private void createGame() {

    }

    public void continueGame(String name) {
        //Run method to find the users game data so they can continue from where they left off
        setLevel(new Level(player));
        setScreen(new GameScreen(this));
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.THOMAS_DEAD:
                setScreen(new MainMenu(this, false));
                return true;
            case MessageType.TOOTHDECAY_DEAD:
                player.incHighestLevel();
                level = new Level(player);
                setScreen(new GameScreen(this));
                return true;
            case MessageType.LEVEL_LOADED:
                setScreen(new GameScreen(this));
                return true;
            case MessageType.LOAD_LEVEL:
                newGame();
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
        return this.player.getID();
    }
}
