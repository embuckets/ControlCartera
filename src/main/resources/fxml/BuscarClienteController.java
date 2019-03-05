/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
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
    private TableView<?> clienteTableView;
    @FXML
    private TableColumn<?, ?> nombreColumn;
    @FXML
    private TableColumn<?, ?> paternoColumn;
    @FXML
    private TableColumn<?, ?> maternoColumn;
    @FXML
    private TextField clienteSelectedField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarAsegurado(KeyEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void seleccionar(ActionEvent event) {
    }
    
}
