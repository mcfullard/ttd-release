package za.ttd.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.states.MessageType;

public class Player implements Telegraph {

    private int playerID;
    private String name;
    private int highestScore, highestLevel;
    private int lives;
    private byte[] salt, hash;
    public ScoringSystem scoring;
    public Controls controls;
    private static Player instance = null;

    private Player() {
        this.scoring = new ScoringSystem();
        this.controls = new Controls();
        registerSelfAsListener();
    }

    /*
    public Player(String name, int highestScore, int highestLevel, int lives) {
        this.name = name;
        this.highestScore = highestScore;
        this.highestLevel = highestLevel;
        this.lives = lives;
        this.scoring = new ScoringSystem();
        this.controls = new Controls();
        controls.defaultControls();
        scoring.setTotals();
        registerSelfAsListener();
    }

    public Player(String name, int highestScore, int highestLevel, int lives, int playerID, byte[] salt, byte[] hash) {
        this.name = name;
        this.highestScore = highestScore;
        this.highestLevel = highestLevel;
        this.lives = lives;
        this.playerID = playerID;
        this.salt = salt;
        this.hash = hash;
        this.scoring = new ScoringSystem();
        this.controls = new Controls();
        registerSelfAsListener();
    }
    */

    public static Player getInstance() {
        if(instance == null) {
            instance = new Player();
        }
        return instance;
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

    public int getHighestScore() {
        if (scoring.totScore > highestScore)
            highestScore = scoring.totScore;
        return highestScore;
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

    public void reset(){
        lives = 3;
        highestLevel = 1;
        scoring.totScore = 0;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.THOMAS_LOSES_LIFE:
                --lives;
                if (lives > 0) {
                    scoring.lifeUsed();
                    MessageManager.getInstance().dispatchMessage(this, MessageType.LEVEL_RESET);
                } else {
                    MessageManager.getInstance().dispatchMessage(this, MessageType.GAME_OVER);
                }
                return true;
            case MessageType.NEXT_LEVEL:
            case MessageType.UPDATE_DB:
                scoring.calcTotScore();
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

        private int lvlScore, totScore, totPowersUsed, totLivesUsed, totCollectiblesFound, totBadBreathKilled;
        private final int collectibleValue = 5, powerUpValue = 10, badBreathValue = 100, lifeValue = 200, toothDecayValue = 300;
        private int powersUsed, badBreathKilled, collectiblesFound, livesUsed, toothDecayDestroyed;

        public ScoringSystem() {
            lvlScore = 0;
            powersUsed = 0;
            badBreathKilled = 0;
        }

        public void setTotals() {
            totCollectiblesFound = 0;
            totLivesUsed = 0;
            totBadBreathKilled = 0;
            totCollectiblesFound = 0;
            totScore = 0;
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

        public void setTotScore(int totScore) {
            this.totScore = totScore;
        }

        public int getTotScore() {
            calcTotScore();
            return totScore;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /*Calculators*/
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

        public void calcTotScore() {
            totScore += lvlScore;
        }
    }

    public class Controls{

        private int Up, Down, Left, Right;

        public void defaultControls() {
            Up = Input.Keys.UP;
            Down = Input.Keys.DOWN;
            Left = Input.Keys.LEFT;
            Right = Input.Keys.RIGHT;
        }

        public int getUp() {
            return Up;
        }

        public void setUp(int up) {
            Up = up;
        }

        public int getDown() {
            return Down;
        }

        public void setDown(int down) {
            Down = down;
        }

        public int getLeft() {
            return Left;
        }

        public void setLeft(int left) {
            Left = left;
        }

        public int getRight() {
            return Right;
        }

        public void setRight(int right) {
            Right = right;
        }
    }
}
