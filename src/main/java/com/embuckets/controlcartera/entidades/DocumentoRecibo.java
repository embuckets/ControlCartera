/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "DOCUMENTO_RECIBO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoRecibo.findAll", query = "SELECT d FROM DocumentoRecibo d"),
    @NamedQuery(name = "DocumentoRecibo.findByIdrecibo", query = "SELECT d FROM DocumentoRecibo d WHERE d.idrecibo = :idrecibo"),
    @NamedQuery(name = "DocumentoRecibo.findByNombre", query = "SELECT d FROM DocumentoRecibo d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "DocumentoRecibo.findByExtension", query = "SELECT d FROM DocumentoRecibo d WHERE d.extension = :extension"),
    @NamedQuery(name = "DocumentoRecibo.findByActualizado", query = "SELECT d FROM DocumentoRecibo d WHERE d.actualizado = :actualizado")})
public class DocumentoRecibo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDRECIBO")
    private Integer idrecibo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "EXTENSION")
    private String extension;
    @Basic(optional = false)
    @Lob
    @Column(name = "ARCHIVO")
    private Serializable archivo;
    @Column(name = "ACTUALIZADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @JoinColumn(name = "IDRECIBO", referencedColumnName = "IDRECIBO", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Recibo recibo;

    public DocumentoRecibo() {
    }

    public DocumentoRecibo(Integer idrecibo) {
        this.idrecibo = idrecibo;
    }

    public DocumentoRecibo(Integer idrecibo, String nombre, String extension, Serializable archivo) {
        this.idrecibo = idrecibo;
        this.nombre = nombre;
        this.extension = extension;
        this.archivo = archivo;
    }

    public Integer getIdrecibo() {
        return idrecibo;
    }

    public void setIdrecibo(Integer idrecibo) {
        this.idrecibo = idrecibo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Serializable getArchivo() {
        return archivo;
    }

    public void setArchivo(Serializable archivo) {
        this.archivo = archivo;
    }

    public Date getActualizado() {
        return actualizado;
    }

    public void setActualizado(Date actualizado) {
        this.actualizado = actualizado;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrecibo != null ? idrecibo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentoRecibo)) {
            return false;
        }
        DocumentoRecibo other = (DocumentoRecibo) object;
        if ((this.idrecibo == null && other.idrecibo != null) || (this.idrecibo != null && !this.idrecibo.equals(other.idrecibo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.DocumentoRecibo[ idrecibo=" + idrecibo + " ]";
    }
    
}
