package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import za.ttd.characters.objects.Position;

public class BadBreath extends Enemy {
    private boolean valuable;
    private float defaultSpeed;
    private Texture texture;
    final String textureFilePath = "core/assets/textures/in/characters/Bad_1.png";

    public BadBreath(Position position, float speed) {
        super(position);
        this.setMovementSpeed(speed);
        this.defaultSpeed = speed;
        valuable = false;
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
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    public void incSpeed() {
        if (!valuable)
            this.setMovementSpeed(this.movementSpeed*1.5f);
    }

    /*
    * Make object to vulnerable, thus allowing Thomas to kill it,
    * make it move slower at the same time, to make it easier for tomas to kill it*/
    public void setVulnerable() {
        valuable = true;
        this.setMovementSpeed(defaultSpeed*.9f);
    }

    public boolean getVulnerability() {
        return valuable;
    }

}
