/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.ui.observable.ObservableArchivo;
import com.embuckets.controlcartera.ui.observable.ObservableDocumentoAsegurado;
import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "DOCUMENTO_ASEGURADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoAsegurado.findAll", query = "SELECT d FROM DocumentoAsegurado d"),
    @NamedQuery(name = "DocumentoAsegurado.findByIdcliente", query = "SELECT d FROM DocumentoAsegurado d WHERE d.documentoAseguradoPK.idcliente = :idcliente"),
    @NamedQuery(name = "DocumentoAsegurado.findByNombre", query = "SELECT d FROM DocumentoAsegurado d WHERE d.documentoAseguradoPK.nombre = :nombre"),
    @NamedQuery(name = "DocumentoAsegurado.findByExtension", query = "SELECT d FROM DocumentoAsegurado d WHERE d.extension = :extension"),
    @NamedQuery(name = "DocumentoAsegurado.findByTipodocumento", query = "SELECT d FROM DocumentoAsegurado d WHERE d.documentoAseguradoPK.tipodocumento = :tipodocumento"),
    @NamedQuery(name = "DocumentoAsegurado.findByActualizado", query = "SELECT d FROM DocumentoAsegurado d WHERE d.actualizado = :actualizado")})
public class DocumentoAsegurado implements Serializable, ObservableDocumentoAsegurado {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocumentoAseguradoPK documentoAseguradoPK;
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
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asegurado asegurado;
    @JoinColumn(name = "TIPODOCUMENTO", referencedColumnName = "TIPODOCUMENTO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoDocumentoAsegurado tipoDocumentoAsegurado;
    private String path;

    public DocumentoAsegurado() {
    }

    public DocumentoAsegurado(DocumentoAseguradoPK documentoAseguradoPK) {
        this.documentoAseguradoPK = documentoAseguradoPK;
    }

    public DocumentoAsegurado(DocumentoAseguradoPK documentoAseguradoPK, String extension, Serializable archivo) {
        this.documentoAseguradoPK = documentoAseguradoPK;
        this.extension = extension;
        this.archivo = archivo;
    }

    public DocumentoAsegurado(int idcliente, String nombre, String tipodocumento) {
        this.documentoAseguradoPK = new DocumentoAseguradoPK(idcliente, nombre, tipodocumento);
    }

    public DocumentoAseguradoPK getDocumentoAseguradoPK() {
        return documentoAseguradoPK;
    }

    public void setDocumentoAseguradoPK(DocumentoAseguradoPK documentoAseguradoPK) {
        this.documentoAseguradoPK = documentoAseguradoPK;
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

    public Asegurado getAsegurado() {
        return asegurado;
    }

    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    public TipoDocumentoAsegurado getTipoDocumentoAsegurado() {
        return tipoDocumentoAsegurado;
    }

    public void setTipoDocumentoAsegurado(TipoDocumentoAsegurado tipoDocumentoAsegurado) {
        this.tipoDocumentoAsegurado = tipoDocumentoAsegurado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentoAseguradoPK != null ? documentoAseguradoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentoAsegurado)) {
            return false;
        }
        DocumentoAsegurado other = (DocumentoAsegurado) object;
        if ((this.documentoAseguradoPK == null && other.documentoAseguradoPK != null) || (this.documentoAseguradoPK != null && !this.documentoAseguradoPK.equals(other.documentoAseguradoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.DocumentoAsegurado[ documentoAseguradoPK=" + documentoAseguradoPK + " ]";
    }

    @Override
    public StringProperty archivoProperty() {
        return new SimpleStringProperty(documentoAseguradoPK.getNombre() + extension);
    }

    @Override
    public StringProperty tipoProperty() {
        return new SimpleStringProperty(tipoDocumentoAsegurado.getTipodocumento());
    }

}
