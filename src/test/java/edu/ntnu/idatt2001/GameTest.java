package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

  @Nested
  @DisplayName("Constructor Test")
  class ConstructorTest {
    @Test
    @DisplayName("Test valid constructor")
    void constructorTest() {
      Player player = new Player("Name",10,10,10, new ArrayList<>());
      Passage passage = new Passage("Title", "Content");
      Story story = new Story("Title", passage);
      Game game = new Game(player, story, new ArrayList<>());
      assertEquals("Name", game.getPlayer().getName());
      assertEquals("Title", game.getStory().getTitle());
      assertEquals("Title", game.getStory().getOpeningPassage().getTitle());
      assertEquals("Content", game.getStory().getOpeningPassage().getContent());
    }
    @Test
    @DisplayName("Test constructor with null player")
    void constructorTestWithNullPlayer() {
      Passage passage = new Passage("Title", "Content");
      Story story = new Story("Title", passage);
      assertThrows(IllegalArgumentException.class, () -> new Game(null, story, new ArrayList<>()));
    }
    @Test
    @DisplayName("Test constructor with null story")
    void constructorTestWithNullStory() {
      Player player = new Player("Name",10,10,10, new ArrayList<>());
      assertThrows(IllegalArgumentException.class, () -> new Game(player, null, new ArrayList<>()));
    }
    @Test
    @DisplayName("Test constructor with null goals")
    void constructorTestWithNullGoals() {
      Player player = new Player("Name",10,10,10, new ArrayList<>());
      Passage passage = new Passage("Title", "Content");
      Story story = new Story("Title", passage);
      assertThrows(IllegalArgumentException.class, () -> new Game(player, story, null));
    }
  }
  @Nested
  @DisplayName("Getters Test")
  class GettersTest {
    @Test
    @DisplayName("Test getPlayer")
    void getPlayer() {
      Player player = new Player("Name",10,10,10, new ArrayList<>());
      Passage passage = new Passage("Title", "Content");
      Story story = new Story("Title", passage);
      Game game = new Game(player, story, new ArrayList<>());
      assertEquals("Name", game.getPlayer().getName());
    }
    @Test
    @DisplayName("Test getStory")
    void getStory() {
      Player player = new Player("Name",10,10,10, new ArrayList<>());
      Passage passage = new Passage("Title", "Content");
      Story story = new Story("Title", passage);
      Game game = new Game(player, story, new ArrayList<>());
      assertEquals("Title", game.getStory().getTitle());
      assertEquals("Title", game.getStory().getOpeningPassage().getTitle());
      assertEquals("Content", game.getStory().getOpeningPassage().getContent());
    }
    @Test
    @DisplayName("Test getGoals")
    void getGoals() {
      Player player = new Player("Name",10,10,10, new ArrayList<>());
      Passage passage = new Passage("Title", "Content");
      Story story = new Story("Title", passage);
      Game game = new Game(player, story, new ArrayList<>());
      assertEquals(0, game.getGoals().size());
    }
  }

  @Nested
  @DisplayName("Doers Test")
  class DoersTest {
    @Test
    @DisplayName("Test begin")
    void begin() {
      Player player = new Player("Name",10,10,10, new ArrayList<>());
      Passage passage = new Passage("Title", "Content");
      Story story = new Story("Title", passage);
      Game game = new Game(player, story, new ArrayList<>());
      game.begin();
      assertEquals("Title", game.getStory().getOpeningPassage().getTitle());
      assertEquals("Content", game.getStory().getOpeningPassage().getContent());
    }
    @Test
    @DisplayName("Test go")
    void go() {
      Player player = new Player("Name",10,10,10, new ArrayList<>());
      Passage passage1 = new Passage("Passage1", "This is passage 1");
      Passage passage2 = new Passage("Passage2", "This is passage 2");
      Link link = new Link("Go to passage2", "Passage2", new ArrayList<>());
      Story story = new Story("Title", passage1);
      story.addPassage(passage2);
      Game game = new Game(player, story, new ArrayList<>());
      game.go(link);
      assertEquals("Passage2", game.go(link).getTitle());
      assertEquals("This is passage 2", game.go(link).getContent());
    }
  }
}

