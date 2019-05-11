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

    /**
     *
     * @param notificacion
     * @param selected
     */
    public NotificacionCumpleWrapper(NotificacionCumple notificacion, Boolean selected) {
        this.notificacion = notificacion;
        this.selectedProperty = new SimpleBooleanProperty(selected);
    }

    /**
     *
     * @return notificacion de cumple
     */
    public NotificacionCumple getNotificacionCumple() {
        return notificacion;
    }

    /**
     *
     * @param notificacion notificacion que envuelve este wrapper
     */
    public void setNotificacionCumple(NotificacionCumple notificacion) {
        this.notificacion = notificacion;
    }

    /**
     *
     * @return si este wrapper esta selecciondo
     */
    public BooleanProperty getSelectedProperty() {
        return selectedProperty;
    }

    /**
     *
     * @param selectedProperty
     */
    public void setSelectedProperty(BooleanProperty selectedProperty) {
        this.selectedProperty = selectedProperty;
    }

    /**
     *
     * @return
     */
    public StringProperty nombreProperty() {
        return notificacion.getCliente().nombreProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty nacimientoProperty() {
        return notificacion.getCliente().nacimientoProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty faltanProperty() {
        return notificacion.faltanProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty estadoProperty() {
        return notificacion.estadoProperty();
    }

    /**
     *
     * @return
     */
    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }

}
