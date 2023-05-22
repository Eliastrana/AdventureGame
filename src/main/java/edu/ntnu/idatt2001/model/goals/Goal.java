package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

/**
 * Interface for the different goals.
 */
public interface Goal {
  boolean isFullfilled(Player player);
}
