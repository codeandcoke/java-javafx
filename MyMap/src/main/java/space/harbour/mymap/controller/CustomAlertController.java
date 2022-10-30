package space.harbour.mymap.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomAlertController implements Initializable {

    public Label text;
    private String someText;
    private MyMapController mapController;
    private int amount;

    public CustomAlertController(MyMapController mapController, String someText, int amount) {
        this.mapController = mapController;
        this.someText = someText;
        this.amount = amount;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setText(someText);
    }

    @FXML
    public void yesAction(ActionEvent event) {
        mapController.money += amount;
    }

    @FXML
    public void ofCourseAction(ActionEvent event) {

    }
}
