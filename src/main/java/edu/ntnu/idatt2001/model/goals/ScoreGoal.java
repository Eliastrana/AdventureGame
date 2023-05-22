package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for the score goal.
 */
public class ScoreGoal implements Goal {
  private int minimumPoints;

  public ScoreGoal(int minimumPoints) {

    this.minimumPoints = minimumPoints;
  }

  /**
   * Checks if the player has enough points to fulfill the goal.
   *
   * @param player the player to check.
   * @return true if the player has enough points, false otherwise.
   */
  public boolean isFullfilled(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    return player.getScore() >= this.minimumPoints;
  }

  @Override
  public String toString() {
    return String.valueOf(minimumPoints);
  }

}
