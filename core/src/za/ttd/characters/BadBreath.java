package za.ttd.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.BadBreathState;
import za.ttd.characters.states.MessageType;
import za.ttd.pathfinding.PathFinder;

public class BadBreath extends Enemy {
    private static final int DECEIVE_RADIUS = 4;
    private static final int FLEE_RADIUS = 10;
    private static final int NEAR_DISTANCE = 2;
    private StateMachine<BadBreath> badBreathStateMachine;
    private static int numberChasing = 0;
    protected ToothDecay toothDecay;

    public BadBreath(Position position, float speed, String actorName) {
        super(position, speed, actorName);
        badBreathStateMachine = new DefaultStateMachine(this, BadBreathState.CHASE);
    }

    @Override
    public void update() {
        super.update();
        badBreathStateMachine.update();
    }

    ////////////////////////////////////////////State controls//////////////////////////////////////////////////////////
    public void chase() {
        this.setDirection(
                getPathFinder()
                .shortestPathTo(position, getThomas().getPosition())
        );
    }

    public void deceive() {
        PathFinder pf = getPathFinder();
        this.setDirection(
                pf.shortestPathTo(
                        position,
                        PathFinder.getRandomPosition(
                                pf.getWithinRadiusOf(
                                        getThomas().getPosition(),
                                        DECEIVE_RADIUS
                                )
                        )
                )
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

    public void defend() {
        PathFinder pf = getPathFinder();
        this.setDirection(
                pf.shortestPathTo(
                        position,
                        PathFinder.getRandomPosition(
                                pf.getWithinRadiusOf(
                                        toothDecay.getPosition(),
                                        DECEIVE_RADIUS
                                )
                        )
                )
        );
    }

    public boolean getThomasNear() {
        return position.getDistanceTo(getThomas().getPosition()) <= NEAR_DISTANCE;
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

    @Override
    public boolean handleMessage(Telegram msg) {
        boolean result = false;
        if(msg.message == MessageType.SEND_TOOTHDECAY)
            if(msg.extraInfo != null) {
                toothDecay = (ToothDecay) msg.extraInfo;
                result = true;
            }
        return super.handleMessage(msg) && result;
    }
}
