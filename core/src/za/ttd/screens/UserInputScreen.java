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
import za.ttd.database.ConnectDB;
import za.ttd.game.Game;
import za.ttd.game.Player;
import za.ttd.game.Security;

/**
 * @author minnaar
 * @since 2015/09/24.
 */
public class UserInputScreen extends AbstractScreen {
    private Stage stage = new Stage();
    private Table table = new Table();
    //private Skin skin = new Skin(Gdx.files.internal("core/assets/textures/out/texture.json"));
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    private Label labelName = new Label("Enter Name", skin);
    private Label labelPassword = new Label("Enter Password", skin);
    private Label labelInfo = new Label("(New players: consider this registration)", skin);
    private TextField textName = new TextField("", skin);
    private TextField textPassword = new TextField("", skin);
    private TextButton buttonContinue = new TextButton("Continue", skin);
    private Dialog dialog;

    public UserInputScreen() {
    }

    @Override
    public void show() {
        stage.setKeyboardFocus(textName);
        Drawable tfBack = textName.getStyle().background;
        tfBack.setLeftWidth(tfBack.getLeftWidth() + 10);
        textName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (c == '\r' || c == '\n' || c == '\t') { // when the user presses enter or tab
                    stage.setKeyboardFocus(textPassword);
                }
            }
        });
        textPassword.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (c == '\r' || c == '\n') { // when the user presses enter
                    validateInput(textName.getText(), textPassword.getText());
                }
            }
        });
        buttonContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                validateInput(textName.getText(), textPassword.getText());
            }
        });
        textPassword.setPasswordMode(true);
        textPassword.setPasswordCharacter('*');
        table.add(labelName).padTop(175).padBottom(20).row();
        table.add(textName).size(282,54).padBottom(20).row();
        table.add(labelPassword).padBottom(20).row();
        table.add(textPassword).size(282,54).padBottom(20).row();
        table.add(buttonContinue).padBottom(200).row();
        table.add(labelInfo);
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

    private void validateInput(String name, String password) {
        if (!name.isEmpty()) {
            Player loadedPlayer = ConnectDB.getPlayer(name);
            if (loadedPlayer == null) {
                loadedPlayer = new Player(name, 0, 1, 3);
                Security.generateHash(loadedPlayer, password);
                Game.getInstance().setPlayer(loadedPlayer);
                setupNotFoundDialog();
                dialog.show(stage);
            } else {
                if(Security.hashMatch(loadedPlayer, password)) {
                    Game.getInstance().setPlayer(loadedPlayer);
                    toMainMenu(false);
                } else {
                    setupInvalidPasswordDialog();
                    dialog.show(stage);
                    textPassword.setText("");
                }
            }
        }
    }

    private void setupInvalidPasswordDialog() {
        dialog = new Dialog("Invalid Password", skin);
        dialog.text("The password you supplied does not match the one on record. Please try again.");
        dialog.button("Ok");
    }

    private void setupNotFoundDialog() {
        dialog = new Dialog("Confirm Player", skin) {
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
        Game.getInstance().setScreen(new MainMenuScreen(newPlayer));
    }
}
