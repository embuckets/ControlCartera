/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author emilio
 */
public interface Notificacion {

    /**
     *
     * @param dateTime
     */
    void setEnviado(LocalDateTime dateTime);

    /**
     *
     * @param estadoNotificacion
     */
    void setEstadonotificacion(EstadoNotificacion estadoNotificacion);

    /**
     *
     * @return
     */
    LocalDateTime getEnviado();

    /**
     *
     * @return
     */
    EstadoNotificacion getEstadonotificacion();

    /**
     *
     * @return
     */
    boolean tieneEmail();

    /**
     *
     * @return
     */
    List<String> getEmailsDeNotificacion();
}
