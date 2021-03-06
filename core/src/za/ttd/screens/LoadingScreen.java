package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import za.ttd.game.Assets;

public class LoadingScreen extends AbstractScreen{
    private Animation currentAnimation;
    private float x, startX;
    private Batch batch;
    private BitmapFont theMessage;
    private String message;
    private float elapsedTime;
    private Thread taskThread;
    private ScreenTypes screenType;

    public LoadingScreen(String message, Runnable task, ScreenTypes screenType) {
        this.screenType = screenType;
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0,0,600,800);
        theMessage = new BitmapFont();
        this.message = message;
        startX = 300-(message.length());

        currentAnimation = Assets.getInstance().getAnimation("Thomas", "Right");
        startX = (300-(30*theMessage.getSpaceWidth()));
        x = 0;
        elapsedTime = 0;
        taskThread = new Thread(task);
        taskThread.start();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (x <= 600)
            x += 1f;
        else
            x = -150;

        batch.begin();
        theMessage.draw(batch, message, startX, 450);
        batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), x, 336);
        batch.end();
        if(!taskThread.isAlive()) {
            ScreenController.getInstance().setScreen(screenType);
        }
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        theMessage.dispose();
    }
}
