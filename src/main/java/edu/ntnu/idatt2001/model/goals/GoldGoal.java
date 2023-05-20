package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

public class GoldGoal implements Goal {
    private int minimumGold;

    public GoldGoal(int minimumGold) {

        this.minimumGold = minimumGold;
    }


    public boolean isFullfilled(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");

        return player.getGold() >= this.minimumGold;
    }


    @Override
    public String toString() {
        return String.valueOf(minimumGold);
    }

}
