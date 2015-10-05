package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.database.ConnectDB;

import java.util.Map;

public class HighScoresScreen extends AbstractScreen {
    private Map<String, Integer> players;

    public HighScoresScreen() {
        fetchPlayers();
    }

    private Stage stage = new Stage();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));

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

        mainTable.add(highScoresLabel).pad(40).row();

        mainTable.add(scrollPane).size(400,200).pad(10).row();

        scoresTable.setFillParent(true);

        mainTable.add(back).padBottom(20).row();


        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
    }

    private void populateScoresTable() {
        for (Map.Entry<String, Integer> entry : players.entrySet()) {
            Label playerLabel = new Label(entry.getKey(), skin);
            Label scoreLabel = new Label(entry.getValue().toString(), skin);
            scoresTable.add(playerLabel).size(100, 40).padBottom(10);
            scoresTable.add(scoreLabel).row();
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
