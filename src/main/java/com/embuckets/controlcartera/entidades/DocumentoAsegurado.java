/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.entidades.globals.Logging;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import com.embuckets.controlcartera.ui.observable.ObservableDocumento;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class DocumentoAsegurado implements Serializable, ObservableDocumento {

    private static final Logger logger = LogManager.getLogger(DocumentoAsegurado.class);
    
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @EmbeddedId
    protected DocumentoAseguradoPK documentoAseguradoPK;
    @Basic(optional = false)
    @Column(name = "EXTENSION")
    private String extension;
    @Basic(optional = false)
    @Lob
    @Column(name = "ARCHIVO")
    private byte[] archivo;
    @Column(name = "ACTUALIZADO")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime actualizado;
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asegurado asegurado;
    @JoinColumn(name = "TIPODOCUMENTO", referencedColumnName = "TIPODOCUMENTO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoDocumentoAsegurado tipoDocumentoAsegurado;

    /**
     *
     */
    public DocumentoAsegurado() {
        this.documentoAseguradoPK = new DocumentoAseguradoPK();
        this.tipoDocumentoAsegurado = new TipoDocumentoAsegurado();
    }

    /**
     * 
     * @param file arhivo a ser leido
     * @param tipoDocumentoAsegurado El tipo de documento
     * @throws IOException 
     */
    public DocumentoAsegurado(File file, String tipoDocumentoAsegurado) throws IOException {
        this.documentoAseguradoPK = new DocumentoAseguradoPK();
        String[] tokens = file.getName().split("\\.");
        this.documentoAseguradoPK.setNombre(tokens[0]);
        this.documentoAseguradoPK.setTipodocumento(tipoDocumentoAsegurado);
        this.extension = "." + tokens[1];
        this.actualizado = LocalDateTime.now();
        this.tipoDocumentoAsegurado = new TipoDocumentoAsegurado(tipoDocumentoAsegurado);
        try {
            this.archivo = Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

    /**
     *
     * @return
     */
    public DocumentoAseguradoPK getDocumentoAseguradoPK() {
        return documentoAseguradoPK;
    }

    /**
     *
     * @param documentoAseguradoPK
     */
    public void setDocumentoAseguradoPK(DocumentoAseguradoPK documentoAseguradoPK) {
        this.documentoAseguradoPK = documentoAseguradoPK;
    }

    /**
     *
     * @return
     */
    public String getExtension() {
        return extension;
    }

    /**
     *
     * @param extension
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     *
     * @return
     */
    public byte[] getArchivo() {
        return archivo;
    }

    /**
     *
     * @param archivo
     */
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getActualizado() {
        return actualizado;
    }

    /**
     *
     * @param actualizado
     */
    public void setActualizado(LocalDateTime actualizado) {
        this.actualizado = actualizado;
    }

    /**
     *
     * @return
     */
    public Asegurado getAsegurado() {
        return asegurado;
    }

    /**
     *
     * @param asegurado
     */
    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    /**
     *
     * @return
     */
    public TipoDocumentoAsegurado getTipoDocumentoAsegurado() {
        return tipoDocumentoAsegurado;
    }

    /**
     *
     * @param tipoDocumentoAsegurado
     */
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

    /**
     *
     * @return
     */
    public String getNombreArchivo() {
        return documentoAseguradoPK.getNombre();
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.DocumentoAsegurado[ documentoAseguradoPK=" + documentoAseguradoPK + " ]";
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty archivoProperty() {
        return new SimpleStringProperty(documentoAseguradoPK.getNombre() + extension);
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty tipoProperty() {
        return new SimpleStringProperty(tipoDocumentoAsegurado.getTipodocumento());
    }

}
