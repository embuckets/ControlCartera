/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "POLIZA_VIDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PolizaVida.findAll", query = "SELECT p FROM PolizaVida p"),
    @NamedQuery(name = "PolizaVida.findByIdpoliza", query = "SELECT p FROM PolizaVida p WHERE p.idpoliza = :idpoliza"),
    @NamedQuery(name = "PolizaVida.findBySumaasegurada", query = "SELECT p FROM PolizaVida p WHERE p.sumaasegurada = :sumaasegurada")})
public class PolizaVida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPOLIZA")
    private Integer idpoliza;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "SUMAASEGURADA")
    private BigDecimal sumaasegurada;
    @ManyToMany(mappedBy = "polizaVidaList", fetch = FetchType.LAZY)
    private List<Cliente> clienteList;
    @JoinColumn(name = "SUMAASEGURADAMONEDA", referencedColumnName = "MONEDA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Moneda sumaaseguradamoneda;
    @JoinColumn(name = "IDPOLIZA", referencedColumnName = "IDPOLIZA", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Poliza poliza;

    public PolizaVida() {
        this.clienteList = new ArrayList<>();
    }

    public PolizaVida(Integer idpoliza) {
        this.idpoliza = idpoliza;
        this.clienteList = new ArrayList<>();
    }

    public PolizaVida(Integer idpoliza, BigDecimal sumaasegurada) {
        this.idpoliza = idpoliza;
        this.sumaasegurada = sumaasegurada;
        this.clienteList = new ArrayList<>();
    }

    public Integer getIdpoliza() {
        return idpoliza;
    }

    public void setIdpoliza(Integer idpoliza) {
        this.idpoliza = idpoliza;
    }

    public BigDecimal getSumaasegurada() {
        return sumaasegurada;
    }

    public void setSumaasegurada(BigDecimal sumaasegurada) {
        this.sumaasegurada = sumaasegurada;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Moneda getSumaaseguradamoneda() {
        return sumaaseguradamoneda;
    }

    public void setSumaaseguradamoneda(Moneda sumaaseguradamoneda) {
        this.sumaaseguradamoneda = sumaaseguradamoneda;
    }

    public Poliza getPoliza() {
        return poliza;
    }

    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
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
        if (!(object instanceof PolizaVida)) {
            return false;
        }
        PolizaVida other = (PolizaVida) object;
        if ((this.idpoliza == null && other.idpoliza != null) || (this.idpoliza != null && !this.idpoliza.equals(other.idpoliza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.PolizaVida[ idpoliza=" + idpoliza + " ]";
    }

}
