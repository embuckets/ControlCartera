/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Auto;
import com.embuckets.controlcartera.entidades.Caratula;
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.Recibo;
import java.net.URL;
import java.time.Year;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class PolizaHomeController implements Initializable {

    @FXML
    private TextField polizaTextField;
    @FXML
    private TextField contratanteTextField;
    @FXML
    private TextField titularTextField;
    @FXML
    private TextField estadoTextField;
    @FXML
    private TextField aseguradoraTextField;
    @FXML
    private TextField ramoTextField;
    @FXML
    private TextField productoTextField;
    @FXML
    private TextField planTextField;
    @FXML
    private TextField inicioVigenciaTextField;
    @FXML
    private TextField finVigenciaTextField;
    @FXML
    private TextField conductoTextField;
    @FXML
    private TextField formaPagoTextField;
    @FXML
    private TextField primaTextField;
    @FXML
    private VBox secondColumnVBox;
    @FXML
    private Button agregarCaratulaButton;
    @FXML
    private TableView<Caratula> caratulaTableView;
    @FXML
    private TableColumn archivoTableColumn;
    @FXML
    private Button regresarButton;
    @FXML
    private TableView<Recibo> recibosTableView;
    @FXML
    private TableColumn reciboTableColumn;
    @FXML
    private TableColumn cubreDesdeTableColumn;
    @FXML
    private TableColumn cubreHastaTableColumn;
    @FXML
    private TableColumn importeTableColumn;
    @FXML
    private TableColumn cobranzaTableColumn;
    @FXML
    private TableColumn notificacionTableColumn;
    @FXML
    private Button regresarButton1;

    //campos de poliza de auto
    private TextField sumaAseguradaAutoTextField;
    private TableView<Auto> autosTableView;
    private TableColumn descripcionTableColumn;
    private TableColumn marcaTableColumn;
    private TableColumn submarcaTableColumn;
    private TableColumn modeloTableColumn;
    private Button agregarAutoButton;

    //campos de vida
    private Poliza poliza;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            //do stuff
            initData();
        });
    }

    private void initData() {
        llenarDatosPoliza();
        llenarCaratula();
        llenarTablaRecibos();

    }

    private void llenarDatosPoliza() {
        polizaTextField.setText(poliza.getNumero());
        contratanteTextField.setText(poliza.getContratante().nombreProperty().get());
        titularTextField.setText(poliza.getTitular().nomberProperty().get());
        estadoTextField.setText(poliza.getEstado().getEstado());

        aseguradoraTextField.setText(poliza.getAseguradora().getAseguradora());
        ramoTextField.setText(poliza.getRamo().getRamo());
        productoTextField.setText(poliza.getProducto());
        planTextField.setText(poliza.getPlan());

        inicioVigenciaTextField.setText(poliza.inicioVigenciaProperty().get());
        finVigenciaTextField.setText(poliza.finVigenciaProperty().get());
        conductoTextField.setText(poliza.getConductocobro().getConductocobro());
        formaPagoTextField.setText(poliza.getFormapago().getFormapago());
        primaTextField.setText(poliza.primaProperty().get() + " " + poliza.getPrimamoneda().getMoneda());

        llenarCamposEspeciales();
    }

    private void llenarCamposEspeciales() {
        if (poliza.getRamo().getRamo().equalsIgnoreCase("autos") || poliza.getRamo().getRamo().equalsIgnoreCase("flotilla")) {
            llenarCamposAuto(poliza.getPolizaAuto());
        }
        if (poliza.getRamo().getRamo().equalsIgnoreCase("gastos medicos")) {
            llenarCamposGastosMedicos();
        }
        if (poliza.getRamo().getRamo().equalsIgnoreCase("vida")) {
            llenarCamposVida();
        }
    }

    private void llenarCamposAuto(PolizaAuto polizaAuto) {
        GridPane sumaAseguradaPane = new GridPane();

        ColumnConstraints firstColumnConstraints = new ColumnConstraints(10, 100, Control.USE_COMPUTED_SIZE, Priority.SOMETIMES, HPos.LEFT, true);
//        firstColumnConstraints.setPercentWidth(-1);
//        firstColumnConstraints.setFillWidth(true);
        ColumnConstraints secondColumnConstraints = new ColumnConstraints(10, 100, Control.USE_COMPUTED_SIZE, Priority.SOMETIMES, HPos.RIGHT, true);
//        secondColumnConstraints.setPercentWidth(-1);
//        secondColumnConstraints.setFillWidth(true);

        sumaAseguradaPane.getColumnConstraints().add(firstColumnConstraints);
        sumaAseguradaPane.getColumnConstraints().add(secondColumnConstraints);

        sumaAseguradaPane.setMinWidth(Control.USE_PREF_SIZE);
        sumaAseguradaPane.setMinHeight(Control.USE_PREF_SIZE);
        sumaAseguradaPane.setPrefWidth(Control.USE_COMPUTED_SIZE);
        sumaAseguradaPane.setPrefHeight(Control.USE_COMPUTED_SIZE);
        sumaAseguradaPane.setMaxWidth(Control.USE_COMPUTED_SIZE);
        sumaAseguradaPane.setMaxHeight(Control.USE_COMPUTED_SIZE);

        sumaAseguradaAutoTextField = new TextField(polizaAuto.getSumaaseguradaauto().getSumaaseguradaauto());
        sumaAseguradaAutoTextField.setEditable(false);
        sumaAseguradaPane.add(new Label("Suma Asegurada"), 0, 0);
        sumaAseguradaPane.add(sumaAseguradaAutoTextField, 1, 0);

        agregarAutoButton = new Button("Agregar");
        agregarAutoButton.setOnAction(event -> {
            //TODO: crearDialogAuto

        });

        GridPane autosPane = new GridPane();
        autosPane.add(new Label("Autos"), 0, 0);
        autosPane.add(agregarAutoButton, 1, 0);

        autosPane.getColumnConstraints().add(firstColumnConstraints);
        autosPane.getColumnConstraints().add(secondColumnConstraints);

        configAutosTable(polizaAuto);
        secondColumnVBox.getChildren().addAll(sumaAseguradaPane, autosPane, autosTableView);

        agregarAutoButton.setOnAction(event -> {
            Optional<Auto> auto = createAgregarAutoDialog(polizaAuto).showAndWait();
            auto.ifPresent(present -> {
                polizaAuto.getAutoList().add(present);
                autosTableView.getItems().clear();
                autosTableView.setItems(FXCollections.observableArrayList(polizaAuto.getAutoList()));
                //TDOD: agregar auto a la base
            });
        });
    }

    private Dialog<Auto> createAgregarAutoDialog(PolizaAuto polizaAuto) {
        Dialog<Auto> dialog = new Dialog<>();
        dialog.setTitle("Agregar auto");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField descripcionField = new TextField();
        TextField marcaField = new TextField();
        TextField submarcaField = new TextField();
        TextField modelofField = new TextField();

        UnaryOperator<TextFormatter.Change> yearFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]{0,4}")) {
                return change;
            }
            return null;
        };
        modelofField.setTextFormatter(new TextFormatter<>(yearFilter));

        grid.add(new Label("Descripcion"), 0, 0);
        grid.add(descripcionField, 1, 0);
        grid.add(new Label("Marca"), 0, 1);
        grid.add(marcaField, 1, 1);
        grid.add(new Label("Submarca"), 0, 2);
        grid.add(submarcaField, 1, 2);
        grid.add(new Label("Modelo"), 0, 3);
        grid.add(modelofField, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                Auto auto = new Auto();
                auto.setIdpoliza(polizaAuto);
                auto.setDescripcion(descripcionField.getText());
                auto.setMarca(marcaField.getText());
                auto.setSubmarca(submarcaField.getText());
                auto.setModelo(Year.of(Integer.valueOf(modelofField.getText())));
                return auto;
            }
            return null;
        });

        return dialog;
    }

    private void configAutosTable(PolizaAuto polizaAuto) {
        autosTableView = new TableView<>();
        descripcionTableColumn = new TableColumn("Descripción");
        marcaTableColumn = new TableColumn("Marca");
        submarcaTableColumn = new TableColumn("Submarca");
        modeloTableColumn = new TableColumn("Modelo");
        //insert autos list
        autosTableView.setItems(FXCollections.observableArrayList(polizaAuto.getAutoList()));
        //property value
        descripcionTableColumn.setCellValueFactory(new PropertyValueFactory("descripcion"));
        marcaTableColumn.setCellValueFactory(new PropertyValueFactory("marca"));
        submarcaTableColumn.setCellValueFactory(new PropertyValueFactory("submarca"));
        modeloTableColumn.setCellValueFactory(new PropertyValueFactory("modelo"));

        //make it pretty
        autosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setColumnWidth(descripcionTableColumn, 100, Control.USE_COMPUTED_SIZE, 5000);
        setColumnWidth(marcaTableColumn, 100, Control.USE_COMPUTED_SIZE, 5000);
        setColumnWidth(submarcaTableColumn, 100, Control.USE_COMPUTED_SIZE, 5000);
        setColumnWidth(modeloTableColumn, 100, Control.USE_COMPUTED_SIZE, 5000);
        //put it together
        autosTableView.getColumns().addAll(descripcionTableColumn, marcaTableColumn, submarcaTableColumn, modeloTableColumn);
    }

    private void setColumnWidth(TableColumn column, double min, double pref, double max) {
        column.setMinWidth(min);
        column.setPrefWidth(pref);
        column.setMaxWidth(max);
    }

    private void llenarCamposGastosMedicos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void llenarCamposVida() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void llenarCaratula() {
        //TODO
    }

    private void llenarTablaRecibos() {
        //TODO
    }

    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
    }

    @FXML
    private void agregarCaratula(ActionEvent event) {
    }

    @FXML
    private void regresar(ActionEvent event) {
    }

}
