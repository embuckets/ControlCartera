/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "POLIZA_AUTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PolizaAuto.findAll", query = "SELECT p FROM PolizaAuto p"),
    @NamedQuery(name = "PolizaAuto.findByIdpoliza", query = "SELECT p FROM PolizaAuto p WHERE p.idpoliza = :idpoliza")})
public class PolizaAuto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPOLIZA")
    private Integer idpoliza;
    @JoinColumn(name = "IDPOLIZA", referencedColumnName = "IDPOLIZA", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Poliza poliza;
    @JoinColumn(name = "SUMAASEGURADAAUTO", referencedColumnName = "SUMAASEGURADAAUTO")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private SumaAseguradaAuto sumaaseguradaauto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpoliza", fetch = FetchType.LAZY)
    private List<Auto> autoList;

    public PolizaAuto() {
        this.sumaaseguradaauto = new SumaAseguradaAuto();
        this.autoList = new ArrayList<>();
    }

    public PolizaAuto(Integer idpoliza) {
        this.idpoliza = idpoliza;
        this.sumaaseguradaauto = new SumaAseguradaAuto();
        this.autoList = new ArrayList<>();
    }

    public Integer getIdpoliza() {
        return idpoliza;
    }

    public void setIdpoliza(Integer idpoliza) {
        this.idpoliza = idpoliza;
    }

    public Poliza getPoliza() {
        return poliza;
    }

    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
    }

    public SumaAseguradaAuto getSumaaseguradaauto() {
        return sumaaseguradaauto;
    }

    public void setSumaaseguradaauto(SumaAseguradaAuto sumaaseguradaauto) {
        this.sumaaseguradaauto = sumaaseguradaauto;
    }

    @XmlTransient
    public List<Auto> getAutoList() {
        return autoList;
    }

    public void setAutoList(List<Auto> autoList) {
        this.autoList = autoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpoliza != null ? idpoliza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PolizaAuto)) {
            return false;
        }
        PolizaAuto other = (PolizaAuto) object;
        if ((this.idpoliza == null && other.idpoliza != null) || (this.idpoliza != null && !this.idpoliza.equals(other.idpoliza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.PolizaAuto[ idpoliza=" + idpoliza + " ]";
    }
    
}
