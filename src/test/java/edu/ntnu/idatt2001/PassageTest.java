package edu.ntnu.idatt2001;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PassageTest {
  @Nested
  @DisplayName("Constructor Test")
  class PassageConstructorTest {

    @Test
    @DisplayName("Test valid constructor")
    void validConstructorTest() {
      Passage passage = new Passage("Title", "Content");
      assertEquals("Title", passage.getTitle());
      assertEquals("Content", passage.getContent());
      assertEquals(0, passage.getLinks().size());
    }

    @Test
    @DisplayName("Test constructor with null title")
    void constructorTestWithNullTitle() {
      assertThrows(IllegalArgumentException.class, () -> new Passage(null, "Content"));
    }

    @Test
    @DisplayName("Test constructor with empty title")
    void constructorTestWithEmptyTitle() {
      assertThrows(IllegalArgumentException.class, () -> new Passage("", "Content"));
    }

    @Test
    @DisplayName("Test constructor with null content")
    void constructorTestWithNullContent() {
      assertThrows(IllegalArgumentException.class, () -> new Passage("Title", null));
    }

    @Test
    @DisplayName("Test constructor with empty content")
    void constructorTestWithEmptyContent() {
      assertThrows(IllegalArgumentException.class, () -> new Passage("Title", ""));
    }
  }

  @Nested
  @DisplayName("Getters Test")
  class PassageGettersTest {

    @Test
    @DisplayName("Test getTitle")
    void getTitle() {
      Passage passage = new Passage("Title", "Content");
      assertEquals("Title", passage.getTitle());
    }

    @Test
    @DisplayName("Test getContent")
    void getContent() {
      Passage passage = new Passage("Title", "Content");
      assertEquals("Content", passage.getContent());
    }

    @Test
    @DisplayName("Test getLinks")
    void getLinks() {
      Passage passage = new Passage("Title", "Content");
      assertEquals(0, passage.getLinks().size());
    }
  }

  @Nested
  @DisplayName("Adders Test")
  class PassageAddersTest {

    @Test
    @DisplayName("Test valid addLink")
    void addLink() {
      Passage passage = new Passage("Title", "Content");
      passage.addLink(new Link("testText", "testReference", new ArrayList<>()));
      assertEquals(1, passage.getLinks().size());
    }

    @Test
    @DisplayName("Test addLink with null link")
    void addLinkWithNullLink() {
      Passage passage = new Passage("Title", "Content");
      assertThrows(IllegalArgumentException.class, () -> passage.addLink(null));
    }
  }


  @Nested
  @DisplayName("Boolean Test")
  class PassageBooleanTest {

    @Test
    @DisplayName("Test hasLink")
    void hasLink() {
      Passage passage = new Passage("Title", "Content");
      assertFalse(passage.hasLink());
      passage.addLink(new Link("testText", "testReference", new ArrayList<>()));
      assertTrue(passage.hasLink());
    }


    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
  }
}

