package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.goals.HealthGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HealthGoalTest {

    private HealthGoal healthGoal;
    private Player player;

    @BeforeEach
    void setUp() {
        healthGoal = new HealthGoal(50);
        player = new Player("TestName", 100, 100, 100,new ArrayList<>());
    }

    @Test
    void testIsFullfilled() {
        player.setHealth(40);
        assertFalse(healthGoal.isFullfilled(player));

        player.setHealth(60);
        assertTrue(healthGoal.isFullfilled(player));
    }

    @Test
    void testIsFullfilledThrowsExceptionWhenPlayerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            healthGoal.isFullfilled(null);
        });
    }

    @Test
    void testToString() {
        assertEquals("50", healthGoal.toString());
    }
}
