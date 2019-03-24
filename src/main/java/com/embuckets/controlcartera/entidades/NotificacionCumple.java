/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.ui.observable.ObservableNotificacionCumple;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
@Table(name = "NOTIFICACION_CUMPLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionCumple.findAll", query = "SELECT n FROM NotificacionCumple n"),
    @NamedQuery(name = "NotificacionCumple.findByIdcliente", query = "SELECT n FROM NotificacionCumple n WHERE n.idcliente = :idcliente"),
    @NamedQuery(name = "NotificacionCumple.findByEnviado", query = "SELECT n FROM NotificacionCumple n WHERE n.enviado = :enviado"),
    @NamedQuery(name = "NotificacionCumple.findPendingWithin", query = "SELECT n FROM NotificacionCumple n WHERE n.estadonotificacion.estadonotificacion = :estado AND n.cliente.nacimiento BETWEEN :startDate AND :endDate"),
    @NamedQuery(name = "NotificacionCumple.findWithin", query = "SELECT n FROM NotificacionCumple n WHERE n.cliente.nacimiento BETWEEN :startDate AND :endDate")})
public class NotificacionCumple implements Serializable, ObservableNotificacionCumple {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private Integer idcliente;
    @Column(name = "ENVIADO")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime enviado;
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;
    @JoinColumn(name = "ESTADONOTIFICACION", referencedColumnName = "ESTADONOTIFICACION")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoNotificacion estadonotificacion;

    public NotificacionCumple() {
    }
//
//    public NotificacionCumple(Integer idcliente) {
//        this.idcliente = idcliente;
//    }

    public NotificacionCumple(Cliente cliente, String estadonotificacion) {
        this.idcliente = cliente.getIdcliente();
        this.cliente = cliente;
        this.estadonotificacion = new EstadoNotificacion(estadonotificacion);
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public LocalDateTime getEnviado() {
        return enviado;
    }

    public void setEnviado(LocalDateTime enviado) {
        this.enviado = enviado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoNotificacion getEstadonotificacion() {
        return estadonotificacion;
    }

    public void setEstadonotificacion(EstadoNotificacion estadonotificacion) {
        this.estadonotificacion = estadonotificacion;
    }

    public String getNombreAsegurado() {
        return cliente.nombreProperty().get();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionCumple)) {
            return false;
        }
        NotificacionCumple other = (NotificacionCumple) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.NotificacionCumple[ idcliente=" + idcliente + " ]";
    }

    @Override
    public StringProperty nombreCompletoProperty() {
        return cliente.nombreProperty();
    }

    @Override
    public StringProperty nacimientoProperty() {
        return cliente.nacimientoProperty();
    }

    @Override
    public StringProperty faltanProperty() {
        LocalDate nacimiento = this.cliente.getNacimiento();
        return new SimpleStringProperty("" + (nacimiento.getDayOfYear() - LocalDate.now().getDayOfYear()) + " días");
    }

    @Override
    public StringProperty estadoProperty() {
        return new SimpleStringProperty(this.estadonotificacion.getEstadonotificacion());
    }

}
