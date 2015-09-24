package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import za.ttd.Game;

public class LoadingScreen extends AbstractScreen {

    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureAtlas textureAtlas;
    private Animation currentAnimation;
    //private Label message;
    private float x, startX;
    private Batch batch;
    private BitmapFont theMessage;
    private String message;
    private float elapsedTime;

    /*private Stage stage;
    private Table table;*/

    public LoadingScreen(Game game) {
        super(game);
        /*stage = new Stage();
        table = new Table();*/
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0,0,600,800);
        theMessage = new BitmapFont();
        message = "LOADING";
        startX = 300-(message.length());

        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        currentAnimation = new Animation(1/8f,
                textureAtlas.findRegion("characters/ThomasR1"),
                textureAtlas.findRegion("characters/ThomasR2"),
                textureAtlas.findRegion("characters/ThomasR3"));
        x = startX-40;
        this.elapsedTime = 0;

        /*table.add(message).size(150, 60).padBottom(10);
        table.add(currentAnimation);*/

       /* table.setFillParent(true);
        stage.addActor(table);*/
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (x <= startX+60)
            x += .5f;
        else
            x = startX-40;

        batch.begin();
        theMessage.draw(batch, message, startX, 450);
        batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), x, 400);
        batch.end();
        /*stage.act();
        stage.draw();*/
    }
}
