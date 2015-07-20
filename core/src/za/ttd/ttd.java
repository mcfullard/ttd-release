package za.ttd;

import com.badlogic.gdx.Game;
import za.ttd.screens.SplashScreen;

public class ttd extends Game {

	public static final String TITLE = "The Wrath of Thomas the Dentist";
	public static final int WIDTH = 600, HEIGHT = 800;

	@Override
	public void create() {
		setScreen(new SplashScreen(this));
	}
}
