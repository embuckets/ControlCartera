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
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class RamoJpaController implements Serializable, JpaController {

    public RamoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public RamoJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ramo ramo) throws PreexistingEntityException, Exception {
        if (ramo.getPolizaList() == null) {
            ramo.setPolizaList(new ArrayList<Poliza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Poliza> attachedPolizaList = new ArrayList<Poliza>();
            for (Poliza polizaListPolizaToAttach : ramo.getPolizaList()) {
                polizaListPolizaToAttach = em.getReference(polizaListPolizaToAttach.getClass(), polizaListPolizaToAttach.getIdpoliza());
                attachedPolizaList.add(polizaListPolizaToAttach);
            }
            ramo.setPolizaList(attachedPolizaList);
            em.persist(ramo);
            for (Poliza polizaListPoliza : ramo.getPolizaList()) {
                Ramo oldRamoOfPolizaListPoliza = polizaListPoliza.getRamo();
                polizaListPoliza.setRamo(ramo);
                polizaListPoliza = em.merge(polizaListPoliza);
                if (oldRamoOfPolizaListPoliza != null) {
                    oldRamoOfPolizaListPoliza.getPolizaList().remove(polizaListPoliza);
                    oldRamoOfPolizaListPoliza = em.merge(oldRamoOfPolizaListPoliza);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRamo(ramo.getRamo()) != null) {
                throw new PreexistingEntityException("Ramo " + ramo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ramo ramo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ramo persistentRamo = em.find(Ramo.class, ramo.getRamo());
            List<Poliza> polizaListOld = persistentRamo.getPolizaList();
            List<Poliza> polizaListNew = ramo.getPolizaList();
            List<String> illegalOrphanMessages = null;
            for (Poliza polizaListOldPoliza : polizaListOld) {
                if (!polizaListNew.contains(polizaListOldPoliza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its ramo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Poliza> attachedPolizaListNew = new ArrayList<Poliza>();
            for (Poliza polizaListNewPolizaToAttach : polizaListNew) {
                polizaListNewPolizaToAttach = em.getReference(polizaListNewPolizaToAttach.getClass(), polizaListNewPolizaToAttach.getIdpoliza());
                attachedPolizaListNew.add(polizaListNewPolizaToAttach);
            }
            polizaListNew = attachedPolizaListNew;
            ramo.setPolizaList(polizaListNew);
            ramo = em.merge(ramo);
            for (Poliza polizaListNewPoliza : polizaListNew) {
                if (!polizaListOld.contains(polizaListNewPoliza)) {
                    Ramo oldRamoOfPolizaListNewPoliza = polizaListNewPoliza.getRamo();
                    polizaListNewPoliza.setRamo(ramo);
                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
                    if (oldRamoOfPolizaListNewPoliza != null && !oldRamoOfPolizaListNewPoliza.equals(ramo)) {
                        oldRamoOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
                        oldRamoOfPolizaListNewPoliza = em.merge(oldRamoOfPolizaListNewPoliza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ramo.getRamo();
                if (findRamo(id) == null) {
                    throw new NonexistentEntityException("The ramo with id " + id + " no longer exists.");
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
            Ramo ramo;
            try {
                ramo = em.getReference(Ramo.class, id);
                ramo.getRamo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ramo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Poliza> polizaListOrphanCheck = ramo.getPolizaList();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ramo (" + ramo + ") cannot be destroyed since the Poliza " + polizaListOrphanCheckPoliza + " in its polizaList field has a non-nullable ramo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ramo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ramo> findRamoEntities() {
        return findRamoEntities(true, -1, -1);
    }

    public List<Ramo> findRamoEntities(int maxResults, int firstResult) {
        return findRamoEntities(false, maxResults, firstResult);
    }

    private List<Ramo> findRamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ramo.class));
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

    public Ramo findRamo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ramo.class, id);
        } finally {
            em.close();
        }
    }

    public int getRamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ramo> rt = cq.from(Ramo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public void create(Object object) throws PreexistingEntityException, Exception {
        //DO NOTHING
    }

    @Override
    public <T> T edit(Object object) throws Exception {
        //DO NOTHING
        return null;
    }

    @Override
    public void remove(Object object) {
        //DO NOTHING
    }

    @Override
    public String getControlledClassName() {
        return Ramo.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByRamo";
    }

    @Override
    public String getFindByIdParameter() {
        return "ramo";
    }

}
