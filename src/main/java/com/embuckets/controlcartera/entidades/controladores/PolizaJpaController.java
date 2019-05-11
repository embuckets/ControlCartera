/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import com.embuckets.controlcartera.entidades.Caratula;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.Auto;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.ConductoCobro;
import com.embuckets.controlcartera.entidades.EstadoPoliza;
import com.embuckets.controlcartera.entidades.FormaPago;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.Recibo;

import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emilio
 */
public class PolizaJpaController implements Serializable, JpaController {

    private static final Logger logger = LogManager.getLogger(PolizaJpaController.class);

    /**
     *
     */
    public PolizaJpaController() {
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void remove(Object object) throws Exception {
        EntityManager em = null;
        boolean isSubTransaction = false;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }
            Poliza poliza = (Poliza) object;
            Caratula caratulaOrphanCheck = poliza.getCaratula();
            if (caratulaOrphanCheck != null) {
                caratulaOrphanCheck.setPoliza(null);
                caratulaOrphanCheck = em.merge(caratulaOrphanCheck);
//                em.remove(caratulaOrphanCheck);
            }

            PolizaAuto polizaAutoOrphanCheck = poliza.getPolizaAuto();
            if (polizaAutoOrphanCheck != null) {
                for (Auto auto : polizaAutoOrphanCheck.getAutoList()) {
//                    polizaAutoOrphanCheck.getAutoList().remove(auto);
//                    polizaAutoOrphanCheck = em.merge(polizaAutoOrphanCheck);
                    em.remove(auto);
                }
                polizaAutoOrphanCheck.getAutoList().clear();
                polizaAutoOrphanCheck.setPoliza(null);
                polizaAutoOrphanCheck = em.merge(polizaAutoOrphanCheck);
//                em.remove(polizaAutoOrphanCheck);
            }
            PolizaVida polizaVidaOrphanCheck = poliza.getPolizaVida();
            if (polizaVidaOrphanCheck != null) {
//                BeneficiarioJpaController beneficiarioJpaController = new BeneficiarioJpaController();
                for (Cliente cliente : polizaVidaOrphanCheck.getClienteList()) {
                    Query query = em.createNativeQuery("DELETE FROM APP.Beneficiario WHERE idcliente = :idcliente AND idpoliza = :idpoliza");
                    query.setParameter("idcliente", cliente.getIdcliente());
                    query.setParameter("idpoliza", poliza.getIdpoliza());
                    query.executeUpdate();
//                    beneficiarioJpaController.remove(new Beneficiario(cliente, polizaVidaOrphanCheck));
                }
                polizaVidaOrphanCheck.setPoliza(null);
//                em.remove(polizaVidaOrphanCheck);
                polizaVidaOrphanCheck = em.merge(polizaVidaOrphanCheck);
            }
            PolizaGmm polizaGmmOrphanCheck = poliza.getPolizaGmm();
            if (polizaGmmOrphanCheck != null) {
//                DependienteJpaController dependienteJpaController = new DependienteJpaController();
                for (Cliente cliente : polizaGmmOrphanCheck.getClienteList()) {
                    Query query = em.createNativeQuery("DELETE FROM APP.Dependiente WHERE idcliente = :idcliente AND idpoliza = :idpoliza");
                    query.setParameter("idcliente", cliente.getIdcliente());
                    query.setParameter("idpoliza", poliza.getIdpoliza());
                    query.executeUpdate();
//                    dependienteJpaController.remove(new Dependiente(cliente, polizaGmmOrphanCheck));
                }
                polizaGmmOrphanCheck.setPoliza(null);
//                em.remove(polizaGmmOrphanCheck);
                polizaGmmOrphanCheck = em.merge(polizaGmmOrphanCheck);
            }
            List<Recibo> reciboListOrphanCheck = poliza.getReciboList();
            for (Recibo reciboListOrphanCheckRecibo : reciboListOrphanCheck) {
                reciboListOrphanCheckRecibo.setIdpoliza(null);
                em.remove(reciboListOrphanCheckRecibo);
//                reciboListOrphanCheckRecibo = em.merge(reciboListOrphanCheckRecibo);
            }
            reciboListOrphanCheck.clear();

            Asegurado contratante = poliza.getContratante();
            if (contratante != null) {
                contratante.getPolizaList().remove(poliza);
                contratante = em.merge(contratante);
            }
            Aseguradora aseguradora = poliza.getAseguradora();
            if (aseguradora != null) {
                aseguradora.getPolizaList().remove(poliza);
                aseguradora = em.merge(aseguradora);
            }
            Cliente titular = poliza.getTitular();
            if (titular != null) {
                titular.getPolizaList().remove(poliza);
                titular = em.merge(titular);
            }
            ConductoCobro conductocobro = poliza.getConductocobro();
            if (conductocobro != null) {
                conductocobro.getPolizaList().remove(poliza);
                conductocobro = em.merge(conductocobro);
            }
            EstadoPoliza estado = poliza.getEstado();
            if (estado != null) {
                estado.getPolizaList().remove(poliza);
                estado = em.merge(estado);
            }
            FormaPago formapago = poliza.getFormapago();
            if (formapago != null) {
                formapago.getPolizaList().remove(poliza);
                formapago = em.merge(formapago);
            }
            Moneda primamoneda = poliza.getPrimamoneda();
            if (primamoneda != null) {
                primamoneda.getPolizaList().remove(poliza);
                primamoneda = em.merge(primamoneda);
            }
            Ramo ramo = poliza.getRamo();
            if (ramo != null) {
                ramo.getPolizaList().remove(poliza);
                ramo = em.merge(ramo);
            }

            em.remove(poliza);
            if (!isSubTransaction) {
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public List<Poliza> getRenovacionesEntre(LocalDate start, LocalDate end) {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            //TODO: ESTA MAL
            Query query = em.createQuery("SELECT p FROM Poliza p WHERE p.finvigencia BETWEEN :start AND :end AND p.estado.estado = :vigente");
            query.setParameter("start", start);
            query.setParameter("end", end);
            query.setParameter("vigente", Globals.POLIZA_ESTADO_VIGENTE);
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.Exception_MESSAGE, ex);
        }
        return new ArrayList<>();
    }

    /**
     * Cambia el titular de la poliza
     * @param poliza poliza a modificar
     * @throws Exception - si falla la operacion
     */
    public void editarTitular(Poliza poliza) throws Exception {
        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }

            Cliente titular = poliza.getTitular();
            List<Cliente> clientes = null;
            clientes = new ClienteJpaController().getByName(titular.getNombre(), titular.getApellidopaterno(), titular.getApellidomaterno());
            if (clientes.isEmpty()) {
                em.persist(titular);
            } else if (clientes.size() == 1) {
                poliza.setTitular(clientes.get(0));
            }
            poliza = em.merge(poliza);

            //averigua si existe y son distintos
            if (!isSubTransaction) {
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    /**
     * Inserta la poliza junto con todos sus atributos.
     * @param object poliza a insertar
     * @throws Exception - si falla la operacion. Se hace un rollback
     */
    @Override
    public void create(Object object) throws Exception {
        Poliza poliza = (Poliza) object;
        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }

            Cliente titular = poliza.getTitular();
            List<Cliente> clientes = null;
            clientes = new ClienteJpaController().getByName(titular.getNombre(), titular.getApellidopaterno(), titular.getApellidomaterno());
            if (clientes.isEmpty()) {
                em.persist(titular);
            } else if (clientes.size() == 1) {
                poliza.setTitular(clientes.get(0));
            }
            em.persist(poliza);

            if (poliza.getCaratula() != null) {
                Caratula caratula = poliza.getCaratula();
                caratula.setPoliza(poliza);
                caratula.setIdpoliza(poliza.getId());
                em.persist(caratula);
            }

            for (Recibo recibo : poliza.getReciboList()) {
//                recibo.setIdpoliza(poliza);
//                em.persist(recibo);

                NotificacionRecibo notificacion = new NotificacionRecibo(recibo, Globals.NOTIFICACION_ESTADO_PENDIENTE);
                notificacion.setIdrecibo(recibo.getIdrecibo());
                em.persist(notificacion);
                recibo.setNotificacionRecibo(notificacion);
            }

            if (poliza.getPolizaAuto() != null) {
                poliza.getPolizaAuto().setPoliza(poliza);
                poliza.getPolizaAuto().setIdpoliza(poliza.getIdpoliza());
                em.persist(poliza.getPolizaAuto());
                for (Auto auto : poliza.getPolizaAuto().getAutoList()) {
                    em.persist(auto);
                }
            }
            if (poliza.getPolizaGmm() != null) {
                poliza.getPolizaGmm().setPoliza(poliza);
                poliza.getPolizaGmm().setIdpoliza(poliza.getIdpoliza());
                em.persist(poliza.getPolizaGmm());
                for (Cliente cliente : poliza.getPolizaGmm().getClienteList()) {
                    em.persist(cliente);
                    Query query = em.createNativeQuery("INSERT INTO APP.DEPENDIENTE (IDCLIENTE, IDPOLIZA) VALUES (:idcliente, :idpoliza)");
                    query.setParameter("idcliente", cliente.getIdcliente());
                    query.setParameter("idpoliza", poliza.getIdpoliza());
                    query.executeUpdate();
                }
            }
            if (poliza.getPolizaVida() != null) {
                poliza.getPolizaVida().setPoliza(poliza);
                poliza.getPolizaVida().setIdpoliza(poliza.getIdpoliza());
                em.persist(poliza.getPolizaVida());
                for (Cliente cliente : poliza.getPolizaVida().getClienteList()) {
                    em.persist(cliente);
                    Query query = em.createNativeQuery("INSERT INTO APP.BENEFICIARIO (IDCLIENTE, IDPOLIZA) VALUES (:idcliente, :idpoliza)");
                    query.setParameter("idcliente", cliente.getIdcliente());
                    query.setParameter("idpoliza", poliza.getIdpoliza());
                    query.executeUpdate();
                }
            }

            if (!isSubTransaction) {
                Asegurado contratante = poliza.getContratante();
                contratante.getPolizaList().add(poliza);//TODO: POSIBLE ERROR
                contratante = em.merge(contratante);
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    /**
     * Busca las polizas que coincidan con los parametros especificados.
     * Busca las polizas que contengan parcialmente los parametros especificados, por ejemplo, si el numeroPoliza es "456", puede regresar las polizas con numero de poliza "123456", "456789", etc.
     * @param numeroPoliza numero de poliza
     * @param aseguradora aseguradora
     * @param ramo ramo
     * @return todas las poliza que contengan los parametros especificados
     */
    public List<Poliza> getBy(String numeroPoliza, String aseguradora, String ramo) {
//        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();

            StringBuilder sb = new StringBuilder("SELECT a FROM Poliza a WHERE ");
            if (numeroPoliza != null && !numeroPoliza.isEmpty()) {
                sb.append("a.numero LIKE :numero ");
            }
            if (aseguradora != null && !aseguradora.isEmpty()) {
                if (numeroPoliza != null && !numeroPoliza.isEmpty()) {
                    sb.append("AND ");
                }
                sb.append("a.aseguradora.aseguradora LIKE :aseguradora ");
            }
            if (ramo != null && !ramo.isEmpty()) {
                if ((numeroPoliza != null && !numeroPoliza.isEmpty()) || (aseguradora != null && !aseguradora.isEmpty())) {
                    sb.append("AND ");
                }
                sb.append("a.ramo.ramo LIKE :ramo ");
            }

            Query query = em.createQuery(sb.toString());
            if (numeroPoliza != null && !numeroPoliza.isEmpty()) {
                query.setParameter("numero", "%" + numeroPoliza + "%");
            }
            if (aseguradora != null && !aseguradora.isEmpty()) {
                query.setParameter("aseguradora", "%" + aseguradora + "%");
            }
            if (ramo != null && !ramo.isEmpty()) {
                query.setParameter("ramo", "%" + ramo + "%");
            }
            return query.getResultList();

        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.Exception_MESSAGE, ex);
        }
        return new ArrayList<>();
    }

    /**
     * Inserta la poliza nueva y cambia el estado de la poliza vieja a "Renovada"
     * @param vieja poliza a renovar
     * @param nueva poliza a insertar
     * @throws Exception - si falla la operacion
     */
    public void renovar(Poliza vieja, Poliza nueva) throws Exception {
        Poliza polizaVieja = (Poliza) vieja;
        Poliza polizaNueva = (Poliza) nueva;
        EstadoPoliza oldEstado = vieja.getEstado();
        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }

            polizaVieja.setEstado(new EstadoPoliza(Globals.POLIZA_ESTADO_RENOVADA));
            em.merge(polizaVieja);
            create(polizaNueva);

            if (!isSubTransaction) {
                em.getTransaction().commit();
            }
            polizaNueva.getContratante().getPolizaList().add(polizaNueva);
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                polizaVieja.setEstado(oldEstado);
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return Poliza.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdpoliza";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "idpoliza";
    }

}
