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
@Table(name = "COBRANZA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cobranza.findAll", query = "SELECT c FROM Cobranza c"),
    @NamedQuery(name = "Cobranza.findByCobranza", query = "SELECT c FROM Cobranza c WHERE c.cobranza = :cobranza")})
public class Cobranza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COBRANZA")
    private String cobranza;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cobranza", fetch = FetchType.LAZY)
    private List<Recibo> reciboList;

    /**
     *
     */
    public Cobranza() {
    }

    /**
     *
     * @param cobranza
     */
    public Cobranza(String cobranza) {
        this.cobranza = cobranza;
    }

    /**
     *
     * @return
     */
    public String getCobranza() {
        return cobranza;
    }

    /**
     *
     * @param cobranza
     */
    public void setCobranza(String cobranza) {
        this.cobranza = cobranza;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Recibo> getReciboList() {
        return reciboList;
    }

    /**
     *
     * @param reciboList
     */
    public void setReciboList(List<Recibo> reciboList) {
        this.reciboList = reciboList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cobranza != null ? cobranza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cobranza)) {
            return false;
        }
        Cobranza other = (Cobranza) object;
        if ((this.cobranza == null && other.cobranza != null) || (this.cobranza != null && !this.cobranza.equals(other.cobranza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Cobranza[ cobranza=" + cobranza + " ]";
    }
    
}
