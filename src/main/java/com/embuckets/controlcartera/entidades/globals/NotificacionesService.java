/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.service.MailService;
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
    
    public void enviarRecibosPendientes(){
        List<NotificacionRecibo> notificaciones = BaseDeDatos.getInstance().getRecibosPendientesDentroDePrimerosDias();
        
    }
}
