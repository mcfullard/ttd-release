package za.ttd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Gamer;
import za.ttd.gameInterfaces.EndLevelListener;
import za.ttd.gameInterfaces.LevelLoadingListener;
import za.ttd.level.Level;
import za.ttd.screens.GameScreen;
import za.ttd.screens.LoadingScreen;
import za.ttd.screens.MainMenu;
import za.ttd.screens.SplashScreen;

public class ttd extends Game
        implements EndLevelListener, LevelLoadingListener, Telegraph
{
    private Level level;
    private Gamer gamer;
    private boolean loaded;

	public static final String TITLE = "The Wrath of Thomas the Dentist";
	public static final int WIDTH = 600, HEIGHT = 800;

	@Override
	public void create() {
		setScreen(new SplashScreen(this));
        loaded = false;
	}

    public void newGame(String name) {
        setScreen(new LoadingScreen(this));
        gamer = new Gamer(name, 0, 1, 2);
        level = new Level(this, gamer);
        MessageManager.getInstance().addProviders(level);
        level.render();
    }

    public void continueGame(String name) {
        //Run method to find the users game data so they can continue from where they left off
        gamer = new Gamer(name, 0, 1, 2);
        level = new Level(this, gamer);
        setScreen(new GameScreen(this));
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void LevelLoadingListener(boolean loaded) {
            if (loaded)
                setScreen(new GameScreen(this));
    }

    @Override
    public void EndLevelListener(boolean levelPassed) {
        if (levelPassed) {
            gamer.incHighestLevel();
            gamer.setTotScore(level.getTotLevelScore());
            level = new Level(this, gamer);
            setScreen(new GameScreen(this));
        }
        else {
            //level = new Level(gamer.getHighestLevel(), gamer.getTotScore(), this, gamer.getLives());

            //add stats to db//
            //show game OverScreen//
            setScreen(new MainMenu(this));
        }
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.THOMAS_DEAD:
                break;
            case MessageType.LEVEL_LOADING:
                break;
        }
        return false;
    }
}
