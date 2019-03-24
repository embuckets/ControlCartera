/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.ui.observable.ObservableNotificacionRecibo;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Table(name = "NOTIFICACION_RECIBO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionRecibo.findAll", query = "SELECT n FROM NotificacionRecibo n"),
    @NamedQuery(name = "NotificacionRecibo.findByIdrecibo", query = "SELECT n FROM NotificacionRecibo n WHERE n.idrecibo = :idrecibo"),
    @NamedQuery(name = "NotificacionRecibo.findByEnviado", query = "SELECT n FROM NotificacionRecibo n WHERE n.enviado = :enviado")})
public class NotificacionRecibo implements Serializable, ObservableNotificacionRecibo {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDRECIBO")
    private Integer idrecibo;
    @Column(name = "ENVIADO")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime enviado;
    @JoinColumn(name = "ESTADONOTIFICACION", referencedColumnName = "ESTADONOTIFICACION")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoNotificacion estadonotificacion;
    @JoinColumn(name = "IDRECIBO", referencedColumnName = "IDRECIBO", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Recibo recibo;

    public NotificacionRecibo() {
    }
//
//    public NotificacionRecibo(Integer idrecibo) {
//        this.idrecibo = idrecibo;
//    }

    public NotificacionRecibo(Recibo recibo, String estadonotificacion) {
        this.idrecibo = recibo.getIdrecibo();
        this.estadonotificacion = new EstadoNotificacion(estadonotificacion);
        this.recibo = recibo;
    }

    public Integer getIdrecibo() {
        return idrecibo;
    }

    public void setIdrecibo(Integer idrecibo) {
        this.idrecibo = idrecibo;
    }

    public LocalDateTime getEnviado() {
        return enviado;
    }

    public void setEnviado(LocalDateTime enviado) {
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

    public String getNombreArchivo() {
        return recibo.getDocumentoRecibo() != null ? recibo.getDocumentoRecibo().getNombre() : "";
    }

    public String getExtensionArchivo() {
        return recibo.getDocumentoRecibo() != null ? recibo.getDocumentoRecibo().getExtension() : "";
    }

    public boolean tieneArchivo() {
        return recibo.getDocumentoRecibo() != null;
    }

    public byte[] getArchivoBytes() {
        return recibo.getDocumentoRecibo() != null ? recibo.getDocumentoRecibo().getArchivo() : null;
    }

    public File getArchivo() {
        if (tieneArchivo()) {
            try {
                Path temp = Files.createTempFile(getNombreArchivo(), getExtensionArchivo());
                Files.write(temp, getArchivoBytes());
                return temp.toFile();
            } catch (IOException ex) {
                Logger.getLogger(NotificacionRecibo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean tieneEmail() {
        return !recibo.getIdpoliza().getContratante().getEmailList().isEmpty();
    }

    public String getEmail() {
        List<Email> emails = recibo.getIdpoliza().getContratante().getEmailList();
        return emails.stream().sorted((o1, o2) -> o1.getTipoemail().getTipoemail().compareTo(o2.getTipoemail().getTipoemail())).findFirst().get().getEmailPK().getEmail();
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

    public String getNombreContratante() {
        return recibo.getIdpoliza().getContratante().getCliente().nombreProperty().get();
    }

    public String getNumeroPoliza() {
        return recibo.getIdpoliza().getNumero();
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.NotificacionRecibo[ idrecibo=" + idrecibo + " ]";
    }

    @Override
    public StringProperty polizaProperty() {
        return new SimpleStringProperty(this.recibo.getIdpoliza().getNumero());
    }

    @Override
    public StringProperty aseguradoProperty() {
        return new SimpleStringProperty(this.recibo.getIdpoliza().getContratante().getCliente().nombreProperty().get());
    }

    @Override
    public StringProperty cubreDesdeProperty() {
        return new SimpleStringProperty(this.recibo.getCubredesde().toString());

    }

    @Override
    public StringProperty cubreHastaProperty() {
        return new SimpleStringProperty(this.recibo.getCubrehasta().toString());
    }

    @Override
    public StringProperty importeProperty() {
        return new SimpleStringProperty(Globals.formatCantidad(this.recibo.getImporte()));
    }

    @Override
    public StringProperty enviadoProperty() {
        if (enviado != null) {
            String date = enviado.toLocalDate().toString();
            String time = enviado.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString();
            return new SimpleStringProperty(date + " " + time);
        } else {
            return new SimpleStringProperty(Globals.NOTIFICACION_ESTADO_PENDIENTE);
        }
    }

    @Override
    public StringProperty cobranzaProperty() {
        return recibo.cobranzaProperty();
    }

    @Override
    public StringProperty documentoProperty() {
        return new SimpleStringProperty("");
    }

}
