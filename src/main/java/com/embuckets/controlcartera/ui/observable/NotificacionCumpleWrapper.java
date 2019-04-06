/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.NotificacionCumple;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class NotificacionCumpleWrapper {

    private NotificacionCumple notificacion;
    private BooleanProperty selectedProperty;

    public NotificacionCumpleWrapper(NotificacionCumple notificacion, Boolean selected) {
        this.notificacion = notificacion;
        this.selectedProperty = new SimpleBooleanProperty(selected);
    }

    public NotificacionCumple getNotificacionCumple() {
        return notificacion;
    }

    public void setNotificacionCumple(NotificacionCumple asegurado) {
        this.notificacion = asegurado;
    }

    public BooleanProperty getSelectedProperty() {
        return selectedProperty;
    }

    public void setSelectedProperty(BooleanProperty selectedProperty) {
        this.selectedProperty = selectedProperty;
    }

    public StringProperty nombreProperty() {
        return notificacion.getCliente().nombreProperty();
    }

    public StringProperty nacimientoProperty() {
        return notificacion.getCliente().nacimientoProperty();
    }

    public StringProperty faltanProperty() {
        return notificacion.faltanProperty();
    }

    public StringProperty estadoProperty() {
        return notificacion.estadoProperty();
    }

    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }

}
