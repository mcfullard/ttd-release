package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import za.ttd.characters.objects.Position;

public class BadBreath extends Enemy {
    private Texture texture;
    private final String textureFilePath = "core/assets/textures/in/characters/Bad_1.png";

    public BadBreath(Position position, float speed) {
        super(position, speed);
        this.setMovementSpeed(speed);
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

    public void incSpeed() {
        if (!super.vulnerable)
            this.setMovementSpeed(this.movementSpeed*1.5f);
    }

    public boolean getVulnerability() {
        return super.vulnerable;
    }

}
