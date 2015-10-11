package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.TelegramProvider;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Game;

/**
 * @author minnaar
 * @since 18 July 2015
 * <p>
 * The main menu that contains buttons to sub-menus and other actions.
 */
public class MainMenuScreen extends AbstractScreen implements Telegraph, TelegramProvider {
    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/defaultui/uiskin.json"));
    private TextButton buttonContinue = new TextButton("Continue", skin);
    private TextButton buttonNewGame = new TextButton("New Game", skin);
    private TextButton buttonHighScore = new TextButton("High Scores", skin);
    private TextButton buttonStatistics = new TextButton("Statistics", skin);
    private TextButton buttonControls = new TextButton("Controls", skin);
    private TextButton buttonCredits = new TextButton("Credits", skin);
    private TextButton buttonExit = new TextButton("Exit", skin);
    private TextButton buttonLogout = new TextButton("Logout", skin);
    private Label title = new Label("Main Menu", skin);
    private ScreenController screenController;
    private Game game;

    //private TextButton buttonHighScores = new TextButton("High Scores", skin);

    public MainMenuScreen() {
        registerSelfAsProvider();
        game = Game.getInstance();
        screenController = ScreenController.getInstance();
    }

    private void registerSelfAsProvider() {
        MessageManager.getInstance().addProviders(this,
                MessageType.LOAD_LEVEL);
    }

    @Override
    public void show() {

        if (!game.isNewPlayer())
            buttonContinue.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.createGame();
                }
            });

        buttonNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.newGame();
            }
        });

        buttonControls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenController.setScreen(ScreenTypes.CONTROLS);
            }
        });

        buttonCredits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                screenController.setScreen(ScreenTypes.CREDITS);
            }

        });

        buttonHighScore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getInstance().setScreen(new LoadingScreen(
                        "Loading Highscores ...",
                        () -> {
                            MessageManager.getInstance().dispatchMessage(
                                    Game.getInstance(),
                                    MessageType.UPDATE_DB);
                        },ScreenTypes.HIGH_SCORES
                ));
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
                screenController.setScreen(ScreenTypes.PLAYER_STATS);
            }
        });

        buttonLogout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenController.setScreen(ScreenTypes.USER_INPUT);
            }
        });



        table.add(title).pad(40).row();
        if (!game.isNewPlayer())
            table.add(buttonContinue).size(150, 40).padBottom(20).row();
        table.add(buttonNewGame).size(150, 40).padBottom(20).row();
        table.add(buttonStatistics).size(150, 40).padBottom(20).row();
        table.add(buttonHighScore).size(150, 40).padBottom(20).row();
        table.add(buttonControls).size(150, 40).padBottom(20).row();
        table.add(buttonCredits).size(150, 40).padBottom(20).row();
        table.add(buttonLogout).size(150, 40).padBottom(20).row();
        table.add(buttonExit).size(150, 40).padBottom(20).row();
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

    @Override
    public Object provideMessageInfo(int msg, Telegraph receiver) {
        return null;
    }
}
