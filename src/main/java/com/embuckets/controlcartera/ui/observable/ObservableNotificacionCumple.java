/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class ObservableNotificacionCumple {

    private IntegerPropertyBase idClienteProperty;
    private StringProperty aseguradoProperty;
    private StringProperty nacimientoProperty;

    public ObservableNotificacionCumple(NotificacionCumple notificacionCumple) {
        Cliente cliente = notificacionCumple.getCliente();
        this.idClienteProperty = new SimpleIntegerProperty(notificacionCumple.getCliente().getIdcliente());
        this.aseguradoProperty = new SimpleStringProperty(cliente.getNombre() + " " + cliente.getApellidopaterno() + " " + cliente.getApellidomaterno());
        this.nacimientoProperty = new SimpleStringProperty(cliente.getNacimiento().toString());
    }

    public IntegerPropertyBase getIdClienteProperty() {
        return idClienteProperty;
    }

    public void setIdClienteProperty(IntegerPropertyBase idClienteProperty) {
        this.idClienteProperty = idClienteProperty;
    }

    public StringProperty getAseguradoProperty() {
        return aseguradoProperty;
    }

    public void setAseguradoProperty(StringProperty aseguradoProperty) {
        this.aseguradoProperty = aseguradoProperty;
    }

    public StringProperty getNacimientoProperty() {
        return nacimientoProperty;
    }

    public void setNacimientoProperty(StringProperty nacimientoProperty) {
        this.nacimientoProperty = nacimientoProperty;
    }

}
