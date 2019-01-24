/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Caratula;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
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
public class CaratulaJpaController implements Serializable {

    public CaratulaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caratula caratula) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Poliza polizaOrphanCheck = caratula.getPoliza();
        if (polizaOrphanCheck != null) {
            Caratula oldCaratulaOfPoliza = polizaOrphanCheck.getCaratula();
            if (oldCaratulaOfPoliza != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Poliza " + polizaOrphanCheck + " already has an item of type Caratula whose poliza column cannot be null. Please make another selection for the poliza field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poliza poliza = caratula.getPoliza();
            if (poliza != null) {
                poliza = em.getReference(poliza.getClass(), poliza.getIdpoliza());
                caratula.setPoliza(poliza);
            }
            em.persist(caratula);
            if (poliza != null) {
                poliza.setCaratula(caratula);
                poliza = em.merge(poliza);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCaratula(caratula.getIdpoliza()) != null) {
                throw new PreexistingEntityException("Caratula " + caratula + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caratula caratula) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caratula persistentCaratula = em.find(Caratula.class, caratula.getIdpoliza());
            Poliza polizaOld = persistentCaratula.getPoliza();
            Poliza polizaNew = caratula.getPoliza();
            List<String> illegalOrphanMessages = null;
            if (polizaNew != null && !polizaNew.equals(polizaOld)) {
                Caratula oldCaratulaOfPoliza = polizaNew.getCaratula();
                if (oldCaratulaOfPoliza != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Poliza " + polizaNew + " already has an item of type Caratula whose poliza column cannot be null. Please make another selection for the poliza field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (polizaNew != null) {
                polizaNew = em.getReference(polizaNew.getClass(), polizaNew.getIdpoliza());
                caratula.setPoliza(polizaNew);
            }
            caratula = em.merge(caratula);
            if (polizaOld != null && !polizaOld.equals(polizaNew)) {
                polizaOld.setCaratula(null);
                polizaOld = em.merge(polizaOld);
            }
            if (polizaNew != null && !polizaNew.equals(polizaOld)) {
                polizaNew.setCaratula(caratula);
                polizaNew = em.merge(polizaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = caratula.getIdpoliza();
                if (findCaratula(id) == null) {
                    throw new NonexistentEntityException("The caratula with id " + id + " no longer exists.");
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
            Caratula caratula;
            try {
                caratula = em.getReference(Caratula.class, id);
                caratula.getIdpoliza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caratula with id " + id + " no longer exists.", enfe);
            }
            Poliza poliza = caratula.getPoliza();
            if (poliza != null) {
                poliza.setCaratula(null);
                poliza = em.merge(poliza);
            }
            em.remove(caratula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Caratula> findCaratulaEntities() {
        return findCaratulaEntities(true, -1, -1);
    }

    public List<Caratula> findCaratulaEntities(int maxResults, int firstResult) {
        return findCaratulaEntities(false, maxResults, firstResult);
    }

    private List<Caratula> findCaratulaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caratula.class));
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

    public Caratula findCaratula(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caratula.class, id);
        } finally {
            em.close();
        }
    }

    public int getCaratulaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caratula> rt = cq.from(Caratula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
