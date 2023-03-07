package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.goals.Goal;

public class GoldGoal implements Goal {
    private int minimumGold;
    public GoldGoal(int minimumGold) {

        this.minimumGold = minimumGold;
    }
    public boolean isFullfilled(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");

        return player.getScore() >= this.minimumGold;
    }
}
