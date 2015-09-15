package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Position;
import za.ttd.renderers.Renderable;

/**
 * Created by s213391244 on 7/24/2015.
 */
public abstract class Collectible extends InGameObject implements Renderable {
    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureRegion stillTexture;
    private TextureAtlas textureAtlas;


    public Collectible(Position position, String itemName) {
        super(position);

        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        stillTexture = textureAtlas.findRegion(String.format("items/%s", itemName));
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    @Override
    public TextureRegion getTexture() {
        return stillTexture;
    }

    @Override
    public Animation getAnimation() {
        return null;
    }

}
