package com.embuckets.controlcartera.mail;

import com.embuckets.controlcartera.entidades.globals.Logging;
import com.embuckets.controlcartera.ui.EnviarNotificacionesTask;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailSender implements TransportListener, Runnable {

    private static final Logger logger = LogManager.getLogger(EmailSender.class);

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
            logger.error(Logging.Exception_MESSAGE, ex);
        }
    }

    @Override
    public void messageDelivered(TransportEvent e) {
        task.getSentMessages().add(e.getMessage());
        task.updateWorkDone();
    }

    @Override
    public void messageNotDelivered(TransportEvent e) {
        logger.info("Message Not Delivered", e);
    }

    @Override
    public void messagePartiallyDelivered(TransportEvent e) {
        task.getSentMessages().add(e.getMessage());
        logger.info("Message Partially Delivered", e);
    }

}
