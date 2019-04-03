/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.EstadoNotificacion;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.exceptions.PreexistingEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import static java.time.temporal.ChronoUnit.MONTHS;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class NotificacionCumpleJpaController implements Serializable, JpaController {

    public NotificacionCumpleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public NotificacionCumpleJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NotificacionCumple notificacionCumple) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Cliente clienteOrphanCheck = notificacionCumple.getCliente();
        if (clienteOrphanCheck != null) {
            NotificacionCumple oldNotificacionCumpleOfCliente = clienteOrphanCheck.getNotificacionCumple();
            if (oldNotificacionCumpleOfCliente != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cliente " + clienteOrphanCheck + " already has an item of type NotificacionCumple whose cliente column cannot be null. Please make another selection for the cliente field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = notificacionCumple.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdcliente());
                notificacionCumple.setCliente(cliente);
            }
            EstadoNotificacion estadonotificacion = notificacionCumple.getEstadonotificacion();
            if (estadonotificacion != null) {
                estadonotificacion = em.getReference(estadonotificacion.getClass(), estadonotificacion.getEstadonotificacion());
                notificacionCumple.setEstadonotificacion(estadonotificacion);
            }
            em.persist(notificacionCumple);
            if (cliente != null) {
                cliente.setNotificacionCumple(notificacionCumple);
                cliente = em.merge(cliente);
            }
            if (estadonotificacion != null) {
                estadonotificacion.getNotificacionCumpleList().add(notificacionCumple);
                estadonotificacion = em.merge(estadonotificacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNotificacionCumple(notificacionCumple.getIdcliente()) != null) {
                throw new PreexistingEntityException("NotificacionCumple " + notificacionCumple + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NotificacionCumple notificacionCumple) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NotificacionCumple persistentNotificacionCumple = em.find(NotificacionCumple.class, notificacionCumple.getIdcliente());
            Cliente clienteOld = persistentNotificacionCumple.getCliente();
            Cliente clienteNew = notificacionCumple.getCliente();
            EstadoNotificacion estadonotificacionOld = persistentNotificacionCumple.getEstadonotificacion();
            EstadoNotificacion estadonotificacionNew = notificacionCumple.getEstadonotificacion();
            List<String> illegalOrphanMessages = null;
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                NotificacionCumple oldNotificacionCumpleOfCliente = clienteNew.getNotificacionCumple();
                if (oldNotificacionCumpleOfCliente != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cliente " + clienteNew + " already has an item of type NotificacionCumple whose cliente column cannot be null. Please make another selection for the cliente field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdcliente());
                notificacionCumple.setCliente(clienteNew);
            }
            if (estadonotificacionNew != null) {
                estadonotificacionNew = em.getReference(estadonotificacionNew.getClass(), estadonotificacionNew.getEstadonotificacion());
                notificacionCumple.setEstadonotificacion(estadonotificacionNew);
            }
            notificacionCumple = em.merge(notificacionCumple);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.setNotificacionCumple(null);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.setNotificacionCumple(notificacionCumple);
                clienteNew = em.merge(clienteNew);
            }
            if (estadonotificacionOld != null && !estadonotificacionOld.equals(estadonotificacionNew)) {
                estadonotificacionOld.getNotificacionCumpleList().remove(notificacionCumple);
                estadonotificacionOld = em.merge(estadonotificacionOld);
            }
            if (estadonotificacionNew != null && !estadonotificacionNew.equals(estadonotificacionOld)) {
                estadonotificacionNew.getNotificacionCumpleList().add(notificacionCumple);
                estadonotificacionNew = em.merge(estadonotificacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notificacionCumple.getIdcliente();
                if (findNotificacionCumple(id) == null) {
                    throw new NonexistentEntityException("The notificacionCumple with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NotificacionCumple notificacionCumple;
            try {
                notificacionCumple = em.getReference(NotificacionCumple.class, id);
                notificacionCumple.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificacionCumple with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = notificacionCumple.getCliente();
            if (cliente != null) {
                cliente.setNotificacionCumple(null);
                cliente = em.merge(cliente);
            }
            EstadoNotificacion estadonotificacion = notificacionCumple.getEstadonotificacion();
            if (estadonotificacion != null) {
                estadonotificacion.getNotificacionCumpleList().remove(notificacionCumple);
                estadonotificacion = em.merge(estadonotificacion);
            }
            em.remove(notificacionCumple);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NotificacionCumple> findNotificacionCumpleEntities() {
        return findNotificacionCumpleEntities(true, -1, -1);
    }

    public List<NotificacionCumple> findNotificacionCumpleEntities(int maxResults, int firstResult) {
        return findNotificacionCumpleEntities(false, maxResults, firstResult);
    }

    private List<NotificacionCumple> findNotificacionCumpleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NotificacionCumple.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public NotificacionCumple findNotificacionCumple(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NotificacionCumple.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacionCumpleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NotificacionCumple> rt = cq.from(NotificacionCumple.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

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

//            //BEFORE
//            String queryString = "SELECT n FROM NotificacionCumple n WHERE ((MONTH(n.cliente.nacimiento) >= month(:start) and day(n.cliente.nacimiento) >= day(:start)) AND (month(n.cliente.nacimiento) <= month(:end) and day(n.cliente.nacimiento) <= day(:end))) AND n.estadonotificacion.estadonotificacion = :pendiente";
//            queryString = queryString.replace(":start", "'" + start.toString() + "'");
//            queryString = queryString.replace(":end", "'" + end.toString() + "'");
//            Query query = em.createQuery(queryString);
//            query.setParameter("pendiente", Globals.NOTIFICACION_ESTADO_PENDIENTE);
//            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

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
//                if (n.getCliente().getNacimiento().isLeapYear() && dayOfYear > 28) {
//                    if (!end.isLeapYear() && end.getDayOfYear() > 28){
//                        dayOfYear -= 1;
//                    }
//                }
                return dayOfYear >= start.getDayOfYear() && dayOfYear <= end.getDayOfYear();
//                return n.getCliente().getNacimiento().getDayOfYear() >= start.getDayOfYear() && n.getCliente().getNacimiento().getDayOfYear() <= end.getDayOfYear();
            };
            return result.stream().filter(predicate).collect(Collectors.toList());
            
//            String queryString = "SELECT n FROM NotificacionCumple n WHERE (MONTH(n.cliente.nacimiento) >= month(:start) and day(n.cliente.nacimiento) >= day(:start)) OR (month(n.cliente.nacimiento) <= month(:end) and day(n.cliente.nacimiento) <= day(:end))";
//            queryString = queryString.replace(":start", "'" + start.toString() + "'");
//            queryString = queryString.replace(":end", "'" + end.toString() + "'");
//            Query query = em.createQuery(queryString);
//            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    public List<NotificacionCumple> getNotificacionesPendientesDeHace(int dias) {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            boolean nextMonth = false;
            StringBuilder queryString = new StringBuilder("SELECT n FROM NotificacionCumple n WHERE (MONTH(n.cliente.nacimiento) = :thisMonth");
            if (LocalDate.now().minusDays(dias).getMonthValue() != LocalDate.now().getMonthValue()) {
                queryString.append(" OR MONTH(n.cliente.nacimiento) = :nextMonth) AND n.estadonotificacion.estadonotificacion = :pendiente");
                nextMonth = true;
            } else {
                queryString.append(") AND n.estadonotificacion.estadonotificacion = :pendiente");
            }
            Query query = em.createQuery(queryString.toString());
            query.setParameter("thisMonth", LocalDate.now().getMonthValue());
            if (nextMonth) {
                query.setParameter("nextMonth", LocalDate.now().minusDays(1).getMonthValue());
            }
            query.setParameter("pendiente", Globals.NOTIFICACION_ESTADO_PENDIENTE);
            List<NotificacionCumple> cumples = query.getResultList();
            Predicate<NotificacionCumple> predicate = (t) -> {
                Period period = t.getCliente().getNacimiento().until(LocalDate.now());
                return (period.getDays() > 0 && period.getDays() <= dias);
            };
            return cumples.stream().filter(predicate).collect(Collectors.toList());
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    /**
     * regresa todas las notificaciones de mes actual y siguiente
     *
     * @return
     */
    public List<NotificacionCumple> getNotificacionesProximas() {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();

            Query query = em.createQuery("SELECT n FROM NotificacionCumple n WHERE MONTH(n.cliente.nacimiento) = :today OR MONTH(n.cliente.nacimiento) = :next");
            query.setParameter("today", LocalDate.now().getMonthValue());
            query.setParameter("next", LocalDate.now().plusMonths(1).getMonthValue());
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    /**
     *
     * @return regresa las notficaciones de este mes y menor o igual al dia de
     * hoy aun pendientes de enviar
     */
    public List<NotificacionCumple> getNotificacionesPendientes() {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();

            Query query = em.createQuery("SELECT n FROM NotificacionCumple n WHERE (MONTH(n.cliente.nacimiento) = :thisMonth AND DAY(n.cliente.nacimiento) <= :today) AND n.estadonotificacion.estadonotificacion = :pendiente");
            query.setParameter("thisMonth", LocalDate.now().getMonthValue());
            query.setParameter("today", LocalDate.now().getDayOfMonth());
            query.setParameter("pendiente", Globals.NOTIFICACION_ESTADO_PENDIENTE);
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public String getControlledClassName() {
        return NotificacionCumple.class.getSimpleName();
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
