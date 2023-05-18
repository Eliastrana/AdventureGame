package edu.ntnu.idatt2001.Action;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;


public class GoldActionTest {
    private GoldAction goldAction;
    private Player player;

    @BeforeEach
    public void setUp() {
        goldAction = new GoldAction(10);
        player = new Player("TestName", 100, 100, 100, new ArrayList<>());
    }

    @Test
    public void testExecute_AddsGoldToPlayer() {
        goldAction.execute(player);
        assertEquals(110, player.getGold());
    }

    @Test
    public void testExecute_ThrowsExceptionIfPlayerIsNull() {
        goldAction = new GoldAction(10);
        assertThrows(IllegalArgumentException.class, () -> goldAction.execute(null));
    }
}
