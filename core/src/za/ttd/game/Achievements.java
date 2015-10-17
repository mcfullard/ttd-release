package za.ttd.game;

import java.util.ArrayList;

public class Achievements {

    private Player player;
    private ArrayList<String> achievements;

    public Achievements() {
        player = Player.getInstance();
        achievements = new ArrayList<>();
    }

    public void updateAchievements() {
        if (player.scoring.getPowersUsed() < 0)
            achievements.add("Man on the move: Finished Level without using a minty mouthwash");

        if (player.scoring.getTotBadBreathKilled() > 50)
            achievements.add("Bad Breath Master: Killed at 50 Bad Breath");

        if (player.scoring.getTotCollectiblesFound() > 10000)
            achievements.add("Plaque Master: Collected 10000 Plaque items");
    }

    public ArrayList<String> getAchievements() {
        return achievements;
    }


    public void clearAchievements() {
        achievements.clear();
    }
}
