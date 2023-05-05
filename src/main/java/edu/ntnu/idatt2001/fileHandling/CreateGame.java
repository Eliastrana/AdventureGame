package edu.ntnu.idatt2001.fileHandling;

import edu.ntnu.idatt2001.*;

import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class that creates a game from a file.
 *
 */

public class CreateGame {
    String filePath;
    Story story;

    /**
     * Constructor for CreateGame
     * @param filepath String filepath
     */

    public CreateGame(String filepath) {

        this.filePath = filepath;
    }

    /**
     * Method that launches the game
     * @return Story
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
                System.out.println("Adding passage to story.");
            }
        }


//        for (Passage passage : passages) {
//            story.addPassage(passage);
//            System.out.println("Legger til story.");
//        }
//        for (int i = 1; i < passages.size(); i++) {
//            Passage passage = passages.get(i);
//            story.addPassage(passage);
//            System.out.println("Legger til story.");
//        }

        return story;
    }

    /**
     * Method that generates the story
     * @param story Story
     * @return String with all passages
     */

    public String storyGenerator(Story story) {
        String totalStory = story.getOpeningPassage().getTitle() + story.getOpeningPassage().getContent();
        for (Passage passage : story.getPassages()) {
            totalStory += passage.getTitle() + passage.getContent();
        }

        return totalStory;
    }

    /**
     * Method that generates the game
     * @param playerFilepath String filepath
     * @return Game
     * @throws IOException IOException
     */

    public Game gameGenerator(String playerFilepath) throws IOException {
        PlayerRegister playerRegister = new PlayerRegister();
        Player player = playerRegister.characterInforVariable(playerFilepath);
        Story story = launchGame();
        return new Game(player, story, new ArrayList<>());
    }



    public static void main(String[] args) throws IOException {
        CreateGame createGame = new CreateGame("src/main/resources/hauntedHouse.paths");
        System.out.println(createGame.storyGenerator(createGame.launchGame()));
        System.out.println(createGame.launchGame().getPassages());


        System.out.println(createGame.gameGenerator("src/main/resources/characters/warrior.paths").getStory().getPassages());
        System.out.println(createGame.storyGenerator(createGame.gameGenerator("src/main/resources/characters/warrior.paths").getStory()));
    }


}
