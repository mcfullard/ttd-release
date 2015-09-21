package za.ttd.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.MessageType;
import za.ttd.characters.states.ToothDecayState;
import za.ttd.pathfinding.PathFinder;

public class ToothDecay extends Enemy {

    private static final int NEAR_THOMAS = 5;
    private StateMachine<ToothDecay> toothDecayStateMachine;
    private static final int FLEE_RADIUS = 10;
    private final int UPDATE_COUNT_LIMIT = 250;
    private int updateCount = 0;
    private Position lastRandomDestination = new Position(1,1);

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
        updateCount++;
        if(updateCount >= UPDATE_COUNT_LIMIT) {
            while(lastRandomDestination.getDistanceTo(thomas.getPosition()) < NEAR_THOMAS &&
                    lastRandomDestination.equals(new Position(1,1))) {
                lastRandomDestination = PathFinder.getRandomPosition(
                        pf.getWithinRadiusOf(
                                getThomas().getPosition(),
                                FLEE_RADIUS
                        )
                );
            }
            updateCount = 0;
        }
        this.setDirection(
                pf.shortestPathTo(
                        position,
                        lastRandomDestination
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
        switch (msg.message) {
            case MessageType.TOOTHBRUSH_COLLECTED:
            case MessageType.LEVEL_RESET:
                toothDecayStateMachine.handleMessage(msg);
                result = true;
        }

        return super.handleMessage(msg) && result;
    }
}
