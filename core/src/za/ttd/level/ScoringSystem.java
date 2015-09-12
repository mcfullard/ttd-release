package za.ttd.level;

import com.badlogic.gdx.utils.TimeUtils;

public class ScoringSystem {

    private int lvlScore, lvlTotScore, totPlayerScore;
    private final int collectibleValue = 5, powerUpValue = 10, plaqueValue = 100, lifeValue = 200, maxLives = 3;
    private int powersUsed, plaqueKilled, collectiblesFound, lives, livesUsed;
    private long startTime, elapsedTime, overtime;

    public ScoringSystem(int totPlayerScore, int lives) {
        lvlScore = 0;
        lvlTotScore = 0;
        this.totPlayerScore = totPlayerScore;
        powersUsed = 0;
        plaqueKilled = 0;
        collectiblesFound = 0;
        this.lives = lives;
        startTime = TimeUtils.millis();
    }

    public void collectibleFound() {
        ++collectiblesFound;
    }

    public void powerUsed() {
        ++powersUsed;
    }

    public void killedPlaque() {
        ++plaqueKilled;
    }

    public void lifeUsed() {
        ++livesUsed;
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

    public int getLives() {
        return lives;
    }

    /*
    * calculate to score for the current level*/
    private void calcLvlScore() {
        lvlScore = collectibleValue*collectiblesFound
                + plaqueValue*plaqueKilled
                - powerUpValue*powersUsed
                - livesUsed*lifeValue;

        if (lvlScore < 0)
            lvlScore = 0;
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
