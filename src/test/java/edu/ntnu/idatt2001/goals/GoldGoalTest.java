package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GoldGoalTest {

    private GoldGoal goldGoal;
    private Player player;

    @BeforeEach
    void setUp() {
        goldGoal = new GoldGoal(100);
        player = new Player("TestName",100,100,100,new ArrayList<>());
    }

    @Test
    void testIsFullfilled() {
        player.setGold(80);
        assertFalse(goldGoal.isFullfilled(player));

        player.setGold(120);
        assertTrue(goldGoal.isFullfilled(player));
    }

    @Test
    void testIsFullfilledThrowsExceptionWhenPlayerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            goldGoal.isFullfilled(null);
        });
    }

    @Test
    void testToString() {
        assertEquals("100", goldGoal.toString());
    }
}
