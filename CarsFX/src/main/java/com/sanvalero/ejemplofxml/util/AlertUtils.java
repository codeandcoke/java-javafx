package com.sanvalero.ejemplofxml.util;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
