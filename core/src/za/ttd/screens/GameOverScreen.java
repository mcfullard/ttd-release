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

public class GameOverScreen extends AbstractScreen {

    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    private Label gameOverLabel = new Label("GAME OVER!", skin);
    private TextButton buttonViewHighScores = new TextButton("High Scores", skin);
    private TextButton buttonViewPlayerStatistics = new TextButton("Statistics", skin);
    private TextButton buttonReturnToMainMenu = new TextButton("Main Menu", skin);

    public void show() {
        buttonViewHighScores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.getInstance().setScreen(ScreenTypes.HIGH_SCORES);
            }
        });
        buttonViewPlayerStatistics.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.getInstance().setScreen(ScreenTypes.PLAYER_STATS);
            }
        });
        buttonReturnToMainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.getInstance().setScreen(ScreenTypes.MAIN_MENU);
            }
        });

        table.add(gameOverLabel).size(100, 100).padBottom(10).center().row();
        table.add(buttonViewHighScores).size(100, 30).padBottom(10).row();
        table.add(buttonViewPlayerStatistics).size(100, 30).padBottom(10).row();
        table.add(buttonReturnToMainMenu).size(100, 30).padBottom(10).row();
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
