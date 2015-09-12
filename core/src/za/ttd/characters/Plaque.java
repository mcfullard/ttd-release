package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import za.ttd.characters.objects.Position;

/**
 * Created by s213391244 on 7/24/2015.
 */
public class Plaque extends Collectible {
    private String textureFilePath = "core/assets/textures/in/characters/decayFull.png";
    private Texture texture;

    public Plaque(Position position) {
        super(position);
        texture = new Texture(Gdx.files.internal(textureFilePath));
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Animation getAnimation() {
        return null;
    }

    @Override
    public float getX() {
        return position.getX();
    }

    @Override
    public float getY() {
        return position.getY();
    }
}
