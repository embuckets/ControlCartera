/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Poliza;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    private int idPoliza;
    private StringProperty numeroProperty;
    private StringProperty aseguradoraProperty;
    private StringProperty ramoProperty;
    private StringProperty productoProperty;
    private StringProperty planProperty;
    private StringProperty primaProperty;
    private StringProperty finVigenciaProperty;
    private ObservableAsegurado contratanteProperty;
    private Date finVigenciaDate;

    public ObservablePoliza(Poliza poliza) {
        this.idPoliza = poliza.getIdpoliza();
        this.numeroProperty = new SimpleStringProperty(poliza.getNumero());
        this.aseguradoraProperty = new SimpleStringProperty(poliza.getAseguradora().getAseguradora());
        this.ramoProperty = new SimpleStringProperty(poliza.getRamo().getRamo());
        this.productoProperty = new SimpleStringProperty(poliza.getProducto());
        this.planProperty = new SimpleStringProperty(poliza.getPlan());
        this.primaProperty = new SimpleStringProperty(poliza.getPrima().toString());
        this.contratanteProperty = new ObservableAsegurado(poliza.getContratante());
        this.finVigenciaDate = poliza.getFinvigencia();
        this.finVigenciaProperty = new SimpleStringProperty(formatDate(poliza.getFinvigencia()));
    }

    public StringProperty aseguradoProperty() {
        return contratanteProperty.nombreProperty();
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
        return new SimpleStringProperty("$" + primaProperty.get());
    }

    public StringProperty finVigenciaProperty() {
        return finVigenciaProperty;
    }

    public StringProperty faltanProperty() {
        LocalDate fin = finVigenciaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new SimpleStringProperty("" + (fin.getDayOfYear() - LocalDate.now().getDayOfYear()) + " días");
    }

    public void setNumeroProperty(StringProperty numeroProperty) {
        this.numeroProperty = numeroProperty;
    }

    public void setAseguradoraProperty(StringProperty aseguradoraProperty) {
        this.aseguradoraProperty = aseguradoraProperty;
    }

    public void setRamoProperty(StringProperty ramoProperty) {
        this.ramoProperty = ramoProperty;
    }

    public void setProductoProperty(StringProperty productoProperty) {
        this.productoProperty = productoProperty;
    }

    public void setPlanProperty(StringProperty planProperty) {
        this.planProperty = planProperty;
    }

    public void setPrimaProperty(StringProperty primaProperty) {
        this.primaProperty = primaProperty;
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

    private String formatDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }

    
    public int getId() {
        return getIdPoliza();
    }

    public int getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza(int idPoliza) {
        this.idPoliza = idPoliza;
    }

    
    public StringProperty nombreProperty() {
        return new SimpleStringProperty("");
    }

}
