package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.game.Game;
import za.ttd.game.Player;

public class ControlsScreen extends AbstractScreen{

    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    private TextButton btnBack = new TextButton("BACK", skin);
    private TextField txtUp, txtDown, txtLeft, txtRight;
    private Label lblUp, lblDown, lblLeft, lblRight, title = new Label("Controls", skin);

    public ControlsScreen(Game game) {
        super(game);
        txtUp = new TextField(Input.Keys.toString(Player.Controls.UP), skin);
        txtDown = new TextField(Input.Keys.toString(Player.Controls.DOWN), skin);
        txtLeft = new TextField(Input.Keys.toString(Player.Controls.LEFT), skin);
        txtRight = new TextField(Input.Keys.toString(Player.Controls.RIGHT), skin);

        lblUp = new Label("UP", skin);
        lblDown = new Label("DOWN", skin);
        lblLeft = new Label("LEFT", skin);
        lblRight = new Label("RIGHT", skin);
    }



    @Override
    public void show() {
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseMenu(game));
            }
        });


        txtUp.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Player.Controls.UP = Input.Keys.valueOf(Character.toString(c));
            }
        });

        txtDown.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Player.Controls.DOWN = Input.Keys.valueOf(Character.toString(c));
            }
        });

        txtLeft.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Player.Controls.LEFT = Input.Keys.valueOf(Character.toString(c));
            }
        });

        txtRight.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                Player.Controls.RIGHT = Input.Keys.valueOf(Character.toString(c));
            }
        });


        table.add(title).center().pad(40).row();


        table.add(lblUp).size(100,60).pad(40).row();
        table.add(txtUp).size(100, 60).pad(40).colspan(1);


        table.add(lblDown).size(100, 60).pad(40).row();
        table.add(txtDown).size(100,60).pad(40).colspan(1);

        table.add(lblLeft).size(100,60).pad(40).row();
        table.add(txtLeft).size(100,60).pad(40).colspan(1);

        table.add(lblRight).size(100,60).pad(40).row();
        table.add(txtRight).size(100,60).pad(40).colspan(1);

        table.add(btnBack).size(100,60).center().padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
