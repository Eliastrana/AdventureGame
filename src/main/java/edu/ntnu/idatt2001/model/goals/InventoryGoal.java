package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for the inventory goal.
 */
public class InventoryGoal implements Goal {
  private int minimumItems;

  public InventoryGoal(int minimumItems) {
    this.minimumItems = minimumItems;
  }


  /**
   * Checks if the player has enough items to fulfill the goal.
   *
   * @param player the player to check.
   * @return true if the player has enough items, false otherwise.
   */
  public boolean isFullfilled(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    return player.getInventory().size() >= minimumItems;
  }


  @Override
  public String toString() {
    return String.valueOf(minimumItems);
  }
}
