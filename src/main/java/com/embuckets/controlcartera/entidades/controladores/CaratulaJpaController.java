/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Caratula;
import java.io.Serializable;
import javax.persistence.Query;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import javax.persistence.EntityManager;

/**
 *
 * @author emilio
 */
public class CaratulaJpaController implements Serializable, JpaController {

    public CaratulaJpaController() {
    }

    @Override
    public void remove(Object object) throws Exception {
        Caratula caratula = (Caratula) object;
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
            Query query = em.createNativeQuery("DELETE FROM APP.Caratula WHERE idpoliza = :idpoliza");
            query.setParameter("idpoliza", caratula.getIdpoliza());
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
        return Caratula.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdpoliza";
    }

    @Override
    public String getFindByIdParameter() {
        return "idpoliza";
    }

}
