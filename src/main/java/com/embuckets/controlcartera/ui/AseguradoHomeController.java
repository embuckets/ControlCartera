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
import com.embuckets.controlcartera.entidades.EmailPK;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TelefonoPK;
import com.embuckets.controlcartera.entidades.TipoEmail;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.TipoTelefono;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Pair;
import org.hibernate.Hibernate;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class AseguradoHomeController implements Initializable, Controller {

    @FXML
    private Label nombreAseguradoLabel;
    private String location = "/fxml/AseguradoHome.fxml";
    private Asegurado asegurado;
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
    @FXML
    private TableView<DocumentoAsegurado> documentoTableView;
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
    private TableView<Poliza> polizasTableView;
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
    private TableColumn estadoTableColumn;
    @FXML
    private Button polizasTabRegresarButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
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
        nombreAseguradoLabel.setText(asegurado.getCliente().nombreProperty().get());
        nombreTextField.setText(asegurado.getCliente().getNombre());
        paternoteTextField.setText(asegurado.getCliente().getApellidopaterno());
        maternoTextField.setText(asegurado.getCliente().getApellidomaterno());
        nacimientoTextField.setText((asegurado.getCliente().getNacimiento() != null) ? asegurado.getCliente().getNacimiento().toString() : "");
        tipoPersonaTextField.setText(asegurado.getTipopersona().getTipopersona());
        rfcTextField.setText(asegurado.getRfc());
    }

    private void llenarDomicilio() {
        if (asegurado.getIddomicilio() == null) {
            asegurado.setIddomicilio(new Domicilio());
            Domicilio domicilio = asegurado.getIddomicilio();
            domicilio.setCalle("");
            domicilio.setExterior("");
            domicilio.setInterior("");
            domicilio.setCodigopostal("");
            domicilio.setColonia("");
            domicilio.setDelegacion(new Delegacion());
            domicilio.setEstado(new Estado());
        }
        calleTextField.setText(asegurado.getIddomicilio().getCalle());
        exteriorTextField.setText(asegurado.getIddomicilio().getExterior());
        interiorTextField.setText(asegurado.getIddomicilio().getInterior());
        codigoPostaTextField.setText(asegurado.getIddomicilio().getCodigopostal());
        coloniaTextField.setText(asegurado.getIddomicilio().getColonia());
        delegacionTextField.setText(asegurado.getIddomicilio().getDelegacion().getDelegacion());
        estadoTextField.setText(asegurado.getIddomicilio().getEstado().getEstado());
    }

    private void llenarTablaTelefono() {
//        List<Telefono> obsTelefonoList = new ArrayList<>();
//        for (Telefono telefono : asegurado.getTelefonoList()) {
//            ObservableTelefono obsTel = new ObservableTelefono(telefono.getTelefonoPK().getTelefono(), telefono.getExtension(), telefono.getTipotelefono().getTipotelefono());
//            obsTel.setIdCliente(telefono.getTelefonoPK().getIdcliente());
//            obsTelefonoList.add(obsTel);
//        }

        telefonoTableView.setItems(FXCollections.observableArrayList(asegurado.getTelefonoList()));
        telefonoTableColumn.setCellValueFactory(new PropertyValueFactory("telefono"));
        extensionTableColumn.setCellValueFactory(new PropertyValueFactory("extension"));
        tipoTelefonoTableColumn.setCellValueFactory(new PropertyValueFactory("tipo"));
        fillTipoTelefonoComboBox();
        //crear contex menu
        telefonoTableView.setRowFactory(new Callback<TableView<Telefono>, TableRow<Telefono>>() {
            @Override
            public TableRow<Telefono> call(TableView<Telefono> tableView) {
                final TableRow<Telefono> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Borrar");
                removeItem.setOnAction((ActionEvent event) -> {
                    try {
                        borrarTelefono(row.getItem());
                    } catch (Exception e) {
                        showAlert(e, "Error al borrar email");
                    }
//TODO: borrar telefono de la base de datos
                });
                MenuItem editItem = new MenuItem("Editar");
                editItem.setOnAction((ActionEvent event) -> {
                    Optional<Telefono> result = createEditTelefonoDialog(row.getItem()).showAndWait();
                    result.ifPresent(nuevo -> {
                        try {
                            editarTelefono(row.getItem(), nuevo);
                        } catch (Exception e) {
                            showAlert(e, "Error al editar telefono");
                        }
                        telefonoTableView.getItems().clear();
                        telefonoTableView.setItems(FXCollections.observableArrayList(asegurado.getTelefonoList()));
                        //TODO: update telefono en la base
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

    private void editarTelefono(Telefono viejo, Telefono nuevo) throws Exception {
        Map<String, Pair<Object, Object>> changes = new HashMap<>();
        changes.put("telefono", new Pair<>(viejo.getTelefonoPK().getTelefono(), nuevo.getTelefonoPK().getTelefono()));
        changes.put("tipo", new Pair<>(viejo.getTipotelefono().getTipotelefono(), nuevo.getTipotelefono().getTipotelefono()));
        changes.put("extension", new Pair<>(viejo.getExtension(), nuevo.getExtension()));
        if (valuesChanged(changes)) {
            try {
                Telefono unproxy = Utilities.initializeAndUnproxy(viejo);
                MainApp.getInstance().getBaseDeDatos().remove(viejo);
                MainApp.getInstance().getBaseDeDatos().create(nuevo);
                asegurado.agregarTelefono(nuevo);
//                viejo.setEmailPK(new EmailPK(asegurado.getIdcliente(), nuevo.getEmailPK().getEmail()));
//                viejo.setTipoemail(new TipoEmail(nuevo.getTipoemail().getTipoemail()));
//                Email unproxy = Utilities.initializeAndUnproxy(viejo);
//                MainApp.getInstance().getBaseDeDatos().edit(unproxy);
            } catch (Exception e) {
//                viejo.setTelefonoPK(new TelefonoPK(asegurado.getIdcliente(), (String) changes.get("telefono").getKey()));
//                viejo.setTipoemail(new TipoEmail((String) changes.get("tipo").getKey()));
                throw e;
            }
        }
    }

    private void borrarTelefono(Telefono telefono) throws Exception {
        try {
            Telefono unproxy = Utilities.initializeAndUnproxy(telefono);
            MainApp.getInstance().getBaseDeDatos().remove(unproxy);
            asegurado.getTelefonoList().remove(telefono);
            telefonoTableView.getItems().remove(telefono);
        } catch (Exception e) {
            throw e;
        }
    }

    private Dialog<Telefono> createEditTelefonoDialog(Telefono telefono) {
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

        TextField telefonoField = new TextField();
        telefonoField.setText(telefono.telefonoProperty().get());
        TextField extensionField = new TextField();
        extensionField.setText(telefono.extensionProperty().get());

        //combo box
        ObservableList<String> list = FXCollections.observableArrayList(Globals.getAllTelefonoTipos());
        ComboBox tipoTelefonoBox = new ComboBox(list);
        tipoTelefonoBox.getSelectionModel().select(list.indexOf(telefono.tipoProperty().get()));

        grid.add(new Label("Telefono"), 0, 0);
        grid.add(telefonoField, 1, 0);
        grid.add(new Label("Extension"), 0, 1);
        grid.add(extensionField, 1, 1);
        grid.add(new Label("Tipo de telefono"), 0, 2);
        grid.add(tipoTelefonoBox, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                Telefono edited = new Telefono(asegurado.getIdcliente(), telefonoField.getText());
                edited.setAsegurado(asegurado);
                edited.setTipotelefono(new TipoTelefono(tipoTelefonoBox.getValue().toString()));
                edited.setExtension(extensionField.getText());
                return edited;
            }
            return null;
        });

        return dialog;

    }

    private void fillTipoTelefonoComboBox() {
        //pedir los estados a la base de datos
        ObservableList<String> list = FXCollections.observableArrayList(Globals.getAllTelefonoTipos());
        tipoTelefonoComboBox.getItems().addAll(list);
        tipoTelefonoComboBox.getSelectionModel().select(0);
    }

    private void llenarTablaEmail() {
//        List<ObservableEmail> obsEmailList = new ArrayList<>();
//        for (Email email : asegurado.getEmailList()) {
//            ObservableEmail obs = new ObservableEmail(email.getEmailPK().getEmail(), email.getTipoemail().getTipoemail());
//            obs.setIdCliente(email.getEmailPK().getIdcliente());
//            obsEmailList.add(obs);
//        }
        emailTableView.setItems(FXCollections.observableArrayList(asegurado.getEmailList()));
        emailTableColumn.setCellValueFactory(new PropertyValueFactory("email"));
        tipoEmailTableColumn.setCellValueFactory(new PropertyValueFactory("tipo"));
        fillTipoEmailComboBox();

        emailTableView.setRowFactory((TableView<Email> tableView) -> {
            final TableRow<Email> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem removeItem = new MenuItem("Borrar");
            removeItem.setOnAction((ActionEvent event) -> {
                try {
                    borrarEmail(row.getItem());
                } catch (Exception e) {
                    showAlert(e, "Error al borrar email");
                }
                //TODO: borrar email de la base de datos
            });
            MenuItem editItem = new MenuItem("Editar");
            editItem.setOnAction((ActionEvent event) -> {
                Optional<Email> result = createEditEmailDialog(row.getItem()).showAndWait();
                result.ifPresent(email -> {
                    try {
                        editarEmail(row.getItem(), email);
                    } catch (Exception ex) {
                        showAlert(ex, "Error al editar email");
                    }
                    emailTableView.getItems().clear();
                    emailTableView.setItems(FXCollections.observableArrayList(asegurado.getEmailList()));
                });
                //crear display window para editar telefono y guardar cambios
                //TODO: borrar telefono de la base de datos
            });
            rowMenu.getItems().addAll(editItem, removeItem);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null));
            return row;
        });
    }

    private void borrarEmail(Email email) throws Exception {
        try {
            Email unproxy = Utilities.initializeAndUnproxy(email);
            MainApp.getInstance().getBaseDeDatos().remove(unproxy);
            asegurado.getEmailList().remove(email);
            emailTableView.getItems().remove(email);
        } catch (Exception e) {
            throw e;
        }
    }

    private void editarEmail(Email viejo, Email nuevo) throws Exception {
        Map<String, Pair<Object, Object>> changes = new HashMap<>();
        changes.put("email", new Pair<>(viejo.getEmailPK().getEmail(), nuevo.getEmailPK().getEmail()));
        changes.put("tipo", new Pair<>(viejo.getTipoemail().getTipoemail(), nuevo.getTipoemail().getTipoemail()));
        if (valuesChanged(changes)) {
            try {
                Email unproxy = Utilities.initializeAndUnproxy(viejo);
                MainApp.getInstance().getBaseDeDatos().remove(viejo);
                MainApp.getInstance().getBaseDeDatos().create(nuevo);
                asegurado.agregarEmail(nuevo);
//                viejo.setEmailPK(new EmailPK(asegurado.getIdcliente(), nuevo.getEmailPK().getEmail()));
//                viejo.setTipoemail(new TipoEmail(nuevo.getTipoemail().getTipoemail()));
//                Email unproxy = Utilities.initializeAndUnproxy(viejo);
//                MainApp.getInstance().getBaseDeDatos().edit(unproxy);
            } catch (Exception e) {
//                viejo.setEmailPK(new EmailPK(asegurado.getIdcliente(), (String) changes.get("email").getKey()));
//                viejo.setTipoemail(new TipoEmail((String) changes.get("tipo").getKey()));
                throw e;
            }
        }
    }

    private Dialog<Email> createEditEmailDialog(Email email) {
        Dialog<Email> dialog = new Dialog<>();
        dialog.setTitle("Editar email");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField emailField = new TextField();
        emailField.setText(email.getEmailPK().getEmail());

        //combo box
        ObservableList<String> list = FXCollections.observableArrayList(Globals.getAllEmailTipos());
        ComboBox tipoEmailBox = new ComboBox(list);
        tipoEmailBox.getSelectionModel().select(list.indexOf(email.tipoProperty().get()));

        grid.add(new Label("Email"), 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(new Label("Tipo de email"), 0, 1);
        grid.add(tipoEmailBox, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                Email edited = new Email(new EmailPK(email.getEmailPK().getIdcliente(), emailField.getText()));
                edited.setAsegurado(asegurado);
                edited.setTipoemail(new TipoEmail(tipoEmailBox.getValue().toString()));
                return edited;
            }
            return null;
        });

        return dialog;
    }

    private void fillTipoEmailComboBox() {
        //pedir los estados a la base de datos
        ObservableList<String> list = FXCollections.observableArrayList(Globals.getAllEmailTipos());
        tipoEmailComboBox.getItems().addAll(list);
        tipoEmailComboBox.getSelectionModel().select(0);
    }

    private void llenarTablaDocumento() {
        documentoTableView.setItems(FXCollections.observableArrayList(asegurado.getDocumentoAseguradoList()));
        archivoTableColumn.setCellValueFactory(new PropertyValueFactory("archivo"));
        tipoArchivoTableColumn.setCellValueFactory(new PropertyValueFactory("tipo"));

        documentoTableView.setRowFactory((TableView<DocumentoAsegurado> table) -> {
            final TableRow<DocumentoAsegurado> row = new TableRow<>();
            final ContextMenu menu = new ContextMenu();
            MenuItem verItem = new MenuItem("Ver");
            verItem.setOnAction((event) -> {
                DocumentoAsegurado documento = row.getItem();
                File file = new File(documento.getDocumentoAseguradoPK().getNombre());
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
                Optional<DocumentoAsegurado> documentoEditado = createDialogEditarDocumento(row.getItem()).showAndWait();
                documentoEditado.ifPresent((present) -> {
                    //TODO: update documento en base de datos
                    //refresh tabla documentos
                    documentoTableView.getItems().clear();
                    documentoTableView.setItems(FXCollections.observableArrayList(asegurado.getDocumentoAseguradoList()));
                });
            });
            MenuItem eliminarItem = new MenuItem("Eliminar");
            eliminarItem.setOnAction((event) -> {
                //TODO: eliminar de base de datos
                asegurado.getDocumentoAseguradoList().remove(row.getItem());
                documentoTableView.getItems().clear();
                documentoTableView.setItems(FXCollections.observableArrayList(asegurado.getDocumentoAseguradoList()));

            });
            menu.getItems().addAll(verItem, editarItem, eliminarItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(menu)
                            .otherwise((ContextMenu) null));

            return row; //To change body of generated lambdas, choose Tools | Templates.
        });

        fillTipoDocumentoComboBox();
    }

    private Dialog<DocumentoAsegurado> createDialogEditarDocumento(DocumentoAsegurado doc) {
        Dialog<DocumentoAsegurado> dialog = new Dialog<>();
        dialog.setTitle("Editar documento");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField archivoField = new TextField(doc.getNombreArchivo());
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

        //combo box
        ObservableList<String> list = FXCollections.observableArrayList(Globals.getAllDocumentoAseguradoTipos());
        ComboBox tipoDocumentoBox = new ComboBox(list);
        tipoDocumentoBox.getSelectionModel().select(list.indexOf(doc.getTipoDocumentoAsegurado().getTipodocumento()));

        grid.add(archivoField, 0, 0);
        grid.add(selectButton, 1, 0);
        grid.add(new Label("Tipo de documento"), 0, 1);
        grid.add(tipoDocumentoBox, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                doc.getDocumentoAseguradoPK().setNombre(archivoField.getText());
                doc.getTipoDocumentoAsegurado().setTipodocumento(tipoDocumentoBox.getValue().toString());
                return doc;
            }
            return null;
        });

        return dialog;
    }

    private void fillTipoDocumentoComboBox() {
        ObservableList<String> estadosList = FXCollections.observableArrayList(Globals.getAllDocumentoAseguradoTipos());
        tipoArchivoComboBox.getItems().addAll(estadosList);
        tipoArchivoComboBox.getSelectionModel().select(0);
    }

    private void llenarNota() {
        notaTextArea.setText(asegurado.getNota());
    }

    @FXML
    private void agregarTelefono(ActionEvent event) {
        try {
            Telefono telefono = new Telefono(asegurado.getIdcliente(), telefonoTextField.getText());
            telefono.setExtension(extensionTextField.getText());
            telefono.setTipotelefono(new TipoTelefono(tipoTelefonoComboBox.getValue().toString()));
            telefono.setAsegurado(asegurado);
            MainApp.getInstance().getBaseDeDatos().create(telefono);
            asegurado.agregarTelefono(telefono);
            telefonoTableView.getItems().add(telefono);
            telefonoTextField.setText("");
            extensionTextField.setText("");
        } catch (Exception ex) {
            showAlert(ex, "Error al guardar telefono");
        }
    }

    @FXML
    private void agregarEmail(ActionEvent event) {
        try {
            Email email = new Email(asegurado.getIdcliente(), emailTextField.getText());
            email.setTipoemail(new TipoEmail(tipoEmailComboBox.getValue().toString()));
            email.setAsegurado(asegurado);
            MainApp.getInstance().getBaseDeDatos().create(email);
            asegurado.agregarEmail(email);
            emailTableView.getItems().add(email);
            emailTextField.setText("");

        } catch (Exception ex) {
            showAlert(ex, "Error al guardar email");
        }
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
        DocumentoAsegurado nuevoDoc = new DocumentoAsegurado();
        nuevoDoc.getDocumentoAseguradoPK().setIdcliente(asegurado.getId());
        nuevoDoc.getDocumentoAseguradoPK().setNombre(archivoTextField.getText());
        nuevoDoc.getTipoDocumentoAsegurado().setTipodocumento(tipoArchivoComboBox.getValue().toString());
        documentoTableView.getItems().add(nuevoDoc);
        archivoTextField.setText("");
    }

    @FXML
    private void homePage(ActionEvent event) {
        try {
            MainApp.getInstance().goHome();
        } catch (IOException ex) {
            Logger.getLogger(AseguradoHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTablaPoliza() {
        polizasTableView.setItems(FXCollections.observableArrayList(asegurado.getPolizaList()));
        polizaTableColumn.setCellValueFactory(new PropertyValueFactory("poliza"));
        aseguradoraTableColumn.setCellValueFactory(new PropertyValueFactory("aseguradora"));
        ramoTableColumn.setCellValueFactory(new PropertyValueFactory("ramo"));
        productoTableColumn.setCellValueFactory(new PropertyValueFactory("producto"));
        planTableColumn.setCellValueFactory(new PropertyValueFactory("plan"));
        primaTableColumn.setCellValueFactory(new PropertyValueFactory("prima"));
        estadoTableColumn.setCellValueFactory(new PropertyValueFactory("estado"));

        polizasTableView.setRowFactory((TableView<Poliza> tableView) -> {
            final TableRow<Poliza> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    goPolizaHomeScene(row.getItem());
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    Poliza selected = polizasTableView.getSelectionModel().getSelectedItem();
                    System.out.println(selected);
                    if (selected != null) {
                        final ContextMenu rowMenu = createContextMenu(selected);
                        row.contextMenuProperty().bind(
                                Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                        .then(rowMenu)
                                        .otherwise((ContextMenu) null));
                    }
                }
            });
            return row;
        });
    }

    private ContextMenu createContextMenu(Poliza poliza) {
        ContextMenu menu = new ContextMenu();
        MenuItem verItem = new MenuItem("Ver");
        verItem.setOnAction((event) -> {
            goPolizaHomeScene(polizasTableView.getSelectionModel().getSelectedItem());
        });
        menu.getItems().add(verItem);

        if (poliza.getEstado().getEstado().equalsIgnoreCase(Globals.POLIZA_ESTADO_VIGENTE)) {
            MenuItem cancelarItem = new MenuItem("Cancelar");
            cancelarItem.setOnAction((event) -> {
                poliza.getEstado().setEstado(Globals.POLIZA_ESTADO_CANCELADA);
                //TODO:
                //update en base de datos
                refreshPolizaList();
            });
            MenuItem renovarItem = new MenuItem("Renovar");
            renovarItem.setOnAction((event) -> {
                poliza.getEstado().setEstado(Globals.POLIZA_ESTADO_RENOVADA);
                refreshPolizaList();
                //TODO: abrir ventana para renovar poliza
                //update en base de datos
            });
            menu.getItems().add(cancelarItem);
            menu.getItems().add(renovarItem);
        }

        MenuItem borrarItem = new MenuItem("Eliminar");
        borrarItem.setOnAction((event) -> {
            asegurado.getPolizaList().remove(polizasTableView.getSelectionModel().getSelectedItem());
            refreshPolizaList();
            //TODO: eliminar poliza de base de datos
        });
        menu.getItems().add(borrarItem);

        return menu;
    }

    private void goPolizaHomeScene(Poliza poliza) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/PolizaHome.fxml"), null, new JavaFXBuilderFactory());
            Parent parent = loader.load();
            PolizaHomeController controller = loader.<PolizaHomeController>getController();
            controller.setPoliza((Poliza) poliza);
            MainApp.getInstance().changeSceneContent(this, location, parent, loader);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refreshPolizaList() {
        polizasTableView.getItems().clear();
        polizasTableView.setItems(FXCollections.observableArrayList(asegurado.getPolizaList()));
    }

    @FXML
    private void editarDatosPersonales(ActionEvent event) {
        Optional<Asegurado> editedAsegurado = createEditDatosPersonalesDialog(asegurado).showAndWait();
        //OLD VALUES??
        if (editedAsegurado.isPresent()) {
            Asegurado edited = editedAsegurado.get();
//            List<Pair<String, String>> changes = new ArrayList<>();
            //Pari<old,new>
            Map<String, Pair<Object, Object>> changes = new HashMap<>();
            changes.put("nombre", new Pair(asegurado.getCliente().getNombre(), edited.getCliente().getNombre()));
            changes.put("paterno", new Pair(asegurado.getCliente().getApellidopaterno(), edited.getCliente().getApellidopaterno()));
            changes.put("materno", new Pair(asegurado.getCliente().getApellidomaterno(), edited.getCliente().getApellidomaterno()));
            changes.put("nacimiento", new Pair(asegurado.getCliente().getNacimiento(), edited.getCliente().getNacimiento()));
            changes.put("tipopersona", new Pair(asegurado.getTipopersona().getTipopersona(), edited.getTipopersona().getTipopersona()));
            changes.put("rfc", new Pair(asegurado.getRfc(), edited.getRfc()));
            if (valuesChanged(changes)) {
                try {
                    asegurado.setTipopersona(new TipoPersona(edited.getTipopersona().getTipopersona()));
                    asegurado.getCliente().setNombre(edited.getCliente().getNombre());
                    asegurado.getCliente().setApellidopaterno(edited.getCliente().getApellidopaterno());
                    asegurado.getCliente().setApellidomaterno(edited.getCliente().getApellidomaterno());
                    asegurado.getCliente().setNacimiento(edited.getCliente().getNacimiento());
                    asegurado.setRfc(edited.getRfc());
//                Hibernate.initialize(asegurado);
                    Asegurado unproxy = Utilities.initializeAndUnproxy(asegurado);
                    MainApp.getInstance().getBaseDeDatos().edit(unproxy);
                } catch (Exception e) {
                    showAlert(e, "Error al editar asegurado");
                    asegurado.setTipopersona(new TipoPersona((String) changes.get("tipopersona").getKey()));
                    asegurado.getCliente().setNombre((String) changes.get("nombre").getKey());
                    asegurado.getCliente().setApellidopaterno((String) changes.get("paterno").getKey());
                    asegurado.getCliente().setApellidomaterno((String) changes.get("materno").getKey());
                    asegurado.getCliente().setNacimiento((LocalDate) changes.get("nacimiento").getKey());
                    asegurado.setRfc((String) changes.get("rfc").getKey());
                }
            }

        }
        llenarDatosPersonales();
    }

    private Dialog<Asegurado> createEditDatosPersonalesDialog(Asegurado asegurado) {
        Dialog<Asegurado> dialog = new Dialog<>();
        dialog.setTitle("Editar asegurado");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label paternoLabel = new Label("Apellido Paterno");
        Label maternoLabel = new Label("Apellido Materno");
        TextField nombreField = new TextField();
        nombreField.setText(asegurado.getCliente().getNombre());
        TextField paternoField = new TextField();
        paternoField.setText(asegurado.getCliente().getApellidopaterno());
        TextField maternoField = new TextField();
        maternoField.setText(asegurado.getCliente().getApellidomaterno());
        DatePicker nacimientoPicker = new DatePicker(asegurado.getCliente().getNacimiento());

        RadioButton personaFisicaRadioButton = new RadioButton(Globals.TIPO_PERSONA_FISICA);
        RadioButton personaMoralRadioButton = new RadioButton(Globals.TIPO_PERSONA_MORAL);
        ToggleGroup group = new ToggleGroup();
        personaFisicaRadioButton.setToggleGroup(group);
        personaMoralRadioButton.setToggleGroup(group);
        if (asegurado.getTipopersona().getTipopersona().equalsIgnoreCase(Globals.TIPO_PERSONA_MORAL)) {
            personaMoralRadioButton.setSelected(true);
        } else {
            personaFisicaRadioButton.setSelected(true);
        }
        //si es persona moral no mostrar estos campos cuando se muestra el dialogo
        paternoField.setVisible(personaFisicaRadioButton.isSelected());
        maternoField.setVisible(personaFisicaRadioButton.isSelected());
        paternoLabel.setVisible(personaFisicaRadioButton.isSelected());
        maternoLabel.setVisible(personaFisicaRadioButton.isSelected());

        personaFisicaRadioButton.setOnAction(event -> {
            paternoField.setVisible(true);
            maternoField.setVisible(true);
            paternoLabel.setVisible(true);
            maternoLabel.setVisible(true);
            paternoField.setText(asegurado.getCliente().getApellidopaterno());
            maternoField.setText(asegurado.getCliente().getApellidomaterno());
        });
        personaMoralRadioButton.setOnAction(event -> {
            paternoField.setVisible(false);
            maternoField.setVisible(false);
            paternoLabel.setVisible(false);
            maternoLabel.setVisible(false);
            paternoField.setText("");
            maternoField.setText("");

        });
        HBox radioHbox = new HBox();
        radioHbox.getChildren().addAll(personaFisicaRadioButton, personaMoralRadioButton);
        TextField rfcField = new TextField(asegurado.getRfc());

        grid.add(new Label("Tipo de persona"), 0, 0);
        grid.add(radioHbox, 1, 0);
        grid.add(new Label("Nombre"), 0, 1);
        grid.add(nombreField, 1, 1);
        grid.add(paternoLabel, 0, 2);
        grid.add(paternoField, 1, 2);
        grid.add(maternoLabel, 0, 3);
        grid.add(maternoField, 1, 3);
        grid.add(new Label("Fecha de nacimiento"), 0, 4);
        grid.add(nacimientoPicker, 1, 4);
        grid.add(new Label("RFC"), 0, 5);
        grid.add(rfcField, 1, 5);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                Asegurado nuevoAsegurado = new Asegurado();
                nuevoAsegurado.setTipopersona(new TipoPersona(((RadioButton) group.getSelectedToggle()).getText()));
                nuevoAsegurado.getCliente().setNombre(nombreField.getText());
                nuevoAsegurado.getCliente().setApellidopaterno(paternoField.getText());
                nuevoAsegurado.getCliente().setApellidomaterno(maternoField.getText());
                nuevoAsegurado.getCliente().setNacimiento(nacimientoPicker.getValue());
                nuevoAsegurado.setRfc(rfcField.getText());
                return nuevoAsegurado;
            }
            return null;
        });
        return dialog;
    }

    @FXML
    private void editarDomicilio(ActionEvent event) {
        Domicilio actual = asegurado.getIddomicilio();
        Optional<Domicilio> editedDomicilio = createEditDomicilioDialog(asegurado.getIddomicilio()).showAndWait();
        //TODO: update domicilio
        if (editedDomicilio.isPresent()) {
            Domicilio edited = editedDomicilio.get();
            //Pair<old,new>
            Map<String, Pair<Object, Object>> changes = new HashMap<>();
            changes.put("calle", new Pair(actual.getCalle(), edited.getCalle()));
            changes.put("exterior", new Pair<>(actual.getExterior(), edited.getExterior()));
            changes.put("interior", new Pair<>(actual.getInterior(), edited.getInterior()));
            changes.put("codigo", new Pair<>(actual.getCodigopostal(), edited.getCodigopostal()));
            changes.put("colonia", new Pair<>(actual.getColonia(), edited.getColonia()));
            changes.put("delegacion", new Pair<>(actual.getDelegacion().getDelegacion(), edited.getDelegacion().getDelegacion()));
            changes.put("estado", new Pair<>(actual.getEstado().getEstado(), edited.getEstado().getEstado()));
            if (valuesChanged(changes)) {
                //edit
                try {
                    actual.setCalle(edited.getCalle());
                    actual.setExterior(edited.getExterior());
                    actual.setInterior(edited.getInterior());
                    actual.setCodigopostal(edited.getCodigopostal());
                    actual.setColonia(edited.getColonia());
                    actual.setDelegacion(new Delegacion(edited.getDelegacion().getDelegacion()));
                    actual.setEstado(new Estado(edited.getEstado().getEstado()));
                    Domicilio unproxy = Utilities.initializeAndUnproxy(actual);
                    MainApp.getInstance().getBaseDeDatos().edit(unproxy);
                } catch (Exception e) {
                    showAlert(e, "Error al editar domicilio");
                    actual.setCalle((String) changes.get("calle").getKey());
                    actual.setExterior((String) changes.get("exterior").getKey());
                    actual.setInterior((String) changes.get("interior").getKey());
                    actual.setCodigopostal((String) changes.get("codigo").getKey());
                    actual.setColonia((String) changes.get("colonia").getKey());
                    actual.setDelegacion(new Delegacion((String) changes.get("delegacion").getKey()));
                    actual.setEstado(new Estado((String) changes.get("estado").getKey()));
                }
            }
        }
        llenarDomicilio();
    }

    private boolean valuesChanged(Map<String, Pair<Object, Object>> changes) {
        for (Pair pair : changes.values()) {
            Object notNull = pair.getKey() == null ? "" : pair.getKey();
            if (!notNull.equals(pair.getValue())) {
                return true;
            }
        }
        return false;
//        return changes.values().stream().anyMatch(p -> !p.getKey().equals(p.getValue()));
    }

    private Dialog<Domicilio> createEditDomicilioDialog(Domicilio domicilio) {
//        if (domicilio == null) {
//            domicilio = new Domicilio();
//        }
        Dialog<Domicilio> dialog = new Dialog<>();
        dialog.setTitle("Editar domicilio");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonData.OK_DONE);

        //create labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField calleField = new TextField(domicilio.getCalle());
        TextField exteriorField = new TextField(domicilio.getExterior());
        TextField interiorField = new TextField(domicilio.getInterior());
        TextField codigoPostalField = new TextField(domicilio.getCodigopostal());
        TextField coloniaField = new TextField(domicilio.getColonia());

//        String[] delegaciones = {"Álvaro Obregón", " Azcapotzalco", " Benito Juárez",
//            " Coyoacán", " Cuajimalpa de Morelos", " Cuauhtémoc", "Gustavo A. Madero",
//            " Iztacalco", " Iztapalapa", " La Magdalena Contreras", "Miguel Hidalgo", " Milpa Alta",
//            " Tláhuac", " Tlalpan", " Venustiano Carranza", " Xochimilco"};
        List<Delegacion> delegaciones = MainApp.getInstance().getBaseDeDatos().getAll(Delegacion.class);
        ObservableList<String> obsDelegaciones = FXCollections.observableArrayList(delegaciones.stream().map(d -> d.getDelegacion()).collect(Collectors.toList()));
        ComboBox delegacionBox = new ComboBox(obsDelegaciones);
        delegacionBox.getSelectionModel().select(obsDelegaciones.indexOf(domicilio.getDelegacion().getDelegacion()));
        if (delegacionBox.getSelectionModel().getSelectedItem() == null) {
            delegacionBox.getSelectionModel().select(0);
        }

//        String[] estados = {"Aguascalientes", " Baja California", " Baja California Sur",
//            " Campeche", " Chiapas", " Chihuahua", " Ciudad de México",
//            " Coahuila", " Colima", " Durango", " Estado de México", " Guanajuato",
//            " Guerrero", " Hidalgo", " Jalisco", " Michoacán", " Morelos",
//            " Nayarit", " Nuevo León", " Oaxaca", " Puebla", " Querétaro",
//            " Quintana Roo", " San Luis Potosí", " Sinaloa", " Sonora",
//            " Tabasco", " Tamaulipas", " Tlaxcala", " Veracruz", " Yucatán", " Zacatecas"};
        List<Estado> estados = MainApp.getInstance().getBaseDeDatos().getAll(Estado.class);
        ObservableList<String> obsEstados = FXCollections.observableArrayList(estados.stream().map(e -> e.getEstado()).collect(Collectors.toList()));
        ComboBox estadosBox = new ComboBox(obsEstados);
        estadosBox.getSelectionModel().select(obsEstados.indexOf(domicilio.getEstado().getEstado()));
        if (estadosBox.getSelectionModel().getSelectedItem() == null) {
            estadosBox.getSelectionModel().select(0);
        }
        grid.add(new Label("Calle"), 0, 0);
        grid.add(calleField, 1, 0);
        grid.add(new Label("No. Exterior"), 0, 1);
        grid.add(exteriorField, 1, 1);
        grid.add(new Label("No. Interior"), 0, 2);
        grid.add(interiorField, 1, 2);
        grid.add(new Label("Código Postal"), 0, 3);
        grid.add(codigoPostalField, 1, 3);
        grid.add(new Label("Colonia"), 0, 4);
        grid.add(coloniaField, 1, 4);
        grid.add(new Label("Delegación"), 0, 5);
        grid.add(delegacionBox, 1, 5);
        grid.add(new Label("Estado"), 0, 6);
        grid.add(estadosBox, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);
        final Button btnOk = (Button) dialog.getDialogPane().lookupButton(guardar);
        BooleanBinding predicate = calleField.textProperty().isEmpty().or(exteriorField.textProperty().isEmpty());
        btnOk.disableProperty().bind(predicate);
//        btnOk.disableProperty().bind(Bindings.or(calleField.textProperty().isEmpty(), exteriorField.textProperty().isEmpty()));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                Domicilio nuevo = new Domicilio();
                //Creear nuevo domicilio y
                nuevo.setCalle(calleField.getText());
                nuevo.setExterior(exteriorField.getText());
                nuevo.setInterior(interiorField.getText());
                nuevo.setCodigopostal(codigoPostalField.getText());
                nuevo.setColonia(coloniaField.getText());
                nuevo.setDelegacion(new Delegacion(delegacionBox.getValue().toString()));
                nuevo.setEstado(new Estado(estadosBox.getValue().toString()));
                return nuevo;
            }
            return null;
        });

        return dialog;
    }

    @FXML
    private void editarNota(ActionEvent event) {
        Optional<String> nuevaNota = createEditNotaDialog(asegurado).showAndWait();
        String oldNota = asegurado.getNota();
        nuevaNota.ifPresent((present) -> {
            try {
                asegurado.setNota(present);
                Asegurado unproxy = Utilities.initializeAndUnproxy(asegurado);
                MainApp.getInstance().getBaseDeDatos().edit(unproxy);
                notaTextArea.setText(present);
            } catch (Exception e) {
                showAlert(e, "Error al editar nota");
                asegurado.setNota(oldNota);
                notaTextArea.setText(oldNota);
            }

            //TODO: persistir nota
        });
    }

    private Dialog<String> createEditNotaDialog(Asegurado asegurado) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Editar nota");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

        //create labels and fields
        VBox grid = new VBox();
        grid.setSpacing(10);

        TextArea notaArea = new TextArea(asegurado.getNota());

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
    private void nuevaPolizaScene(ActionEvent event) {
        try {
            //TODO: guardar poliza en base de datos
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/NuevaPoliza.fxml"), null, new JavaFXBuilderFactory());
            Parent parent = loader.load();
            NuevaPolizaController controller = loader.<NuevaPolizaController>getController();
            controller.setContratante(this.asegurado);
            MainApp.getInstance().changeSceneContent(this, location, parent, loader);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAlert(Exception ex, String header) {
        Alert alert = Utilities.makeAlert(ex, header);
        alert.showAndWait();
    }

    @Override
    public void setData(Object obj) {
        setAsegurado((Asegurado) obj);
    }

    @Override
    public Object getData() {
        return this.asegurado;
    }

}
