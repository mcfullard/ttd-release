package za.ttd.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.EnemySpeedState;
import za.ttd.characters.states.MessageType;
import za.ttd.pathfinding.PathFinder;

public abstract class Enemy extends Actor {
    protected float defaultSpeed;
    protected boolean vulnerable;
    protected Thomas thomas;
    protected PathFinder pathFinder;
    private StateMachine<Enemy> speedStateMachine;

    private static final int FLEE_RADIUS = 20;
    private static final int NEAR_THOMAS = 10;
    private final int UPDATE_COUNT_LIMIT = 40;
    private int updateCount = 0;
    private Position lastRandomDestination = new Position(1,1);

    public Enemy(Position position, float speed, String actorName) {
        super(position, speed, actorName);
        this.defaultSpeed = speed;
        speedStateMachine = new DefaultStateMachine(this, EnemySpeedState.NORMAL);
        vulnerable = false;
        registerSelfAsListener();
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListeners(this,
                MessageType.SEND_THOMAS,
                MessageType.SEND_PATHFINDER,
                MessageType.MOUTHWASH_COLLECTED,
                MessageType.MOUTHWASH_EXPIRED
        );
    }

    @Override
    public void update() {
        super.update();
        speedStateMachine.update();
    }

    public StateMachine<Enemy> getSpeedStateMachine() {
        return speedStateMachine;
    }

    /*
    * Make object to vulnerable or no longer vulnerable,
    * allows thomas to kill it*/
    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;

        if (vulnerable) {
            //Change to vulnerable animation set
        }
        else {
            //Change to normal animation set
        }
    }
    /*
    * @return the current state of enemies vulnerability*/
    public boolean getVulnerability() {
        return vulnerable;
    }

    /////////////////////////////////////////////Speed Controls/////////////////////////////////////////////////////////
    public void slow() {
        super.movementSpeed = defaultSpeed*.5f;
    }

    public void normalSpeed() {
        super.movementSpeed = defaultSpeed;
    }

    public void speedUp() {
        super.movementSpeed = defaultSpeed*1.4f;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Thomas getThomas() {
        return thomas;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public void killThomas() {
        thomas.kill();
        MessageManager.getInstance().dispatchMessage(this, MessageType.THOMAS_LOSES_LIFE);
    }

    public boolean collided(Position checkPos) {
        return position.compareBase(checkPos);
    }

    public void flee() {
        PathFinder pf = getPathFinder();
        updateCount++;
        if(updateCount >= UPDATE_COUNT_LIMIT) {
            do {
                lastRandomDestination = PathFinder.getRandomPosition(
                        pf.getWithinRadiusOf(
                                position,
                                FLEE_RADIUS
                        )
                );
            } while(pf.getWithinRadiusOf(thomas.getPosition(), NEAR_THOMAS).contains(lastRandomDestination)
                    && !position.getDirectionTo(lastRandomDestination)
                    .equals(position.getDirectionTo(thomas.getPosition())));
            updateCount = 0;
        }
        this.setDirection(
                pf.shortestPathTo(
                        position,
                        lastRandomDestination
                )
        );
    }

    @Override
    public void revive() {
        super.revive();
        this.vulnerable = false;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        boolean result = false;
        switch(msg.message){
            case MessageType.SEND_THOMAS:
                if(msg.extraInfo != null)
                    thomas = (Thomas) msg.extraInfo;
                result = true;
                break;
            case MessageType.SEND_PATHFINDER:
                if(msg.extraInfo != null)
                    pathFinder = (PathFinder) msg.extraInfo;
                break;
            case MessageType.MOUTHWASH_COLLECTED:
            case MessageType.MOUTHWASH_EXPIRED:
                result = speedStateMachine.handleMessage(msg);
                break;
        }
        return super.handleMessage(msg) && result;
    }
}
