/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.service;

import com.embuckets.controlcartera.entidades.Agente;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
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

    private MailService() {
        loadProperties();
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

}
