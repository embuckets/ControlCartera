/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emilio
 */
public class NotificacionCumpleJpaController implements Serializable, JpaController {

    private static final Logger logger = LogManager.getLogger(NotificacionCumpleJpaController.class);

    /**
     *
     */
    public NotificacionCumpleJpaController() {
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public List<NotificacionCumple> getNotificacionesPendientesEntre(LocalDate start, LocalDate end) {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();

            long months = end.getMonthValue() - start.getMonthValue();
            final String templateString = "SELECT n FROM NotificacionCumple n WHERE MONTH(n.cliente.nacimiento) = month(:start) AND n.estadonotificacion.estadonotificacion = :pendiente";
            String queryString = templateString.replace(":start", "'" + start.toString() + "'");
            Query query = em.createQuery(queryString);
            query.setParameter("pendiente", Globals.NOTIFICACION_ESTADO_PENDIENTE);
            Set<NotificacionCumple> result = new HashSet<>();
            result.addAll(query.getResultList());

            for (int i = 1; i <= months; i++) {
                queryString = templateString.replace(":start", "'" + start.plusMonths(i).toString() + "'");
                query = em.createQuery(queryString);
                query.setParameter("pendiente", Globals.NOTIFICACION_ESTADO_PENDIENTE);
                result.addAll(query.getResultList());
            }
            Predicate<NotificacionCumple> predicate = (n) -> {
                return n.getCliente().getNacimiento().getDayOfYear() >= start.getDayOfYear() && n.getCliente().getNacimiento().getDayOfYear() <= end.getDayOfYear();
            };
            return result.stream().filter(predicate).collect(Collectors.toList());

        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.Exception_MESSAGE, ex);
        }
        return new ArrayList<>();
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public List<NotificacionCumple> getNotificacionesEntre(LocalDate start, LocalDate end) {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            long months = end.getMonthValue() - start.getMonthValue();
            final String templateString = "SELECT n FROM NotificacionCumple n WHERE MONTH(n.cliente.nacimiento) = month(:start)";
            String queryString = templateString.replace(":start", "'" + start.toString() + "'");
            Query query = em.createQuery(queryString);
            Set<NotificacionCumple> result = new HashSet<>();
            result.addAll(query.getResultList());

            for (int i = 1; i <= months; i++) {
                queryString = templateString.replace(":start", "'" + start.plusMonths(i).toString() + "'");
                query = em.createQuery(queryString);
                result.addAll(query.getResultList());
            }
            Predicate<NotificacionCumple> predicate = (n) -> {
                int dayOfYear = n.getCliente().getNacimiento().getDayOfYear();
                return dayOfYear >= start.getDayOfYear() && dayOfYear <= end.getDayOfYear();
            };
            return result.stream().filter(predicate).collect(Collectors.toList());

        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.Exception_MESSAGE, ex);
        }
        return new ArrayList<>();
    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return NotificacionCumple.class.getSimpleName();
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
