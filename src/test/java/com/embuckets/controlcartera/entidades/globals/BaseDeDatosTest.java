/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.Delegacion;
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.entidades.TipoEmail;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.controladores.AseguradoJpaController;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
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
public class BaseDeDatosTest {

    BaseDeDatos bd;

    public BaseDeDatosTest() {
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
     * Test of getInstance method, of class BaseDeDatos.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        BaseDeDatos expResult = null;
        BaseDeDatos result = BaseDeDatos.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntityManager method, of class BaseDeDatos.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        BaseDeDatos instance = null;
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class BaseDeDatos.
     */
    @Test
    public void testCreate() {
        System.out.println("createAsegurado");
        int random = new Random().nextInt(100);
        Asegurado asegurado = new Asegurado();
        asegurado.setCliente(new Cliente());
        asegurado.getCliente().setNombre("Cliente" + random);
        asegurado.getCliente().setApellidopaterno("Paterno" + random);
        asegurado.getCliente().setApellidomaterno("Materno" + random);
        asegurado.getCliente().setNacimiento(LocalDate.of(1994, Month.MARCH, 23));
        asegurado.setTipopersona(new TipoPersona("Fisica"));
        asegurado.setIddomicilio(new Domicilio());
        asegurado.getIddomicilio().setCalle("Calle" + random);
        asegurado.getIddomicilio().setExterior("Exterior" + random);
        List<Delegacion> delegaciones = bd.getAll(Delegacion.class);
        asegurado.getIddomicilio().setDelegacion(delegaciones.get(5));
        List<Estado> estados = bd.getAll(Estado.class);
        asegurado.getIddomicilio().setEstado(estados.get(5));
        bd.create(asegurado);
        assertNotNull(asegurado.getId());
        assertNotNull(bd.getById(Asegurado.class, asegurado.getId()));
    }

    /**
     * Test of getById method, of class BaseDeDatos.
     */
    @Test
    public void testgetById() {
        System.out.println("getById");
        Asegurado asegurado = bd.getById(Asegurado.class, 1509);
        assertNotNull(asegurado.getCliente());
        assertNotNull(asegurado.getIddomicilio());
        assertNotNull(asegurado.getIddomicilio().getDelegacion());
        assertNotNull(asegurado.getIddomicilio().getCalle());
        assertNotNull(asegurado.getTipopersona());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getById method, of class BaseDeDatos.
     */
    @Test
    public void testgetAll() {
        System.out.println("testgetAll");
        List<Asegurado> asegurados = bd.getAll(Asegurado.class);
        assertNotNull(asegurados);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getById method, of class BaseDeDatos.
     */
    @Test
    public void testgetByIdAndEdit() {
        System.out.println("getByIdAndEdit");
        Asegurado asegurado = bd.getById(Asegurado.class, 1508);
        assertNotNull(asegurado);
        String nuevoNombre = "Nuevo Nombre";
        asegurado.getCliente().setNombre(nuevoNombre);
//        asegurado.getEmailList().get(0).setTipoemail(new TipoEmail(Globals.EMAIL_TIPO_TRABAJO));
        Asegurado edited = bd.edit(asegurado);
        assertEquals(edited.getCliente().getNombre(), nuevoNombre);
        assertEquals(asegurado, edited);
    }

    /**
     * Test of getById method, of class BaseDeDatos.
     */
    @Test
    public void testGetAndRemove() {
        System.out.println("GetAndRemove");
        int id = 1508;
        Asegurado asegurado = bd.getById(Asegurado.class, id);
        assertNotNull(asegurado);
        bd.remove(asegurado);
        assertNull(bd.getById(Asegurado.class, id));
//        assertNull(asegurado);
    }

    /**
     * Test of getById method, of class BaseDeDatos.
     */
    @Test
    public void testGetClassName() {
        System.out.println("testGetClassName");
        System.out.println(this.getClass().getName());
        System.out.println(this.getClass().getCanonicalName());
        System.out.println(this.getClass().getSimpleName());
        System.out.println(this.getClass().getTypeName());
//        assertNull(asegurado);
    }

    /**
     * Test of close method, of class BaseDeDatos.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        bd.close();
        // TODO review the generated test code and remove the default call to fail.
    }

}
