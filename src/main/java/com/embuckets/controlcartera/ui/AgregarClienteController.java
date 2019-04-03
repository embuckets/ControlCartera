/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Cliente;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author emilio
 */
public class AgregarClienteController implements Initializable {

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
        Platform.runLater(() -> {
            llenarCliente();
        });
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
                cliente = row.getItem();
                clienteSelectedField.setText(cliente.nombreProperty().get());
            });
            return row;
        });
    }

    private void llenarCliente() {
        if (cliente != null) {
            clienteSelectedField.setText(cliente.nombreProperty().get());
        }
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

    @FXML
    private void crearCliente(ActionEvent event) {
        Optional<Cliente> optional = createAgregarClienteDialog().showAndWait();
        optional.ifPresent(present -> {
            this.cliente = present;
            clienteSelectedField.setText(cliente.nombreProperty().get());
        });
    }

    private Dialog<Cliente> createAgregarClienteDialog() {
        Dialog<Cliente> dialogLocal = new Dialog<>();
        dialogLocal.setTitle("Agregar beneficiario");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialogLocal.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
//        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombreFieldLocal = new TextField();
        TextField paternoFieldLocal = new TextField();
        TextField maternoFieldLocal = new TextField();
        DatePicker naciminetoDatePicker = new DatePicker();
//        naciminetoDatePicker.setPromptText("");

        grid.add(new Label("Nombre"), 0, 0);
        grid.add(nombreFieldLocal, 1, 0);
        grid.add(new Label("Apellido Paterno"), 0, 1);
        grid.add(paternoFieldLocal, 1, 1);
        grid.add(new Label("Apellido materno"), 0, 2);
        grid.add(maternoFieldLocal, 1, 2);
        grid.add(new Label("Fecha de nacimiento"), 0, 3);
        grid.add(naciminetoDatePicker, 1, 3);

        dialogLocal.getDialogPane().setContent(grid);
        final Button btnOk = (Button) dialogLocal.getDialogPane().lookupButton(guardar);
        BooleanBinding predicate = nombreFieldLocal.textProperty().isEmpty().or(paternoFieldLocal.textProperty().isEmpty());
        btnOk.disableProperty().bind(predicate);

        dialogLocal.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                Cliente clienteLocal = new Cliente();
                clienteLocal.setNombre(nombreFieldLocal.getText());
                clienteLocal.setApellidopaterno(paternoFieldLocal.getText());
                clienteLocal.setApellidomaterno(maternoFieldLocal.getText());
                clienteLocal.setNacimiento(naciminetoDatePicker.getValue());
                return clienteLocal;
            }
            return null;
        });

        return dialogLocal;
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
        //TODO: buscar asegurado con campos del nombre incomplentos
        if (!nombreField.getText().isEmpty() || !parternoField.getText().isEmpty() || !maternoField.getText().isEmpty()) {
            List<Cliente> asegurados = MainApp.getInstance().getBaseDeDatos().buscarClientesPor(nombreField.getText(), parternoField.getText(), maternoField.getText());
            clienteTableView.setItems(FXCollections.observableArrayList(asegurados));
        } else {
            clienteTableView.getItems().clear();
            clienteTableView.setItems(FXCollections.observableArrayList(getAllClientes()));
        }
    }

    @FXML
    private void seleccionar(ActionEvent event) {

    }

    @FXML
    private void buscarAsegurado(KeyEvent event) {
        //TODO: buscar asegurado con campos del nombre incomplentos
    }

    public Dialog<Cliente> getDialog() {
        return dialog;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
