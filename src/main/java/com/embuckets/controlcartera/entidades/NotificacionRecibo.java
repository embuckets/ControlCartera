/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import com.embuckets.controlcartera.ui.observable.ObservableNotificacionRecibo;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * -- recibos dentro de primeros 15 dias (0,15] select app.recibo.CUBREDESDE,
 * {fn TIMESTAMPDIFF(SQL_TSI_DAY, APP.RECIBO.CUBREDESDE, CURRENT_TIMESTAMP)} as
 * days, {fn TIMESTAMPDIFF(SQL_TSI_HOUR, APP.RECIBO.CUBREDESDE,
 * CURRENT_TIMESTAMP)} as hours from app.recibo WHERE ({fn
 * TIMESTAMPDIFF(SQL_TSI_DAY, APP.RECIBO.CUBREDESDE, CURRENT_TIMESTAMP)} > 0 AND
 * {fn TIMESTAMPDIFF(SQL_TSI_DAY, APP.RECIBO.CUBREDESDE, CURRENT_TIMESTAMP)} <= 15);
 * -- recibos dentro de (15,25]
 * select app.recibo.CUBREDESDE, {fn TIMESTAMPDIFF(SQL_TSI_DAY, APP.RECIBO.CUBREDESDE, CURRENT_TIMESTAMP)} as days, {fn TIMESTAMPDIFF(SQL_TSI_HOUR, APP.RECIBO.CUBREDESDE, CURRENT_TIMESTAMP)} as hours from app.recibo WHERE ({fn TIMESTAMPDIFF(SQL_TSI_DAY, APP.RECIBO.CUBREDESDE, CURRENT_TIMESTAMP)}
 * > 15 AND {fn TIMESTAMPDIFF(SQL_TSI_DAY, APP.RECIBO.CUBREDESDE,
 * CURRENT_TIMESTAMP)} <= 25);
 * -- recibos dentro de (25,30]
 * select app.recibo.CUBREDESDE, {fn TIMESTAMPDIFF(SQL_TSI_DAY, APP.RECIBO.CUBREDESDE, CURRENT_TIMESTAMP)} as days, {fn TIMESTAMPDIFF(SQL_TSI_HOUR, APP.RECIBO.CUBREDESDE, CURRENT_TIMESTAMP)} as hours from app.recibo WHERE ({fn TIMESTAMPDIFF(SQL_TSI_DAY, APP.RECIBO.CUBREDESDE, CURRENT_TIMESTAMP)}
 * > 25 AND {fn TIMESTAMPDIFF(SQL_TSI_DAY, APP.RECIBO.CUBREDESDE,
 * CURRENT_TIMESTAMP)} <= 30);
 */
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
//    @NamedQuery(name = "NotificacionRecibo.findDentro15DiasPendientes", query = "SELECT n FROM NotificacionRecibo n WHERE ({fn TIMESTAMPDIFF(SQL_TSI_DAY, n.recibo.cubredesde, CURRENT_TIMESTAMP)} > 0 AND {fn TIMESTAMPDIFF(SQL_TSI_DAY,  n.recibo.cubredesde, CURRENT_TIMESTAMP)} <= 15) AND n.recibo.cobranza.cobranza = :pendiente")})
public class NotificacionRecibo implements Serializable, ObservableNotificacionRecibo, Notificacion {

    private static final Logger logger = LogManager.getLogger(NotificacionRecibo.class);
    
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

    /**
     *
     */
    public NotificacionRecibo() {
    }
//
//    public NotificacionRecibo(Integer idrecibo) {
//        this.idrecibo = idrecibo;
//    }

    /**
     *
     * @param recibo
     * @param estadonotificacion
     */
    public NotificacionRecibo(Recibo recibo, String estadonotificacion) {
        this.idrecibo = recibo.getIdrecibo();
        this.estadonotificacion = new EstadoNotificacion(estadonotificacion);
        this.recibo = recibo;
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
    @Override
    public LocalDateTime getEnviado() {
        return enviado;
    }

    /**
     *
     * @param enviado
     */
    @Override
    public void setEnviado(LocalDateTime enviado) {
        this.enviado = enviado;
    }

    /**
     *
     * @return
     */
    @Override
    public EstadoNotificacion getEstadonotificacion() {
        return estadonotificacion;
    }

    /**
     *
     * @param estadonotificacion
     */
    @Override
    public void setEstadonotificacion(EstadoNotificacion estadonotificacion) {
        this.estadonotificacion = estadonotificacion;
    }

    /**
     *
     * @return
     */
    public Recibo getRecibo() {
        return recibo;
    }

    /**
     *
     * @param recibo
     */
    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    /**
     *
     * @return
     */
    public String getNombreArchivo() {
        return recibo.getDocumentoRecibo() != null ? recibo.getDocumentoRecibo().getNombre() : "";
    }

    /**
     *
     * @return
     */
    public String getExtensionArchivo() {
        return recibo.getDocumentoRecibo() != null ? recibo.getDocumentoRecibo().getExtension() : "";
    }

    /**
     *
     * @return
     */
    public boolean tieneArchivo() {
        return recibo.getDocumentoRecibo() != null;
    }

    /**
     *
     * @return
     */
    public byte[] getArchivoBytes() {
        return recibo.getDocumentoRecibo() != null ? recibo.getDocumentoRecibo().getArchivo() : null;
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public File getArchivo() throws IOException {
        if (tieneArchivo()) {
            try {
                Path temp = Files.createTempFile(getNombreArchivo(), getExtensionArchivo());
                Files.write(temp, getArchivoBytes());
                return temp.toFile();
            } catch (IOException ex) {
                logger.error(Logging.Exception_MESSAGE, ex);
                throw ex;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean tieneEmail() {
        return !recibo.getIdpoliza().getContratante().getEmailList().isEmpty();
    }

    /**
     *
     * @return
     */
    @Override
    public List<String> getEmailsDeNotificacion() {
        List<Email> emails = recibo.getIdpoliza().getContratante().getEmailList();
        List<String> emailsDeNotificacio = emails.stream().filter(e -> e.isNotificar()).map(e -> e.getEmailPK().getEmail()).collect(Collectors.toList());
        if (!emailsDeNotificacio.isEmpty()) {
            return emailsDeNotificacio;
        } else {
            ArrayList<String> first = new ArrayList<>();
            first.add(emails.get(0).getEmailPK().getEmail());
            return first;
        }
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

    /**
     *
     * @return
     */
    public String getNombreContratante() {
        return recibo.getIdpoliza().getContratante().getCliente().nombreProperty().get();
    }

    /**
     *
     * @return
     */
    public String getNumeroPoliza() {
        return recibo.getIdpoliza().getNumero();
    }

    @Override
    public String toString() {
        return "com.embuckets.controlcartera.entidades.NotificacionRecibo[ idrecibo=" + idrecibo + " ]";
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty polizaProperty() {
        return new SimpleStringProperty(this.recibo.getIdpoliza().getNumero());
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty aseguradoProperty() {
        return new SimpleStringProperty(this.recibo.getIdpoliza().getContratante().getCliente().nombreProperty().get());
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty cubreDesdeProperty() {
        return new SimpleStringProperty(this.recibo.getCubredesde().toString());

    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty cubreHastaProperty() {
        return new SimpleStringProperty(this.recibo.getCubrehasta().toString());
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty importeProperty() {
        return new SimpleStringProperty(Globals.formatCantidad(this.recibo.getImporte()));
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    public StringProperty conductoProperty() {
        return new SimpleStringProperty(recibo.getIdpoliza().getConductocobro().getConductocobro());
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty cobranzaProperty() {
        return recibo.cobranzaProperty();
    }

    /**
     *
     * @return
     */
    @Override
    public StringProperty documentoProperty() {
        return new SimpleStringProperty("");
    }

    /**
     *
     * @return
     */
    public StringProperty diasDesdeProperty() {
        long dias = DAYS.between(recibo.getCubredesde(), LocalDate.now());
        String faltan = dias > 0 ? "" + dias : "faltan " + Math.abs(dias);
        return new SimpleStringProperty(faltan);
    }

}
