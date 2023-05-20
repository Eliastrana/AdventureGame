package edu.ntnu.idatt2001.Action;

import edu.ntnu.idatt2001.model.action.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionsFactoryTest {
  private ActionsFactory actionsFactory;

  @BeforeEach
  public void setUp() {
    actionsFactory = new ActionsFactory();
  }

  @Test
  public void testCreateAction_ReturnsHealthAction() {
    Action action = actionsFactory.createAction("{HealthAction: 50}");
    assertTrue(action instanceof HealthAction);
  }

  @Test
  public void testCreateAction_ReturnsGoldAction() {
    Action action = actionsFactory.createAction("{GoldAction: 100}");
    assertTrue(action instanceof GoldAction);
  }

  @Test
  public void testCreateAction_ReturnsScoreAction() {
    Action action = actionsFactory.createAction("{ScoreAction: 10}");
    assertTrue(action instanceof ScoreAction);
  }

  @Test
  public void testCreateAction_ReturnsInventoryAction() {
    Action action = actionsFactory.createAction("{InventoryAction: item}");
    assertTrue(action instanceof InventoryAction);
  }

  @Test
  public void testCreateAction_ReturnsNullForInvalidInput() {
    Action action = actionsFactory.createAction(null);
    assertNull(action);
  }

  @Test
  public void testCreateAction_ReturnsNullForEmptyInput() {
    Action action = actionsFactory.createAction("");
    assertNull(action);
  }

  @Test
  public void testCreateAction_ReturnsNullForInvalidActionType() {
    assertThrows(IllegalArgumentException.class, () -> {
      actionsFactory.createAction("{UnknownAction: value}");
    });
  }

  @Test
  public void testCreateAction_ReturnsNullForInvalidValueFormat() {
    assertThrows(IllegalArgumentException.class, () -> {
      actionsFactory.createAction("{HealthAction: invalid}");
    });
  }

  // Additional Tests

  @Test
  public void testCreateAction_ThrowsExceptionForInvalidHealthValue() {
    assertThrows(IllegalArgumentException.class, () -> {
      actionsFactory.createAction("{HealthAction: invalid}");
    });
  }

  @Test
  public void testCreateAction_ThrowsExceptionForInvalidGoldValue() {
    assertThrows(IllegalArgumentException.class, () -> {
      actionsFactory.createAction("{GoldAction: invalid}");
    });
  }

  @Test
  public void testCreateAction_ThrowsExceptionForInvalidScoreValue() {
    assertThrows(IllegalArgumentException.class, () -> {
      actionsFactory.createAction("{ScoreAction: invalid}");
    });
  }
}
