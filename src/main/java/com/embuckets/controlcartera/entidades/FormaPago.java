/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "FORMA_PAGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormaPago.findAll", query = "SELECT f FROM FormaPago f"),
    @NamedQuery(name = "FormaPago.findByFormapago", query = "SELECT f FROM FormaPago f WHERE f.formapago = :formapago")})
public class FormaPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "FORMAPAGO")
    private String formapago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formapago", fetch = FetchType.LAZY)
    private List<Poliza> polizaList;

    public FormaPago() {
    }

    public FormaPago(String formapago) {
        this.formapago = formapago;
    }

    public String getFormapago() {
        return formapago;
    }

    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    @XmlTransient
    public List<Poliza> getPolizaList() {
        return polizaList;
    }

    public void setPolizaList(List<Poliza> polizaList) {
        this.polizaList = polizaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formapago != null ? formapago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormaPago)) {
            return false;
        }
        FormaPago other = (FormaPago) object;
        if ((this.formapago == null && other.formapago != null) || (this.formapago != null && !this.formapago.equals(other.formapago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.FormaPago[ formapago=" + formapago + " ]";
    }
    
}
