/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.entidades.globals.Logging;
import com.embuckets.controlcartera.ui.observable.ObservableDocumento;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
@Table(name = "CARATULA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caratula.findAll", query = "SELECT c FROM Caratula c"),
    @NamedQuery(name = "Caratula.findByIdpoliza", query = "SELECT c FROM Caratula c WHERE c.idpoliza = :idpoliza"),
    @NamedQuery(name = "Caratula.findByNombre", query = "SELECT c FROM Caratula c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Caratula.findByExtension", query = "SELECT c FROM Caratula c WHERE c.extension = :extension"),
    @NamedQuery(name = "Caratula.findByActualizado", query = "SELECT c FROM Caratula c WHERE c.actualizado = :actualizado")})
public class Caratula implements Serializable, ObservableDocumento {

    private static final Logger logger = LogManager.getLogger(Caratula.class);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPOLIZA")
    private Integer idpoliza;
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
    @JoinColumn(name = "IDPOLIZA", referencedColumnName = "IDPOLIZA", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Poliza poliza;

    /**
     *
     */
    public Caratula() {
    }

    /**
     *
     * @param file
     * @param poliza
     * @throws IOException
     */
    public Caratula(File file, Poliza poliza) throws IOException {
        this.poliza = poliza;
        this.idpoliza = poliza.getIdpoliza();
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
    public Integer getIdpoliza() {
        return idpoliza;
    }

    /**
     *
     * @param idpoliza
     */
    public void setIdpoliza(Integer idpoliza) {
        this.idpoliza = idpoliza;
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
    public Poliza getPoliza() {
        return poliza;
    }

    /**
     *
     * @param poliza
     */
    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpoliza != null ? idpoliza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caratula)) {
            return false;
        }
        Caratula other = (Caratula) object;
        if ((this.idpoliza == null && other.idpoliza != null) || (this.idpoliza != null && !this.idpoliza.equals(other.idpoliza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Caratula[ idpoliza=" + idpoliza + " ]";
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty archivoProperty() {
        return new SimpleStringProperty(nombre + extension);
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty tipoProperty() {
        return new SimpleStringProperty("");
    }

}
