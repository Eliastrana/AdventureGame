package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.goals.Goal;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Player player;
    private Story story;

    private ArrayList<Goal> goals;

    public Game(Player player, Story story, ArrayList<Goal> goals) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (story == null) {
            throw new IllegalArgumentException("Story cannot be null");
        }
        if (goals == null) {
            throw new IllegalArgumentException("Goals cannot be null");
        }
        this.player = player;
        this.story = story;
        this.goals = goals;
    }

    public Player getPlayer() {
        return player;
    }

    public Story getStory() {
        return story;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public Passage begin() {
        return story.getOpeningPassage();
    }

    public Passage go(Link link) {
        if (link == null) {
            throw new IllegalArgumentException("Link cannot be null");
        }
        return story.getPassage(link);
    }

}

