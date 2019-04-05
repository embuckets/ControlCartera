/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Cliente;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
public class ClienteJpaControllerTest {

    private EntityManagerFactory entityManagerFactory;

    public ClienteJpaControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("cartera");

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of edit method, of class ClienteJpaController.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Cliente cliente = null;
        ClienteJpaController instance = null;
        instance.edit(cliente);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findCliente method, of class ClienteJpaController.
     */
    @Test
    public void testFindClienteByNombreYApellidos() {
        System.out.println("findClienteByNombreYApellidos");
        String nombre = "emilio";
        String apellidopaterno = "her";
        String apellidomaterno = "sego";

    }

}
