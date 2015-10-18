package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.characters.states.MessageType;
import za.ttd.database.ConnectDB;
import za.ttd.game.Game;
import za.ttd.game.Level;
import za.ttd.game.Player;

public class GameScreen extends AbstractScreen implements Telegraph{

    private Level level;
    private Dialog dialog;
    private Stage stage = new Stage();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    
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
        TextButton buttonOkay = new TextButton("Okay", skin);
        buttonOkay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        dialog = new Dialog("Instructions", skin);
        dialog.text("Lorem ipsum dolar sit amet. Consecutor... I can't remember the rest");
        dialog.button(buttonOkay);
        if(ConnectDB.checkPlayerExists(Player.getInstance().getName())) {
            dialog.show(stage);
        }
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
