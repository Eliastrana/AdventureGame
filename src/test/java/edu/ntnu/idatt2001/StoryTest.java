package edu.ntnu.idatt2001;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoryTest {

  @Nested
  @DisplayName("Constructor Test")
  class ConstructorTest {

    @Test
    @DisplayName("Test valid constructor")
    void constructorTest() {
      Story story = new Story("Title", new Passage("Title", "Content"));
      assertEquals("Title", story.getTitle());
      assertEquals("Title", story.getOpeningPassage().getTitle());
      assertEquals("Content", story.getOpeningPassage().getContent());
    }

    @Test
    @DisplayName("Test constructor with null title")
    void constructorTestWithNullTitle() {
      assertThrows(IllegalArgumentException.class, () -> new Story(null, new Passage("Title", "Content")));
    }

    @Test
    @DisplayName("Test constructor with empty title")
    void constructorTestWithEmptyTitle() {
      assertThrows(IllegalArgumentException.class, () -> new Story("", new Passage("Title", "Content")));
    }

    @Test
    @DisplayName("Test constructor with null openingPassage")
    void constructorTestWithNullOpeningPassage() {
      assertThrows(IllegalArgumentException.class, () -> new Story("Title", null));
    }
  }

  @Nested
  @DisplayName("Getters Test")
  class GettersTest {

    @Test
    @DisplayName("Test getTitle")
    void getTitle() {
      Story story = new Story("Title", new Passage("Title", "Content"));
      assertEquals("Title", story.getTitle());
    }

    @Test
    @DisplayName("Test getOpeningPassage")
    void getOpeningPassage() {
      Story story = new Story("Title", new Passage("Title", "Content"));
      assertEquals("Title", story.getOpeningPassage().getTitle());
      assertEquals("Content", story.getOpeningPassage().getContent());
    }

    @Test
    @DisplayName("Test getPassages")
    void getPassages() {
      Story story = new Story("Title", new Passage("Title", "Content"));
      story.addPassage(new Passage("Title", "Content"));
      assertEquals(1, story.getPassages().size());
    }

    //@Test
    //@DisplayName("Test getPassage")

  }

}

@Nested
@DisplayName("Adders Test")
class AddersTest {
  @Test
  @DisplayName("Test addPassage")
  void addPassage() {
    Story story = new Story("Title", new Passage("Title", "Content"));
    Passage passage = new Passage("Title", "Content");
    story.addPassage(passage);
    assertEquals(1, story.getPassages().size());
  }
}
