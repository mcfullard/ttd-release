package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.game.Game;

/**
 * @author minnaar
 * @since 18 July 2015
 *
 * The main menu that contains buttons to sub-menus and other actions.
 *
 */
public class MainMenu extends AbstractScreen implements Telegraph {
    private Stage stage = new Stage();
    private Table table = new Table();
    //private Skin skin = new Skin(Gdx.files.internal("core/assets/textures/out/texture.json"));
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    private TextButton buttonContinue = new TextButton("Continue", skin);
    private TextButton buttonNewGame = new TextButton("New Game", skin);
    private TextButton buttonSavedGames = new TextButton("Saved Games", skin);
    private TextButton buttonStatistics = new TextButton("Statistics", skin);
    private TextButton buttonControls = new TextButton("Controls", skin);
    private TextButton buttonCredits = new TextButton("Credits", skin);
    private TextButton buttonExit = new TextButton("Exit", skin);
    private Label title = new Label("Main Menu", skin);
    private boolean newPlayer;

    public MainMenu(Game game, boolean newPlayer) {
        super(game);
        this.newPlayer = newPlayer;
    }

    @Override
    public void show() {
        buttonContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.createGame();
            }
        });

        buttonNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoadingScreen(game));
                game.createGame();
                //MessageManager.getInstance().dispatchMessage(1,MainMenu.this, MessageType.LOAD_LEVEL);
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Save game the exit
                Gdx.app.exit();
            }
        });

        buttonStatistics.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayerStatisticsScreen(game));
            }
        });

        table.add(title).padBottom(40).row();
        if (!newPlayer)
            table.add(buttonContinue).size(150, 60).padBottom(20).row();
        table.add(buttonNewGame).size(150,60).padBottom(20).row();
        table.add(buttonSavedGames).size(150,60).padBottom(20).row();
        table.add(buttonStatistics).size(150,60).padBottom(20).row();
        table.add(buttonControls).size(150,60).padBottom(20).row();
        table.add(buttonCredits).size(150,60).padBottom(20).row();
        table.add(buttonExit).size(150,60).padBottom(20).row();

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

    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }
}
