/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "POLIZA_GMM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PolizaGmm.findAll", query = "SELECT p FROM PolizaGmm p"),
    @NamedQuery(name = "PolizaGmm.findByIdpoliza", query = "SELECT p FROM PolizaGmm p WHERE p.idpoliza = :idpoliza"),
    @NamedQuery(name = "PolizaGmm.findByDeducible", query = "SELECT p FROM PolizaGmm p WHERE p.deducible = :deducible"),
    @NamedQuery(name = "PolizaGmm.findBySumaasegurada", query = "SELECT p FROM PolizaGmm p WHERE p.sumaasegurada = :sumaasegurada"),
    @NamedQuery(name = "PolizaGmm.findByCoaseguro", query = "SELECT p FROM PolizaGmm p WHERE p.coaseguro = :coaseguro")})
public class PolizaGmm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPOLIZA")
    private Integer idpoliza;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "DEDUCIBLE")
    private BigDecimal deducible;
    @Basic(optional = false)
    @Column(name = "SUMAASEGURADA")
    private String sumaasegurada;
    @Basic(optional = false)
    @Column(name = "COASEGURO")
    private short coaseguro;
    @ManyToMany(mappedBy = "polizaGmmList", fetch = FetchType.LAZY)
    private List<Cliente> clienteList;
    @JoinColumn(name = "SUMAASEGURADAMONDEDA", referencedColumnName = "MONEDA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Moneda sumaaseguradamondeda;
    @JoinColumn(name = "DEDUCIBLEMONEDA", referencedColumnName = "MONEDA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Moneda deduciblemoneda;
    @JoinColumn(name = "IDPOLIZA", referencedColumnName = "IDPOLIZA", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Poliza poliza;

    public PolizaGmm() {
    }

    public PolizaGmm(Integer idpoliza) {
        this.idpoliza = idpoliza;
    }

    public PolizaGmm(Integer idpoliza, BigDecimal deducible, String sumaasegurada, short coaseguro) {
        this.idpoliza = idpoliza;
        this.deducible = deducible;
        this.sumaasegurada = sumaasegurada;
        this.coaseguro = coaseguro;
    }

    public Integer getIdpoliza() {
        return idpoliza;
    }

    public void setIdpoliza(Integer idpoliza) {
        this.idpoliza = idpoliza;
    }

    public BigDecimal getDeducible() {
        return deducible;
    }

    public void setDeducible(BigDecimal deducible) {
        this.deducible = deducible;
    }

    public String getSumaasegurada() {
        return sumaasegurada;
    }

    public void setSumaasegurada(String sumaasegurada) {
        this.sumaasegurada = sumaasegurada;
    }

    public short getCoaseguro() {
        return coaseguro;
    }

    public void setCoaseguro(short coaseguro) {
        this.coaseguro = coaseguro;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Moneda getSumaaseguradamondeda() {
        return sumaaseguradamondeda;
    }

    public void setSumaaseguradamondeda(Moneda sumaaseguradamondeda) {
        this.sumaaseguradamondeda = sumaaseguradamondeda;
    }

    public Moneda getDeduciblemoneda() {
        return deduciblemoneda;
    }

    public void setDeduciblemoneda(Moneda deduciblemoneda) {
        this.deduciblemoneda = deduciblemoneda;
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
        if (!(object instanceof PolizaGmm)) {
            return false;
        }
        PolizaGmm other = (PolizaGmm) object;
        if ((this.idpoliza == null && other.idpoliza != null) || (this.idpoliza != null && !this.idpoliza.equals(other.idpoliza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.PolizaGmm[ idpoliza=" + idpoliza + " ]";
    }
    
}
