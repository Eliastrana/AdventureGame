package edu.ntnu.idatt2001.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.Optional;

/**
 * A utility class for showing alerts.
 */
public class AlertUtil {

  /**
   * Shows an alert with the given title and message in fullscreen.
   *
   * @param title       title of the alert
   * @param message     message of the alert
   * @param x           x position of the alert
   * @param y           y position of the alert
   * @param ownerWindow the owner window of the alert
   */
  public static void showAlert(String title,
                               String message,
                               double x,
                               double y,
                               Window ownerWindow) {
    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle(title);
    dialog.setHeaderText(null);

    // Set white background
    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
    stage.getScene().getRoot().setStyle("-fx-background-color: #ffffff;");

    positionDialog(dialog, x, y);

    if (ownerWindow != null) {
      if (isMacOs()) {
        dialog.initOwner(ownerWindow);
        dialog.initModality(Modality.APPLICATION_MODAL);
      } else {
        // Set the owner window for non-MacOS platforms
        dialog.initOwner(ownerWindow);
      }
    }

    Label label = new Label(message);
    label.setStyle("-fx-text-fill: white; -fx-font-size: 25px; "
            + "-fx-effect: dropshadow(gaussian, black, 15, 0, 0, 0);");
    dialog.getDialogPane().setContent(label);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    dialog.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
    dialog.showAndWait();
  }

  /**
   * Positions the dialog at the given x and y coordinates.
   *
   * @param dialog the dialog to position
   * @param x      double x coordinate
   * @param y      double y coordinate
   */
  private static void positionDialog(Dialog<?> dialog, double x, double y) {
    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
    stage.setX(x);
    stage.setY(y);
  }

  /**
   * Returns true if the current operating system is MacOS.
   *
   * @return true if the current operating system is MacOS
   */
  private static boolean isMacOs() {
    String osName = System.getProperty("os.name").toLowerCase();
    return osName.contains("mac");
  }


  /**
   * Shows an infobox with the given title and message.
   *
   * @param alertTitle  title of the alert
   * @param alertHeader message of the alert
   * @param message     message of the alert
   */
  public static void showAlertBoxInfo(String alertTitle, String alertHeader, String message) {
    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
    alertInfo.setTitle(alertTitle);
    alertInfo.setHeaderText(alertHeader);
    alertInfo.setContentText(message);
    alertInfo.showAndWait();
  }

  /**
   * Shows an error-box with the given title and message.
   *
   * @param alertTitle  title of the alert
   * @param alertHeader message of the alert
   * @param message     message of the alert
   */
  public static void showAlertBoxError(String alertTitle, String alertHeader, String message) {
    Alert alertError = new Alert(Alert.AlertType.ERROR);
    alertError.setTitle(alertTitle);
    alertError.setHeaderText(alertHeader);
    alertError.setContentText(message);
    alertError.showAndWait();
    return;
  }

  //an alertbox that lets the user cancel of press ok




  public static Optional<ButtonType> showAlertBoxConfirmation(String alertTitle, String alertHeader, String message) {
    Alert alertError = new Alert(Alert.AlertType.ERROR);
    alertError.initStyle(StageStyle.UTILITY);
    alertError.initModality(Modality.APPLICATION_MODAL);
    alertError.setTitle(alertTitle);
    alertError.setHeaderText(alertHeader);
    alertError.setContentText(message);

    ButtonType buttonTypeCancel = new ButtonType("Cancel");
    alertError.getButtonTypes().setAll(buttonTypeCancel);
    alertError.showAndWait();
    return alertError.showAndWait();
  }
}
