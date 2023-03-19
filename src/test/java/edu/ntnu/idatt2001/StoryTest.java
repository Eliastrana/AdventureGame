package edu.ntnu.idatt2001;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@Nested
@DisplayName("Constructor Test")
class StoryConstructorTest {

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
class StoryGettersTest {

  @Test
  @DisplayName("Test getTitle")
  void getTitle() {
    Story story = new Story("Title", new Passage("Title", "Content"));
    assertEquals("Title", story.getTitle());
  }

  @Test
  @DisplayName("Test GetBrokenLinks")
  void getBrokenLinks() {
    Story story = new Story("Title", new Passage("Title", "Content"));
    assertEquals(0, story.getBrokenLinks().size());
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


@Nested
@DisplayName("Void methods Test")
class StoryAddersTest {
  @Test
  @DisplayName("Test addPassage")
  void addPassage() {
    Story story = new Story("Story Title", new Passage("passageTitle", "passageContent"));
    Passage passage = new Passage("newTitle", "newContent");
    story.addPassage(passage);
    assertEquals(1, story.getPassages().size());
  }
  @Test
  @DisplayName("Test addPassage with null passage")
  void addPassageWithNullPassage() {
    Story story = new Story("Title", new Passage("Title", "Content"));
    assertThrows(IllegalArgumentException.class, () -> story.addPassage(null));
  }
  @Test
  @DisplayName("Test addPassage with existing passage")
  void addPassageWithExistingPassage() {
    Story story = new Story("storyTitle", new Passage("passageTitle", "passageContent"));
    Passage passage = new Passage("passageTitle", "passageContent");
    assertThrows(IllegalArgumentException.class, () -> story.addPassage(passage));
  }
  @Test
  @DisplayName("Test removePassage")
  void removePassage() {
    Story story = new Story("storyTitle", new Passage("passageTitle", "passageContent"));
    Passage passage = new Passage("newTitle", "newContent");
    Link link = new Link("linkTitle", "linkContent");
    passage.addLink(link);
    story.addPassage(passage);
    story.removePassage(passage.getLinks().get(0));
    assertEquals(1, story.getPassages().size());
  }
}
