/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.EmailPK;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TipoPersona;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
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
public class AseguradoJpaControllerTest {

    EntityManagerFactory entityManagerFactory;

    public AseguradoJpaControllerTest() {
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
     * Test of getEntityManager method, of class AseguradoJpaController.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        AseguradoJpaController instance = null;
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class AseguradoJpaController.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        int random = new Random().nextInt(20);
        Asegurado asegurado = new Asegurado();
        asegurado.setCliente(new Cliente());
        asegurado.getCliente().setNombre("Cliente" + random);
        asegurado.getCliente().setApellidopaterno("Paterno" + random);
        asegurado.getCliente().setApellidomaterno("Materno" + random);
        asegurado.getCliente().setNacimiento(Date.from(Instant.parse("1993-05-22T00:00:00.00Z")));
        asegurado.setTipopersona(new TipoPersona("Fisica"));
        asegurado.setIddomicilio(new Domicilio());
        asegurado.getIddomicilio().setCalle("Calle" + random);
        asegurado.getIddomicilio().setExterior("Exterior" + random);
//        asegurado.setEmailList(new ArrayList<>());
//        asegurado.getEmailList().add(new Email(asegurado.getIdcliente(), "correo" + random + "@correo.com"));
//        asegurado.getEmailList().add(new Email(asegurado.getIdcliente(), "correo2" + random + "@correo.com"));
//        asegurado.setTelefonoList(new ArrayList<>());
//        asegurado.getTelefonoList().add(new Telefono(asegurado.getIdcliente(), "" + random + random + random));
//        ClienteJpaController clienteJpaController = new ClienteJpaController(entityManagerFactory);
//        clienteJpaController.create(asegurado.getCliente());
//        clienteJpaController.getEntityManager().createNamedQuery("")

        AseguradoJpaController instance = new AseguradoJpaController(entityManagerFactory);
        instance.create(asegurado);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class AseguradoJpaController.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Asegurado asegurado = null;
        AseguradoJpaController instance = null;
        instance.edit(asegurado);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class AseguradoJpaController.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        AseguradoJpaController instance = null;
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAseguradoEntities method, of class AseguradoJpaController.
     */
    @Test
    public void testFindAseguradoEntities_0args() {
        System.out.println("findAseguradoEntities");
        AseguradoJpaController instance = null;
        List<Asegurado> expResult = null;
        List<Asegurado> result = instance.findAseguradoEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAseguradoEntities method, of class AseguradoJpaController.
     */
    @Test
    public void testFindAseguradoEntities_int_int() {
        System.out.println("findAseguradoEntities");
        int maxResults = 10;
        int firstResult = 0;
//        AseguradoJpaController instance = new AseguradoJpaController(entityManagerFactory);
//        List<Asegurado> expResult = null;
//        List<Asegurado> result = instance.findAllAsegurados();
//        for (Asegurado asegurado : result) {
//            System.out.println(asegurado.toString());
//            if (asegurado.getPolizaList1() != null) {
//                for (Poliza poliza : asegurado.getPolizaList1()) {
//                    System.out.println(poliza.toString());
//                }
//            }
//            System.out.println();
//        }
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of findAsegurado method, of class AseguradoJpaController.
     */
    @Test
    public void testFindAsegurado() {
        System.out.println("findAsegurado");
        Integer id = null;
        AseguradoJpaController instance = null;
        Asegurado expResult = null;
        Asegurado result = instance.findAsegurado(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAseguradoCount method, of class AseguradoJpaController.
     */
    @Test
    public void testGetAseguradoCount() {
        System.out.println("getAseguradoCount");
        AseguradoJpaController instance = null;
        int expResult = 0;
        int result = instance.getAseguradoCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
