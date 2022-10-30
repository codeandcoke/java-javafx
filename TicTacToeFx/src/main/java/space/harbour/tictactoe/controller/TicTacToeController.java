package space.harbour.tictactoe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import space.harbour.tictactoe.util.Resources;

import java.net.URL;
import java.util.ResourceBundle;

public class TicTacToeController implements Initializable {

    public ImageView tile00;
    public ImageView tile01;
    public ImageView tile02;

    public TicTacToeController() {

    }

    @FXML
    public void handleActionButtonClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Specific initializer executed after UI componentes instantiation
    }

    @FXML
    public void tile00MouseClicked(MouseEvent event) {
        tile00.setImage(new Image(Resources.getImage("x.png")));
    }

    @FXML
    public void tile01MouseClicked(MouseEvent event) {
    }

    @FXML
    public void tile02MouseClicked(MouseEvent event) {

    }

}
