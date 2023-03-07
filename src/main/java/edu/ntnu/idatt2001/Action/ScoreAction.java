package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.Player;

public class ScoreAction implements Action {
    private int points;

    public ScoreAction(int points) {
        this.points = points;
    }

    public void execute(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        player.addScore(points);
    }
}
