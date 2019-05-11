/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.Auto;
import com.embuckets.controlcartera.entidades.Beneficiario;
import com.embuckets.controlcartera.entidades.Caratula;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.Cobranza;
import com.embuckets.controlcartera.entidades.ConductoCobro;
import com.embuckets.controlcartera.entidades.Delegacion;
import com.embuckets.controlcartera.entidades.Dependiente;
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.DocumentoRecibo;
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.entidades.EstadoNotificacion;
import com.embuckets.controlcartera.entidades.EstadoPoliza;
import com.embuckets.controlcartera.entidades.FormaPago;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.entidades.Recibo;
import com.embuckets.controlcartera.entidades.SumaAseguradaAuto;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TipoDocumentoAsegurado;
import com.embuckets.controlcartera.entidades.TipoEmail;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.TipoTelefono;
import com.embuckets.controlcartera.entidades.controladores.AseguradoJpaController;
import com.embuckets.controlcartera.entidades.controladores.AseguradoraJpaController;
import com.embuckets.controlcartera.entidades.controladores.AutoJpaController;
import com.embuckets.controlcartera.entidades.controladores.BeneficiarioJpaController;
import com.embuckets.controlcartera.entidades.controladores.CaratulaJpaController;
import com.embuckets.controlcartera.entidades.controladores.ClienteJpaController;
import com.embuckets.controlcartera.entidades.controladores.CobranzaJpaController;
import com.embuckets.controlcartera.entidades.controladores.ConductoCobroJpaController;
import com.embuckets.controlcartera.entidades.controladores.DelegacionJpaController;
import com.embuckets.controlcartera.entidades.controladores.DependienteJpaController;
import com.embuckets.controlcartera.entidades.controladores.DocumentoAseguradoJpaController;
import com.embuckets.controlcartera.entidades.controladores.DocumentoReciboJpaController;
import com.embuckets.controlcartera.entidades.controladores.DomicilioJpaController;
import com.embuckets.controlcartera.entidades.controladores.EmailJpaController;
import com.embuckets.controlcartera.entidades.controladores.EstadoJpaController;
import com.embuckets.controlcartera.entidades.controladores.EstadoNotificacionJpaController;
import com.embuckets.controlcartera.entidades.controladores.EstadoPolizaJpaController;
import com.embuckets.controlcartera.entidades.controladores.FormaPagoJpaController;
import com.embuckets.controlcartera.entidades.controladores.JpaController;
import com.embuckets.controlcartera.entidades.controladores.MonedaJpaController;
import com.embuckets.controlcartera.entidades.controladores.NotificacionCumpleJpaController;
import com.embuckets.controlcartera.entidades.controladores.NotificacionReciboJpaController;
import com.embuckets.controlcartera.entidades.controladores.PolizaAutoJpaController;
import com.embuckets.controlcartera.entidades.controladores.PolizaGmmJpaController;
import com.embuckets.controlcartera.entidades.controladores.PolizaJpaController;
import com.embuckets.controlcartera.entidades.controladores.PolizaVidaJpaController;
import com.embuckets.controlcartera.entidades.controladores.RamoJpaController;
import com.embuckets.controlcartera.entidades.controladores.ReciboJpaController;
import com.embuckets.controlcartera.entidades.controladores.SumaAseguradaAutoJpaController;
import com.embuckets.controlcartera.entidades.controladores.TelefonoJpaController;
import com.embuckets.controlcartera.entidades.controladores.TipoDocumentoAseguradoJpaController;
import com.embuckets.controlcartera.entidades.controladores.TipoEmailJpaController;
import com.embuckets.controlcartera.entidades.controladores.TipoPersonaJpaController;
import com.embuckets.controlcartera.entidades.controladores.TipoTelefonoJpaController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.derby.tools.ij;

/**
 *
 * @author emilio
 */
public class BaseDeDatos {

    private static final Logger logger = LogManager.getLogger(BaseDeDatos.class);

    private static BaseDeDatos bd;
    private EntityManagerFactory emf;
    private EntityManager em;
    private Map<Class, JpaController> controllers;

    private BaseDeDatos() throws SQLException, Exception {
        if (Files.notExists(Paths.get("./cartera"))) {
            createDB();
        }
        this.emf = Persistence.createEntityManagerFactory("cartera");
        this.em = this.emf.createEntityManager();
        controllers = createControllers();
    }

    /**
     * Obtiene la unica instancia de la base de datos
     * @return la base de datos
     * @throws Exception - si no se puede conectar a la base de datos
     */
    public static BaseDeDatos getInstance() throws Exception {
        if (bd == null) {
            bd = new BaseDeDatos();
        }
        return bd;
    }

    /**
     * Obtiene el EntityManager. 
     * @return el entity manager
     */
    public EntityManager getEntityManager() {
        if (em != null) {
            em = emf.createEntityManager();
        }
        return em;
    }

    private void clearAndFlush() {
        em.clear();
        if (em.getTransaction().isActive()) {
            em.flush();
        }
    }

    /**
     * Inserta el objeto especificado. Delega la operacion al controlador de la entidad
     * @param object la entidad a insertar
     * @throws Exception - si falla la operacion. Se hace un rollback
     */
    public void create(Object object) throws Exception {
        try {
            controllers.get(object.getClass()).create(object);
        } catch (EntityExistsException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
//            clearAndFlush();
            throw ex;
        } catch (Exception ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

    /**
     * Busca todos los registros de la clase especificada
     * @param <T> el tipo de la clase especificada
     * @param clazz la clase especificada
     * @return todos los registros de la clase especificada
     */
    public <T> List<T> getAll(Class clazz) {
        return controllers.get(clazz).getAll();
    }

    /**
     * Busca el registro de la entidad especificada con el identificador especificado.
     * @param <T> el tipo del registro
     * @param clazz la clase del registro
     * @param id el identificador del registro
     * @return El registro con el identificador especificado. <code>null</code> si no lo encuentra.
     */
    public <T> T getById(Class clazz, int id) {
        return controllers.get(clazz).getById(id);
    }

    /**
     * Actualiza el registro especificado. Delega la operacion al controlador de la entidad.
     * @param <T> el tipo de la entidad
     * @param object la entidad a actualizar
     * @return la entidad actualizada
     * @throws Exception - si falla la operacion. Se hace un rollback
     */
    public <T> T edit(Object object) throws Exception {
        try {
            return controllers.get(object.getClass()).edit(object);
        } catch (Exception ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

    /**
     * Cambia el titular de la poliza.
     * @param poliza poliza a cambiar el titular
     * @throws Exception - si falla la operacion. Se hace un rollback
     */
    public void cambiarTitular(Poliza poliza) throws Exception {
        try {
            ((PolizaJpaController) controllers.get(Poliza.class)).editarTitular(poliza);
        } catch (Exception ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

    /**
     * Elimina la entidad especificada junto con sus dependencias. Delega la operacion al controlador de la entidad.
     * @param object entidad a eliminar
     * @throws Exception - si falla la operacion. Se hace un rollback
     */
    public void remove(Object object) throws Exception {
        try {
            controllers.get(object.getClass()).remove(object);
        } catch (IllegalArgumentException ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
//            clearAndFlush();
            throw ex;
        } catch (Exception ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

    /**
     * Cierra la conexion a la base de datos.
     */
    public void close() {
        if (em != null) {
            if (em.getTransaction().isActive()) {
                try {
                    em.getTransaction().commit();
                } catch (RollbackException ex) {
                    logger.error(Logging.Exception_MESSAGE, ex);
                }
            }
            em.close();
            em = null;
        }
        if (emf != null) {
            emf.close();
            emf = null;
        }
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            if (((ex.getErrorCode() == 50000)
                    && ("XJ015".equals(ex.getSQLState())))) {
                // we got the expected exception
//                System.out.println("Derby shut down normally");
                // Note that for single database shutdown, the expected
                // SQL state is "08006", and the error code is 45000.
            } else {
                logger.error("Error al apagar Base de datos", ex);
                // if the error code or SQLState is different, we have
                // an unexpected exception (shutdown failed)
                //                System.err.println("Derby did not shut down normally");
            }
        }
    }

    /**
     * Inserta la poliza nueva y cambia el estado de la poliza vieja a "Renovada"
     * @param vieja poliza a renovar
     * @param nueva poliza a insertar
     * @throws Exception - si falla la operacion
     */
    public void renovarPoliza(Poliza vieja, Poliza nueva) throws Exception {
        try {
            ((PolizaJpaController) controllers.get(Poliza.class)).renovar(vieja, nueva);
        } catch (Exception ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

    /**
     * Busca las notificaciones de cumpleaños que se encuentren dentro de las fechas especificadas sin importar el estado de notificacion.
     * @param start fecha de inicio
     * @param end fecha de fin
     * @return las notificaciones de los asegurados que cumplan dentro del rango especificado
     */
    public List<NotificacionCumple> getCumplesEntre(LocalDate start, LocalDate end) {
        try {
            return ((NotificacionCumpleJpaController) controllers.get(NotificacionCumple.class)).getNotificacionesEntre(start, end);
        } catch (Exception ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }
    }

    /**
     * Busca las notificaciones de cumpleaños pendientes de enviar dentro del rango especificado.
     * @param start fecha de inicio
     * @param end fecha de fin
     * @return las notificaciones pendientes de enviar de los asegurados que cumplen dentro del rango especificado
     */
    public List<NotificacionCumple> getCumplesPendientesEntre(LocalDate start, LocalDate end) {
        return ((NotificacionCumpleJpaController) controllers.get(NotificacionCumple.class)).getNotificacionesPendientesEntre(start, end);
    }

    /**
     * Busca las notificaciones de recibos pendientes de enviar dentro del rango especificado.
     * @param start fecha de inicio
     * @param end fecha de fin
     * @return las notificaciones pendientes de enviar de los recibos pendientes de pago
     */
    public List<NotificacionRecibo> getRecibosPendientesEntre(LocalDate start, LocalDate end) {
        return ((NotificacionReciboJpaController) controllers.get(NotificacionRecibo.class)).getNotificacionesPendientesEntre(start, end);
    }

    /**
     * Busca las notificaciones de recibos dentro del rango especificado sin importar el estado de cobranza.
     * @param start fecha de inicio
     * @param end fecha de fin
     * @return las notificaciones pendientes de enviar de los recibos dentro del rango
     */
    public List<NotificacionRecibo> getRecibosEntre(LocalDate start, LocalDate end) {
        return ((NotificacionReciboJpaController) controllers.get(NotificacionRecibo.class)).getNotificacionesEntre(start, end);
    }

    /**
     * Busca las polizas cuya fecha de fin de vigencia se encuentre dentro del rango especificado
     * @param start fecha de inicio
     * @param end fecha de fin
     * @return todas las polizas con fin de vigencia dentro del rango
     */
    public List<Poliza> getRenovacionesEntre(LocalDate start, LocalDate end) {
        return ((PolizaJpaController) controllers.get(Poliza.class)).getRenovacionesEntre(start, end);
    }

    /**
     * Inserta los asegurados especificados. Delega la operacion al controlador de la entidad
     * @param asegurados asegurados a insertar
     * @throws Exception - si falla la operacion
     */
    public void importarAsegurados(List<Asegurado> asegurados) throws Exception {
        try {
            ((AseguradoJpaController) controllers.get(Asegurado.class)).importarAsegurados(asegurados);
        } catch (Exception ex) {
            logger.error(Logging.Exception_MESSAGE, ex);
            throw ex;
        }

    }

    
    private static void createDB() throws FileNotFoundException, IOException, SQLException, Exception {
        Connection conn = null;
        try (InputStream in = new FileInputStream("bd/crear-cartera.sql");
                OutputStream out = new FileOutputStream("logs/logfile.log")) {
            conn = createConnection();
            Statement s = conn.createStatement();
            s.executeUpdate("set schema APP");

            int errores = ij.runScript(conn, in, "utf-8", out, "utf-8");

            if (errores != 0) {
                throw new Exception("Error al crear base de datos");
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
//            try {
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException ex) {
//                if (((ex.getErrorCode() == 50000)
//                        && ("XJ015".equals(ex.getSQLState())))) {
//                    // we got the expected exception
////                System.out.println("Derby shut down normally");
//                    // Note that for single database shutdown, the expected
//                    // SQL state is "08006", and the error code is 45000.
//                } else {
//                    logger.error("Error al apagar Base de datos", ex);
//                    // if the error code or SQLState is different, we have
//                    // an unexpected exception (shutdown failed)
//                    //                System.err.println("Derby did not shut down normally");
//                }
//            }
        }
    }

    private static Connection createConnection() throws SQLException, ClassNotFoundException {
        Properties props = new Properties();
        props.put("create", "true");
        props.put("user", "emilio");
        props.put("password", "emilio");
        props.put("collation", "TERRITORY_BASED:PRIMARY");
        props.put("territory", "es_MX");
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        
        return DriverManager.getConnection("jdbc:derby:cartera", props);
    }

    /**
     * Busca los asegurados por nombre y apellidos.
     * En caso de que algun parametro sea <code>null</code> o vacio se omite del query. 
     * Busca asegurado que contengan parcialmente los parametros especificados, por ejemplo, si solo se especifica el nombre "em", regresera los asegurados con nombre "Emilio", "Emiliano", "Emma" etc.
     * @param nombre nombre de pila
     * @param paterno apellido paterno
     * @param materno apellido matenro
     * @return asegurados que tengan el nombre y apellidos especificados
     */
    public List<Asegurado> buscarAseguradosPorNombre(String nombre, String paterno, String materno) {
        return ((AseguradoJpaController) controllers.get(Asegurado.class)).getByName(nombre, paterno, materno);
    }

    /**
     * Busca a los clientes por nombre y apellidos.
     * En caso de que algun parametro sea <code>null</code> o vacio se omite del query. 
     * Busca cliente que contengan parcialmente los parametros especificados, por ejemplo, si solo se especifica el nombre "em", regresera los clientes con nombre "Emilio", "Emiliano", "Emma" etc.
     * @param nombre nombre de pila
     * @param paterno apellido paterno
     * @param materno apellido materno
     * @return todos los clientes con nombre y apellidos especificados
     */
    public List<Cliente> buscarClientesPor(String nombre, String paterno, String materno) {
        return ((ClienteJpaController) controllers.get(Cliente.class)).getByName(nombre, paterno, materno);
    }

    /**
     * Busca las polizas que coincidan con los parametros especificados.
     * Busca las polizas que contengan parcialmente los parametros especificados, por ejemplo, si el numeroPoliza es "456", puede regresar las polizas con numero de poliza "123456", "456789", etc.
     * @param numeroPoliza numero de poliza
     * @param aseguradora aseguradora
     * @param ramo ramo
     * @return todas las poliza que contengan los parametros especificados
     */
    public List<Poliza> buscarPolizasPor(String numeroPoliza, String aseguradora, String ramo) {
        return ((PolizaJpaController) controllers.get(Poliza.class)).getBy(numeroPoliza, aseguradora, ramo);
    }

    private Map<Class, JpaController> createControllers() {
        Map<Class, JpaController> map = new HashMap<>();
        map.put(Auto.class, new AutoJpaController());
        map.put(Asegurado.class, new AseguradoJpaController());
        map.put(Aseguradora.class, new AseguradoraJpaController());
        map.put(Caratula.class, new CaratulaJpaController());
        map.put(Cliente.class, new ClienteJpaController());
        map.put(Cobranza.class, new CobranzaJpaController());
        map.put(ConductoCobro.class, new ConductoCobroJpaController());
        map.put(Delegacion.class, new DelegacionJpaController());
        map.put(DocumentoAsegurado.class, new DocumentoAseguradoJpaController());
        map.put(DocumentoRecibo.class, new DocumentoReciboJpaController());
        map.put(Domicilio.class, new DomicilioJpaController());
        map.put(Email.class, new EmailJpaController());
        map.put(Estado.class, new EstadoJpaController());
        map.put(EstadoNotificacion.class, new EstadoNotificacionJpaController());
        map.put(EstadoPoliza.class, new EstadoPolizaJpaController());
        map.put(FormaPago.class, new FormaPagoJpaController());
        map.put(Moneda.class, new MonedaJpaController());
        map.put(NotificacionCumple.class, new NotificacionCumpleJpaController());
        map.put(NotificacionRecibo.class, new NotificacionReciboJpaController());
        map.put(PolizaAuto.class, new PolizaAutoJpaController());
        map.put(PolizaGmm.class, new PolizaGmmJpaController());
        map.put(Poliza.class, new PolizaJpaController());
        map.put(PolizaVida.class, new PolizaVidaJpaController());
        map.put(Ramo.class, new RamoJpaController());
        map.put(Recibo.class, new ReciboJpaController());
        map.put(SumaAseguradaAuto.class, new SumaAseguradaAutoJpaController());
        map.put(Telefono.class, new TelefonoJpaController());
        map.put(TipoDocumentoAsegurado.class, new TipoDocumentoAseguradoJpaController());
        map.put(TipoEmail.class, new TipoEmailJpaController());
        map.put(TipoPersona.class, new TipoPersonaJpaController());
        map.put(TipoTelefono.class, new TipoTelefonoJpaController());
        map.put(Beneficiario.class, new BeneficiarioJpaController());
        map.put(Dependiente.class, new DependienteJpaController());
        return map;
    }

}
