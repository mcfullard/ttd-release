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
    private static final int FLEE_RADIUS = 10;

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

    public void flee() {
        PathFinder pf = getPathFinder();
        this.setDirection(
                pf.shortestPathTo(
                        position,
                        PathFinder.getRandomPosition(
                                pf.getWithinRadiusOf(
                                        getThomas().getPosition(),
                                        FLEE_RADIUS
                                )
                        )
                )
        );
    }

    public void die() {
        alive = false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean handleMessage(Telegram msg) {
        boolean result = false;
        if(msg.message == MessageType.TOOTHBRUSH_COLLECTED) {
            toothDecayStateMachine.handleMessage(msg);
            result = true;
        }
        return super.handleMessage(msg) && result;
    }
}
