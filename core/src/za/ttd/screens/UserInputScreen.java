package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import za.ttd.game.Game;

/**
 * @author minnaar
 * @since 2015/09/24.
 */
public class UserInputScreen extends AbstractScreen {
    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/textures/out/texture.json"));
    private Label labelName = new Label("Enter Name", skin);
    private TextField textName = new TextField("", skin);
    private TextButton buttonContinue = new TextButton("Continue", skin);
    private Dialog dialog = new Dialog("Info", skin);

    public UserInputScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        table.add(labelName).padBottom(20).row();
        table.add(textName).size(282,54).padBottom(20).row();
        table.add(buttonContinue).padBottom(20).row();
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }


}
