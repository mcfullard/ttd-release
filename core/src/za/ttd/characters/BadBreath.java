package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Position;

public class BadBreath extends Enemy {
    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureRegion stillTexture;
    private TextureAtlas textureAtlas;
    private Animation currentAnimation, animationR, animationL, animationU, animationD;

    public BadBreath(Position position, float speed, String actorName) {
        super(position, speed, actorName);
        this.setMovementSpeed(speed);

        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        stillTexture = textureAtlas.findRegion("characters/BadBreath0");
        //create animations
        currentAnimation = null; //Will change depending on direction of player

        //Replace up and down references with correct for up and down (currently don't exist)
        animationU = new Animation(1/4f,
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"));
        animationD = new Animation(1/4f,
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"));
        animationL = new Animation(1/4f,
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"));
        animationR = new Animation(1/4f,
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"),
                textureAtlas.findRegion("characters/BadBreath0"));
    }

    @Override
    public TextureRegion getTexture() {
        return stillTexture;
    }

    @Override
    public Animation getAnimation() {
        return currentAnimation;
    }

    public void incSpeed() {
        if (!super.vulnerable)
            this.setMovementSpeed(this.movementSpeed*1.5f);
    }

    public boolean getVulnerability() {
        return super.vulnerable;
    }

}
