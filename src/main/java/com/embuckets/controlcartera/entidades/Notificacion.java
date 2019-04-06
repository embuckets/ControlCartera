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

    void setEnviado(LocalDateTime dateTime);

    void setEstadonotificacion(EstadoNotificacion estadoNotificacion);

    LocalDateTime getEnviado();

    EstadoNotificacion getEstadonotificacion();

    boolean tieneEmail();

    List<String> getEmailsDeNotificacion();
}
