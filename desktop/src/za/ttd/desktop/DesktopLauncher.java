package za.ttd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import za.ttd.ttd;

public class DesktopLauncher {
	public static void main (String[] arg) {
		// this line should only be used during development - remove on release
		TexturePacker.process("core/assets/img/sprites/in", "core/assets/img/sprites/out", "sprites");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ttd.WIDTH;
		config.height = ttd.HEIGHT;
		new LwjglApplication(new ttd(), config);
	}
}
