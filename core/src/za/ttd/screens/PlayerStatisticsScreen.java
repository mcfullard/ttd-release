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
import za.ttd.database.ConnectDB;
import za.ttd.game.Game;

public class PlayerStatisticsScreen extends AbstractScreen {
    private ConnectDB connectDB = new ConnectDB();
    private ConnectDB.Statistics statistics = null;
    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    private Label playerStatsLabel = new Label("Player Statistics", skin);
    private Label benny = new Label("Benny The Brush Pickups: " + statistics.getBenny(), skin);
    private Label levelLives = new Label("Level Lives: " + statistics.getLevelLives(), skin);
    private Label minty = new Label("Minty Mouthwash Pickups: " + statistics.getMinty(), skin);
    private Label plaque = new Label("Plaque Collected: " + statistics.getBenny(), skin);
    private Label badBreath = new Label("Bad Breath Kills: " + statistics.getBadBreath(), skin);
    private Label toothDecay = new Label("toothDecay Kills: " + statistics.getToothDecay(), skin);
    private TextButton back = new TextButton("Back", skin);

    private static PlayerStatisticsScreen instance;

    private PlayerStatisticsScreen(Game game) {
        super(game);
        try {
            statistics = connectDB.getStatistics(this.game.getPlayerID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PlayerStatisticsScreen getInstance(Game game) {
        if (instance == null)
            instance = new PlayerStatisticsScreen(game);

        return instance;
    }

    @Override
    public void show() {
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //display gameOverScreen
            }
        });

        table.add(playerStatsLabel).size(200, 80).padBottom(40).row();
        table.add(levelLives).size(100, 40).padBottom(10).row();
        table.add(plaque).size(100, 40).padBottom(10).row();
        table.add(badBreath).size(100, 40).padBottom(10).row();
        table.add(minty).size(100, 40).padBottom(10).row();
        table.add(benny).size(100, 40).padBottom(10).row();
        table.add(toothDecay).size(100, 40).padBottom(10).row();
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
