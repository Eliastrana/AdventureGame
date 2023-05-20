package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.model.Action.ScoreAction;
import edu.ntnu.idatt2001.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreActionTest {
    private ScoreAction scoreAction;
    private Player player;

    @BeforeEach
    public void setUp() {
        scoreAction = new ScoreAction(10);
        player = new Player("TestName", 100, 100, 100, new ArrayList<>());
    }

    @Test
    public void testExecute_AddsScoreToPlayer() {
        scoreAction.execute(player);
        assertEquals(110, player.getScore());
    }

    @Test
    public void testExecute_ThrowsExceptionIfPlayerIsNull() {
        scoreAction = new ScoreAction(10);
        assertThrows(IllegalArgumentException.class, () -> scoreAction.execute(null));
    }
}
