package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.Action.Action;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class LinkTest {
  @Nested
  @DisplayName("Constructor Test")
  class LinkConstructorTest {
    @Test
    @DisplayName("Test valid constructor")
    void constructorTest() {
      Link link = new Link("Text", "Content", new ArrayList<>());
      assertEquals("Text", link.getText());
      assertEquals("Content", link.getReference());
      assertEquals(0, link.getActions().size());
    }

    @Test
    @DisplayName("Test constructor with null text")
    void constructorTestWithNullText() {
      assertThrows(IllegalArgumentException.class, () -> new Link(null, "Content", new ArrayList<>()));
    }

    @Test
    @DisplayName("Test constructor with empty text")
    void constructorTestWithEmptyText() {
      assertThrows(IllegalArgumentException.class, () -> new Link("", "Content", new ArrayList<>()));
    }

    @Test
    @DisplayName("Test constructor with null reference")
    void constructorTestWithNullReference() {
      assertThrows(IllegalArgumentException.class, () -> new Link("Text", null, new ArrayList<>()));
    }

    @Test
    @DisplayName("Test constructor with empty reference")
    void constructorTestWithEmptyReference() {
      assertThrows(IllegalArgumentException.class, () -> new Link("Text", "", new ArrayList<>()));
    }

  }

  @Nested
  @DisplayName("Getters Test")
  class LinkGettersTest {
    @Test
    @DisplayName("Test getText")
    void getText() {
      Link link = new Link("Text", "Content", new ArrayList<>());
      assertEquals("Text", link.getText());
    }

    @Test
    @DisplayName("Test getReference")
    void getReference() {
      Link link = new Link("Text", "Content", new ArrayList<>());
      assertEquals("Content", link.getReference());
    }

    @Test
    @DisplayName("Test getActions")
    void getActions() {
      Link link = new Link("Text", "Content", new ArrayList<>());
      assertEquals(0, link.getActions().size());
    }
  }

  @Nested
  @DisplayName("Adders Test")
  class LinkAddersTest {
    @Test
    @DisplayName("Test addAction")
      // Vet ikke hvordan jeg skal teste denne
    void addAction() {
      Link link = new Link("Text", "Content", new ArrayList<>());
      assertEquals(0, link.getActions().size());
    }


    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
  }
}


