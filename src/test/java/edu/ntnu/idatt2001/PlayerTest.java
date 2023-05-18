package edu.ntnu.idatt2001;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PlayerTest {
  @Nested
  @DisplayName("Constructor Test")
  class PlayerConstructorTest {

    @Test
    @DisplayName("Test valid constructor")
    void constructorTest() {
      Player player = new Player("Vegard", 100, 0, 0, new ArrayList<>());
      assertEquals("Vegard", player.getName());
      assertEquals(100, player.getHealth());
      assertEquals(0, player.getScore());
      assertEquals(0, player.getInventory().size());
    }

    @Test
    @DisplayName("Test constructor with null name")
    void constructorTestWithNullName() {
      assertThrows(NullPointerException.class, () -> new Player(null, 100, 0, 0));
    }

    @Test
    @DisplayName("Test constructor with empty name")
    void constructorTestWithEmptyName() {
      assertThrows(NullPointerException.class, () -> new Player("", 100, 0, 0));
    }

    @Test
    @DisplayName("Test constructor with negative health")
    void constructorTestWithNegativeHealth() {
      assertThrows(IllegalArgumentException.class, () -> new Player("Vegard", -1, 0, 0));
    }

    @Test
    @DisplayName("Test constructor with negative score")
    void constructorTestWithNegativeScore() {
      assertThrows(IllegalArgumentException.class, () -> new Player("Vegard", 100, -1, 0));
    }
  }

  @Nested
  @DisplayName("Getters Test")
  class PlayerGettersTest {

    @Test
    @DisplayName("Test getName")
    void getName() {
      Player player = new Player("Vegard", 100, 0, 0, new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      assertEquals("Vegard", player.getName());
    }

    @Test
    @DisplayName("Test getHealth")
    void getHealth() {
      Player player = new Player("Vegard", 100, 0, 0, new ArrayList<>(List.of("Sword", "Shield", "Potion")) );
      assertEquals(100, player.getHealth());
    }

    @Test
    @DisplayName("Test getScore")
    void getScore() {
      Player player = new Player("Vegard", 100, 0, 0,new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      assertEquals(0, player.getScore());
    }

    @Test
    @DisplayName("Test getInventory")
    void getInventory() {
      Player player = new Player("Vegard", 100, 0, 0,new ArrayList<>());
      ArrayList<String> inventory = new ArrayList<>();
      inventory.add("Sword");
      player.addToInventory("Sword");
      assertEquals(inventory, player.getInventory());
    }

    @Test
    @DisplayName("Test getGold")
    void getGold() {
      Player player = new Player("Vegard", 100, 0, 0, new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      assertEquals(0, player.getGold());
    }

  }


  @Nested
  @DisplayName("Adders Test")
  class PlayerAddersTest {

    @Test
    @DisplayName("Test addToInventory")
    void addToInventory() {
      Player player = new Player("Vegard", 100, 0, 0, new ArrayList<>());
      player.addToInventory("Sword");
      assertEquals(1, player.getInventory().size());
    }

    @Test
    @DisplayName("Test addGold")
    void addGold() {
      Player player = new Player("Vegard", 100, 0, 0, new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      player.addGold(10);
      assertEquals(10, player.getGold());
    }

    @Test
    @DisplayName("Test addHealth")
    void addHealth() {
      Player player = new Player("Vegard", 100, 0, 0, new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      player.addHealth(10);
      assertEquals(110, player.getHealth());
    }

    @Test
    @DisplayName("Test addScore")
    void addScore() {
      Player player = new Player("Vegard", 100, 0, 0,new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      player.addScore(10);
      assertEquals(10, player.getScore());
    }
  }

  @Nested
  @DisplayName("Setters Test")
  class PlayerSettersTest {

    @Test
    @DisplayName("Test valid setHealth")
    void setHealth() {
      Player player = new Player("Vegard", 100, 0, 0, new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      player.setHealth(100);
      assertEquals(100, player.getHealth());
    }

    @Test
    @DisplayName("Test setHealth with negative health")
    void setHealthWithNegativeHealth() {
      Player player = new Player("Vegard", 100, 0, 0, new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      assertThrows(IllegalArgumentException.class, () -> player.setHealth(-1));
    }

    @Test
    @DisplayName("Test valid setScore")
    void setScore() {
      Player player = new Player("Vegard", 100, 0, 0,new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      player.setScore(0);
      assertEquals(0, player.getScore());
    }

    @Test
    @DisplayName("Test setScore with negative score")
    void setScoreWithNegativeScore() {
      Player player = new Player("Vegard", 100, 0, 0,new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      assertThrows(IllegalArgumentException.class, () -> player.setScore(-1));
    }

    @Test
    @DisplayName("Test valid setInventory")
    void setInventory() {
      Player player = new Player("Vegard", 100, 0, 0,new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      ArrayList<String> inventory = new ArrayList<>();
      inventory.add("Sword");
      player.setInventory(inventory);
      assertEquals(inventory, player.getInventory());
    }

    @Test
    @DisplayName("Test setInventory with null inventory")
    void setInventoryWithNullInventory() {
      Player player = new Player("Vegard", 100, 0, 0,new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      assertThrows(IllegalArgumentException.class, () -> player.setInventory(null));
    }

    @Test
    @DisplayName("Test setGold")
    void setGold() {
      Player player = new Player("Vegard", 100, 0, 0,new ArrayList<>(List.of("Sword", "Shield", "Potion")));
      player.setGold(0);
      assertEquals(0, player.getGold());
    }
  }
}




