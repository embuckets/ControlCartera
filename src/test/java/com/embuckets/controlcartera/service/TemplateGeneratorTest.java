/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.service;

import com.embuckets.controlcartera.mail.TemplateGenerator;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.util.List;
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
public class TemplateGeneratorTest {
    BaseDeDatos bd;
    public TemplateGeneratorTest() {
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
    }
    
    @After
    public void tearDown() {
        bd.close();
    }

    /**
     * Test of getCobranzaMessage method, of class TemplateGenerator.
     */
    @Test
    public void testGetCobranzaMessage() {
        System.out.println("getCobranzaMessage");
        List<NotificacionRecibo> notificaciones = bd.getRecibosPendientes();
        for (NotificacionRecibo notificacion : notificaciones){
            String message = TemplateGenerator.getCobranzaMessage(notificacion);
            System.out.println("====================");
            System.out.println(message);
            System.out.println("====================");
        }
    }

    /**
     * Test of getCumpleMessage method, of class TemplateGenerator.
     */
    @Test
    public void testGetCumpleMessage() {
        System.out.println("getCumpleMessage");
        List<NotificacionCumple> notificaciones = bd.getCumplesPendientesDeHace();//MAL
        for (NotificacionCumple notificacion : notificaciones){
            String message = TemplateGenerator.getCumpleMessage(notificacion);
            System.out.println("====================");
            System.out.println(message);
            System.out.println("====================");
        }
    }
    
}
