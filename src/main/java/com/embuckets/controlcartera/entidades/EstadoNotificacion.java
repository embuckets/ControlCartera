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
@Table(name = "ESTADO_NOTIFICACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoNotificacion.findAll", query = "SELECT e FROM EstadoNotificacion e"),
    @NamedQuery(name = "EstadoNotificacion.findByEstadonotificacion", query = "SELECT e FROM EstadoNotificacion e WHERE e.estadonotificacion = :estadonotificacion")})
public class EstadoNotificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ESTADONOTIFICACION")
    private String estadonotificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadonotificacion", fetch = FetchType.LAZY)
    private List<NotificacionCumple> notificacionCumpleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadonotificacion", fetch = FetchType.LAZY)
    private List<NotificacionRecibo> notificacionReciboList;

    /**
     *
     */
    public EstadoNotificacion() {
    }

    /**
     *
     * @param estadonotificacion
     */
    public EstadoNotificacion(String estadonotificacion) {
        this.estadonotificacion = estadonotificacion;
    }

    /**
     *
     * @return
     */
    public String getEstadonotificacion() {
        return estadonotificacion;
    }

    /**
     *
     * @param estadonotificacion
     */
    public void setEstadonotificacion(String estadonotificacion) {
        this.estadonotificacion = estadonotificacion;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<NotificacionCumple> getNotificacionCumpleList() {
        return notificacionCumpleList;
    }

    /**
     *
     * @param notificacionCumpleList
     */
    public void setNotificacionCumpleList(List<NotificacionCumple> notificacionCumpleList) {
        this.notificacionCumpleList = notificacionCumpleList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<NotificacionRecibo> getNotificacionReciboList() {
        return notificacionReciboList;
    }

    /**
     *
     * @param notificacionReciboList
     */
    public void setNotificacionReciboList(List<NotificacionRecibo> notificacionReciboList) {
        this.notificacionReciboList = notificacionReciboList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadonotificacion != null ? estadonotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoNotificacion)) {
            return false;
        }
        EstadoNotificacion other = (EstadoNotificacion) object;
        if ((this.estadonotificacion == null && other.estadonotificacion != null) || (this.estadonotificacion != null && !this.estadonotificacion.equals(other.estadonotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.EstadoNotificacion[ estadonotificacion=" + estadonotificacion + " ]";
    }
    
}
