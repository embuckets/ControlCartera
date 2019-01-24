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
@Table(name = "TIPO_TELEFONO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTelefono.findAll", query = "SELECT t FROM TipoTelefono t"),
    @NamedQuery(name = "TipoTelefono.findByTipotelefono", query = "SELECT t FROM TipoTelefono t WHERE t.tipotelefono = :tipotelefono")})
public class TipoTelefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPOTELEFONO")
    private String tipotelefono;
    @OneToMany(mappedBy = "tipotelefono", fetch = FetchType.LAZY)
    private List<Telefono> telefonoList;

    public TipoTelefono() {
    }

    public TipoTelefono(String tipotelefono) {
        this.tipotelefono = tipotelefono;
    }

    public String getTipotelefono() {
        return tipotelefono;
    }

    public void setTipotelefono(String tipotelefono) {
        this.tipotelefono = tipotelefono;
    }

    @XmlTransient
    public List<Telefono> getTelefonoList() {
        return telefonoList;
    }

    public void setTelefonoList(List<Telefono> telefonoList) {
        this.telefonoList = telefonoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipotelefono != null ? tipotelefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTelefono)) {
            return false;
        }
        TipoTelefono other = (TipoTelefono) object;
        if ((this.tipotelefono == null && other.tipotelefono != null) || (this.tipotelefono != null && !this.tipotelefono.equals(other.tipotelefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.TipoTelefono[ tipotelefono=" + tipotelefono + " ]";
    }
    
}
