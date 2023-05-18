package edu.ntnu.idatt2001.goals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoalFactoryTest {

  private GoalFactory factory;

  @BeforeEach
  void setUp() {
    factory = new GoalFactory();
  }

  @Test
  void createGoalReturnsHealthGoal() {
    Goal goal = factory.createGoal("HealthGoal: 100");
    assertTrue(goal instanceof HealthGoal);
  }

  @Test
  void createGoalReturnsGoldGoal() {
    Goal goal = factory.createGoal("GoldGoal: 100");
    assertTrue(goal instanceof GoldGoal);
  }

  @Test
  void createGoalReturnsInventoryGoal() {
    Goal goal = factory.createGoal("InventoryGoal: 100");
    assertTrue(goal instanceof InventoryGoal);
  }

  @Test
  void createGoalReturnsScoreGoal() {
    Goal goal = factory.createGoal("ScoreGoal: 100");
    assertTrue(goal instanceof ScoreGoal);
  }

  @Test
  void createGoalThrowsExceptionForInvalidInput() {
    assertThrows(IllegalArgumentException.class, () -> factory.createGoal("InvalidGoal: 100"));
  }


  @Test
  void createGoalReturnsNullForEmptyInput() {
    Goal goal = factory.createGoal("");
    assertNull(goal);
  }

  @Test
  void createGoalReturnsNullForNullInput() {
    Goal goal = factory.createGoal(null);
    assertNull(goal);
  }
}
