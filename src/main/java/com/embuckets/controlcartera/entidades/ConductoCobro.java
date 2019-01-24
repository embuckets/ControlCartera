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
@Table(name = "CONDUCTO_COBRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConductoCobro.findAll", query = "SELECT c FROM ConductoCobro c"),
    @NamedQuery(name = "ConductoCobro.findByConductocobro", query = "SELECT c FROM ConductoCobro c WHERE c.conductocobro = :conductocobro")})
public class ConductoCobro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CONDUCTOCOBRO")
    private String conductocobro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conductocobro", fetch = FetchType.LAZY)
    private List<Poliza> polizaList;

    public ConductoCobro() {
    }

    public ConductoCobro(String conductocobro) {
        this.conductocobro = conductocobro;
    }

    public String getConductocobro() {
        return conductocobro;
    }

    public void setConductocobro(String conductocobro) {
        this.conductocobro = conductocobro;
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
        hash += (conductocobro != null ? conductocobro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConductoCobro)) {
            return false;
        }
        ConductoCobro other = (ConductoCobro) object;
        if ((this.conductocobro == null && other.conductocobro != null) || (this.conductocobro != null && !this.conductocobro.equals(other.conductocobro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.ConductoCobro[ conductocobro=" + conductocobro + " ]";
    }
    
}
