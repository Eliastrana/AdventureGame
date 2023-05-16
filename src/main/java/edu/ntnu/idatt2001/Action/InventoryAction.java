package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.utility.SoundPlayer;

/**
 * Action for adding gold to the player.
 */
public class InventoryAction implements Action {
  private String item;


  InventoryAction(String item) {

    this.item = item;
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

    int initialInventorySize = player.getInventory().size();
    player.addToInventory(item);
    int finalInventorySize = player.getInventory().size();

    if (finalInventorySize > initialInventorySize) {
      SoundPlayer.play("src/main/resources/sounds/pop.wav");
    }

  }
}
