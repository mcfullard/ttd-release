package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Position;

public class Plaque extends Collectible {
    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureAtlas textureAtlas;
    private TextureRegion plaqueTexture;

    public Plaque(Position position) {
        super(position);

        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        plaqueTexture = textureAtlas.findRegion("items/Plaque");
    }

    @Override
    public TextureRegion getTexture() {
        return plaqueTexture;
    }

    @Override
    public Animation getAnimation() {
        return null;
    }
}
