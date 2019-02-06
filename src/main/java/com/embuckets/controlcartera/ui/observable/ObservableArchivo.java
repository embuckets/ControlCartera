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

    private StringProperty archivoProperty;
    private StringProperty tipoProperty;

    public ObservableArchivo(String archivoProperty, String tipoProperty) {
        this.archivoProperty = new SimpleStringProperty(archivoProperty);
        this.tipoProperty = new SimpleStringProperty(tipoProperty);
    }

    public StringProperty archivoProperty() {
        return archivoProperty;
    }

    public void setArchivoProperty(StringProperty archivoProperty) {
        this.archivoProperty = archivoProperty;
    }

    public StringProperty tipoProperty() {
        return tipoProperty;
    }

    public void setTipoProperty(StringProperty tipoProperty) {
        this.tipoProperty = tipoProperty;
    }

}
