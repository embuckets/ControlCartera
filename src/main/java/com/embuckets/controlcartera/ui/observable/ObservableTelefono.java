/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Telefono;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class ObservableTelefono {

    private int idCliente;
    private StringProperty telefonoProperty;
    private StringProperty extensionProperty;
    private StringProperty tipoProperty;

    public ObservableTelefono(String telefonoProperty, String extensionProperty, String tipoProperty) {
        this.telefonoProperty = new SimpleStringProperty(telefonoProperty);
        this.extensionProperty = new SimpleStringProperty(extensionProperty);
        this.tipoProperty = new SimpleStringProperty(tipoProperty);
    }

    public ObservableTelefono(Telefono telefono) {
        this.telefonoProperty = new SimpleStringProperty(telefono.getTelefonoPK().getTelefono());
        this.extensionProperty = new SimpleStringProperty(telefono.getExtension());
        this.tipoProperty = new SimpleStringProperty(telefono.getTipotelefono().getTipotelefono());
        this.idCliente = telefono.getTelefonoPK().getIdcliente();

    }

    public StringProperty telefonoProperty() {
        return telefonoProperty;
    }

    public void setTelefonoProperty(StringProperty telefonoProperty) {
        this.telefonoProperty = telefonoProperty;
    }

    public StringProperty extensionProperty() {
        return extensionProperty;
    }

    public void setExtensionProperty(StringProperty extensionProperty) {
        this.extensionProperty = extensionProperty;
    }

    public StringProperty tipoProperty() {
        return tipoProperty;
    }

    public void setTipoProperty(StringProperty tipoProperty) {
        this.tipoProperty = tipoProperty;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

}
