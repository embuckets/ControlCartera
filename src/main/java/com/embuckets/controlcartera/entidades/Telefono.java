/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.ui.observable.ObservableTelefono;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "TELEFONO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telefono.findAll", query = "SELECT t FROM Telefono t"),
    @NamedQuery(name = "Telefono.findByIdcliente", query = "SELECT t FROM Telefono t WHERE t.telefonoPK.idcliente = :idcliente"),
    @NamedQuery(name = "Telefono.findByTelefono", query = "SELECT t FROM Telefono t WHERE t.telefonoPK.telefono = :telefono"),
    @NamedQuery(name = "Telefono.findByExtension", query = "SELECT t FROM Telefono t WHERE t.extension = :extension")})
public class Telefono implements Serializable, ObservableTelefono {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TelefonoPK telefonoPK;
    @Column(name = "EXTENSION")
    private String extension;
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asegurado asegurado;
    @JoinColumn(name = "TIPOTELEFONO", referencedColumnName = "TIPOTELEFONO")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoTelefono tipotelefono;

    public Telefono() {
    }

    public Telefono(TelefonoPK telefonoPK) {
        this.telefonoPK = telefonoPK;
    }

    public Telefono(int idcliente, String telefono) {
        this.telefonoPK = new TelefonoPK(idcliente, telefono);
    }

    public Telefono(String telefono) {
        this.telefonoPK = new TelefonoPK();
        this.telefonoPK.setTelefono(telefono);
    }

    public TelefonoPK getTelefonoPK() {
        return telefonoPK;
    }

    public void setTelefonoPK(TelefonoPK telefonoPK) {
        this.telefonoPK = telefonoPK;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Asegurado getAsegurado() {
        return asegurado;
    }

    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    public TipoTelefono getTipotelefono() {
        return tipotelefono;
    }

    public void setTipotelefono(TipoTelefono tipotelefono) {
        this.tipotelefono = tipotelefono;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (telefonoPK != null ? telefonoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telefono)) {
            return false;
        }
        Telefono other = (Telefono) object;
        if ((this.telefonoPK == null && other.telefonoPK != null) || (this.telefonoPK != null && !this.telefonoPK.equals(other.telefonoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Telefono[ telefonoPK=" + telefonoPK + " ]";
    }

    @Override
    public StringProperty telefonoProperty() {
        return new SimpleStringProperty(telefonoPK.getTelefono());
    }

    @Override
    public StringProperty extensionProperty() {
        return new SimpleStringProperty(extension);
    }

    @Override
    public StringProperty tipoProperty() {
        return new SimpleStringProperty(tipotelefono.getTipotelefono());
    }

}
