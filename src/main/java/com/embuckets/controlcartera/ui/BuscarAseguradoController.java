/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class BuscarAseguradoController implements Initializable {

    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField parternoField;
    @FXML
    private TextField maternoField;
    @FXML
    private TableView<Asegurado> clienteTableView;
    @FXML
    private TableColumn nombreColumn;
    @FXML
    private TableColumn paternoColumn;
    @FXML
    private TableColumn maternoColumn;
    @FXML
    private TextField clienteSelectedField;

    private Asegurado asegurado;
    private Dialog<Asegurado> dialog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTablaAsegurados();
        crearDialog();
    }

    private void llenarTablaAsegurados() {
        clienteTableView.setItems(FXCollections.observableArrayList(getAllAsegurados()));

        nombreColumn.setCellValueFactory(new PropertyValueFactory("primerNombre"));
        paternoColumn.setCellValueFactory(new PropertyValueFactory("paterno"));
        maternoColumn.setCellValueFactory(new PropertyValueFactory("materno"));

        clienteTableView.setRowFactory((TableView<Asegurado> param) -> {
            final TableRow<Asegurado> row = new TableRow<>();
            row.setOnMouseClicked((event) -> {
                asegurado = row.getItem();
                clienteSelectedField.setText(asegurado.nombreProperty().get());
            });
//            row.onMouseClickedProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())));

            return row; //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    private void crearDialog() {
        dialog = new Dialog<>();
        dialog.setTitle("Buscar Asegurado");
        ButtonType seleccionarButtonType = new ButtonType("Seleccionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(seleccionarButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(dialogPane);
        dialog.setResultConverter((dialogButton) -> {
            if (dialogButton == seleccionarButtonType) {
                return asegurado;
            }
            return null;
        });
    }

    private List<Asegurado> getAllAsegurados() {
        //TODO: leer asegurados de base de datos
        return MainApp.getInstance().getBaseDeDatos().getAll(Asegurado.class);
//        return createAseguradosFalsos();
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void buscarAsegurado(KeyEvent event) {
        //TODO: buscar asegurado con campos del nombre incomplentos
        if (!nombreField.getText().isEmpty() || !parternoField.getText().isEmpty() || !maternoField.getText().isEmpty()) {
            List<Asegurado> asegurados = MainApp.getInstance().getBaseDeDatos().buscarAseguradosPorNombre(nombreField.getText(), parternoField.getText(), maternoField.getText());
            clienteTableView.setItems(FXCollections.observableArrayList(asegurados));
        } else {
            clienteTableView.getItems().clear();
            clienteTableView.setItems(FXCollections.observableArrayList(getAllAsegurados()));
        }
    }

    @FXML
    private void seleccionar(ActionEvent event) {

    }

    public Dialog<Asegurado> getDialog() {
        return dialog;
    }

}
