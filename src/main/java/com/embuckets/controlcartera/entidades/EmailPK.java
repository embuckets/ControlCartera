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
public class EmailPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private int idcliente;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;

    /**
     *
     */
    public EmailPK() {
    }

    /**
     *
     * @param idcliente
     * @param email
     */
    public EmailPK(int idcliente, String email) {
        this.idcliente = idcliente;
        this.email = email;
    }

    /**
     *
     * @return
     */
    public int getIdcliente() {
        return idcliente;
    }

    /**
     *
     * @param idcliente
     */
    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcliente;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailPK)) {
            return false;
        }
        EmailPK other = (EmailPK) object;
        if (this.idcliente != other.idcliente) {
            return false;
        }
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.EmailPK[ idcliente=" + idcliente + ", email=" + email + " ]";
    }
    
}
