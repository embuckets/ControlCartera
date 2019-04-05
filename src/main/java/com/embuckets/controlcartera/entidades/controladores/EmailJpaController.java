/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Utilities;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author emilio
 */
public class EmailJpaController implements Serializable, JpaController {

    public EmailJpaController() {
    }

    @Override
    public void remove(Object object) {
        EntityManager em = null;
        Email email = (Email) object;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            List<Email> asegUnproxy = Utilities.initializeAndUnproxy(email.getAsegurado().getEmailList());
            asegUnproxy.remove(email);
            em.merge(email.getAsegurado());
            em.remove(email);
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
        return Email.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdcliente";
    }

    @Override
    public String getFindByIdParameter() {
        return "idcliente";
    }

}
