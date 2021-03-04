package com.sanvalero.ejemplofxml;

import com.sanvalero.ejemplofxml.domain.Coche;
import com.sanvalero.ejemplofxml.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    public TextField tfMatricula;
    public TextField tfMarca;
    public TextField tfModelo;
    public ComboBox<String> cbTipo;
    public TableView<Coche> tvCoches;
    public Label lbEstado;
    public Button btNuevo, btModificar, btGuardar, btEliminar;

    private enum Accion {
        NUEVO, MODIFICAR
    }
    private Accion accion;

    private CocheDAO cocheDAO;
    private Coche cocheSeleccionado;

    public AppController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cocheDAO = new CocheDAO();
        cocheDAO.conectar();

        fijarColumnasTabla();

        cargarDatos();

    }

    private void fijarColumnasTabla() {
        Field[] fields = Coche.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id"))
                continue;

            TableColumn<Coche, String> column = new TableColumn<>("[" + field.getName() + "]");
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tvCoches.getColumns().add(column);
        }

        tvCoches.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void cargarDatos() {
        modoEdicion(false);

        tvCoches.getItems().clear();
        List<Coche> coches = cocheDAO.obtenerCoches();
        tvCoches.setItems(FXCollections.observableArrayList(coches));

        String[] tipos = new String[]{"<Selecciona tipo>", "Turismo", "Deportivo", "Camión", "Furgoneta"};
        cbTipo.setItems(FXCollections.observableArrayList(tipos));
    }

    @FXML
    public void nuevoCoche(Event event) {
        limpiarCajas();
        modoEdicion(true);
        accion = Accion.NUEVO;
    }

    @FXML
    public void modificarCoche(Event event) {
        modoEdicion(true);
        accion = Accion.MODIFICAR;
    }

    @FXML
    public void eliminarCoche(Event event) {
        Coche coche = tvCoches.getSelectionModel().getSelectedItem();
        if (coche == null) {
            lbEstado.setText("No se ha seleccionado ningún coche");
            return;
        }

        try {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Eliminar coche");
            confirmacion.setContentText("¿Estás seguro?");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                return;

            cocheDAO.eliminarCoche(coche);
            lbEstado.setText("Coche eliminado con éxito");

            cargarDatos();
        } catch (SQLException sqle) {
            AlertUtils.mostrarError("No se ha podido eliminar el coche");
        }
    }

    @FXML
    public void guardarCoche(Event event) {
        String matricula = tfMatricula.getText();
        if (matricula.equals("")) {
            AlertUtils.mostrarError("La matricula es un campo obligatorio");
            return;
        }
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();
        Coche coche = new Coche(matricula, marca, modelo, tipo);

        try {
            switch (accion) {
                case NUEVO:
                    cocheDAO.guardarCoche(coche);
                    break;
                case MODIFICAR:
                    cocheDAO.modificarCoche(cocheSeleccionado, coche);
                    break;
            }
        } catch (SQLException sqle) {
            AlertUtils.mostrarError("Error al guadar el coche");
        }

        lbEstado.setText("Coche guardado con éxito");
        cargarDatos();

        modoEdicion(false);
    }

    @FXML
    public void cancelar() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Edición");
        confirmacion.setContentText("¿Estás seguro?");
        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
            return;

        modoEdicion(false);
        cargarCoche(cocheSeleccionado);
    }

    private void cargarCoche(Coche coche) {
        tfMatricula.setText(coche.getMatricula());
        tfMarca.setText(coche.getMarca());
        tfModelo.setText(coche.getModelo());
        cbTipo.setValue(coche.getTipo());
    }

    @FXML
    public void seleccionarCoche(Event event) {
        cocheSeleccionado = tvCoches.getSelectionModel().getSelectedItem();
        cargarCoche(cocheSeleccionado);
    }

    @FXML
    public void importar(ActionEvent event) {

    }

    @FXML
    public void exportar(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            File fichero = fileChooser.showSaveDialog(null);

            FileWriter fileWriter = new FileWriter(fichero);
            CSVPrinter printer = new CSVPrinter(fileWriter,
                    CSVFormat.DEFAULT.withHeader("id", "matricula", "marca", "modelo", "tipo"));

            List<Coche> coches = cocheDAO.obtenerCoches();
            for (Coche coche : coches)
                printer.printRecord(coche.getId(), coche.getMatricula(), coche.getMarca(),
                        coche.getModelo(), coche.getTipo());

            printer.close();
        } catch (IOException ioe) {
            AlertUtils.mostrarError("Error al exportar los datos");
        }
    }

    /**
     * Boceto de cómo mostrar un Dialog
     */
    public void mostrarDialogo() {
        Dialog dialog = new Dialog();
        dialog.setTitle("hola Aitor");
        dialog.setContentText("hola a todos");
        dialog.show();
    }

    private void limpiarCajas() {
        tfMatricula.setText("");
        tfModelo.setText("");
        tfMarca.setText("");
        cbTipo.setValue("<Selecciona tipo>");
        tfMatricula.requestFocus();
    }

    private void modoEdicion(boolean activar) {
        btNuevo.setDisable(activar);
        btGuardar.setDisable(!activar);
        btModificar.setDisable(activar);
        btEliminar.setDisable(activar);

        tfMatricula.setEditable(activar);
        tfMarca.setEditable(activar);
        tfModelo.setEditable(activar);
        cbTipo.setDisable(!activar);

        tvCoches.setDisable(activar);
    }
}
