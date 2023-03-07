package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.Player;

public class GoldAction implements Action {
    int gold;
    GoldAction(int gold) {
        this.gold = gold;
    }


    public void execute(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");

        player.addGold(gold);
    }

}
