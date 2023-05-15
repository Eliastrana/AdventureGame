package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.utility.SoundPlayer;

public class HealthAction implements Action {
    private int health;
    public HealthAction(int health) {
        this.health = health;
    }

    public void execute(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        player.addHealth(health);

        if (health > 0){
            SoundPlayer.play("src/main/resources/sounds/positivesound.wav");
        } else {
            SoundPlayer.play("src/main/resources/sounds/negativesound.wav");
        }
    }



}
