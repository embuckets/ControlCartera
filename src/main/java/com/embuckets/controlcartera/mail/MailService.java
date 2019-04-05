/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.mail;

import com.embuckets.controlcartera.entidades.Agente;
import com.embuckets.controlcartera.entidades.Notificacion;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emilio
 */
public class MailService {

    private static final Logger logger = LogManager.getLogger(MailService.class);

    private static MailService mailService;
    private String mailHost;
    private String from;
    private String password;
    private int port;
    private Map<Message, Notificacion> scheduledNotificaciones;
    private Set<Message> sentMessages;
    private Session session;

    private MailService() throws Exception {
        loadProperties();
        scheduledNotificaciones = Collections.synchronizedMap(new HashMap<>());
        sentMessages = Collections.synchronizedSet(new HashSet<>());
    }

    public static MailService getInstance() throws Exception {
        if (mailService == null) {
            mailService = new MailService();
        }
        return mailService;
    }

    private void loadProperties() throws Exception {
        try (InputStream input = new FileInputStream(Globals.SMTP_CONFIG_PATH)) {
            from = Agente.getInstance().getEmail();
            password = Agente.getInstance().getPassword();
            if (from == null || from.isEmpty() || password == null || password.isEmpty()) {
                throw new Exception("No hay email o password registrado para enviar correos");
            }
            Properties smtpProperties = new Properties();
            smtpProperties.load(input);
            setMailHostAndPort(smtpProperties);
        } catch (FileNotFoundException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
        } catch (IOException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
        }
    }

    public void refresh() throws Exception {
        loadProperties();
    }

    private void setMailHostAndPort(Properties properties) throws Exception {
        try {
            String smtp = from.split("@")[1].split("\\.")[0];
            mailHost = properties.getProperty(smtp).split(",")[0];
            port = Integer.valueOf(properties.getProperty(smtp).split(",")[1]);
        } catch (Exception ex) {
            throw new Exception("Email inválido. No se pudo encontrar el servidor de correo", ex);
        }
    }

    public Transport connect() throws NoSuchProviderException, MessagingException {
        try {
            Session session = createSession();
            Transport transport = session.getTransport("smtp");
            setSession(session);
            transport.connect(mailHost, from, password);
            return transport;
        } catch (NoSuchProviderException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        } catch (MessagingException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

    public MimeMessage createMimeMessage(Notificacion notificacion) throws IOException, MessagingException {
        if (notificacion instanceof NotificacionCumple) {
            return createMimeMessage((NotificacionCumple) notificacion);
        } else {
            return createMimeMessage((NotificacionRecibo) notificacion);
        }
    }

    public MimeMessage createMimeMessage(NotificacionRecibo notificacionRecibo) throws IOException, MessagingException {
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

    public MimeMessage createMimeMessage(NotificacionCumple notificacionRecibo) throws AddressException, MessagingException, IOException {
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

    public InternetAddress[] createAddressArray(List<String> emails) {
        InternetAddress[] result = new InternetAddress[emails.size()];
        for (int i = 0; i < result.length; i++) {
            try {
                result[i] = new InternetAddress(emails.get(i));
            } catch (AddressException ex) {
                logger.error(Logging.Exception_MESSAGE, ex);
            }
        }
        return result;
    }

    private Session createSession() {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", mailHost);
        //certificados de confianza
        props.put("mail.smtp.ssl.trust", mailHost);
//        props.put("mail.smtp.ssl.trust", "smtp.live.com");
//        props.put("mail.smtp.ssl.trust", "smtp.office365.com");
//        props.put("mail.smtp.ssl.trust", "smtp.mail.yahoo.com");
//        props.put("mail.smtp.ssl.trust", "smtp.live.com");
//        props.put("mail.smtp.ssl.trust", "fortimail.aarco.com.mx");

//        props.put("mail.smtp.port", port);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        return session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Message, Notificacion> getScheduledNotificaciones() {
        return scheduledNotificaciones;
    }

    public void setScheduledNotificaciones(Map<Message, Notificacion> scheduledNotificaciones) {
        this.scheduledNotificaciones = scheduledNotificaciones;
    }

    public Set<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(Set<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }
}
