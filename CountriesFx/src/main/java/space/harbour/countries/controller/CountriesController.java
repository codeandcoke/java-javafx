package space.harbour.countries.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import space.harbour.countries.domain.Country;
import space.harbour.countries.service.CountriesApiService;
import space.harbour.countries.service.CountriesService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CountriesController implements Initializable {

    public TextField countrySearchTextField;
    public Button searchButton;
    public TableView<Country> dataTable;
    public Label statusLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareTableView();

        CountriesService countriesService = new CountriesService();
        List<Country> countryList = countriesService.getAllCountries();
        dataTable.setItems(FXCollections.observableArrayList(countryList));
    }

    private void prepareTableView() {
        TableColumn<Country, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Country, String> regionColumn = new TableColumn<>("Region");
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        TableColumn<Country, String> subregionColumn = new TableColumn<>("Subregion");
        subregionColumn.setCellValueFactory(new PropertyValueFactory<>("subregion"));
        TableColumn<Country, Integer> populationColumn = new TableColumn<>("Population");
        populationColumn.setCellValueFactory(new PropertyValueFactory<>("population"));
        // TODO Add as many columns as you need

        dataTable.getColumns().add(nameColumn);
        dataTable.getColumns().add(regionColumn);
        dataTable.getColumns().add(subregionColumn);
        dataTable.getColumns().add(populationColumn);
    }

    @FXML
    public void searchCountry(ActionEvent event) {
        String searchText = countrySearchTextField.getText();
        // TODO Call API operation to get information about a specific country and populate the TableView

        statusLabel.setText("Looking for " + searchText);
    }

    @FXML
    public void tableViewMouseClicked(MouseEvent event) {
        // TODO If the user double click, call API to get the information about the selected country
        if (event.getClickCount() == 2) {
            Country selectedCountry = dataTable.getSelectionModel().getSelectedItem();
            // Only for test purpose
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have selected " + selectedCountry.getName());
            alert.show();
        }
    }
}
