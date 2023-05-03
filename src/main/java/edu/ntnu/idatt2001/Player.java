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

    private Passage lastPassage;



    // required parameters only
    public Player(String name, int health) {
        this(name,health,0);
    }

    public Player(String name, int health, int score) {
        this(name,health,score,0);
    }

    public Player(String name, int health, int score, int gold) {
        this(name,health,score,gold,null);
    }
    public Player(String name, int health, int score, int gold, ArrayList<String> inventory) {
        this.name = name;
        this.health = health;
        this.score = score;
        this.gold = gold;
        this.inventory = inventory;
    }
    public Player(String name, int health, int score, int gold, ArrayList<String> inventory, Passage lastPassage) {
        this.name = name;
        this.health = health;
        this.score = score;
        this.gold = gold;
        this.inventory = inventory;
        this.lastPassage = lastPassage;
    }



    /**
     * Returns the name of the player.
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the health of the player.
     * @return int health
     */

    public int getHealth() {
        return health;
    }

    /**
     * Returns the score of the player.
     * @return int score
     */

    public int getScore() {
        return score;
    }

    /**
     * Adds an item to the player's inventory.
     * @param item String item
     */
    public void addToInventory(String item) {
        if (item == null || item.isBlank()) {
            throw new IllegalArgumentException("Item cannot be empty");
        }
        inventory.add(item);
    }

    /**
     * Returns the inventory of the player.
     * @return ArrayList inventory
     */

    public ArrayList<String> getInventory() {

        return inventory;
    }

    /**
     * Return the last Passage the player was in.
     * @return Passage lastPassage
     */

    public Passage getLastPassage() {

        return lastPassage;
    }
    public void setLastPassage(Passage passage) {
        if (passage == null) {
            throw new IllegalArgumentException("The passage cannot be null");
        }
        this.lastPassage = passage;
    }

    /**
     * Returns the gold of the player.
     * @return int gold
     */

    public int getGold() {
        return gold;
    }

    /**
     * Adds gold to the player's gold.
     * @param gold int gold
     */
    public void addGold(int gold) {
        if (gold < 0) {
            throw new IllegalArgumentException("Gold cannot be negative");
        }
        this.gold += gold;
    }

    /**
     * Adds health to the player's health.
     * @param health int health
     */
    public void addHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.health += health;
    }

    /**
     * Adds score to the player's score.
     * @param score int score
     */
    public void addScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.score += score;
    }

    @Override
    public String toString() {
        return """
                Player {
                name: %s,
                health: %d,
                score: %d,
                gold: %d,
                inventory: %s
                }
                """.formatted(name, health, score, gold, inventory);
    }
}