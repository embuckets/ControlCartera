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
@Table(name = "RAMO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ramo.findAll", query = "SELECT r FROM Ramo r"),
    @NamedQuery(name = "Ramo.findByRamo", query = "SELECT r FROM Ramo r WHERE r.ramo = :ramo")})
public class Ramo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RAMO")
    private String ramo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ramo", fetch = FetchType.LAZY)
    private List<Poliza> polizaList;

    public Ramo() {
    }

    public Ramo(String ramo) {
        this.ramo = ramo;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
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
        hash += (ramo != null ? ramo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ramo)) {
            return false;
        }
        Ramo other = (Ramo) object;
        if ((this.ramo == null && other.ramo != null) || (this.ramo != null && !this.ramo.equals(other.ramo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Ramo[ ramo=" + ramo + " ]";
    }
    
}
