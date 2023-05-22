package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for the health goal.
 */
public class HealthGoal implements Goal {
  private int minimumHealth;

  public HealthGoal(int minimumHealth) {

    this.minimumHealth = minimumHealth;
  }


  /**
   * Checks if the player has enough health to fulfill the goal.
   *
   * @param player the player to check.
   * @return true if the player has enough health, false otherwise.
   */
  public boolean isFullfilled(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }

    return player.getHealth() >= this.minimumHealth;
  }


  @Override
  public String toString() {
    return String.valueOf(minimumHealth);
  }

}
