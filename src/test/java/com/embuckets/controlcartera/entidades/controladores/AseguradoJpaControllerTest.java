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
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    EntityManager em;
    BaseDeDatos bd;

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
        bd = BaseDeDatos.getInstance();
//        entityManagerFactory = Persistence.createEntityManagerFactory("cartera");
    }

    @After
    public void tearDown() {
        bd.close();
    }

    /**
     * Test of getEntityManager method, of class AseguradoJpaController.
     */
    @Test
    public void testBuscarPorNombre() {
        System.out.println("testBuscarPorNombre");
        String nombre = "ados";
        String paterno = "";
        String materno = "";
        try {
            List<Asegurado> asegurados = bd.buscarAseguradosPorNombre(nombre, paterno, materno);
            assertFalse(asegurados.isEmpty());
            System.out.println("====RESULT===");
            asegurados.stream().forEach(a -> System.out.println(a));
        } catch (Exception ex) {
            Logger.getLogger(AseguradoJpaControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getEntityManager method, of class AseguradoJpaController.
     */
    @Test
    public void testCreateFail() {
        System.out.println("testCreateFail");
        String nombre = "";
        String paterno = "";
        String materno = "";
        Asegurado aseguradoToFail = new Asegurado(nombre, paterno, materno);
        try {
            bd.create(aseguradoToFail);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Test of create method, of class AseguradoJpaController.
     */
    @Test
    public void testCreateNombreYDomicilio() throws Exception {
        System.out.println("create");
        int random = new Random().nextInt(20);
        Asegurado asegurado = new Asegurado();
        asegurado.setCliente(new Cliente());
        asegurado.getCliente().setNombre("Cliente" + random);
        asegurado.getCliente().setApellidopaterno("Paterno" + random);
        asegurado.getCliente().setApellidomaterno("Materno" + random);
        asegurado.getCliente().setNacimiento(LocalDate.of(1993, Month.MARCH, 2));
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

        AseguradoJpaController instance = new AseguradoJpaController();
        instance.create(asegurado);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class AseguradoJpaController.
     */
    @Test
    public void testCreateNombreYDomicilioYTelefonoYEmails() throws Exception {
        System.out.println("create");
        int random = new Random().nextInt(20);
        Asegurado asegurado = new Asegurado();
        asegurado.setCliente(new Cliente());
        asegurado.getCliente().setNombre("Cliente" + random);
        asegurado.getCliente().setApellidopaterno("Paterno" + random);
        asegurado.getCliente().setApellidomaterno("Materno" + random);
        asegurado.getCliente().setNacimiento(LocalDate.of(1993, Month.MARCH, 2));
        asegurado.setTipopersona(new TipoPersona("Fisica"));
        asegurado.setIddomicilio(new Domicilio());
        asegurado.getIddomicilio().setCalle("Calle" + random);
        asegurado.getIddomicilio().setExterior("Exterior" + random);
        asegurado.setEmailList(new ArrayList<>());
        asegurado.getEmailList().add(new Email("correo" + random + "@correo.com"));
        asegurado.getEmailList().add(new Email("correo2" + random + "@correo.com"));
        asegurado.setTelefonoList(new ArrayList<>());
        asegurado.getTelefonoList().add(new Telefono("" + random + random + random));
//        ClienteJpaController clienteJpaController = new ClienteJpaController(entityManagerFactory);
//        clienteJpaController.create(asegurado.getCliente());
//        clienteJpaController.getEntityManager().createNamedQuery("")

        AseguradoJpaController instance = new AseguradoJpaController();
        instance.create(asegurado);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class AseguradoJpaController.
     */
    @Test
    public void testCreateNombreYDomicilioYTelefonoYEmailsYEdit() throws Exception {
        System.out.println("create");
        int random = new Random().nextInt(20);
        Asegurado asegurado = new Asegurado();
        asegurado.setCliente(new Cliente());
        asegurado.getCliente().setNombre("Cliente" + random);
        asegurado.getCliente().setApellidopaterno("Paterno" + random);
        asegurado.getCliente().setApellidomaterno("Materno" + random);
        asegurado.getCliente().setNacimiento(LocalDate.of(1993, Month.MARCH, 2));
        asegurado.setTipopersona(new TipoPersona("Fisica"));
        asegurado.setIddomicilio(new Domicilio());
        asegurado.getIddomicilio().setCalle("Calle" + random);
        asegurado.getIddomicilio().setExterior("Exterior" + random);
        asegurado.setEmailList(new ArrayList<>());
        Email email = new Email("correo" + random + "@correo.com");
        asegurado.getEmailList().add(email);
        asegurado.getEmailList().add(new Email("correo2" + random + "@correo.com"));
        asegurado.setTelefonoList(new ArrayList<>());
        asegurado.getTelefonoList().add(new Telefono("" + random + random + random));
//        ClienteJpaController clienteJpaController = new ClienteJpaController(entityManagerFactory);
//        clienteJpaController.create(asegurado.getCliente());
//        clienteJpaController.getEntityManager().createNamedQuery("")

        AseguradoJpaController instance = new AseguradoJpaController();
        instance.create(asegurado);
        asegurado.getCliente().setApellidopaterno("OtroAppellido");
        asegurado.setRfc("RFC" + random);
        asegurado.getEmailList().remove(email);
        asegurado.getTelefonoList().add(new Telefono("55" + random));
        instance.edit(asegurado);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}
