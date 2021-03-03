package com.sanvalero.ejemplofxml;

import com.sanvalero.ejemplofxml.domain.Coche;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AppController {

    public TextField tfMatricula;
    public TextField tfMarca;
    public TextField tfModelo;
    public ComboBox<String> cbTipo;
    public VBox vBox;

    private CocheDAO cocheDAO;

    public AppController() {
        cocheDAO = new CocheDAO();
        cocheDAO.conectar();
    }

    @FXML
    public void nuevoCoche(Event event) {

    }

    @FXML
    public void modificarCoche(Event event) {

    }

    @FXML
    public void eliminarCoche(Event event) {

    }

    @FXML
    public void guardarCoche(Event event) {
        String matricula = tfMatricula.getText();
        if (matricula.equals("")) {
            // TODO Error de que falta indicar la matricula como campo obligatorio
        }
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();

        Coche coche = new Coche(matricula, marca, modelo, tipo);
        cocheDAO.guardarCoche(coche);
    }
}
