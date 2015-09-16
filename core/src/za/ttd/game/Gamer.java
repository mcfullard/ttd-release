package za.ttd.game;

public class Gamer {

    private String name;
    private int totScore, highestLevel;
    private int gamerID;
    private int lives;


    public Gamer(String name, int highestScore, int highestLevel, int lives) {
        this.name = name;
        this.totScore = highestScore;
        this.highestLevel = highestLevel;
        this.lives = lives;
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
}
