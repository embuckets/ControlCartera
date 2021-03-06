/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.ui.observable.ObservableNotificacionRecibo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emilio
 */
@Entity
@Table(name = "RECIBO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recibo.findAll", query = "SELECT r FROM Recibo r"),
    @NamedQuery(name = "Recibo.findByIdrecibo", query = "SELECT r FROM Recibo r WHERE r.idrecibo = :idrecibo"),
    @NamedQuery(name = "Recibo.findByCubredesde", query = "SELECT r FROM Recibo r WHERE r.cubredesde = :cubredesde"),
    @NamedQuery(name = "Recibo.findByCubrehasta", query = "SELECT r FROM Recibo r WHERE r.cubrehasta = :cubrehasta"),
    @NamedQuery(name = "Recibo.findByImporte", query = "SELECT r FROM Recibo r WHERE r.importe = :importe")})
public class Recibo implements Serializable, ObservableNotificacionRecibo {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRECIBO")
    private Integer idrecibo;
    @Basic(optional = false)
    @Column(name = "CUBREDESDE")
//    @Temporal(TemporalType.DATE)
    private LocalDate cubredesde;
    @Basic(optional = false)
    @Column(name = "CUBREHASTA")
//    @Temporal(TemporalType.DATE)
    private LocalDate cubrehasta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "IMPORTE")
    private BigDecimal importe;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recibo", fetch = FetchType.LAZY)
    private DocumentoRecibo documentoRecibo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recibo", fetch = FetchType.LAZY)
    private NotificacionRecibo notificacionRecibo;
    @JoinColumn(name = "COBRANZA", referencedColumnName = "COBRANZA")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cobranza cobranza;
    @JoinColumn(name = "IDPOLIZA", referencedColumnName = "IDPOLIZA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Poliza idpoliza;

    /**
     *
     */
    public Recibo() {
    }

    /**
     *
     * @param idrecibo
     */
    public Recibo(Integer idrecibo) {
        this.idrecibo = idrecibo;
    }

    /**
     *
     * @param idrecibo
     * @param cubredesde
     * @param cubrehasta
     * @param importe
     */
    public Recibo(Integer idrecibo, LocalDate cubredesde, LocalDate cubrehasta, BigDecimal importe) {
        this.idrecibo = idrecibo;
        this.cubredesde = cubredesde;
        this.cubrehasta = cubrehasta;
        this.importe = importe;
    }

    /**
     *
     * @return
     */
    public Integer getIdrecibo() {
        return idrecibo;
    }

    /**
     *
     * @param idrecibo
     */
    public void setIdrecibo(Integer idrecibo) {
        this.idrecibo = idrecibo;
    }

    /**
     *
     * @return
     */
    public LocalDate getCubredesde() {
        return cubredesde;
    }

    /**
     *
     * @param cubredesde
     */
    public void setCubredesde(LocalDate cubredesde) {
        this.cubredesde = cubredesde;
    }

//    public void setCubredesde(LocalDate cubredesde) {
//        this.cubredesde = Date.from(cubredesde.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//    }

    /**
     *
     * @return
     */
    public LocalDate getCubrehasta() {
        return cubrehasta;
    }

    /**
     *
     * @param cubrehasta
     */
    public void setCubrehasta(LocalDate cubrehasta) {
        this.cubrehasta = cubrehasta;
    }

//    public void setCubrehasta(LocalDate cubrehasta) {
//        this.cubrehasta = Date.from(cubrehasta.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//    }

    /**
     *
     * @return
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     *
     * @param importe
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     *
     * @return
     */
    public DocumentoRecibo getDocumentoRecibo() {
        return documentoRecibo;
    }

    /**
     *
     * @param documentoRecibo
     */
    public void setDocumentoRecibo(DocumentoRecibo documentoRecibo) {
        this.documentoRecibo = documentoRecibo;
    }

    /**
     *
     * @return
     */
    public NotificacionRecibo getNotificacionRecibo() {
        return notificacionRecibo;
    }

    /**
     *
     * @param notificacionRecibo
     */
    public void setNotificacionRecibo(NotificacionRecibo notificacionRecibo) {
        this.notificacionRecibo = notificacionRecibo;
    }

    /**
     *
     * @return
     */
    public Cobranza getCobranza() {
        return cobranza;
    }

    /**
     *
     * @param cobranza
     */
    public void setCobranza(Cobranza cobranza) {
        this.cobranza = cobranza;
    }

    /**
     *
     * @return
     */
    public Poliza getIdpoliza() {
        return idpoliza;
    }

    /**
     *
     * @param idpoliza
     */
    public void setIdpoliza(Poliza idpoliza) {
        this.idpoliza = idpoliza;
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
        if (!(object instanceof Recibo)) {
            return false;
        }
        Recibo other = (Recibo) object;
        if ((this.idrecibo == null && other.idrecibo != null) || (this.idrecibo != null && !this.idrecibo.equals(other.idrecibo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.Recibo[ idrecibo=" + idrecibo + " ]";
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty polizaProperty() {
        return getIdpoliza().numeroProperty();
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty aseguradoProperty() {
        return getIdpoliza().getContratante().nombreProperty();
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty cubreDesdeProperty() {
        return new SimpleStringProperty(cubredesde.toString());
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty cubreHastaProperty() {
        return new SimpleStringProperty(cubrehasta.toString());
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty importeProperty() {
        return new SimpleStringProperty(Globals.formatCantidad(importe));
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty enviadoProperty() {
        //puede ser null
        if (notificacionRecibo != null && notificacionRecibo.getEnviado() != null) {
            return notificacionRecibo.enviadoProperty();
        } else {
            return new SimpleStringProperty(Globals.NOTIFICACION_ESTADO_PENDIENTE);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty cobranzaProperty() {
        return new SimpleStringProperty(cobranza.getCobranza());
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty documentoProperty() {
        return documentoRecibo == null ? new SimpleStringProperty("NO") : new SimpleStringProperty("SI");
    }

}
