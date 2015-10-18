package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Game;
import za.ttd.game.Level;

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
        dialog = new Dialog("Instructions", skin);
        dialog.text("Lorem ipsum dolar sit amet. Consecutor... I can't remember the rest");
        dialog.button("Okay");
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        //level.render();
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
