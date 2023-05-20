package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.goals.InventoryGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryGoalTest {

    private InventoryGoal inventoryGoal;
    private Player player;

    @BeforeEach
    void setUp() {
        inventoryGoal = new InventoryGoal(3);
        player = new Player("TestName", 100, 100, 100, new ArrayList<>());
    }

    @Test
    void testIsFullfilled() {
        player.addToInventory("item1");
        assertFalse(inventoryGoal.isFullfilled(player));

        player.addToInventory("item2");
        assertFalse(inventoryGoal.isFullfilled(player));

        player.addToInventory("item3");
        assertTrue(inventoryGoal.isFullfilled(player));
    }
}
