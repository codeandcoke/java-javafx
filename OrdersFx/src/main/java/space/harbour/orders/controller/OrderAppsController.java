package space.harbour.orders.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import space.harbour.orders.domain.Order;
import space.harbour.orders.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrderAppsController implements Initializable {

    public TextField searchTextField;
    public Button searchButton;
    public Label statusLabel;
    public TableView<Order> dataTable;
    private List<Order> orderList;

    public OrderAppsController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Specific initializer executed after UI componentes instantiation
        prepareTableView();
    }

    private void prepareTableView() {
        TableColumn<Order, String> idColumn = new TableColumn<>("Order Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Order, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Order, String> customerNameColumn = new TableColumn<>("Custoner name");
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableColumn<Order, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        dataTable.getColumns().add(idColumn);
        dataTable.getColumns().add(dateColumn);
        dataTable.getColumns().add(customerNameColumn);
        dataTable.getColumns().add(priceColumn);
    }

    @FXML
    public void searchCustomer(ActionEvent event) {
        String customerName = searchTextField.getText();
        if (customerName.equals("")) {
            // TODO Show an error
            return;
        }
        if (orderList == null) {
            // TODO Show an error
            return;
        }
        if (orderList.size() == 0) {
            // TODO Show a warning message
            return;
        }
        List<Order> customerOrderList = orderList.stream()
                .filter(order -> order.getCustomerName().equals(customerName))
                .collect(Collectors.toList());
        if (customerOrderList.size() == 0) {
            // TODO Show a warning message "No data"
            statusLabel.setText("No data for Customer: " + customerName);
            dataTable.getItems().clear();
            return;
        }
        dataTable.setItems(FXCollections.observableArrayList(customerOrderList));
        statusLabel.setText("Data loaded for Customer: " + customerName);
    }

    @FXML
    public void onSearchTextFieldKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            searchCustomer(null);
    }

    @FXML
    public void loadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(statusLabel.getScene().getWindow());
        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a valid file");
            alert.setHeaderText("Error while loading file");
            alert.setTitle("Error while loading file");
            alert.show();
            return;
        }

        try {
            orderList = FileUtils.readOrdersFile(file.getAbsolutePath());
            dataTable.setItems(FXCollections.observableArrayList(orderList));
            statusLabel.setText("Orders loaded!");
        } catch (FileNotFoundException fnfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File not found");
            alert.setHeaderText("Error while loading file");
            alert.setTitle("Error while loading file");
            alert.show();
        } catch (IOException ioe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error while reading file");
            alert.setHeaderText("Error while loading file");
            alert.setTitle("Error while loading file");
            alert.show();
        }
    }

    @FXML
    public void quit(ActionEvent event) {
        // TODO Are you sure?
        System.exit(0);
    }
}
