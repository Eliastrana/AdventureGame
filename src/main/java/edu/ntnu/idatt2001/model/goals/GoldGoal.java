package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for the gold goal.
 */
public class GoldGoal implements Goal {
  private int minimumGold;

  public GoldGoal(int minimumGold) {

    this.minimumGold = minimumGold;
  }

  /**
   * Checks if the player has enough gold to fulfill the goal.
   *
   * @param player the player to check.
   * @return true if the player has enough gold, false otherwise.
   */
  public boolean isFullfilled(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }

    return player.getGold() >= this.minimumGold;
  }

  @Override
  public String toString() {
    return String.valueOf(minimumGold);
  }

}
