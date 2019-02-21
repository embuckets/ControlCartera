/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades;

import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.ui.observable.ObservableTreeItem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import javafx.beans.property.StringProperty;
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
public class PolizaTest {

    public PolizaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getIdpoliza method, of class Poliza.
     */
    @Test
    public void testGetIdpoliza() {
        System.out.println("getIdpoliza");
        Poliza instance = new Poliza();
        Integer expResult = null;
        Integer result = instance.getIdpoliza();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIdpoliza method, of class Poliza.
     */
    @Test
    public void testSetIdpoliza() {
        System.out.println("setIdpoliza");
        Integer idpoliza = null;
        Poliza instance = new Poliza();
        instance.setIdpoliza(idpoliza);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumero method, of class Poliza.
     */
    @Test
    public void testGetNumero() {
        System.out.println("getNumero");
        Poliza instance = new Poliza();
        String expResult = "";
        String result = instance.getNumero();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumero method, of class Poliza.
     */
    @Test
    public void testSetNumero() {
        System.out.println("setNumero");
        String numero = "";
        Poliza instance = new Poliza();
        instance.setNumero(numero);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProducto method, of class Poliza.
     */
    @Test
    public void testGetProducto() {
        System.out.println("getProducto");
        Poliza instance = new Poliza();
        String expResult = "";
        String result = instance.getProducto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setProducto method, of class Poliza.
     */
    @Test
    public void testSetProducto() {
        System.out.println("setProducto");
        String producto = "";
        Poliza instance = new Poliza();
        instance.setProducto(producto);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlan method, of class Poliza.
     */
    @Test
    public void testGetPlan() {
        System.out.println("getPlan");
        Poliza instance = new Poliza();
        String expResult = "";
        String result = instance.getPlan();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlan method, of class Poliza.
     */
    @Test
    public void testSetPlan() {
        System.out.println("setPlan");
        String plan = "";
        Poliza instance = new Poliza();
        instance.setPlan(plan);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIniciovigencia method, of class Poliza.
     */
    @Test
    public void testGetIniciovigencia() {
        System.out.println("getIniciovigencia");
        Poliza instance = new Poliza();
        Date expResult = null;
        Date result = instance.getIniciovigencia();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIniciovigencia method, of class Poliza.
     */
    @Test
    public void testSetIniciovigencia() {
        System.out.println("setIniciovigencia");
        Date iniciovigencia = null;
        Poliza instance = new Poliza();
        instance.setIniciovigencia(iniciovigencia);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinvigencia method, of class Poliza.
     */
    @Test
    public void testGetFinvigencia() {
        System.out.println("getFinvigencia");
        Poliza instance = new Poliza();
        Date expResult = null;
        Date result = instance.getFinvigencia();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFinvigencia method, of class Poliza.
     */
    @Test
    public void testSetFinvigencia() {
        System.out.println("setFinvigencia");
        Date finvigencia = null;
        Poliza instance = new Poliza();
        instance.setFinvigencia(finvigencia);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrima method, of class Poliza.
     */
    @Test
    public void testGetPrima() {
        System.out.println("getPrima");
        Poliza instance = new Poliza();
        BigDecimal expResult = null;
        BigDecimal result = instance.getPrima();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrima method, of class Poliza.
     */
    @Test
    public void testSetPrima() {
        System.out.println("setPrima");
        BigDecimal prima = null;
        Poliza instance = new Poliza();
        instance.setPrima(prima);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNota method, of class Poliza.
     */
    @Test
    public void testGetNota() {
        System.out.println("getNota");
        Poliza instance = new Poliza();
        String expResult = "";
        String result = instance.getNota();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNota method, of class Poliza.
     */
    @Test
    public void testSetNota() {
        System.out.println("setNota");
        String nota = "";
        Poliza instance = new Poliza();
        instance.setNota(nota);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCaratula method, of class Poliza.
     */
    @Test
    public void testGetCaratula() {
        System.out.println("getCaratula");
        Poliza instance = new Poliza();
        Caratula expResult = null;
        Caratula result = instance.getCaratula();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCaratula method, of class Poliza.
     */
    @Test
    public void testSetCaratula() {
        System.out.println("setCaratula");
        Caratula caratula = null;
        Poliza instance = new Poliza();
        instance.setCaratula(caratula);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPolizaAuto method, of class Poliza.
     */
    @Test
    public void testGetPolizaAuto() {
        System.out.println("getPolizaAuto");
        Poliza instance = new Poliza();
        PolizaAuto expResult = null;
        PolizaAuto result = instance.getPolizaAuto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPolizaAuto method, of class Poliza.
     */
    @Test
    public void testSetPolizaAuto() {
        System.out.println("setPolizaAuto");
        PolizaAuto polizaAuto = null;
        Poliza instance = new Poliza();
        instance.setPolizaAuto(polizaAuto);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContratante method, of class Poliza.
     */
    @Test
    public void testGetContratante() {
        System.out.println("getContratante");
        Poliza instance = new Poliza();
        Asegurado expResult = null;
        Asegurado result = instance.getContratante();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setContratante method, of class Poliza.
     */
    @Test
    public void testSetContratante() {
        System.out.println("setContratante");
        Asegurado contratante = null;
        Poliza instance = new Poliza();
        instance.setContratante(contratante);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAseguradora method, of class Poliza.
     */
    @Test
    public void testGetAseguradora() {
        System.out.println("getAseguradora");
        Poliza instance = new Poliza();
        Aseguradora expResult = null;
        Aseguradora result = instance.getAseguradora();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAseguradora method, of class Poliza.
     */
    @Test
    public void testSetAseguradora() {
        System.out.println("setAseguradora");
        Aseguradora aseguradora = null;
        Poliza instance = new Poliza();
        instance.setAseguradora(aseguradora);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTitular method, of class Poliza.
     */
    @Test
    public void testGetTitular() {
        System.out.println("getTitular");
        Poliza instance = new Poliza();
        Cliente expResult = null;
        Cliente result = instance.getTitular();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTitular method, of class Poliza.
     */
    @Test
    public void testSetTitular() {
        System.out.println("setTitular");
        Cliente titular = null;
        Poliza instance = new Poliza();
        instance.setTitular(titular);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConductocobro method, of class Poliza.
     */
    @Test
    public void testGetConductocobro() {
        System.out.println("getConductocobro");
        Poliza instance = new Poliza();
        ConductoCobro expResult = null;
        ConductoCobro result = instance.getConductocobro();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConductocobro method, of class Poliza.
     */
    @Test
    public void testSetConductocobro() {
        System.out.println("setConductocobro");
        ConductoCobro conductocobro = null;
        Poliza instance = new Poliza();
        instance.setConductocobro(conductocobro);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEstado method, of class Poliza.
     */
    @Test
    public void testGetEstado() {
        System.out.println("getEstado");
        Poliza instance = new Poliza();
        EstadoPoliza expResult = null;
        EstadoPoliza result = instance.getEstado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEstado method, of class Poliza.
     */
    @Test
    public void testSetEstado() {
        System.out.println("setEstado");
        EstadoPoliza estado = null;
        Poliza instance = new Poliza();
        instance.setEstado(estado);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFormapago method, of class Poliza.
     */
    @Test
    public void testGetFormapago() {
        System.out.println("getFormapago");
        Poliza instance = new Poliza();
        FormaPago expResult = null;
        FormaPago result = instance.getFormapago();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFormapago method, of class Poliza.
     */
    @Test
    public void testSetFormapago() {
        System.out.println("setFormapago");
        FormaPago formapago = null;
        Poliza instance = new Poliza();
        instance.setFormapago(formapago);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrimamoneda method, of class Poliza.
     */
    @Test
    public void testGetPrimamoneda() {
        System.out.println("getPrimamoneda");
        Poliza instance = new Poliza();
        Moneda expResult = null;
        Moneda result = instance.getPrimamoneda();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrimamoneda method, of class Poliza.
     */
    @Test
    public void testSetPrimamoneda() {
        System.out.println("setPrimamoneda");
        Moneda primamoneda = null;
        Poliza instance = new Poliza();
        instance.setPrimamoneda(primamoneda);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRamo method, of class Poliza.
     */
    @Test
    public void testGetRamo() {
        System.out.println("getRamo");
        Poliza instance = new Poliza();
        Ramo expResult = null;
        Ramo result = instance.getRamo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRamo method, of class Poliza.
     */
    @Test
    public void testSetRamo() {
        System.out.println("setRamo");
        Ramo ramo = null;
        Poliza instance = new Poliza();
        instance.setRamo(ramo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPolizaVida method, of class Poliza.
     */
    @Test
    public void testGetPolizaVida() {
        System.out.println("getPolizaVida");
        Poliza instance = new Poliza();
        PolizaVida expResult = null;
        PolizaVida result = instance.getPolizaVida();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPolizaVida method, of class Poliza.
     */
    @Test
    public void testSetPolizaVida() {
        System.out.println("setPolizaVida");
        PolizaVida polizaVida = null;
        Poliza instance = new Poliza();
        instance.setPolizaVida(polizaVida);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPolizaGmm method, of class Poliza.
     */
    @Test
    public void testGetPolizaGmm() {
        System.out.println("getPolizaGmm");
        Poliza instance = new Poliza();
        PolizaGmm expResult = null;
        PolizaGmm result = instance.getPolizaGmm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPolizaGmm method, of class Poliza.
     */
    @Test
    public void testSetPolizaGmm() {
        System.out.println("setPolizaGmm");
        PolizaGmm polizaGmm = null;
        Poliza instance = new Poliza();
        instance.setPolizaGmm(polizaGmm);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReciboList method, of class Poliza.
     */
    @Test
    public void testGetReciboList() {
        System.out.println("getReciboList");
        Poliza instance = new Poliza();
        List<Recibo> expResult = null;
        List<Recibo> result = instance.getReciboList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReciboList method, of class Poliza.
     */
    @Test
    public void testSetReciboList() {
        System.out.println("setReciboList");
        List<Recibo> reciboList = null;
        Poliza instance = new Poliza();
        instance.setReciboList(reciboList);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Poliza.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Poliza instance = new Poliza();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Poliza.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object object = null;
        Poliza instance = new Poliza();
        boolean expResult = false;
        boolean result = instance.equals(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Poliza.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Poliza instance = new Poliza();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Poliza.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Poliza instance = new Poliza();
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nombreProperty method, of class Poliza.
     */
    @Test
    public void testNombreProperty() {
        System.out.println("nombreProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.nombreProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of numeroProperty method, of class Poliza.
     */
    @Test
    public void testNumeroProperty() {
        System.out.println("numeroProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.numeroProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aseguradoraProperty method, of class Poliza.
     */
    @Test
    public void testAseguradoraProperty() {
        System.out.println("aseguradoraProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.aseguradoraProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ramoProperty method, of class Poliza.
     */
    @Test
    public void testRamoProperty() {
        System.out.println("ramoProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.ramoProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of productoProperty method, of class Poliza.
     */
    @Test
    public void testProductoProperty() {
        System.out.println("productoProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.productoProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of planProperty method, of class Poliza.
     */
    @Test
    public void testPlanProperty() {
        System.out.println("planProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.planProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of primaProperty method, of class Poliza.
     */
    @Test
    public void testPrimaProperty() {
        System.out.println("primaProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.primaProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPolizaListProperty method, of class Poliza.
     */
    @Test
    public void testGetPolizaListProperty() {
        System.out.println("getPolizaListProperty");
        Poliza instance = new Poliza();
        List<? extends ObservableTreeItem> expResult = null;
        List<? extends ObservableTreeItem> result = instance.getPolizaListProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aseguradoProperty method, of class Poliza.
     */
    @Test
    public void testAseguradoProperty() {
        System.out.println("aseguradoProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.aseguradoProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of polizaProperty method, of class Poliza.
     */
    @Test
    public void testPolizaProperty() {
        System.out.println("polizaProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.polizaProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finVigenciaProperty method, of class Poliza.
     */
    @Test
    public void testFinVigenciaProperty() {
        System.out.println("finVigenciaProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.finVigenciaProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inicioVigenciaProperty method, of class Poliza.
     */
    @Test
    public void testInicioVigenciaProperty() {
        System.out.println("inicioVigenciaProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.inicioVigenciaProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of faltanProperty method, of class Poliza.
     */
    @Test
    public void testFaltanProperty() {
        System.out.println("faltanProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.faltanProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of estadoProperty method, of class Poliza.
     */
    @Test
    public void testEstadoProperty() {
        System.out.println("estadoProperty");
        Poliza instance = new Poliza();
        StringProperty expResult = null;
        StringProperty result = instance.estadoProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generarRecibos method, of class Poliza.
     */
    @Test
    public void testGenerarRecibos() {
        System.out.println("generarRecibos");
        int recibosPagados = 6;
        BigDecimal importeConDerechoDePoliza = new BigDecimal(10000.14);
        BigDecimal importeSubsecuente = new BigDecimal(9500.14);
        Poliza instance = new Poliza();
        instance.setIniciovigencia(Date.from(LocalDate.of(2019, Month.JANUARY, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        instance.setFinvigencia(Date.from(LocalDate.of(2020, Month.JANUARY, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        instance.setFormapago(new FormaPago(Globals.FORMA_PAGO_MENSUAL));
        instance.generarRecibos(recibosPagados, importeConDerechoDePoliza, importeSubsecuente);
        // TODO review the generated test code and remove the default call to fail.
        instance.getReciboList().forEach(recibo -> System.out.println(recibo.getCubredesde() + ", " + recibo.getCubrehasta() + ", " + recibo.getImporte()));
        Recibo ultimoRecibo = instance.getReciboList().get(instance.getReciboList().size() - 1);
        assertEquals(ultimoRecibo.getCubrehasta(), instance.getFinvigencia());
    }

}
