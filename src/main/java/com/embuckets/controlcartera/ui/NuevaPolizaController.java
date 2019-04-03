/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.Caratula;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.ConductoCobro;
import com.embuckets.controlcartera.entidades.EstadoPoliza;
import com.embuckets.controlcartera.entidades.FormaPago;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.entidades.SumaAseguradaAuto;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class NuevaPolizaController implements Initializable, Controller {

    private String location = "/fxml/NuevaPoliza.fxml";
    @FXML
    private TextField contratanteField;
    @FXML
    private TextField titularField;
    @FXML
    private TextField numeroField;
    @FXML
    private CheckBox esContratanteCheckBox;
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

    @FXML
    private TextField importePrimerReciboField;
    @FXML
    private TextField importeSubsecuentesField;
    @FXML
    private Spinner<Integer> recibosPagadosSpinner;

    //DATA
    private Cliente titular;
    private Asegurado contratante;
    private Caratula caratula;
    private Poliza poliza;

    //CAMPOS EXTRA CONTENEDOR
    @FXML
    private VBox camposExtraVBox;

    //CAMPOS ESPECIALES GMM
    private TextField deducibleGMField;
    private ComboBox<String> deducibleGMMonedaBox;
    private TextField sumaAseguradaGMField; //puede ser texto o numeros
    private ComboBox<String> sumaAseguradaGMMonedaBox; //puede ser ninguna en case de texto
    private ComboBox<String> coaseguroGMBox;

    //CAMPOS ESPECIALES VIDA
    private TextField sumaAseguradaVidaField;
    private ComboBox<String> sumaAseguradaVidaMonedaBox; //puede ser ninguna en case de texto

    //CAMPOS ESPECIALES AUTOS o FLOTILLA
    private ComboBox<String> sumaAseguradaAutosBox; //Comercial o Factura

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.poliza = new Poliza();
        Platform.runLater(() -> {
            llenarContratante();
        });
        initData();
    }

    private void initData() {
        llenarAseguradoraComboBox();
        llenarRamoComboBox();
        llenarConductoCobroComboBox();
        llenarFormaPagoComboBox();
        llenarPrimaMonedaComboBox();
        configTablaCaratula();

        //format prima textField
        configPrimaField();
        configCamposRecibos();
    }

    private void llenarContratante() {
        if (this.contratante != null) {
            contratanteField.setText(contratante.nombreProperty().get());
        }
    }

    private void llenarAseguradoraComboBox() {
        aseguradoraCombo.setItems(FXCollections.observableArrayList(getAseguradoras()));
        aseguradoraCombo.getSelectionModel().selectFirst();
    }

    private void llenarRamoComboBox() {
        ramoCombo.setItems(FXCollections.observableArrayList(getRamos()));
        ramoCombo.getSelectionModel().selectFirst();

        ramoCombo.setOnAction((event) -> {
            if (ramoCombo.getValue().equals(Globals.POLIZA_RAMO_GM)) {
                showCamposGM();
            } else if (ramoCombo.getValue().equals(Globals.POLIZA_RAMO_VIDA)) {
                showCamposVida();
            } else if (ramoCombo.getValue().equals(Globals.POLIZA_RAMO_AUTOS) || ramoCombo.getValue().equals(Globals.POLIZA_RAMO_FLOTILLA)) {
                showCamposAutos();
            } else {
                hideCamposExtras();
            }
        });

        if (ramoCombo.getValue().equals(Globals.POLIZA_RAMO_GM)) {
            showCamposGM();
        } else if (ramoCombo.getValue().equals(Globals.POLIZA_RAMO_VIDA)) {
            showCamposVida();
        } else if (ramoCombo.getValue().equals(Globals.POLIZA_RAMO_AUTOS) || ramoCombo.getValue().equals(Globals.POLIZA_RAMO_FLOTILLA)) {
            showCamposAutos();
        } else {
            hideCamposExtras();
        }
    }

    private void showCamposGM() {
        HBox headerHBox = new HBox();
        headerHBox.getChildren().add(new Label("Poliza de Gastos Médicos"));
        HBox contentBox = new HBox();
        deducibleGMField = new TextField();
        deducibleGMField.setTextFormatter(new TextFormatter<>(textFormatterOnlyNumbers()));
        deducibleGMMonedaBox = new ComboBox<>(FXCollections.observableArrayList(getMoneda()));
        deducibleGMMonedaBox.getSelectionModel().selectFirst();
        sumaAseguradaGMField = new TextField();
        sumaAseguradaGMMonedaBox = new ComboBox<>(FXCollections.observableArrayList(getMoneda()));
        sumaAseguradaGMMonedaBox.getSelectionModel().selectFirst();
        coaseguroGMBox = new ComboBox<>(FXCollections.observableArrayList(getCoaseguroValues()));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0, 1);
        valueFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString() + " %";
            }

            @Override
            public Integer fromString(String string) {
                String valorSinPorcentaje = string.replaceAll(" %", "").trim();
                if (valorSinPorcentaje.isEmpty()) {
                    return 0;
                } else {
                    return Integer.valueOf(string);
                }
            }
        });
//        coaseguroGMBox.setValueFactory(valueFactory);
//        Label sumaLabel = new Label("Suma asegurada");
//        sumaLabel.setStyle(location);
        contentBox.setSpacing(10);
        contentBox.getChildren().addAll(new Label("Deducible"), deducibleGMField, deducibleGMMonedaBox, new Label("Suma asegurada"), sumaAseguradaGMField, sumaAseguradaGMMonedaBox, new Label("Coaseguro"), coaseguroGMBox);

        camposExtraVBox.getChildren().clear();
        camposExtraVBox.getChildren().addAll(headerHBox, contentBox);
    }

    private void showCamposVida() {
        HBox headerHBox = new HBox();
        headerHBox.getChildren().add(new Label("Poliza de Vida"));
        HBox contentBox = new HBox();
        sumaAseguradaVidaField = new TextField();
        sumaAseguradaVidaField.setTextFormatter(new TextFormatter<>(textFormatterOnlyNumbers()));
        sumaAseguradaVidaMonedaBox = new ComboBox<>(FXCollections.observableArrayList(getMoneda()));
        sumaAseguradaVidaMonedaBox.getSelectionModel().selectFirst();
        contentBox.setSpacing(10);
        contentBox.getChildren().addAll(new Label("Suma asegurada"), sumaAseguradaVidaField, sumaAseguradaVidaMonedaBox);

        camposExtraVBox.getChildren().clear();
        camposExtraVBox.getChildren().addAll(headerHBox, contentBox);
    }

    private void showCamposAutos() {
        HBox headerHBox = new HBox();
        headerHBox.getChildren().add(new Label("Poliza de Autos"));
        HBox contentBox = new HBox();
        sumaAseguradaAutosBox = new ComboBox<>(FXCollections.observableArrayList(Globals.getAllPolizaAutoSumas()));
        contentBox.setSpacing(10);
        contentBox.getChildren().addAll(new Label("Suma asegurada"), sumaAseguradaAutosBox);

        camposExtraVBox.getChildren().clear();
        camposExtraVBox.getChildren().addAll(headerHBox, contentBox);
    }

    private void hideCamposExtras() {
        camposExtraVBox.getChildren().clear();
    }

    private ArrayList<String> getCoaseguroValues() {
        ArrayList<String> numbers = new ArrayList<>();
        for (int i = 0; i < 51; i++) {
            numbers.add("" + i);
        }
        return numbers;
    }

    private ArrayList<String> getSumaAseguradaAutosValues() {
        ArrayList<String> values = new ArrayList<>();

        values.add(Globals.POLIZA_AUTO_SUMA_COMERCIAL);
        values.add(Globals.POLIZA_AUTO_SUMA_FACTURA);
        return values;
    }

    private void llenarConductoCobroComboBox() {
        conductoCombo.setItems(FXCollections.observableArrayList(getConductoCobro()));
        conductoCombo.getSelectionModel().selectFirst();

    }

    private void llenarFormaPagoComboBox() {
        formaPagoCombo.setItems(FXCollections.observableArrayList(getFormaPago()));
        formaPagoCombo.getSelectionModel().selectFirst();

        formaPagoCombo.setOnAction((event) -> {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, cuantosRecibos(), 0, 1);
            recibosPagadosSpinner.setValueFactory(valueFactory);
            if (formaPagoCombo.getValue().equalsIgnoreCase(Globals.FORMA_PAGO_ANUAL)) {
                importeSubsecuentesField.setEditable(false);
                importeSubsecuentesField.setDisable(true);
                importeSubsecuentesField.setText("");
                importePrimerReciboField.textProperty().bindBidirectional(primaField.textProperty());
            } else {
                importePrimerReciboField.textProperty().unbindBidirectional(primaField.textProperty());
                importeSubsecuentesField.setEditable(true);
                importeSubsecuentesField.setDisable(false);
            }
        });
    }

    private void llenarPrimaMonedaComboBox() {
        primaMonedaCombo.setItems(FXCollections.observableArrayList(getMoneda()));
        primaMonedaCombo.getSelectionModel().selectFirst();
    }

    private void configTablaCaratula() {
        caratulaTableView.setItems(FXCollections.observableArrayList());
        archivoTableColumn.setCellValueFactory(new PropertyValueFactory("archivo"));

        BooleanBinding agregarCaratulaBinding = Bindings.isNotEmpty(caratulaTableView.itemsProperty().get());
        agregarCaratulaButton.disableProperty().bind(agregarCaratulaBinding);

        caratulaTableView.setRowFactory((TableView<Caratula> table) -> {
            final TableRow<Caratula> row = new TableRow<>();
            final ContextMenu menu = new ContextMenu();
            MenuItem eliminarItem = new MenuItem("Eliminar");
            eliminarItem.setOnAction((event) -> {
                //TODO: eliminar de base de datos
                caratula = null;
                poliza.setCaratula(null);
                caratulaTableView.getItems().clear();
//                agregarCaratulaButton.setDisable(false);
//                caratulaTableView.setItems(FXCollections.observableArrayList(poliza.getCaratula()));// esta por demar
            });
            menu.getItems().addAll(eliminarItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(menu)
                            .otherwise((ContextMenu) null));
            return row; //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    private List<String> getAseguradoras() {
        List<Aseguradora> aseguradoras = MainApp.getInstance().getBaseDeDatos().getAll(Aseguradora.class);
        return aseguradoras.stream().map(a -> a.getAseguradora()).collect(Collectors.toList());
    }

    private String[] getRamos() {
        return Globals.getAllRamos();
    }

    private String[] getConductoCobro() {
        return Globals.getAllConductoCobro();
    }

    private String[] getFormaPago() {
        return Globals.getAllFormaPago();
    }

    private String[] getMoneda() {
        return Globals.getAllMonedas();
    }

    private int cuantosRecibos() {
        if (this.formaPagoCombo.getValue().equalsIgnoreCase(Globals.FORMA_PAGO_ANUAL)) {
            return 1;
        } else if (this.formaPagoCombo.getValue().equalsIgnoreCase(Globals.FORMA_PAGO_SEMESTRAL)) {
            return 2;
        } else if (this.formaPagoCombo.getValue().equalsIgnoreCase(Globals.FORMA_PAGO_TRIMESTRAL)) {
            return 4;
        } else if (this.formaPagoCombo.getValue().equalsIgnoreCase(Globals.FORMA_PAGO_MENSUAL)) {
            return 12;
        } else {
            return 1;
        }
    }

    @FXML
    private void buscarContratante(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/BuscarCliente.fxml"), null, new JavaFXBuilderFactory());
            BuscarAseguradoController buscarController = new BuscarAseguradoController();
            loader.setController(buscarController);
            Parent parent = loader.load();
            Optional<Asegurado> asegurado = buscarController.getDialog().showAndWait();
            asegurado.ifPresent((present) -> {
                this.contratante = present;
                this.contratanteField.setText(present.nombreProperty().get());
            });
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void buscarTitular(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/AgregarCliente.fxml"), null, new JavaFXBuilderFactory());
            Parent parent = loader.load();
            AgregarClienteController agregarClienteController = loader.getController();
            Optional<Cliente> asegurado = agregarClienteController.getDialog().showAndWait();
            asegurado.ifPresent((present) -> {
                this.titular = present;
                this.titularField.setText(present.nombreProperty().get());
            });
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void esContratante(ActionEvent event) {
        if (esContratanteCheckBox.isSelected()) {
            titular = contratante.getCliente();
            titularField.setText(titular.nombreProperty().get());
        } else {
            titular = null;
            titularField.setText("");
        }
    }

    @FXML
    private void agregarCaratula(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Elige un Documento");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(MainApp.getInstance().getStage());
        if (file != null) {
            caratula = new Caratula(file, poliza);
            poliza.setCaratula(caratula);
            caratulaTableView.getItems().add(caratula);
//            agregarCaratulaButton.setDisable(true);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        MainApp.getInstance().goBack();
    }

    @FXML
    private void guardar(ActionEvent event) {
        List<String> errores = new ArrayList<>();
        if (validarDatos(errores)) {
            poliza.setNumero(numeroField.getText());
            poliza.setContratante(contratante);
            poliza.setAseguradora(new Aseguradora(aseguradoraCombo.getValue()));
            poliza.setTitular(titular);
            poliza.setRamo(new Ramo(ramoCombo.getValue()));
            poliza.setProducto(productoField.getText());
            poliza.setPlan(planField.getText());
            poliza.setIniciovigencia(inicioVigenciaPicker.getValue());
            poliza.setFinvigencia(finVigenciaPicker.getValue());
            poliza.setConductocobro(new ConductoCobro(conductoCombo.getValue()));
            poliza.setFormapago(new FormaPago(formaPagoCombo.getValue()));
            poliza.setPrima(new BigDecimal(primaField.getText()));
            poliza.setPrimamoneda(new Moneda(primaMonedaCombo.getValue()));
            poliza.setNota(notaTextArea.getText());
            poliza.setEstado(new EstadoPoliza(Globals.POLIZA_ESTADO_VIGENTE));

            if (caratula != null) {
                poliza.setCaratula(caratula);
                caratula.setPoliza(poliza);
            }

            double importeSubsecuente;
            try {
                importeSubsecuente = Double.valueOf(importeSubsecuentesField.getText());
            } catch (NumberFormatException e) {
                importeSubsecuente = 0;
            }
            poliza.generarRecibos(recibosPagadosSpinner.getValue(), BigDecimal.valueOf(Double.valueOf(importePrimerReciboField.getText())), BigDecimal.valueOf(importeSubsecuente));

            if (poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_GM)) {
                poliza.setPolizaGmm(new PolizaGmm());
                poliza.getPolizaGmm().setDeducible(BigDecimal.valueOf(Double.valueOf(deducibleGMField.getText())));
                poliza.getPolizaGmm().setDeduciblemoneda(new Moneda(deducibleGMMonedaBox.getValue()));
                poliza.getPolizaGmm().setSumaasegurada(sumaAseguradaGMField.getText());
                poliza.getPolizaGmm().setSumaaseguradamondeda(new Moneda(sumaAseguradaGMMonedaBox.getValue()));
                poliza.getPolizaGmm().setCoaseguro(Short.valueOf(coaseguroGMBox.getValue()));
            } else if (poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_VIDA)) {
                poliza.setPolizaVida(new PolizaVida());
                poliza.getPolizaVida().setSumaasegurada(BigDecimal.valueOf(Double.valueOf(sumaAseguradaVidaField.getText())));
                poliza.getPolizaVida().setSumaaseguradamoneda(new Moneda(sumaAseguradaVidaMonedaBox.getValue()));
            } else if (poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_AUTOS) || poliza.getRamo().getRamo().equalsIgnoreCase(Globals.POLIZA_RAMO_FLOTILLA)) {
                poliza.setPolizaAuto(new PolizaAuto());
                poliza.getPolizaAuto().setSumaaseguradaauto(new SumaAseguradaAuto(sumaAseguradaAutosBox.getValue()));
            }

            try {
                //TODO: guardar poliza en base de datos
                MainApp.getInstance().getBaseDeDatos().create(poliza);
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/PolizaHome.fxml"), null, new JavaFXBuilderFactory());
                Parent parent = loader.load();
                PolizaHomeController controller = loader.<PolizaHomeController>getController();
                controller.setPoliza(poliza);
                MainApp.getInstance().changeSceneContent(this, location, parent, loader);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(NuevaPolizaController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al guardar poliza");
                alert.setContentText(ex.getCause().getLocalizedMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al guardar poliza");
            String mensaje = "";
            mensaje = errores.stream().map((error) -> error + "\n").reduce(mensaje, String::concat);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }
    }

    private boolean validarDatos(List<String> errores) {
        if (this.contratante == null) {
            errores.add("Contratante no puede estar vacío");
            return false;
        }
        if (this.titular == null) {
            errores.add("Titular no puede estar vacío");
            return false;
        }
        if (numeroField.getText().isEmpty() && inicioVigenciaPicker.getValue() == null && finVigenciaPicker.getValue() == null && primaField.getText().isEmpty()) {
            errores.add("Numero de poliza, inicio de vigencia y prima no pueden estar vacíos");
            return false;
        }
        if (importePrimerReciboField.getText().isEmpty()) {
            errores.add("El importe del primer recibo no puede estar vacío");
            return false;
        }
        if (importeSubsecuentesField.getText().isEmpty() && !formaPagoCombo.getValue().equalsIgnoreCase(Globals.FORMA_PAGO_ANUAL)) {
            errores.add("El importe de los recbios subsecuentes no puede estar vacío");
            return false;
        }

        //VALIDAR DATOS ESPECIALES
        if (ramoCombo.getValue().equals(Globals.POLIZA_RAMO_GM)) {
            if (deducibleGMField.getText().isEmpty() || sumaAseguradaGMField.getText().isEmpty() || coaseguroGMBox.getValue() == null) {
                errores.add("Datos de poliza de Gastos Médicos incompletos");
                return false;
            }
        } else if (ramoCombo.getValue().equals(Globals.POLIZA_RAMO_AUTOS) || ramoCombo.getValue().equals(Globals.POLIZA_RAMO_FLOTILLA)) {
            if (sumaAseguradaAutosBox.getValue() == null) {
                errores.add("Datos de poliza de Autos incompletos");
                return false;
            }
        } else if (ramoCombo.getValue().equals(Globals.POLIZA_RAMO_VIDA)) {
            if (sumaAseguradaVidaField.getText().isEmpty()) {
                errores.add("Datos de poliza de Vida incompletos");
                return false;
            }
        }
        return true;
    }

    private void configPrimaField() {
        UnaryOperator<TextFormatter.Change> cantidadFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^([0-9]*\\.?[0-9]*|[0-9]*\\.[0-9]+)$")) {
                return change;
            }
            return null;
        };
        primaField.setTextFormatter(new TextFormatter<>(cantidadFilter));
    }

    private UnaryOperator<TextFormatter.Change> textFormatterOnlyNumbers() {
        UnaryOperator<TextFormatter.Change> cantidadFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^([0-9]*\\.?[0-9]*|[0-9]*\\.[0-9]+)$")) {
                return change;
            }
            return null;
        };
        return cantidadFilter;
    }

    private void configCamposRecibos() {
        SpinnerValueFactory<Integer> recibosPagadosFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, cuantosRecibos(), 0, 1);
        recibosPagadosSpinner.setValueFactory(recibosPagadosFactory);

        UnaryOperator<TextFormatter.Change> cantidadFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^([0-9]*\\.?[0-9]*|[0-9]*\\.[0-9]+)$")) {
                return change;
            }
            return null;
        };
        importePrimerReciboField.setTextFormatter(new TextFormatter<>(cantidadFilter));
        importeSubsecuentesField.setTextFormatter(new TextFormatter<>(cantidadFilter));

        if (formaPagoCombo.getValue().equalsIgnoreCase(Globals.FORMA_PAGO_ANUAL)) {
            importeSubsecuentesField.setEditable(false);
            importeSubsecuentesField.setDisable(true);
            importeSubsecuentesField.setText("");
            importePrimerReciboField.textProperty().bindBidirectional(primaField.textProperty());
        } else {
            importePrimerReciboField.textProperty().unbindBidirectional(primaField.textProperty());
            importeSubsecuentesField.setEditable(true);
            importeSubsecuentesField.setDisable(false);
        }
    }

    @FXML
    private void setFinVigenciaOnDatePicker(ActionEvent event) {
        finVigenciaPicker.setValue(inicioVigenciaPicker.getValue().plusMonths(12));
    }

    @Override
    public void setData(Object obj) {
        //NO NOTHING
    }

    @Override
    public Object getData() {
        return null;
    }

    public void setContratante(Asegurado contratante) {
        this.contratante = contratante;
    }

}
