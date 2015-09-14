package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Direction;
import za.ttd.characters.objects.Position;
import za.ttd.renderers.Renderable;

public abstract class Actor extends InGameObject implements Renderable {

    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureRegion stillTexture;
    private TextureAtlas textureAtlas;
    private Animation currentAnimation, animationR, animationL, animationU, animationD;

    protected Direction curDirection, nextDirection;

    private final float x, y;

    public Actor(Position position, String actorName) {
        super(position);
        x = position.getX();
        y = position.getY();

        curDirection = Direction.NONE;
        nextDirection = Direction.NONE;

        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        stillTexture = textureAtlas.findRegion(String.format("characters/%sL1", actorName));
        //create animations
        currentAnimation = null; //Will change depending on direction of player

        //Replace up and down references with correct for up and down (currently don't exist)
        animationU = new Animation(1/4f,
                textureAtlas.findRegion(String.format("characters/%sL1", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL2", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL3", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL4", actorName)));
        animationD = new Animation(1/4f,
                textureAtlas.findRegion(String.format("characters/%sL1", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL2", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL3", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL4", actorName)));
        animationL = new Animation(1/4f,
                textureAtlas.findRegion(String.format("characters/%sL1", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL2", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL3", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL4", actorName)));
        animationR = new Animation(1/4f,
                textureAtlas.findRegion(String.format("characters/%sR1", actorName)),
                textureAtlas.findRegion(String.format("characters/%sR2", actorName)),
                textureAtlas.findRegion(String.format("characters/%sR3", actorName)),
                textureAtlas.findRegion(String.format("characters/%sR4", actorName)));
    }

    public void update() {
        chooseAnimation();
    }

    public Direction getCurDirection() {
        return curDirection;
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public int getIntX() {
        return position.getIntX();
    }

    public int getIntY() {
        return position.getIntY();
    }

    @Override
    public TextureRegion getTexture() {
        return stillTexture;
    }

    @Override
    public Animation getAnimation() {
        return currentAnimation;
    }

    /*
    * Change the current animation depending on the direction the character is moving*/
    private void chooseAnimation() {
        switch (curDirection) {
            case UP: currentAnimation = animationU;
                break;
            case DOWN: currentAnimation = animationD;
                break;
            case LEFT: currentAnimation = animationL;
                break;
            case RIGHT: currentAnimation = animationR;
                break;
            default: currentAnimation = null;
                break;
        }
    }

    public void resetPositions() {
        position.setX(x);
        position.setY(y);
    }
}
