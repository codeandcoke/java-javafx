package space.harbour.orders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import space.harbour.orders.controller.OrderAppsController;
import space.harbour.orders.util.Resources;

/**
 * OrdersFX application
 * @author Santiago Faci
 * @version November 2021
 */
public class OrdersApp extends Application {

    private static Logger logger = LogManager.getLogger(OrdersApp.class);

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Resources.getUI("MainWindow.fxml"));
        loader.setController(new OrderAppsController());
        BorderPane pane = loader.load();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("OrdersFx");
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
