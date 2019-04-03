/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.EstadoNotificacion;
import com.embuckets.controlcartera.entidades.Notificacion;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.mail.MailService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableNumberValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.MimeMessage;
import com.embuckets.controlcartera.mail.EmailSender;

/**
 *
 * @author emilio
 */
public class EnviarNotificacionesTask extends Task<Void> implements TransportListener {

    private MailService mailService;
    private List<? extends Notificacion> notificaciones;
    private Map<Message, Notificacion> scheduledNotificaciones;
    private Set<Message> sentMessages;
    public static AtomicInteger workDone;
    private double goal;
//    private Task<Void> task;

    public EnviarNotificacionesTask(List<? extends Notificacion> notificaciones) {
        this.mailService = new MailService();
        this.notificaciones = notificaciones;
        scheduledNotificaciones = Collections.synchronizedMap(new HashMap<>());
        sentMessages = Collections.synchronizedSet(new HashSet<>());

        goal = notificaciones.size() + notificaciones.size();
        workDone = new AtomicInteger(0);
    }

    @Override
    protected Void call() throws Exception {
        try (Transport transport = mailService.connect()) {
//            transport.addTransportListener(this);
            List<Thread> threads = Collections.synchronizedList(new ArrayList<>());
            for (Notificacion notificacion : notificaciones) {
                if (!notificacion.tieneEmail()) {
                    continue;
                }
                MimeMessage mimeMessage = mailService.createMimeMessage(notificacion);
                scheduledNotificaciones.put(mimeMessage, notificacion);
                Thread t = new Thread(new EmailSender(transport, mimeMessage, mailService.createAddressArray(notificacion.getEmailsDeNotificacion()), this));
//                Thread t = new Thread(makeRunnableSender(transport, mimeMessage, mailService.createAddressArray(notificacion.getEmailsDeNotificacion())));
                threads.add(t);
                t.start();
            }
            while (threads.stream().anyMatch(t -> t.isAlive())) {
                Thread.sleep(notificaciones.size() * 1000);
            }
            for (Message mess : sentMessages) {
                Notificacion notif = scheduledNotificaciones.get(mess);
                LocalDateTime oldEnviado = notif.getEnviado();
                String oldEstado = notif.getEstadonotificacion().getEstadonotificacion();
                try {
                    notif.setEnviado(LocalDateTime.now());
                    notif.setEstadonotificacion(new EstadoNotificacion(Globals.NOTIFICACION_ESTADO_ENVIADO));
                    BaseDeDatos.getInstance().edit(notif);
                } catch (Exception ex) {
                    Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
                    notif.setEnviado(oldEnviado);
                    notif.setEstadonotificacion(new EstadoNotificacion(oldEstado));
                }
                updateProgress(workDone.addAndGet(1), goal);
            }
            updateProgress(goal, goal);
            scheduledNotificaciones.clear();
            sentMessages.clear(); //TODO: no borra antes de enviar todos los correos
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public Set<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(Set<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public void updateWorkDone() {
        workDone.addAndGet(1);
    }

//    private Task<Void> makeTask(EnviarNotificacionesTask notificacionesTask) {
//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                try (Transport transport = mailService.connect()) {
//                    transport.addTransportListener(notificacionesTask);
//                    List<Thread> threads = Collections.synchronizedList(new ArrayList<>());
//                    for (NotificacionRecibo notificacion : notificaciones) {
//                        if (!notificacion.tieneEmail()) {
//                            continue;
//                        }
//                        MimeMessage mimeMessage = mailService.createMimeMessage(notificacion);
//                        scheduledNotificaciones.put(mimeMessage, notificacion);
//                        Thread t = new Thread(new EnviarNotificacionesTask.EmailSender(transport, mimeMessage, mailService.createAddressArray(notificacion.getEmailsDeNotificacion())));
//                        threads.add(t);
//                        t.start();
//                        updateProgress(workDone.doubleValue(), goal);
//                    }
//                    while (threads.stream().anyMatch(t -> t.isAlive())) {
//                    }
//                    for (Message mess : sentMessages) {
//                        Notificacion notif = scheduledNotificaciones.get(mess);
//                        LocalDateTime oldEnviado = notif.getEnviado();
//                        String oldEstado = notif.getEstadonotificacion().getEstadonotificacion();
//                        try {
//                            notif.setEnviado(LocalDateTime.now());
//                            notif.setEstadonotificacion(new EstadoNotificacion(Globals.NOTIFICACION_ESTADO_ENVIADO));
//                            BaseDeDatos.getInstance().edit(notif);
//                        } catch (Exception ex) {
//                            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//                            notif.setEnviado(oldEnviado);
//                            notif.setEstadonotificacion(new EstadoNotificacion(oldEstado));
//                        }
//                        EnviarNotificacionesTask.workDone.addAndGet(1);
//                        updateProgress(workDone.doubleValue(), goal);
//                    }
//                    scheduledNotificaciones.clear();
//                    sentMessages.clear(); //TODO: no borra antes de enviar todos los correos
//                } catch (Exception e) {
//                    throw e;
//                }
//                return null;
//            }
//        };
//        return task;
//    }
//    public Task<Void> getTask() {
//        return task;
//    }
    public Runnable makeRunnableSender(Transport transport, MimeMessage message, Address[] addresses) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    transport.sendMessage(message, addresses);
                } catch (MessagingException ex) {
                    Logger.getLogger(EnviarNotificacionesTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    @Override
    public void messageDelivered(TransportEvent e) {
        sentMessages.add(e.getMessage());
        updateProgress(workDone.addAndGet(1), goal);
    }

    @Override
    public void messageNotDelivered(TransportEvent e) {
        updateProgress(workDone.addAndGet(1), goal);
        try {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "No se envio el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
        } catch (MessagingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void messagePartiallyDelivered(TransportEvent e) {
        updateProgress(workDone.addAndGet(1), goal);
        try {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "parcialmente enviado el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
        } catch (MessagingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private class EmailSender implements TransportListener, Runnable {
//
//        private Transport transport;
//        private MimeMessage message;
//        private Address[] addresses;
//
//        public EmailSender(Transport transport, MimeMessage message, Address[] addresses) {
//            this.transport = transport;
//            this.message = message;
//            this.addresses = addresses;
//        }
//
//        @Override
//        public void run() {
//            transport.addTransportListener(this);
//            try {
//                transport.sendMessage(this.message, this.addresses);
//            } catch (MessagingException ex) {
//                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        @Override
//        public void messageDelivered(TransportEvent e) {
//            sentMessages.add(e.getMessage());
//            EnviarNotificacionesTask.workDone.addAndGet(1);
//        }
//
//        @Override
//        public void messageNotDelivered(TransportEvent e) {
//            EnviarNotificacionesTask.workDone.addAndGet(1);
//            try {
//                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "No se envio el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
//            } catch (MessagingException ex) {
//                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//
//        @Override
//        public void messagePartiallyDelivered(TransportEvent e) {
//            EnviarNotificacionesTask.workDone.addAndGet(1);
//            try {
//                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "parcialmente enviado el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
//            } catch (MessagingException ex) {
//                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//    }
}
