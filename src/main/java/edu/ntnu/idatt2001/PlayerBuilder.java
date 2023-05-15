package edu.ntnu.idatt2001;

import java.util.ArrayList;
import javafx.util.Builder;

/**
 * This class is a builder for the player class.
 */
public class PlayerBuilder implements Builder {
  private String name;
  private int health;
  private int score;
  private int gold;
  private ArrayList<String> inventory = new ArrayList<>();

  public PlayerBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public PlayerBuilder setHealth(int health) {
    this.health = health;
    return this;
  }

  public PlayerBuilder setScore(int score) {
    this.score = score;
    return this;
  }

  public PlayerBuilder setGold(int gold) {
    this.gold = gold;
    return this;
  }

  public PlayerBuilder addToInventory(String inventory) {
    this.inventory.add(inventory);
    return this;
  }


  @Override
  public Object build() {
    return new Player(name, health, score, gold, inventory);
  }

  public Player getPlayer() {
    return new Player(name, health, score, gold, inventory);
  }


  @Override
  public String toString() {
    return
            "Name: " + name
                    + " Health: "
                    + health
                    + " Score: "
                    + score
                    + " Gold: "
                    + gold
                    + " Inventory: "
                    + inventory;
  }

}
