package za.ttd.level;

public class ScoringSystem {

    private int score;
    final int collectibleValue = 5;

    public ScoringSystem() {
        score = 0;
    }

    public void incScoreCollectible() {
        score += collectibleValue;
    }

    public void resetScore() {
        score = 0;
    }

}
