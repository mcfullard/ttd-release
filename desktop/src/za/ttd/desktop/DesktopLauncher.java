package za.ttd.desktop;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import sun.plugin2.message.Message;
import za.ttd.characters.states.MessageType;
import za.ttd.ttd;

public class DesktopLauncher {
	public static void main (String[] arg) {
		// this code should only run used during development - remove on release
		TexturePacker.process("core/assets/textures/in", "core/assets/textures/out", "texture");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ttd.WIDTH;
		config.height = ttd.HEIGHT;
		config.title = ttd.TITLE;
		ttd gameInstance = new ttd();
		MessageManager.getInstance().addListeners(gameInstance,
				MessageType.THOMAS_DEAD,
				MessageType.TOOTHDECAY_DEAD,
				MessageType.LEVEL_LOADING
				);
		new LwjglApplication(gameInstance, config);
	}
}
