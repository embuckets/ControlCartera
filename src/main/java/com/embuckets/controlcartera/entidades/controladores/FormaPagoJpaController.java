/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.FormaPago;
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
public class FormaPagoJpaController implements Serializable {

    public FormaPagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FormaPago formaPago) throws PreexistingEntityException, Exception {
        if (formaPago.getPolizaList() == null) {
            formaPago.setPolizaList(new ArrayList<Poliza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Poliza> attachedPolizaList = new ArrayList<Poliza>();
            for (Poliza polizaListPolizaToAttach : formaPago.getPolizaList()) {
                polizaListPolizaToAttach = em.getReference(polizaListPolizaToAttach.getClass(), polizaListPolizaToAttach.getIdpoliza());
                attachedPolizaList.add(polizaListPolizaToAttach);
            }
            formaPago.setPolizaList(attachedPolizaList);
            em.persist(formaPago);
            for (Poliza polizaListPoliza : formaPago.getPolizaList()) {
                FormaPago oldFormapagoOfPolizaListPoliza = polizaListPoliza.getFormapago();
                polizaListPoliza.setFormapago(formaPago);
                polizaListPoliza = em.merge(polizaListPoliza);
                if (oldFormapagoOfPolizaListPoliza != null) {
                    oldFormapagoOfPolizaListPoliza.getPolizaList().remove(polizaListPoliza);
                    oldFormapagoOfPolizaListPoliza = em.merge(oldFormapagoOfPolizaListPoliza);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFormaPago(formaPago.getFormapago()) != null) {
                throw new PreexistingEntityException("FormaPago " + formaPago + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FormaPago formaPago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormaPago persistentFormaPago = em.find(FormaPago.class, formaPago.getFormapago());
            List<Poliza> polizaListOld = persistentFormaPago.getPolizaList();
            List<Poliza> polizaListNew = formaPago.getPolizaList();
            List<String> illegalOrphanMessages = null;
            for (Poliza polizaListOldPoliza : polizaListOld) {
                if (!polizaListNew.contains(polizaListOldPoliza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its formapago field is not nullable.");
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
            formaPago.setPolizaList(polizaListNew);
            formaPago = em.merge(formaPago);
            for (Poliza polizaListNewPoliza : polizaListNew) {
                if (!polizaListOld.contains(polizaListNewPoliza)) {
                    FormaPago oldFormapagoOfPolizaListNewPoliza = polizaListNewPoliza.getFormapago();
                    polizaListNewPoliza.setFormapago(formaPago);
                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
                    if (oldFormapagoOfPolizaListNewPoliza != null && !oldFormapagoOfPolizaListNewPoliza.equals(formaPago)) {
                        oldFormapagoOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
                        oldFormapagoOfPolizaListNewPoliza = em.merge(oldFormapagoOfPolizaListNewPoliza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = formaPago.getFormapago();
                if (findFormaPago(id) == null) {
                    throw new NonexistentEntityException("The formaPago with id " + id + " no longer exists.");
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
            FormaPago formaPago;
            try {
                formaPago = em.getReference(FormaPago.class, id);
                formaPago.getFormapago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formaPago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Poliza> polizaListOrphanCheck = formaPago.getPolizaList();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FormaPago (" + formaPago + ") cannot be destroyed since the Poliza " + polizaListOrphanCheckPoliza + " in its polizaList field has a non-nullable formapago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(formaPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FormaPago> findFormaPagoEntities() {
        return findFormaPagoEntities(true, -1, -1);
    }

    public List<FormaPago> findFormaPagoEntities(int maxResults, int firstResult) {
        return findFormaPagoEntities(false, maxResults, firstResult);
    }

    private List<FormaPago> findFormaPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FormaPago.class));
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

    public FormaPago findFormaPago(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormaPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormaPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FormaPago> rt = cq.from(FormaPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
