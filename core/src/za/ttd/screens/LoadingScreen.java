package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import za.ttd.ttd;

/**
 * Created by Bas on 18/09/2015.
 */
public class LoadingScreen extends AbstractScreen {
    private final String atlasFilePath = "core/assets/textures/out/texture.atlas", message = "LOADING";
    private TextureAtlas textureAtlas;
    private Animation currentAnimation;
    private Batch batch;
    private BitmapFont theMessage;
    private float x;
    public LoadingScreen(ttd game) {
        super(game);
        batch = new SpriteBatch();

        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        currentAnimation = new Animation(1/8f,
                textureAtlas.findRegion("characters/ThomasR1"),
                textureAtlas.findRegion("characters/ThomasR2"),
                textureAtlas.findRegion("characters/ThomasR3"));

        theMessage = new BitmapFont();
        //Tell SpriteBatch to render in the co-ordinate system specified by screen
        batch.getProjectionMatrix().setToOrtho2D(0,0,600,800);
        x = 300;

    }

    @Override
    public void render(float delta) {
        if(x <= 350)
            x = x + .5f;
        else
            x = 300;

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        theMessage.draw(batch, message, 300, 400);
        batch.draw(currentAnimation.getKeyFrame(Gdx.graphics.getDeltaTime(), true), x, 350);
        batch.end();
    }
}
