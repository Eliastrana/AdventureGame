package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.goals.Goal;

public class HealthGoal implements Goal {
    private int minimumHealth;

    public HealthGoal(int minimumHealth) {
        this.minimumHealth = minimumHealth;
    }

    public boolean isFullfilled(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");

        return player.getHealth() >= this.minimumHealth;
    }
}
