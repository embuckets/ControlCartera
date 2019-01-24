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
import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import java.util.ArrayList;
import java.util.List;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getPolizaGmmList() == null) {
            cliente.setPolizaGmmList(new ArrayList<PolizaGmm>());
        }
        if (cliente.getPolizaVidaList() == null) {
            cliente.setPolizaVidaList(new ArrayList<PolizaVida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asegurado asegurado = cliente.getAsegurado();
            if (asegurado != null) {
                asegurado = em.getReference(asegurado.getClass(), asegurado.getIdcliente());
                cliente.setAsegurado(asegurado);
            }
            NotificacionCumple notificacionCumple = cliente.getNotificacionCumple();
            if (notificacionCumple != null) {
                notificacionCumple = em.getReference(notificacionCumple.getClass(), notificacionCumple.getIdcliente());
                cliente.setNotificacionCumple(notificacionCumple);
            }
            List<PolizaGmm> attachedPolizaGmmList = new ArrayList<PolizaGmm>();
            for (PolizaGmm polizaGmmListPolizaGmmToAttach : cliente.getPolizaGmmList()) {
                polizaGmmListPolizaGmmToAttach = em.getReference(polizaGmmListPolizaGmmToAttach.getClass(), polizaGmmListPolizaGmmToAttach.getIdpoliza());
                attachedPolizaGmmList.add(polizaGmmListPolizaGmmToAttach);
            }
            cliente.setPolizaGmmList(attachedPolizaGmmList);
            List<PolizaVida> attachedPolizaVidaList = new ArrayList<PolizaVida>();
            for (PolizaVida polizaVidaListPolizaVidaToAttach : cliente.getPolizaVidaList()) {
                polizaVidaListPolizaVidaToAttach = em.getReference(polizaVidaListPolizaVidaToAttach.getClass(), polizaVidaListPolizaVidaToAttach.getIdpoliza());
                attachedPolizaVidaList.add(polizaVidaListPolizaVidaToAttach);
            }
            cliente.setPolizaVidaList(attachedPolizaVidaList);
            em.persist(cliente);
            if (asegurado != null) {
                Cliente oldClienteOfAsegurado = asegurado.getCliente();
                if (oldClienteOfAsegurado != null) {
                    oldClienteOfAsegurado.setAsegurado(null);
                    oldClienteOfAsegurado = em.merge(oldClienteOfAsegurado);
                }
                asegurado.setCliente(cliente);
                asegurado = em.merge(asegurado);
            }
            if (notificacionCumple != null) {
                Cliente oldClienteOfNotificacionCumple = notificacionCumple.getCliente();
                if (oldClienteOfNotificacionCumple != null) {
                    oldClienteOfNotificacionCumple.setNotificacionCumple(null);
                    oldClienteOfNotificacionCumple = em.merge(oldClienteOfNotificacionCumple);
                }
                notificacionCumple.setCliente(cliente);
                notificacionCumple = em.merge(notificacionCumple);
            }
            for (PolizaGmm polizaGmmListPolizaGmm : cliente.getPolizaGmmList()) {
                polizaGmmListPolizaGmm.getClienteList().add(cliente);
                polizaGmmListPolizaGmm = em.merge(polizaGmmListPolizaGmm);
            }
            for (PolizaVida polizaVidaListPolizaVida : cliente.getPolizaVidaList()) {
                polizaVidaListPolizaVida.getClienteList().add(cliente);
                polizaVidaListPolizaVida = em.merge(polizaVidaListPolizaVida);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
            Asegurado aseguradoOld = persistentCliente.getAsegurado();
            Asegurado aseguradoNew = cliente.getAsegurado();
            NotificacionCumple notificacionCumpleOld = persistentCliente.getNotificacionCumple();
            NotificacionCumple notificacionCumpleNew = cliente.getNotificacionCumple();
            List<PolizaGmm> polizaGmmListOld = persistentCliente.getPolizaGmmList();
            List<PolizaGmm> polizaGmmListNew = cliente.getPolizaGmmList();
            List<PolizaVida> polizaVidaListOld = persistentCliente.getPolizaVidaList();
            List<PolizaVida> polizaVidaListNew = cliente.getPolizaVidaList();
            List<String> illegalOrphanMessages = null;
            if (aseguradoOld != null && !aseguradoOld.equals(aseguradoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Asegurado " + aseguradoOld + " since its cliente field is not nullable.");
            }
            if (notificacionCumpleOld != null && !notificacionCumpleOld.equals(notificacionCumpleNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain NotificacionCumple " + notificacionCumpleOld + " since its cliente field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (aseguradoNew != null) {
                aseguradoNew = em.getReference(aseguradoNew.getClass(), aseguradoNew.getIdcliente());
                cliente.setAsegurado(aseguradoNew);
            }
            if (notificacionCumpleNew != null) {
                notificacionCumpleNew = em.getReference(notificacionCumpleNew.getClass(), notificacionCumpleNew.getIdcliente());
                cliente.setNotificacionCumple(notificacionCumpleNew);
            }
            List<PolizaGmm> attachedPolizaGmmListNew = new ArrayList<PolizaGmm>();
            for (PolizaGmm polizaGmmListNewPolizaGmmToAttach : polizaGmmListNew) {
                polizaGmmListNewPolizaGmmToAttach = em.getReference(polizaGmmListNewPolizaGmmToAttach.getClass(), polizaGmmListNewPolizaGmmToAttach.getIdpoliza());
                attachedPolizaGmmListNew.add(polizaGmmListNewPolizaGmmToAttach);
            }
            polizaGmmListNew = attachedPolizaGmmListNew;
            cliente.setPolizaGmmList(polizaGmmListNew);
            List<PolizaVida> attachedPolizaVidaListNew = new ArrayList<PolizaVida>();
            for (PolizaVida polizaVidaListNewPolizaVidaToAttach : polizaVidaListNew) {
                polizaVidaListNewPolizaVidaToAttach = em.getReference(polizaVidaListNewPolizaVidaToAttach.getClass(), polizaVidaListNewPolizaVidaToAttach.getIdpoliza());
                attachedPolizaVidaListNew.add(polizaVidaListNewPolizaVidaToAttach);
            }
            polizaVidaListNew = attachedPolizaVidaListNew;
            cliente.setPolizaVidaList(polizaVidaListNew);
            cliente = em.merge(cliente);
            if (aseguradoNew != null && !aseguradoNew.equals(aseguradoOld)) {
                Cliente oldClienteOfAsegurado = aseguradoNew.getCliente();
                if (oldClienteOfAsegurado != null) {
                    oldClienteOfAsegurado.setAsegurado(null);
                    oldClienteOfAsegurado = em.merge(oldClienteOfAsegurado);
                }
                aseguradoNew.setCliente(cliente);
                aseguradoNew = em.merge(aseguradoNew);
            }
            if (notificacionCumpleNew != null && !notificacionCumpleNew.equals(notificacionCumpleOld)) {
                Cliente oldClienteOfNotificacionCumple = notificacionCumpleNew.getCliente();
                if (oldClienteOfNotificacionCumple != null) {
                    oldClienteOfNotificacionCumple.setNotificacionCumple(null);
                    oldClienteOfNotificacionCumple = em.merge(oldClienteOfNotificacionCumple);
                }
                notificacionCumpleNew.setCliente(cliente);
                notificacionCumpleNew = em.merge(notificacionCumpleNew);
            }
            for (PolizaGmm polizaGmmListOldPolizaGmm : polizaGmmListOld) {
                if (!polizaGmmListNew.contains(polizaGmmListOldPolizaGmm)) {
                    polizaGmmListOldPolizaGmm.getClienteList().remove(cliente);
                    polizaGmmListOldPolizaGmm = em.merge(polizaGmmListOldPolizaGmm);
                }
            }
            for (PolizaGmm polizaGmmListNewPolizaGmm : polizaGmmListNew) {
                if (!polizaGmmListOld.contains(polizaGmmListNewPolizaGmm)) {
                    polizaGmmListNewPolizaGmm.getClienteList().add(cliente);
                    polizaGmmListNewPolizaGmm = em.merge(polizaGmmListNewPolizaGmm);
                }
            }
            for (PolizaVida polizaVidaListOldPolizaVida : polizaVidaListOld) {
                if (!polizaVidaListNew.contains(polizaVidaListOldPolizaVida)) {
                    polizaVidaListOldPolizaVida.getClienteList().remove(cliente);
                    polizaVidaListOldPolizaVida = em.merge(polizaVidaListOldPolizaVida);
                }
            }
            for (PolizaVida polizaVidaListNewPolizaVida : polizaVidaListNew) {
                if (!polizaVidaListOld.contains(polizaVidaListNewPolizaVida)) {
                    polizaVidaListNewPolizaVida.getClienteList().add(cliente);
                    polizaVidaListNewPolizaVida = em.merge(polizaVidaListNewPolizaVida);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Asegurado aseguradoOrphanCheck = cliente.getAsegurado();
            if (aseguradoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Asegurado " + aseguradoOrphanCheck + " in its asegurado field has a non-nullable cliente field.");
            }
            NotificacionCumple notificacionCumpleOrphanCheck = cliente.getNotificacionCumple();
            if (notificacionCumpleOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the NotificacionCumple " + notificacionCumpleOrphanCheck + " in its notificacionCumple field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PolizaGmm> polizaGmmList = cliente.getPolizaGmmList();
            for (PolizaGmm polizaGmmListPolizaGmm : polizaGmmList) {
                polizaGmmListPolizaGmm.getClienteList().remove(cliente);
                polizaGmmListPolizaGmm = em.merge(polizaGmmListPolizaGmm);
            }
            List<PolizaVida> polizaVidaList = cliente.getPolizaVidaList();
            for (PolizaVida polizaVidaListPolizaVida : polizaVidaList) {
                polizaVidaListPolizaVida.getClienteList().remove(cliente);
                polizaVidaListPolizaVida = em.merge(polizaVidaListPolizaVida);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
