/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.ui.observable.ObservableAuto;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "AUTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auto.findAll", query = "SELECT a FROM Auto a"),
    @NamedQuery(name = "Auto.findByIdauto", query = "SELECT a FROM Auto a WHERE a.idauto = :idauto"),
    @NamedQuery(name = "Auto.findByDescripcion", query = "SELECT a FROM Auto a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Auto.findByMarca", query = "SELECT a FROM Auto a WHERE a.marca = :marca"),
    @NamedQuery(name = "Auto.findBySubmarca", query = "SELECT a FROM Auto a WHERE a.submarca = :submarca"),
    @NamedQuery(name = "Auto.findByModelo", query = "SELECT a FROM Auto a WHERE a.modelo = :modelo")})
public class Auto implements Serializable, ObservableAuto {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDAUTO")
    private Integer idauto;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "MARCA")
    private String marca;
    @Basic(optional = false)
    @Column(name = "SUBMARCA")
    private String submarca;
    @Basic(optional = false)
    @Column(name = "MODELO")
    @Temporal(TemporalType.DATE)
    private Date modelo;
    @JoinColumn(name = "IDPOLIZA", referencedColumnName = "IDPOLIZA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PolizaAuto idpoliza;

    public Auto() {
    }

    public Auto(Integer idauto) {
        this.idauto = idauto;
    }

    public Auto(Integer idauto, String descripcion, String marca, String submarca, Year modelo) {
        this.idauto = idauto;
        this.descripcion = descripcion;
        this.marca = marca;
        this.submarca = submarca;
        this.modelo = Date.from(LocalDate.of(modelo.getValue(), Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Integer getIdauto() {
        return idauto;
    }

    public void setIdauto(Integer idauto) {
        this.idauto = idauto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSubmarca() {
        return submarca;
    }

    public void setSubmarca(String submarca) {
        this.submarca = submarca;
    }

    public Date getModelo() {
        return modelo;
    }

    public void setModelo(Year modelo) {
        this.modelo = Date.from(LocalDate.of(modelo.getValue(), Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public PolizaAuto getIdpoliza() {
        return idpoliza;
    }

    public void setIdpoliza(PolizaAuto idpoliza) {
        this.idpoliza = idpoliza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idauto != null ? idauto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auto)) {
            return false;
        }
        Auto other = (Auto) object;
        if ((this.idauto == null && other.idauto != null) || (this.idauto != null && !this.idauto.equals(other.idauto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Auto[ idauto=" + idauto + " ]";
    }

    @Override
    public StringProperty descripcionProperty() {
        return new SimpleStringProperty(descripcion);
    }

    @Override
    public StringProperty marcaProperty() {
        return new SimpleStringProperty(marca);
    }

    @Override
    public StringProperty submarcaProperty() {
        return new SimpleStringProperty(submarca);
    }

    @Override
    public StringProperty modeloProperty() {
        return new SimpleStringProperty("" + modelo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
    }

}
