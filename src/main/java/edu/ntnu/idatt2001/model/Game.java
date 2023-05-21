package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.goals.Goal;
import java.util.List;

/**
 * A class that represents a game in a text adventure game.
 * A game has a player, a story, and a list of goals.
 */
public class Game {

  private Player player;
  private Story story;

  private List<Goal> goals;

  /**
   * Creates a new game with the given player, story, and goals.
   *
   * @param player player of the game
   * @param story  story of the game
   * @param goals  goals of the game
   */
  public Game(Player player, Story story, List<Goal> goals) {
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


  /**
   * Returns the player of the game.
   *
   * @return player of the game
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Returns the story of the game.
   *
   * @return story of the game
   */
  public Story getStory() {
    return story;
  }

  /**
   * Returns the goals of the game.
   *
   * @return List of the goals in the game
   */
  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * Returns the opening passage of the game.
   *
   * @return first Passage of the game
   */
  public Passage begin() {
    return story.getOpeningPassage();
  }

  /**
   * Returns the passage the player goes to when choosing the given link.
   *
   * @param link Link to go to
   * @return Passage the player goes to
   */
  public Passage go(Link link) {
    if (link == null) {
      throw new IllegalArgumentException("Link cannot be null");
    }
    return story.getPassage(link);
  }

}

