package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.Player;

public class HealthAction implements Action {
    private int health;
    public HealthAction(int health) {

        this.health = health;
    }

    public void execute(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        player.addHealth(health);
    }

}
