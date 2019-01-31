/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.ui.observable.ObservableAsegurado;
import com.embuckets.controlcartera.ui.observable.ObservablePoliza;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class HomeController implements Initializable {

    //TreeTableView 
    @FXML
    private TreeTableView treeAsegurados;
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
    private TableView tableViewRenovaciones;
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
    private TableView tableViewRecibos;
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
    private TableView tableViewCumple;
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
//        fillTablaRecibos();
//        fillTablaCumple();
        fillTablaAsegurados();
    }

    private void fillTablaAsegurados() {
        treeAsegurados.setRoot(createTree());
        //agregar listeners
        treeAsegurados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //Agregar listener para doble click que lleve a la pagina del asegurado o poliza
            System.out.println("oldValue: " + oldValue);//item previamente seleccionado
            System.out.println("newValue: " + newValue);//item actualmente seleccionado ObservableAsegurado | ObservablePoliza
            System.out.println("observable: " + observable);//item actualmente seleccionado ObservableAsegurado | ObservablePoliza
            System.out.println();
        });
//        treeAsegurados.getSelectionModel().selectedItemProperty().addListener();
    }

    private TreeItem createTree() {
        List<ObservableAsegurado> observableAsegurados = createObservableAsegurados(getAsegurados());
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
            asegurado.getObservablePolizas().stream().forEach((poliza) -> {
                aseguradoItem.getChildren().add(new TreeItem(poliza));
            });
        });
        return root;

    }

    private List<ObservableAsegurado> createObservableAsegurados(List<Asegurado> list) {
        List<ObservableAsegurado> obsList = new ArrayList<>();
        for (Asegurado ase : list) {
            ObservableAsegurado obsAsegurdo = new ObservableAsegurado(ase);
            for (Poliza pol : ase.getPolizaList()) {
                ObservablePoliza obsPoliza = new ObservablePoliza(pol);
                obsAsegurdo.addObservablePoliza(obsPoliza);
            }
            obsList.add(obsAsegurdo);
        }
        return obsList;
    }

    /**
     * lee todos los asegurados de la base
     *
     * @return lista de todos los asegurados
     */
    private List<Asegurado> getAsegurados() {
        //TODO: pedir al sistema ControlCartera todos los asegurados
        return createAseguradosFalsos();
    }

    private List<Asegurado> createAseguradosFalsos() {
        Asegurado asegurado1 = new Asegurado("emilio", "hernandez", "segovia");
        Asegurado asegurado2 = new Asegurado("daniel", "hernandez", "segovia");
        asegurado1.getCliente().setNacimiento(Date.valueOf(LocalDate.of(1993, Month.MAY, 22)));
        asegurado2.getCliente().setNacimiento(Date.valueOf(LocalDate.of(1994, Month.SEPTEMBER, 23)));

        Poliza poliza1 = new Poliza();
        poliza1.setNumero("numeor1");
        poliza1.setAseguradora(new Aseguradora("GNP"));
        poliza1.setRamo(new Ramo("vida"));
        poliza1.setProducto("producto");
        poliza1.setPlan("plan");
        poliza1.setPrima(new BigDecimal(21456));
        poliza1.setPrimamoneda(new Moneda("pesos"));
        poliza1.setIniciovigencia(java.util.Date.from(Instant.now()));
        poliza1.setFinvigencia(java.util.Date.from(Instant.now().plus(Duration.ofDays(365))));

        Poliza poliza2 = new Poliza();
        poliza2.setNumero("numeor2");
        poliza2.setAseguradora(new Aseguradora("GNP"));
        poliza2.setRamo(new Ramo("vida"));
        poliza2.setProducto("producto");
        poliza2.setPlan("plan");
        poliza2.setPrima(new BigDecimal(54789));
        poliza2.setIniciovigencia(java.util.Date.from(Instant.now()));
        poliza2.setFinvigencia(java.util.Date.from(Instant.now().plus(Duration.ofDays(365))));
        asegurado1.getPolizaList().add(poliza1);
        asegurado1.getPolizaList().add(poliza2);

        Poliza poliza3 = new Poliza();
        poliza3.setNumero("numeor3");
        poliza3.setAseguradora(new Aseguradora("PLAN SEGURO"));
        poliza3.setRamo(new Ramo("GASTOS MEDICOS"));
        poliza3.setProducto("producto");
        poliza3.setPlan("plan");
        poliza3.setPrima(new BigDecimal(12456));
        poliza3.setIniciovigencia(java.util.Date.from(Instant.now()));
        poliza3.setFinvigencia(java.util.Date.from(Instant.now().plus(Duration.ofDays(365))));
        asegurado2.getPolizaList().add(poliza3);
        List<Asegurado> list = new ArrayList<>();
        list.add(asegurado1);
        list.add(asegurado2);
        return list;
    }

    private ObservableList<ObservableAsegurado> initializeAsegurados() {
        Asegurado asegurado1 = new Asegurado("emilio", "hernandez", "segovia");
        Asegurado asegurado2 = new Asegurado("daniel", "hernandez", "segovia");
        asegurado1.getCliente().setNacimiento(Date.valueOf(LocalDate.of(1993, Month.MAY, 22)));
        asegurado2.getCliente().setNacimiento(Date.valueOf(LocalDate.of(1994, Month.SEPTEMBER, 23)));
        List<ObservableAsegurado> list = new ArrayList<>();

        list.add(new ObservableAsegurado(asegurado1));
        list.add(new ObservableAsegurado(asegurado2));

        return FXCollections.observableArrayList(list);
    }

    private void fillTablaRenovaciones() {
        tableViewRenovaciones.setItems(createRenovaciones());
        cumpleNombreTableColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cumpleNacimientoTableColumn.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));

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
        Main.getInstance().changeSceneContent("NuevoAsegurado.fxml");
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
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
    }

    private void fillTablaCumple() {
        tableViewCumple.setItems(initializeAsegurados());
    }

    private void fillTablaRecibos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ObservableList createRenovaciones() {
        return createObservableRenovacionesList(getRenovaciones());
    }

    private ObservableList createObservableRenovacionesList(List<Poliza> renovaciones) {
        List<ObservablePoliza> result = new ArrayList<>();
        for (Poliza poliza : renovaciones) {
            ObservablePoliza observablePoliza = new ObservablePoliza(poliza);
            result.add(observablePoliza);
        }
        return FXCollections.observableArrayList(result);
    }

    private List<Poliza> getRenovaciones() {
        //TODO: get renovaciones de la base de datos
        return createRenovacionesFalsas();
    }

    private List<Poliza> createRenovacionesFalsas() {
        List<Poliza> result = new ArrayList<>();
        List<Asegurado> asegurados = getAsegurados();
        for (Asegurado asegurado : asegurados) {
            List<Poliza> polizas = asegurado.getPolizaList();
            polizas.stream().forEach((p) -> result.add(p));
        }
        return result;
    }

}
