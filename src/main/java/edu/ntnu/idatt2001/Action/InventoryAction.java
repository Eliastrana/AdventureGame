package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.Player;

public class InventoryAction implements Action {
    private String item;

    InventoryAction(String item) {

        this.item = item;
    }

    public void execute(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        player.addToInventory(item);
    }
}
