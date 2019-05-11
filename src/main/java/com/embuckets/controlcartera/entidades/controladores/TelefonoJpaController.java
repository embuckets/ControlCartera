/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import com.embuckets.controlcartera.entidades.Telefono;


import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Utilities;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author emilio
 */
public class TelefonoJpaController implements Serializable, JpaController {

    /**
     *
     */
    public TelefonoJpaController() {
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void remove(Object object) throws Exception {
        EntityManager em = null;
        Telefono telefono = (Telefono) object;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            List<Telefono> asegUnproxy = Utilities.initializeAndUnproxy(telefono.getAsegurado().getTelefonoList());
            asegUnproxy.remove(telefono);
//            email.getAsegurado().getEmailList().remove(email);
            em.merge(telefono.getAsegurado());
            em.remove(telefono);
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
     * @return
     */
    @Override
    public String getControlledClassName() {
        return Telefono.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdcliente";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "idcliente";
    }

}
