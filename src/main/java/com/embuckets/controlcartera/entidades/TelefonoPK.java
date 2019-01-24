/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author emilio
 */
@Embeddable
public class TelefonoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private int idcliente;
    @Basic(optional = false)
    @Column(name = "TELEFONO")
    private String telefono;

    public TelefonoPK() {
    }

    public TelefonoPK(int idcliente, String telefono) {
        this.idcliente = idcliente;
        this.telefono = telefono;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcliente;
        hash += (telefono != null ? telefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TelefonoPK)) {
            return false;
        }
        TelefonoPK other = (TelefonoPK) object;
        if (this.idcliente != other.idcliente) {
            return false;
        }
        if ((this.telefono == null && other.telefono != null) || (this.telefono != null && !this.telefono.equals(other.telefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.TelefonoPK[ idcliente=" + idcliente + ", telefono=" + telefono + " ]";
    }
    
}
