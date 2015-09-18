package za.ttd;

import com.badlogic.gdx.Game;
import za.ttd.game.Gamer;
import za.ttd.level.Level;
import za.ttd.screens.GameScreen;
import za.ttd.screens.MainMenu;
import za.ttd.screens.SplashScreen;

public class ttd extends Game implements Level.endGameListener {
    private Level level;
    private Gamer gamer;

	public static final String TITLE = "The Wrath of Thomas the Dentist";
	public static final int WIDTH = 600, HEIGHT = 800;

	@Override
	public void create() {
		setScreen(new SplashScreen(this));
	}

    public void newGame(String name) {
        gamer = new Gamer(name, 0, 1, 3);
        level = new Level(0, 1, this, 3);
        setScreen(new GameScreen(this));
    }

    public void continueGame(String name) {
        //Run method to find the users game data so they can continue from where they left off
        gamer = new Gamer(name, 0, 1, 3);
        level = new Level(gamer.getHighestLevel(), gamer.getTotScore(), this,  gamer.getLives());
        setScreen(new GameScreen(this));
    }

    public Level getLevel() {
        return level;
    }

    public int getLevelNumber() {
        return gamer.getHighestLevel();
    }

    @Override
    public void endGameListener(boolean levelComplete) {
        if (levelComplete) {
            gamer.incHighestLevel();
            gamer.setTotScore(level.getTotLevelScore());
            gamer.setLives(level.getLives());
            level = new Level(gamer.getHighestLevel(), gamer.getTotScore(), this,  gamer.getLives());
            setScreen(new GameScreen(this));
        }
        else {
            //level = new Level(gamer.getHighestLevel(), gamer.getTotScore(), this, gamer.getLives());

            //add stats to db//
            //show game OverScreen//
            setScreen(new MainMenu(this));
        }
    }
}
