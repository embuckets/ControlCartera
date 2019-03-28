/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.ui.observable.ObservableCliente;
import com.embuckets.controlcartera.ui.observable.ObservableNotificacionRecibo;
import com.embuckets.controlcartera.ui.observable.ObservableRenovacion;
import com.embuckets.controlcartera.ui.observable.ObservableTreeItem;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class HomeController implements Initializable, Controller {

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
    private Button buttonNuevo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTablaRenovaciones();
        fillTablaRecibos();
        fillTablaCumple();
        fillTablaAsegurados();
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
                            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (obs instanceof Poliza) {
                        try {
                            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/PolizaHome.fxml"), null, new JavaFXBuilderFactory());
                            Parent parent = loader.load();
                            PolizaHomeController controller = loader.<PolizaHomeController>getController();
                            controller.setPoliza((Poliza) obs);
                            MainApp.getInstance().changeSceneContent(this, location, parent, loader);
                        } catch (IOException ex) {
                            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void abrirSceneNuevoAsegurado(ActionEvent event) throws IOException {
        MainApp.getInstance().changeSceneContent(this, location, "/fxml/NuevoAsegurado.fxml");
    }

    public void abrirSceneNuevaPoliza(ActionEvent event) throws IOException {
        MainApp.getInstance().changeSceneContent(this, location, "/fxml/NuevaPoliza.fxml");
    }

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

    @Override
    public void setData(Object obj) {
        //do nothing
    }

    @Override
    public Object getData() {
        return null;
    }

}
