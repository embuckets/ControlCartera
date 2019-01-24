/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "NOTIFICACION_RECIBO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionRecibo.findAll", query = "SELECT n FROM NotificacionRecibo n"),
    @NamedQuery(name = "NotificacionRecibo.findByIdrecibo", query = "SELECT n FROM NotificacionRecibo n WHERE n.idrecibo = :idrecibo"),
    @NamedQuery(name = "NotificacionRecibo.findByEnviado", query = "SELECT n FROM NotificacionRecibo n WHERE n.enviado = :enviado")})
public class NotificacionRecibo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDRECIBO")
    private Integer idrecibo;
    @Column(name = "ENVIADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enviado;
    @JoinColumn(name = "ESTADONOTIFICACION", referencedColumnName = "ESTADONOTIFICACION")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoNotificacion estadonotificacion;
    @JoinColumn(name = "IDRECIBO", referencedColumnName = "IDRECIBO", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Recibo recibo;

    public NotificacionRecibo() {
    }

    public NotificacionRecibo(Integer idrecibo) {
        this.idrecibo = idrecibo;
    }

    public Integer getIdrecibo() {
        return idrecibo;
    }

    public void setIdrecibo(Integer idrecibo) {
        this.idrecibo = idrecibo;
    }

    public Date getEnviado() {
        return enviado;
    }

    public void setEnviado(Date enviado) {
        this.enviado = enviado;
    }

    public EstadoNotificacion getEstadonotificacion() {
        return estadonotificacion;
    }

    public void setEstadonotificacion(EstadoNotificacion estadonotificacion) {
        this.estadonotificacion = estadonotificacion;
    }

    public Recibo getRecibo() {
        return recibo;
    }

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
        if (!(object instanceof NotificacionRecibo)) {
            return false;
        }
        NotificacionRecibo other = (NotificacionRecibo) object;
        if ((this.idrecibo == null && other.idrecibo != null) || (this.idrecibo != null && !this.idrecibo.equals(other.idrecibo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.NotificacionRecibo[ idrecibo=" + idrecibo + " ]";
    }
    
}
