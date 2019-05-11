/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.entidades.globals.Logging;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.time.LocalDateTime;
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
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger logger = LogManager.getLogger(DocumentoRecibo.class);

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
    private byte[] archivo;
    @Column(name = "ACTUALIZADO")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime actualizado;
    @JoinColumn(name = "IDRECIBO", referencedColumnName = "IDRECIBO", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Recibo recibo;

    /**
     *
     */
    public DocumentoRecibo() {
    }

    /**
     * Constructor
     * @param file Archivo a ser leido
     * @param recibo recibo a cual pertence este documento
     * @throws IOException cuando hay error al leer el archivo
     */
    public DocumentoRecibo(File file, Recibo recibo) throws IOException {
        this.recibo = recibo;
        this.idrecibo = recibo.getIdrecibo();
        String[] tokens = file.getName().split("\\.");
        this.nombre = tokens[0];
        this.extension = "." + tokens[1];
        this.actualizado = LocalDateTime.now();
        try {
            this.archivo = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            logger.error(Logging.Exception_MESSAGE, e);
            throw e;
        }
    }

    /**
     *
     * @return
     */
    public Integer getIdrecibo() {
        return idrecibo;
    }

    /**
     *
     * @param idrecibo
     */
    public void setIdrecibo(Integer idrecibo) {
        this.idrecibo = idrecibo;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public Recibo getRecibo() {
        return recibo;
    }

    /**
     *
     * @param recibo
     */
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
