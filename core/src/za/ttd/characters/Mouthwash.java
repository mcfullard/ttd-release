package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import za.ttd.characters.objects.Position;

/**
 * Created by s213391244 on 7/24/2015.
 */
public class Mouthwash extends Collectible {
    static final int score = 50;
    static final String FILENAME = "core/assets/textures/in/characters/mouthWash.png";
    private Texture texture;

    public Mouthwash(Position position, int speed){
        super(position);
        setMovementSpeed(speed);
        texture = new Texture(Gdx.files.internal(FILENAME));
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
