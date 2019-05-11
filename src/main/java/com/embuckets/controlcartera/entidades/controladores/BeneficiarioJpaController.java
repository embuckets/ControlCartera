/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Beneficiario;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author emilio
 */
public class BeneficiarioJpaController implements Serializable, JpaController {

    /**
     * Crea el enlace de beneficiario con la poliza. Crea el registro en caso de no existir previamente.
     * @param object
     * @throws Exception
     */
    @Override
    public void create(Object object) throws Exception {
        EntityManager em = null;
        Beneficiario beneficiario = (Beneficiario) object;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            if (beneficiario.getCliente().getIdcliente() == null) {
                em.persist(beneficiario.getCliente());
            }
            Query query = em.createNativeQuery("INSERT INTO APP.BENEFICIARIO (IDCLIENTE, IDPOLIZA) VALUES (:idcliente, :idpoliza)");
            query.setParameter("idcliente", beneficiario.getCliente().getIdcliente());
            query.setParameter("idpoliza", beneficiario.getPolizaVida().getIdpoliza());
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    /**
     *
     * @param <T>
     * @param object
     * @return
     * @throws Exception
     */
    @Override
    public <T> T edit(Object object) throws Exception {
        EntityManager em = null;
        Beneficiario beneficiario = (Beneficiario) object;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            T merged = em.merge((T) beneficiario.getCliente());
            em.getTransaction().commit();
            return merged;
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    /**
     * Elimina el enlace del beneficiario con la poliza.
     * @param object
     * @throws Exception
     */
    @Override
    public void remove(Object object) throws Exception {
        EntityManager em = null;
        Beneficiario beneficiario = (Beneficiario) object;
        boolean isSubTransaction = false;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }
            Query query = em.createNativeQuery("DELETE FROM APP.Beneficiario WHERE idcliente = :idcliente AND idpoliza = :idpoliza");
            query.setParameter("idcliente", beneficiario.getCliente().getIdcliente());
            query.setParameter("idpoliza", beneficiario.getPolizaVida().getIdpoliza());
            query.executeUpdate();

            if (!isSubTransaction) {
                PolizaVida pVida = beneficiario.getPolizaVida();
                List<Cliente> pVidaClientes = beneficiario.getPolizaVida().getClienteList();
                Cliente cliente = beneficiario.getCliente();
                boolean removed = pVidaClientes.remove(cliente);
                logger.info("Remove [" + beneficiario.getCliente() + "] = " + removed);
                pVida = em.merge(pVida);

            }

//            logger.info("Finding beneficiario [" + cliente.getIdcliente() + "] en polizaVida [" + pVida.getIdpoliza() + "]");
//            logger.info("Contains [" + pVidaClientes.contains(cliente) + "]");
//            for (Cliente b : pVidaClientes) {
////                logger.info("equals [" + b.getIdcliente() + "] = [" + b.equals(cliente) + "]");
//                logger.info("intValue [" + b.getIdcliente().intValue() + "] = [" + (b.getIdcliente().intValue() == cliente.getIdcliente().intValue()) + "]");
////                logger.info("compare [" + b.getIdcliente() + "] = [" + (b.getIdcliente().compareTo(cliente.getIdcliente())) + "]");
//                if (b.getIdcliente().intValue() == cliente.getIdcliente().intValue()) {
//                    int index = pVidaClientes.indexOf(b);
//                    logger.info("index [" + index + "]");
//                    boolean removed = pVidaClientes.remove(b);
//                    logger.info("remove object [" + b.getIdcliente() + "] = [" + removed + "]");
//                    if (!removed) {
//                        if (index >= 0) {
//                            logger.info("remove index [" + b.getIdcliente() + "] = [" + pVidaClientes.remove(index) + "]");
//                        }
//                    }
//                    break;
//                }
//            }
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
     * @return
     */
    @Override
    public String getControlledClassName() {
        return "";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "";
    }

}
