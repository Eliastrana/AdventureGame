package edu.ntnu.idatt2001.utility;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AlertUtil {



    public static void showAlert(String title, String message, double x, double y, Window ownerWindow) {


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Set white background
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getScene().getRoot().setStyle("-fx-background-color: white;");

        positionAlert(alert, x, y);

        if (ownerWindow != null && isMacOS()) {
            alert.initOwner(ownerWindow);
            alert.initModality(Modality.APPLICATION_MODAL);
        }

        // Customize font size and color
        alert.getDialogPane().setStyle("-fx-font-size: 18px; -fx-text-fill: #000000; -fx-stroke: #FFFFFF; -fx-stroke-width: 18px;");

        alert.getDialogPane().setId("alertBox");

        alert.showAndWait();
    }

    private static void positionAlert(Alert alert, double x, double y) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setX(x);
        stage.setY(y);
    }

    private static boolean isMacOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("mac");
    }
}
