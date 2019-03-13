/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Aseguradora;
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
public class AseguradoraJpaController implements Serializable, JpaController {

    public AseguradoraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public AseguradoraJpaController() {
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aseguradora aseguradora) throws PreexistingEntityException, Exception {
        if (aseguradora.getPolizaList() == null) {
            aseguradora.setPolizaList(new ArrayList<Poliza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Poliza> attachedPolizaList = new ArrayList<Poliza>();
            for (Poliza polizaListPolizaToAttach : aseguradora.getPolizaList()) {
                polizaListPolizaToAttach = em.getReference(polizaListPolizaToAttach.getClass(), polizaListPolizaToAttach.getIdpoliza());
                attachedPolizaList.add(polizaListPolizaToAttach);
            }
            aseguradora.setPolizaList(attachedPolizaList);
            em.persist(aseguradora);
            for (Poliza polizaListPoliza : aseguradora.getPolizaList()) {
                Aseguradora oldAseguradoraOfPolizaListPoliza = polizaListPoliza.getAseguradora();
                polizaListPoliza.setAseguradora(aseguradora);
                polizaListPoliza = em.merge(polizaListPoliza);
                if (oldAseguradoraOfPolizaListPoliza != null) {
                    oldAseguradoraOfPolizaListPoliza.getPolizaList().remove(polizaListPoliza);
                    oldAseguradoraOfPolizaListPoliza = em.merge(oldAseguradoraOfPolizaListPoliza);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAseguradora(aseguradora.getAseguradora()) != null) {
                throw new PreexistingEntityException("Aseguradora " + aseguradora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aseguradora aseguradora) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aseguradora persistentAseguradora = em.find(Aseguradora.class, aseguradora.getAseguradora());
            List<Poliza> polizaListOld = persistentAseguradora.getPolizaList();
            List<Poliza> polizaListNew = aseguradora.getPolizaList();
            List<String> illegalOrphanMessages = null;
            for (Poliza polizaListOldPoliza : polizaListOld) {
                if (!polizaListNew.contains(polizaListOldPoliza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its aseguradora field is not nullable.");
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
            aseguradora.setPolizaList(polizaListNew);
            aseguradora = em.merge(aseguradora);
            for (Poliza polizaListNewPoliza : polizaListNew) {
                if (!polizaListOld.contains(polizaListNewPoliza)) {
                    Aseguradora oldAseguradoraOfPolizaListNewPoliza = polizaListNewPoliza.getAseguradora();
                    polizaListNewPoliza.setAseguradora(aseguradora);
                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
                    if (oldAseguradoraOfPolizaListNewPoliza != null && !oldAseguradoraOfPolizaListNewPoliza.equals(aseguradora)) {
                        oldAseguradoraOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
                        oldAseguradoraOfPolizaListNewPoliza = em.merge(oldAseguradoraOfPolizaListNewPoliza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = aseguradora.getAseguradora();
                if (findAseguradora(id) == null) {
                    throw new NonexistentEntityException("The aseguradora with id " + id + " no longer exists.");
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
            Aseguradora aseguradora;
            try {
                aseguradora = em.getReference(Aseguradora.class, id);
                aseguradora.getAseguradora();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aseguradora with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Poliza> polizaListOrphanCheck = aseguradora.getPolizaList();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Aseguradora (" + aseguradora + ") cannot be destroyed since the Poliza " + polizaListOrphanCheckPoliza + " in its polizaList field has a non-nullable aseguradora field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(aseguradora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aseguradora> findAseguradoraEntities() {
        return findAseguradoraEntities(true, -1, -1);
    }

    public List<Aseguradora> findAseguradoraEntities(int maxResults, int firstResult) {
        return findAseguradoraEntities(false, maxResults, firstResult);
    }

    private List<Aseguradora> findAseguradoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aseguradora.class));
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

    public Aseguradora findAseguradora(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aseguradora.class, id);
        } finally {
            em.close();
        }
    }

    public int getAseguradoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aseguradora> rt = cq.from(Aseguradora.class);
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
    public void remove(Object object) {
        //DO NOTHING
    }

    @Override
    public String getControlledClassName() {
        return Aseguradora.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByAseguradora";
    }

    @Override
    public String getFindByIdParameter() {
        return "aseguradora";
    }

}
