package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.game.Game;

public class GameOverScreen extends AbstractScreen {
    private Game game;
    private static GameOverScreen instance;

    private GameOverScreen(Game game) {
        super(game);
        this.game = game;
    }

    public static GameOverScreen getInstance(Game game) {
        if (instance == null)
            instance = new GameOverScreen(game);

        return instance;
    }

    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    private Label gameOverLabel = new Label("GAME OVER!", skin);
    private TextButton buttonViewHighScores = new TextButton("View High Scores", skin);
    private TextButton buttonViewPlayerStatistics = new TextButton("View Player Statistics", skin);
    private TextButton buttonReturnToMainMenu = new TextButton("Return To Main Menu", skin);

    public void show() {
        buttonViewHighScores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //display high scores screen
            }
        });
        buttonViewPlayerStatistics.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //display player statistics screen
            }
        });
        buttonReturnToMainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //display main menu
            }
        });

        table.add(gameOverLabel).size(200, 80).padBottom(40).row();
        table.add(buttonViewHighScores).size(150, 60).padBottom(20).row();
        table.add(buttonViewPlayerStatistics).size(150, 60).padBottom(20).row();
        table.add(buttonReturnToMainMenu).size(150, 60).padBottom(20).row();
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
