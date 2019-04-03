/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class NotificacionReciboWrapper {

    private NotificacionRecibo notificacion;
    private BooleanProperty selectedProperty;

    public NotificacionReciboWrapper(NotificacionRecibo notificacion, Boolean selected) {
        this.notificacion = notificacion;
        this.selectedProperty = new SimpleBooleanProperty(selected);
    }

    public NotificacionRecibo getNotificacionRecibo() {
        return notificacion;
    }

    public void setNotificacionRecibo(NotificacionRecibo asegurado) {
        this.notificacion = asegurado;
    }

    public BooleanProperty getSelectedProperty() {
        return selectedProperty;
    }

    public void setSelectedProperty(BooleanProperty selectedProperty) {
        this.selectedProperty = selectedProperty;
    }

    public StringProperty aseguradoProperty() {
        return notificacion.aseguradoProperty();
    }

    public StringProperty polizaProperty() {
        return notificacion.polizaProperty();
    }

    public StringProperty cubreDesdeProperty() {
        return notificacion.cubreDesdeProperty();
    }

    public StringProperty cubreHastaProperty() {
        return notificacion.cubreHastaProperty();
    }

    public StringProperty importeProperty() {
        return notificacion.importeProperty();
    }

    public StringProperty enviadoProperty() {
        return notificacion.enviadoProperty();
    }

    public StringProperty diasDesdeProperty() {
        return notificacion.diasDesdeProperty();
    }

    public StringProperty conductoProperty() {
        return notificacion.conductoProperty();
    }

    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }

}
