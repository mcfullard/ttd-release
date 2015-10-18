package za.ttd.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Position;
import za.ttd.game.Assets;
import za.ttd.renderers.Renderable;

public abstract class Collectible extends InGameObject implements Renderable {
    private TextureRegion stillTexture;

    public Collectible(Position position, String itemName) {
        super(position);
        stillTexture = Assets.getInstance().getTexture(itemName);
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
