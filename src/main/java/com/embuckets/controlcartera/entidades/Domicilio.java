/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "DOMICILIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Domicilio.findAll", query = "SELECT d FROM Domicilio d"),
    @NamedQuery(name = "Domicilio.findByIddomicilio", query = "SELECT d FROM Domicilio d WHERE d.iddomicilio = :iddomicilio"),
    @NamedQuery(name = "Domicilio.findByCalle", query = "SELECT d FROM Domicilio d WHERE d.calle = :calle"),
    @NamedQuery(name = "Domicilio.findByExterior", query = "SELECT d FROM Domicilio d WHERE d.exterior = :exterior"),
    @NamedQuery(name = "Domicilio.findByInterior", query = "SELECT d FROM Domicilio d WHERE d.interior = :interior"),
    @NamedQuery(name = "Domicilio.findByCodigopostal", query = "SELECT d FROM Domicilio d WHERE d.codigopostal = :codigopostal"),
    @NamedQuery(name = "Domicilio.findByColonia", query = "SELECT d FROM Domicilio d WHERE d.colonia = :colonia")})
public class Domicilio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDDOMICILIO")
    private Integer iddomicilio;
    @Basic(optional = false)
    @Column(name = "CALLE")
    private String calle;
    @Basic(optional = false)
    @Column(name = "EXTERIOR")
    private String exterior;
    @Column(name = "INTERIOR")
    private String interior;
    @Column(name = "CODIGOPOSTAL")
    private String codigopostal;
    @Column(name = "COLONIA")
    private String colonia;
    @JoinColumn(name = "DELEGACION", referencedColumnName = "DELEGACION")
    @ManyToOne(fetch = FetchType.EAGER)
    private Delegacion delegacion;
    @JoinColumn(name = "ESTADO", referencedColumnName = "ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estado;
    @OneToMany(mappedBy = "iddomicilio", fetch = FetchType.LAZY)
    private List<Asegurado> aseguradoList;

    public Domicilio() {
    }

    public Domicilio(Integer iddomicilio) {
        this.iddomicilio = iddomicilio;
    }

    public Domicilio(Integer iddomicilio, String calle, String exterior) {
        this.iddomicilio = iddomicilio;
        this.calle = calle;
        this.exterior = exterior;
    }

    public Integer getIddomicilio() {
        return iddomicilio;
    }

    public void setIddomicilio(Integer iddomicilio) {
        this.iddomicilio = iddomicilio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getExterior() {
        return exterior;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Delegacion getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(Delegacion delegacion) {
        this.delegacion = delegacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Asegurado> getAseguradoList() {
        return aseguradoList;
    }

    public void setAseguradoList(List<Asegurado> aseguradoList) {
        this.aseguradoList = aseguradoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddomicilio != null ? iddomicilio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Domicilio)) {
            return false;
        }
        Domicilio other = (Domicilio) object;
        if ((this.iddomicilio == null && other.iddomicilio != null) || (this.iddomicilio != null && !this.iddomicilio.equals(other.iddomicilio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Domicilio[ iddomicilio=" + iddomicilio + " ]";
    }

}
