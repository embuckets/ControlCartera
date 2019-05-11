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
@Table(name = "MONEDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Moneda.findAll", query = "SELECT m FROM Moneda m"),
    @NamedQuery(name = "Moneda.findByMoneda", query = "SELECT m FROM Moneda m WHERE m.moneda = :moneda")})
public class Moneda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MONEDA")
    private String moneda;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "primamoneda", fetch = FetchType.LAZY)
    private List<Poliza> polizaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sumaaseguradamoneda", fetch = FetchType.LAZY)
    private List<PolizaVida> polizaVidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sumaaseguradamondeda", fetch = FetchType.LAZY)
    private List<PolizaGmm> polizaGmmList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deduciblemoneda", fetch = FetchType.LAZY)
    private List<PolizaGmm> polizaGmmList1;

    /**
     *
     */
    public Moneda() {
    }

    /**
     *
     * @param moneda
     */
    public Moneda(String moneda) {
        this.moneda = moneda;
    }

    /**
     *
     * @return
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     *
     * @param moneda
     */
    public void setMoneda(String moneda) {
        this.moneda = moneda;
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

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PolizaVida> getPolizaVidaList() {
        return polizaVidaList;
    }

    /**
     *
     * @param polizaVidaList
     */
    public void setPolizaVidaList(List<PolizaVida> polizaVidaList) {
        this.polizaVidaList = polizaVidaList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PolizaGmm> getPolizaGmmList() {
        return polizaGmmList;
    }

    /**
     *
     * @param polizaGmmList
     */
    public void setPolizaGmmList(List<PolizaGmm> polizaGmmList) {
        this.polizaGmmList = polizaGmmList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PolizaGmm> getPolizaGmmList1() {
        return polizaGmmList1;
    }

    /**
     *
     * @param polizaGmmList1
     */
    public void setPolizaGmmList1(List<PolizaGmm> polizaGmmList1) {
        this.polizaGmmList1 = polizaGmmList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moneda != null ? moneda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Moneda)) {
            return false;
        }
        Moneda other = (Moneda) object;
        if ((this.moneda == null && other.moneda != null) || (this.moneda != null && !this.moneda.equals(other.moneda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Moneda[ moneda=" + moneda + " ]";
    }
    
}
