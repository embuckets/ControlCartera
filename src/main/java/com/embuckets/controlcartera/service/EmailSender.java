
package com.embuckets.controlcartera.service;

import com.embuckets.controlcartera.ui.EnviarNotificacionesTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.MimeMessage;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.embuckets.controlcartera.service;
//
//import com.embuckets.controlcartera.entidades.EstadoNotificacion;
//import com.embuckets.controlcartera.entidades.NotificacionRecibo;
//import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
//import com.embuckets.controlcartera.entidades.globals.Globals;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.mail.Address;
//import javax.mail.MessagingException;
//import javax.mail.Transport;
//import javax.mail.event.TransportEvent;
//import javax.mail.event.TransportListener;
//import javax.mail.internet.MimeMessage;
//
///**
// *
// * @author emilio
// */
public class EmailSender implements TransportListener, Runnable {

    private Transport transport;

    private MimeMessage message;
    private Address[] addresses;
    private EnviarNotificacionesTask task;

    public EmailSender(Transport transport, MimeMessage message, Address[] addresses, EnviarNotificacionesTask task) {
        this.transport = transport;
        this.message = message;
        this.addresses = addresses;
        this.task = task;
    }

    public EmailSender(Transport transport, MimeMessage message, Address[] addresses) {
        this.transport = transport;
        this.message = message;
        this.addresses = addresses;
    }

    @Override
    public void run() {
        transport.addTransportListener(this);
        try {
            transport.sendMessage(message, addresses);
        } catch (MessagingException ex) {
            Logger.getLogger(EnviarNotificacionesTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void messageDelivered(TransportEvent e) {
        task.getSentMessages().add(e.getMessage());
        task.updateWorkDone();
//        System.out.println("Mensaje enviado" + Thread.currentThread().getName());
//        try {
////            NotificacionRecibo notificacionRecibo = scheduledNotificacionesRecibo.get(e.getMessage());
//            notificacionRecibo.setEnviado(LocalDateTime.now());
//            notificacionRecibo.setEstadonotificacion(new EstadoNotificacion(Globals.NOTIFICACION_ESTADO_ENVIADO));
//            BaseDeDatos.getInstance().edit(notificacionRecibo);
//        } catch (Exception ex) {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void messageNotDelivered(TransportEvent e) {
//        try {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "No se envio el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
//        } catch (MessagingException ex) {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    @Override
    public void messagePartiallyDelivered(TransportEvent e) {
        task.getSentMessages().add(e.getMessage());
//        try {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "parcialmente enviado el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
//        } catch (MessagingException ex) {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
