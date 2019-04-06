/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Asegurado;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author emilio
 */
public class ObservableAsegurado {

    //TODO: ID
    private int idCliente;
    private StringProperty nombreProperty;
    private StringProperty paternoProperty;
    private StringProperty maternoProperty;
    private StringProperty nacimientoProperty;
    private ListProperty<ObservablePoliza> polizasProperty;

    public ObservableAsegurado(Asegurado asegurado) {
        this.idCliente = asegurado.getIdcliente();
        this.nombreProperty = new SimpleStringProperty(asegurado.getCliente().getNombre());
        this.paternoProperty = new SimpleStringProperty(asegurado.getCliente().getApellidopaterno());
        this.maternoProperty = new SimpleStringProperty(asegurado.getCliente().getApellidomaterno());
        this.nacimientoProperty = new SimpleStringProperty(asegurado.getCliente().getNacimiento().toString());
        ObservableList<ObservablePoliza> obsList = FXCollections.observableArrayList();
        this.polizasProperty = new SimpleListProperty<>(obsList);
    }

    public StringProperty nombreProperty() {
        return new SimpleStringProperty(nombreProperty.get() + " " + paternoProperty.get() + " " + maternoProperty.get());
    }

    public StringProperty nacimientoProperty() {
        return nacimientoProperty;
    }

    public void addObservablePoliza(ObservablePoliza poliza) {
        polizasProperty.add(poliza);
    }

    public ListProperty<ObservablePoliza> getObservablePolizas() {
        return polizasProperty;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getId() {
        return idCliente;
    }

    public StringProperty numeroProperty() {
        return new SimpleStringProperty("");
    }

    public StringProperty aseguradoraProperty() {
        return new SimpleStringProperty("");
    }

    public StringProperty ramoProperty() {
        return new SimpleStringProperty("");
    }

    public StringProperty productoProperty() {
        return new SimpleStringProperty("");
    }

    public StringProperty planProperty() {
        return new SimpleStringProperty("");
    }

    public StringProperty primaProperty() {
        return new SimpleStringProperty("");
    }

}
