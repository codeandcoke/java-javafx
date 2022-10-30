package com.sanvalero.ejemplofxml;

import com.sanvalero.ejemplofxml.db.CarDAO;
import com.sanvalero.ejemplofxml.domain.Car;
import com.sanvalero.ejemplofxml.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AppController {

    public TextField licenseTextField;
    public TextField brandTextField;
    public TextField modelTextField;
    public ComboBox<String> typeComboBox;
    public ListView<Car> carsListView;
    public Label statusLabel;
    public Button newButton, editButton, saveButton, deleteButton;
    private enum Accion {
        NEW, MODIFY
    }
    private Accion action;

    private CarDAO carDAO;
    private Car selectedCar;

    public AppController() {
        carDAO = new CarDAO();
        try {
            carDAO.connect();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            AlertUtils.showError("An error has occurred while connecting to the database");
        } catch (ClassNotFoundException cnfe) {
            AlertUtils.showError("Error while initializing application");
        } catch (IOException ioe) {
            AlertUtils.showError("Error while loading configuration");
        }
    }

    public void loadData() {
        editMode(false);

        carsListView.getItems().clear();
        try {
            List<Car> cars = carDAO.getCars();
            carsListView.setItems(FXCollections.observableList(cars));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            AlertUtils.showError("Error has occurred while loading data");
        }

        String[] carTypes = new String[]{"<Select type>", "Car", "Sport car", "Truck", "Van"};
        typeComboBox.setItems(FXCollections.observableArrayList(carTypes));
    }

    @FXML
    public void newCar(Event event) {
        clearTextFields();
        editMode(true);
        action = Accion.NEW;
    }

    @FXML
    public void editCar(Event event) {
        editMode(true);
        action = Accion.MODIFY;
    }

    @FXML
    public void deleteCar(Event event) {
        Car car = carsListView.getSelectionModel().getSelectedItem();
        if (car == null) {
            statusLabel.setText("No car was selected");
            return;
        }

        try {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete car");
            confirmationAlert.setContentText("Are you sure?");
            Optional<ButtonType> answer = confirmationAlert.showAndWait();
            if (answer.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                return;

            carDAO.deleteCar(car);
            statusLabel.setText("Car was delete successfully");

            loadData();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            AlertUtils.showError("Error! The cars was not deleted");
        }
    }

    @FXML
    public void saveCar(Event event) {
        String license = licenseTextField.getText();
        if (license.equals("")) {
            AlertUtils.showError("The license is mandatory");
            return;
        }
        String brand = brandTextField.getText();
        String model = modelTextField.getText();
        String type = typeComboBox.getSelectionModel().getSelectedItem();
        Car car = new Car(license, brand, model, type);

        try {
            switch (action) {
                case NEW:
                    carDAO.saveCar(car);
                    break;
                case MODIFY:
                    carDAO.editCar(selectedCar, car);
                    break;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            AlertUtils.showError("An error has occurred while saving car data");
        }

        //stateLabel.setText("The cars was saved successfully");
        loadData();

        editMode(false);
    }

    @FXML
    public void cancel() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Edit");
        confirmationAlert.setContentText("Are you sure?");
        Optional<ButtonType> answer = confirmationAlert.showAndWait();
        if (answer.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
            return;

        editMode(false);
        loadCar(selectedCar);
    }

    private void loadCar(Car car) {
        licenseTextField.setText(car.getLicense());
        brandTextField.setText(car.getBrand());
        modelTextField.setText(car.getModel());
        typeComboBox.setValue(car.getType());
    }

    @FXML
    public void selectCar(Event event) {
        selectedCar = carsListView.getSelectionModel().getSelectedItem();
        loadCar(selectedCar);
    }

    @FXML
    public void importData(ActionEvent event) {

    }

    @FXML
    public void exportData(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(null);

            FileWriter fileWriter = new FileWriter(file);
            CSVPrinter printer = new CSVPrinter(fileWriter,
                    CSVFormat.DEFAULT.withHeader("id", "license", "brand", "model", "type"));

            List<Car> coches = carDAO.getCars();
            for (Car car : coches)
                printer.printRecord(car.getId(), car.getLicense(), car.getBrand(),
                        car.getModel(), car.getType());

            printer.close();
        } catch (SQLException sqle) {
            AlertUtils.showError("Error connecting to the database");
        } catch (IOException ioe) {
            AlertUtils.showError("Error while exporting data");
        }
    }

    private void clearTextFields() {
        licenseTextField.setText("");
        modelTextField.setText("");
        brandTextField.setText("");
        typeComboBox.setValue("<Select type>");
        licenseTextField.requestFocus();
    }

    private void editMode(boolean enabled) {
        newButton.setDisable(enabled);
        saveButton.setDisable(!enabled);
        editButton.setDisable(enabled);
        deleteButton.setDisable(enabled);

        licenseTextField.setEditable(enabled);
        brandTextField.setEditable(enabled);
        modelTextField.setEditable(enabled);
        typeComboBox.setDisable(!enabled);

        carsListView.setDisable(enabled);
    }
}
