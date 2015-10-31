package za.ttd.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import za.ttd.characters.objects.Position;
import za.ttd.game.Assets;

public class Toothbrush extends Collectible {

    private Animation currentAnimation;
    public Toothbrush(Position position) {
        super(position, "Benny");
        currentAnimation = Assets.getInstance().getAnimation("Benny", "Idle");
    }

    @Override
    public Animation getAnimation() {
        return currentAnimation;
    }
}
