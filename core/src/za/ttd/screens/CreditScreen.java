package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import za.ttd.game.Assets;

public class CreditScreen extends AbstractScreen {
    private Animation currentAnimation;
    private float x, startX , startY;
    private Batch batch;
    private BitmapFont theMessage;
    private String thankMessage;
    private String namesMessage;
    private String exitMessage;
    private float elapsedTime;

    public CreditScreen() {
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0,0,600,800);
        theMessage = new BitmapFont();
        thankMessage = "Thank You For Playing Our Game\n                  Team Sudo";
        namesMessage ="      Developers\n\nMinnaar Fullard\nSebastian Lasevicius\nPhilip Le Grange\nRhys Botes";
        exitMessage="Press Esc key to return";

        startX = (300-(30*theMessage.getSpaceWidth()));
        startY=0;
        currentAnimation = Assets.getInstance().getAnimation("Thomas", "Right");
        x = 0;
        this.elapsedTime = 0;
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
        if (startY <= 800)
            startY += .5f;
        else
            startY = -150;

        batch.begin();
            theMessage.draw(batch,exitMessage,10,790);
            theMessage.draw(batch, thankMessage, startX-60, startY);
            theMessage.draw(batch, namesMessage,startX,startY+150);
            batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), x, 336);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ScreenController.getInstance().setScreen(ScreenTypes.MAIN_MENU);
        }
    }
}
