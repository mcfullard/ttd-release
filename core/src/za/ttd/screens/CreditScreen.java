package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import za.ttd.game.Game;

public class CreditScreen extends AbstractScreen {

    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureAtlas textureAtlas;
    private Animation currentAnimation;
    private Animation creditAnimation;
    private float x, startX , startY;
    private Batch batch;
    private BitmapFont theMessage;
    private String thankMessage;
    private String namesMessage;
    private float elapsedTime;

    private static CreditScreen instance;

    private CreditScreen(Game game) {
        super(game);
        /*stage = new Stage();
        table = new Table();*/
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0,0,600,800);
        theMessage = new BitmapFont();
        thankMessage = "Thank You For Playing Our Game\n                  Team Sudo";
        namesMessage ="      Developers\n\nMinnaar Fullard\nSebastian Lasevicius\nPhilip Le Grange\nRhys Botes";

        startX = (300-(30*theMessage.getSpaceWidth()));
        startY=0;

        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));

        currentAnimation = new Animation(1/8f,
                textureAtlas.findRegion("characters/ThomasBR1"),
                textureAtlas.findRegion("characters/ThomasBR2"),
                textureAtlas.findRegion("characters/ThomasBR3"));
        x = 0;
        this.elapsedTime = 0;
    }

    public static CreditScreen getInstance(Game game) {
        if (instance == null)
            instance = new CreditScreen(game);

        return instance;
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
        theMessage.draw(batch, thankMessage, startX-50, startY);
        theMessage.draw(batch, namesMessage,startX,startY+150);
        batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), x, 336);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ScreenController.getInstance(game).previousScreen();
        }
    }
}
