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

    /**
     *
     * @return
     * @throws Exception
     */
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

    /**
     * actualiza los atributos a partir del archivo de configuracion
     * @throws Exception
     */
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

    /**
     * Crea el trasporte de correo ya conectado con el servidor de correos
     * @return el transporte de correo
     * @throws NoSuchProviderException - si no se encuentra el provedor de correo para el correo del asegurado
     * @throws MessagingException - si falla la operacion
     */
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

    /**
     * crea el mensaje de correo segun el tipo de notificacion.
     * en caso de existir un archivo asociado con la notificacion lo adjunta al correo automaticamente
     * @param notificacion de la cual llenar el correo
     * @return correo html 
     * @throws IOException - si falla la operacion
     * @throws MessagingException - si falla la operacion
     */
    public MimeMessage createMimeMessage(Notificacion notificacion) throws IOException, MessagingException {
        if (notificacion instanceof NotificacionCumple) {
            return createMimeMessage((NotificacionCumple) notificacion);
        } else {
            return createMimeMessage((NotificacionRecibo) notificacion);
        }
    }

    private MimeMessage createMimeMessage(NotificacionRecibo notificacionRecibo) throws IOException, MessagingException {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));

        for (String email : notificacionRecibo.getEmailsDeNotificacion()) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        }
        message.setSubject("Notificacion de Cobranza");
        message.setHeader("X-Mailer", "sendhtml");
        message.setSentDate(new Date());
        String messageText = TemplateGenerator.crearMensajeHTMLDeNotificacionDeCobranza(notificacionRecibo);
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

    private MimeMessage createMimeMessage(NotificacionCumple notificacionRecibo) throws AddressException, MessagingException, IOException {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));

        for (String email : notificacionRecibo.getEmailsDeNotificacion()) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        }
        message.setSubject("Notificacion de Cumpleaños");
        message.setHeader("X-Mailer", "sendhtml");
        message.setSentDate(new Date());
        String messageText = TemplateGenerator.crearMensajeHTMLDeNotificacionDeCumple(notificacionRecibo);
        message.setDataHandler(new DataHandler(new ByteArrayDataSource(messageText, "text/html")));

        message.saveChanges();
        return message;
    }

    /**
     * crea un arreglo de a partir de la lista de direcciones especificados
     * @param emails 
     * @return arreglo de direcciones de internet
     */
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

    /**
     *
     * @return
     */
    public Session getSession() {
        return session;
    }

    /**
     *
     * @param session
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     *
     * @return
     */
    public String getMailHost() {
        return mailHost;
    }

    /**
     *
     * @param mailHost
     */
    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    /**
     *
     * @return
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public Map<Message, Notificacion> getScheduledNotificaciones() {
        return scheduledNotificaciones;
    }

    /**
     *
     * @param scheduledNotificaciones
     */
    public void setScheduledNotificaciones(Map<Message, Notificacion> scheduledNotificaciones) {
        this.scheduledNotificaciones = scheduledNotificaciones;
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
}
