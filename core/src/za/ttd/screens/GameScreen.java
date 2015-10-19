package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import za.ttd.characters.states.MessageType;
import za.ttd.database.ConnectDB;
import za.ttd.game.Game;
import za.ttd.game.Level;
import za.ttd.game.Player;

public class GameScreen extends AbstractScreen implements Telegraph{

    private Level level;
    private Dialog dialog;
    private Stage stage = new Stage();
    private Skin skin = new Skin(Gdx.files.internal("defaultui/uiskin.json"));
    
    public GameScreen() {
        this.level = Game.getInstance().getLevel();
        registerSelfAsListener();
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListener(this,
                MessageType.LEVEL_PAUSED
        );
    }

    @Override
    public void show() {
        super.show();
        dialog = new Dialog("Instructions", skin);
        Label label = new Label(
                "Use the arrow keys to move around and \nevade the Bad Breath and Tooth Decay. \n" +
                "Collect Minty Mouthwash bottles in the \ncorners to make Bad Breath temporarily \n" +
                "vulnerable. Collect Benny the Brush to \ndefeat Tooth Decay and progress to the " +
                "next level.", skin);
        label.setAlignment(Align.center);
        dialog.text(label);
        dialog.button("Okay");
        if(Player.getInstance().getHighestLevel() == 1 && !ConnectDB.checkPlayerExists(Player.getInstance().getName())) {
            dialog.show(stage);
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        level.render();
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.LEVEL_PAUSED:
                ScreenController.getInstance().setScreen(ScreenTypes.PAUSE_MENU);
                return true;
        }
        return false;
    }
}
