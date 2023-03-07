package edu.ntnu.idatt2001;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int score;
    private int gold;
    private ArrayList<String> inventory = new ArrayList<>();

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
