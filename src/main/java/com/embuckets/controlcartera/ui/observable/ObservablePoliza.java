/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Poliza;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class ObservablePoliza {

    //TODO: ID
    private IntegerPropertyBase idPolizaProperty;
    private StringProperty numeroProperty;
    private StringProperty aseguradoraProperty;
    private StringProperty ramoProperty;
    private StringProperty productoProperty;
    private StringProperty planProperty;
    private StringProperty primaProperty;
    private StringProperty finVigenciaProperty;
    private ObservableAsegurado contratanteProperty;

    public ObservablePoliza(Poliza poliza) {
        this.idPolizaProperty = new SimpleIntegerProperty(poliza.getIdpoliza());
        this.numeroProperty = new SimpleStringProperty(poliza.getNumero());
        this.aseguradoraProperty = new SimpleStringProperty(poliza.getAseguradora().getAseguradora());
        this.ramoProperty = new SimpleStringProperty(poliza.getRamo().toString());
        this.productoProperty = new SimpleStringProperty(poliza.getProducto());
        this.planProperty = new SimpleStringProperty(poliza.getPlan());
        this.primaProperty = new SimpleStringProperty(poliza.getPrima().toString());
        this.contratanteProperty = new ObservableAsegurado(poliza.getContratante());
        this.finVigenciaProperty = new SimpleStringProperty(poliza.getFinvigencia().toString());
    }

    public StringProperty numeroProperty() {
        return numeroProperty;
    }

    public StringProperty aseguradoraProperty() {
        return aseguradoraProperty;
    }

    public StringProperty ramoProperty() {
        return ramoProperty;
    }

//    public StringProperty nombreProperty() {
//        //TODO: regresar PLAN + PRODUCTO
//        return numeroProperty;
//    }
    public StringProperty productoProperty() {
        //TODO: regresar PLAN + PRODUCTO
        return productoProperty;
    }

    public StringProperty planProperty() {
        //TODO: regresar PLAN + PRODUCTO
        return planProperty;
    }

    public StringProperty primaProperty() {
        //TODO: regresar PLAN + PRODUCTO
        return primaProperty;
    }

    public IntegerPropertyBase getIdPolizaProperty() {
        return idPolizaProperty;
    }

    public void setIdPolizaProperty(IntegerPropertyBase idPolizaProperty) {
        this.idPolizaProperty = idPolizaProperty;
    }

    public StringProperty getNumeroProperty() {
        return numeroProperty;
    }

    public void setNumeroProperty(StringProperty numeroProperty) {
        this.numeroProperty = numeroProperty;
    }

    public StringProperty getAseguradoraProperty() {
        return aseguradoraProperty;
    }

    public void setAseguradoraProperty(StringProperty aseguradoraProperty) {
        this.aseguradoraProperty = aseguradoraProperty;
    }

    public StringProperty getRamoProperty() {
        return ramoProperty;
    }

    public void setRamoProperty(StringProperty ramoProperty) {
        this.ramoProperty = ramoProperty;
    }

    public StringProperty getProductoProperty() {
        return productoProperty;
    }

    public void setProductoProperty(StringProperty productoProperty) {
        this.productoProperty = productoProperty;
    }

    public StringProperty getPlanProperty() {
        return planProperty;
    }

    public void setPlanProperty(StringProperty planProperty) {
        this.planProperty = planProperty;
    }

    public StringProperty getPrimaProperty() {
        return primaProperty;
    }

    public void setPrimaProperty(StringProperty primaProperty) {
        this.primaProperty = primaProperty;
    }

    public StringProperty getFinVigenciaProperty() {
        return finVigenciaProperty;
    }

    public void setFinVigenciaProperty(StringProperty finVigenciaProperty) {
        this.finVigenciaProperty = finVigenciaProperty;
    }

    public ObservableAsegurado getContratanteProperty() {
        return contratanteProperty;
    }

    public void setContratanteProperty(ObservableAsegurado contratanteProperty) {
        this.contratanteProperty = contratanteProperty;
    }

}
