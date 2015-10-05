package za.ttd.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.states.MessageType;

public class Player implements Telegraph {

    private int playerID;
    private String name;
    private int totScore, highestLevel;
    private int lives;
    private byte[] salt, hash;
    public ScoringSystem scoring;
    public Controls controls;

    public Player(String name, int highestScore, int highestLevel, int lives) {
        this.name = name;
        this.totScore = highestScore;
        this.highestLevel = highestLevel;
        this.lives = lives;
        this.scoring = new ScoringSystem();
        this.controls = new Controls();
        registerSelfAsListener();
    }

    public Player(String name, int highestScore, int highestLevel, int lives, int playerID, byte[] salt, byte[] hash) {
        this.name = name;
        this.totScore = highestScore;
        this.highestLevel = highestLevel;
        this.lives = lives;
        this.playerID = playerID;
        this.salt = salt;
        this.hash = hash;
        this.scoring = new ScoringSystem();
        registerSelfAsListener();
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListeners(this,
                MessageType.THOMAS_LOSES_LIFE,
                MessageType.BADBREATH_DEAD,
                MessageType.MOUTHWASH_COLLECTED,
                MessageType.PLAQUE_COLLECTED,
                MessageType.TOOTHDECAY_DEAD,
                MessageType.UPDATE_DB,
                MessageType.NEXT_LEVEL
        );
    }

    public int getTotScore() {
        if (scoring.curTotScore > totScore)
            totScore = scoring.curTotScore;
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
                    scoring.curTotScore = 0;
                    MessageManager.getInstance().dispatchMessage(this, MessageType.GAME_OVER);
                }
                return true;
            case MessageType.NEXT_LEVEL:
            case MessageType.UPDATE_DB:
                scoring.calcCurTotScore();
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

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getHash() {
        return hash;
    }

    public class ScoringSystem {

        private int lvlScore, curTotScore, totPowersUsed, totLivesUsed, totCollectiblesFound, totBadBreathKilled;
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

        public void setTotLivesUsed(int totLivesUsed) {
            this.totLivesUsed = totLivesUsed;
        }

        public void setTotCollectiblesFound(int totCollectiblesFound) {
            this.totCollectiblesFound = totCollectiblesFound;
        }

        public void setTotBadBreathKilled(int totBadBreathKilled) {
            this.totBadBreathKilled = totBadBreathKilled;
        }

        public void setTotPowersUsed(int totPowersUsed) {
            this.totPowersUsed = totPowersUsed;
        }

        public void setLvlScore(int lvlScore) {
            this.lvlScore = lvlScore;
        }

        public int getCurTotScore() {
            return curTotScore;
        }

        public void setCurTotScore(int curTotScore) {
            this.curTotScore = curTotScore;
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

        public void calcCurTotScore() {
            curTotScore += lvlScore;
        }
    }

    public static class Controls{

        public static int UP, DOWN, LEFT, RIGHT;

        public Controls() {
        }

        public void defaultControls() {
            UP = Input.Keys.UP;
            DOWN = Input.Keys.DOWN;
            LEFT = Input.Keys.LEFT;
            RIGHT = Input.Keys.RIGHT;
        }
    }
}
