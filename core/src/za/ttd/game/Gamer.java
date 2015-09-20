package za.ttd.game;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.utils.TimeUtils;
import za.ttd.characters.states.MessageType;

public class Gamer implements Telegraph {

    private String name;
    private int totScore, highestLevel;
    private int gamerID;
    private int lives;
    public ScoringSystem scoring;

    public Gamer(String name, int highestScore, int highestLevel, int lives) {
        this.name = name;
        this.totScore = highestScore;
        this.highestLevel = highestLevel;
        this.lives = lives;
        this.scoring = new ScoringSystem();
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

    public void setHighestLevel(int highestLevel) {
        this.highestLevel = highestLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamerID() {
        return gamerID;
    }

    public void setGamerID(int gamerID) {
        this.gamerID = gamerID;
    }

    public void incHighestLevel() {
        ++highestLevel;
    }

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch(msg.message) {
            case MessageType.THOMAS_LOSES_LIFE:
                if(getLives() > 0) {
                    scoring.lifeUsed();
                    MessageManager.getInstance().dispatchMessage(this, MessageType.LEVEL_RESET);
                } else {
                    MessageManager.getInstance().dispatchMessage(this, MessageType.THOMAS_DEAD);
                }
                return true;
            case MessageType.BADBREATH_DEAD:
                scoring.killedBadBreath();
                break;
            case MessageType.TOOTHDECAY_DEAD:
                scoring.killedToothDecay();
                break;
        }
        return false;
    }

    public class ScoringSystem {

        private int lvlScore, lvlTotScore, totPowersUsed, totLivesUsed, totCollectiblesFound, totBadBreathKilled;
        private final int collectibleValue = 5, powerUpValue = 10, badBreathValue = 100, lifeValue = 200, toothDecayValue = 300;
        private int powersUsed, badBreathKilled, collectiblesFound, livesUsed, toothDecayDestroyed;
        private long startTime, elapsedTime, overtime;

        public ScoringSystem() {
            lvlScore = 0;
            lvlTotScore = 0;
            powersUsed = 0;
            badBreathKilled = 0;
            collectiblesFound = 0;
            totLivesUsed = 0;
            totBadBreathKilled = 0;
            totCollectiblesFound = 0;
            startTime = TimeUtils.millis();
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

        /*
        * @return the current score of the player for this level*/
        public int getLvlScore() {
            calcLvlScore();
            return lvlScore;
        }

        /*
        * @return a long value of the time in seconds */
        public long getElapsedTime() {
            elapsedTime = TimeUtils.timeSinceMillis(startTime); //- TimeUtils.timeSinceMillis(overtime);
            return elapsedTime/1000;
        }

        /*
        * @return an int of the total score for the current level */
        public int getLvlTotScore() {
            calcLvlTotScore();
            return lvlTotScore;
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

        /*
            * calculate to score for the current level*/
        private void calcLvlScore() {
            lvlScore = collectibleValue*collectiblesFound
                    + badBreathValue * badBreathKilled
                    + toothDecayDestroyed * toothDecayValue
                    - powerUpValue*powersUsed
                    - livesUsed*lifeValue;

            if (lvlScore < 0) {
                lvlScore = 0;
                livesUsed = 0;
                powersUsed = 0;
                collectiblesFound = 0;
                badBreathKilled = 0;
            }
        }

        /*
        * calculate the total score for the level taking in the time it took to complete the level*/
        private void calcLvlTotScore() {

            int timePenalty = (int)elapsedTime/60000;
            if (timePenalty > 0)
                lvlTotScore = lvlScore + (1000/timePenalty);
            else
                lvlTotScore = lvlScore;
        }

        /*used to calculate the time passed when in the pause menu*/
        public void pauseTime() {
            overtime = TimeUtils.millis();
        }
    }

}
