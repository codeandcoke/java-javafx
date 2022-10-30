package space.harbour.mymap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import space.harbour.mymap.controller.MyMapController;
import space.harbour.mymap.util.Resources;

import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MyMapApp extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Resources.getUI("mymap.fxml"));
        MyMapController myMapController = new MyMapController();
        loader.setController(myMapController);
        BorderPane pane = loader.load();

        stage.setOnShown(event -> myMapController.doSomething());

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("MyMap");
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
