package za.ttd.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.states.MessageType;

import java.util.List;

public class Player implements Telegraph {

    private int playerID;
    private String name;
    private int highestScore, highestLevel;
    private int lives;
    private byte[] salt, hash;
    public ScoringSystem scoring;
    public Controls controls;
    private static Player instance = null;

    private Achievements achievements;

    private Player() {
        this.scoring = new ScoringSystem();
        this.controls = new Controls();
        registerSelfAsListener();
    }

    public static Player getInstance() {
        if(instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public void resetScoringSystem() {
        this.scoring = new ScoringSystem();
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = new Achievements(achievements);
    }

    public List<Achievement> getAchievementsObtained() {
        return achievements.getAchievementsObtained();
    }

    public void updateAchievements() {
        achievements.updateAchievements();
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
                MessageType.NEXT_LEVEL
        );
    }

    public int getHighestScore() {
        if (scoring.currentTotalScore > highestScore)
            highestScore = scoring.currentTotalScore;
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

    public void playerReset(){
        lives = 3;
        highestLevel = 1;
        scoring.currentTotalScore = 0;
        levelReset();
    }

    public void levelReset() {
        scoring.currentLvlScore = 0;
        scoring.currentNumBadBreathKilled = 0;
        scoring.currentNumCollectiblesFound = 0;
        scoring.currentNumPowersUsed = 0;
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
                    scoring.lifeUsed();
                    scoring.calcTotScore();
                    achievements.updateAchievements();
                    MessageManager.getInstance().dispatchMessage(this, MessageType.GAME_OVER);
                }
                return true;
            case MessageType.NEXT_LEVEL:
                achievements.updateAchievements();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public class ScoringSystem {

        private int currentLvlScore, currentTotalScore, totPowersUsed, totLivesUsed, totCollectiblesFound, totBadBreathKilled;
        private final int collectibleValue = 5, powerUpValue = 10, badBreathValue = 100, lifeValue = 100, toothDecayValue = 300;
        private int currentNumPowersUsed, currentNumBadBreathKilled, currentNumCollectiblesFound, currentNumLivesUsed, currentNumToothDecayDestroyed;

        public void collectibleFound() {
            ++currentNumCollectiblesFound;
            ++totCollectiblesFound;
        }

        public void powerUsed() {
            ++currentNumPowersUsed;
            ++totPowersUsed;
        }

        public void killedBadBreath() {
            ++currentNumBadBreathKilled;
            ++totBadBreathKilled;
        }

        public void killedToothDecay() {
            ++currentNumToothDecayDestroyed;
        }

        public void lifeUsed() {
            ++currentNumLivesUsed;
            ++totLivesUsed;
        }

        //////////////////////////////////////////////////////Getters///////////////////////////////////////////////////
        /*
        * @return the current score of the player for this level*/
        public int getCurrentLvlScore() {
            calcLvlScore();
            return currentLvlScore;
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

        public int getCurrentNumPowersUsed() {
            return currentNumPowersUsed;
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

        public void setCurrentLvlScore(int currentLvlScore) {
            this.currentLvlScore = currentLvlScore;
        }

        public void setCurrentTotalScore(int currentTotalScore) {
            this.currentTotalScore = currentTotalScore;
        }

        public int getCurrentTotalScore() {
            calcTotScore();
            return currentTotalScore;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /*Calculators*/
        private void calcLvlScore() {
            currentLvlScore = collectibleValue * currentNumCollectiblesFound
                    + badBreathValue * currentNumBadBreathKilled
                    + currentNumToothDecayDestroyed * toothDecayValue;

            if (currentLvlScore < 0) {
                currentLvlScore = 0;
            }
        }

        public void calcTotScore() {
            currentTotalScore += currentLvlScore
                    - powerUpValue * currentNumPowersUsed
                    - currentNumLivesUsed * lifeValue;
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
