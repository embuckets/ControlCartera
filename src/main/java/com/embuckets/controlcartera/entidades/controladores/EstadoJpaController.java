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
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) throws PreexistingEntityException, Exception {
        if (estado.getDomicilioList() == null) {
            estado.setDomicilioList(new ArrayList<Domicilio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Domicilio> attachedDomicilioList = new ArrayList<Domicilio>();
            for (Domicilio domicilioListDomicilioToAttach : estado.getDomicilioList()) {
                domicilioListDomicilioToAttach = em.getReference(domicilioListDomicilioToAttach.getClass(), domicilioListDomicilioToAttach.getIddomicilio());
                attachedDomicilioList.add(domicilioListDomicilioToAttach);
            }
            estado.setDomicilioList(attachedDomicilioList);
            em.persist(estado);
            for (Domicilio domicilioListDomicilio : estado.getDomicilioList()) {
                Estado oldEstadoOfDomicilioListDomicilio = domicilioListDomicilio.getEstado();
                domicilioListDomicilio.setEstado(estado);
                domicilioListDomicilio = em.merge(domicilioListDomicilio);
                if (oldEstadoOfDomicilioListDomicilio != null) {
                    oldEstadoOfDomicilioListDomicilio.getDomicilioList().remove(domicilioListDomicilio);
                    oldEstadoOfDomicilioListDomicilio = em.merge(oldEstadoOfDomicilioListDomicilio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstado(estado.getEstado()) != null) {
                throw new PreexistingEntityException("Estado " + estado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getEstado());
            List<Domicilio> domicilioListOld = persistentEstado.getDomicilioList();
            List<Domicilio> domicilioListNew = estado.getDomicilioList();
            List<Domicilio> attachedDomicilioListNew = new ArrayList<Domicilio>();
            for (Domicilio domicilioListNewDomicilioToAttach : domicilioListNew) {
                domicilioListNewDomicilioToAttach = em.getReference(domicilioListNewDomicilioToAttach.getClass(), domicilioListNewDomicilioToAttach.getIddomicilio());
                attachedDomicilioListNew.add(domicilioListNewDomicilioToAttach);
            }
            domicilioListNew = attachedDomicilioListNew;
            estado.setDomicilioList(domicilioListNew);
            estado = em.merge(estado);
            for (Domicilio domicilioListOldDomicilio : domicilioListOld) {
                if (!domicilioListNew.contains(domicilioListOldDomicilio)) {
                    domicilioListOldDomicilio.setEstado(null);
                    domicilioListOldDomicilio = em.merge(domicilioListOldDomicilio);
                }
            }
            for (Domicilio domicilioListNewDomicilio : domicilioListNew) {
                if (!domicilioListOld.contains(domicilioListNewDomicilio)) {
                    Estado oldEstadoOfDomicilioListNewDomicilio = domicilioListNewDomicilio.getEstado();
                    domicilioListNewDomicilio.setEstado(estado);
                    domicilioListNewDomicilio = em.merge(domicilioListNewDomicilio);
                    if (oldEstadoOfDomicilioListNewDomicilio != null && !oldEstadoOfDomicilioListNewDomicilio.equals(estado)) {
                        oldEstadoOfDomicilioListNewDomicilio.getDomicilioList().remove(domicilioListNewDomicilio);
                        oldEstadoOfDomicilioListNewDomicilio = em.merge(oldEstadoOfDomicilioListNewDomicilio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estado.getEstado();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<Domicilio> domicilioList = estado.getDomicilioList();
            for (Domicilio domicilioListDomicilio : domicilioList) {
                domicilioListDomicilio.setEstado(null);
                domicilioListDomicilio = em.merge(domicilioListDomicilio);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
