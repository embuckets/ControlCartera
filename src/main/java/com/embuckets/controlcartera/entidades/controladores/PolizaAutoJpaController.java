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
import com.embuckets.controlcartera.entidades.SumaAseguradaAuto;
import com.embuckets.controlcartera.entidades.Auto;
import com.embuckets.controlcartera.entidades.PolizaAuto;
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
public class PolizaAutoJpaController implements Serializable, JpaController {

    public PolizaAutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public PolizaAutoJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PolizaAuto polizaAuto) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (polizaAuto.getAutoList() == null) {
            polizaAuto.setAutoList(new ArrayList<Auto>());
        }
        List<String> illegalOrphanMessages = null;
        Poliza polizaOrphanCheck = polizaAuto.getPoliza();
        if (polizaOrphanCheck != null) {
            PolizaAuto oldPolizaAutoOfPoliza = polizaOrphanCheck.getPolizaAuto();
            if (oldPolizaAutoOfPoliza != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Poliza " + polizaOrphanCheck + " already has an item of type PolizaAuto whose poliza column cannot be null. Please make another selection for the poliza field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poliza poliza = polizaAuto.getPoliza();
            if (poliza != null) {
                poliza = em.getReference(poliza.getClass(), poliza.getIdpoliza());
                polizaAuto.setPoliza(poliza);
            }
            SumaAseguradaAuto sumaaseguradaauto = polizaAuto.getSumaaseguradaauto();
            if (sumaaseguradaauto != null) {
                sumaaseguradaauto = em.getReference(sumaaseguradaauto.getClass(), sumaaseguradaauto.getSumaaseguradaauto());
                polizaAuto.setSumaaseguradaauto(sumaaseguradaauto);
            }
            List<Auto> attachedAutoList = new ArrayList<Auto>();
            for (Auto autoListAutoToAttach : polizaAuto.getAutoList()) {
                autoListAutoToAttach = em.getReference(autoListAutoToAttach.getClass(), autoListAutoToAttach.getIdauto());
                attachedAutoList.add(autoListAutoToAttach);
            }
            polizaAuto.setAutoList(attachedAutoList);
            em.persist(polizaAuto);
            if (poliza != null) {
                poliza.setPolizaAuto(polizaAuto);
                poliza = em.merge(poliza);
            }
            if (sumaaseguradaauto != null) {
                sumaaseguradaauto.getPolizaAutoList().add(polizaAuto);
                sumaaseguradaauto = em.merge(sumaaseguradaauto);
            }
            for (Auto autoListAuto : polizaAuto.getAutoList()) {
                PolizaAuto oldIdpolizaOfAutoListAuto = autoListAuto.getIdpoliza();
                autoListAuto.setIdpoliza(polizaAuto);
                autoListAuto = em.merge(autoListAuto);
                if (oldIdpolizaOfAutoListAuto != null) {
                    oldIdpolizaOfAutoListAuto.getAutoList().remove(autoListAuto);
                    oldIdpolizaOfAutoListAuto = em.merge(oldIdpolizaOfAutoListAuto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPolizaAuto(polizaAuto.getIdpoliza()) != null) {
                throw new PreexistingEntityException("PolizaAuto " + polizaAuto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PolizaAuto polizaAuto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PolizaAuto persistentPolizaAuto = em.find(PolizaAuto.class, polizaAuto.getIdpoliza());
            Poliza polizaOld = persistentPolizaAuto.getPoliza();
            Poliza polizaNew = polizaAuto.getPoliza();
            SumaAseguradaAuto sumaaseguradaautoOld = persistentPolizaAuto.getSumaaseguradaauto();
            SumaAseguradaAuto sumaaseguradaautoNew = polizaAuto.getSumaaseguradaauto();
            List<Auto> autoListOld = persistentPolizaAuto.getAutoList();
            List<Auto> autoListNew = polizaAuto.getAutoList();
            List<String> illegalOrphanMessages = null;
            if (polizaNew != null && !polizaNew.equals(polizaOld)) {
                PolizaAuto oldPolizaAutoOfPoliza = polizaNew.getPolizaAuto();
                if (oldPolizaAutoOfPoliza != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Poliza " + polizaNew + " already has an item of type PolizaAuto whose poliza column cannot be null. Please make another selection for the poliza field.");
                }
            }
            for (Auto autoListOldAuto : autoListOld) {
                if (!autoListNew.contains(autoListOldAuto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Auto " + autoListOldAuto + " since its idpoliza field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (polizaNew != null) {
                polizaNew = em.getReference(polizaNew.getClass(), polizaNew.getIdpoliza());
                polizaAuto.setPoliza(polizaNew);
            }
            if (sumaaseguradaautoNew != null) {
                sumaaseguradaautoNew = em.getReference(sumaaseguradaautoNew.getClass(), sumaaseguradaautoNew.getSumaaseguradaauto());
                polizaAuto.setSumaaseguradaauto(sumaaseguradaautoNew);
            }
            List<Auto> attachedAutoListNew = new ArrayList<Auto>();
            for (Auto autoListNewAutoToAttach : autoListNew) {
                autoListNewAutoToAttach = em.getReference(autoListNewAutoToAttach.getClass(), autoListNewAutoToAttach.getIdauto());
                attachedAutoListNew.add(autoListNewAutoToAttach);
            }
            autoListNew = attachedAutoListNew;
            polizaAuto.setAutoList(autoListNew);
            polizaAuto = em.merge(polizaAuto);
            if (polizaOld != null && !polizaOld.equals(polizaNew)) {
                polizaOld.setPolizaAuto(null);
                polizaOld = em.merge(polizaOld);
            }
            if (polizaNew != null && !polizaNew.equals(polizaOld)) {
                polizaNew.setPolizaAuto(polizaAuto);
                polizaNew = em.merge(polizaNew);
            }
            if (sumaaseguradaautoOld != null && !sumaaseguradaautoOld.equals(sumaaseguradaautoNew)) {
                sumaaseguradaautoOld.getPolizaAutoList().remove(polizaAuto);
                sumaaseguradaautoOld = em.merge(sumaaseguradaautoOld);
            }
            if (sumaaseguradaautoNew != null && !sumaaseguradaautoNew.equals(sumaaseguradaautoOld)) {
                sumaaseguradaautoNew.getPolizaAutoList().add(polizaAuto);
                sumaaseguradaautoNew = em.merge(sumaaseguradaautoNew);
            }
            for (Auto autoListNewAuto : autoListNew) {
                if (!autoListOld.contains(autoListNewAuto)) {
                    PolizaAuto oldIdpolizaOfAutoListNewAuto = autoListNewAuto.getIdpoliza();
                    autoListNewAuto.setIdpoliza(polizaAuto);
                    autoListNewAuto = em.merge(autoListNewAuto);
                    if (oldIdpolizaOfAutoListNewAuto != null && !oldIdpolizaOfAutoListNewAuto.equals(polizaAuto)) {
                        oldIdpolizaOfAutoListNewAuto.getAutoList().remove(autoListNewAuto);
                        oldIdpolizaOfAutoListNewAuto = em.merge(oldIdpolizaOfAutoListNewAuto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = polizaAuto.getIdpoliza();
                if (findPolizaAuto(id) == null) {
                    throw new NonexistentEntityException("The polizaAuto with id " + id + " no longer exists.");
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
            PolizaAuto polizaAuto;
            try {
                polizaAuto = em.getReference(PolizaAuto.class, id);
                polizaAuto.getIdpoliza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The polizaAuto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Auto> autoListOrphanCheck = polizaAuto.getAutoList();
            for (Auto autoListOrphanCheckAuto : autoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PolizaAuto (" + polizaAuto + ") cannot be destroyed since the Auto " + autoListOrphanCheckAuto + " in its autoList field has a non-nullable idpoliza field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Poliza poliza = polizaAuto.getPoliza();
            if (poliza != null) {
                poliza.setPolizaAuto(null);
                poliza = em.merge(poliza);
            }
            SumaAseguradaAuto sumaaseguradaauto = polizaAuto.getSumaaseguradaauto();
            if (sumaaseguradaauto != null) {
                sumaaseguradaauto.getPolizaAutoList().remove(polizaAuto);
                sumaaseguradaauto = em.merge(sumaaseguradaauto);
            }
            em.remove(polizaAuto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PolizaAuto> findPolizaAutoEntities() {
        return findPolizaAutoEntities(true, -1, -1);
    }

    public List<PolizaAuto> findPolizaAutoEntities(int maxResults, int firstResult) {
        return findPolizaAutoEntities(false, maxResults, firstResult);
    }

    private List<PolizaAuto> findPolizaAutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PolizaAuto.class));
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

    public PolizaAuto findPolizaAuto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PolizaAuto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPolizaAutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PolizaAuto> rt = cq.from(PolizaAuto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public String getControlledClassName() {
        return PolizaAuto.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdpoliza";
    }

    @Override
    public String getFindByIdParameter() {
        return "idpoliza";
    }

}
