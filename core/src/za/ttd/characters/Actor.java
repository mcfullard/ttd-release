package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Direction;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.MessageType;
import za.ttd.renderers.Renderable;

import java.util.Map;

public abstract class Actor extends InGameObject
        implements Renderable, Telegraph
{

    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureRegion stillTexture;
    private TextureAtlas textureAtlas;
    private Animation currentAnimation, animationR, animationL, animationU, animationD;
    protected float movementSpeed;
    protected Map<Position, InGameObject> gameItems;

    protected Direction direction;

    private final float defaultX, defaultY;

    public Actor(Position position, float movementSpeed, String actorName) {
        super(position);
        defaultX = position.getX();
        defaultY = position.getY();

        this.movementSpeed = movementSpeed;

        direction = Direction.NONE;

        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        stillTexture = textureAtlas.findRegion(String.format("characters/%s0", actorName));
        //create animations
        currentAnimation = null; //Will change depending on direction of player

        //Replace up and down references with correct for up and down (currently don't exist)
        animationU = new Animation(1/8f,
                textureAtlas.findRegion(String.format("characters/%sU1", actorName)),
                textureAtlas.findRegion(String.format("characters/%sU2", actorName)),
                textureAtlas.findRegion(String.format("characters/%sU3", actorName)));
        animationD = new Animation(1/8f,
                textureAtlas.findRegion(String.format("characters/%sD1", actorName)),
                textureAtlas.findRegion(String.format("characters/%sD2", actorName)),
                textureAtlas.findRegion(String.format("characters/%sD3", actorName)));
        animationL = new Animation(1/8f,
                textureAtlas.findRegion(String.format("characters/%sL1", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL2", actorName)),
                textureAtlas.findRegion(String.format("characters/%sL3", actorName)));
        animationR = new Animation(1/8f,
                textureAtlas.findRegion(String.format("characters/%sR1", actorName)),
                textureAtlas.findRegion(String.format("characters/%sR2", actorName)),
                textureAtlas.findRegion(String.format("characters/%sR3", actorName)));
    }

    public void update() {
        chooseAnimation();
    }

    //////////////////////////////////////////Getter and Setter methods/////////////////////////////////////////////////
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public Map<Position, InGameObject> getGameItems() {
        return gameItems;
    }

    @Override
    public TextureRegion getTexture() {
        return stillTexture;
    }

    @Override
    public Animation getAnimation() {
        return currentAnimation;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Kill this actor
    public void kill() {
        alive = false;
    }

    /*
    * Change the current animation depending on the direction the character is moving*/
    private void chooseAnimation() {
        switch (direction) {
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

    /*Reset this Actors position to it's default position,
    * Bring this Actor back to life*/
    public void revive() {
        currentAnimation = null;
        direction = Direction.NONE;
        position.setX(defaultX);
        position.setY(defaultY);
        alive = true;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        if(msg.message == MessageType.SEND_ITEMS) {
            if(msg.extraInfo != null) {
                gameItems = (Map<Position, InGameObject>) msg.extraInfo;
                return true;
            }
        }
        return false;
    }
}
