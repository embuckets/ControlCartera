/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.service;

import com.embuckets.controlcartera.mail.TemplateGenerator;
import com.embuckets.controlcartera.mail.MailService;
import com.embuckets.controlcartera.entidades.Agente;
import com.embuckets.controlcartera.entidades.EstadoNotificacion;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emilio
 */
public class MailServiceTest {

    MailService mailService;
    BaseDeDatos bd;
    Agente agente;

    public MailServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        bd = BaseDeDatos.getInstance();
        mailService = MailService.getInstance();
        agente = Agente.getInstance();
    }

    @After
    public void tearDown() {
        bd.close();
    }

    /**
     * Test of getInstance method, of class MailService.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        MailService expResult = null;
        MailService result = MailService.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMail method, of class MailService.
     */
    @Test
    public void testSendMailJustText() {
        System.out.println("sendMail");
        List<NotificacionRecibo> notificaciones = bd.getRecibosPendientesDentroDePrimerosDias();
        for (NotificacionRecibo notificacion : notificaciones) {
            if (notificacion.tieneEmail()) {
                String message = TemplateGenerator.getCobranzaMessage(notificacion);
                try {
                    mailService.sendMail(message, notificacion.getEmailsDeNotificacion().get(0), "Notificacion de cobranza");
                } catch (MessagingException e) {
                    Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, e);
                    fail(e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * Test of sendMail method, of class MailService.
     */
    @Test
    public void testSendMailWithAttachments() {
        System.out.println("sendMail");
        List<NotificacionRecibo> notificaciones = bd.getRecibosPendientes();
        for (NotificacionRecibo notificacion : notificaciones) {
            if (notificacion.tieneEmail()) {
                if (notificacion.tieneArchivo()) {
                    String message = TemplateGenerator.getCobranzaMessage(notificacion);
                    try {
                        mailService.sendMail(message, notificacion.getEmailsDeNotificacion().get(0), "Notificacion de cobranza", notificacion.getArchivo());
                    } catch (MessagingException e) {
                        Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, e);
                        fail(e.getLocalizedMessage());
                    } catch (IOException ex) {
                        Logger.getLogger(MailServiceTest.class.getName()).log(Level.SEVERE, null, ex);
                        fail(ex.getLocalizedMessage());
                    }

                }
            }
        }
    }

}
