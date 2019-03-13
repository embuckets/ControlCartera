/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Cobranza;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.Recibo;
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
public class CobranzaJpaController implements Serializable, JpaController {

    public CobranzaJpaController() {
    }

    @Override
    public void remove(Object object) {
        //DO NOTHING
    }

    @Override
    public <T> T edit(Object object) throws Exception {
        return null;
    }

    @Override
    public void create(Object object) throws PreexistingEntityException, Exception {
        //DO NOTHING
    }

    public CobranzaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cobranza cobranza) throws PreexistingEntityException, Exception {
        if (cobranza.getReciboList() == null) {
            cobranza.setReciboList(new ArrayList<Recibo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Recibo> attachedReciboList = new ArrayList<Recibo>();
            for (Recibo reciboListReciboToAttach : cobranza.getReciboList()) {
                reciboListReciboToAttach = em.getReference(reciboListReciboToAttach.getClass(), reciboListReciboToAttach.getIdrecibo());
                attachedReciboList.add(reciboListReciboToAttach);
            }
            cobranza.setReciboList(attachedReciboList);
            em.persist(cobranza);
            for (Recibo reciboListRecibo : cobranza.getReciboList()) {
                Cobranza oldCobranzaOfReciboListRecibo = reciboListRecibo.getCobranza();
                reciboListRecibo.setCobranza(cobranza);
                reciboListRecibo = em.merge(reciboListRecibo);
                if (oldCobranzaOfReciboListRecibo != null) {
                    oldCobranzaOfReciboListRecibo.getReciboList().remove(reciboListRecibo);
                    oldCobranzaOfReciboListRecibo = em.merge(oldCobranzaOfReciboListRecibo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCobranza(cobranza.getCobranza()) != null) {
                throw new PreexistingEntityException("Cobranza " + cobranza + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cobranza cobranza) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cobranza persistentCobranza = em.find(Cobranza.class, cobranza.getCobranza());
            List<Recibo> reciboListOld = persistentCobranza.getReciboList();
            List<Recibo> reciboListNew = cobranza.getReciboList();
            List<String> illegalOrphanMessages = null;
            for (Recibo reciboListOldRecibo : reciboListOld) {
                if (!reciboListNew.contains(reciboListOldRecibo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Recibo " + reciboListOldRecibo + " since its cobranza field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Recibo> attachedReciboListNew = new ArrayList<Recibo>();
            for (Recibo reciboListNewReciboToAttach : reciboListNew) {
                reciboListNewReciboToAttach = em.getReference(reciboListNewReciboToAttach.getClass(), reciboListNewReciboToAttach.getIdrecibo());
                attachedReciboListNew.add(reciboListNewReciboToAttach);
            }
            reciboListNew = attachedReciboListNew;
            cobranza.setReciboList(reciboListNew);
            cobranza = em.merge(cobranza);
            for (Recibo reciboListNewRecibo : reciboListNew) {
                if (!reciboListOld.contains(reciboListNewRecibo)) {
                    Cobranza oldCobranzaOfReciboListNewRecibo = reciboListNewRecibo.getCobranza();
                    reciboListNewRecibo.setCobranza(cobranza);
                    reciboListNewRecibo = em.merge(reciboListNewRecibo);
                    if (oldCobranzaOfReciboListNewRecibo != null && !oldCobranzaOfReciboListNewRecibo.equals(cobranza)) {
                        oldCobranzaOfReciboListNewRecibo.getReciboList().remove(reciboListNewRecibo);
                        oldCobranzaOfReciboListNewRecibo = em.merge(oldCobranzaOfReciboListNewRecibo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cobranza.getCobranza();
                if (findCobranza(id) == null) {
                    throw new NonexistentEntityException("The cobranza with id " + id + " no longer exists.");
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
            Cobranza cobranza;
            try {
                cobranza = em.getReference(Cobranza.class, id);
                cobranza.getCobranza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cobranza with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Recibo> reciboListOrphanCheck = cobranza.getReciboList();
            for (Recibo reciboListOrphanCheckRecibo : reciboListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cobranza (" + cobranza + ") cannot be destroyed since the Recibo " + reciboListOrphanCheckRecibo + " in its reciboList field has a non-nullable cobranza field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cobranza);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cobranza> findCobranzaEntities() {
        return findCobranzaEntities(true, -1, -1);
    }

    public List<Cobranza> findCobranzaEntities(int maxResults, int firstResult) {
        return findCobranzaEntities(false, maxResults, firstResult);
    }

    private List<Cobranza> findCobranzaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cobranza.class));
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

    public Cobranza findCobranza(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cobranza.class, id);
        } finally {
            em.close();
        }
    }

    public int getCobranzaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cobranza> rt = cq.from(Cobranza.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public String getControlledClassName() {
        return Cobranza.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByCobranza";
    }

    @Override
    public String getFindByIdParameter() {
        return "cobranza";
    }

}
