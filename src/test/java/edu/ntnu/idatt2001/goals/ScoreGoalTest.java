package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreGoalTest {

    private ScoreGoal scoreGoal;
    private Player player;

    @BeforeEach
    void setUp() {
        scoreGoal = new ScoreGoal(10);
        player = new Player("TestName", 100,100,100,new ArrayList<>());
    }

    @Test
    void testIsFullfilled() {
        player.setScore(5);
        assertFalse(scoreGoal.isFullfilled(player));

        player.setScore(15);
        assertTrue(scoreGoal.isFullfilled(player));
    }

    @Test
    void testIsFullfilledThrowsExceptionWhenPlayerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreGoal.isFullfilled(null);
        });
    }

    @Test
    void testToString() {
        assertEquals("10", scoreGoal.toString());
    }
}
