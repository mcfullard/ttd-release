package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Direction;
import za.ttd.characters.objects.Movement;
import za.ttd.characters.objects.Position;
import za.ttd.mapgen.Map;

public class Player extends Actor {

    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureRegion stillTexture;
    private TextureAtlas textureAtlas;
    private Animation currentAnimation, animationR, animationL, animationU, animationD;

    private Movement movement;
    private Direction curDirection, nextDirection;

    public Player(Position position, float speed) {
        super(position);
        setMovementSpeed(speed);
        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        this.movement = new Movement(null, position, speed);
        curDirection = Direction.NONE;
        nextDirection = Direction.NONE;

        stillTexture = textureAtlas.findRegion("characters/ThomL1");
        //create animations
        currentAnimation = null; //Will change depending on direction of player

        //Replace up and down references with correct for up and down (currently don't exist)
        animationU = new Animation(1/4f,
                textureAtlas.findRegion("characters/ThomL1"),
                textureAtlas.findRegion("characters/ThomL1"),
                textureAtlas.findRegion("characters/ThomL1"),
                textureAtlas.findRegion("characters/ThomL1"));
        animationD = new Animation(1/4f,
                textureAtlas.findRegion("characters/ThomL1"),
                textureAtlas.findRegion("characters/ThomL1"),
                textureAtlas.findRegion("characters/ThomL1"),
                textureAtlas.findRegion("characters/ThomL1"));
        animationL = new Animation(1/4f,
                textureAtlas.findRegion("characters/ThomL1"),
                textureAtlas.findRegion("characters/ThomL2"),
                textureAtlas.findRegion("characters/ThomL3"),
                textureAtlas.findRegion("characters/ThomL4"));
        animationR = new Animation(1/4f,
                textureAtlas.findRegion("characters/ThomR1"),
                textureAtlas.findRegion("characters/ThomR2"),
                textureAtlas.findRegion("characters/ThomR3"),
                textureAtlas.findRegion("characters/ThomR4"));
    }

    public void setMovementMap(Map map) {
        movement.setMap(map);
    }

    @Override
    public void update() {
        processKeys();
        Move();
        chooseAnimation();
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
    * Get input from user*/
    private void processKeys() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            nextDirection = Direction.UP;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            nextDirection = Direction.DOWN;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            nextDirection = Direction.LEFT;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            nextDirection = Direction.RIGHT;

        if (curDirection == Direction.NONE)
            curDirection = nextDirection;
    }

    /*
    * Control movement of character, can remember next key presses*/
    private void Move() {
        switch (nextDirection) {
            case UP:
                if(movement.tryMoveUp(curDirection))
                    curDirection = nextDirection;
                break;
            case DOWN:
                if(movement.tryMoveDown(curDirection))
                    curDirection = nextDirection;
                break;
            case LEFT:
                if(movement.tryMoveLeft(curDirection))
                    curDirection = nextDirection;
                break;
            case RIGHT:
                if(movement.tryMoveRight(curDirection))
                    curDirection = nextDirection;
                break;
            default:
                break;
        }

        switch (curDirection) {
            case UP:
                if (!movement.moveUp())
                    curDirection = Direction.NONE;
                break;
            case DOWN:
                if (!movement.moveDown())
                    curDirection = Direction.NONE;
                break;
            case LEFT:
                if (!movement.moveLeft())
                    curDirection = Direction.NONE;
                break;
            case RIGHT:
                if (!movement.moveRight())
                    curDirection = Direction.NONE;
                break;
            default:
                break;
        }
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
}
