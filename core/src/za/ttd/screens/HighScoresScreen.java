package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import javafx.util.Pair;
import za.ttd.database.ConnectDB;

import java.util.List;

public class HighScoresScreen extends AbstractScreen {
    private List<Pair<String, Integer>> players;

    public HighScoresScreen() {
        fetchPlayers();
    }

    private Stage stage = new Stage();
    private Skin skin = new Skin(Gdx.files.internal("defaultui/uiskin.json"));

    private Table scoresTable = new Table(skin);
    private ScrollPane scrollPane = new ScrollPane(scoresTable);

    private Label highScoresLabel = new Label("High Scores", skin);
    private TextButton back = new TextButton("Back", skin);

    private Table mainTable = new Table();

    private void fetchPlayers() {
        this.players = ConnectDB.getHighestScoringPlayers();
    }

    @Override
    public void show() {
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    ScreenController.getInstance().previousScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        populateScoresTable();

        mainTable.add(highScoresLabel).colspan(2).row();

        mainTable.add(scrollPane).row();

        scoresTable.setSize(600,400);

        mainTable.add(back).size(150,40).colspan(2).row();
        mainTable.setSize(600,800);

        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
    }

    private void populateScoresTable() {
        for (Pair<String, Integer> pair : players) {
            Label playerLabel = new Label(pair.getKey(), skin);

            Label scoreLabel = new Label(pair.getValue().toString(), skin);
            scoresTable.add(playerLabel).size(200,30).pad(5);
            scoresTable.add(scoreLabel).pad(5).row();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
