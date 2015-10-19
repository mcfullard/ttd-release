package za.ttd.characters.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.BadBreath;

/**
 * @author minnaar
 * @since 2015/09/18.
 */
public enum BadBreathState implements State<BadBreath> {
    CHASE {
        @Override
        public void enter(BadBreath badBreath) {
            super.enter(badBreath);
        }

        @Override
        public void update(BadBreath badBreath) {
            if (!badBreath.getThomasNear()) {
                badBreath.getBadBreathStateMachine().changeState(DECEIVE);
            }
            if(badBreath.getChance()) {
                badBreath.getBadBreathStateMachine().changeState(DEFEND);
            }
            super.update(badBreath);
            badBreath.chase();
        }

        @Override
        public boolean onMessage(BadBreath badBreath, Telegram telegram) {
            boolean status = super.onMessage(badBreath, telegram);

            if (!status && telegram.message == MessageType.MOUTHWASH_COLLECTED) {
                badBreath.getBadBreathStateMachine().changeState(FLEE);
                return true;
            }
            return status;
        }
    },
    DECEIVE {
        @Override
        public void update(BadBreath badBreath) {
            if(badBreath.getThomasNear()) {
                badBreath.getBadBreathStateMachine().changeState(CHASE);
            }
            super.update(badBreath);
            badBreath.deceive();
        }

        @Override
        public boolean onMessage(BadBreath badBreath, Telegram telegram) {
            boolean status = super.onMessage(badBreath, telegram);

            if (!status && telegram.message == MessageType.TOOTHBRUSH_COLLECTED) {
                badBreath.getBadBreathStateMachine().changeState(DEFEND);
                return true;
            }
            else if (!status && telegram.message == MessageType.MOUTHWASH_COLLECTED) {
                badBreath.getBadBreathStateMachine().changeState(FLEE);
                return true;
            }
            else
                return status;
        }
    },
    DEFEND {

        @Override
        public void enter(BadBreath badBreath) {
            badBreath.setCounter(BadBreath.COUNT_LIMIT);
        }

        @Override
        public void update(BadBreath badBreath) {
            if (badBreath.getThomasNear()) {
                badBreath.getBadBreathStateMachine().changeState(CHASE);
            }
            super.update(badBreath);
            badBreath.defend();
        }

        @Override
        public boolean onMessage(BadBreath badBreath, Telegram telegram) {
            boolean status = super.onMessage(badBreath, telegram);
            if (!status && telegram.message == MessageType.MOUTHWASH_COLLECTED) {
                badBreath.getBadBreathStateMachine().changeState(FLEE);
                return true;
            }
            else
                return status;
        }
    },
    FLEE {
        @Override
        public void enter(BadBreath badBreath) {
            badBreath.vulAnimationSet();
        }

        @Override
        public void update(BadBreath badBreath) {
            badBreath.flee();
            if(badBreath.collided(badBreath.getThomas().getPosition()))
                badBreath.getBadBreathStateMachine().changeState(DIE);
        }

        @Override
        public void exit(BadBreath badBreath) {
            badBreath.normAnimationSet();
        }

        @Override
        public boolean onMessage(BadBreath badBreath, Telegram telegram) {
            boolean status = super.onMessage(badBreath, telegram);
            if(!status) {
                switch (telegram.message) {
                    case MessageType.TOOTHBRUSH_COLLECTED:
                        badBreath.getBadBreathStateMachine().changeState(DEFEND);
                        return true;
                    case MessageType.MOUTHWASH_EXPIRED:
                        badBreath.getBadBreathStateMachine().revertToPreviousState();
                        return true;
                }
            }
            return status;
        }
    },
    DIE {
        @Override
        public void enter(BadBreath badBreath) {
            MessageManager.getInstance().dispatchMessage(badBreath, MessageType.BADBREATH_DEAD);
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
    public void enter(BadBreath entity) {

    }

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
        else
            return false;
    }
}
