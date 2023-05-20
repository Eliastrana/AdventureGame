package edu.ntnu.idatt2001.model.Action;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.utility.SoundPlayer;

/**
 * Action for adding gold to the player.
 */
public class GoldAction implements Action {
  int gold;

  public GoldAction(int gold) {
    this.gold = gold;
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
    player.addGold(gold);

    if (gold > 0) {
      SoundPlayer.play("src/main/resources/sounds/coins.wav");
    } else {
      SoundPlayer.play("src/main/resources/sounds/negativesound.wav");
    }
  }

}
