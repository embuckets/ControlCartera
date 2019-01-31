/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Cliente;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class ObservableCliente {

    private IntegerPropertyBase idCliente;
    private StringProperty nombreProperty;
    private StringProperty paternoProperty;
    private StringProperty maternoProperty;
    private StringProperty nacimientoProperty;

    public ObservableCliente(Cliente cliente) {
        this.idCliente = new SimpleIntegerProperty(cliente.getIdcliente());
        this.nombreProperty = new SimpleStringProperty(cliente.getNombre());
        this.paternoProperty = new SimpleStringProperty(cliente.getApellidopaterno());
        this.maternoProperty = new SimpleStringProperty(cliente.getApellidomaterno());
        this.nacimientoProperty = new SimpleStringProperty(cliente.getNacimiento().toString());
    }

    public StringProperty getNombreProperty() {
        return nombreProperty;
    }

    public void setNombreProperty(StringProperty nombreProperty) {
        this.nombreProperty = nombreProperty;
    }

    public StringProperty getPaternoProperty() {
        return paternoProperty;
    }

    public void setPaternoProperty(StringProperty paternoProperty) {
        this.paternoProperty = paternoProperty;
    }

    public StringProperty getMaternoProperty() {
        return maternoProperty;
    }

    public void setMaternoProperty(StringProperty maternoProperty) {
        this.maternoProperty = maternoProperty;
    }

    public StringProperty getNacimientoProperty() {
        return nacimientoProperty;
    }

    public void setNacimientoProperty(StringProperty nacimientoProperty) {
        this.nacimientoProperty = nacimientoProperty;
    }

    public IntegerPropertyBase getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(IntegerPropertyBase idCliente) {
        this.idCliente = idCliente;
    }

}
