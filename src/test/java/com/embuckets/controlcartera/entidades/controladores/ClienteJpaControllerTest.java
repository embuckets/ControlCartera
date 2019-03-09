/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.controladores.ClienteJpaController;
import com.embuckets.controlcartera.entidades.Cliente;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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
     * Test of getEntityManager method, of class ClienteJpaController.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        ClienteJpaController instance = null;
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class ClienteJpaController.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Cliente cliente = new Cliente();
        cliente.setNombre("Daniel");
        cliente.setApellidopaterno("Hernandez");
        cliente.setApellidomaterno("Segovia");
        cliente.setNacimiento(Date.from(Instant.now()));
        ClienteJpaController instance = new ClienteJpaController(entityManagerFactory);
        instance.create(cliente);
        System.out.println("ID: " + cliente.getIdcliente());
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
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
     * Test of destroy method, of class ClienteJpaController.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        ClienteJpaController instance = null;
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findClienteEntities method, of class ClienteJpaController.
     */
    @Test
    public void testFindClienteEntities_0args() {
        System.out.println("findClienteEntities");
        ClienteJpaController instance = null;
        List<Cliente> expResult = null;
        List<Cliente> result = instance.findClienteEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findClienteEntities method, of class ClienteJpaController.
     */
    @Test
    public void testFindClienteEntities_int_int() {
        System.out.println("findClienteEntities");
        int maxResults = 0;
        int firstResult = 0;
        ClienteJpaController instance = null;
        List<Cliente> expResult = null;
        List<Cliente> result = instance.findClienteEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findCliente method, of class ClienteJpaController.
     */
    @Test
    public void testFindCliente() {
        System.out.println("findCliente");
        Integer id = null;
        ClienteJpaController instance = null;
        Cliente expResult = null;
        Cliente result = instance.findCliente(id);
        assertEquals(expResult, result);
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

//        ClienteJpaController instance = new ClienteJpaController(entityManagerFactory);
//        try {
//            Cliente cliente = instance.findClienteByNombreCompleto(nombre, apellidopaterno, apellidomaterno);
//            assertTrue("Cliente " + nombre + " " + apellidopaterno + " " + apellidomaterno + " tiene ID = " + cliente.getIdcliente(), true);
//
//        } catch (NoResultException ex) {
//            fail("Cliente " + nombre + " " + apellidopaterno + " " + apellidomaterno + " no existe");
//        }
//
//      // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getClienteCount method, of class ClienteJpaController.
     */
    @Test
    public void testGetClienteCount() {
        System.out.println("getClienteCount");
        ClienteJpaController instance = null;
        int expResult = 0;
        int result = instance.getClienteCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
