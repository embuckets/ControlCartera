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
public class DocumentoAseguradoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private int idcliente;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "TIPODOCUMENTO")
    private String tipodocumento;

    public DocumentoAseguradoPK() {
    }

    public DocumentoAseguradoPK(int idcliente, String nombre, String tipodocumento) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.tipodocumento = tipodocumento;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcliente;
        hash += (nombre != null ? nombre.hashCode() : 0);
        hash += (tipodocumento != null ? tipodocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentoAseguradoPK)) {
            return false;
        }
        DocumentoAseguradoPK other = (DocumentoAseguradoPK) object;
        if (this.idcliente != other.idcliente) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        if ((this.tipodocumento == null && other.tipodocumento != null) || (this.tipodocumento != null && !this.tipodocumento.equals(other.tipodocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.DocumentoAseguradoPK[ idcliente=" + idcliente + ", nombre=" + nombre + ", tipodocumento=" + tipodocumento + " ]";
    }
    
}
