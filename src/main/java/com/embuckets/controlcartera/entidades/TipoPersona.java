/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "TIPO_PERSONA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPersona.findAll", query = "SELECT t FROM TipoPersona t"),
    @NamedQuery(name = "TipoPersona.findByTipopersona", query = "SELECT t FROM TipoPersona t WHERE t.tipopersona = :tipopersona")})
public class TipoPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPOPERSONA")
    private String tipopersona;
    @OneToMany(mappedBy = "tipopersona", fetch = FetchType.LAZY)
    private List<Asegurado> aseguradoList;

    /**
     *
     */
    public TipoPersona() {
    }

    /**
     *
     * @param tipopersona
     */
    public TipoPersona(String tipopersona) {
        this.tipopersona = tipopersona;
    }

    /**
     *
     * @return
     */
    public String getTipopersona() {
        return tipopersona;
    }

    /**
     *
     * @param tipopersona
     */
    public void setTipopersona(String tipopersona) {
        this.tipopersona = tipopersona;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Asegurado> getAseguradoList() {
        return aseguradoList;
    }

    /**
     *
     * @param aseguradoList
     */
    public void setAseguradoList(List<Asegurado> aseguradoList) {
        this.aseguradoList = aseguradoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipopersona != null ? tipopersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPersona)) {
            return false;
        }
        TipoPersona other = (TipoPersona) object;
        if ((this.tipopersona == null && other.tipopersona != null) || (this.tipopersona != null && !this.tipopersona.equals(other.tipopersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.TipoPersona[ tipopersona=" + tipopersona + " ]";
    }
    
}
