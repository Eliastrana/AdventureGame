package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.utility.SoundPlayer;

public class GoldAction implements Action {
    int gold;
    GoldAction(int gold) {
        this.gold = gold;
    }


    public void execute(Player player) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        player.addGold(gold);

//        if (gold > 0){
//            SoundPlayer.play("src/main/resources/sounds/positivesound.wav");
//        } else {
//            SoundPlayer.play("src/main/resources/sounds/negativesound.wav");
//        }
    }

}
