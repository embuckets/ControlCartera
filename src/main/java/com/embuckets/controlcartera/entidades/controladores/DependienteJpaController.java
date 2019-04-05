/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.Dependiente;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emilio
 */
public class DependienteJpaController implements Serializable, JpaController {

    private static final Logger logger = LogManager.getLogger(DependienteJpaController.class);

    @Override
    public void create(Object object) throws EntityExistsException, Exception {
        EntityManager em = null;
        Dependiente dependiente = (Dependiente) object;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            if (dependiente.getCliente().getIdcliente() == null) {
                em.persist(dependiente.getCliente());
            }
            Query query = em.createNativeQuery("INSERT INTO APP.DEPENDIENTE (IDCLIENTE, IDPOLIZA) VALUES (:idcliente, :idpoliza)");
            query.setParameter("idcliente", dependiente.getCliente().getIdcliente());
            query.setParameter("idpoliza", dependiente.getPolizaGmm().getIdpoliza());
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public <T> T edit(Object object) throws Exception {
        EntityManager em = null;
        Dependiente beneficiario = (Dependiente) object;
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

    @Override
    public void remove(Object object) {
        EntityManager em = null;
        Dependiente beneficiario = (Dependiente) object;
        boolean isSubTransaction = false;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }
            Query query = em.createNativeQuery("DELETE FROM APP.DEPENDIENTE WHERE idcliente = :idcliente AND idpoliza = :idpoliza");
            query.setParameter("idcliente", beneficiario.getCliente().getIdcliente());
            query.setParameter("idpoliza", beneficiario.getPolizaGmm().getIdpoliza());
            query.executeUpdate();

            if (!isSubTransaction) {
                List<Cliente> clientes = beneficiario.getPolizaGmm().getClienteList();
                logger.info("Remove [" + beneficiario.getCliente() + "] = " + clientes.remove(beneficiario.getCliente()));
                em.merge(beneficiario.getPolizaGmm());
            }

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

    @Override
    public String getControlledClassName() {
        return "";
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "";
    }

    @Override
    public String getFindByIdParameter() {
        return "";
    }

}
