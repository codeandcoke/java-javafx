package space.harbour.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import space.harbour.tictactoe.controller.TicTacToeController;
import space.harbour.tictactoe.util.Resources;

/**
 * TicTacToeFx template with package support (.jar file)
 * @author Santiago Faci
 * @version November 2021
 */
public class TicTacToeApp extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Resources.getUI("TicTacToe.fxml"));
        loader.setController(new TicTacToeController());
        BorderPane pane = loader.load();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("TicTacToeFx");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
            launch();
        }
}
