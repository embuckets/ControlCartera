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
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TipoTelefono;
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
public class TipoTelefonoJpaController implements Serializable, JpaController {

    public TipoTelefonoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public TipoTelefonoJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTelefono tipoTelefono) throws PreexistingEntityException, Exception {
        if (tipoTelefono.getTelefonoList() == null) {
            tipoTelefono.setTelefonoList(new ArrayList<Telefono>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Telefono> attachedTelefonoList = new ArrayList<Telefono>();
            for (Telefono telefonoListTelefonoToAttach : tipoTelefono.getTelefonoList()) {
                telefonoListTelefonoToAttach = em.getReference(telefonoListTelefonoToAttach.getClass(), telefonoListTelefonoToAttach.getTelefonoPK());
                attachedTelefonoList.add(telefonoListTelefonoToAttach);
            }
            tipoTelefono.setTelefonoList(attachedTelefonoList);
            em.persist(tipoTelefono);
            for (Telefono telefonoListTelefono : tipoTelefono.getTelefonoList()) {
                TipoTelefono oldTipotelefonoOfTelefonoListTelefono = telefonoListTelefono.getTipotelefono();
                telefonoListTelefono.setTipotelefono(tipoTelefono);
                telefonoListTelefono = em.merge(telefonoListTelefono);
                if (oldTipotelefonoOfTelefonoListTelefono != null) {
                    oldTipotelefonoOfTelefonoListTelefono.getTelefonoList().remove(telefonoListTelefono);
                    oldTipotelefonoOfTelefonoListTelefono = em.merge(oldTipotelefonoOfTelefonoListTelefono);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoTelefono(tipoTelefono.getTipotelefono()) != null) {
                throw new PreexistingEntityException("TipoTelefono " + tipoTelefono + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTelefono tipoTelefono) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTelefono persistentTipoTelefono = em.find(TipoTelefono.class, tipoTelefono.getTipotelefono());
            List<Telefono> telefonoListOld = persistentTipoTelefono.getTelefonoList();
            List<Telefono> telefonoListNew = tipoTelefono.getTelefonoList();
            List<Telefono> attachedTelefonoListNew = new ArrayList<Telefono>();
            for (Telefono telefonoListNewTelefonoToAttach : telefonoListNew) {
                telefonoListNewTelefonoToAttach = em.getReference(telefonoListNewTelefonoToAttach.getClass(), telefonoListNewTelefonoToAttach.getTelefonoPK());
                attachedTelefonoListNew.add(telefonoListNewTelefonoToAttach);
            }
            telefonoListNew = attachedTelefonoListNew;
            tipoTelefono.setTelefonoList(telefonoListNew);
            tipoTelefono = em.merge(tipoTelefono);
            for (Telefono telefonoListOldTelefono : telefonoListOld) {
                if (!telefonoListNew.contains(telefonoListOldTelefono)) {
                    telefonoListOldTelefono.setTipotelefono(null);
                    telefonoListOldTelefono = em.merge(telefonoListOldTelefono);
                }
            }
            for (Telefono telefonoListNewTelefono : telefonoListNew) {
                if (!telefonoListOld.contains(telefonoListNewTelefono)) {
                    TipoTelefono oldTipotelefonoOfTelefonoListNewTelefono = telefonoListNewTelefono.getTipotelefono();
                    telefonoListNewTelefono.setTipotelefono(tipoTelefono);
                    telefonoListNewTelefono = em.merge(telefonoListNewTelefono);
                    if (oldTipotelefonoOfTelefonoListNewTelefono != null && !oldTipotelefonoOfTelefonoListNewTelefono.equals(tipoTelefono)) {
                        oldTipotelefonoOfTelefonoListNewTelefono.getTelefonoList().remove(telefonoListNewTelefono);
                        oldTipotelefonoOfTelefonoListNewTelefono = em.merge(oldTipotelefonoOfTelefonoListNewTelefono);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipoTelefono.getTipotelefono();
                if (findTipoTelefono(id) == null) {
                    throw new NonexistentEntityException("The tipoTelefono with id " + id + " no longer exists.");
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
            TipoTelefono tipoTelefono;
            try {
                tipoTelefono = em.getReference(TipoTelefono.class, id);
                tipoTelefono.getTipotelefono();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTelefono with id " + id + " no longer exists.", enfe);
            }
            List<Telefono> telefonoList = tipoTelefono.getTelefonoList();
            for (Telefono telefonoListTelefono : telefonoList) {
                telefonoListTelefono.setTipotelefono(null);
                telefonoListTelefono = em.merge(telefonoListTelefono);
            }
            em.remove(tipoTelefono);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTelefono> findTipoTelefonoEntities() {
        return findTipoTelefonoEntities(true, -1, -1);
    }

    public List<TipoTelefono> findTipoTelefonoEntities(int maxResults, int firstResult) {
        return findTipoTelefonoEntities(false, maxResults, firstResult);
    }

    private List<TipoTelefono> findTipoTelefonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTelefono.class));
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

    public TipoTelefono findTipoTelefono(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTelefono.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTelefonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTelefono> rt = cq.from(TipoTelefono.class);
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
        return TipoTelefono.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByTipotelefono";
    }

    @Override
    public String getFindByIdParameter() {
        return "tipotelefono";
    }

}
