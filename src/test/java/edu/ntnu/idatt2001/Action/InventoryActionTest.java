package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.action.InventoryAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryActionTest {
  private InventoryAction inventoryAction;
  private Player player;

  @BeforeEach
  public void setUp() {
    inventoryAction = new InventoryAction("item");
    ArrayList<String> inventory = new ArrayList<>();
    player = new Player("TestName", 100, 100, 100, inventory);
  }

  @Test
  public void testExecute_AddsItemToPlayerInventory() {
    inventoryAction.execute(player);
    assertTrue(player.getInventory().contains("item"));
  }

  @Test
  public void testExecute_ThrowsExceptionIfPlayerIsNull() {
    inventoryAction = new InventoryAction("item");
    assertThrows(IllegalArgumentException.class, () -> inventoryAction.execute(null));
  }

  @Test
  public void testExecute_DoesNotAddItemToInventoryIfPlayerIsNull() {
    inventoryAction = new InventoryAction("item");
    assertThrows(IllegalArgumentException.class, () -> inventoryAction.execute(null));
    assertFalse(player.getInventory().contains("item"));
  }
}
