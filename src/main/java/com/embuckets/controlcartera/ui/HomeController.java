/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.Auto;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.ConductoCobro;
import com.embuckets.controlcartera.entidades.EstadoPoliza;
import com.embuckets.controlcartera.entidades.FormaPago;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.entidades.Recibo;
import com.embuckets.controlcartera.entidades.SumaAseguradaAuto;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.ui.observable.ObservableAsegurado;
import com.embuckets.controlcartera.ui.observable.ObservableCliente;
import com.embuckets.controlcartera.ui.observable.ObservableNotificacionRecibo;
import com.embuckets.controlcartera.ui.observable.ObservablePoliza;
import com.embuckets.controlcartera.ui.observable.ObservableRenovacion;
import com.embuckets.controlcartera.ui.observable.ObservableTreeItem;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
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
public class HomeController implements Initializable {

    private String location = "/fxml/Home.fxml";
    //TreeTableView 
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
    private TableView<ObservableCliente> tableViewCumple;
    @FXML
    private TableColumn cumpleNombreTableColumn;
    @FXML
    private TableColumn cumpleNacimientoTableColumn;

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
        //agregar listeners
//        treeAsegurados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            //Agregar listener para doble click que lleve a la pagina del asegurado o poliza
//            System.out.println("oldValue: " + oldValue);//item previamente seleccionado
//            System.out.println("newValue: " + newValue);//item actualmente seleccionado ObservableAsegurado | ObservablePoliza
//            System.out.println("observable: " + observable);//item actualmente seleccionado ObservableAsegurado | ObservablePoliza
//            System.out.println();
//        });

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
//            controller.setAseguradoId(id);
//        loader.setController(controller);
                            MainApp.getInstance().changeSceneContent(location, parent, loader);
//mandar el id y que el controlador de AsegurdoHome lo tome de la base
                        } catch (IOException ex) {
                            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (obs instanceof Poliza) {
                        try {
                            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/PolizaHome.fxml"), null, new JavaFXBuilderFactory());
                            Parent parent = loader.load();
                            PolizaHomeController controller = loader.<PolizaHomeController>getController();
                            controller.setPoliza((Poliza) obs);
//            controller.setAseguradoId(id);
//        loader.setController(controller);
                            MainApp.getInstance().changeSceneContent(location, parent, loader);
//mandar el id y que el controlador de AsegurdoHome lo tome de la base
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

//        treeAsegurados.getSelectionModel().selectedItemProperty().addListener();
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

//    private List<ObservableTreeItem> createObservableAsegurados(List<Asegurado> list) {
//        List<ObservableAsegurado> obsList = new ArrayList<>();
//        for (Asegurado ase : list) {
//            ObservableAsegurado obsAsegurdo = new ObservableAsegurado(ase);
//            for (Poliza pol : ase.getPolizaList()) {
//                ObservablePoliza obsPoliza = new ObservablePoliza(pol);
//                obsAsegurdo.addObservablePoliza(obsPoliza);
//            }
//            obsList.add(obsAsegurdo);
//        }
//        return obsList;
//    }
    /**
     * lee todos los asegurados de la base
     *
     * @return lista de todos los asegurados
     */
    private List<? extends ObservableTreeItem> getAsegurados() {
        //TODO: pedir al sistema ControlCartera todos los asegurados
        return createAseguradosFalsos();
    }

    private List<Asegurado> createAseguradosFalsos() {
        Asegurado asegurado1 = new Asegurado("emilio", "hernandez", "segovia");
        asegurado1.setIdcliente(1);
        asegurado1.getCliente().setIdcliente(1);
        asegurado1.getCliente().setNacimiento(Date.from(Instant.parse("1993-05-22T00:00:01.00Z")));
        asegurado1.setTipopersona(new TipoPersona("Fisica"));
        Asegurado asegurado2 = new Asegurado("daniel", "hernandez", "segovia");
        asegurado2.getCliente().setNacimiento(Date.from(Instant.parse("1994-09-23T00:00:01.00Z")));
        asegurado2.setIdcliente(2);
        asegurado2.getCliente().setIdcliente(2);
        asegurado2.setTipopersona(new TipoPersona("Fisica"));

        Poliza poliza1 = new Poliza();
        poliza1.setIdpoliza(1);
        poliza1.setNumero("numeor1");
        poliza1.setAseguradora(new Aseguradora("GNP"));
        poliza1.setRamo(new Ramo("vida"));
        poliza1.setProducto("producto");
        poliza1.setPlan("plan");
        poliza1.setPrima(new BigDecimal(21456));
        poliza1.setPrimamoneda(new Moneda("pesos"));
        poliza1.setIniciovigencia(java.util.Date.from(Instant.now().minus(Duration.ofDays(15))));
        poliza1.setFinvigencia(java.util.Date.from(Instant.now().plus(Duration.ofDays(365))));
        poliza1.setEstado(new EstadoPoliza("Vigente"));
        poliza1.setConductocobro(new ConductoCobro("agente"));
        poliza1.setFormapago(new FormaPago("mensual"));
        Cliente benef = new Cliente("beneficiario1", "hijo", "hijo");
        benef.setNacimiento(LocalDate.of(2016, Month.JANUARY, 9));
        poliza1.setPolizaVida(new PolizaVida(1));
        poliza1.getPolizaVida().setSumaasegurada(BigDecimal.valueOf(50000));
        poliza1.getPolizaVida().setSumaaseguradamoneda(new Moneda("Dolares"));
        poliza1.getPolizaVida().getClienteList().add(benef);
        poliza1.generarRecibos(3, new BigDecimal(10123.12), new BigDecimal(9123.12));

        Poliza poliza2 = new Poliza();
        poliza2.setIdpoliza(2);
        poliza2.setNumero("numeor2");
        poliza2.setAseguradora(new Aseguradora("GNP"));
        poliza2.setRamo(new Ramo("autos"));
        poliza2.setProducto("producto");
        poliza2.setPlan("plan");
        poliza2.setPrima(new BigDecimal(54789));
        poliza2.setPrimamoneda(new Moneda("pesos"));
        poliza2.setIniciovigencia(java.util.Date.from(Instant.now().minus(Duration.ofDays(20))));
        poliza2.setFinvigencia(java.util.Date.from(Instant.now().plus(Duration.ofDays(365))));
        poliza2.setEstado(new EstadoPoliza("Cancelada"));
        poliza2.setPolizaAuto(new PolizaAuto(2));
        poliza2.getPolizaAuto().setSumaaseguradaauto(new SumaAseguradaAuto("Factura"));
        poliza2.getPolizaAuto().getAutoList().add(new Auto(2, "STD 4PT RL", "VW", "Jetta", Year.of(2016)));
        poliza2.setConductocobro(new ConductoCobro("agente"));
        poliza2.setFormapago(new FormaPago("mensual"));
        poliza2.generarRecibos(4, new BigDecimal(10123.12), new BigDecimal(9123.12));

        poliza1.setContratante(asegurado1);
        poliza1.setTitular(asegurado1.getCliente());
        poliza2.setContratante(asegurado1);
        poliza2.setTitular(asegurado1.getCliente());
        asegurado1.getPolizaList().add(poliza1);
        asegurado1.getPolizaList().add(poliza2);

        Poliza poliza3 = new Poliza();
        poliza3.setIdpoliza(3);
        poliza3.setNumero("numeor3");
        poliza3.setAseguradora(new Aseguradora("PLAN SEGURO"));
        poliza3.setRamo(new Ramo("gastos medicos"));
        poliza3.setProducto("producto");
        poliza3.setPlan("plan");
        poliza3.setPrima(new BigDecimal(12456));
        poliza3.setPrimamoneda(new Moneda("PESOS"));
        poliza3.setIniciovigencia(java.util.Date.from(Instant.now().minus(Duration.ofDays(5))));
        poliza3.setFinvigencia(java.util.Date.from(Instant.now().plus(Duration.ofDays(365))));
        poliza3.setEstado(new EstadoPoliza("No vigente"));
        poliza3.setConductocobro(new ConductoCobro("agente"));
        poliza3.setFormapago(new FormaPago(Globals.FORMA_PAGO_TRIMESTRAL));
        poliza3.setPolizaGmm(new PolizaGmm(3, BigDecimal.valueOf(789654.12), "100,000,000", (short) 10));
        poliza3.getPolizaGmm().setDeduciblemoneda(new Moneda("PESOS"));
        poliza3.getPolizaGmm().setSumaaseguradamondeda(new Moneda("PESOS"));
        Cliente depend = new Cliente("beneficiario1", "hijo", "hijo");
        depend.setNacimiento(LocalDate.of(2016, Month.JANUARY, 9));
        poliza3.getPolizaGmm().getClienteList().add(depend);
        poliza3.generarRecibos(2, new BigDecimal(10123.12), new BigDecimal(9123.12));

        poliza3.setContratante(asegurado2);
        poliza3.setTitular(asegurado2.getCliente());
        asegurado2.getPolizaList().add(poliza3);

        List<Asegurado> list = new ArrayList<>();
        list.add(asegurado1);
        list.add(asegurado2);
        return list;
    }

    private ObservableList<ObservableCliente> createCumple() {
        return FXCollections.observableArrayList(getClientes());
//        return createObservableClientes(getClientes());
    }

//    private ObservableList<ObservableCliente> createObservableClientes(List<Cliente> clientes) {
//        List<ObservableCliente> observableClientes = new ArrayList<>();
//        clientes.stream().map((cliente) -> new ObservableCliente(cliente)).forEachOrdered((obvs) -> {
//            observableClientes.add(obvs);
//        });
//        return FXCollections.observableArrayList(observableClientes);
//    }
    private List<? extends Cliente> getClientes() {
        //TODO: pedir clientes que cumplan
        return createClientesFalsos();
    }

    private List<Cliente> createClientesFalsos() {
        List<Asegurado> asegurados = createAseguradosFalsos();
        List<Cliente> clientes = new ArrayList<>();
        asegurados.forEach((asegurado) -> {
            clientes.add(asegurado.getCliente());
        });
        return clientes;
    }

    private void fillTablaRenovaciones() {
        tableViewRenovaciones.setItems(createRenovaciones());

        renovacionesAseguradoTableColumn.setCellValueFactory(new PropertyValueFactory("asegurado"));
        renovacionesPolizaTableColumn.setCellValueFactory(new PropertyValueFactory("numero"));
        renovacionesFinVigenciaTableColumn.setCellValueFactory(new PropertyValueFactory("finVigencia"));
        renovacionesFaltanTableColumn.setCellValueFactory(new PropertyValueFactory("faltan"));

//        cumpleNombreTableColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
//        cumpleNacimientoTableColumn.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));
//        colNotificar.setCellValueFactory(new Callback<CellDataFeatures<ObservableAsegurado, CheckBox>, ObservableValue<CheckBox>>() {
//            //This callback tell the cell how to bind the data model 'Registered' property to
//            //the cell, itself.
//            @Override
//            public ObservableValue<CheckBox> call(CellDataFeatures<ObservableAsegurado, CheckBox> param) {
//                ObservableAsegurado asegurado = param.getValue();
//                CheckBox checkbox = new CheckBox();
//                checkbox.selectedProperty().setValue(asegurado.isNotificado());
//                checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                        asegurado.setNotificado(newValue);
////                        System.out.println(asegurado.nombreProperty + ", " + asegurado.notificarProperty);
//                    }
//                });
//
//                checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                        asegurado.setNotificado(checkNotificarCumple.isSelected());
////                        checkbox.setSelected(asegurado.notificarProperty.getValue());
//                    }
//                });
//
//                return new SimpleObjectProperty<CheckBox>(checkbox);
////                return param.getValue().notificarProperty();
//            }
//        });
//        colNotificar.setCellFactory(CheckBoxTableCell.forTableColumn(colNotificar));
    }

//    @FXML
//    void notificarTodosCumple(ActionEvent event) {
//        CheckBox checkBox = (CheckBox) event.getSource();
//        ObservableList<ObservableAsegurado> asegurados = tableViewCumple.getItems();
//        for (ObservableAsegurado asegurado : asegurados) {
//            asegurado.setNotificado(checkBox.isSelected());
//            colNotificar.getCellData(asegurado).setSelected(checkBox.isSelected());
////            System.out.println(asegurado.nombreProperty + ", " + asegurado.notificarProperty);
//
//        }
//
//        tableViewCumple.setItems(asegurados);
//
//    }
    public void abrirSceneNuevoAsegurado(ActionEvent event) throws IOException {
        MainApp.getInstance().changeSceneContent(location, "/fxml/NuevoAsegurado.fxml");
//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("NuevoAsegurado.fxml"));
//            
////            VBox page = (VBox) FXMLLoader.load(getClass().getResource("NuevoAsegurado.fxml"));
//            Scene newScene = new Scene(parent);
//            
//            Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            mainWindow.getScene().getRoot().setEffect(new GaussianBlur());
//            
//            Stage popUpStage = new Stage(StageStyle.DECORATED);
//            popUpStage.initOwner(mainWindow);
//            popUpStage.initModality(Modality.APPLICATION_MODAL);
//            popUpStage.setScene(newScene);
////            popUpStage.setMaximized(true);
//            popUpStage.show();
//            
////            mainWindow.setScene(newScene);
////            mainWindow.setMaximized(true);
////            mainWindow.show();
//            
//        } catch (IOException ex) {
//            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
    }

    private void fillTablaCumple() {
        tableViewCumple.setItems(createCumple());

        cumpleNombreTableColumn.setCellValueFactory(new PropertyValueFactory("nombre"));
        cumpleNacimientoTableColumn.setCellValueFactory(new PropertyValueFactory("nacimiento"));
    }

    private void fillTablaRecibos() {
        tableViewRecibos.setItems(createRecibos());

        recibosAseguradoTableColumn.setCellValueFactory(new PropertyValueFactory("asegurado"));
        recibosPolizaTableColumn.setCellValueFactory(new PropertyValueFactory("poliza"));
        recibosDesdeTableColumn.setCellValueFactory(new PropertyValueFactory("cubreDesde"));
        recibosHastaTableColumn.setCellValueFactory(new PropertyValueFactory("cubreHasta"));
        recibosImporteTableColumn.setCellValueFactory(new PropertyValueFactory("importe"));
        recibosUltimaNotificacionTableColumn.setCellValueFactory(new PropertyValueFactory("enviado"));

    }

    private ObservableList<ObservableRenovacion> createRenovaciones() {
        return FXCollections.observableArrayList(getRenovaciones());
//        return createObservableRenovacionesList();
    }

//    private ObservableList<ObservablePoliza> createObservableRenovacionesList(List<Poliza> renovaciones) {
//        List<ObservablePoliza> result = new ArrayList<>();
//        for (Poliza poliza : renovaciones) {
//            ObservablePoliza observablePoliza = new ObservablePoliza(poliza);
//            result.add(observablePoliza);
//        }
//        return FXCollections.observableArrayList(result);
//    }
    private List<? extends ObservableRenovacion> getRenovaciones() {
        //TODO: get renovaciones de la base de datos
        return createRenovacionesFalsas();
    }

    private List<Poliza> createRenovacionesFalsas() {
        List<Poliza> result = new ArrayList<>();
        List<Asegurado> asegurados = (List<Asegurado>) getAsegurados();
        for (Asegurado asegurado : asegurados) {
            List<Poliza> polizas = asegurado.getPolizaList();
            polizas.stream().forEach((p) -> result.add(p));
        }
        return result;
    }

    private ObservableList<ObservableNotificacionRecibo> createRecibos() {
        return FXCollections.observableArrayList(getNotificacionesRecibos());
    }

    private List<? extends NotificacionRecibo> getNotificacionesRecibos() {
        //TODO: getNotifacaciones de la base de dato
        return createNotificacionesRecibosFalsos();
    }

//    private ObservableList<ObservableNotificacionRecibo> createObservableNotificacionesRecibo(List<NotificacionRecibo> notificacionesRecibos) {
//        List<ObservableNotificacionRecibo> result = new ArrayList<>();
//        notificacionesRecibos.stream().map((notificacionRecibo) -> new ObservableNotificacionRecibo(notificacionRecibo)).forEachOrdered((obv) -> {
//            result.add(obv);
//        });
//        return FXCollections.observableArrayList(result);
//    }
    private List<NotificacionRecibo> createNotificacionesRecibosFalsos() {
        NotificacionRecibo notificacion1 = new NotificacionRecibo();
        notificacion1.setEnviado(Date.from(Instant.now().minus(Duration.ofHours(2))));
        notificacion1.setIdrecibo(1);
        notificacion1.setRecibo(new Recibo(1, Date.from(Instant.parse("2018-12-01T00:00:01.00Z")), Date.from(Instant.parse("2018-12-31T00:00:01.00Z")), BigDecimal.valueOf(12548.45)));
        notificacion1.getRecibo().setIdpoliza(new Poliza());
        notificacion1.getRecibo().getIdpoliza().setNumero("numero1");
        notificacion1.getRecibo().getIdpoliza().setContratante(new Asegurado("Emilio", "Hernandez", "Segovia"));

        NotificacionRecibo notificacion2 = new NotificacionRecibo();
        notificacion2.setEnviado(Date.from(Instant.now().minus(Duration.ofHours(26))));
        notificacion2.setIdrecibo(2);
        notificacion2.setRecibo(new Recibo(2, Date.from(Instant.parse("2019-01-01T00:00:01.00Z")), Date.from(Instant.parse("2019-01-31T00:00:01.00Z")), BigDecimal.valueOf(12548.45)));
        notificacion2.getRecibo().setIdpoliza(new Poliza());
        notificacion2.getRecibo().getIdpoliza().setNumero("numero1");
        notificacion2.getRecibo().getIdpoliza().setContratante(new Asegurado("Daniel", "Hernandez", "Segovia"));

        List<NotificacionRecibo> result = new ArrayList<>();
        result.add(notificacion1);
        result.add(notificacion2);
        return result;

    }

}
