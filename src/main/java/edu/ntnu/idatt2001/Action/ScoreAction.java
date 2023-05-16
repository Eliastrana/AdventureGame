package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.utility.SoundPlayer;


/**
 * Action for adding gold to the player.
 */
public class ScoreAction implements Action {
  private int points;

  public ScoreAction(int points) {
    this.points = points;
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

    if (points > 0) {
      SoundPlayer.play("src/main/resources/sounds/positivesound.wav");
    } else {
      SoundPlayer.play("src/main/resources/sounds/negativesound.wav");
    }

    player.addScore(points);


  }
}
