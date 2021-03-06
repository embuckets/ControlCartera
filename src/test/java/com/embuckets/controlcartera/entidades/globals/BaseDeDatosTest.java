/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

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
    public void setUp() throws Exception {
        bd = BaseDeDatos.getInstance();
    }

    @After
    public void tearDown() {
        bd.close();
    }
//
//    /**
//     * Test of getInstance method, of class BaseDeDatos.
//     */
//    @Test
//    public void testGetInstance() {
//        System.out.println("getInstance");
//        BaseDeDatos expResult = null;
//        BaseDeDatos result = BaseDeDatos.getInstance();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getInstance method, of class BaseDeDatos.
//     */
//    @Test
//    public void testFiltrarPolizas() {
//        System.out.println("testFiltrarPolizas");
//        String numeroPoliza = "123";
//        String aseguradora = "Axa";
//        String ramo = "Autos";
//        List<Poliza> polizas = bd.buscarPolizasPor(numeroPoliza, aseguradora, ramo);
//        polizas.stream().forEach(p -> System.out.println(p));
//        
//    }
//
//    /**
//     * Test of getEntityManager method, of class BaseDeDatos.
//     */
//    @Test
//    public void testGetEntityManager() {
//        System.out.println("getEntityManager");
//        BaseDeDatos instance = null;
//        EntityManager expResult = null;
//        EntityManager result = instance.getEntityManager();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of create method, of class BaseDeDatos.
//     */
//    @Test
//    public void testCreate() {
//        System.out.println("createAsegurado");
//        int random = new Random().nextInt(100);
//        Asegurado asegurado = new Asegurado();
//        asegurado.setCliente(new Cliente());
//        asegurado.getCliente().setNombre("Cliente" + random);
//        asegurado.getCliente().setApellidopaterno("Paterno" + random);
//        asegurado.getCliente().setApellidomaterno("Materno" + random);
//        asegurado.getCliente().setNacimiento(LocalDate.of(1994, Month.MARCH, 23));
//        asegurado.setTipopersona(new TipoPersona("Fisica"));
//        asegurado.setIddomicilio(new Domicilio());
//        asegurado.getIddomicilio().setCalle("Calle" + random);
//        asegurado.getIddomicilio().setExterior("Exterior" + random);
//        List<Delegacion> delegaciones = bd.getAll(Delegacion.class);
//        asegurado.getIddomicilio().setDelegacion(delegaciones.get(5));
//        List<Estado> estados = bd.getAll(Estado.class);
//        asegurado.getIddomicilio().setEstado(estados.get(5));
////        bd.create(asegurado);
//        assertNotNull(asegurado.getId());
//        assertNotNull(bd.getById(Asegurado.class, asegurado.getId()));
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testgetById() {
//        System.out.println("getById");
//        Asegurado asegurado = bd.getById(Asegurado.class, 1513);
//        assertNotNull(asegurado.getCliente());
//        assertNotNull(asegurado.getIddomicilio());
//        assertNotNull(asegurado.getIddomicilio().getDelegacion());
//        assertNotNull(asegurado.getIddomicilio().getCalle());
//        assertNotNull(asegurado.getTipopersona());
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testgetAll() {
//        System.out.println("testgetAll");
//        List<Asegurado> asegurados = bd.getAll(Asegurado.class);
//        assertNotNull(asegurados);
//        // TODO review the generated test code and remove the default call to fail.
//    }
//    
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testBeneficiarioEquals() {
//        System.out.println("testgetAll");
//        Asegurado daniel = bd.buscarAseguradosPorNombre("daniel", "hernandez", "segovia").get(0);
//        PolizaGmm polizaGmm = daniel.getPolizaList().stream().filter(p -> p.getRamo().getRamo().equals(Globals.POLIZA_RAMO_GM)).findFirst().get().getPolizaGmm();
//        PolizaVida polizaVida = daniel.getPolizaList().stream().filter(p -> p.getRamo().getRamo().equals(Globals.POLIZA_RAMO_VIDA)).findFirst().get().getPolizaVida();
//        
//        
//        List<Dependiente> dependientes = new ArrayList<>();
//        polizaGmm.getClienteList().forEach(c -> dependientes.add(new Dependiente(c, polizaGmm)));
//        dependientes.stream().forEach(d -> {
//            System.out.println("Contains: " + d.getCliente()+ " - " + polizaGmm.getClienteList().contains(d.getCliente()));
//            System.out.println("Hash: " + d.hashCode());
//            System.out.println("Remove: " + polizaGmm.getClienteList().remove(d.getCliente()));
//        });
//        
//        
//        
//        
//        
//        
//        
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testImportar() {
//        System.out.println("testImportar");
//        try {
//            List<Asegurado> importados = new ExcelImporter().importar(new File("C:/Users/emilio/Desktop/plantilla-cartera-copy.xlsx"));
//            bd.importarAsegurados(importados);
//        } catch (Exception ex) {
//            Logger.getLogger(BaseDeDatosTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testgetByIdAndEdit() {
//        System.out.println("getByIdAndEdit");
//        Asegurado asegurado = bd.getById(Asegurado.class, 1508);
//        assertNotNull(asegurado);
//        String nuevoNombre = "Nuevo Nombre";
//        asegurado.getCliente().setNombre(nuevoNombre);
////        asegurado.getEmailList().get(0).setTipoemail(new TipoEmail(Globals.EMAIL_TIPO_TRABAJO));
//        Asegurado edited;
//        try {
//            edited = bd.edit(asegurado);
//            assertEquals(edited.getCliente().getNombre(), nuevoNombre);
//            assertEquals(asegurado, edited);
//        } catch (Exception ex) {
//            Logger.getLogger(BaseDeDatosTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testGetAndRemove() {
//        System.out.println("GetAndRemove");
//        int id = 1508;
//        Asegurado asegurado = bd.getById(Asegurado.class, id);
//        assertNotNull(asegurado);
//        try {
//            bd.remove(asegurado);
//        } catch (Exception ex) {
//            Logger.getLogger(BaseDeDatosTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        assertNull(bd.getById(Asegurado.class, id));
////        assertNull(asegurado);
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testCreatePolizaVida() {
//        System.out.println("testCreatePoliza");
//        Asegurado asegurado = bd.getById(Asegurado.class, 1513);
//        assertNotNull(asegurado);
//
//        Poliza poliza1 = new Poliza();
//        poliza1.setNumero("numeor1");
//        poliza1.setAseguradora(new Aseguradora("GNP"));
//        poliza1.setRamo(new Ramo("vida"));
//        poliza1.setProducto("producto");
//        poliza1.setPlan("plan");
//        poliza1.setPrima(new BigDecimal(21456));
//        poliza1.setPrimamoneda(new Moneda("pesos"));
//        poliza1.setIniciovigencia(LocalDate.now());
//        poliza1.setFinvigencia(LocalDate.now().plusMonths(12));
//        poliza1.setEstado(new EstadoPoliza("Vigente"));
//        poliza1.setConductocobro(new ConductoCobro("agente"));
//        poliza1.setFormapago(new FormaPago("semestral"));
//        Cliente benef = new Cliente("beneficiario1", "hijo", "hijo");
//        benef.setNacimiento(LocalDate.of(2016, Month.JANUARY, 9));
//        poliza1.setPolizaVida(new PolizaVida());
//        poliza1.getPolizaVida().setPoliza(poliza1);
//        poliza1.getPolizaVida().setSumaasegurada(BigDecimal.valueOf(50000));
//        poliza1.getPolizaVida().setSumaaseguradamoneda(new Moneda("Dolares"));
//        poliza1.getPolizaVida().getClienteList().add(benef);
//        poliza1.generarRecibos(3, new BigDecimal(10123.12), new BigDecimal(9123.12));
//
//        poliza1.setTitular(asegurado.getCliente());
//        poliza1.setContratante(asegurado);
//
//        asegurado.getPolizaList().add(poliza1);
////        bd.create(poliza1);
//        Poliza retrieved = bd.getById(Poliza.class, poliza1.getIdpoliza());
//        assertNotNull(retrieved);
//        assertNotNull(retrieved.getPolizaVida());
//        assertNotNull(retrieved.getPolizaVida().getClienteList());
//        assertTrue(retrieved.getPolizaVida().getClienteList().size() > 0);
//        assertNotNull(benef.getIdcliente());
//
////        assertNull(asegurado);
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testCreatePolizaAuto() {
//        System.out.println("testCreatePoliza");
//        Asegurado asegurado = bd.getById(Asegurado.class, 1513);
//        assertNotNull(asegurado);
//
//        Poliza poliza2 = new Poliza();
//        poliza2.setNumero("numeor2");
//        poliza2.setAseguradora(new Aseguradora("GNP"));
//        poliza2.setRamo(new Ramo("autos"));
//        poliza2.setProducto("producto");
//        poliza2.setPlan("plan");
//        poliza2.setPrima(new BigDecimal(54789));
//        poliza2.setPrimamoneda(new Moneda("pesos"));
//        poliza2.setIniciovigencia(LocalDate.now());
//        poliza2.setFinvigencia(LocalDate.now().plusMonths(12));
//        poliza2.setEstado(new EstadoPoliza("Cancelada"));
//        poliza2.setPolizaAuto(new PolizaAuto());
//        poliza2.getPolizaAuto().setPoliza(poliza2);//changed
//        poliza2.getPolizaAuto().setSumaaseguradaauto(new SumaAseguradaAuto("Factura"));
//        Auto auto = new Auto();
//        auto.setDescripcion("STD 4PT RL");
//        auto.setMarca("VW");
//        auto.setSubmarca("Jetta");
//        auto.setModelo(Year.of(2016));
//        auto.setIdpoliza(poliza2.getPolizaAuto());
//        poliza2.getPolizaAuto().getAutoList().add(auto);
//        poliza2.setConductocobro(new ConductoCobro("agente"));
//        poliza2.setFormapago(new FormaPago("Semestral"));
//        poliza2.generarRecibos(4, new BigDecimal(10123.12), new BigDecimal(9123.12));
//
//        poliza2.setTitular(asegurado.getCliente());
//        poliza2.setContratante(asegurado);
//
//        asegurado.getPolizaList().add(poliza2);
////        bd.create(poliza2);
//        Poliza retrieved = bd.getById(Poliza.class, poliza2.getIdpoliza());
//        assertNotNull(retrieved);
//        assertNotNull(retrieved.getPolizaAuto());
//        assertNotNull(retrieved.getPolizaAuto().getAutoList());
//        assertTrue(retrieved.getPolizaAuto().getAutoList().size() > 0);
//        assertNotNull(auto.getIdauto());
//
////        assertNull(asegurado);
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testGetPolizaVida() {
//        System.out.println("testGetPolizaVida");
//        Poliza retrieved = bd.getById(Poliza.class, 13);
//        assertNotNull(retrieved);
//        assertNotNull(retrieved.getPolizaVida());
//        assertNotNull(retrieved.getPolizaVida().getClienteList());
//        assertTrue(retrieved.getPolizaVida().getClienteList().size() > 0);
////        assertNull(asegurado);
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testNotificacionEdge() {
//        System.out.println("testNotificacionEdge");
//        List<NotificacionCumple> cumples = bd.getCumplesEntre(LocalDate.of(2019, Month.APRIL, 30), LocalDate.of(2019, Month.APRIL, 30));
//        for (NotificacionCumple c : cumples) {
//            int d1 = c.getCliente().getNacimiento().getDayOfYear();
//            int d2 = LocalDate.of(2019, Month.MAY, 01).getDayOfYear();
//            System.out.println(c.getCliente().getNacimiento() + ": d1 = " + d1 + " - d2 = " + d2 + " -> " + (d1 <= d2));
//            System.out.println(c.getCliente().getNacimiento() + ": leap = " + c.getCliente().getNacimiento().isLeapYear());
//        }
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testGetPolizaAutoAndRemove() {
//        System.out.println("testGetPolizaAutoAndRemove");
//        int id = 16;
//        Poliza retrieved = bd.getById(Poliza.class, id);
//        assertNotNull(retrieved);
//        try {
//            bd.remove(retrieved);
//        } catch (Exception ex) {
//            Logger.getLogger(BaseDeDatosTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        assertNull(bd.getById(Poliza.class, id));
////        assertNull(asegurado);
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testCreateAuto() {
//        System.out.println("testCreateAuto");
//        int id = 5;
//        Poliza poliza = bd.getById(Poliza.class, id);
//        assertNotNull(poliza);
//        Auto nuevoAuto = new Auto();
//        nuevoAuto.setDescripcion("NUEVO AUTO");
//        nuevoAuto.setMarca("NUEVO AUTO");
//        nuevoAuto.setSubmarca("NUEVO AUTO");
//        nuevoAuto.setModelo(Year.parse("2017"));
//        nuevoAuto.setIdpoliza(poliza.getPolizaAuto());
////        bd.create(nuevoAuto);
//        assertNotNull(nuevoAuto.getIdauto());
////        assertNull(asegurado);
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testRemoveAuto() {
//        System.out.println("testRemoveAuto");
//        int id = 5;
//        Poliza poliza = bd.getById(Poliza.class, id);
//        assertNotNull(poliza);
//        Auto auto = poliza.getPolizaAuto().getAutoList().get(0);
//        try {
//            bd.remove(auto);
//        } catch (Exception ex) {
//            Logger.getLogger(BaseDeDatosTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        assertNull(bd.getById(Auto.class, auto.getIdauto()));
////        assertNull(asegurado);
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testGetCumplesPendientesEntre() {
//        System.out.println("testGetCumplesPendientesEntre");
//        List<NotificacionCumple> notificacionCumples = bd.getCumplesPendientesEntre(LocalDate.of(2019, Month.MARCH, 25), LocalDate.of(2019, Month.MARCH, 30));
//        assertEquals(notificacionCumples.size(), 2);
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testGetRecibosPendientesEntre() {
//        System.out.println("testGetRecibosPendientesEntre");
//        List<NotificacionRecibo> notificacionCumples = bd.getRecibosPendientesEntre(LocalDate.of(2019, Month.MARCH, 25), LocalDate.of(2019, Month.APRIL, 30));
//        notificacionCumples.stream().forEach(n -> System.out.println(n.getRecibo().getIdrecibo()));
//    }
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testRenovacionesEntre() {
//        System.out.println("testRenovacionesEntre");
//        LocalDate start = LocalDate.of(2019, Month.MARCH, 25);
//        LocalDate end = LocalDate.of(2019, Month.APRIL, 30);
//
//        List<Poliza> notificacionCumples = bd.getRenovacionesEntre(start, end);
//        Predicate<Poliza> predicate = (p) -> {
//            return p.getFinvigencia().isAfter(start) && p.getFinvigencia().isBefore(end) && p.getEstado().getEstado().equalsIgnoreCase(Globals.POLIZA_ESTADO_VIGENTE);
//        };
//        assertTrue(notificacionCumples.stream().allMatch(predicate));
//    }
//
//
//    /**
//     * Test of getById method, of class BaseDeDatos.
//     */
//    @Test
//    public void testGetClassName() {
//        System.out.println("testGetClassName");
//        System.out.println(this.getClass().getName());
//        System.out.println(this.getClass().getCanonicalName());
//        System.out.println(this.getClass().getSimpleName());
//        System.out.println(this.getClass().getTypeName());
////        assertNull(asegurado);
//    }
//
//    /**
//     * Test of close method, of class BaseDeDatos.
//     */
//    @Test
//    public void testClose() {
//        System.out.println("close");
//        bd.close();
//        // TODO review the generated test code and remove the default call to fail.
//    }

}
