package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

public class ScoreGoal implements Goal {
    private int minimumPoints;

    public ScoreGoal(int minimumPoints) {

        this.minimumPoints = minimumPoints;
    }

    public boolean isFullfilled(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        return player.getScore() >= this.minimumPoints;
    }

    @Override
    public String toString() {
        return String.valueOf(minimumPoints);
    }

}
