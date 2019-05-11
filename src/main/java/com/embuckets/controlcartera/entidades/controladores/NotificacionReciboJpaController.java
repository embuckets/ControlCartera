/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;



import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author emilio
 */
public class NotificacionReciboJpaController implements Serializable, JpaController {

    /**
     *
     */
    public NotificacionReciboJpaController() {
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public List<NotificacionRecibo> getNotificacionesPendientesEntre(LocalDate start, LocalDate end) {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            Query query = em.createQuery("SELECT n FROM NotificacionRecibo n WHERE n.recibo.cubredesde BETWEEN :start AND :end AND n.recibo.cobranza.cobranza = :cobranza");
            query.setParameter("start", start);
            query.setParameter("end", end);
            query.setParameter("cobranza", Globals.RECIBO_COBRANZA_PENDIENTE);
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.Exception_MESSAGE);
        }
        return new ArrayList<>();
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public List<NotificacionRecibo> getNotificacionesEntre(LocalDate start, LocalDate end) {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            Query query = em.createQuery("SELECT n FROM NotificacionRecibo n WHERE n.recibo.cubredesde BETWEEN :start AND :end");
            query.setParameter("start", start);
            query.setParameter("end", end);
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.Exception_MESSAGE);
        }
        return new ArrayList<>();
    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return NotificacionRecibo.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdrecibo";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "idrecibo";
    }

}
