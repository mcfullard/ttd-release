package za.ttd.Managers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Bas on 29/07/2015.
 */
public class AssetManager implements ApplicationListener {

    public Texture tocImage; //toc: Top Open Connector
    public Texture rocImage; //roc: Right Open Connector
    public Texture bocImage; //boc: Bottom Open Connector
    public Texture locImage; //loc: Left Open Connector
    public Texture hocImage; //hoc: Horizontal open Connector
    public Texture vocImage; //voc: Vertical Open Connector
    public Texture tveImage; //tve: Top Vertical Edge
    public Texture rheImage; //rhe: Right Horizontal Edge
    public Texture bveImage; //bve: Bottom Vertical Edge
    public Texture lheImage; //lhe: Left Horizontal Edge
    public Texture tleImage; //tle: Top Left Edge
    public Texture bleImage; //ble: Bottom Left Edge
    public Texture treImage; //tre: Top Right Edge
    public Texture breImage; //bre: Bottom Right Edge

    private OrthographicCamera camera;
    private SpriteBatch batch;

    @Override
    public void create() {
        /*Here we load the images for each level before the level shows up
          Images are 32X32 pixels*/

        tocImage = new Texture(Gdx.files.internal("tocImage.png"));
        rocImage = new Texture(Gdx.files.internal("rocImage.png"));
        bocImage = new Texture(Gdx.files.internal("bocImage.png"));
        locImage = new Texture(Gdx.files.internal("locImage.png"));
        hocImage = new Texture(Gdx.files.internal("hocImage.png"));
        vocImage = new Texture(Gdx.files.internal("vocImage.png"));
        tveImage = new Texture(Gdx.files.internal("tveImage.png"));
        rheImage = new Texture(Gdx.files.internal("rheImage.png"));
        bveImage = new Texture(Gdx.files.internal("bveImage.png"));
        lheImage = new Texture(Gdx.files.internal("lheImage.png"));
        tleImage = new Texture(Gdx.files.internal("tleImage.png"));
        bleImage = new Texture(Gdx.files.internal("bleImage.png"));
        treImage = new Texture(Gdx.files.internal("treImage.png"));
        breImage = new Texture(Gdx.files.internal("breImage.png"));

        //Camera objects
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
