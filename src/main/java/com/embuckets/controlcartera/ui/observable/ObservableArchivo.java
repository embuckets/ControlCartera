/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class ObservableArchivo {

    private int idCliente;
    private StringProperty archivoProperty;
    private StringProperty tipoProperty;

    /**
     *
     * @param archivoProperty
     * @param tipoProperty
     */
    public ObservableArchivo(String archivoProperty, String tipoProperty) {
        this.archivoProperty = new SimpleStringProperty(archivoProperty);
        this.tipoProperty = new SimpleStringProperty(tipoProperty);
    }

    /**
     *
     * @return
     */
    public StringProperty archivoProperty() {
        return archivoProperty;
    }

    /**
     *
     * @param archivoProperty
     */
    public void setArchivoProperty(StringProperty archivoProperty) {
        this.archivoProperty = archivoProperty;
    }

    /**
     *
     * @return
     */
    public StringProperty tipoProperty() {
        return tipoProperty;
    }

    /**
     *
     * @param tipoProperty
     */
    public void setTipoProperty(StringProperty tipoProperty) {
        this.tipoProperty = tipoProperty;
    }

    /**
     *
     * @return
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     *
     * @param idCliente
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

}
