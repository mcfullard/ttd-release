package za.ttd.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Position;

public class BadBreath extends Enemy {
    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureRegion stillTexture;
    private TextureAtlas textureAtlas;
    private Animation currentAnimation, animationR, animationL, animationU, animationD;

    public BadBreath(Position position, float speed) {
        super(position, speed);
        this.setMovementSpeed(speed);
        stillTexture = null;
    }

    @Override
    public TextureRegion getTexture() {
        return stillTexture;
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
