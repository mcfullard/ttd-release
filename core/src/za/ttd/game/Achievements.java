package za.ttd.game;

import java.util.ArrayList;

public class Achievements {
    private Player player;
    private ArrayList<Achievement> achievementsObtained, achievements;

    public Achievements() {
        player = Player.getInstance();
        achievementsObtained = new ArrayList<>();
        achievements = new ArrayList<>();

    }

    public void updateAchievements() {
        for (Achievement achievement:achievements) {
            int metric = 0;
            boolean check = true;
            switch (achievement.getMetric()) {
                case "LIVES":
                    metric = player.getLives();
                    break;
                case "HIGHSCORE":
                    metric = player.getHighestScore();
                    break;
                case "BADBREATH_KILLED":
                    metric = player.scoring.getTotBadBreathKilled();
                    break;
                case "LEVEL":
                    metric = player.getHighestLevel();
                    break;
                default:
                    check = false;
                    break;
            }

            if (check && doComparison(metric, achievement.getCondition(), achievement.getBound()))
                obtainedAchievement(achievement);
        }
    }


    public boolean doComparison(int metric,String condition, int bound){
        switch (condition) {
            case "<":
                return metric < bound;
            case "==":
                return metric == bound;
            case ">":
                return metric > bound;
            case "<=":
                return metric <= bound;
            case ">=":
                return metric >= bound;
            default:
                return false;
        }
    }

    private void obtainedAchievement(Achievement achievement) {
        boolean obtained = false;
        for (int i = 0; i < achievementsObtained.size(); i++)
            if (achievement.getId() == achievementsObtained.get(i).getId())
                obtained = true;

        if (!obtained)
            achievementsObtained.add(achievement);

    }

    public ArrayList<Achievement> getAchievements() {
        return achievementsObtained;
    }
}
