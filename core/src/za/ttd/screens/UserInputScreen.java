package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import za.ttd.game.Game;
import za.ttd.game.Level;
import za.ttd.game.Player;

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
    private Dialog dialog;

    public UserInputScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        Drawable tfBack = textName.getStyle().background;
        tfBack.setLeftWidth(tfBack.getLeftWidth() + 10);
        textName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (c == '\r' || c == '\n') { // when the user presses enter
                    validateInput(textName.getText());
                }
            }
        });
        buttonContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                validateInput(textName.getText());
            }
        });
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

    private void validateInput(String name) {
        if (!name.isEmpty()) {
            Player loadedPlayer = game.loadPlayer(name);
            setupDialog();
            if (loadedPlayer == null) {
                loadedPlayer = new Player(name, 0, 1, 2);
                game.setPlayer(loadedPlayer);
                dialog.show(stage);
            } else {
                game.setPlayer(loadedPlayer);
                toMainMenu(false);
            }
        }
    }

    private void setupDialog() {
        Skin default_skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
        dialog = new Dialog("Confirm Player", default_skin) {
            @Override
            public void result(Object obj) {
                if ((boolean) obj) {
                    toMainMenu(true);
                }
            }
        };
        dialog.text("No player with this name was found. Would you like to create one?");
        dialog.button("No", false);
        dialog.button("Yes", true);
    }

    private void toMainMenu(boolean newPlayer) {
        game.setScreen(new MainMenu(game, newPlayer));
    }
}
