package za.ttd.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.MessageType;
import za.ttd.characters.states.ToothDecayState;
import za.ttd.pathfinding.PathFinder;

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
        this.setDirection(
                getPathFinder()
                        .shortestPathTo(position, getThomas().getPosition())
        );
    }

    @Override
    public void flee() {
        super.flee();
    }

    public void die() {
        alive = false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean handleMessage(Telegram msg) {
        boolean result = false;
        switch (msg.message) {
            case MessageType.TOOTHBRUSH_COLLECTED:
            case MessageType.LEVEL_RESET:
                toothDecayStateMachine.handleMessage(msg);
                result = true;
        }

        return super.handleMessage(msg) && result;
    }
}
