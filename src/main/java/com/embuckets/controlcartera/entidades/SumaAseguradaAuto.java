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
@Table(name = "SUMA_ASEGURADA_AUTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SumaAseguradaAuto.findAll", query = "SELECT s FROM SumaAseguradaAuto s"),
    @NamedQuery(name = "SumaAseguradaAuto.findBySumaaseguradaauto", query = "SELECT s FROM SumaAseguradaAuto s WHERE s.sumaaseguradaauto = :sumaaseguradaauto")})
public class SumaAseguradaAuto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SUMAASEGURADAAUTO")
    private String sumaaseguradaauto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sumaaseguradaauto", fetch = FetchType.LAZY)
    private List<PolizaAuto> polizaAutoList;

    public SumaAseguradaAuto() {
    }

    public SumaAseguradaAuto(String sumaaseguradaauto) {
        this.sumaaseguradaauto = sumaaseguradaauto;
    }

    public String getSumaaseguradaauto() {
        return sumaaseguradaauto;
    }

    public void setSumaaseguradaauto(String sumaaseguradaauto) {
        this.sumaaseguradaauto = sumaaseguradaauto;
    }

    @XmlTransient
    public List<PolizaAuto> getPolizaAutoList() {
        return polizaAutoList;
    }

    public void setPolizaAutoList(List<PolizaAuto> polizaAutoList) {
        this.polizaAutoList = polizaAutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sumaaseguradaauto != null ? sumaaseguradaauto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SumaAseguradaAuto)) {
            return false;
        }
        SumaAseguradaAuto other = (SumaAseguradaAuto) object;
        if ((this.sumaaseguradaauto == null && other.sumaaseguradaauto != null) || (this.sumaaseguradaauto != null && !this.sumaaseguradaauto.equals(other.sumaaseguradaauto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.SumaAseguradaAuto[ sumaaseguradaauto=" + sumaaseguradaauto + " ]";
    }
    
}
