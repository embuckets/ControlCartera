/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.EmailPK;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TelefonoPK;
import com.embuckets.controlcartera.entidades.TipoEmail;
import com.embuckets.controlcartera.entidades.TipoTelefono;
import com.embuckets.controlcartera.ui.observable.ObservableArchivo;
import com.embuckets.controlcartera.ui.observable.ObservableEmail;
import com.embuckets.controlcartera.ui.observable.ObservablePoliza;
import com.embuckets.controlcartera.ui.observable.ObservableTelefono;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class AseguradoHomeController implements Initializable {

    private Asegurado asegurado;
    private int aseguradoId;
    @FXML
    private Label nombreAseguradoLabel;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField paternoteTextField;
    @FXML
    private TextField maternoTextField;
    @FXML
    private TextField rfcTextField;
    @FXML
    private TextField nacimientoTextField;
    @FXML
    private TextField tipoPersonaTextField;
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
    private TextField delegacionTextField;
    @FXML
    private TextField estadoTextField;
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
    @FXML
    private TextArea notaTextArea;
    @FXML
    private Button informacionTabRegesarButton;
    @FXML
    private TableView polizasTableView;
    @FXML
    private TableColumn polizaTableColumn;
    @FXML
    private TableColumn aseguradoraTableColumn;
    @FXML
    private TableColumn ramoTableColumn;
    @FXML
    private TableColumn productoTableColumn;
    @FXML
    private TableColumn planTableColumn;
    @FXML
    private TableColumn primaTableColumn;
    @FXML
    private Button polizasTabRegresarButton;

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

    /**
     * metodo para probar elpaso de parametros desde otro controlador
     *
     * @param asegurado
     */
    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    public void setAseguradoId(int id) {
        aseguradoId = id;
    }

    private void initData() {
        llenarDatosPersonales();
        llenarDomicilio();
        llenarTablaTelefono();
        llenarTablaEmail();
        llenarTablaDocumento();
        llenarNota();
        llenarTablaPoliza();
//        StringProperty nombre = Main.getInstance().getNombre();
//        
//        textNombre.setText(asegurado.getNombre());
//        textPaterno.setText(asegurado.getApellidoPaterno().orElse(""));
//        textMaterno.setText(asegurado.getApellidoMaterno().orElse(""));
//        System.out.println(asegurado);
    }

    private void llenarDatosPersonales() {
        nombreTextField.setText(asegurado.getCliente().getNombre());
        paternoteTextField.setText(asegurado.getCliente().getApellidopaterno());
        maternoTextField.setText(asegurado.getCliente().getApellidomaterno());
        nacimientoTextField.setText((asegurado.getCliente().getNacimiento() != null) ? asegurado.getCliente().getNacimiento().toString() : "");
        tipoPersonaTextField.setText(asegurado.getTipopersona().getTipopersona());
        rfcTextField.setText(asegurado.getRfc());
    }

    private void llenarDomicilio() {
        calleTextField.setText(asegurado.getIddomicilio().getCalle());
        exteriorTextField.setText(asegurado.getIddomicilio().getExterior());
        interiorTextField.setText(asegurado.getIddomicilio().getInterior());
        codigoPostaTextField.setText(asegurado.getIddomicilio().getCodigopostal());
        coloniaTextField.setText(asegurado.getIddomicilio().getColonia());
        delegacionTextField.setText(asegurado.getIddomicilio().getDelegacion().getDelegacion());
        estadoTextField.setText(asegurado.getIddomicilio().getEstado().getEstado());
    }

    private void llenarTablaTelefono() {
        List<ObservableTelefono> obsTelefonoList = new ArrayList<>();
        for (Telefono telefono : asegurado.getTelefonoList()) {
            ObservableTelefono obsTel = new ObservableTelefono(telefono.getTelefonoPK().getTelefono(), telefono.getExtension(), telefono.getTipotelefono().getTipotelefono());
            obsTel.setIdCliente(telefono.getTelefonoPK().getIdcliente());
            obsTelefonoList.add(obsTel);
        }

        telefonoTableView.setItems(FXCollections.observableArrayList(obsTelefonoList));
        telefonoTableColumn.setCellValueFactory(new PropertyValueFactory("telefono"));
        extensionTableColumn.setCellValueFactory(new PropertyValueFactory("extension"));
        tipoTelefonoTableColumn.setCellValueFactory(new PropertyValueFactory("tipo"));
        fillTipoTelefonoComboBox();

        telefonoTableView.setRowFactory(new Callback<TableView<ObservableTelefono>, TableRow<ObservableTelefono>>() {
            @Override
            public TableRow<ObservableTelefono> call(TableView<ObservableTelefono> tableView) {
                final TableRow<ObservableTelefono> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Borrar");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        ObservableTelefono item = row.getItem();
                        Telefono tel = new Telefono(new TelefonoPK(item.getIdCliente(), item.telefonoProperty().get()));
                        asegurado.getTelefonoList().remove(tel);
                        telefonoTableView.getItems().remove(row.getItem());
                        //TODO: borrar telefono de la base de datos
                    }
                });
                MenuItem editItem = new MenuItem("Editar");
                editItem.setOnAction((ActionEvent event) -> {
                    Optional<Telefono> result = createEditTelefonoDialog(row.getItem()).showAndWait();
                    result.ifPresent(telefono -> {
                        System.out.println(telefono);
                        //borrar telefono anterior
                        //meter telefono nuevo al asegurado y tabla
                        //update telefono
                    });
                });
                rowMenu.getItems().addAll(editItem, removeItem);

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null));
                return row;
            }
        });
    }

    private Dialog<Telefono> createEditTelefonoDialog(ObservableTelefono telefono) {
        Dialog<Telefono> dialog = new Dialog<>();
        dialog.setTitle("Editar telefono");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField telefonoTextField = new TextField();
        telefonoTextField.setText(telefono.telefonoProperty().get());
        TextField extensionTextField = new TextField();
        extensionTextField.setText(telefono.extensionProperty().get());

        //combo box
        String[] tipoTelefono = {"Casa", "Movil", "Trabajo"};
        ObservableList<String> list = FXCollections.observableArrayList(tipoTelefono);
        ComboBox tipoTelefonoComboBox = new ComboBox(list);
        tipoTelefonoComboBox.getSelectionModel().select(list.indexOf(telefono.tipoProperty().get()));

        grid.add(new Label("Telefono"), 0, 0);
        grid.add(telefonoTextField, 1, 0);
        grid.add(new Label("Extension"), 0, 1);
        grid.add(extensionTextField, 1, 1);
        grid.add(new Label("Tipo de telefono"), 0, 2);
        grid.add(tipoTelefonoComboBox, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                Telefono newTel = new Telefono(new TelefonoPK(telefono.getIdCliente(), telefonoTextField.getText()));
                newTel.setExtension(extensionTextField.getText());
                newTel.setTipotelefono(new TipoTelefono(tipoTelefonoComboBox.getValue().toString()));
                return newTel;
            }
            return null;
        });

        return dialog;

    }

    private void fillTipoTelefonoComboBox() {
        //pedir los estados a la base de datos
        String[] tipoTelefono = {"Casa", "Movil", "Trabajo"};
        ObservableList<String> list = FXCollections.observableArrayList(tipoTelefono);
        tipoTelefonoComboBox.getItems().addAll(list);
        tipoTelefonoComboBox.getSelectionModel().select(0);
    }

    private void llenarTablaEmail() {
        List<ObservableEmail> obsEmailList = new ArrayList<>();
        for (Email email : asegurado.getEmailList()) {
            ObservableEmail obs = new ObservableEmail(email.getEmailPK().getEmail(), email.getTipoemail().getTipoemail());
            obs.setIdCliente(email.getEmailPK().getIdcliente());
            obsEmailList.add(obs);
        }
        emailTableView.setItems(FXCollections.observableArrayList(obsEmailList));
        emailTableColumn.setCellValueFactory(new PropertyValueFactory("email"));
        tipoEmailTableColumn.setCellValueFactory(new PropertyValueFactory("tipo"));
        fillTipoEmailComboBox();

        emailTableView.setRowFactory(new Callback<TableView<ObservableEmail>, TableRow<ObservableEmail>>() {
            @Override
            public TableRow<ObservableEmail> call(TableView<ObservableEmail> tableView) {
                final TableRow<ObservableEmail> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Borrar");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        ObservableEmail item = row.getItem();
                        Email email = new Email(new EmailPK(item.getIdCliente(), item.emailProperty().get()));
                        asegurado.getEmailList().remove(email);
                        emailTableView.getItems().remove(row.getItem());
                        //TODO: borrar email de la base de datos
                    }
                });
                MenuItem editItem = new MenuItem("Editar");
                editItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        ObservableEmail item = row.getItem();
                        Email tel = new Email(new EmailPK(item.getIdCliente(), item.emailProperty().get()));
                        //crear display window para editar telefono y guardar cambios
                        //TODO: borrar telefono de la base de datos
                    }
                });
                rowMenu.getItems().addAll(editItem, removeItem);

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null));
                return row;
            }
        });
    }

    private void fillTipoEmailComboBox() {
        //pedir los estados a la base de datos
        String[] values = {"Personal", "Trabajo"};
        ObservableList<String> list = FXCollections.observableArrayList(values);
        tipoEmailComboBox.getItems().addAll(list);
        tipoEmailComboBox.getSelectionModel().select(0);
    }

    private void llenarTablaDocumento() {
        List<ObservableArchivo> documentos = new ArrayList<>();
        for (DocumentoAsegurado doc : asegurado.getDocumentoAseguradoList()) {
            ObservableArchivo obs = new ObservableArchivo(doc.getDocumentoAseguradoPK().getNombre(), doc.getTipoDocumentoAsegurado().getTipodocumento());
            obs.setIdCliente(doc.getDocumentoAseguradoPK().getIdcliente());
            documentos.add(obs);
        }
        documentoTableView.setItems(FXCollections.observableArrayList(documentos));
        archivoTableColumn.setCellValueFactory(new PropertyValueFactory("archivo"));
        tipoArchivoTableColumn.setCellValueFactory(new PropertyValueFactory("tipo"));
        fillTipoDocumentoComboBox();
    }

    private void fillTipoDocumentoComboBox() {
        String[] documentos = {"Identificacion", "Domicilio", "RFC", "Otro"};
        ObservableList<String> estadosList = FXCollections.observableArrayList(documentos);
        tipoArchivoComboBox.getItems().addAll(estadosList);
        tipoArchivoComboBox.getSelectionModel().select(0);
    }

    private void llenarNota() {
        notaTextArea.setText(asegurado.getNota());
    }

    @FXML
    private void agregarTelefono(ActionEvent event) {
        Telefono telefono = new Telefono(asegurado.getIdcliente(), telefonoTextField.getText());
        telefono.setExtension(extensionTextField.getText());
        telefono.setTipotelefono(new TipoTelefono(tipoTelefonoComboBox.getValue().toString()));
        telefonoTableView.getItems().add(new ObservableTelefono(telefono));
        //
        asegurado.agregarTelefono(telefono);
        //TODO: persist telefono
    }

    @FXML
    private void agregarEmail(ActionEvent event) {
        Email email = new Email(asegurado.getIdcliente(), emailTextField.getText());
        email.setTipoemail(new TipoEmail(tipoEmailComboBox.getValue().toString()));
        emailTableView.getItems().add(new ObservableEmail(email));
        asegurado.agregarEmail(email);
        //TODO: persist email
    }

    @FXML
    private void seleccionarArchivo(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Elige un Documento");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = chooser.showOpenDialog(MainApp.getInstance().getStage());
        if (file != null) {
            archivoTextField.setText(file.getPath());
        }
    }

    @FXML
    private void agregarDocumento(ActionEvent event) {
        //TODO: crear DocumentoAsegurado 
        //agregar el documento al asegurado
        //persist el documento
        ObservableArchivo obs = new ObservableArchivo(archivoTextField.getText(), tipoArchivoComboBox.getValue().toString());
        documentoTableView.getItems().add(obs);
        archivoTextField.setText("");
    }

    @FXML
    private void homePage(ActionEvent event) {
        try {
            MainApp.getInstance().changeSceneContent("/fxml/Home.fxml");
        } catch (IOException ex) {
            Logger.getLogger(AseguradoHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTablaPoliza() {
        polizasTableView.setItems(createObservablePolizaList());
        polizaTableColumn.setCellValueFactory(new PropertyValueFactory("poliza"));
        aseguradoraTableColumn.setCellValueFactory(new PropertyValueFactory("aseguradora"));
        ramoTableColumn.setCellValueFactory(new PropertyValueFactory("ramo"));
        productoTableColumn.setCellValueFactory(new PropertyValueFactory("producto"));
        planTableColumn.setCellValueFactory(new PropertyValueFactory("plan"));
        primaTableColumn.setCellValueFactory(new PropertyValueFactory("prima"));
    }

    private ObservableList<ObservablePoliza> createObservablePolizaList() {
        List<ObservablePoliza> obsList = new ArrayList<>();
        for (Poliza poliza : asegurado.getPolizaList()) {
            ObservablePoliza obs = new ObservablePoliza(poliza);
            obsList.add(obs);
        }
        return FXCollections.observableArrayList(obsList);
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
                        T item = row.getItem();
                        if (item instanceof ObservableArchivo) {
                            //borrar archivo
                            //asegurado.getDocumentoAseguradoList().remove((DocumentoAsegurado)item);
                            //esta seguro que quiere borrar
                            //TODO: remove de base de datos
                        }
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

    @FXML
    private void editarDatosPersonales(ActionEvent event) {
    }

    @FXML
    private void editarDomicilio(ActionEvent event) {
    }

    @FXML
    private void editarNota(ActionEvent event) {
    }

}
