/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Agente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class AgenteController implements Initializable {

    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField paternoField;
    @FXML
    private TextField maternoField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label passwordLabel;

    private Agente agente;
    private Dialog<Agente> dialog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarAgente();
    }

    private void llenarAgente() {
        agente = MainApp.getInstance().getAgente();
        nombreField.setText(agente.getNombre());
        paternoField.setText(agente.getApellidoPaterno());
        maternoField.setText(agente.getApellidoMaterno());
        emailField.setText(agente.getEmail());
        passwordField.setText(agente.getPassword());

        dialog = new Dialog<>();
        dialog.setTitle("Editar agente");
        ButtonType seleccionarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(seleccionarButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(dialogPane);
        dialog.setResultConverter((dialogButton) -> {
            if (dialogButton == seleccionarButtonType) {
                agente.setNombre(nombreField.getText());
                agente.setApellidoPaterno(paternoField.getText());
                agente.setApellidoMaterno(maternoField.getText());
                agente.setEmail(emailField.getText());
                agente.setPassword(passwordField.getText());
                return agente;
            }
            return null;
        });
    }

    public Dialog<Agente> getDialog() {
        return dialog;
    }

    @FXML
    private void show(MouseEvent event) {
        passwordLabel.setText(passwordField.getText());
        passwordLabel.setVisible(true);
    }
    @FXML
    private void hide(MouseEvent event) {
        passwordLabel.setVisible(false);
        passwordLabel.setText("");
        
    }
    

}
