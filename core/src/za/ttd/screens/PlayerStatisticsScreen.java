package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.game.Achievement;
import za.ttd.game.Player;

public class PlayerStatisticsScreen extends AbstractScreen {
    private Player player = Player.getInstance();
    private Stage stage = new Stage();
    private Table table = new Table();
    private Table achievementsTable = new Table();
    private Skin skin = new Skin(Gdx.files.internal("defaultui/uiskin.json"));
    private Label playerStatsLabel = new Label("Player Statistics", skin);

    private ScrollPane scrollPane = new ScrollPane(achievementsTable);

    private Label levelLives = new Label("Lives Used: ", skin);
    private Label livesValue = new Label("" + player.scoring.getTotLivesUsed(), skin);

    private Label collectibles = new Label("Collectibles Found: ", skin);
    private Label collectiblesValue = new Label("" + player.scoring.getTotCollectiblesFound(), skin);

    private Label badBreath = new Label("Bad Breath Kills: ", skin);
    private Label badBreathValue = new Label("" + player.scoring.getTotBadBreathKilled(), skin);

    private Label powersUsed = new Label("Total Powers Used: ", skin);
    private Label powersUsedValue = new Label("" + player.scoring.getTotPowersUsed(), skin);
    private TextButton back = new TextButton("Back", skin);

    private Label lblHighestScore = new Label("Highest Score: ", skin);
    private Label highestScore = new Label("" + player.getHighestScore(), skin);

    private Label achievementsLabel = new Label("Achievements", skin);

    @Override
    public void show() {
        player.updateAchievements();

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

        table.add(playerStatsLabel).colspan(2).padBottom(20).center().row();
        table.add(lblHighestScore).size(200, 30).padBottom(5).left();
        table.add(highestScore).right().row();
        table.add(levelLives).size(200, 30).padBottom(5).left();
        table.add(livesValue).right().row();
        table.add(badBreath).size(200, 30).padBottom(5).left();
        table.add(badBreathValue).right().row();
        table.add(collectibles).size(200, 30).padBottom(5).left();
        table.add(collectiblesValue).right().row();
        table.add(powersUsed).size(200, 30).padBottom(5).left();
        table.add(powersUsedValue).right().padBottom(20).row();

        //populate achievements and add to main table
        table.add(achievementsLabel).colspan(2).padBottom(20).center().row();
        populateAchievementsTable();
        achievementsTable.setSize(400,400);
        scrollPane.setSize(400,400);
        table.add(scrollPane).row();
        table.setSize(400,400);

        table.add(back).size(150, 40).padTop(20).colspan(2);

        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        dispose();
    }


    private void populateAchievementsTable(){
        for(Achievement achievement:player.getAchievementsObtained()){
            Label achievementLabel = new Label(achievement.getDescription(), skin);
            achievementLabel.setSize(400,10);
            achievementsTable.add(achievementLabel).pad(5).left().row();
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
