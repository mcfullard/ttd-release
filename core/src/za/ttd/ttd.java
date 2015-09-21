package za.ttd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Player;
import za.ttd.level.Level;
import za.ttd.screens.GameScreen;
import za.ttd.screens.LoadingScreen;
import za.ttd.screens.MainMenu;
import za.ttd.screens.SplashScreen;

public class ttd extends Game
        implements Telegraph
{
    private Level level;
    private Player player;

	public static final String TITLE = "The Wrath of Thomas the Dentist";
	public static final int WIDTH = 600, HEIGHT = 800;

    public ttd() {
        registerSelfAsListener();
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListeners(this,
                MessageType.THOMAS_DEAD,
                MessageType.TOOTHDECAY_DEAD,
                MessageType.LEVEL_LOADED
        );
    }

	@Override
	public void create() {
		setScreen(new SplashScreen(this));
	}

    public void newGame(String name) {
        setScreen(new LoadingScreen(this));
        player = new Player(name, 0, 1, 2);
        level = new Level(player);
        level.render();
    }

    public void continueGame(String name) {
        //Run method to find the users game data so they can continue from where they left off
        player = new Player(name, 0, 1, 2);
        level = new Level(player);
        setScreen(new GameScreen(this));
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.THOMAS_DEAD:
                setScreen(new MainMenu(this));
                return true;
            case MessageType.TOOTHDECAY_DEAD:
                player.incHighestLevel();
                level = new Level(player);
                setScreen(new GameScreen(this));
                return true;
            case MessageType.LEVEL_LOADED:
                setScreen(new GameScreen(this));
                return true;
        }
        return false;
    }
}
