package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.Action.Action;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class StoryTest {
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
      assertThrows(NullPointerException.class, () -> new Story(null, new Passage("Title", "Content")));
    }

    @Test
    @DisplayName("Test constructor with empty title")
    void constructorTestWithEmptyTitle() {
      assertThrows(NullPointerException.class, () -> new Story("", new Passage("Title", "Content")));
    }

    @Test
    @DisplayName("Test constructor with null openingPassage")
    void constructorTestWithNullOpeningPassage() {
      assertThrows(NullPointerException.class, () -> new Story("Title", null));
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
    void testGetBrokenLinks() {
      Story story = new Story("Title", new Passage("Title", "Content"));
      Link brokenLink = new Link("Broken text", "Broken Link",new ArrayList<Action>());
      Link workingLink = new Link("Working text", "Second Passage",new ArrayList<Action>());
      Passage openingPassage = new Passage("Opening Passage", "Content");
      Passage secondPassage = new Passage("Second Passage", "Content");
      openingPassage.addLink(brokenLink);
      secondPassage.addLink(workingLink);
      story.addPassage(openingPassage);
      story.addPassage(secondPassage);
      List<Link> brokenLinks = story.getBrokenLinks();
      assertEquals(1, brokenLinks.size());
      assertTrue(brokenLinks.contains(brokenLink));
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
      story.addPassage(new Passage("newTitle", "newContent"));
      assertEquals(1, story.getPassages().size());
    }
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
      assertThrows(NullPointerException.class, () -> story.addPassage(null));
    }


    @Test
    @DisplayName("Test valid removePassage")
    void removePassage() {
      Story story = new Story("storyTitle", new Passage("passageTitle", "passageContent"));
      Passage passage = new Passage("newTitle", "newContent");
      Link link = new Link("linkTitle", "linkReference", new ArrayList<>());
      story.addPassage(passage);
      story.removePassage(link);
      assertEquals(1, story.getPassages().size());
    }

    @Test
    @DisplayName("Test removePassage with null link")
    void removePassageWithNullLink() {
      Story story = new Story("storyTitle", new Passage("passageTitle", "passageContent"));
      Passage passage = new Passage("newTitle", "newContent");
      story.addPassage(passage);
      assertThrows(NullPointerException.class, () -> story.removePassage(null));
    }

    @Test
    @DisplayName("Test removePassage with link in use")
    void removePassageWithLinkInUse() {
      Passage openingPassage = new Passage("Opening Passage", "Content");
      Passage secondPassage = new Passage("Second Passage", "Content");
      Link link = new Link("Go to second passage", "Second Passage", new ArrayList<>());
      Story story = new Story("My Story", openingPassage);
      story.addPassage(secondPassage);
      openingPassage.addLink(link);
      assertThrows(IllegalArgumentException.class, () -> story.removePassage(link));
    }
  }
}
