package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.goals.Goal;

import java.util.HashSet;
import java.util.List;

public class InventoryGoal implements Goal {
    private List<String> mandatoryItems;
    InventoryGoal(List<String> mandatoryItems) {

        this.mandatoryItems = mandatoryItems;
    }

    public boolean isFullfilled(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        HashSet<String> playerItems = new HashSet<>(player.getInventoryList());
        return playerItems.containsAll(mandatoryItems);
    }
}
