package edu.ntnu.idatt2001;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a player in a text adventure game.
 * A player has a name, health, score, gold, and inventory.
 */
public class Player {
    private String name;
    private int health;
    private int score;
    private int gold;
    private ArrayList<String> inventory;

    /**
     * Creates a new player with the given name, health, score, and gold.
     * @param name String name
     * @param health int health
     * @param score int score
     * @param gold int gold
     */

    public Player(String name, int health, int score, int gold) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        if (gold < 0) {
            throw new IllegalArgumentException("Gold cannot be negative");
        }

        this.name = name;
        this.health = health;
        this.score = score;
        this.gold = gold;
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }
    public void addToInventory(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be empty");
        }
        inventory.add(item);
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }
    public List<String> getInventoryList() {
        return inventory;
    }

    public int getGold() {
        return gold;
    }
    public void addGold(int gold) {
        if (gold < 0) {
            throw new IllegalArgumentException("Gold cannot be negative");
        }
        this.gold += gold;
    }
    public void addHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.health += health;
    }
    public void addScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.score += score;
    }


}
