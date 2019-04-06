/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import com.embuckets.controlcartera.entidades.globals.Utilities;
import com.embuckets.controlcartera.ui.observable.NotificacionCumpleWrapper;
import com.embuckets.controlcartera.ui.observable.NotificacionReciboWrapper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class NotificacionHomeController implements Initializable {

    private static final Logger logger = LogManager.getLogger(NotificacionHomeController.class);

    private TableView<NotificacionCumpleWrapper> aseguradosTableView;
    private TableColumn<NotificacionCumpleWrapper, Boolean> checkBoxTableColumn;
    private TableColumn nombreTableColumn;

    @FXML
    private TableView<NotificacionReciboWrapper> recibosTableView;
    @FXML
    private TableColumn<NotificacionReciboWrapper, Boolean> recibosCheckBoxColumn;
    @FXML
    private TableColumn<?, ?> recibosAseguradoTableColumn;
    @FXML
    private TableColumn<?, ?> recibosPolizaTableColumn;
    @FXML
    private TableColumn<?, ?> recibosDesdeTableColumn;
    @FXML
    private TableColumn<?, ?> recibosHastaTableColumn;
    @FXML
    private TableColumn<?, ?> recibosImporteTableColumn;
    @FXML
    private TableColumn<?, ?> recibosConductoTableColumn;
    @FXML
    private TableColumn<?, ?> recibosUltimaNotificacionTableColumn;
    @FXML
    private TableColumn<?, ?> diasDesdeTableColumn;

    @FXML
    private TableView<Poliza> renovacionesTableView;
    @FXML
    private TableColumn<?, ?> renovacionesAseguradoTableColumn;
    @FXML
    private TableColumn<?, ?> renovacionesPolizaTableColumn;
    @FXML
    private TableColumn<?, ?> renovacionesFinVigenciaTableColumn;
    @FXML
    private TableColumn<?, ?> renovacionesFaltanTableColumn;

    @FXML
    private TableView<NotificacionCumpleWrapper> cumpleTableView;
    @FXML
    private TableColumn<NotificacionCumpleWrapper, Boolean> cumpleCheckBoxColumn;
    @FXML
    private TableColumn<?, ?> cumpleNombreTableColumn;
    @FXML
    private TableColumn<?, ?> cumpleNacimientoTableColumn;
    @FXML
    private TableColumn<?, ?> cumpleFaltanTableColumn;
    @FXML
    private TableColumn<?, ?> cumpleEstadoTableColumn;
    @FXML
    private DatePicker cubreDesdeStartPicker;
    @FXML
    private DatePicker cubreDesdeEndPicker;
    @FXML
    private DatePicker renovacionEntreStartPicker;
    @FXML
    private DatePicker renovacionEntreEndPicker;
    @FXML
    private DatePicker cumpleEntreStartPicker;
    @FXML
    private DatePicker cumpleEntreEndPicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            llenarDatePickers();
            llenarRecibos();
            llenarCumples();
            llenarRenovaciones();
        });
    }

    private List<NotificacionCumpleWrapper> getAseguradoWrappers() {
        List<NotificacionCumple> asegurados = MainApp.getInstance().getBaseDeDatos().getAll(Asegurado.class);
        List<NotificacionCumpleWrapper> wrappers = new ArrayList<>();
        asegurados.stream().forEach(a -> wrappers.add(new NotificacionCumpleWrapper(a, Boolean.FALSE)));
        return wrappers;
    }

    private void llenarRecibos() {
        recibosTableView.setItems(FXCollections.observableArrayList(getNotificacionReciboWrappers()));

        recibosAseguradoTableColumn.setCellValueFactory(new PropertyValueFactory<>("asegurado"));
        recibosPolizaTableColumn.setCellValueFactory(new PropertyValueFactory<>("poliza"));
        recibosDesdeTableColumn.setCellValueFactory(new PropertyValueFactory<>("cubreDesde"));
        recibosHastaTableColumn.setCellValueFactory(new PropertyValueFactory<>("cubreHasta"));
        recibosImporteTableColumn.setCellValueFactory(new PropertyValueFactory<>("importe"));
        recibosConductoTableColumn.setCellValueFactory(new PropertyValueFactory<>("conducto"));
        recibosUltimaNotificacionTableColumn.setCellValueFactory(new PropertyValueFactory<>("enviado"));
        diasDesdeTableColumn.setCellValueFactory(new PropertyValueFactory<>("diasDesde"));
        recibosCheckBoxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        recibosCheckBoxColumn.setCellFactory(v -> new CheckBoxTableCell<>());

        int numberColumns = recibosTableView.getColumns().size() - 1;
        recibosAseguradoTableColumn.prefWidthProperty().bind(recibosTableView.widthProperty().subtract(recibosCheckBoxColumn.widthProperty()).divide(numberColumns));
        recibosPolizaTableColumn.prefWidthProperty().bind(recibosTableView.widthProperty().subtract(recibosCheckBoxColumn.widthProperty()).divide(numberColumns));
        recibosDesdeTableColumn.prefWidthProperty().bind(recibosTableView.widthProperty().subtract(recibosCheckBoxColumn.widthProperty()).divide(numberColumns));
        recibosHastaTableColumn.prefWidthProperty().bind(recibosTableView.widthProperty().subtract(recibosCheckBoxColumn.widthProperty()).divide(numberColumns));
        recibosImporteTableColumn.prefWidthProperty().bind(recibosTableView.widthProperty().subtract(recibosCheckBoxColumn.widthProperty()).divide(numberColumns));
        recibosUltimaNotificacionTableColumn.prefWidthProperty().bind(recibosTableView.widthProperty().subtract(recibosCheckBoxColumn.widthProperty()).divide(numberColumns));
        diasDesdeTableColumn.prefWidthProperty().bind(recibosTableView.widthProperty().subtract(recibosCheckBoxColumn.widthProperty()).divide(numberColumns));
    }

    private List<NotificacionReciboWrapper> getNotificacionReciboWrappers() {
        List<NotificacionRecibo> recibos = MainApp.getInstance().getBaseDeDatos().getRecibosPendientesEntre(cubreDesdeStartPicker.getValue(), cubreDesdeEndPicker.getValue());
        return wrapNotificacionRecibo(recibos);
    }

    private List<NotificacionReciboWrapper> wrapNotificacionRecibo(List<NotificacionRecibo> recibos) {
        List<NotificacionReciboWrapper> result = new ArrayList<>();
        recibos.stream().forEach(r -> result.add(new NotificacionReciboWrapper(r, Boolean.FALSE)));
        return result;
    }

    private List<NotificacionCumpleWrapper> wrapNotificacionCumple(List<NotificacionCumple> cumples) {
        List<NotificacionCumpleWrapper> result = new ArrayList<>();
        cumples.stream().forEach(r -> result.add(new NotificacionCumpleWrapper(r, Boolean.FALSE)));
        return result;
    }

    private void llenarCumples() {
        cumpleTableView.setItems(FXCollections.observableArrayList(getNotificacionCumpleWrappers()));

        cumpleNombreTableColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cumpleNacimientoTableColumn.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));
        cumpleFaltanTableColumn.setCellValueFactory(new PropertyValueFactory<>("faltan"));
        cumpleEstadoTableColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cumpleCheckBoxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        cumpleCheckBoxColumn.setCellFactory(v -> new CheckBoxTableCell<>());

        int numberColumns = cumpleTableView.getColumns().size() - 1;
        cumpleNombreTableColumn.prefWidthProperty().bind(cumpleTableView.widthProperty().subtract(cumpleCheckBoxColumn.widthProperty()).divide(numberColumns));
        cumpleNacimientoTableColumn.prefWidthProperty().bind(cumpleTableView.widthProperty().subtract(cumpleCheckBoxColumn.widthProperty()).divide(numberColumns));
        cumpleFaltanTableColumn.prefWidthProperty().bind(cumpleTableView.widthProperty().subtract(cumpleCheckBoxColumn.widthProperty()).divide(numberColumns));
        cumpleEstadoTableColumn.prefWidthProperty().bind(cumpleTableView.widthProperty().subtract(cumpleCheckBoxColumn.widthProperty()).divide(numberColumns));
    }

    private List<NotificacionCumpleWrapper> getNotificacionCumpleWrappers() {
        List<NotificacionCumple> notificaciones = MainApp.getInstance().getBaseDeDatos().getCumplesPendientesEntre(cumpleEntreStartPicker.getValue(), cumpleEntreEndPicker.getValue());
        return wrapNotificacionCumple(notificaciones);
    }

    private void llenarRenovaciones() {
        renovacionesTableView.setItems(FXCollections.observableArrayList(getRenovaciones()));
        renovacionesAseguradoTableColumn.setCellValueFactory(new PropertyValueFactory<>("asegurado"));
        renovacionesPolizaTableColumn.setCellValueFactory(new PropertyValueFactory<>("poliza"));
        renovacionesFinVigenciaTableColumn.setCellValueFactory(new PropertyValueFactory<>("finVigencia"));
        renovacionesFaltanTableColumn.setCellValueFactory(new PropertyValueFactory<>("faltan"));
    }

    private List<Poliza> getRenovaciones() {
        return MainApp.getInstance().getBaseDeDatos().getRenovacionesEntre(Globals.RENOVACION_ENTRE_START_DEFAULT, Globals.RENOVACION_ENTRE_END_DEFAULT);
    }

    private void llenarDatePickers() {
        cubreDesdeStartPicker.setValue(Globals.RECIBO_CUBRE_DESDE_INICIO_DEFAULT);
        cubreDesdeEndPicker.setValue(Globals.RECIBO_CUBRE_DESDE_FIN_DEFAULT);

        renovacionEntreStartPicker.setValue(Globals.RENOVACION_ENTRE_START_DEFAULT);
        renovacionEntreEndPicker.setValue(Globals.RENOVACION_ENTRE_END_DEFAULT);

        cumpleEntreStartPicker.setValue(Globals.CUMPLES_ENTRE_START_DEFAULT);
        cumpleEntreEndPicker.setValue(Globals.CUMPLES_ENTRE_END_DEFAULT);
    }

    @FXML
    private void notificarRecibos(ActionEvent event) {
        try {
            Stream<NotificacionReciboWrapper> wrappers = recibosTableView.getItems().stream().filter(w -> w.getSelectedProperty().get());
            List<NotificacionRecibo> recibos = wrappers.map(w -> w.getNotificacionRecibo()).collect(Collectors.toList());
            EnviarNotificacionesTask task = new EnviarNotificacionesTask(recibos);

            ProgressForm form = new ProgressForm();
            form.activateProgressBar(task);
            task.setOnSucceeded((e) -> {
                form.getDialogStage().close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exito");
                alert.setHeaderText("Se han enviado las notificaciones");
                alert.showAndWait();
            });
            task.setOnFailed(e -> {
                form.getDialogStage().close();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se pudo enviar las notificaciones");
                alert.setContentText("Hubo un error al conectarse con el servidor de correos");
                alert.showAndWait();
            });

            form.getDialogStage().show();
            Thread t = new Thread(task);
            t.start();
            while (t.isAlive()) {
            }
            recibosTableView.getItems().clear();
            recibosTableView.setItems(FXCollections.observableArrayList(getNotificacionReciboWrappers()));

        } catch (Exception ex) {
            Utilities.makeAlert(ex, "Error al enviar notificaciones").showAndWait();
        }
    }

    @FXML
    private void notificarCumples(ActionEvent event) {
        try {
            Stream<NotificacionCumpleWrapper> wrappers = cumpleTableView.getItems().stream().filter(w -> w.getSelectedProperty().get());
            List<NotificacionCumple> cumples = wrappers.map(n -> n.getNotificacionCumple()).collect(Collectors.toList());
            EnviarNotificacionesTask task = new EnviarNotificacionesTask(cumples);

            ProgressForm form = new ProgressForm();
            form.activateProgressBar(task);
            task.setOnSucceeded((e) -> {
                form.getDialogStage().close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exito");
                alert.setHeaderText("Se han enviado las notificaciones");
                alert.showAndWait();
            });
            task.setOnFailed(e -> {
                form.getDialogStage().close();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se pudo enviar las notificaciones");
                alert.setContentText("Hubo un error al conectarse con el servidor de correos");
                alert.showAndWait();
            });

            form.getDialogStage().show();
            Thread t = new Thread(task);
            t.start();

            cumpleTableView.getItems().clear();
            cumpleTableView.setItems(FXCollections.observableArrayList(getNotificacionCumpleWrappers()));
        } catch (Exception ex) {
            Utilities.makeAlert(ex, "Error al enviar notificaciones").showAndWait();
        }
    }

    @FXML
    private void goHome(ActionEvent event) {
        try {
            MainApp.getInstance().goHome();
        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            Utilities.makeAlert(Alert.AlertType.ERROR, "Error al cambiar de ventana", "").showAndWait();
        }
    }

    @FXML
    private void buscarRecibos(ActionEvent event) {
        if (validarRecibosDatePicker()) {
            List<NotificacionRecibo> notificaciones = MainApp.getInstance().getBaseDeDatos().getRecibosPendientesEntre(cubreDesdeStartPicker.getValue(), cubreDesdeEndPicker.getValue());
            recibosTableView.getItems().clear();
            recibosTableView.setItems(FXCollections.observableArrayList(wrapNotificacionRecibo(notificaciones)));
        } else {
            makeAlertFechasNoValidas();
            makeAlertFechasNoValidas().showAndWait();
        }
    }

    @FXML
    private void buscarRenovaciones(ActionEvent event) {
        if (validarRenovacionesDatePicker()) {
            List<Poliza> resultList = MainApp.getInstance().getBaseDeDatos().getRenovacionesEntre(renovacionEntreStartPicker.getValue(), renovacionEntreEndPicker.getValue());
            renovacionesTableView.getItems().clear();
            renovacionesTableView.setItems(FXCollections.observableArrayList(resultList));
        } else {
            makeAlertFechasNoValidas().showAndWait();
        }
    }

    @FXML
    private void buscarCumples(ActionEvent event) {
        if (validarCumplesDatePicker()) {
            List<NotificacionCumple> resultList = MainApp.getInstance().getBaseDeDatos().getCumplesPendientesEntre(cumpleEntreStartPicker.getValue(), cumpleEntreEndPicker.getValue());
            cumpleTableView.getItems().clear();
            cumpleTableView.setItems(FXCollections.observableArrayList(wrapNotificacionCumple(resultList)));
        } else {
            makeAlertFechasNoValidas().showAndWait();
        }
    }

    private boolean validarRecibosDatePicker() {
        if (cubreDesdeStartPicker.getValue() != null && cubreDesdeEndPicker.getValue() != null) {
            return cubreDesdeStartPicker.getValue().isBefore(cubreDesdeEndPicker.getValue());
        }
        return false;
    }

    private boolean validarRenovacionesDatePicker() {
        if (renovacionEntreEndPicker.getValue() != null && renovacionEntreStartPicker.getValue() != null) {
            return renovacionEntreStartPicker.getValue().isBefore(renovacionEntreEndPicker.getValue());
        }
        return false;
    }

    private boolean validarCumplesDatePicker() {
        if (cumpleEntreStartPicker.getValue() != null && cumpleEntreEndPicker.getValue() != null) {
            return cumpleEntreStartPicker.getValue().isBefore(cumpleEntreEndPicker.getValue());
        }
        return false;
    }

    private Alert makeAlertFechasNoValidas() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Fechas no válidas");
        alert.setContentText("Las fechas no pueden ser vacías y la primera debe ser menor que la segunda");
        return alert;
    }

}
