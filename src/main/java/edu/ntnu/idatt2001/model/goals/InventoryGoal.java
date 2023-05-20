package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

public class InventoryGoal implements Goal {
    private int minimumItems;

    public InventoryGoal(int minimumItems) {
        this.minimumItems = minimumItems;
    }


    public boolean isFullfilled(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        return player.getInventory().size() >= minimumItems;
    }


    @Override
    public String toString() {
        return String.valueOf(minimumItems);
    }
}
