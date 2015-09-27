package za.ttd.characters;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Direction;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Assets;
import za.ttd.renderers.Renderable;

import java.util.Map;

public abstract class Actor extends InGameObject
        implements Renderable, Telegraph {

    private Animation currentAnimation, animationU, animationD, animationL, animationR, animationI;
    private Assets assets;
    protected float movementSpeed;
    protected Map<Position, InGameObject> gameItems;
    protected Direction direction;

    private final float defaultX, defaultY;

    public Actor(Position position, float movementSpeed, String actorName) {
        super(position);
        defaultX = position.getX();
        defaultY = position.getY();

        this.movementSpeed = movementSpeed;

        assets = Assets.getInstance();
        direction = Direction.NONE;

        //Get animations
        animationI = assets.getAnimation(actorName, "Idle");
        animationU = assets.getAnimation(actorName, "Up");
        animationD = assets.getAnimation(actorName, "Down");
        animationL = assets.getAnimation(actorName, "Left");
        animationR = assets.getAnimation(actorName, "Right");

        currentAnimation = animationI;
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
        return null;
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
            default: currentAnimation = animationI;
                break;
        }
    }

    /*Reset this Actors position to it's default position,
    * Bring this Actor back to life*/
    public void revive() {
        currentAnimation = animationI;
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
