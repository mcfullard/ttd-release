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
import za.ttd.game.Player;

/**
 * Created by s213391244 on 9/18/2015.
 */
public class PlayerStatisticsScreen extends AbstractScreen {
    public PlayerStatisticsScreen() {
    }

    private Player player = Game.getInstance().getPlayer();
    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    private Label playerStatsLabel = new Label("Player Statistics", skin);
    private Label levelLives = new Label("Lives Used: " + player.scoring.getTotLivesUsed(), skin);
    private Label collectibles = new Label("Collectibles Found: " + player.scoring.getTotCollectiblesFound(), skin);
    private Label badBreath = new Label("Bad Breath Kills: " + player.scoring.getTotBadBreathKilled(), skin);
    private Label powersUsed = new Label("Total Powers Used: " + player.scoring.getTotPowersUsed(), skin);
    private TextButton back = new TextButton("Back", skin);

    @Override
    public void show() {
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenController.getInstance().setScreen(ScreenTypes.MAIN_MENU);
            }
        });

        table.add(playerStatsLabel).size(200, 80).padBottom(40).row();
        table.add(levelLives).size(100, 40).padBottom(10).row();
        table.add(badBreath).size(100, 40).padBottom(10).row();
        table.add(collectibles).size(100, 40).padBottom(10).row();
        table.add(powersUsed).size(100, 40).padBottom(10).row();
        table.add(back);
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
