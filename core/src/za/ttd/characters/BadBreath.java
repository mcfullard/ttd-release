package za.ttd.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.BadBreathState;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Player;
import za.ttd.pathfinding.PathFinder;

import java.util.Random;

public class BadBreath extends Enemy {
    private static final int DECEIVE_RADIUS = 4;
    private static final int DEFEND_RADIUS = 1;
    public static final int COUNT_LIMIT = 10;
    private static final int NEAR_DISTANCE = 5;
    private static final int CHANCE_UPPER_BOUND = 10;
    private StateMachine<BadBreath> badBreathStateMachine;
    private int counter = 0;
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
        counter++;
        if(counter >= COUNT_LIMIT) {
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
            counter = 0;
        }
    }

    @Override
    public void flee() {
        super.flee();
    }

    public void die() {
        alive = false;
    }

    public void defend() {
        counter++;
        if(counter >= COUNT_LIMIT) {
            PathFinder pf = getPathFinder();
            this.setDirection(
                    pf.shortestPathTo(
                            position,
                            PathFinder.getRandomPosition(
                                    pf.getWithinRadiusOf(
                                            toothDecay.getPosition(),
                                            DEFEND_RADIUS
                                    )
                            )
                    )
            );
            counter = 0;
        }
    }

    public boolean getChance() {
        boolean result = false;
        counter++;
        if(counter >= COUNT_LIMIT) {
            int lowerBound = Player.getInstance().getHighestLevel();
            int range = CHANCE_UPPER_BOUND - lowerBound + 1;
            Random random = new Random(lowerBound);
            result = random.nextInt((range < 1 ? 1 : range)) == 0;
            counter = 0;
        }
        return result;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean getThomasNear() {
        return position.getDistanceTo(getThomas().getPosition()) <= NEAR_DISTANCE;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public StateMachine<BadBreath> getBadBreathStateMachine() {
        return badBreathStateMachine;
    }

    public boolean getVulnerability() {
        return super.vulnerable;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        boolean result = false;
        switch(msg.message) {
            case MessageType.SEND_TOOTHDECAY:
                if (msg.extraInfo != null) {
                    toothDecay = (ToothDecay) msg.extraInfo;
                    result = true;
                }
                break;
            case MessageType.TOOTHBRUSH_COLLECTED:
            case MessageType.MOUTHWASH_COLLECTED:
            case MessageType.MOUTHWASH_EXPIRED:
            case MessageType.LEVEL_RESET:
                badBreathStateMachine.handleMessage(msg);
                break;
        }
        return super.handleMessage(msg) && result;
    }
}
