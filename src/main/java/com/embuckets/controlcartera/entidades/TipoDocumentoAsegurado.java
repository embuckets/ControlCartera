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
@Table(name = "TIPO_DOCUMENTO_ASEGURADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDocumentoAsegurado.findAll", query = "SELECT t FROM TipoDocumentoAsegurado t"),
    @NamedQuery(name = "TipoDocumentoAsegurado.findByTipodocumento", query = "SELECT t FROM TipoDocumentoAsegurado t WHERE t.tipodocumento = :tipodocumento")})
public class TipoDocumentoAsegurado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPODOCUMENTO")
    private String tipodocumento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumentoAsegurado", fetch = FetchType.LAZY)
    private List<DocumentoAsegurado> documentoAseguradoList;

    /**
     *
     */
    public TipoDocumentoAsegurado() {
    }

    /**
     *
     * @param tipodocumento
     */
    public TipoDocumentoAsegurado(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    /**
     *
     * @return
     */
    public String getTipodocumento() {
        return tipodocumento;
    }

    /**
     *
     * @param tipodocumento
     */
    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<DocumentoAsegurado> getDocumentoAseguradoList() {
        return documentoAseguradoList;
    }

    /**
     *
     * @param documentoAseguradoList
     */
    public void setDocumentoAseguradoList(List<DocumentoAsegurado> documentoAseguradoList) {
        this.documentoAseguradoList = documentoAseguradoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipodocumento != null ? tipodocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDocumentoAsegurado)) {
            return false;
        }
        TipoDocumentoAsegurado other = (TipoDocumentoAsegurado) object;
        if ((this.tipodocumento == null && other.tipodocumento != null) || (this.tipodocumento != null && !this.tipodocumento.equals(other.tipodocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.TipoDocumentoAsegurado[ tipodocumento=" + tipodocumento + " ]";
    }
    
}
