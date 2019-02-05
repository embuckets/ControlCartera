/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class NuevoAseguradoController implements Initializable {

    @FXML
    private RadioButton radioPersonaFisica;
    @FXML
    private RadioButton radioPersonaMoral;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textPaterno;
    @FXML
    private Label labelApellidoPaterno;
    @FXML
    private Label labelApellidoMaterno;
    @FXML
    private TextField textMaterno;
    @FXML
    private TextField textTelefono;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textRfc;
    @FXML
    private TextField textCalle;
    @FXML
    private TextField textNoExterior;
    @FXML
    private TextField textNoInterior;
    @FXML
    private TextField textCodigoPostal;
    @FXML
    private TextField textColonia;
    @FXML
    private TextField textDelegacion;
    @FXML
    private ComboBox<String> comboBoxEstado;
    @FXML
    private ComboBox<String> comboBoxDocumento;
    @FXML
    private TextArea textNota;

    /**
     * Initializes the controller class.o
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configEstados();
        configDocumentos();
        configTipoPersona();
    }

    private void configEstados() {
        String[] estados = {"Aguascalientes", " Baja California", " Baja California Sur",
            " Campeche", " Chiapas", " Chihuahua", " Ciudad de México",
            " Coahuila", " Colima", " Durango", " Estado de México", " Guanajuato",
            " Guerrero", " Hidalgo", " Jalisco", " Michoacán", " Morelos",
            " Nayarit", " Nuevo León", " Oaxaca", " Puebla", " Querétaro",
            " Quintana Roo", " San Luis Potosí", " Sinaloa", " Sonora",
            " Tabasco", " Tamaulipas", " Tlaxcala", " Veracruz", " Yucatán", " Zacatecas"};
        ObservableList<String> estadosList = FXCollections.observableArrayList(estados);
        comboBoxEstado.getItems().addAll(estadosList);
        comboBoxEstado.getSelectionModel().select(6);
    }

    private void configDocumentos() {
        String[] documentos = {"Identificacion", "Comprobante de domicilio"};
        ObservableList<String> estadosList = FXCollections.observableArrayList(documentos);
        comboBoxDocumento.getItems().addAll(estadosList);
    }

    private void configTipoPersona() {
        ToggleGroup group = new ToggleGroup();
        radioPersonaFisica.setToggleGroup(group);
        radioPersonaFisica.setSelected(true);
        radioPersonaMoral.setToggleGroup(group);
    }

    public void tipoPersonaHandler(ActionEvent event) {
        if (radioPersonaFisica.isSelected()) {
            textPaterno.setText("");
            textPaterno.setVisible(true);
            textMaterno.setText("");
            textMaterno.setVisible(true);
            labelApellidoMaterno.setVisible(true);
            labelApellidoPaterno.setVisible(true);
        } else if (radioPersonaMoral.isSelected()) {
            textPaterno.setText("");
            textPaterno.setVisible(false);
            textMaterno.setText("");
            textMaterno.setVisible(false);
            labelApellidoMaterno.setVisible(false);
            labelApellidoPaterno.setVisible(false);
        }
    }

    public void homePage(ActionEvent event) throws IOException {
        MainApp.getInstance().changeSceneContent("Home.fxml");
//        
//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("Home.fxml"));
//            Scene newScene = new Scene(parent);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(newScene);
//
//        } catch (IOException ex) {
//            Logger.getLogger(NuevoAseguradoController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void guardar(ActionEvent event) throws IOException {
        String nombre = textNombre.getText();
        String paterno = textPaterno.getText();
        String materno = textMaterno.getText();
        //TODO: Validar los valores

        Asegurado asegurado = new Asegurado(nombre, paterno, materno);

//        MainApp.getInstance().changeSceneContent("AseguradoHome.fxml");
    }

}
