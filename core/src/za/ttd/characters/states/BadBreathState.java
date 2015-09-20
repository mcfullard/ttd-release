package za.ttd.characters.states;

import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.BadBreath;
import com.badlogic.gdx.ai.fsm.State;

/**
 * @author minnaar
 * @since 2015/09/18.
 */
public enum BadBreathState implements State<BadBreath> {
    CHASE {
        @Override
        public void enter(BadBreath badBreath) {
            badBreath.chase();
            badBreath.incNumbersChasing();
        }

        @Override
        public void update(BadBreath badBreath) {
            if (badBreath.getNumberChasing() >= 2) {
                badBreath.getBadBreathStateMachine().changeState(DECEIVE);
            }
        }

        @Override
        public void exit(BadBreath badBreath) {
            badBreath.decNumberChasing();
        }
    },
    DECEIVE {
        @Override
        public void enter(BadBreath badBreath) {
            badBreath.deceive();
        }

        @Override
        public void update(BadBreath badBreath) {
            if (badBreath.getThomasNear())
                badBreath.getBadBreathStateMachine().changeState(CHASE);
        }

        @Override
        public boolean onMessage(BadBreath badBreath, Telegram telegram) {
            if (telegram.message == MessageType.TOOTHBRUSH_COLLECTED) {
                badBreath.getBadBreathStateMachine().changeState(DEFEND);
                return true;
            }
            return false;
        }
    },
    DEFEND {
        @Override
        public void enter(BadBreath badBreath) {
            badBreath.defend();
        }

        @Override
        public void update(BadBreath badBreath) {

        }
    },
    FLEE {
        @Override
        public void enter(BadBreath badBreath) {
            badBreath.flee();
        }
    },
    DIE {
        @Override
        public void enter(BadBreath badBreath) {
            badBreath.die();
        }

        @Override
        public void exit(BadBreath badBreath) {
            badBreath.reset();
        }
    };

    @Override
    public void update(BadBreath badBreath) {

    }

    @Override
    public void exit(BadBreath badBreath) {

    }

    @Override
    public boolean onMessage(BadBreath badBreath, Telegram telegram) {
        if (telegram.message == MessageType.MOUTHWASH_EXPIRED || telegram.message == MessageType.LEVEL_RESET) {
            badBreath.getBadBreathStateMachine().changeState(CHASE);
            return true;
        }
        else
            return false;
    }
}
