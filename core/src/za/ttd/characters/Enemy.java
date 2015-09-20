package za.ttd.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.EnemySpeedState;
import za.ttd.characters.states.MessageType;

public abstract class Enemy extends Actor {
    protected float defaultSpeed;
    protected boolean vulnerable, killable;
    protected Player thomas;
    private StateMachine<Enemy> speedStateMachine;

    public Enemy(Position position, float speed, String actorName) {
        super(position, speed, actorName);
        this.defaultSpeed = speed;
        speedStateMachine = new DefaultStateMachine(this, EnemySpeedState.NORMAL);
        vulnerable = false;
        killable = false;
    }

    public void update() {
        speedStateMachine.update();
    }

    public StateMachine<Enemy> getSpeedStateMachine() {
        return speedStateMachine;
    }

    /*
    * Make object to vulnerable or no longer vulnerable,
    * make it move slower/or reset to original speed,
    * being allows tomas to killable it*/
    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;

        if (vulnerable) {
            slow();
            //Play different animations
        }
        else
            super.movementSpeed = defaultSpeed;
    }
    /*
    * @return the current state of enemies vulnerability*/
    public boolean getVulnerability() {
        return vulnerable;
    }

    public boolean getKillable() {
        return killable;
    }

    public void setKillable(boolean killable) {
        this.killable = killable;
    }

    public void slow() {
        super.movementSpeed = defaultSpeed*.8f;
    }

    public void normalSpeed() {
        if (vulnerable)
            slow();
        else
            super.movementSpeed = defaultSpeed;
    }

    public void speedUp() {
        if (vulnerable)
            super.movementSpeed = defaultSpeed;
        else
            super.movementSpeed = defaultSpeed*1.2f;
    }

    public boolean collided(Position checkPos) {
        return position.compareBase(checkPos);
    }

    @Override
    public void reset() {
        super.reset();
        this.vulnerable = false;
        this.killable = false;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch(msg.message){
            case MessageType.SEND_THOMAS:
                if(msg.extraInfo != null)
                    thomas = (Player) msg.extraInfo;
                return true;
            case MessageType.TOOTHBRUSH_COLLECTED:
                return true;
            case MessageType.MOUTHWASH_COLLECTED:
                return true;
            case MessageType.MOUTHWASH_EXPIRED:
                return true;
        }
        return false;
    }
}
