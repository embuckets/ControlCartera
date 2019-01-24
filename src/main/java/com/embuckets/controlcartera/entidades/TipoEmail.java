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
@Table(name = "TIPO_EMAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEmail.findAll", query = "SELECT t FROM TipoEmail t"),
    @NamedQuery(name = "TipoEmail.findByTipoemail", query = "SELECT t FROM TipoEmail t WHERE t.tipoemail = :tipoemail")})
public class TipoEmail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPOEMAIL")
    private String tipoemail;
    @OneToMany(mappedBy = "tipoemail", fetch = FetchType.LAZY)
    private List<Email> emailList;

    public TipoEmail() {
    }

    public TipoEmail(String tipoemail) {
        this.tipoemail = tipoemail;
    }

    public String getTipoemail() {
        return tipoemail;
    }

    public void setTipoemail(String tipoemail) {
        this.tipoemail = tipoemail;
    }

    @XmlTransient
    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoemail != null ? tipoemail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEmail)) {
            return false;
        }
        TipoEmail other = (TipoEmail) object;
        if ((this.tipoemail == null && other.tipoemail != null) || (this.tipoemail != null && !this.tipoemail.equals(other.tipoemail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.TipoEmail[ tipoemail=" + tipoemail + " ]";
    }
    
}
