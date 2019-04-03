/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.mail.MailService;
import java.util.List;

/**
 *
 * @author emilio
 */
public class NotificacionesService {

    private MailService mailService;

    private static NotificacionesService service;

    private NotificacionesService() {
        mailService = MailService.getInstance();
    }

    public static NotificacionesService getInstance() {
        if (service == null) {
            service = new NotificacionesService();
        }
        return service;
    }

    public void enviarRecibosPendientes() {
        List<NotificacionRecibo> notificaciones = BaseDeDatos.getInstance().getRecibosPendientesDentroDePrimerosDias();
//        mailService.enviarNotificacionesCobranza(notificaciones);
//        mailService.clearColaMensajesRecibos();
    }

    public void enviarRecibosPendientes(List<NotificacionRecibo> notificaciones) {
//        mailService.enviarNotificacionesCobranza(notificaciones);
//        mailService.clearColaMensajesRecibos();
    }

    public void enviarCumplesPendientes() {
        List<NotificacionCumple> notificaciones = BaseDeDatos.getInstance().getCumplesPendientesDeHace();
//        mailService.enviarNotificacionesCumple(notificaciones);
    }

    public void enviarCumplesPendientes(List<NotificacionCumple> notificaciones) {
//        mailService.enviarNotificacionesCumple(notificaciones);
    }
}
