/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Auto;
import com.embuckets.controlcartera.entidades.Beneficiario;
import com.embuckets.controlcartera.entidades.Caratula;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.Cobranza;
import com.embuckets.controlcartera.entidades.Dependiente;
import com.embuckets.controlcartera.entidades.DocumentoRecibo;
import com.embuckets.controlcartera.entidades.EstadoPoliza;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.Recibo;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import com.embuckets.controlcartera.entidades.globals.Utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class PolizaHomeController implements Initializable, Controller {

    private static final Logger logger = LogManager.getLogger(PolizaHomeController.class);

    @FXML
    private Label numeroPolizaLabel;
    private String location = "/fxml/PolizaHome.fxml";

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
    private TableColumn docReciboTableColumn;
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
     * @param url
     * @param rb
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

        renovarPolizaButton.disableProperty().bind(Bindings.not(estadoTextField.textProperty().isEqualTo(Globals.POLIZA_ESTADO_VIGENTE)));
        cancelarPolizaButton.disableProperty().bind(Bindings.not(estadoTextField.textProperty().isEqualTo(Globals.POLIZA_ESTADO_VIGENTE)));

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
        secondColumnVBox.getChildren().clear();

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
                agregarAuto(present);
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

    private void agregarAuto(Auto auto) {
        try {
            MainApp.getInstance().getBaseDeDatos().create(auto);
            poliza.getPolizaAuto().getAutoList().add(auto);
            autosTableView.getItems().add(auto);
        } catch (Exception e) {
            Utilities.makeAlert(e, "error al guardar auto").showAndWait();
        }
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
//        grid.setPadding(new Insets(20, 150, 10, 10));

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
        final Button buttonOk = (Button) dialog.getDialogPane().lookupButton(guardar);
        BooleanBinding binding = Bindings.or(marcaField.textProperty().isEmpty(), submarcaField.textProperty().isEmpty()).or(descripcionField.textProperty().isEmpty()).or(modelofField.textProperty().isEmpty());
        buttonOk.disableProperty().bind(binding);
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
                borrarAuto(row.getItem());
            });
            MenuItem editarMenuItem = new MenuItem("Editar");
            editarMenuItem.setOnAction(event -> {
                Optional<Auto> auto = createEditarAutoDialog(row.getItem()).showAndWait();
                auto.ifPresent(present -> {
                    editarAuto(row.getItem(), present);
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

    private void borrarAuto(Auto auto) {
        try {
            MainApp.getInstance().getBaseDeDatos().remove(auto);
            poliza.getPolizaAuto().getAutoList().remove(auto);
            autosTableView.getItems().remove(auto);
        } catch (Exception e) {
            Utilities.makeAlert(e, "error al borrar auto").showAndWait();
        }
    }

    private void editarAuto(Auto actual, Auto edited) {
        Map<String, Pair<Object, Object>> changes = new HashMap<>();
        changes.put("desc", new Pair(actual.getDescripcion(), edited.getDescripcion()));
        changes.put("marca", new Pair(actual.getMarca(), edited.getMarca()));
        changes.put("submarca", new Pair(actual.getSubmarca(), edited.getSubmarca()));
        changes.put("modelo", new Pair(actual.getModelo(), edited.getModelo()));
        if (valuesChanged(changes)) {
            try {
                actual.setDescripcion(edited.getDescripcion());
                actual.setMarca(edited.getMarca());
                actual.setSubmarca(edited.getSubmarca());
                actual.setModelo(Year.of(edited.getModelo().getYear()));
                MainApp.getInstance().getBaseDeDatos().edit(actual);
            } catch (Exception e) {
                Utilities.makeAlert(e, "error al editar auto").showAndWait();
                actual.setDescripcion((String) changes.get("desc").getKey());
                actual.setMarca((String) changes.get("marca").getKey());
                actual.setSubmarca((String) changes.get("submarca").getKey());
                actual.setModelo(Year.of((int) changes.get("modelo").getKey()));
            }
            autosTableView.getItems().clear();
            autosTableView.setItems(FXCollections.observableArrayList(poliza.getPolizaAuto().getAutoList()));

        }
    }

    private boolean valuesChanged(Map<String, Pair<Object, Object>> changes) {
        for (Pair pair : changes.values()) {
            Object notNull = pair.getKey() == null ? "" : pair.getKey();
            if (!notNull.equals(pair.getValue())) {
                return true;
            }
        }
        return false;
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
//        grid.setPadding(new Insets(20, 150, 10, 10));

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
        final Button buttonOk = (Button) dialog.getDialogPane().lookupButton(guardar);
        BooleanBinding binding = Bindings.or(marcaField.textProperty().isEmpty(), submarcaField.textProperty().isEmpty()).or(descripcionField.textProperty().isEmpty()).or(modelofField.textProperty().isEmpty());
        buttonOk.disableProperty().bind(binding);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                //si no estan vacios
                Auto edited = new Auto();
                edited.setDescripcion(descripcionField.getText());
                edited.setMarca(marcaField.getText());
                edited.setSubmarca(submarcaField.getText());
                edited.setModelo(Year.of(Integer.valueOf(modelofField.getText())));
                edited.setIdpoliza(poliza.getPolizaAuto());
                return edited;
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

        secondColumnVBox.getChildren().clear();

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
            showAgregarDependienteDialog();

//            Optional<Cliente> cliente = createAgregarClienteDialog().showAndWait();
//            cliente.ifPresent(present -> {
//                try {
//                    MainApp.getInstance().getBaseDeDatos().create(new Dependiente(present, polizaGmm));
//                    polizaGmm.getClienteList().add(present);
//                    clientesTableView.getItems().add(present);
//                } catch (Exception e) {
//                    Utilities.makeAlert(e, "Error al agregar dependiente").showAndWait();
//                }
//            });
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
                Cliente cliente = row.getItem();
                try {
                    MainApp.getInstance().getBaseDeDatos().remove(new Dependiente(cliente, polizaGmm));
//                    polizaGmm.getClienteList().remove(row.getItem());
                    clientesTableView.getItems().remove(row.getItem());
                } catch (Exception e) {
                    Utilities.makeAlert(e, "Error al borrar dependiente").showAndWait();
                }
            });
            MenuItem editarMenuItem = new MenuItem("Editar");
            editarMenuItem.setOnAction(event -> {
                Optional<Cliente> cliente = createEditarBeneficiarioDialog(row.getItem()).showAndWait();
                cliente.ifPresent(present -> {
                    try {
                        MainApp.getInstance().getBaseDeDatos().edit(new Dependiente(present, polizaGmm));
                    } catch (Exception e) {
                        Utilities.makeAlert(e, "Error al editar dependiente").showAndWait();
                    }
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
        secondColumnVBox.getChildren().clear();

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
        agregarBeneficiarioButton.setOnAction((ActionEvent event) -> {
            showAgregarBeneficiarioDialog();

        });

        GridPane tableBeneficiariosGridPane = new GridPane();
        tableBeneficiariosGridPane.add(new Label("Beneficiarios"), 0, 0);
        tableBeneficiariosGridPane.add(agregarBeneficiarioButton, 1, 0);
        tableBeneficiariosGridPane.getColumnConstraints().add(firstColumnConstraints);
        tableBeneficiariosGridPane.getColumnConstraints().add(secondColumnConstraints);

        configBeneficiariosTable(polizaVida);
        secondColumnVBox.getChildren().addAll(sumaAseguradPane, tableBeneficiariosGridPane, clientesTableView);
    }

    private void showAgregarBeneficiarioDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/AgregarCliente.fxml"), null, new JavaFXBuilderFactory());
            Parent parent = loader.load();
            AgregarClienteController agregarClienteController = loader.getController();
            Optional<Cliente> cliente = agregarClienteController.getDialog().showAndWait();
            cliente.ifPresent((present) -> {
                try {
                    MainApp.getInstance().getBaseDeDatos().create(new Beneficiario(present, poliza.getPolizaVida()));
                    poliza.getPolizaVida().getClienteList().add(present);
                    clientesTableView.getItems().add(present);
                } catch (Exception ex) {
                    logger.error(Logging.Exception_MESSAGE, ex);
                    Utilities.makeAlert(Alert.AlertType.ERROR, "Error al guardar beneficiario", "").showAndWait();
                }
            });
        } catch (IOException e) {
            logger.error(Logging.Exception_MESSAGE, e);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Al abrir ventana", "").showAndWait();
        }
    }

    private void showAgregarDependienteDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/AgregarCliente.fxml"), null, new JavaFXBuilderFactory());
            Parent parent = loader.load();
            AgregarClienteController agregarClienteController = loader.getController();
            Optional<Cliente> cliente = agregarClienteController.getDialog().showAndWait();
            cliente.ifPresent((present) -> {
                try {
                    MainApp.getInstance().getBaseDeDatos().create(new Dependiente(present, poliza.getPolizaGmm()));
                    poliza.getPolizaGmm().getClienteList().add(present);
                    clientesTableView.getItems().add(present);
                } catch (Exception e) {
                    logger.error(Logging.Exception_MESSAGE, e);
                    Utilities.makeAlert(Alert.AlertType.ERROR, "Error al guardar dependiente", "").showAndWait();
                }
            });
        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Error al abrir la ventana", "").showAndWait();
        }
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
//        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombreField = new TextField();
        TextField paternoField = new TextField();
        TextField maternoField = new TextField();
        DatePicker naciminetoDatePicker = new DatePicker();
//        naciminetoDatePicker.setPromptText("");

        grid.add(new Label("Nombre"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Apellido Paterno"), 0, 1);
        grid.add(paternoField, 1, 1);
        grid.add(new Label("Apellido materno"), 0, 2);
        grid.add(maternoField, 1, 2);
        grid.add(new Label("Fecha de nacimiento"), 0, 3);
        grid.add(naciminetoDatePicker, 1, 3);

        dialog.getDialogPane().setContent(grid);
        final Button btnOk = (Button) dialog.getDialogPane().lookupButton(guardar);
        BooleanBinding predicate = nombreField.textProperty().isEmpty().or(paternoField.textProperty().isEmpty());
        btnOk.disableProperty().bind(predicate);

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
                Cliente cliente = row.getItem();
                try {
                    MainApp.getInstance().getBaseDeDatos().remove(new Beneficiario(cliente, polizaVida));
                    clientesTableView.getItems().clear();
                    clientesTableView.setItems(FXCollections.observableArrayList(polizaVida.getClienteList()));
//                    clientesTableView.setItems(FXCollections.observableArrayList(polizaVida.getClienteList()));
                } catch (Exception e) {
                    Utilities.makeAlert(e, "Error al borrar beneficiario").showAndWait();
                }
            });
            MenuItem editarMenuItem = new MenuItem("Editar");
            editarMenuItem.setOnAction(event -> {
                Optional<Cliente> cliente = createEditarBeneficiarioDialog(row.getItem()).showAndWait();
                cliente.ifPresent(present -> {
                    try {
                        MainApp.getInstance().getBaseDeDatos().edit(new Beneficiario(present, polizaVida));
                    } catch (Exception e) {
                        Utilities.makeAlert(e, "Error al editar beneficiario").showAndWait();
                    }
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
//        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombreField = new TextField(cliente.getNombre());
        TextField paternoField = new TextField(cliente.getApellidopaterno());
        TextField maternoField = new TextField(cliente.getApellidomaterno());
        DatePicker naciminetoDatePicker = new DatePicker(cliente.getNacimiento());

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

        BooleanBinding agregarCaratulaBinding = Bindings.isNotEmpty(caratulaTableView.itemsProperty().get());
        agregarCaratulaButton.disableProperty().bind(agregarCaratulaBinding);
//        agregarAutoButton.setDisable(caratulaTableView.getItems().isEmpty());
        archivoTableColumn.setCellValueFactory(new PropertyValueFactory("archivo"));

        caratulaTableView.setRowFactory((TableView<Caratula> table) -> {
            final TableRow<Caratula> row = new TableRow<>();
            final ContextMenu menu = new ContextMenu();
            MenuItem verItem = new MenuItem("Ver");
            verItem.setOnAction((event) -> {
                verCaratula(row.getItem());
            });

            MenuItem editarItem = new MenuItem("Editar");
            editarItem.setOnAction((event) -> {
                Optional<Caratula> documentoEditado = createDialogEditarDocumento(row.getItem()).showAndWait();
                documentoEditado.ifPresent((present) -> {
                    editarCaratula(present);
                });
            });

            MenuItem eliminarItem = new MenuItem("Eliminar");
            eliminarItem.setOnAction((event) -> {
                borrarCaratula(row.getItem());
            });

            menu.getItems().addAll(verItem, editarItem, eliminarItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(menu)
                            .otherwise((ContextMenu) null));
            return row; //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    private void verCaratula(Caratula caratula) {
        try {
            Path temp = Files.createTempFile(caratula.getNombre(), caratula.getExtension());
            Files.write(temp, caratula.getArchivo());
            if (!Desktop.isDesktopSupported()) {
                Utilities.makeAlert(Alert.AlertType.ERROR, "Desktop no soportado", "").showAndWait();
            }
            if (temp.toFile().exists()) {
                try {
                    Desktop.getDesktop().open(temp.toFile());
                } catch (IOException ex) {
                    logger.error(Logging.Exception_MESSAGE, ex);
                    Utilities.makeAlert(Alert.AlertType.ERROR, "Error al abrir el archivo", "").showAndWait();
                }
            }

        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Error al abrir el archivo", "").showAndWait();
        }
    }

    private void borrarCaratula(Caratula caratula) {
        try {
            MainApp.getInstance().getBaseDeDatos().remove(caratula);
            poliza.setCaratula(null);
            caratulaTableView.getItems().clear();
//            agregarCaratulaButton.setDisable(false);
        } catch (Exception e) {
            Utilities.makeAlert(e, "error al borrar caratula").showAndWait();
        }
    }

    private void editarCaratula(Caratula caratula) {
        if (!poliza.getCaratula().archivoProperty().get().equalsIgnoreCase(caratula.archivoProperty().get())) {
            Caratula actual = this.poliza.getCaratula();
            String oldNombre = actual.getNombre();
            String oldExtension = actual.getExtension();
            byte[] oldArchivo = actual.getArchivo();
            try {
                actual.setNombre(caratula.getNombre());
                actual.setExtension(caratula.getExtension());
                actual.setArchivo(caratula.getArchivo());
                MainApp.getInstance().getBaseDeDatos().edit(actual);
            } catch (Exception e) {
                actual.setNombre(oldNombre);
                actual.setExtension(oldExtension);
                actual.setArchivo(oldArchivo);
                Utilities.makeAlert(e, "error al editar caratula").showAndWait();
            }
            caratulaTableView.getItems().clear();
            caratulaTableView.setItems(FXCollections.observableArrayList(poliza.getCaratula()));
        }
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

        TextField archivoField = new TextField(caratula.archivoProperty().get());
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
                if (archivoField.getText().equalsIgnoreCase(caratula.archivoProperty().get())) {
                    return caratula;
                } else {
                    try {
                        Caratula nueva = new Caratula(new File(archivoField.getText()), poliza);
                        nueva.setIdpoliza(poliza.getIdpoliza());
                        nueva.setPoliza(poliza);
                        return nueva;
                    } catch (IOException ex) {
                        logger.error(Logging.Exception_MESSAGE, ex);
                        Utilities.makeAlert(Alert.AlertType.ERROR, "Error al leer el archivo", "").showAndWait();
                    }
                }
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
        docReciboTableColumn.setCellValueFactory(new PropertyValueFactory("documento"));

//        crearContextMenuTablaRecibos();
        recibosTableView.setRowFactory((TableView<Recibo> table) -> {
            final TableRow<Recibo> row = new TableRow<>();
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
                pagarRecibo(recibo);
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
                    guardarDocumentoRecibo(file, recibo);
                }
            });
            menu.getItems().add(agregarArchivoItem);
        } // si existe archivo adjunto
        else {
            MenuItem verArchivoItem = new MenuItem("Ver recibo");
            verArchivoItem.setOnAction(event -> {
                verDocumento(recibo.getDocumentoRecibo());
            });
            menu.getItems().add(verArchivoItem);

            //TODO; menu para cambiar archivo
        }
        return menu;
    }

    private void pagarRecibo(Recibo recibo) {
        try {
            recibo.setCobranza(new Cobranza(Globals.RECIBO_COBRANZA_PAGADO));
            MainApp.getInstance().getBaseDeDatos().edit(recibo);
        } catch (Exception e) {
            recibo.setCobranza(new Cobranza(Globals.RECIBO_COBRANZA_PENDIENTE));
            Utilities.makeAlert(e, "Error al pagar recibo").showAndWait();
        }
        recibosTableView.getItems().clear();
        recibosTableView.setItems(FXCollections.observableArrayList(poliza.getReciboList()));
    }

    private void verDocumento(DocumentoRecibo doc) {
        try {
            Path temp = Files.createTempFile(doc.getNombre(), doc.getExtension());
            Files.write(temp, doc.getArchivo());
            if (!Desktop.isDesktopSupported()) {
                Utilities.makeAlert(Alert.AlertType.ERROR, "Desktop no soportado", "").showAndWait();
            }
            if (temp.toFile().exists()) {
                try {
                    Desktop.getDesktop().open(temp.toFile());
                } catch (IOException ex) {
                    logger.error(Logging.Exception_MESSAGE, ex);
                    Utilities.makeAlert(Alert.AlertType.ERROR, "Error al abrir el archivo", "").showAndWait();
                }
            }

        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Error al abrir el archivo", "").showAndWait();
        }
    }

    private void guardarDocumentoRecibo(File file, Recibo recibo) {
        try {
            DocumentoRecibo documentoRecibo = new DocumentoRecibo(file, recibo);
            MainApp.getInstance().getBaseDeDatos().create(documentoRecibo);
            recibo.setDocumentoRecibo(documentoRecibo);
            recibosTableView.getItems().clear();
            recibosTableView.setItems(FXCollections.observableArrayList(poliza.getReciboList()));
        } catch (IOException e) {
            logger.error(Logging.Exception_MESSAGE, e);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Error al leer el archivo", "").showAndWait();
        } catch (Exception ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Error al guardar el arhivo", "").showAndWait();
        }
    }

    /**
     *
     * @param poliza
     */
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
            createCaratula(file);
        }

    }

    private void createCaratula(File file) {
        try {
            Caratula caratula = new Caratula(file, poliza);
            caratula.setPoliza(poliza);
            caratula.setIdpoliza(poliza.getIdpoliza());
            MainApp.getInstance().getBaseDeDatos().create(caratula);
            poliza.setCaratula(caratula);
            caratulaTableView.getItems().add(caratula);
//            agregarCaratulaButton.setDisable(true);
        } catch (IOException e) {
            logger.error(Logging.Exception_MESSAGE, e);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Error al leer el archivo", "").showAndWait();
        } catch (Exception ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Error al guardar la caratula", "").showAndWait();
        }
    }

    @FXML
    private void renovarPoliza(ActionEvent event) {
        //TODO: hacer ventana de renovar poliza
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Renovar poliza");
        alert.setHeaderText("Seguro que quieres renovar la poliza?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/RenovarPoliza.fxml"), null, new JavaFXBuilderFactory());
                Parent parent = loader.load();
                RenovarPolizaController controller = loader.<RenovarPolizaController>getController();
                controller.setData(this.poliza);
                MainApp.getInstance().changeSceneContent(this, location, parent, loader);
            } catch (IOException ex) {
                logger.error(Logging.Exception_MESSAGE, ex);
                Utilities.makeAlert(Alert.AlertType.ERROR, Logging.CAMBIAR_VENTANA_MESSAGE, "").showAndWait();
            }
        }

    }

    @FXML
    private void cancelarPoliza(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancelar poliza");
        alert.setHeaderText("Seguro que quieres cancelar la poliza?");
        alert.setContentText("Esta acción cambia el estatus de la poliza a cancelada y no se enviaran notificaciones.\n"
                + "Podrás consultarla en cualquier momento.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            EstadoPoliza anterior = poliza.getEstado();
            try {
                poliza.setEstado(new EstadoPoliza(Globals.POLIZA_ESTADO_CANCELADA));
                MainApp.getInstance().getBaseDeDatos().edit(poliza);
            } catch (Exception e) {
                poliza.setEstado(new EstadoPoliza(anterior.getEstado()));
                Alert error = Utilities.makeAlert(e, "Error al cancelar poliza");
                error.showAndWait();
            }
            estadoTextField.setText(poliza.getEstado().getEstado());
            // ... user chose OK
        }
//        else {
//            // ... user chose CANCEL or closed the dialog
//        }
    }

    @FXML
    private void eliminarPoliza(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Borrar poliza");
        alert.setHeaderText("Seguro que quieres borrar la poliza?");
        alert.setContentText("Esta acción eliminara la poliza permanentemente.\nQuiere continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            EstadoPoliza anterior = poliza.getEstado();
            try {
                MainApp.getInstance().getBaseDeDatos().remove(poliza);
                MainApp.getInstance().goBack();
            } catch (Exception e) {
                Alert error = Utilities.makeAlert(e, "Error al borrar poliza");
                error.showAndWait();
            }
            // ... user chose OK
        }
    }

    @FXML
    private void editarNota(ActionEvent event) {
        Optional<String> nuevaNota = createEditNotaDialog(poliza.getNota()).showAndWait();
        nuevaNota.ifPresent((present) -> {
            String old = poliza.getNota();
            try {
                poliza.setNota(present);
                MainApp.getInstance().getBaseDeDatos().edit(poliza);
            } catch (Exception e) {
                poliza.setNota(old);
                Utilities.makeAlert(e, "Error al editar nota").showAndWait();
            }
            notaTextArea.setText(poliza.getNota());
            //TODO: persistir nota
        });
    }

    private Dialog<String> createEditNotaDialog(String nota) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Editar nota");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        VBox grid = new VBox();
        grid.setSpacing(10);

        TextArea notaArea = new TextArea(nota);

        grid.getChildren().addAll(new Label("Nota"), notaArea);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                return notaArea.getText();
            }
            return null;
        });

        return dialog;
    }

    @FXML
    private void editarTitular(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/AgregarCliente.fxml"), null, new JavaFXBuilderFactory());
            Parent parent = loader.load();
            AgregarClienteController agregarClienteController = loader.getController();
            agregarClienteController.setCliente(poliza.getTitular());
            Optional<Cliente> titular = agregarClienteController.getDialog().showAndWait();
            titular.ifPresent((present) -> {
                cambiarTitular(present);
            });
        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Error al abrir la ventana", "").showAndWait();
        }
    }

    private void cambiarTitular(Cliente nuevo) {
        if (!nuevo.equals(poliza.getTitular())) {
            Cliente oldTitular = poliza.getTitular();
            try {
                poliza.setTitular(nuevo);
                MainApp.getInstance().getBaseDeDatos().cambiarTitular(poliza);
            } catch (Exception ex) {
                logger.error(Logging.Exception_MESSAGE, ex);
                Utilities.makeAlert(Alert.AlertType.ERROR, "Error al cambiar al titular", "").showAndWait();
                poliza.setTitular(oldTitular);
            }
            llenarDatosPoliza();
        }
    }

    @FXML
    private void editarDatosPlan(ActionEvent event) {
        //<producto, plan>
        Optional<Pair<String, String>> pair = editPlanDialog(poliza.getProducto(), poliza.getPlan()).showAndWait();
        pair.ifPresent(p -> {
            String oldProducto = poliza.getProducto();
            String oldPlan = poliza.getPlan();
            if (!p.getKey().equals(poliza.getProducto()) || !p.getValue().equals(poliza.getPlan())) {
                try {
                    poliza.setProducto(p.getKey());
                    poliza.setPlan(p.getValue());
                    MainApp.getInstance().getBaseDeDatos().edit(poliza);
                } catch (Exception ex) {
                    logger.error(Logging.Exception_MESSAGE, ex);
                    Utilities.makeAlert(Alert.AlertType.ERROR, "Error editar la poliza", "").showAndWait();
                    poliza.setProducto(oldProducto);
                    poliza.setPlan(oldPlan);
                }
            }
            llenarDatosPoliza();
        });
    }

    private Dialog<Pair<String, String>> editPlanDialog(String producto, String plan) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Editar Datos del plan");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
//        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField productoField = new TextField(producto);
        TextField planField = new TextField(plan);

        grid.add(new Label("Producto"), 0, 0);
        grid.add(productoField, 1, 0);
        grid.add(new Label("Plan"), 0, 1);
        grid.add(planField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                Pair<String, String> pair = new Pair<>(productoField.getText(), planField.getText());
                return new Pair<>(productoField.getText(), planField.getText());
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
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, Logging.CAMBIAR_VENTANA_MESSAGE, "").showAndWait();
        }
    }

    @FXML
    private void goHome(ActionEvent event) {
        try {
            MainApp.getInstance().goHome();
        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, Logging.CAMBIAR_VENTANA_MESSAGE, "").showAndWait();
        }
    }

    /**
     *
     * @param obj
     */
    @Override
    public void setData(Object obj) {
        this.poliza = (Poliza) obj;
    }

    /**
     *
     * @return
     */
    @Override
    public Object getData() {
        return this.poliza;
    }

}
