package space.harbour.mymap.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.WorldMapView;
import space.harbour.mymap.util.Resources;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyMapController implements Initializable {

    public WorldMapView worldMap;
    private Connection connection;
    public int money;
    public int progress;
    public List<String> attackedCountries;
    public List<String> friends;

    public MyMapController() {
        attackedCountries = new ArrayList<>();
        friends = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO I have to do something here
        WorldMapView.Location location1 = new WorldMapView.Location("Zaragoza", 40, -0.8);
        WorldMapView.Location location2 = new WorldMapView.Location("Barcelona", 41.39, 2.07);
        WorldMapView.Location location3 = new WorldMapView.Location("Bucarest", 44.43, 25.95);
        worldMap.getLocations().add(location1);
        worldMap.getLocations().add(location2);
        worldMap.getLocations().add(location3);
    }

    @FXML
    public void selectLocation(MouseEvent event) {
        if (worldMap.getSelectedLocations().size() == 0)
            return;

        WorldMapView.Location selectedLocation = worldMap.getSelectedLocations().get(0);
        String cityName = selectedLocation.getName();
        System.out.println(cityName);
    }

    public void selectCountry(MouseEvent event) {
        // FIXME it doesn't work because bla bla bla
        List<WorldMapView.Location> selectedLocations = worldMap.getSelectedLocations();
        if (selectedLocations.size() == 0)
            return;

        WorldMapView.Location location = selectedLocations.get(0);
        System.out.println(location.getName());
        System.out.println(location.getLatitude());
        System.out.println(location.getLongitude());

    }

    private void loadCustomerAlert(String sceneFile, String someText, int money) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Resources.getUI(sceneFile));
        CustomAlertController customAlertController = new CustomAlertController(
                this, someText, money);
        loader.setController(customAlertController);

        try {
            VBox pane = loader.load();

            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Next challenge");
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void loadCountry(String countryCode) {
        if (countryCode.equals("Spain")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have selected Spain");
            alert.show();

        } else if (countryCode.equals("Portugal")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have selected Portugal");
            alert.show();
        }

    }

    public void doSomething() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Say something");
        alert.show();
    }
}
