/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Delegacion;
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TipoEmail;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.TipoTelefono;
import com.embuckets.controlcartera.ui.observable.ObservableArchivo;
import com.embuckets.controlcartera.ui.observable.ObservableEmail;
import com.embuckets.controlcartera.ui.observable.ObservableTelefono;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
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

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class NuevoAseguradoController implements Initializable {

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
    private TableView telefonoTableView;
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
    private TableView emailTableView;
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
    private TableView documentoTableView;
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
        String[] tipoTelefono = {"Casa", "Movil", "Trabajo"};
        ObservableList<String> list = FXCollections.observableArrayList(tipoTelefono);
        tipoTelefonoComboBox.getItems().addAll(list);
        tipoTelefonoComboBox.getSelectionModel().select(0);
    }

    private void fillTipoEmailComboBox() {
        //pedir los estados a la base de datos
        String[] values = {"Personal", "Trabajo"};
        ObservableList<String> list = FXCollections.observableArrayList(values);
        tipoEmailComboBox.getItems().addAll(list);
        tipoEmailComboBox.getSelectionModel().select(0);
    }

    private void fillEstadosComboBox() {

        //pedir los estados a la base de datos
        String[] estados = {"Aguascalientes", " Baja California", " Baja California Sur",
            " Campeche", " Chiapas", " Chihuahua", " Ciudad de México",
            " Coahuila", " Colima", " Durango", " Estado de México", " Guanajuato",
            " Guerrero", " Hidalgo", " Jalisco", " Michoacán", " Morelos",
            " Nayarit", " Nuevo León", " Oaxaca", " Puebla", " Querétaro",
            " Quintana Roo", " San Luis Potosí", " Sinaloa", " Sonora",
            " Tabasco", " Tamaulipas", " Tlaxcala", " Veracruz", " Yucatán", " Zacatecas"};
        ObservableList<String> estadosList = FXCollections.observableArrayList(estados);
        estadoComboBox.getItems().addAll(estadosList);
        estadoComboBox.getSelectionModel().select(6);
    }

    private void fillDelegacionComboBox() {
        //pedir los estados a la base de datos
        String[] estados = {"Álvaro Obregón", " Azcapotzalco", " Benito Juárez",
            " Coyoacán", " Cuajimalpa de Morelos", " Cuauhtémoc", "Gustavo A. Madero",
            " Iztacalco", " Iztapalapa", " La Magdalena Contreras", "Miguel Hidalgo", " Milpa Alta",
            " Tláhuac", " Tlalpan", " Venustiano Carranza", " Xochimilco"};
        ObservableList<String> estadosList = FXCollections.observableArrayList(estados);
        delegacionComboBox.getItems().addAll(estadosList);
        delegacionComboBox.getSelectionModel().select(0);
    }

    private void fillTipoDocumentoComboBox() {
        String[] documentos = {"Identificacion", "Domicilio", "RFC", "Otro"};
        ObservableList<String> estadosList = FXCollections.observableArrayList(documentos);
        tipoArchivoComboBox.getItems().addAll(estadosList);
        tipoArchivoComboBox.getSelectionModel().select(0);
    }

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
        List<ObservableArchivo> list = new ArrayList<>();
        return FXCollections.observableArrayList(list);
    }

    private ObservableList<ObservableTelefono> createObservableTelefonoList() {
        List<ObservableTelefono> list = new ArrayList<>();
        return FXCollections.observableArrayList(list);
    }

    private ObservableList<ObservableEmail> createObservableEmailList() {
        List<ObservableEmail> list = new ArrayList<>();
        return FXCollections.observableArrayList(list);
    }

    public void agregarTelefono(ActionEvent event) {
        //si los textfield no estan vacios
        if (!telefonoTextField.getText().isEmpty()) {
            ObservableTelefono obs = new ObservableTelefono(telefonoTextField.getText(), extensionTextField.getText(), tipoTelefonoComboBox.getValue().toString());
            //agregar a la tabla
            telefonoTableView.getItems().add(obs);
            telefonoTextField.setText("");
            extensionTextField.setText("");
        }
    }

    public void agregarEmail(ActionEvent event) {
        //si los textfield no estan vacios
        if (!emailTextField.getText().isEmpty() && validarEmail(emailTextField.getText())) {
            ObservableEmail obs = new ObservableEmail(emailTextField.getText(), tipoEmailComboBox.getValue().toString());
            //agregar a la tabla
            emailTableView.getItems().add(obs);
            emailTextField.setText("");
        }
    }

    private boolean validarEmail(String email) {
        Pattern ptr = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
        return ptr.matcher(email).matches();
    }

    public void seleccionarArchivo(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Elige un Documento");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(MainApp.getInstance().getStage());
        if (file != null) {
            archivoTextField.setText(file.getPath());
        }
    }

    public void agregarDocumento(ActionEvent event) {
        ObservableArchivo obs = new ObservableArchivo(archivoTextField.getText(), tipoArchivoComboBox.getValue().toString());
        documentoTableView.getItems().add(obs);
        archivoTextField.setText("");
    }

    public void homePage(ActionEvent event) throws IOException {
        MainApp.getInstance().changeSceneContent("/fxml/Home.fxml");
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
        if (validarForm()) {
            //construir asegurado

            Asegurado asegurado = new Asegurado(nombreTextField.getText(), paternoTextField.getText(), maternoTextField.getText());
            asegurado.setRfc(rfcTextField.getText());
            RadioButton tipoPersona = (RadioButton) tipoPersonaGroup.getSelectedToggle();
            asegurado.setTipopersona(new TipoPersona(tipoPersona.getText()));
            ObservableList<ObservableTelefono> telefonoList = telefonoTableView.getItems();
            for (ObservableTelefono obsTel : telefonoList) {
                Telefono telefono = new Telefono(obsTel.telefonoProperty().get());
                telefono.setExtension(obsTel.extensionProperty().get());
                telefono.setTipotelefono(new TipoTelefono(obsTel.tipoProperty().get()));
                asegurado.agregarTelefono(telefono);
            }

            ObservableList<ObservableEmail> emailList = emailTableView.getItems();
            for (ObservableEmail observableEmail : emailList) {
                Email email = new Email(observableEmail.emailProperty().get());
                email.setTipoemail(new TipoEmail(observableEmail.tipoProperty().get()));
                asegurado.agregarEmail(email);
            }

            Domicilio domicilio = new Domicilio();
            domicilio.setCalle(calleTextField.getText());
            domicilio.setExterior(exteriorTextField.getText());
            domicilio.setInterior(interiorTextField.getText());
            domicilio.setCodigopostal(codigoPostaTextField.getText());
            domicilio.setColonia(coloniaTextField.getText());
            domicilio.setDelegacion(new Delegacion(delegacionComboBox.getValue().toString()));
            domicilio.setEstado(new Estado(estadoComboBox.getValue().toString()));
            asegurado.setIddomicilio(domicilio);

            asegurado.setNota(notaTextArea.getText());
            //guardar asegurado
            persistAsegurado(asegurado);
            //ir a Asegurado Home
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/AseguradoHome.fxml"), null, new JavaFXBuilderFactory());
            Parent parent = loader.load();
            AseguradoHomeController controller = loader.<AseguradoHomeController>getController();
            controller.setAsegurado(asegurado);
//            controller.setAseguradoId(id);
//        loader.setController(controller);
            MainApp.getInstance().changeSceneContent(parent);
        }

//        MainApp.getInstance().changeSceneContent("AseguradoHome.fxml");
    }

    private boolean validarForm() {
        //validar nombre
        if (personaFisicaRadioButton.isSelected() && ((nombreTextField.getText().isEmpty()) || (apellidoPaternoLabel.getText().isEmpty()))) {
            return false;
        } else if (personaMoralRadionButton.isSelected() && nombreTextField.getText().isEmpty()) {
            return false;
        }
        if (!calleTextField.getText().isEmpty() && exteriorTextField.getText().isEmpty()) {
            return false;
        }
        if (calleTextField.getText().isEmpty() && !exteriorTextField.getText().isEmpty()) {
            return false;
        }
        return true;

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
