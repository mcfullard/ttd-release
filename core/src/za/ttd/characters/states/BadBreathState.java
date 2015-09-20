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
            super.update(badBreath);

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
    },
    FLEE {
        @Override
        public void enter(BadBreath badBreath) {
            badBreath.flee();
        }

        @Override
        public void update(BadBreath badBreath) {
            if(badBreath.collided(badBreath.getThomas().getPosition()))
                badBreath.getBadBreathStateMachine().changeState(DIE);
        }

        @Override
        public boolean onMessage(BadBreath badBreath, Telegram telegram) {
            boolean state = super.onMessage(badBreath, telegram);

            if (!state && telegram.message == MessageType.MOUTHWASH_EXPIRED) {
                badBreath.getBadBreathStateMachine().changeState(CHASE);
                return true;
            }
            else
                return state;
        }
    },
    DIE {
        @Override
        public void enter(BadBreath badBreath) {
            badBreath.die();
        }

        @Override
        public void update(BadBreath badBreath) {
        }

        @Override
        public void exit(BadBreath badBreath) {
            badBreath.revive();
        }
    };

    @Override
    public void update(BadBreath badBreath) {
        if (badBreath.collided(badBreath.getThomas().getPosition()))
            badBreath.killThomas();
    }

    @Override
    public void exit(BadBreath badBreath) {

    }

    @Override
    public boolean onMessage(BadBreath badBreath, Telegram telegram) {
        if (telegram.message == MessageType.LEVEL_RESET) {
            badBreath.getBadBreathStateMachine().changeState(CHASE);
            return true;
        }
        else if (telegram.message == MessageType.MOUTHWASH_COLLECTED) {
            badBreath.getBadBreathStateMachine().changeState(FLEE);
            return true;
        }
        else
            return false;
    }
}
