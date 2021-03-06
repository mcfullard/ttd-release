package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author minnaar
 * @since 18 July 2015
 *
 * SplashScreen screen displayed once the game starts up
 *
 */
public class SplashScreen extends AbstractScreen {
    private Texture texture = new Texture(Gdx.files.internal("img/screens/splash.png"));
    private Image splashImage = new Image(texture);
    private Stage stage = new Stage();

    public SplashScreen() {}

    @Override
    public void show() {
        stage.addActor(splashImage);
        splashImage.addAction(Actions.sequence(
                Actions.alpha(0f),
                Actions.fadeIn(0.5f),
                Actions.delay(2),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        ScreenController.getInstance().setScreen(ScreenTypes.USER_INPUT);
                    }
                })
        ));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
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
        texture.dispose();
        stage.dispose();
    }
}
