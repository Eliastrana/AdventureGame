package edu.ntnu.idatt2001.fileHandling;


import edu.ntnu.idatt2001.Game;
import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Player;
import edu.ntnu.idatt2001.Story;
import edu.ntnu.idatt2001.goals.Goal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * A class that creates a game from a file.
 */

public class CreateGame {
  String filePath;

  String goalFilePath;
  String characterPath;
  String characterIconPath;

  /**
   * Constructor for CreateGame.
   *
   * @param filePath      filePath
   * @param characterPath characterPath
   * @param goalsPath     goalsPath
   * @param characterIcon characterIcon
   */

  public CreateGame(String filePath, String characterPath, String goalsPath, String characterIcon) {

    this.filePath = filePath;
    this.goalFilePath = goalsPath;
    this.characterPath = characterPath;
    this.characterIconPath = characterIcon;
  }

  /**
   * Method that launches the game.
   *
   * @return Story to launch
   * @throws IOException IOException
   */

  public Story launchGame() throws IOException {
    FileRead fileRead = new FileRead(filePath);
    List<Passage> passages = fileRead.formatPathsFile();

    if (passages.isEmpty() || passages.get(0) == null) {
      throw new IllegalArgumentException("Invalid passages list");
    }

    Story story = new Story(filePath, passages.get(0));

    for (int i = 1; i < passages.size(); i++) {
      Passage passage = passages.get(i);
      if (passage != null) {
        story.addPassage(passage);
      }
    }

    return story;
  }

  public String getCharacterIconPath() {
    return characterIconPath;
  }



  /**
   * Method that generates the game.
   *
   * @param playerFilepath String filepath
   * @return Game
   * @throws IOException IOException
   */

  public Game gameGenerator(String playerFilepath) throws IOException {
    Player player = playerGenerator(playerFilepath);
    Story story = launchGame();
    ArrayList<Goal> goals = goalGenerator(goalFilePath);
    return new Game(player, story, goals);
  }

  /**
   * Method that generates the player.
   *
   * @param playerFilepath String filepath
   * @return Player
   */
  public Player playerGenerator(String playerFilepath) {
    PlayerRegister playerRegister = new PlayerRegister();
    return playerRegister.characterInforVariable(playerFilepath);
  }

  /**
   * Method that generates the goals.
   *
   * @param goalFile String filepath
   * @return ArrayList of goals
   */

  public ArrayList<Goal> goalGenerator(String goalFile) {
    GoalRegister goalRegister = new GoalRegister();
    goalRegister.loadGoalsFromFile(goalFile);
    return (ArrayList<Goal>) goalRegister.getGoals();
  }


}
