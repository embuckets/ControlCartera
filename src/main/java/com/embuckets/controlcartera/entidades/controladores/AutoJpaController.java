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

    public AutoJpaController() {
    }

    @Override
    public void remove(Object object) throws Exception {
        EntityManager em = null;
        Auto auto = (Auto) object;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            auto.getIdpoliza().getAutoList().remove(auto);
            em.merge(auto.getIdpoliza());
            em.remove(auto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public String getControlledClassName() {
        return Auto.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdauto";
    }

    @Override
    public String getFindByIdParameter() {
        return "idauto";
    }

}
