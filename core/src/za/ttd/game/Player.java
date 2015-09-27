package za.ttd.game;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.states.MessageType;

public class Player implements Telegraph {

    private int ID;
    private String name;
    private int totScore, highestLevel;
    private int lives;
    public ScoringSystem scoring;

    public Player(String name, int highestScore, int highestLevel, int lives) {
        this.name = name;
        this.totScore = highestScore;
        this.highestLevel = highestLevel;
        this.lives = lives;
        this.scoring = new ScoringSystem();
        registerSelfAsListener();
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListeners(this,
                MessageType.THOMAS_LOSES_LIFE,
                MessageType.BADBREATH_DEAD,
                MessageType.TOOTHDECAY_DEAD);
    }

    public int getTotScore() {
        return totScore;
    }

    public void setTotScore(int highestScore) {
        this.totScore = highestScore;
    }

    public int getHighestLevel() {
        return highestLevel;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setHighestLevel(int highestLevel) {
        this.highestLevel = highestLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void incHighestLevel() {
        ++highestLevel;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.THOMAS_LOSES_LIFE:
                --lives;
                if (this.lives > 0) {
                    scoring.lifeUsed();
                    MessageManager.getInstance().dispatchMessage(this, MessageType.LEVEL_RESET);
                } else {
                    MessageManager.getInstance().dispatchMessage(this, MessageType.GAME_OVER);
                }
                return true;
            case MessageType.BADBREATH_DEAD:
                scoring.killedBadBreath();
                return true;
            case MessageType.TOOTHDECAY_DEAD:
                scoring.killedToothDecay();
                return true;
            case MessageType.PLAQUE_COLLECTED:
                scoring.collectibleFound();
                return true;
            case MessageType.MOUTHWASH_COLLECTED:
                scoring.powerUsed();
                return true;
        }
        return false;
    }

    public class ScoringSystem {

        private int lvlScore, totPowersUsed, totLivesUsed, totCollectiblesFound, totBadBreathKilled;
        private final int collectibleValue = 5, powerUpValue = 10, badBreathValue = 100, lifeValue = 200, toothDecayValue = 300;
        private int powersUsed, badBreathKilled, collectiblesFound, livesUsed, toothDecayDestroyed;

        public ScoringSystem() {
            lvlScore = 0;
            powersUsed = 0;
            badBreathKilled = 0;
            collectiblesFound = 0;
            totLivesUsed = 0;
            totBadBreathKilled = 0;
            totCollectiblesFound = 0;
        }

        public void collectibleFound() {
            ++collectiblesFound;
            ++totCollectiblesFound;
        }

        public void powerUsed() {
            ++powersUsed;
            ++totPowersUsed;
        }

        public void killedBadBreath() {
            ++badBreathKilled;
            ++totBadBreathKilled;
        }

        public void killedToothDecay() {
            ++toothDecayDestroyed;
        }

        public void lifeUsed() {
            ++livesUsed;
            ++totLivesUsed;
        }

        //////////////////////////////////////////////////////Getters///////////////////////////////////////////////////
        /*
        * @return the current score of the player for this level*/
        public int getLvlScore() {
            calcLvlScore();
            return lvlScore;
        }

        public int getTotLivesUsed() {
            return totLivesUsed;
        }

        public int getTotCollectiblesFound() {
            return totCollectiblesFound;
        }

        public int getTotBadBreathKilled() {
            return totBadBreathKilled;
        }

        public int getTotPowersUsed() {
            return totPowersUsed;
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /*
        * calculate to score for the current level*/
        private void calcLvlScore() {
            lvlScore = collectibleValue * collectiblesFound
                    + badBreathValue * badBreathKilled
                    + toothDecayDestroyed * toothDecayValue
                    - powerUpValue * powersUsed
                    - livesUsed * lifeValue;

            if (lvlScore < 0) {
                lvlScore = 0;
                livesUsed = 0;
                powersUsed = 0;
                collectiblesFound = 0;
                badBreathKilled = 0;
            }
        }
    }
}
