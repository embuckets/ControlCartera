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
//    @Temporal(TemporalType.DATE)
    private LocalDate modelo;
    @JoinColumn(name = "IDPOLIZA", referencedColumnName = "IDPOLIZA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PolizaAuto idpoliza;

    /**
     *
     */
    public Auto() {
    }

    /**
     *
     * @param idauto
     */
    public Auto(Integer idauto) {
        this.idauto = idauto;
    }

    /**
     *
     * @param idauto
     * @param descripcion
     * @param marca
     * @param submarca
     * @param modelo
     */
    public Auto(Integer idauto, String descripcion, String marca, String submarca, Year modelo) {
        this.idauto = idauto;
        this.descripcion = descripcion;
        this.marca = marca;
        this.submarca = submarca;
        this.modelo = LocalDate.of(modelo.getValue(), Month.JANUARY, 1);
    }

    /**
     *
     * @return
     */
    public Integer getIdauto() {
        return idauto;
    }

    /**
     *
     * @param idauto
     */
    public void setIdauto(Integer idauto) {
        this.idauto = idauto;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public String getMarca() {
        return marca;
    }

    /**
     *
     * @param marca
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     *
     * @return
     */
    public String getSubmarca() {
        return submarca;
    }

    /**
     *
     * @param submarca
     */
    public void setSubmarca(String submarca) {
        this.submarca = submarca;
    }

    /**
     *
     * @return
     */
    public LocalDate getModelo() {
        return modelo;
    }

    /**
     *
     * @param modelo
     */
    public void setModelo(Year modelo) {
        this.modelo = LocalDate.of(modelo.getValue(), Month.JANUARY, 1);
    }

    /**
     *
     * @return
     */
    public PolizaAuto getIdpoliza() {
        return idpoliza;
    }

    /**
     *
     * @param idpoliza
     */
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

    /**
     *
     * @return
     */
    @Override
    public StringProperty descripcionProperty() {
        return new SimpleStringProperty(descripcion);
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty marcaProperty() {
        return new SimpleStringProperty(marca);
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty submarcaProperty() {
        return new SimpleStringProperty(submarca);
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty modeloProperty() {
        return new SimpleStringProperty("" + modelo.getYear());
    }

}
