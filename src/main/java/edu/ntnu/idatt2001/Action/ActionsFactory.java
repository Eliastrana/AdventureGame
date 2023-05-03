package edu.ntnu.idatt2001.Action;


public class ActionsFactory {

  public static Action createAction(String input) {
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
        System.out.println("actionType: " + actionType);
        System.out.println("actionValue: " + actionValue);
        System.out.println(actionType + " " + actionValue);

        switch (actionType) {
          case "HealthAction":
            try {
              int health = Integer.parseInt(actionValue);
              return new HealthAction(health);
            } catch (NumberFormatException e) {
              System.err.println("Invalid health value for HealthAction: " + actionValue);
            }
            break;
          // Add more action types here as needed

          case "GoldAction":
              try {
                  int gold = Integer.parseInt(actionValue);
                  return new GoldAction(gold);
              } catch (NumberFormatException e) {
                  System.err.println("Invalid gold value for GoldAction: " + actionValue);
              }
              break;

            case "ScoreAction":
                try {
                    int score = Integer.parseInt(actionValue);
                    return new ScoreAction(score);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid score value for ScoreAction: " + actionValue);
                }
                break;

          case "InventoryAction":
              try {
                return new InventoryAction(actionValue);
              } catch (NumberFormatException e) {
                  System.err.println("Invalid inventory value for InventoryAction: " + actionValue);
              }
              break;
          default:
            System.err.println("Unknown action type: " + actionType);
        }
      }
    }

    return null;
  }
}

