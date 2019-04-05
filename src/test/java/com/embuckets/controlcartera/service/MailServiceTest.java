/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.service;

import com.embuckets.controlcartera.mail.MailService;
import com.embuckets.controlcartera.entidades.Agente;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

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
    public void setUp() throws Exception {
        bd = BaseDeDatos.getInstance();
        mailService = MailService.getInstance();
        agente = Agente.getInstance();
    }

    @After
    public void tearDown() {
        bd.close();
    }

}
