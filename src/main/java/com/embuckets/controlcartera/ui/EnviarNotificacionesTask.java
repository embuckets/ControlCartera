/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import com.embuckets.controlcartera.entidades.EstadoNotificacion;
import com.embuckets.controlcartera.entidades.Notificacion;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.mail.MailService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.concurrent.Task;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import com.embuckets.controlcartera.mail.EmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emilio
 */
public class EnviarNotificacionesTask extends Task<Void> {

    private static final Logger logger = LogManager.getLogger(EnviarNotificacionesTask.class);

    private MailService mailService;
    private List<? extends Notificacion> notificaciones;
    private Map<Message, Notificacion> scheduledNotificaciones;
    private Set<Message> sentMessages;

    /**
     *
     */
    public static AtomicInteger workDone;
    private double goal;
//    private Task<Void> task;

    /**
     *
     * @param notificaciones
     * @throws Exception
     */
    public EnviarNotificacionesTask(List<? extends Notificacion> notificaciones) throws Exception {
        this.mailService = MailService.getInstance();
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
                    logger.warn("Error al enviar correo", ex);
                    notif.setEnviado(oldEnviado);
                    notif.setEstadonotificacion(new EstadoNotificacion(oldEstado));
                }
                updateProgress(workDone.addAndGet(1), goal);
            }
            updateProgress(goal, goal);
            scheduledNotificaciones.clear();
            sentMessages.clear(); //TODO: no borra antes de enviar todos los correos
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Set<Message> getSentMessages() {
        return sentMessages;
    }

    /**
     *
     * @param sentMessages
     */
    public void setSentMessages(Set<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    /**
     *
     */
    public void updateWorkDone() {
        workDone.addAndGet(1);
    }

}
