package za.ttd.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.EnemySpeedState;
import za.ttd.characters.states.MessageType;

public abstract class Enemy extends Actor {
    protected float defaultSpeed;
    protected boolean vulnerable;
    protected Thomas thomas;
    private StateMachine<Enemy> speedStateMachine;

    public Enemy(Position position, float speed, String actorName) {
        super(position, speed, actorName);
        this.defaultSpeed = speed;
        speedStateMachine = new DefaultStateMachine(this, EnemySpeedState.NORMAL);
        vulnerable = false;
    }

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
        super.movementSpeed = defaultSpeed*.8f;
    }

    public void normalSpeed() {
        super.movementSpeed = defaultSpeed;
    }

    public void speedUp() {
        super.movementSpeed = defaultSpeed*1.2f;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Thomas getThomas() {
        return thomas;
    }

    public void killThomas() {
        thomas.kill();
    }

    public boolean collided(Position checkPos) {
        return position.compareBase(checkPos);
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
            case MessageType.TOOTHBRUSH_COLLECTED:
                result = true;
                break;
            case MessageType.MOUTHWASH_COLLECTED:
                result = true;
                break;
            case MessageType.MOUTHWASH_EXPIRED:
                result = true;
                break;
        }
        return super.handleMessage(msg) && result;
    }
}
