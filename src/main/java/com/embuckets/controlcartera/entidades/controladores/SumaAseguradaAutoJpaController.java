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
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.SumaAseguradaAuto;
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
public class SumaAseguradaAutoJpaController implements Serializable {

    public SumaAseguradaAutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SumaAseguradaAuto sumaAseguradaAuto) throws PreexistingEntityException, Exception {
        if (sumaAseguradaAuto.getPolizaAutoList() == null) {
            sumaAseguradaAuto.setPolizaAutoList(new ArrayList<PolizaAuto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PolizaAuto> attachedPolizaAutoList = new ArrayList<PolizaAuto>();
            for (PolizaAuto polizaAutoListPolizaAutoToAttach : sumaAseguradaAuto.getPolizaAutoList()) {
                polizaAutoListPolizaAutoToAttach = em.getReference(polizaAutoListPolizaAutoToAttach.getClass(), polizaAutoListPolizaAutoToAttach.getIdpoliza());
                attachedPolizaAutoList.add(polizaAutoListPolizaAutoToAttach);
            }
            sumaAseguradaAuto.setPolizaAutoList(attachedPolizaAutoList);
            em.persist(sumaAseguradaAuto);
            for (PolizaAuto polizaAutoListPolizaAuto : sumaAseguradaAuto.getPolizaAutoList()) {
                SumaAseguradaAuto oldSumaaseguradaautoOfPolizaAutoListPolizaAuto = polizaAutoListPolizaAuto.getSumaaseguradaauto();
                polizaAutoListPolizaAuto.setSumaaseguradaauto(sumaAseguradaAuto);
                polizaAutoListPolizaAuto = em.merge(polizaAutoListPolizaAuto);
                if (oldSumaaseguradaautoOfPolizaAutoListPolizaAuto != null) {
                    oldSumaaseguradaautoOfPolizaAutoListPolizaAuto.getPolizaAutoList().remove(polizaAutoListPolizaAuto);
                    oldSumaaseguradaautoOfPolizaAutoListPolizaAuto = em.merge(oldSumaaseguradaautoOfPolizaAutoListPolizaAuto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSumaAseguradaAuto(sumaAseguradaAuto.getSumaaseguradaauto()) != null) {
                throw new PreexistingEntityException("SumaAseguradaAuto " + sumaAseguradaAuto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SumaAseguradaAuto sumaAseguradaAuto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SumaAseguradaAuto persistentSumaAseguradaAuto = em.find(SumaAseguradaAuto.class, sumaAseguradaAuto.getSumaaseguradaauto());
            List<PolizaAuto> polizaAutoListOld = persistentSumaAseguradaAuto.getPolizaAutoList();
            List<PolizaAuto> polizaAutoListNew = sumaAseguradaAuto.getPolizaAutoList();
            List<String> illegalOrphanMessages = null;
            for (PolizaAuto polizaAutoListOldPolizaAuto : polizaAutoListOld) {
                if (!polizaAutoListNew.contains(polizaAutoListOldPolizaAuto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PolizaAuto " + polizaAutoListOldPolizaAuto + " since its sumaaseguradaauto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PolizaAuto> attachedPolizaAutoListNew = new ArrayList<PolizaAuto>();
            for (PolizaAuto polizaAutoListNewPolizaAutoToAttach : polizaAutoListNew) {
                polizaAutoListNewPolizaAutoToAttach = em.getReference(polizaAutoListNewPolizaAutoToAttach.getClass(), polizaAutoListNewPolizaAutoToAttach.getIdpoliza());
                attachedPolizaAutoListNew.add(polizaAutoListNewPolizaAutoToAttach);
            }
            polizaAutoListNew = attachedPolizaAutoListNew;
            sumaAseguradaAuto.setPolizaAutoList(polizaAutoListNew);
            sumaAseguradaAuto = em.merge(sumaAseguradaAuto);
            for (PolizaAuto polizaAutoListNewPolizaAuto : polizaAutoListNew) {
                if (!polizaAutoListOld.contains(polizaAutoListNewPolizaAuto)) {
                    SumaAseguradaAuto oldSumaaseguradaautoOfPolizaAutoListNewPolizaAuto = polizaAutoListNewPolizaAuto.getSumaaseguradaauto();
                    polizaAutoListNewPolizaAuto.setSumaaseguradaauto(sumaAseguradaAuto);
                    polizaAutoListNewPolizaAuto = em.merge(polizaAutoListNewPolizaAuto);
                    if (oldSumaaseguradaautoOfPolizaAutoListNewPolizaAuto != null && !oldSumaaseguradaautoOfPolizaAutoListNewPolizaAuto.equals(sumaAseguradaAuto)) {
                        oldSumaaseguradaautoOfPolizaAutoListNewPolizaAuto.getPolizaAutoList().remove(polizaAutoListNewPolizaAuto);
                        oldSumaaseguradaautoOfPolizaAutoListNewPolizaAuto = em.merge(oldSumaaseguradaautoOfPolizaAutoListNewPolizaAuto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sumaAseguradaAuto.getSumaaseguradaauto();
                if (findSumaAseguradaAuto(id) == null) {
                    throw new NonexistentEntityException("The sumaAseguradaAuto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SumaAseguradaAuto sumaAseguradaAuto;
            try {
                sumaAseguradaAuto = em.getReference(SumaAseguradaAuto.class, id);
                sumaAseguradaAuto.getSumaaseguradaauto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sumaAseguradaAuto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PolizaAuto> polizaAutoListOrphanCheck = sumaAseguradaAuto.getPolizaAutoList();
            for (PolizaAuto polizaAutoListOrphanCheckPolizaAuto : polizaAutoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SumaAseguradaAuto (" + sumaAseguradaAuto + ") cannot be destroyed since the PolizaAuto " + polizaAutoListOrphanCheckPolizaAuto + " in its polizaAutoList field has a non-nullable sumaaseguradaauto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sumaAseguradaAuto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SumaAseguradaAuto> findSumaAseguradaAutoEntities() {
        return findSumaAseguradaAutoEntities(true, -1, -1);
    }

    public List<SumaAseguradaAuto> findSumaAseguradaAutoEntities(int maxResults, int firstResult) {
        return findSumaAseguradaAutoEntities(false, maxResults, firstResult);
    }

    private List<SumaAseguradaAuto> findSumaAseguradaAutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SumaAseguradaAuto.class));
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

    public SumaAseguradaAuto findSumaAseguradaAuto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SumaAseguradaAuto.class, id);
        } finally {
            em.close();
        }
    }

    public int getSumaAseguradaAutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SumaAseguradaAuto> rt = cq.from(SumaAseguradaAuto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
