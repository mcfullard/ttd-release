package za.ttd.level;

public class ScoringSystem {

    private int lvlScore;
    final int collectibleValue = 5;

    public ScoringSystem() {
        lvlScore = 0;
    }

    public void incScoreCollectible() {
        lvlScore += collectibleValue;
    }

    public void resetScore() {
        lvlScore = 0;
    }

    public int getLvlScore() {
        return lvlScore;
    }

}
