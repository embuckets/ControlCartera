/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.ui.observable.ObservableEmail;
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
@Table(name = "EMAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e"),
    @NamedQuery(name = "Email.findByIdcliente", query = "SELECT e FROM Email e WHERE e.emailPK.idcliente = :idcliente"),
    @NamedQuery(name = "Email.findByEmail", query = "SELECT e FROM Email e WHERE e.emailPK.email = :email")})
public class Email implements Serializable, ObservableEmail {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmailPK emailPK;
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asegurado asegurado;
    @JoinColumn(name = "TIPOEMAIL", referencedColumnName = "TIPOEMAIL")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoEmail tipoemail;
    @Column(name = "NOTIFICAR")
    private boolean notificar;

    public Email() {
    }

    public Email(EmailPK emailPK) {
        this.emailPK = emailPK;
    }

    public Email(int idcliente, String email) {
        this.emailPK = new EmailPK(idcliente, email);
    }

    public Email(String email) {
        this.emailPK = new EmailPK();
        this.emailPK.setEmail(email);
    }

    public EmailPK getEmailPK() {
        return emailPK;
    }

    public void setEmailPK(EmailPK emailPK) {
        this.emailPK = emailPK;
    }

    public Asegurado getAsegurado() {
        return asegurado;
    }

    public void setAsegurado(Asegurado asegurado) {
        this.asegurado = asegurado;
    }

    public TipoEmail getTipoemail() {
        return tipoemail;
    }

    public void setTipoemail(TipoEmail tipoemail) {
        this.tipoemail = tipoemail;
    }

    public boolean isNotificar() {
        return notificar;
    }

    public void setNotificar(boolean notificar) {
        this.notificar = notificar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emailPK != null ? emailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.emailPK == null && other.emailPK != null) || (this.emailPK != null && !this.emailPK.equals(other.emailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Email[ emailPK=" + emailPK + " ]";
    }

    @Override
    public StringProperty emailProperty() {
        return new SimpleStringProperty(this.emailPK.getEmail());
    }

    @Override
    public StringProperty tipoProperty() {
        return new SimpleStringProperty(this.tipoemail.getTipoemail());
    }

    @Override
    public StringProperty notificarProperty() {
        return notificar ? new SimpleStringProperty("SI") : new SimpleStringProperty("NO");
    }

}
