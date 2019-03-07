/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Delegacion;
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TipoEmail;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.TipoTelefono;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
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
        nacimientoTextField.setText((asegurado.getCliente().getNacimiento() != null) ? asegurado.getCliente().getNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString() : "");
        tipoPersonaTextField.setText(asegurado.getTipopersona().getTipopersona());
        rfcTextField.setText(asegurado.getRfc());
    }

    private void llenarDomicilio() {
        if (asegurado.getIddomicilio() == null) {
            asegurado.setIddomicilio(new Domicilio());
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
                removeItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        Telefono tel = row.getItem();
//                        Telefono tel = new Telefono(new TelefonoPK(item.getIdCliente(), item.telefonoProperty().get()));
                        asegurado.getTelefonoList().remove(tel);
                        telefonoTableView.getItems().remove(row.getItem());
                        //TODO: borrar telefono de la base de datos
                    }
                });
                MenuItem editItem = new MenuItem("Editar");
                editItem.setOnAction((ActionEvent event) -> {
                    Optional<Telefono> result = createEditTelefonoDialog(row.getItem()).showAndWait();
                    result.ifPresent(telefono -> {
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
        String[] tipoTelefono = {"Casa", "Movil", "Trabajo"};
        ObservableList<String> list = FXCollections.observableArrayList(tipoTelefono);
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
                telefono.getTelefonoPK().setTelefono(telefonoField.getText());
                telefono.setExtension(extensionField.getText());
                telefono.getTipotelefono().setTipotelefono(tipoTelefonoBox.getValue().toString());
                return telefono;
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
                asegurado.getEmailList().remove(row.getItem());
                emailTableView.getItems().remove(row.getItem());
                //TODO: borrar email de la base de datos
            });
            MenuItem editItem = new MenuItem("Editar");
            editItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Optional<Email> result = createEditEmailDialog(row.getItem()).showAndWait();
                    result.ifPresent(email -> {
                        emailTableView.getItems().clear();
                        emailTableView.setItems(FXCollections.observableArrayList(asegurado.getEmailList()));
                    });
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
        });
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
        String[] tipoEmail = {"Personal", "Trabajo"};
        ObservableList<String> list = FXCollections.observableArrayList(tipoEmail);
        ComboBox tipoEmailBox = new ComboBox(list);
        tipoEmailBox.getSelectionModel().select(list.indexOf(email.tipoProperty().get()));

        grid.add(new Label("Email"), 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(new Label("Tipo de email"), 0, 1);
        grid.add(tipoEmailBox, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                email.getEmailPK().setEmail(emailField.getText());
                email.getTipoemail().setTipoemail(tipoEmailBox.getValue().toString());
                return email;
            }
            return null;
        });

        return dialog;
    }

    private void fillTipoEmailComboBox() {
        //pedir los estados a la base de datos
        String[] values = {"Personal", "Trabajo"};
        ObservableList<String> list = FXCollections.observableArrayList(values);
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
        ObservableList<String> list = FXCollections.observableArrayList(tipoDocumentoAsegurado());
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

    private String[] tipoDocumentoAsegurado() {
        return new String[]{Globals.DOCUMENTO_ASEGURADO_TIPO_DOMICILIO, Globals.DOCUMENTO_ASEGURADO_TIPO_IDENTIFICACION,
            Globals.DOCUMENTO_ASEGURADO_TIPO_RFC, Globals.DOCUMENTO_ASEGURADO_TIPO_DOMICILIO};
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
        telefonoTableView.getItems().add(telefono);
        //
        asegurado.agregarTelefono(telefono);
        telefonoTextField.setText("");
        extensionTextField.setText("");
        //TODO: persist telefono
    }

    @FXML
    private void agregarEmail(ActionEvent event) {
        Email email = new Email(asegurado.getIdcliente(), emailTextField.getText());
        email.setTipoemail(new TipoEmail(tipoEmailComboBox.getValue().toString()));
        emailTableView.getItems().add(email);
        asegurado.agregarEmail(email);
        emailTextField.setText("");
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
        //TODO: update asegurado
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
        DatePicker nacimientoPicker = new DatePicker(asegurado.getCliente().getNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        RadioButton personaFisicaRadioButton = new RadioButton("Física");
        RadioButton personaMoralRadioButton = new RadioButton("Moral");
        ToggleGroup group = new ToggleGroup();
        personaFisicaRadioButton.setToggleGroup(group);
        personaMoralRadioButton.setToggleGroup(group);
        if (asegurado.getTipopersona().getTipopersona().equalsIgnoreCase("Moral")) {
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
                asegurado.setTipopersona(new TipoPersona(((RadioButton) group.getSelectedToggle()).getText()));
                asegurado.getCliente().setNombre(nombreField.getText());
                asegurado.getCliente().setApellidopaterno(paternoField.getText());
                asegurado.getCliente().setApellidomaterno(maternoField.getText());
                asegurado.getCliente().setNacimiento(Date.from(nacimientoPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                asegurado.setRfc(rfcField.getText());
                return asegurado;
            }
            return null;
        });
        return dialog;
    }

    @FXML
    private void editarDomicilio(ActionEvent event) {
        Optional<Domicilio> editedDomicilio = createEditDomicilioDialog(asegurado.getIddomicilio()).showAndWait();
        //TODO: update domicilio
        llenarDomicilio();
    }

    private Dialog<Domicilio> createEditDomicilioDialog(Domicilio domicilio) {
//        if (domicilio == null) {
//            domicilio = new Domicilio();
//        }
        Dialog<Domicilio> dialog = new Dialog<>();
        dialog.setTitle("Editar domicilio");
        //set the button types
        ButtonType guardar = new ButtonType("Guardar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardar, ButtonType.CANCEL);

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

        String[] delegaciones = {"Álvaro Obregón", " Azcapotzalco", " Benito Juárez",
            " Coyoacán", " Cuajimalpa de Morelos", " Cuauhtémoc", "Gustavo A. Madero",
            " Iztacalco", " Iztapalapa", " La Magdalena Contreras", "Miguel Hidalgo", " Milpa Alta",
            " Tláhuac", " Tlalpan", " Venustiano Carranza", " Xochimilco"};
        ObservableList<String> obsDelegaciones = FXCollections.observableArrayList(delegaciones);
        ComboBox delegacionBox = new ComboBox(obsDelegaciones);
        delegacionBox.getSelectionModel().select(obsDelegaciones.indexOf(domicilio.getDelegacion().getDelegacion()));

        String[] estados = {"Aguascalientes", " Baja California", " Baja California Sur",
            " Campeche", " Chiapas", " Chihuahua", " Ciudad de México",
            " Coahuila", " Colima", " Durango", " Estado de México", " Guanajuato",
            " Guerrero", " Hidalgo", " Jalisco", " Michoacán", " Morelos",
            " Nayarit", " Nuevo León", " Oaxaca", " Puebla", " Querétaro",
            " Quintana Roo", " San Luis Potosí", " Sinaloa", " Sonora",
            " Tabasco", " Tamaulipas", " Tlaxcala", " Veracruz", " Yucatán", " Zacatecas"};
        ObservableList<String> obsEstados = FXCollections.observableArrayList(estados);
        ComboBox EstadosBox = new ComboBox(FXCollections.observableArrayList(obsEstados));
        EstadosBox.getSelectionModel().select(obsEstados.indexOf(domicilio.getEstado().getEstado()));

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
        grid.add(EstadosBox, 1, 6);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardar) {
                domicilio.setCalle(calleField.getText());
                domicilio.setExterior(exteriorField.getText());
                domicilio.setInterior(interiorField.getText());
                domicilio.setCodigopostal(codigoPostalField.getText());
                domicilio.setColonia(coloniaField.getText());
                domicilio.setDelegacion(new Delegacion(delegacionBox.getValue().toString()));
                domicilio.setEstado(new Estado(EstadosBox.getValue().toString()));
                return domicilio;
            }
            return null;
        });

        return dialog;
    }

    @FXML
    private void editarNota(ActionEvent event) {
        Optional<String> nuevaNota = createEditNotaDialog(asegurado).showAndWait();
        nuevaNota.ifPresent((present) -> {
            notaTextArea.setText(present);
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
                asegurado.setNota(notaArea.getText());
                return asegurado.getNota();
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

    @Override
    public void setData(Object obj) {
        setAsegurado((Asegurado) obj);
    }

    @Override
    public Object getData() {
        return this.asegurado;
    }

}
