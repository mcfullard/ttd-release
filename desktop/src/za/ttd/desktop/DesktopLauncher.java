package za.ttd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import za.ttd.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		// this code should only run used during development - remove on release
		//TexturePacker.process("core/assets/textures/in", "core/assets/textures/out", "texture");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Game.WIDTH;
		config.height = Game.HEIGHT;
		config.title = Game.TITLE;
		new LwjglApplication(Game.getInstance(), config);
	}
}
