/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Auto;
import com.embuckets.controlcartera.entidades.Caratula;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.Recibo;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class PolizaHomeController implements Initializable {

    @FXML
    private Label numeroPolizaLabel;
    private String location = "fxml/PolizaHome.fxml";

    @FXML
    private Button eliminarPolizaButton;
    @FXML
    private Button cancelarPolizaButton;
    @FXML
    private Button renovarPolizaButton;
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
    //Nota
    @FXML
    private TextArea notaTextArea;

    //campos de poliza de auto
    private TextField sumaAseguradaAutoTextField;
    private TableView<Auto> autosTableView;
    private TableColumn descripcionTableColumn;
    private TableColumn marcaTableColumn;
    private TableColumn submarcaTableColumn;
    private TableColumn modeloTableColumn;
    private Button agregarAutoButton;

    //campos de vida
    private TextField sumaAseguradaVidaTextField;
    private TableView<Cliente> clientesTableView;
    private TableColumn nombreTableColumn;
    private TableColumn nacimientoTableColumn;

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
        numeroPolizaLabel.setText(poliza.getNumero());
        polizaTextField.setText(poliza.getNumero());
        contratanteTextField.setText(poliza.getContratante().nombreProperty().get());
        titularTextField.setText(poliza.getTitular().nombreProperty().get());
        estadoTextField.setText(poliza.getEstado().getEstado());

        aseguradoraTextField.setText(poliza.getAseguradora().getAseguradora());
        ramoTextField.setText(poliza.getRamo().getRamo());
        productoTextField.setText(poliza.getProducto());
        planTextField.setText(poliza.getPlan());

        inicioVigenciaTextField.setText(poliza.inicioVigenciaProperty().get());
        finVigenciaTextField.setText(poliza.finVigenciaProperty().get());
        conductoTextField.setText(poliza.getConductocobro().getConductocobro());
        formaPagoTextField.setText(poliza.getFormapago().getFormapago());
        primaTextField.setText(poliza.primaProperty().get());
        notaTextArea.setText(poliza.getNota());

        llenarCamposEspeciales();
    }

    private void llenarCamposEspeciales() {
        if (poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_AUTOS) || poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_FLOTILLA)) {
            llenarCamposAuto(poliza.getPolizaAuto());
        } else if (poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_GM)) {
            llenarCamposGastosMedicos(poliza.getPolizaGmm());
        } else if (poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_VIDA)) {
            llenarCamposVida(poliza.getPolizaVida());
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

//        sumaAseguradaPane.setMinWidth(Control.USE_PREF_SIZE);
//        sumaAseguradaPane.setMinHeight(Control.USE_PREF_SIZE);
//        sumaAseguradaPane.setPrefWidth(Control.USE_COMPUTED_SIZE);
//        sumaAseguradaPane.setPrefHeight(Control.USE_COMPUTED_SIZE);
//        sumaAseguradaPane.setMaxWidth(Control.USE_COMPUTED_SIZE);
//        sumaAseguradaPane.setMaxHeight(Control.USE_COMPUTED_SIZE);
        sumaAseguradaAutoTextField = new TextField(polizaAuto.getSumaaseguradaauto().getSumaaseguradaauto());
        sumaAseguradaAutoTextField.setEditable(false);
        sumaAseguradaPane.add(new Label("Suma Asegurada"), 0, 0);
        sumaAseguradaPane.add(sumaAseguradaAutoTextField, 1, 0);

        agregarAutoButton = new Button("Agregar");
        agregarAutoButton.setOnAction(event -> {
            Optional<Auto> auto = createAgregarAutoDialog(polizaAuto).showAndWait();
            auto.ifPresent(present -> {
                polizaAuto.getAutoList().add(present);
                autosTableView.getItems().clear();
                autosTableView.setItems(FXCollections.observableArrayList(polizaAuto.getAutoList()));
                //TDOD: agregar auto a la base
            });
        });

        GridPane autosPane = new GridPane();
        autosPane.add(new Label("Autos"), 0, 0);
        autosPane.add(agregarAutoButton, 1, 0);

        autosPane.getColumnConstraints().add(firstColumnConstraints);
        autosPane.getColumnConstraints().add(secondColumnConstraints);

        configAutosTable(polizaAuto);
        secondColumnVBox.getChildren().addAll(sumaAseguradaPane, autosPane, autosTableView);

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
        //set menu contex
        autosTableView.setRowFactory((TableView<Auto> param) -> {
            final TableRow<Auto> row = new TableRow<>();
            final ContextMenu menu = new ContextMenu();
            MenuItem borrarMenuItem = new MenuItem("Borrar");
            borrarMenuItem.setOnAction(event -> {
                polizaAuto.getAutoList().remove(row.getItem());
                autosTableView.getItems().remove(row.getItem());
                //TODO quitar auto de la base de datos
            });
            MenuItem editarMenuItem = new MenuItem("Editar");
            editarMenuItem.setOnAction(event -> {
                Optional<Auto> auto = createEditarAutoDialog(row.getItem()).showAndWait();
                auto.ifPresent(present -> {
                    autosTableView.getItems().clear();
                    autosTableView.setItems(FXCollections.observableArrayList(polizaAuto.getAutoList()));
                });
                //TODO quitar auto de la base de datos
            });
            menu.getItems().addAll(borrarMenuItem, editarMenuItem);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(menu)
                            .otherwise((ContextMenu) null));
            return row;
        });
    }

    private void setColumnWidth(TableColumn column, double min, double pref, double max) {
        column.setMinWidth(min);
        column.setPrefWidth(pref);
        column.setMaxWidth(max);
    }

    private Dialog<Auto> createEditarAutoDialog(Auto auto) {
        Dialog<Auto> dialog = new Dialog<>();
        dialog.setTitle("Editar auto");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField descripcionField = new TextField(auto.getDescripcion());
        TextField marcaField = new TextField(auto.getMarca());
        TextField submarcaField = new TextField(auto.getSubmarca());
        TextField modelofField = new TextField(auto.modeloProperty().get());

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
                //si no estan vacios
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

    private <T> Callback<TableView<T>, TableRow<T>> createContextMenu(TableView<T> table) {
        return new Callback<TableView<T>, TableRow<T>>() {
            @Override
            public TableRow<T> call(TableView<T> tableView) {
                final TableRow<T> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Borrar");
                removeItem.setOnAction((ActionEvent event) -> {
                    table.getItems().remove(row.getItem());
                });
                rowMenu.getItems().addAll(removeItem);

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null));
                return row;
            }
        };
    }

    private void llenarCamposGastosMedicos(PolizaGmm polizaGmm) {
        GridPane datosGastosMedicosPane = new GridPane();
        ColumnConstraints firstColumnConstraints = new ColumnConstraints(10, 100, Control.USE_COMPUTED_SIZE, Priority.SOMETIMES, HPos.LEFT, true);
        ColumnConstraints secondColumnConstraints = new ColumnConstraints(10, 100, Control.USE_COMPUTED_SIZE, Priority.SOMETIMES, HPos.RIGHT, true);
        datosGastosMedicosPane.getColumnConstraints().add(firstColumnConstraints);
        datosGastosMedicosPane.getColumnConstraints().add(secondColumnConstraints);

        TextField deducibleField = new TextField(Globals.formatCantidad(polizaGmm.getDeducible()) + " " + polizaGmm.getDeduciblemoneda().getMoneda());
        deducibleField.setEditable(false);
        TextField sumaAseguradaField = new TextField(Globals.formatCantidad(polizaGmm.getSumaasegurada()) + " " + polizaGmm.getSumaaseguradamondeda().getMoneda());
        sumaAseguradaField.setEditable(false);
        TextField coaseguroField = new TextField("" + polizaGmm.getCoaseguro() + "%");
        coaseguroField.setEditable(false);

        datosGastosMedicosPane.add(new Label("Suma asegurada"), 0, 0);
        datosGastosMedicosPane.add(sumaAseguradaField, 1, 0);
        datosGastosMedicosPane.add(new Label("Deducible"), 0, 1);
        datosGastosMedicosPane.add(deducibleField, 1, 1);
        datosGastosMedicosPane.add(new Label("Coaseguro"), 0, 2);
        datosGastosMedicosPane.add(coaseguroField, 1, 2);

        Button agregarDependienteButton = new Button("Agregar");
        agregarDependienteButton.setOnAction(event -> {
            Optional<Cliente> auto = createAgregarClienteDialog().showAndWait();
            auto.ifPresent(present -> {
                polizaGmm.getClienteList().add(present);
                clientesTableView.getItems().clear();
                clientesTableView.setItems(FXCollections.observableArrayList(polizaGmm.getClienteList()));
                //TDOD: agregar beneficiario a la base
            });
        });

        GridPane clientesPane = new GridPane();
        clientesPane.add(new Label("Dependientes"), 0, 0);
        clientesPane.add(agregarDependienteButton, 1, 0);
        clientesPane.getColumnConstraints().add(firstColumnConstraints);
        clientesPane.getColumnConstraints().add(secondColumnConstraints);
        configDependientesTable(polizaGmm);
        secondColumnVBox.getChildren().addAll(datosGastosMedicosPane, clientesPane, clientesTableView);
    }

    private void configDependientesTable(PolizaGmm polizaGmm) {
        clientesTableView = new TableView<>();
        nombreTableColumn = new TableColumn("Nombre");
        nacimientoTableColumn = new TableColumn("Fecha de nacimiento");

        clientesTableView.setItems(FXCollections.observableArrayList(polizaGmm.getClienteList()));

        nombreTableColumn.setCellValueFactory(new PropertyValueFactory("nombre"));
        nacimientoTableColumn.setCellValueFactory(new PropertyValueFactory("nacimiento"));

        clientesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setColumnWidth(nombreTableColumn, 100, Control.USE_COMPUTED_SIZE, 5000);
        setColumnWidth(nacimientoTableColumn, 100, Control.USE_COMPUTED_SIZE, 5000);

        clientesTableView.getColumns().addAll(nombreTableColumn, nacimientoTableColumn);

        clientesTableView.setRowFactory((TableView<Cliente> param) -> {
            final TableRow<Cliente> row = new TableRow<>();
            final ContextMenu menu = new ContextMenu();
            MenuItem borrarMenuItem = new MenuItem("Borrar");
            borrarMenuItem.setOnAction(event -> {
                polizaGmm.getClienteList().remove(row.getItem());
                clientesTableView.getItems().remove(row.getItem());
            });
            MenuItem editarMenuItem = new MenuItem("Editar");
            editarMenuItem.setOnAction(event -> {
                Optional<Cliente> cliente = createEditarBeneficiarioDialog(row.getItem()).showAndWait();
                cliente.ifPresent(present -> {
                    clientesTableView.getItems().clear();
                    clientesTableView.setItems(FXCollections.observableArrayList(polizaGmm.getClienteList()));
                });
            });
            menu.getItems().addAll(borrarMenuItem, editarMenuItem);

            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(menu)
                            .otherwise((ContextMenu) null));
            return row;
        });
    }

    private void llenarCamposVida(PolizaVida polizaVida) {
        GridPane sumaAseguradPane = new GridPane();
        ColumnConstraints firstColumnConstraints = new ColumnConstraints(10, 100, Control.USE_COMPUTED_SIZE, Priority.SOMETIMES, HPos.LEFT, true);
        ColumnConstraints secondColumnConstraints = new ColumnConstraints(10, 100, Control.USE_COMPUTED_SIZE, Priority.SOMETIMES, HPos.RIGHT, true);
        sumaAseguradPane.getColumnConstraints().add(firstColumnConstraints);
        sumaAseguradPane.getColumnConstraints().add(secondColumnConstraints);

        sumaAseguradaVidaTextField = new TextField(Globals.formatCantidad(polizaVida.getSumaasegurada()) + " " + polizaVida.getSumaaseguradamoneda().getMoneda());
        sumaAseguradaVidaTextField.setEditable(false);
        sumaAseguradPane.add(new Label("Suma asegurada"), 0, 0);
        sumaAseguradPane.add(sumaAseguradaVidaTextField, 1, 0);

        Button agregarBeneficiarioButton = new Button("Agregar");
        agregarBeneficiarioButton.setOnAction(event -> {
            Optional<Cliente> auto = createAgregarClienteDialog().showAndWait();
            auto.ifPresent(present -> {
                polizaVida.getClienteList().add(present);
                clientesTableView.getItems().clear();
                clientesTableView.setItems(FXCollections.observableArrayList(polizaVida.getClienteList()));
                //TDOD: agregar beneficiario a la base
            });
        });

        GridPane tableBeneficiariosGridPane = new GridPane();
        tableBeneficiariosGridPane.add(new Label("Beneficiarios"), 0, 0);
        tableBeneficiariosGridPane.add(agregarBeneficiarioButton, 1, 0);
        tableBeneficiariosGridPane.getColumnConstraints().add(firstColumnConstraints);
        tableBeneficiariosGridPane.getColumnConstraints().add(secondColumnConstraints);

        configBeneficiariosTable(polizaVida);
        secondColumnVBox.getChildren().addAll(sumaAseguradPane, tableBeneficiariosGridPane, clientesTableView);
    }

    private Dialog<Cliente> createAgregarClienteDialog() {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Agregar beneficiario");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombreField = new TextField();
        TextField paternoField = new TextField();
        TextField maternoField = new TextField();
        DatePicker naciminetoDatePicker = new DatePicker();
        naciminetoDatePicker.setPromptText("dd/mm/aaaa");

        grid.add(new Label("Nombre"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Apellido Paterno"), 0, 1);
        grid.add(paternoField, 1, 1);
        grid.add(new Label("Apellido materno"), 0, 2);
        grid.add(maternoField, 1, 2);
        grid.add(new Label("Fecha de nacimiento"), 0, 3);
        grid.add(naciminetoDatePicker, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                Cliente cliente = new Cliente();
                cliente.setNombre(nombreField.getText());
                cliente.setApellidopaterno(paternoField.getText());
                cliente.setApellidomaterno(maternoField.getText());
                cliente.setNacimiento(naciminetoDatePicker.getValue());
                return cliente;
            }
            return null;
        });

        return dialog;
    }

    private void configBeneficiariosTable(PolizaVida polizaVida) {
        clientesTableView = new TableView<>();
        nombreTableColumn = new TableColumn("Nombre");
        nacimientoTableColumn = new TableColumn("Fecha de nacimiento");

        clientesTableView.setItems(FXCollections.observableArrayList(polizaVida.getClienteList()));

        nombreTableColumn.setCellValueFactory(new PropertyValueFactory("nombre"));
        nacimientoTableColumn.setCellValueFactory(new PropertyValueFactory("nacimiento"));

        clientesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setColumnWidth(nombreTableColumn, 100, Control.USE_COMPUTED_SIZE, 5000);
        setColumnWidth(nacimientoTableColumn, 100, Control.USE_COMPUTED_SIZE, 5000);

        clientesTableView.getColumns().addAll(nombreTableColumn, nacimientoTableColumn);

        clientesTableView.setRowFactory((TableView<Cliente> param) -> {
            final TableRow<Cliente> row = new TableRow<>();
            final ContextMenu menu = new ContextMenu();
            MenuItem borrarMenuItem = new MenuItem("Borrar");
            borrarMenuItem.setOnAction(event -> {
                polizaVida.getClienteList().remove(row.getItem());
                clientesTableView.getItems().remove(row.getItem());
            });
            MenuItem editarMenuItem = new MenuItem("Editar");
            editarMenuItem.setOnAction(event -> {
                Optional<Cliente> cliente = createEditarBeneficiarioDialog(row.getItem()).showAndWait();
                cliente.ifPresent(present -> {
                    clientesTableView.getItems().clear();
                    clientesTableView.setItems(FXCollections.observableArrayList(polizaVida.getClienteList()));
                });
            });
            menu.getItems().addAll(borrarMenuItem, editarMenuItem);

            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(menu)
                            .otherwise((ContextMenu) null));
            return row;
        });
    }

    private Dialog<Cliente> createEditarBeneficiarioDialog(Cliente cliente) {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Editar beneficiario");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombreField = new TextField(cliente.getNombre());
        TextField paternoField = new TextField(cliente.getApellidopaterno());
        TextField maternoField = new TextField(cliente.getApellidomaterno());
        DatePicker naciminetoDatePicker = new DatePicker(cliente.getNacimiento().toLocalDate());

        grid.add(new Label("Nombre"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Apellido Paterno"), 0, 1);
        grid.add(paternoField, 1, 1);
        grid.add(new Label("Apellido materno"), 0, 2);
        grid.add(maternoField, 1, 2);
        grid.add(new Label("Fecha de nacimiento"), 0, 3);
        grid.add(naciminetoDatePicker, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                cliente.setNombre(nombreField.getText());
                cliente.setApellidopaterno(paternoField.getText());
                cliente.setApellidomaterno(maternoField.getText());
                cliente.setNacimiento(naciminetoDatePicker.getValue());
                return cliente;
            }
            return null;
        });

        return dialog;
    }

    private void llenarCaratula() {

        if (poliza.getCaratula() != null) {
            caratulaTableView.setItems(FXCollections.observableArrayList(poliza.getCaratula()));
        }
//        BooleanBinding agregarCaratulaBinding = Bindings.isNotEmpty(caratulaTableView.getItems());
//        agregarCaratulaButton.disableProperty().bind(agregarCaratulaBinding);

        archivoTableColumn.setCellValueFactory(new PropertyValueFactory("archivo"));

        caratulaTableView.setRowFactory((TableView<Caratula> table) -> {
            final TableRow<Caratula> row = new TableRow<>();
            final ContextMenu menu = new ContextMenu();
            MenuItem verItem = new MenuItem("Ver");
            verItem.setOnAction((event) -> {
                Caratula caratula = row.getItem();
                File file = new File(caratula.getNombre());
                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Desktop not supported");
                }
                if (file.exists()) {
                    try {
                        Desktop.getDesktop().open(file);
                    } catch (IOException ex) {
                        Logger.getLogger(AseguradoHomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            MenuItem editarItem = new MenuItem("Editar");
            editarItem.setOnAction((event) -> {
                Optional<Caratula> documentoEditado = createDialogEditarDocumento(row.getItem()).showAndWait();
                documentoEditado.ifPresent((present) -> {
                    //TODO: update documento en base de datos
                    //refresh tabla documentos
                    caratulaTableView.getItems().clear();
                    caratulaTableView.setItems(FXCollections.observableArrayList(poliza.getCaratula()));
                });
            });
            MenuItem eliminarItem = new MenuItem("Eliminar");
            eliminarItem.setOnAction((event) -> {
                //TODO: eliminar de base de datos
                poliza.setCaratula(null);
                caratulaTableView.getItems().clear();
                agregarCaratulaButton.setDisable(false);
//                caratulaTableView.setItems(FXCollections.observableArrayList(poliza.getCaratula()));// esta por demar
            });
            menu.getItems().addAll(verItem, editarItem, eliminarItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(menu)
                            .otherwise((ContextMenu) null));
            return row; //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    private Dialog<Caratula> createDialogEditarDocumento(Caratula caratula) {
        Dialog<Caratula> dialog = new Dialog<>();
        dialog.setTitle("Editar caratula");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField archivoField = new TextField(caratula.getNombre());
        archivoField.setEditable(false);
        archivoField.setPrefColumnCount(50);
        Button selectButton = new Button("Seleccionar archivo");
        selectButton.setOnAction((event) -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Elige un Documento");
            chooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File file = chooser.showOpenDialog(MainApp.getInstance().getStage());
            if (file != null) {
                archivoField.setText(file.getPath());
            }
        });

        grid.add(archivoField, 0, 0);
        grid.add(selectButton, 1, 0);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                caratula.setNombre(archivoField.getText());
                return caratula;
            }
            return null;
        });

        return dialog;
    }

    private void llenarTablaRecibos() {
        //TODO
        recibosTableView.setItems(FXCollections.observableArrayList(poliza.getReciboList()));
        cubreDesdeTableColumn.setCellValueFactory(new PropertyValueFactory("cubreDesde"));
        cubreHastaTableColumn.setCellValueFactory(new PropertyValueFactory("cubreHasta"));
        importeTableColumn.setCellValueFactory(new PropertyValueFactory("importe"));
        cobranzaTableColumn.setCellValueFactory(new PropertyValueFactory("cobranza"));
        notificacionTableColumn.setCellValueFactory(new PropertyValueFactory("enviado"));

//        crearContextMenuTablaRecibos();
        recibosTableView.setRowFactory((TableView<Recibo> table) -> {
            final TableRow<Recibo> row = new TableRow<>();
//            final ContextMenu rowMenu = new ContextMenu();

            row.setOnMouseClicked((event) -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    Recibo selected = recibosTableView.getSelectionModel().getSelectedItem();
                    if (selected != null) {
                        final ContextMenu rowMenu = createContextMenu(selected);
                        row.contextMenuProperty().bind(
                                Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                        .then(rowMenu)
                                        .otherwise((ContextMenu) null));
                    }
                }
            });
            return row; //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    private ContextMenu createContextMenu(Recibo recibo) {
        ContextMenu menu = new ContextMenu();
        if (recibo.getCobranza().getCobranza().equalsIgnoreCase(Globals.RECIBO_COBRANZA_PENDIENTE)) {
            MenuItem pagarItem = new MenuItem("Pagar");
            pagarItem.setOnAction((event) -> {
                recibo.getCobranza().setCobranza(Globals.RECIBO_COBRANZA_PAGADO);
                //TODO: refresh list
                //guardar en base de datos
                //refresh contex menu??
                recibosTableView.getItems().clear();
                recibosTableView.setItems(FXCollections.observableArrayList(poliza.getReciboList()));
            });
            menu.getItems().add(pagarItem);
            //si no tiene archivo de recibo
            //agregar menu seleccion de archivo
        }
        if (recibo.getDocumentoRecibo() == null) {
            MenuItem agregarArchivoItem = new MenuItem("Agregar recibo");
            agregarArchivoItem.setOnAction(event -> {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Elige un documento");
                chooser.setInitialDirectory(new File(System.getProperty("user.home")));
                File file = chooser.showOpenDialog(MainApp.getInstance().getStage());
                if (file != null) {
                    //TODO: crear DocumentoRecibo
                    //recibo.setDocumento(newDocumento)
                    //guardar newDocumento
                    System.out.println(file.getPath());
                }
                // TODO: mostrar dialogo para seleccion de archivo
                //guardar archivo en base de datos
            });
            menu.getItems().add(agregarArchivoItem);

        } // si existe archivo adjunto
        else {
            MenuItem verArchivoItem = new MenuItem("Ver recibo");
            verArchivoItem.setOnAction(event -> {
                System.out.println("abrir programa para ver archivo recibo");
                // TODO: abrir archivo con programa predefinido por sistema (pdf)
            });
            menu.getItems().add(verArchivoItem);
        }
        return menu;
    }

    private void crearContextMenuTablaRecibos() {
//        recibosTableView.setRowFactory((TableView<Recibo> table) -> {
//            final TableRow<Recibo> row = new TableRow<>();
//            final ContextMenu rowMenu = new ContextMenu();
//            //set cobranza pagada
//            if (row.getItem().getCobranza().getCobranza().equals(Globals.RECIBO_COBRANZA_PENDIENTE)) {
//                MenuItem pagadoItem = new MenuItem("Pagado");
//                pagadoItem.setOnAction(event -> {
//                    row.getItem().getCobranza().setCobranza(Globals.RECIBO_COBRANZA_PAGADO);
//                    //refresh lista???
//                    //TODO: update recbio en base de datos
//                });
//            }
//            //si no tiene archivo de recibo
//            //agregar menu seleccion de archivo
//            if (row.getItem().getDocumentoRecibo() == null) {
//                MenuItem agregarArchivoItem = new MenuItem("Agregar recibo");
//                agregarArchivoItem.setOnAction(event -> {
//                    // TODO: mostrar dialogo para seleccion de archivo
//                    //guardar archivo en base de datos
//                });
//            } // si existe archivo adjunto
//            else {
//                MenuItem verArchivoItem = new MenuItem("Ver recibo");
//                verArchivoItem.setOnAction(event -> {
//                    // TODO: abrir archivo con programa predefinido por sistema (pdf)
//                });
//            }
//            //TODO: Opcion de enviar notificacion del recibo seleccionado?
//
//            // only display context menu for non-null items:
//            row.contextMenuProperty().bind(
//                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
//                            .then(rowMenu)
//                            .otherwise((ContextMenu) null));
//
//            return row; //To change body of generated lambdas, choose Tools | Templates.
//        });
    }

    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
    }

    @FXML
    private void agregarCaratula(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Elige un Documento");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(MainApp.getInstance().getStage());
        if (file != null) {
            Caratula caratula = new Caratula(poliza.getId());
            caratula.setNombre(file.getPath());
            caratula.setPoliza(poliza);
//                caratula.setArchivo(file);
            poliza.setCaratula(caratula);
            //TODO: leer archivo, persistir caratula
            caratulaTableView.setItems(FXCollections.observableArrayList(caratula));
            agregarCaratulaButton.setDisable(true);
        }

    }

    @FXML
    private void renovarPoliza(ActionEvent event) {
    }

    @FXML
    private void cancelarPoliza(ActionEvent event) {
    }

    @FXML
    private void eliminarPoliza(ActionEvent event) {
    }

    @FXML
    private void editarNota(ActionEvent event) {
        Optional<String> nuevaNota = createEditNotaDialog(poliza).showAndWait();
        nuevaNota.ifPresent((present) -> {
            notaTextArea.setText(present);
            //TODO: persistir nota
        });
    }

    private Dialog<String> createEditNotaDialog(Poliza poliza) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Editar nota");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        VBox grid = new VBox();
        grid.setSpacing(10);

        TextArea notaArea = new TextArea(poliza.getNota());

        grid.getChildren().addAll(new Label("Nota"), notaArea);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                poliza.setNota(notaArea.getText());
                return poliza.getNota();
            }
            return null;
        });

        return dialog;
    }

    @FXML
    private void regresar(ActionEvent event) {
        try {
            MainApp.getInstance().goBack();
        } catch (IOException ex) {
            Logger.getLogger(PolizaHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goHome(ActionEvent event) {
        try {
            MainApp.getInstance().goHome();
        } catch (IOException ex) {
            Logger.getLogger(PolizaHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
