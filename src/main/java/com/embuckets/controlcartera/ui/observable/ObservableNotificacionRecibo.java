/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class ObservableNotificacionRecibo {

    private IntegerPropertyBase idReciboProperty;
    private StringProperty polizaProperty;
    private StringProperty aseguradoProperty;
    private StringProperty cubreDesdeProperty;
    private StringProperty cubreHastaProperty;
    private StringProperty importeProperty;
    private StringProperty enviadoProperty;

    public ObservableNotificacionRecibo(NotificacionRecibo notificacionRecibo) {
        Poliza poliza = notificacionRecibo.getRecibo().getIdpoliza();
        Asegurado asegurado = poliza.getContratante();
        Cliente cliente = asegurado.getCliente();
        this.idReciboProperty = new SimpleIntegerProperty(notificacionRecibo.getRecibo().getIdrecibo());
        this.polizaProperty = new SimpleStringProperty(poliza.getNumero());
        this.aseguradoProperty = new SimpleStringProperty(cliente.getNombre() + " " + cliente.getApellidopaterno() + " " + cliente.getApellidomaterno());
        this.cubreDesdeProperty = new SimpleStringProperty(notificacionRecibo.getRecibo().getCubredesde().toString());
        this.cubreHastaProperty = new SimpleStringProperty(notificacionRecibo.getRecibo().getCubrehasta().toString());
        this.importeProperty = new SimpleStringProperty(notificacionRecibo.getRecibo().getImporte().toPlainString());
        this.enviadoProperty = new SimpleStringProperty(notificacionRecibo.getEnviado().toString());
    }

    public IntegerPropertyBase getIdReciboProperty() {
        return idReciboProperty;
    }

    public void setIdReciboProperty(IntegerPropertyBase idReciboProperty) {
        this.idReciboProperty = idReciboProperty;
    }

    public StringProperty getPolizaProperty() {
        return polizaProperty;
    }

    public void setPolizaProperty(StringProperty polizaProperty) {
        this.polizaProperty = polizaProperty;
    }

    public StringProperty getAseguradoProperty() {
        return aseguradoProperty;
    }

    public void setAseguradoProperty(StringProperty aseguradoProperty) {
        this.aseguradoProperty = aseguradoProperty;
    }

    public StringProperty getCubreDesdeProperty() {
        return cubreDesdeProperty;
    }

    public void setCubreDesdeProperty(StringProperty cubreDesdeProperty) {
        this.cubreDesdeProperty = cubreDesdeProperty;
    }

    public StringProperty getCubreHastaProperty() {
        return cubreHastaProperty;
    }

    public void setCubreHastaProperty(StringProperty cubreHastaProperty) {
        this.cubreHastaProperty = cubreHastaProperty;
    }

    public StringProperty getImporteProperty() {
        return importeProperty;
    }

    public void setImporteProperty(StringProperty importeProperty) {
        this.importeProperty = importeProperty;
    }

    public StringProperty getEnviadoProperty() {
        return enviadoProperty;
    }

    public void setEnviadoProperty(StringProperty enviadoProperty) {
        this.enviadoProperty = enviadoProperty;
    }

}
