package za.ttd.level;

import com.badlogic.gdx.utils.TimeUtils;

public class ScoringSystem {

    private int lvlScore, lvlTotScore, totPlayerScore, totPowersUsed, totLivesUsed, totCollectiblesFound, totBadBreathKilled;
    private final int collectibleValue = 5, powerUpValue = 10, badBreathValue = 100, lifeValue = 200, maxLives = 3;
    private int powersUsed, badBreathKilled, collectiblesFound, livesUsed;
    private long startTime, elapsedTime, overtime;

    public ScoringSystem(int totPlayerScore) {
        this.totPlayerScore = totPlayerScore;

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
        lvlTotScore = lvlScore + (int)(1000/(elapsedTime/60000));
    }

    /*used to calculate the time passed when in the pause menu*/
    public void pauseTime() {
        overtime = TimeUtils.millis();
    }
}
