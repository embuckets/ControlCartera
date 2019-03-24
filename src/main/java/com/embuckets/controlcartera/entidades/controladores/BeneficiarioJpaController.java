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
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author emilio
 */
public class BeneficiarioJpaController implements Serializable, JpaController {

    @Override
    public void create(Object object) throws EntityExistsException, Exception {
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

    @Override
    public void remove(Object object) throws Exception{
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
