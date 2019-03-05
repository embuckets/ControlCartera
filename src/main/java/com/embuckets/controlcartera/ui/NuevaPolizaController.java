/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Caratula;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import jdk.nashorn.internal.objects.Global;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class NuevaPolizaController implements Initializable {

    private String location = "fxml/NuevaPoliza.fxml";
    @FXML
    private TextField contratanteField;
    @FXML
    private TextField titularField;
    @FXML
    private TextField numeroField;
    @FXML
    private ComboBox<String> aseguradoraCombo;
    @FXML
    private ComboBox<String> ramoCombo;
    @FXML
    private TextField productoField;
    @FXML
    private TextField planField;
    @FXML
    private ComboBox<String> conductoCombo;
    @FXML
    private TextField primaField;
    @FXML
    private ComboBox<String> primaMonedaCombo;
    @FXML
    private DatePicker inicioVigenciaPicker;
    @FXML
    private DatePicker finVigenciaPicker;
    @FXML
    private ComboBox<String> formaPagoCombo;
    @FXML
    private Button agregarCaratulaButton;
    @FXML
    private TableView<Caratula> caratulaTableView;
    @FXML
    private TableColumn archivoTableColumn;
    @FXML
    private TextArea notaTextArea;

    //DATA
    private Asegurado titular;
    private Cliente contratante;
    private Caratula caratula;
    private Poliza poliza;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarAseguradoraComboBox();
        llenarRamoComboBox();
        llenarConductoCobroComboBox();
        llenarFormaPagoComboBox();
        llenarPrimaMonedaComboBox();
        configTablaCaratula();
    }

    private void llenarAseguradoraComboBox() {
        aseguradoraCombo.setItems(FXCollections.observableArrayList(getAseguradoras()));
        aseguradoraCombo.getSelectionModel().selectFirst();
    }

    private void llenarRamoComboBox() {
        ramoCombo.setItems(FXCollections.observableArrayList(getRamos()));
        ramoCombo.getSelectionModel().selectFirst();

    }

    private void llenarConductoCobroComboBox() {
        conductoCombo.setItems(FXCollections.observableArrayList(getConductoCobro()));
        conductoCombo.getSelectionModel().selectFirst();

    }

    private void llenarFormaPagoComboBox() {
        formaPagoCombo.setItems(FXCollections.observableArrayList(getFormaPago()));
        formaPagoCombo.getSelectionModel().selectFirst();
    }

    private void llenarPrimaMonedaComboBox() {
        primaMonedaCombo.setItems(FXCollections.observableArrayList(getMoneda()));
        primaMonedaCombo.getSelectionModel().selectFirst();
    }

    private void configTablaCaratula() {
        archivoTableColumn.setCellValueFactory(new PropertyValueFactory("archivo"));
        caratulaTableView.setItems(FXCollections.observableArrayList());
    }

    private String[] getAseguradoras() {
        return new String[]{"Inbursa",
            "Quálitas",
            "Afirme",
            "Argos",
            "Atlas",
            "Azteca",
            "Banorte",
            "BBVA Bancomer",
            "Sura",
            "Monterrey New York Life",
            "Tokio Marine",
            "UMBRELLA",
            "Virginia Surety",
            "Zurich"
        };
    }

    private String[] getRamos() {
        return new String[]{
            Globals.POLIZA_RAMO_AUTOS,
            Globals.POLIZA_RAMO_ACC_PER,
            Globals.POLIZA_RAMO_EMPRESARIAL,
            Globals.POLIZA_RAMO_FLOTILLA,
            Globals.POLIZA_RAMO_GM,
            Globals.POLIZA_RAMO_HOGAR,
            Globals.POLIZA_RAMO_INVERSION,
            Globals.POLIZA_RAMO_RC,
            Globals.POLIZA_RAMO_TRANSPORTE,
            Globals.POLIZA_RAMO_VIDA
        };
    }

    private String[] getConductoCobro() {
        return new String[]{
            Globals.CONDUCTO_COBRO_AGENTE,
            Globals.CONDUCTO_COBRO_CAT,
            Globals.CONDUCTO_COBRO_CASH
        };
    }

    private String[] getFormaPago() {
        return new String[]{
            Globals.FORMA_PAGO_ANUAL,
            Globals.FORMA_PAGO_SEMESTRAL,
            Globals.FORMA_PAGO_TRIMESTRAL,
            Globals.FORMA_PAGO_MENSUAL
        };
    }

    private String[] getMoneda() {
        return new String[]{
            Globals.MONEDA_PESOS,
            Globals.MONEDA_DOLARES,
            Globals.MONEDA_UMAM
        };
    }

    @FXML
    private void buscarContratante(ActionEvent event) {
        
    }

    @FXML
    private void buscarTitular(ActionEvent event) {
    }

    @FXML
    private void esContratante(ActionEvent event) {
    }

    @FXML
    private void agregarCaratula(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
    }
    
    private Dialog<Asegurado> buscarContratante() {
//        Dialog<Asegurado> dialog = new Dialog<>();
//        dialog.setTitle("Buscar Contratante");
//        //set the button types
//        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);
//
//        //create labels and fields
//        GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(20, 150, 10, 10));
//
//        TextField nombreField = new TextField();
//        TextField paternoField = new TextField();
//        TextField maternoField = new TextField();
//        DatePicker naciminetoDatePicker = new DatePicker();
//        naciminetoDatePicker.setPromptText("dd/mm/aaaa");
//
//        grid.add(new Label("Nombre"), 0, 0);
//        grid.add(nombreField, 1, 0);
//        grid.add(new Label("Apellido Paterno"), 0, 1);
//        grid.add(paternoField, 1, 1);
//        grid.add(new Label("Apellido materno"), 0, 2);
//        grid.add(maternoField, 1, 2);
//        grid.add(new Label("Fecha de nacimiento"), 0, 3);
//        grid.add(naciminetoDatePicker, 1, 3);
//
//        dialog.getDialogPane().setContent(grid);
//        dialog.setResultConverter(dialogButton -> {
//            if (dialogButton == guardar) {
//                Cliente cliente = new Cliente();
//                cliente.setNombre(nombreField.getText());
//                cliente.setApellidopaterno(paternoField.getText());
//                cliente.setApellidomaterno(maternoField.getText());
//                cliente.setNacimiento(naciminetoDatePicker.getValue());
//                return cliente;
//            }
//            return null;
//        });
//
//        return dialog;
    }
    
//    private HBox 

}
