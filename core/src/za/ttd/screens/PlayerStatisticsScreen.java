package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.game.Game;
import za.ttd.game.Player;

public class PlayerStatisticsScreen extends AbstractScreen {
    public PlayerStatisticsScreen() {
    }

    private Player player = Game.getInstance().getPlayer();
    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    private Label playerStatsLabel = new Label("Player Statistics", skin);

    private Label levelLives = new Label("Lives Used: ", skin);
    private Label livesValue = new Label("" + player.scoring.getTotLivesUsed(), skin);

    private Label collectibles = new Label("Collectibles Found: ", skin);
    private Label collectiblesValue = new Label("" + player.scoring.getTotCollectiblesFound(), skin);

    private Label badBreath = new Label("Bad Breath Kills: ", skin);
    private Label badBreathValue = new Label("" + player.scoring.getTotBadBreathKilled(), skin);

    private Label powersUsed = new Label("Total Powers Used: ", skin);
    private Label powersUsedValue = new Label("" + player.scoring.getTotPowersUsed(), skin);
    private TextButton back = new TextButton("Back", skin);
    private TextButton highScores = new TextButton("High Scores", skin);

    @Override
    public void show() {
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.getInstance().setScreen(ScreenTypes.MAIN_MENU);
            }
        });
        highScores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.getInstance().setScreen(ScreenTypes.HIGH_SCORES);
            }
        });

        table.add(playerStatsLabel).colspan(2).row();
        table.add(levelLives).size(200, 30).padBottom(5).center();
        table.add(livesValue).right().row();
        table.add(badBreath).size(200, 30).padBottom(5).center();
        table.add(badBreathValue).right().row();
        table.add(collectibles).size(200, 30).padBottom(5).center();
        table.add(collectiblesValue).right().row();
        table.add(powersUsed).size(200, 30).padBottom(5).center();
        table.add(powersUsedValue).right().row();
        table.add(back).size(100, 30).padTop(10).left();
        table.add(highScores).size(100, 30).center().padTop(10).right().row();
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
