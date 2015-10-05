package za.ttd.screens;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Game;
import za.ttd.game.Level;

/**
 * @author minnaar
 * @since 2015/07/20.
 */
public class GameScreen extends AbstractScreen implements Telegraph{

    private Level level;
    private static GameScreen instance;
    
    private GameScreen(Game game) {
        super(game);
        this.level = game.getLevel();
        registerSelfAsListener();
    }

    public static GameScreen getInstance(Game game) {
        if (instance == null)
            instance = new GameScreen(game);

        return instance;
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListener(this,
                MessageType.LEVEL_PAUSED
        );
    }

    @Override
    public void render(float delta) {
        level.render();
    }


    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.LEVEL_PAUSED:
                ScreenController.getInstance(game).setScreen(ScreenTypes.PAUSE_MENU);
                return true;
        }
        return false;
    }
}
