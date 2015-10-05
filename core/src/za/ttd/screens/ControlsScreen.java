package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
    private Player player;

    public ControlsScreen() {
        player = Game.getInstance().getPlayer();
        txtUp = new TextField(Input.Keys.toString(player.controls.getUp()), skin);
        txtDown = new TextField(Input.Keys.toString(player.controls.getDown()), skin);
        txtLeft = new TextField(Input.Keys.toString(player.controls.getLeft()), skin);
        txtRight = new TextField(Input.Keys.toString(player.controls.getRight()), skin);

        lblUp = new Label("UP", skin);
        lblDown = new Label("DOWN", skin);
        lblLeft = new Label("LEFT", skin);
        lblRight = new Label("RIGHT", skin);
    }



    @Override
    public void show() {
        super.show();
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ControlsScreen.this.dispose();
                try {
                    ScreenController.getInstance().previousScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        txtUp.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                txtUp.setText(Input.Keys.toString(keycode));
                player.controls.setUp(keycode);
                return true;
            }
        });

        txtDown.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                txtDown.setText(Input.Keys.toString(keycode));
                player.controls.setDown(keycode);
                return true;
            }
        });

        txtLeft.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event,int keycode) {
                txtLeft.setText(Input.Keys.toString(keycode));
                player.controls.setLeft(keycode);
                return true;
            }
        });

        txtRight.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event,int keycode) {
                txtRight.setText(Input.Keys.toString(keycode));
                player.controls.setRight(keycode);
                return true;
            }
        });


        table.add(title).pad(40).row();
        table.add(lblUp).size(100,60).pad(40);
        table.add(txtUp).size(60, 40).pad(40);
        table.row();
        table.add(lblDown).size(100, 40).pad(40);
        table.add(txtDown).size(60, 40).pad(40);
        table.row();
        table.add(lblLeft).size(100, 60).pad(40);
        table.add(txtLeft).size(60, 40).pad(40);
        table.row();
        table.add(lblRight).size(100, 60).pad(40);
        table.add(txtRight).size(60, 40).pad(40);
        table.row();
        table.add(btnBack).size(100,60);

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
