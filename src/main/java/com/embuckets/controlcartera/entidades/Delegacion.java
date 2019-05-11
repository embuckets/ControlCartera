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
@Table(name = "DELEGACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Delegacion.findAll", query = "SELECT d FROM Delegacion d"),
    @NamedQuery(name = "Delegacion.findByDelegacion", query = "SELECT d FROM Delegacion d WHERE d.delegacion = :delegacion")})
public class Delegacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DELEGACION")
    private String delegacion;
    @OneToMany(mappedBy = "delegacion", fetch = FetchType.LAZY)
    private List<Domicilio> domicilioList;

    /**
     *
     */
    public Delegacion() {
    }

    /**
     *
     * @param delegacion
     */
    public Delegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    /**
     *
     * @return
     */
    public String getDelegacion() {
        return delegacion;
    }

    /**
     *
     * @param delegacion
     */
    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Domicilio> getDomicilioList() {
        return domicilioList;
    }

    /**
     *
     * @param domicilioList
     */
    public void setDomicilioList(List<Domicilio> domicilioList) {
        this.domicilioList = domicilioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (delegacion != null ? delegacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Delegacion)) {
            return false;
        }
        Delegacion other = (Delegacion) object;
        if ((this.delegacion == null && other.delegacion != null) || (this.delegacion != null && !this.delegacion.equals(other.delegacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Delegacion[ delegacion=" + delegacion + " ]";
    }
    
}
