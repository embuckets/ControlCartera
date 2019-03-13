/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.EstadoPoliza;
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
public class EstadoPolizaJpaController implements Serializable, JpaController {

    public EstadoPolizaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EstadoPolizaJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoPoliza estadoPoliza) throws PreexistingEntityException, Exception {
        if (estadoPoliza.getPolizaList() == null) {
            estadoPoliza.setPolizaList(new ArrayList<Poliza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Poliza> attachedPolizaList = new ArrayList<Poliza>();
            for (Poliza polizaListPolizaToAttach : estadoPoliza.getPolizaList()) {
                polizaListPolizaToAttach = em.getReference(polizaListPolizaToAttach.getClass(), polizaListPolizaToAttach.getIdpoliza());
                attachedPolizaList.add(polizaListPolizaToAttach);
            }
            estadoPoliza.setPolizaList(attachedPolizaList);
            em.persist(estadoPoliza);
            for (Poliza polizaListPoliza : estadoPoliza.getPolizaList()) {
                EstadoPoliza oldEstadoOfPolizaListPoliza = polizaListPoliza.getEstado();
                polizaListPoliza.setEstado(estadoPoliza);
                polizaListPoliza = em.merge(polizaListPoliza);
                if (oldEstadoOfPolizaListPoliza != null) {
                    oldEstadoOfPolizaListPoliza.getPolizaList().remove(polizaListPoliza);
                    oldEstadoOfPolizaListPoliza = em.merge(oldEstadoOfPolizaListPoliza);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoPoliza(estadoPoliza.getEstado()) != null) {
                throw new PreexistingEntityException("EstadoPoliza " + estadoPoliza + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoPoliza estadoPoliza) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoPoliza persistentEstadoPoliza = em.find(EstadoPoliza.class, estadoPoliza.getEstado());
            List<Poliza> polizaListOld = persistentEstadoPoliza.getPolizaList();
            List<Poliza> polizaListNew = estadoPoliza.getPolizaList();
            List<String> illegalOrphanMessages = null;
            for (Poliza polizaListOldPoliza : polizaListOld) {
                if (!polizaListNew.contains(polizaListOldPoliza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its estado field is not nullable.");
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
            estadoPoliza.setPolizaList(polizaListNew);
            estadoPoliza = em.merge(estadoPoliza);
            for (Poliza polizaListNewPoliza : polizaListNew) {
                if (!polizaListOld.contains(polizaListNewPoliza)) {
                    EstadoPoliza oldEstadoOfPolizaListNewPoliza = polizaListNewPoliza.getEstado();
                    polizaListNewPoliza.setEstado(estadoPoliza);
                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
                    if (oldEstadoOfPolizaListNewPoliza != null && !oldEstadoOfPolizaListNewPoliza.equals(estadoPoliza)) {
                        oldEstadoOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
                        oldEstadoOfPolizaListNewPoliza = em.merge(oldEstadoOfPolizaListNewPoliza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estadoPoliza.getEstado();
                if (findEstadoPoliza(id) == null) {
                    throw new NonexistentEntityException("The estadoPoliza with id " + id + " no longer exists.");
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
            EstadoPoliza estadoPoliza;
            try {
                estadoPoliza = em.getReference(EstadoPoliza.class, id);
                estadoPoliza.getEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoPoliza with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Poliza> polizaListOrphanCheck = estadoPoliza.getPolizaList();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoPoliza (" + estadoPoliza + ") cannot be destroyed since the Poliza " + polizaListOrphanCheckPoliza + " in its polizaList field has a non-nullable estado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoPoliza);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoPoliza> findEstadoPolizaEntities() {
        return findEstadoPolizaEntities(true, -1, -1);
    }

    public List<EstadoPoliza> findEstadoPolizaEntities(int maxResults, int firstResult) {
        return findEstadoPolizaEntities(false, maxResults, firstResult);
    }

    private List<EstadoPoliza> findEstadoPolizaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoPoliza.class));
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

    public EstadoPoliza findEstadoPoliza(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoPoliza.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoPolizaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoPoliza> rt = cq.from(EstadoPoliza.class);
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
        return EstadoPoliza.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByEstado";
    }

    @Override
    public String getFindByIdParameter() {
        return "estado";
    }

}
