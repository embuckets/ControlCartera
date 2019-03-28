/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.service;

import com.embuckets.controlcartera.entidades.Agente;
import com.embuckets.controlcartera.entidades.EstadoNotificacion;
import com.embuckets.controlcartera.entidades.Notificacion;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author emilio
 */
public class MailService {

    private static MailService mailService;
    private String mailHost;
    private String from;
    private String password;
    private int port;
    private Map<Message, Notificacion> scheduledNotificaciones;
    private Set<Message> sentMessages;

    private MailService() {
        loadProperties();
        scheduledNotificaciones = Collections.synchronizedMap(new HashMap<>());
        sentMessages = Collections.synchronizedSet(new HashSet<>());
    }

    public static MailService getInstance() {
        if (mailService == null) {
            mailService = new MailService();
        }
        return mailService;
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream(Globals.SMTP_CONFIG_PATH)) {
            from = Agente.getInstance().getEmail();
            password = Agente.getInstance().getPassword();
            Properties smtpProperties = new Properties();
            smtpProperties.load(input);
            setMailHostAndPort(smtpProperties);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setMailHostAndPort(Properties properties) {
        String smtp = from.split("@")[1].split("\\.")[0];
        mailHost = properties.getProperty(smtp).split(",")[0];
        port = Integer.valueOf(properties.getProperty(smtp).split(",")[1]);
    }

    public void sendMail(String messageText, String to, String subject) throws NoSuchProviderException, MessagingException {
        try {
            MimeMessage message = new MimeMessage(createSession());
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            message.setHeader("X-Mailer", "sendhtml");
            message.setSubject(subject);
            message.setSentDate(new Date());

            // Now set the actual message
            message.setDataHandler(new DataHandler(new ByteArrayDataSource(messageText, "text/html")));

            Transport.send(message, from, password);

        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (MessagingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (IOException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMail(String messageText, String to, String subject, File file) throws NoSuchProviderException, MessagingException, IOException {
        try {
            MimeMessage message = new MimeMessage(createSession());

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setHeader("X-Mailer", "sendhtml");
            //text bodyPart
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(messageText, "text/html")));
            //attachemnt bodyPart
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(file);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setSentDate(new Date());

            message.setContent(multipart);

            Transport.send(message, from, password);

        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public void enviarNotificacionesCobranza(List<NotificacionRecibo> notificaciones) {
        Session session = createSession();
        try (Transport transport = session.getTransport("smtp")) {
            transport.connect(mailHost, from, password);
            List<Thread> threads = Collections.synchronizedList(new ArrayList<Thread>());
            for (NotificacionRecibo notificacion : notificaciones) {
                if (!notificacion.tieneEmail()) {
                    continue;
                }
                MimeMessage mimeMessage = createMimeMessage(notificacion, session);
                scheduledNotificaciones.put(mimeMessage, notificacion);
                Thread t = new Thread(new EmailSender(transport, mimeMessage, createAddressArray(notificacion.getEmailsDeNotificacion())));
                threads.add(t);
                t.start();
            }
            while (threads.stream().anyMatch(t -> t.isAlive())) {
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
            }
            scheduledNotificaciones.clear();
            sentMessages.clear(); //TODO: no borra antes de enviar todos los correos
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarNotificacionesCumple(List<NotificacionCumple> notificaciones) {
        Session session = createSession();
        List<Thread> threads = Collections.synchronizedList(new ArrayList<Thread>());
        try (Transport transport = session.getTransport("smtp")) {
            transport.connect(mailHost, from, password);
            for (NotificacionCumple notificacion : notificaciones) {
                if (!notificacion.tieneEmail()) {
                    continue;
                }
                MimeMessage mimeMessage = createMimeMessage(notificacion, session);
                scheduledNotificaciones.put(mimeMessage, notificacion);
                Thread t = new Thread(new EmailSender(transport, mimeMessage, createAddressArray(notificacion.getEmailsDeNotificacion())));
                threads.add(t);
                t.start();
            }
            while (threads.stream().anyMatch(t -> t.isAlive())) {
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
            }
            scheduledNotificaciones.clear();
            sentMessages.clear();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearColaMensajesRecibos() {
        scheduledNotificaciones.clear();
    }

    public void clearColaMensajesCumple() {
        scheduledNotificaciones.clear();
    }

    private MimeMessage createMimeMessage(NotificacionRecibo notificacionRecibo, Session session) throws IOException, MessagingException {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));

        for (String email : notificacionRecibo.getEmailsDeNotificacion()) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        }
        message.setSubject("Notificacion de Cobranza");
        message.setHeader("X-Mailer", "sendhtml");
        message.setSentDate(new Date());
        String messageText = TemplateGenerator.getCobranzaMessage(notificacionRecibo);
        if (notificacionRecibo.tieneArchivo()) {
            //text bodyPart
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(messageText, "text/html")));
            //attachemnt bodyPart
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(notificacionRecibo.getArchivo());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);
        } else {
            message.setDataHandler(new DataHandler(new ByteArrayDataSource(messageText, "text/html")));
        }

        message.saveChanges();
        return message;
    }

    private MimeMessage createMimeMessage(NotificacionCumple notificacionRecibo, Session session) throws IOException, MessagingException {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));

        for (String email : notificacionRecibo.getEmailsDeNotificacion()) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        }
        message.setSubject("Notificacion de Cumpleaños");
        message.setHeader("X-Mailer", "sendhtml");
        message.setSentDate(new Date());
        String messageText = TemplateGenerator.getCumpleMessage(notificacionRecibo);
        message.setDataHandler(new DataHandler(new ByteArrayDataSource(messageText, "text/html")));

        message.saveChanges();
        return message;
    }

    private InternetAddress[] createAddressArray(List<String> emails) {
        List<InternetAddress> addresses = new ArrayList<>();
        InternetAddress[] result = new InternetAddress[emails.size()];
        for (int i = 0; i < result.length; i++) {
            try {
                result[i] = new InternetAddress(emails.get(i));
            } catch (AddressException ex) {
                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    private Session createSession() {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", mailHost);
//        props.put("mail.smtp.port", port);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        return session;
    }

//    @Override
//    public void messageDelivered(TransportEvent e) {
//        System.out.println("Mensaje enviado" + Thread.currentThread().getName());
//        try {
//            NotificacionRecibo notificacionRecibo = scheduledNotificacionesRecibo.get(e.getMessage());
//            notificacionRecibo.setEnviado(LocalDateTime.now());
//            notificacionRecibo.setEstadonotificacion(new EstadoNotificacion(Globals.NOTIFICACION_ESTADO_ENVIADO));
//            BaseDeDatos.getInstance().edit(notificacionRecibo);
//        } catch (Exception ex) {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @Override
//    public void messageNotDelivered(TransportEvent e) {
//        try {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "No se envio el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
//        } catch (MessagingException ex) {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    @Override
//    public void messagePartiallyDelivered(TransportEvent e) {
//        try {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "parcialmente enviado el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
//        } catch (MessagingException ex) {
//            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    private class EmailSender implements TransportListener, Runnable {

        private Transport transport;
        private MimeMessage message;
        private Address[] addresses;

        public EmailSender(Transport transport, MimeMessage message, Address[] addresses) {
            this.transport = transport;
            this.message = message;
            this.addresses = addresses;
        }

        @Override
        public void run() {
            transport.addTransportListener(this);
            try {
                transport.sendMessage(this.message, this.addresses);
            } catch (MessagingException ex) {
                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void messageDelivered(TransportEvent e) {
            sentMessages.add(e.getMessage());
//            try {
//                Notificacion notificacion = scheduledNotificaciones.get(e.getMessage());
//                notificacion.setEnviado(LocalDateTime.now());
//                notificacion.setEstadonotificacion(new EstadoNotificacion(Globals.NOTIFICACION_ESTADO_ENVIADO));
//                BaseDeDatos.getInstance().edit(notificacion);
//            } catch (Exception ex) {
//                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

        @Override
        public void messageNotDelivered(TransportEvent e) {
            try {
                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "No se envio el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
            } catch (MessagingException ex) {
                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void messagePartiallyDelivered(TransportEvent e) {
            try {
                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, "parcialmente enviado el mensaje para " + Arrays.toString(e.getMessage().getAllRecipients()));
            } catch (MessagingException ex) {
                Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
