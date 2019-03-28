/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import com.embuckets.controlcartera.entidades.Agente;
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
public class NotificacionesServiceTest {

    BaseDeDatos bd;
    Agente agente;
    NotificacionesService service;

    public NotificacionesServiceTest() {
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
        agente = Agente.getInstance();
        service = NotificacionesService.getInstance();
    }

    @After
    public void tearDown() {
        bd.close();
    }

    /**
     * Test of getInstance method, of class NotificacionesService.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        NotificacionesService expResult = null;
        NotificacionesService result = NotificacionesService.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enviarRecibosPendientes method, of class NotificacionesService.
     */
    @Test
    public void testEnviarRecibosPendientes() {
        System.out.println("enviarRecibosPendientes");
        service.enviarRecibosPendientes();
    }

    /**
     * Test of enviarRecibosPendientes method, of class NotificacionesService.
     */
    @Test
    public void testEnviarCumplePendientes() {
        System.out.println("testEnviarCumplePendientes");
        service.enviarCumplesPendientes();
    }

}
