package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.model.action.HealthAction;
import edu.ntnu.idatt2001.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HealthActionTest {
    private HealthAction healthAction;
    private Player player;

    @BeforeEach
    public void setUp() {
        healthAction = new HealthAction(10);
        player = new Player("TestName", 100, 100, 100, new ArrayList<>());
    }

    @Test
    public void testExecute_AddsHealthToPlayer() {
        healthAction.execute(player);
        assertEquals(110, player.getHealth());
    }

    @Test
    public void testExecute_ThrowsExceptionIfPlayerIsNull() {
        healthAction = new HealthAction(10);
        assertThrows(IllegalArgumentException.class, () -> healthAction.execute(null));
    }
}
