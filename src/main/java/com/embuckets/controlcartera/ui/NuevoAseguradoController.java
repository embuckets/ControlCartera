/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.globals.Utilities;
import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Delegacion;
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TipoEmail;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.TipoTelefono;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import com.embuckets.controlcartera.ui.observable.ObservableArchivo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class NuevoAseguradoController implements Initializable {

    private static final Logger logger = LogManager.getLogger(NuevoAseguradoController.class);

    private String location = "fxml/NuevoAsegurado.fxml";
    //Informacion Personal
    @FXML
    private ToggleGroup tipoPersonaGroup;
    @FXML
    private RadioButton personaFisicaRadioButton;
    @FXML
    private RadioButton personaMoralRadionButton;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField paternoTextField;
    @FXML
    private Label apellidoPaternoLabel;
    @FXML
    private Label apellidoMaternoLabel;
    @FXML
    private TextField maternoTextField;
    @FXML
    private TextField rfcTextField;
    @FXML
    private DatePicker nacimientoDatePicker;

    //Telefono TableView
    @FXML
    private TableView<Telefono> telefonoTableView;
    @FXML
    private TableColumn telefonoTableColumn;
    @FXML
    private TableColumn extensionTableColumn;
    @FXML
    private TableColumn tipoTelefonoTableColumn;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private TextField extensionTextField;
    @FXML
    private ComboBox tipoTelefonoComboBox;
    @FXML
    private Button agregarTelefonoButton;

    //Email TableView
    @FXML
    private TableView<Email> emailTableView;
    @FXML
    private TableColumn emailTableColumn;
    @FXML
    private TableColumn tipoEmailTableColumn;
    @FXML
    private TextField emailTextField;
    @FXML
    private ComboBox tipoEmailComboBox;
    @FXML
    private Button agregarEmailButton;

    //Domicilio
    @FXML
    private TextField calleTextField;
    @FXML
    private TextField exteriorTextField;
    @FXML
    private TextField interiorTextField;
    @FXML
    private TextField codigoPostaTextField;
    @FXML
    private TextField coloniaTextField;
    @FXML
    private ComboBox delegacionComboBox;
    @FXML
    private ComboBox estadoComboBox;

    //Documentos
    @FXML
    private TableView<ObservableArchivo> documentoTableView;
    @FXML
    private TableColumn archivoTableColumn;
    @FXML
    private TableColumn tipoArchivoTableColumn;
    @FXML
    private TextField archivoTextField;
    @FXML
    private Button selectArchivoButton;
    @FXML
    private ComboBox tipoArchivoComboBox;
    @FXML
    private Button agregarArchivoButton;

    //Nota
    @FXML
    private TextArea notaTextArea;

    //Cancelar & guardar
    @FXML
    private Button cancelarButton;
    @FXML
    private Button guardarButton;

    /**
     * Initializes the controller class.o
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        groupTipoPersonaRadioButtons();
        fillTipoTelefonoComboBox();
        fillTipoEmailComboBox();
        fillEstadosComboBox();
        fillDelegacionComboBox();
        fillTipoDocumentoComboBox();
        configTelefonoTable();
        configEmailTable();
        configDocumentoTable();
    }

    private void groupTipoPersonaRadioButtons() {
        tipoPersonaGroup = new ToggleGroup();
        personaFisicaRadioButton.setToggleGroup(tipoPersonaGroup);
        personaFisicaRadioButton.setSelected(true);
        personaMoralRadionButton.setToggleGroup(tipoPersonaGroup);
    }

    private void fillTipoTelefonoComboBox() {
        //pedir los estados a la base de datos
//        String[] tipoTelefono = {"Casa", "Movil", "Trabajo"};
//        ObservableList<String> list = FXCollections.observableArrayList(tipoTelefono);

        tipoTelefonoComboBox.setItems(FXCollections.observableArrayList(Globals.getAllTelefonoTipos()));
//        tipoTelefonoComboBox.getItems().addAll(list);
        tipoTelefonoComboBox.getSelectionModel().select(0);
    }

    private void fillTipoEmailComboBox() {
        //pedir los estados a la base de datos
//        String[] values = {"Personal", "Trabajo"};
//        ObservableList<String> list = FXCollections.observableArrayList(values);
        tipoEmailComboBox.setItems(FXCollections.observableArrayList(Globals.getAllEmailTipos()));
//        tipoEmailComboBox.getItems().addAll(list);
        tipoEmailComboBox.getSelectionModel().select(0);
    }

    private void fillEstadosComboBox() {

        estadoComboBox.setItems(FXCollections.observableArrayList(getAllEstados()));
//        estadoComboBox.getSelectionModel().select(6);
    }

    private List<String> getAllEstados() {
        List<Estado> estados = MainApp.getInstance().getBaseDeDatos().getAll(Estado.class);
        return estados.stream().map(e -> e.getEstado()).collect(Collectors.toList());
    }

    private void fillDelegacionComboBox() {
        delegacionComboBox.setItems(FXCollections.observableArrayList(getAllDelegaciones()));
//        delegacionComboBox.getSelectionModel().select(0);
    }

    private List<String> getAllDelegaciones() {
        List<Delegacion> estados = MainApp.getInstance().getBaseDeDatos().getAll(Delegacion.class);
        return estados.stream().map(e -> e.getDelegacion()).collect(Collectors.toList());
    }

    private void fillTipoDocumentoComboBox() {
//        String[] documentos = {"Identificacion", "Domicilio", "RFC", "Otro"};
//        ObservableList<String> estadosList = FXCollections.observableArrayList(Globals.getAllDocumentoAseguradoTipos());
        tipoArchivoComboBox.setItems(FXCollections.observableArrayList(Globals.getAllDocumentoAseguradoTipos()));
        tipoArchivoComboBox.getSelectionModel().select(0);
    }

    /**
     *
     * @param event
     */
    public void tipoPersonaHandler(ActionEvent event) {
        if (personaFisicaRadioButton.isSelected()) {
            paternoTextField.setText("");
            paternoTextField.setVisible(true);
            maternoTextField.setText("");
            maternoTextField.setVisible(true);
            apellidoPaternoLabel.setVisible(true);
            apellidoMaternoLabel.setVisible(true);
        } else if (personaMoralRadionButton.isSelected()) {
            paternoTextField.setText("");
            paternoTextField.setVisible(false);
            maternoTextField.setText("");
            maternoTextField.setVisible(false);
            apellidoPaternoLabel.setVisible(false);
            apellidoMaternoLabel.setVisible(false);
        }
    }

    private void configTelefonoTable() {
        telefonoTableColumn.setCellValueFactory(new PropertyValueFactory("telefono"));
        extensionTableColumn.setCellValueFactory(new PropertyValueFactory("extension"));
        tipoTelefonoTableColumn.setCellValueFactory(new PropertyValueFactory("tipo"));

        telefonoTableView.setItems(createObservableTelefonoList());
        telefonoTableView.setRowFactory(createContextMenu(telefonoTableView));

        UnaryOperator<Change> phoneFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        telefonoTextField.setTextFormatter(new TextFormatter<>(phoneFilter));

        extensionTextField.setTextFormatter(new TextFormatter<>(phoneFilter));
    }

    private void configEmailTable() {
        emailTableColumn.setCellValueFactory(new PropertyValueFactory("email"));
        tipoEmailTableColumn.setCellValueFactory(new PropertyValueFactory("tipo"));

        emailTableView.setItems(createObservableEmailList());
        emailTableView.setRowFactory(createContextMenu(emailTableView));
    }

    private void configDocumentoTable() {
        archivoTableColumn.setCellValueFactory(new PropertyValueFactory("archivo"));
        tipoArchivoTableColumn.setCellValueFactory(new PropertyValueFactory("tipo"));

        documentoTableView.setItems(createObservableArchivoList());
        documentoTableView.setRowFactory(createContextMenu(documentoTableView));
    }

    private <T> Callback<TableView<T>, TableRow<T>> createContextMenu(TableView<T> table) {
        return new Callback<TableView<T>, TableRow<T>>() {
            @Override
            public TableRow<T> call(TableView<T> tableView) {
                final TableRow<T> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Borrar");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        table.getItems().remove(row.getItem());
                    }
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

    private ObservableList<ObservableArchivo> createObservableArchivoList() {
//        List<ObservableArchivo> list = new ArrayList<>();
        return FXCollections.observableArrayList(new ArrayList<ObservableArchivo>());
    }

    private ObservableList<Telefono> createObservableTelefonoList() {
        List<Telefono> list = new ArrayList<>();
        return FXCollections.observableArrayList(list);
    }

    private ObservableList<Email> createObservableEmailList() {
        List<Email> list = new ArrayList<>();
        return FXCollections.observableArrayList(list);
    }

    /**
     *
     * @param event
     */
    public void agregarTelefono(ActionEvent event) {
        //si los textfield no estan vacios
        if (!telefonoTextField.getText().isEmpty()) {
            Telefono obs = new Telefono(telefonoTextField.getText());
            obs.setExtension(extensionTextField.getText());
            obs.setTipotelefono(new TipoTelefono(tipoTelefonoComboBox.getValue().toString()));
            //agregar a la tabla
            telefonoTableView.getItems().add(obs);
            telefonoTextField.setText("");
            extensionTextField.setText("");
        }
    }

    /**
     *
     * @param event
     */
    public void agregarEmail(ActionEvent event) {
        //si los textfield no estan vacios
        if (!emailTextField.getText().isEmpty() && validarEmail(emailTextField.getText())) {
            Email obs = new Email(emailTextField.getText());
            obs.setTipoemail(new TipoEmail(tipoEmailComboBox.getValue().toString()));
            //agregar a la tabla
            emailTableView.getItems().add(obs);
            emailTextField.setText("");
        }
    }

    private boolean validarEmail(String email) {
        Pattern ptr = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
        return ptr.matcher(email).matches();
    }

    /**
     *
     * @param event
     */
    public void seleccionarArchivo(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Elige un Documento");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(MainApp.getInstance().getStage());
        if (file != null) {
            archivoTextField.setText(file.getPath());
        }
    }

    /**
     *
     * @param event
     */
    public void agregarDocumento(ActionEvent event) {
        if (!archivoTextField.getText().isEmpty()) {
            ObservableArchivo obs = new ObservableArchivo(archivoTextField.getText(), tipoArchivoComboBox.getValue().toString());
            documentoTableView.getItems().add(obs);
            archivoTextField.setText("");
        }
    }

    /**
     *
     * @param event
     */
    public void homePage(ActionEvent event) {
        try {
            MainApp.getInstance().goHome();
        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, Logging.CAMBIAR_VENTANA_MESSAGE, "").showAndWait();
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void guardar(ActionEvent event) throws IOException {
        List<String> errores = new ArrayList<>();
        if (validarForm(errores)) {
            //construir asegurado

            Asegurado asegurado = new Asegurado(nombreTextField.getText(), paternoTextField.getText(), maternoTextField.getText());
            asegurado.setRfc(rfcTextField.getText());
            RadioButton tipoPersona = (RadioButton) tipoPersonaGroup.getSelectedToggle();
            asegurado.setTipopersona(new TipoPersona(tipoPersona.getText()));
            asegurado.getCliente().setNacimiento(nacimientoDatePicker.getValue());
//            ObservableList<ObservableTelefono> telefonoList = telefonoTableView.getItems();
            for (Telefono telefono : telefonoTableView.getItems()) {
                telefono.setAsegurado(asegurado);
                asegurado.agregarTelefono(telefono);
            }

//            ObservableList<ObservableEmail> emailList = emailTableView.getItems();
            for (Email email : emailTableView.getItems()) {
                email.setAsegurado(asegurado);
                asegurado.agregarEmail(email);
            }

            if (!domicilioVacio()) {
                Domicilio domicilio = new Domicilio();
                domicilio.setCalle(calleTextField.getText());
                domicilio.setExterior(exteriorTextField.getText());
                domicilio.setInterior(interiorTextField.getText());
                domicilio.setCodigopostal(codigoPostaTextField.getText());
                domicilio.setColonia(coloniaTextField.getText());
                if (delegacionComboBox.getValue() != null) {
                    domicilio.setDelegacion(new Delegacion(delegacionComboBox.getValue().toString()));
                }
                if (estadoComboBox.getValue() != null) {
                    domicilio.setEstado(new Estado(estadoComboBox.getValue().toString()));
                }
                asegurado.setIddomicilio(domicilio);
            }

            //TODO: crear DocumentosAsegurado
            asegurado.setNota(notaTextArea.getText());

            for (ObservableArchivo observableArchivo : documentoTableView.getItems()) {
                File file = new File(observableArchivo.archivoProperty().get());
                DocumentoAsegurado doc = new DocumentoAsegurado(file, observableArchivo.tipoProperty().get());
                asegurado.agregarDocumento(doc);
                doc.setAsegurado(asegurado);
            }
            List<Asegurado> existentes = MainApp.getInstance().getBaseDeDatos().buscarAseguradosPorNombre(asegurado.getNombre(), asegurado.getApellidoPaterno(), asegurado.getApellidoMaterno());
            if (existentes.isEmpty()) {
                try {
                    MainApp.getInstance().getBaseDeDatos().create(asegurado);
                    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/AseguradoHome.fxml"), null, new JavaFXBuilderFactory());
                    Parent parent = loader.load();
                    AseguradoHomeController controller = loader.<AseguradoHomeController>getController();
                    controller.setAsegurado(asegurado);
                    MainApp.getInstance().changeSceneContent(this, location, parent, loader);
                } catch (Exception ex) {
                    Alert alertDialog = Utilities.makeAlert(ex, "Error al guardar asegurado");
                    alertDialog.showAndWait();
                    return;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No se puede guardar al asegurado");
                alert.setContentText("El asegurado " + asegurado.getNombreCompleto() + " ya existe");
                alert.showAndWait();
                return;
            }//end if asegurado ya existe
        }//end if validarForm
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al guardar asegurado");
            String mensaje = "";
            mensaje = errores.stream().map((error) -> error + "\n").reduce(mensaje, String::concat);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }

//        MainApp.getInstance().changeSceneContent("AseguradoHome.fxml");
    }

    private boolean validarForm(List<String> errores) {
        //validar nombre
        if (personaFisicaRadioButton.isSelected() && ((nombreTextField.getText().isEmpty()) || (apellidoPaternoLabel.getText().isEmpty()))) {
            errores.add("Persona Física requiere nombre y apellido paterno");
            return false;
        } else if (personaMoralRadionButton.isSelected() && nombreTextField.getText().isEmpty()) {
            errores.add("Persona Moral requiere nombre");
            return false;
        }
        if (!domicilioValido(errores)) {
            return false;
        }
        return true;

    }

    private boolean domicilioValido(List<String> errores) {
        if (calleTextField.getText().isEmpty() && !exteriorTextField.getText().isEmpty()) {
            errores.add("Domicilio requiere calle y numero exterior");
            return false;
        }
        if (!calleTextField.getText().isEmpty() && exteriorTextField.getText().isEmpty()) {
            errores.add("Domicilio requiere calle y numero exterior");
            return false;
        }
        return true;
    }

    private boolean domicilioVacio() {
        if (calleTextField.getText().isEmpty() && exteriorTextField.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    private void persistAsegurado(Asegurado asegurado) {
        int id = 1;
        asegurado.setIdcliente(id);
        asegurado.getCliente().setIdcliente(id);
        asegurado.getIddomicilio().setIddomicilio(id);
        asegurado.getEmailList().stream().forEach((email) -> email.getEmailPK().setIdcliente(id));
        asegurado.getTelefonoList().stream().forEach((tel) -> tel.getTelefonoPK().setIdcliente(id));
    }
}
