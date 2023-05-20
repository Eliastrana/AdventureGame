package edu.ntnu.idatt2001.model.Action;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.utility.SoundPlayer;

/**
 * Action for adding gold to the player.
 */
public class HealthAction implements Action {
  private int health;

  public HealthAction(int health) {
    this.health = health;
  }

  /**
   * Adds gold to the player.
   *
   * @param player the player to add gold to.
   */
  public void execute(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    player.addHealth(health);

    if (health > 0) {
      SoundPlayer.play("src/main/resources/sounds/positivesound.wav");
    } else {
      SoundPlayer.play("src/main/resources/sounds/negativesound.wav");
    }
  }


}
