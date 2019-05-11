/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class NotificacionReciboWrapper {

    private NotificacionRecibo notificacion;
    private BooleanProperty selectedProperty;

    /**
     *
     * @param notificacion
     * @param selected
     */
    public NotificacionReciboWrapper(NotificacionRecibo notificacion, Boolean selected) {
        this.notificacion = notificacion;
        this.selectedProperty = new SimpleBooleanProperty(selected);
    }

    /**
     *
     * @return
     */
    public NotificacionRecibo getNotificacionRecibo() {
        return notificacion;
    }

    /**
     *
     * @param asegurado
     */
    public void setNotificacionRecibo(NotificacionRecibo asegurado) {
        this.notificacion = asegurado;
    }

    /**
     *
     * @return
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
    public StringProperty aseguradoProperty() {
        return notificacion.aseguradoProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty polizaProperty() {
        return notificacion.polizaProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty cubreDesdeProperty() {
        return notificacion.cubreDesdeProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty cubreHastaProperty() {
        return notificacion.cubreHastaProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty importeProperty() {
        return notificacion.importeProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty enviadoProperty() {
        return notificacion.enviadoProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty diasDesdeProperty() {
        return notificacion.diasDesdeProperty();
    }

    /**
     *
     * @return
     */
    public StringProperty conductoProperty() {
        return notificacion.conductoProperty();
    }

    /**
     *
     * @return
     */
    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }

}
