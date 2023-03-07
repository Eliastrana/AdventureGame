package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.goals.Goal;

public class ScoreGoal implements Goal {
    private int minimumPoints;

    public ScoreGoal(int minimumPoints) {
        this.minimumPoints = minimumPoints;
    }
    public boolean isFullfilled(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        return player.getScore() >= this.minimumPoints;
    }
}
