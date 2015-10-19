package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    private Skin skin = new Skin(Gdx.files.internal("defaultui/uiskin.json"));
    private Label labelName = new Label("Enter Name", skin);
    private Label labelPassword = new Label("Enter Password", skin);
    private Label labelInfo = new Label("(New players: consider this registration)", skin);
    private TextField textName = new TextField("", skin);
    private TextField textPassword = new TextField("", skin);
    private TextButton buttonContinue = new TextButton("Login", skin);
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
            if (!ConnectDB.checkPlayerExists(name)) {
                Player player = Player.getInstance();
                player.setName(name);
                player.setHighestScore(0);
                player.setHighestLevel(1);
                player.setLives(3);
                player.resetScoringSystem();
                player.controls.defaultControls();
                player.setAchievements(ConnectDB.getAchievements());
                Security.generateHash(player, password);
                setupNotFoundDialog();
                dialog.show(stage);
            } else {
                ConnectDB.populatePlayer(name);
                if(Security.hashMatch(Player.getInstance(), password)) {
                    Game.getInstance().setNewPlayer(false);
                    toMainMenu();
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
        dialog.text("The password you supplied does not match the one on record. Please try again.\n\n *If you are a New User please note that this Name is already in use");
        dialog.button("Ok");
    }

    private void setupNotFoundDialog() {
        dialog = new Dialog("Confirm Player", skin) {
            @Override
            public void result(Object obj) {
                if ((boolean) obj) {
                    toMainMenu();
                }
            }
        };
        dialog.text("No player with this name was found. Would you like to create one?");
        dialog.button("No", false);
        dialog.button("Yes", true);
    }

    @Override
    public void hide() {
        dispose();
    }

    private void toMainMenu() {
        ScreenController.getInstance().setScreen(ScreenTypes.MAIN_MENU);
    }
}
