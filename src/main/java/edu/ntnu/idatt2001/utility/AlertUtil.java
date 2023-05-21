package edu.ntnu.idatt2001.utility;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AlertUtil {

    public static void showAlert(String title, String message, double x, double y, Window ownerWindow) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(null);

        // Set white background
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getScene().getRoot().setStyle("-fx-background-color: #ffffff;");

        positionDialog(dialog, x, y);

        if (ownerWindow != null) {
            if (isMacOS()) {
                dialog.initOwner(ownerWindow);
                dialog.initModality(Modality.APPLICATION_MODAL);
            } else {
                // Set the owner window for non-MacOS platforms
                dialog.initOwner(ownerWindow);
            }
        }

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 25px; -fx-effect: dropshadow(gaussian, black, 15, 0, 0, 0);");
        dialog.getDialogPane().setContent(label);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        dialog.showAndWait();
    }

    private static void positionDialog(Dialog<?> dialog, double x, double y) {
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.setX(x);
        stage.setY(y);
    }

    private static boolean isMacOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("mac");
    }
}
