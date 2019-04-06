/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Cliente;
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
 *
 * @author emilio
 */
public class BuscarClienteController implements Initializable {

    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField parternoField;
    @FXML
    private TextField maternoField;
    @FXML
    private TableView<Cliente> clienteTableView;
    @FXML
    private TableColumn nombreColumn;
    @FXML
    private TableColumn paternoColumn;
    @FXML
    private TableColumn maternoColumn;
    @FXML
    private TextField clienteSelectedField;

    private Cliente cliente;
    private Dialog<Cliente> dialog;

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
        clienteTableView.setItems(FXCollections.observableArrayList(getAllClientes()));

        nombreColumn.setCellValueFactory(new PropertyValueFactory("primerNombre"));
        paternoColumn.setCellValueFactory(new PropertyValueFactory("paterno"));
        maternoColumn.setCellValueFactory(new PropertyValueFactory("materno"));

        clienteTableView.setRowFactory((TableView<Cliente> param) -> {
            final TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked((event) -> {
                if (row.getItem() != null) {
                    cliente = row.getItem();
                    clienteSelectedField.setText(cliente.nombreProperty().get());
                }
            });

            return row; //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    private void crearDialog() {
        dialog = new Dialog<>();
        dialog.setTitle("Buscar Cliente");
        ButtonType seleccionarButtonType = new ButtonType("Seleccionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(seleccionarButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(dialogPane);
        dialog.setResultConverter((dialogButton) -> {
            if (dialogButton == seleccionarButtonType) {
                return cliente;
            }
            return null;
        });
    }

    private List<Cliente> getAllClientes() {
        return MainApp.getInstance().getBaseDeDatos().getAll(Cliente.class);
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void buscarCliente(KeyEvent event) {
        //TODO: buscar asegurado con campos del nombre incomplentos
    }

    @FXML
    private void seleccionar(ActionEvent event) {

    }

    @FXML
    private void buscarAsegurado(KeyEvent event) {
        //TODO: buscar asegurado con campos del nombre incomplentos
    }

    @FXML
    private void quitarFiltros(KeyEvent event) {
        //TODO: buscar asegurado con campos del nombre incomplentos
        clienteTableView.getItems().clear();
        clienteTableView.setItems(FXCollections.observableArrayList(getAllClientes()));

    }

    public Dialog<Cliente> getDialog() {
        return dialog;
    }

}
