package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

public class HealthGoal implements Goal {
    private int minimumHealth;

    public HealthGoal(int minimumHealth) {

        this.minimumHealth = minimumHealth;
    }


    public boolean isFullfilled(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");

        return player.getHealth() >= this.minimumHealth;
    }



    @Override
    public String toString() {
        return String.valueOf(minimumHealth);
    }

}
