package edu.ntnu.idatt2001;

import javafx.util.Builder;
import java.util.ArrayList;

public class PlayerBuilder implements Builder {
    private String name;
    private int health;
    private int score;
    private int gold;
    private ArrayList<String> inventory;

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


    @Override
    public Object build() {
        return new Player(name, health, score, gold);
    }


    @Override
    public String toString() {
        return
                "Name: " + name +
                " Health: " + health +
                " Score: " + score +
                " Gold: " + gold +
                " Inventory: " + inventory;
    }

}
