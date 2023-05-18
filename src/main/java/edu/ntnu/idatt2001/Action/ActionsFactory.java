package edu.ntnu.idatt2001.Action;

/**
 * ActionsFactory for creating different actions.
 */
public class ActionsFactory {

  /**
   * Creates an action from the given input.
   * @param input String {actionType: actionValue}.
   *
   * @return Action the created action.
   *
   */
  public Action createAction(String input) {
    if (input == null || input.trim().isEmpty()) {
      return null;
    }

    input = input.trim();
    if (input.startsWith("{") && input.endsWith("}")) {
      String actionData = input.substring(1, input.length() - 1).trim();
      String[] actionParts = actionData.split(":", 2);

      if (actionParts.length == 2) {
        String actionType = actionParts[0].trim();
        String actionValue = actionParts[1].trim();

        switch (actionType) {
          case "HealthAction":
            try {
              int health = Integer.parseInt(actionValue);
              return new HealthAction(health);
            } catch (NumberFormatException e) {
             throw new IllegalArgumentException("Invalid health value for HealthAction: " + actionValue);
            }



          case "GoldAction":
            try {
              int gold = Integer.parseInt(actionValue);
              return new GoldAction(gold);
            } catch (NumberFormatException e) {
              throw new IllegalArgumentException("Invalid gold value for GoldAction: " + actionValue);
            }


          case "ScoreAction":
            try {
              int score = Integer.parseInt(actionValue);
              return new ScoreAction(score);
            } catch (NumberFormatException e) {
              throw new IllegalArgumentException("Invalid score value for ScoreAction: " + actionValue);
            }

          case "InventoryAction":
            try {
              return new InventoryAction(actionValue);
            } catch (NumberFormatException e) {
             throw new IllegalArgumentException("Invalid inventory value for InventoryAction: " + actionValue);
            }

          default:
            throw new IllegalArgumentException("Unknown action type: " + actionType);
        }
      }
    }

    return null;
  }
}
