/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Auto;
import java.io.Serializable;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import javax.persistence.EntityManager;

/**
 *
 * @author emilio
 */
public class AutoJpaController implements Serializable, JpaController {

    /**
     *
     */
    public AutoJpaController() {
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void remove(Object object) throws Exception {
        Auto auto = (Auto) object;
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
            
            auto.getIdpoliza().getAutoList().remove(auto);
            em.merge(auto.getIdpoliza());
            em.remove(auto);
            
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
        return Auto.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdauto";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "idauto";
    }

}
