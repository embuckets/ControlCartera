/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Agente;
import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Utilities;
import com.embuckets.controlcartera.excel.ExcelImporter;
import com.embuckets.controlcartera.mail.MailService;
import com.embuckets.controlcartera.ui.observable.ObservableNotificacionRecibo;
import com.embuckets.controlcartera.ui.observable.ObservableRenovacion;
import com.embuckets.controlcartera.ui.observable.ObservableTreeItem;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class HomeController implements Initializable, Controller {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    private String location = "/fxml/Home.fxml";
    @FXML
    private TreeTableView<ObservableTreeItem> treeAsegurados;
    @FXML
    private TreeTableColumn nombreTreeTableColumn;
    @FXML
    private TreeTableColumn numeroPolizaTreeTableColumn;
    @FXML
    private TreeTableColumn aseguradoraTreeTableColumn;
    @FXML
    private TreeTableColumn ramoTreeTableColumn;
    @FXML
    private TreeTableColumn productoTreeTableColumn;
    @FXML
    private TreeTableColumn planTreeTableColumn;
    @FXML
    private TreeTableColumn primaTreeTableColumn;

    //Renovaciones TableView
    @FXML
    private TableView<ObservableRenovacion> tableViewRenovaciones;
    @FXML
    private TableColumn renovacionesAseguradoTableColumn;
    @FXML
    private TableColumn renovacionesPolizaTableColumn;
    @FXML
    private TableColumn renovacionesFinVigenciaTableColumn;
    @FXML
    private TableColumn renovacionesFaltanTableColumn;

    //Recibos TableView
    @FXML
    private TableView<ObservableNotificacionRecibo> tableViewRecibos;
    @FXML
    private TableColumn recibosAseguradoTableColumn;
    @FXML
    private TableColumn recibosPolizaTableColumn;
    @FXML
    private TableColumn recibosDesdeTableColumn;
    @FXML
    private TableColumn recibosHastaTableColumn;
    @FXML
    private TableColumn recibosImporteTableColumn;
    @FXML
    private TableColumn recibosUltimaNotificacionTableColumn;

    //TableView Cumple
    @FXML
    private TableView<NotificacionCumple> tableViewCumple;
    @FXML
    private TableColumn cumpleNombreTableColumn;
    @FXML
    private TableColumn cumpleNacimientoTableColumn;
    @FXML
    private TableColumn cumpleFaltanTableColumn;
    @FXML
    private TableColumn cumpleEstadoTableColumn;

    //Botones
    @FXML
    private Button nuevoAseguradoButton;
    @FXML
    private Button nuevaPolizaButton;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField paternoField;
    @FXML
    private TextField maternoField;
    @FXML
    private TextField numeroPolizaField;
    @FXML
    private ComboBox<String> aseguradoraCombo;
    @FXML
    private ComboBox<String> ramoCombo;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTablaRenovaciones();
        fillTablaRecibos();
        fillTablaCumple();
        fillTablaAsegurados();
        llenarComboFiltros();
    }

    private void fillTablaAsegurados() {
        treeAsegurados.setRoot(createTree());

        treeAsegurados.setRowFactory(table -> {
            TreeTableRow<ObservableTreeItem> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ObservableTreeItem obs = row.getItem();
                    if (obs instanceof Asegurado) {
                        try {
                            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/AseguradoHome.fxml"), null, new JavaFXBuilderFactory());
                            Parent parent = loader.load();
                            AseguradoHomeController controller = loader.<AseguradoHomeController>getController();
                            controller.setAsegurado((Asegurado) obs);
                            MainApp.getInstance().changeSceneContent(this, location, parent, loader);
                        } catch (IOException ex) {
                            logger.fatal("Error al cargar fxml", ex);
                            showAlert(ex, "Error al cargar fxml");
                        }
                    } else if (obs instanceof Poliza) {
                        try {
                            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/PolizaHome.fxml"), null, new JavaFXBuilderFactory());
                            Parent parent = loader.load();
                            PolizaHomeController controller = loader.<PolizaHomeController>getController();
                            controller.setPoliza((Poliza) obs);
                            MainApp.getInstance().changeSceneContent(this, location, parent, loader);
                        } catch (IOException ex) {
                            logger.fatal("Error al cargar fxml", ex);
                            showAlert(ex, "Error al cargar fxml");
                        }
                    }
                }
            });
            row.setOnKeyPressed(event -> {
                //TODO: no funciona
                if (event.getCode() == (KeyCode.ENTER)) {
                    ObservableTreeItem obs = row.getItem();
                    System.out.println(obs.getId());
                }
            });
            return row;
        });

    }

    private TreeItem createTree() {
        List<ObservableTreeItem> observableAsegurados = (List<ObservableTreeItem>) getAsegurados();
        //make root item
        TreeItem root = new TreeItem(new Asegurado("Control Cartera", "", ""));
        root.setExpanded(true);
        //make columns
        nombreTreeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory("nombre"));
        numeroPolizaTreeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory("numero"));
        aseguradoraTreeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory("aseguradora"));
        ramoTreeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory("ramo"));
        productoTreeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory("producto"));
        planTreeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory("plan"));
        primaTreeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory("prima"));

        //agrega los asegurados a root
        observableAsegurados.stream().forEach((asegurado) -> {
            TreeItem aseguradoItem = new TreeItem(asegurado);
            root.getChildren().add(aseguradoItem);
            asegurado.getPolizaListProperty().stream().forEach((poliza) -> {
                aseguradoItem.getChildren().add(new TreeItem(poliza));
            });
        });
        return root;

    }

    /**
     * lee todos los asegurados de la base
     *
     * @return lista de todos los asegurados
     */
    private List<? extends ObservableTreeItem> getAsegurados() {
        //TODO: pedir al sistema ControlCartera todos los asegurados
        return MainApp.getInstance().getBaseDeDatos().getAll(Asegurado.class);
    }

    private ObservableList<NotificacionCumple> getCumplesProximos() {
        return FXCollections.observableArrayList(getNotificacionesCumple());
    }

    private List<NotificacionCumple> getNotificacionesCumple() {
        return MainApp.getInstance().getBaseDeDatos().getCumplesEntre(Globals.CUMPLES_ENTRE_START_DEFAULT, Globals.CUMPLES_ENTRE_END_DEFAULT);
    }

    private void fillTablaRenovaciones() {
        tableViewRenovaciones.setItems(getRenovacionesProximas());

        renovacionesAseguradoTableColumn.setCellValueFactory(new PropertyValueFactory("asegurado"));
        renovacionesPolizaTableColumn.setCellValueFactory(new PropertyValueFactory("numero"));
        renovacionesFinVigenciaTableColumn.setCellValueFactory(new PropertyValueFactory("finVigencia"));
        renovacionesFaltanTableColumn.setCellValueFactory(new PropertyValueFactory("faltan"));
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirSceneNuevoAsegurado(ActionEvent event) throws IOException {
        MainApp.getInstance().changeSceneContent(this, location, "/fxml/NuevoAsegurado.fxml");
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirSceneNuevaPoliza(ActionEvent event) throws IOException {
        MainApp.getInstance().changeSceneContent(this, location, "/fxml/NuevaPoliza.fxml");
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToNotificaciones(ActionEvent event) throws IOException {
        MainApp.getInstance().changeSceneContent(this, location, "/fxml/NotificacionHome.fxml");
    }

    private void fillTablaCumple() {
        tableViewCumple.setItems(getCumplesProximos());

        cumpleNombreTableColumn.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
        cumpleNacimientoTableColumn.setCellValueFactory(new PropertyValueFactory("nacimiento"));
        cumpleFaltanTableColumn.setCellValueFactory(new PropertyValueFactory("faltan"));
        cumpleEstadoTableColumn.setCellValueFactory(new PropertyValueFactory("estado"));
    }

    private void fillTablaRecibos() {
        tableViewRecibos.setItems(getRecibosProximos());

        recibosAseguradoTableColumn.setCellValueFactory(new PropertyValueFactory("asegurado"));
        recibosPolizaTableColumn.setCellValueFactory(new PropertyValueFactory("poliza"));
        recibosDesdeTableColumn.setCellValueFactory(new PropertyValueFactory("cubreDesde"));
        recibosHastaTableColumn.setCellValueFactory(new PropertyValueFactory("cubreHasta"));
        recibosImporteTableColumn.setCellValueFactory(new PropertyValueFactory("importe"));
        recibosUltimaNotificacionTableColumn.setCellValueFactory(new PropertyValueFactory("enviado"));

    }

    /**
     *
     * @param event
     */
    @FXML
    public void editarAgente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Agente.fxml"), null, new JavaFXBuilderFactory());
            Parent parent = loader.load();
            AgenteController controller = loader.getController();
            Optional<Agente> agente = controller.getDialog().showAndWait();
            agente.ifPresent((present) -> {
                present.guardar();
                try {
                    MailService.getInstance().refresh();
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Datos incompletos");
                    alert.setContentText("Email o password guardados son inválidos. No podra enviar notificaciones por correo\n" + ex.getMessage());
                    alert.showAndWait();
                }
            });
        } catch (IOException ex) {
            //TODO
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void importarCartera(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Elige un Documento");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));
        File file = chooser.showOpenDialog(MainApp.getInstance().getStage());
        if (file != null) {
            try {
                List<Asegurado> asegurados = new ExcelImporter().importar(file);
                MainApp.getInstance().getBaseDeDatos().importarAsegurados(asegurados);
                Utilities.makeAlert(AlertType.INFORMATION, "Importar cartera", "Los datos se importaron exitosamente").showAndWait();
                fillTablaAsegurados();
            } catch (Exception e) {
                TextArea textArea = new TextArea(e.getMessage());
                textArea.setEditable(false);
                Dialog dialog = new Dialog();
                dialog.setTitle("Error al importar");
                DialogPane pane = new DialogPane();
                pane.setContent(textArea);
                dialog.setDialogPane(pane);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();
            }
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void exportarPlantilla(ActionEvent event) {
        final DirectoryChooser chooser = new DirectoryChooser();
        final File directory = chooser.showDialog(MainApp.getInstance().getStage());
        if (directory != null) {
            try (InputStream in = new FileInputStream(Globals.TEMPLATE_EXCEL)) {
                File targetFile = new File(directory.getAbsolutePath() + "/plantilla-cartera.xlsx");
                File plantilla = new File(Globals.TEMPLATE_EXCEL);
                byte[] bytes = Files.readAllBytes(plantilla.toPath());
                Files.write(targetFile.toPath(), bytes);
                Desktop.getDesktop().open(targetFile.getParentFile());
            } catch (IOException ex) {
                logger.fatal("Error al exportar excel", ex);
                showAlert(ex, "Error al exportar excel");
            }

        }
    }

    private ObservableList<ObservableRenovacion> getRenovacionesProximas() {
        return FXCollections.observableArrayList(getRenovaciones());
    }

    private List<? extends ObservableRenovacion> getRenovaciones() {
        return MainApp.getInstance().getBaseDeDatos().getRenovacionesEntre(Globals.RENOVACION_ENTRE_START_DEFAULT, Globals.RENOVACION_ENTRE_END_DEFAULT);
    }

    private ObservableList<ObservableNotificacionRecibo> getRecibosProximos() {
        return FXCollections.observableArrayList(getNotificacionesRecibos());
    }

    private List<NotificacionRecibo> getNotificacionesRecibos() {
        return MainApp.getInstance().getBaseDeDatos().getRecibosEntre(Globals.RECIBO_CUBRE_DESDE_INICIO_DEFAULT, Globals.RECIBO_CUBRE_DESDE_FIN_DEFAULT);
    }

    /**
     *
     * @param obj
     */
    @Override
    public void setData(Object obj) {
        //do nothing
    }

    /**
     *
     * @return
     */
    @Override
    public Object getData() {
        return null;
    }

    @FXML
    private void filtrarAsegurados(ActionEvent event) {
        //
        List<ObservableTreeItem> observableAsegurados = (List<ObservableTreeItem>) filtrarAseguradorsAsItems(nombreField.getText(), paternoField.getText(), maternoField.getText());
        //make root item
        TreeItem root = new TreeItem(new Asegurado("Control Cartera", "", ""));
        root.setExpanded(true);

        //agrega los asegurados a root
        observableAsegurados.stream().forEach((asegurado) -> {
            TreeItem aseguradoItem = new TreeItem(asegurado);
            root.getChildren().add(aseguradoItem);
            asegurado.getPolizaListProperty().stream().forEach((poliza) -> {
                aseguradoItem.getChildren().add(new TreeItem(poliza));
            });
        });
//        treeAsegurados.getRoot().getChildren().clear();
        treeAsegurados.setRoot(root);
    }

    private List<? extends ObservableTreeItem> filtrarAseguradorsAsItems(String nombre, String paterno, String materno) {
        return MainApp.getInstance().getBaseDeDatos().buscarAseguradosPorNombre(nombre, paterno, materno);
    }

    @FXML
    private void quitarFiltros(ActionEvent event) {
        //
        List<ObservableTreeItem> observableAsegurados = (List<ObservableTreeItem>) getAsegurados();
        //make root item
        TreeItem root = new TreeItem(new Asegurado("Control Cartera", "", ""));
        root.setExpanded(true);

        //agrega los asegurados a root
        observableAsegurados.stream().forEach((asegurado) -> {
            TreeItem aseguradoItem = new TreeItem(asegurado);
            root.getChildren().add(aseguradoItem);
            asegurado.getPolizaListProperty().stream().forEach((poliza) -> {
                aseguradoItem.getChildren().add(new TreeItem(poliza));
            });
        });
//        treeAsegurados.getRoot().getChildren().clear();
        treeAsegurados.setRoot(root);
        nombreField.setText("");
        paternoField.setText("");
        maternoField.setText("");

        numeroPolizaField.setText("");
        aseguradoraCombo.getSelectionModel().clearSelection();
        ramoCombo.getSelectionModel().clearSelection();
    }

    @FXML
    private void filtrarPolizas(ActionEvent event) {
        List<Poliza> polizas = MainApp.getInstance().getBaseDeDatos().buscarPolizasPor(numeroPolizaField.getText(), aseguradoraCombo.getValue(), ramoCombo.getValue());

        Map<Asegurado, List<Poliza>> mapped = new HashMap<>();
        polizas.forEach((poliza) -> {
            if (mapped.containsKey(poliza.getContratante())) {
                mapped.get(poliza.getContratante()).add(poliza);
            } else {
                mapped.put(poliza.getContratante(), new ArrayList<>());
                mapped.get(poliza.getContratante()).add(poliza);
            }
        });
        TreeItem root = new TreeItem(new Asegurado("Control Cartera", "", ""));
        root.setExpanded(true);

        //agrega los asegurados a root
        mapped.keySet().stream().forEach((k) -> {
            TreeItem aseguradoItem = new TreeItem(k);
            root.getChildren().add(aseguradoItem);
            mapped.get(k).forEach((poliza) -> {
                aseguradoItem.getChildren().add(new TreeItem(poliza));
            });
        });
        treeAsegurados.setRoot(root);
    }

    private List<? extends ObservableTreeItem> filterPolizas(String numeroPoliza, String aseguradora, String ramo) {
        return MainApp.getInstance().getBaseDeDatos().buscarPolizasPor(numeroPoliza, aseguradora, ramo);
    }

    private void llenarComboFiltros() {
        List<Aseguradora> aseguradoras = MainApp.getInstance().getBaseDeDatos().getAll(Aseguradora.class);
        List<String> aseguradorasStrings = aseguradoras.stream().map(a -> a.getAseguradora()).collect(Collectors.toList());
        aseguradoraCombo.setItems(FXCollections.observableArrayList(aseguradorasStrings));

        ramoCombo.setItems(FXCollections.observableArrayList(Globals.getAllRamos()));
    }

    private void showAlert(Exception ex, String header) {
        Utilities.makeAlert(ex, header).showAndWait();
    }

}
