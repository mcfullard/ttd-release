package za.ttd.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    private AssetManager manager;
    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureRegion stillTexture;
    private TextureAtlas textureAtlas;
    private Animation animationR, animationL, animationU, animationD;

    public Assets() {
        manager = new AssetManager();
    }

    public static void Load() {

    }

    public static Boolean isLoaded() {
        return false;
    }

    public static void dispose() {
    }
}
