/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "ESTADO_POLIZA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoPoliza.findAll", query = "SELECT e FROM EstadoPoliza e"),
    @NamedQuery(name = "EstadoPoliza.findByEstado", query = "SELECT e FROM EstadoPoliza e WHERE e.estado = :estado")})
public class EstadoPoliza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado", fetch = FetchType.LAZY)
    private List<Poliza> polizaList;

    public EstadoPoliza() {
    }

    public EstadoPoliza(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Poliza> getPolizaList() {
        return polizaList;
    }

    public void setPolizaList(List<Poliza> polizaList) {
        this.polizaList = polizaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estado != null ? estado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoPoliza)) {
            return false;
        }
        EstadoPoliza other = (EstadoPoliza) object;
        if ((this.estado == null && other.estado != null) || (this.estado != null && !this.estado.equals(other.estado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.EstadoPoliza[ estado=" + estado + " ]";
    }
    
}
