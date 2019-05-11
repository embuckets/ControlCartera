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
@Table(name = "ASEGURADORA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aseguradora.findAll", query = "SELECT a FROM Aseguradora a"),
    @NamedQuery(name = "Aseguradora.findByAseguradora", query = "SELECT a FROM Aseguradora a WHERE a.aseguradora = :aseguradora")})
public class Aseguradora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ASEGURADORA")
    private String aseguradora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.LAZY)
    private List<Poliza> polizaList;

    /**
     *
     */
    public Aseguradora() {
    }

    /**
     *
     * @param aseguradora
     */
    public Aseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    /**
     *
     * @return
     */
    public String getAseguradora() {
        return aseguradora;
    }

    /**
     *
     * @param aseguradora
     */
    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Poliza> getPolizaList() {
        return polizaList;
    }

    /**
     *
     * @param polizaList
     */
    public void setPolizaList(List<Poliza> polizaList) {
        this.polizaList = polizaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aseguradora != null ? aseguradora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aseguradora)) {
            return false;
        }
        Aseguradora other = (Aseguradora) object;
        if ((this.aseguradora == null && other.aseguradora != null) || (this.aseguradora != null && !this.aseguradora.equals(other.aseguradora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Aseguradora[ aseguradora=" + aseguradora + " ]";
    }
    
}
