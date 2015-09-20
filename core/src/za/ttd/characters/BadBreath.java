package za.ttd.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.BadBreathState;

public class BadBreath extends Enemy {

    private StateMachine<BadBreath> badBreathStateMachine;
    private static int numberChasing;

    public BadBreath(Position position, float speed, String actorName) {
        super(position, speed, actorName);
        numberChasing = 0;
        badBreathStateMachine = new DefaultStateMachine(this, BadBreathState.CHASE);
    }

    public void update() {
        super.update();
        badBreathStateMachine.update();
    }

    ////////////////////////////////////////////State controls//////////////////////////////////////////////////////////
    public void chase() {

    }

    public void deceive() {

    }

    public void flee() {

    }

    public void die() {
        alive = false;
    }

    public void defend() {

    }

    public boolean getThomasNear() {
        return false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int getNumberChasing() {
        return numberChasing;
    }

    public void incNumbersChasing() {
        ++numberChasing;
    }

    public void decNumberChasing() {
        --numberChasing;
    }

    public StateMachine<BadBreath> getBadBreathStateMachine() {
        return badBreathStateMachine;
    }

    public boolean getVulnerability() {
        return super.vulnerable;
    }

}
