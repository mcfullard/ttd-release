package za.ttd.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.ToothDecayState;

public class ToothDecay extends Enemy {

    private StateMachine<ToothDecay> toothDecayStateMachine;

    public ToothDecay(Position position, float speed){
        super(position, speed, "ToothDecay");
        toothDecayStateMachine = new DefaultStateMachine<ToothDecay>(this, ToothDecayState.CHASE);
    }

    @Override
    public void update() {
        super.update();
        toothDecayStateMachine.update();
    }

    public StateMachine<ToothDecay> getToothDecayStateMachine() {
        return toothDecayStateMachine;
    }

    ////////////////////////////////////////////State controls//////////////////////////////////////////////////////////
    public void chase() {

    }

    public void flee() {

    }

    public void die() {
        alive = false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
