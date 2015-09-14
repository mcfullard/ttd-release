package za.ttd.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Position;

/**
 * Created by s213391244 on 7/24/2015.
 */
public class ToothDecay extends Enemy {

    public ToothDecay(Position position, int speed){
        super(position,speed);
        this.setMovementSpeed(speed);
    }

    @Override
    public TextureRegion getTexture() {
        return null;
    }

    @Override
    public Animation getAnimation() {
        return null;
    }
}
