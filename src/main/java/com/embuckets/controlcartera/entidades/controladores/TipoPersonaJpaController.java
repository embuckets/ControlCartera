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
import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.TipoPersona;
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
public class TipoPersonaJpaController implements Serializable {

    public TipoPersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPersona tipoPersona) throws PreexistingEntityException, Exception {
        if (tipoPersona.getAseguradoList() == null) {
            tipoPersona.setAseguradoList(new ArrayList<Asegurado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Asegurado> attachedAseguradoList = new ArrayList<Asegurado>();
            for (Asegurado aseguradoListAseguradoToAttach : tipoPersona.getAseguradoList()) {
                aseguradoListAseguradoToAttach = em.getReference(aseguradoListAseguradoToAttach.getClass(), aseguradoListAseguradoToAttach.getIdcliente());
                attachedAseguradoList.add(aseguradoListAseguradoToAttach);
            }
            tipoPersona.setAseguradoList(attachedAseguradoList);
            em.persist(tipoPersona);
            for (Asegurado aseguradoListAsegurado : tipoPersona.getAseguradoList()) {
                TipoPersona oldTipopersonaOfAseguradoListAsegurado = aseguradoListAsegurado.getTipopersona();
                aseguradoListAsegurado.setTipopersona(tipoPersona);
                aseguradoListAsegurado = em.merge(aseguradoListAsegurado);
                if (oldTipopersonaOfAseguradoListAsegurado != null) {
                    oldTipopersonaOfAseguradoListAsegurado.getAseguradoList().remove(aseguradoListAsegurado);
                    oldTipopersonaOfAseguradoListAsegurado = em.merge(oldTipopersonaOfAseguradoListAsegurado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoPersona(tipoPersona.getTipopersona()) != null) {
                throw new PreexistingEntityException("TipoPersona " + tipoPersona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPersona tipoPersona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPersona persistentTipoPersona = em.find(TipoPersona.class, tipoPersona.getTipopersona());
            List<Asegurado> aseguradoListOld = persistentTipoPersona.getAseguradoList();
            List<Asegurado> aseguradoListNew = tipoPersona.getAseguradoList();
            List<Asegurado> attachedAseguradoListNew = new ArrayList<Asegurado>();
            for (Asegurado aseguradoListNewAseguradoToAttach : aseguradoListNew) {
                aseguradoListNewAseguradoToAttach = em.getReference(aseguradoListNewAseguradoToAttach.getClass(), aseguradoListNewAseguradoToAttach.getIdcliente());
                attachedAseguradoListNew.add(aseguradoListNewAseguradoToAttach);
            }
            aseguradoListNew = attachedAseguradoListNew;
            tipoPersona.setAseguradoList(aseguradoListNew);
            tipoPersona = em.merge(tipoPersona);
            for (Asegurado aseguradoListOldAsegurado : aseguradoListOld) {
                if (!aseguradoListNew.contains(aseguradoListOldAsegurado)) {
                    aseguradoListOldAsegurado.setTipopersona(null);
                    aseguradoListOldAsegurado = em.merge(aseguradoListOldAsegurado);
                }
            }
            for (Asegurado aseguradoListNewAsegurado : aseguradoListNew) {
                if (!aseguradoListOld.contains(aseguradoListNewAsegurado)) {
                    TipoPersona oldTipopersonaOfAseguradoListNewAsegurado = aseguradoListNewAsegurado.getTipopersona();
                    aseguradoListNewAsegurado.setTipopersona(tipoPersona);
                    aseguradoListNewAsegurado = em.merge(aseguradoListNewAsegurado);
                    if (oldTipopersonaOfAseguradoListNewAsegurado != null && !oldTipopersonaOfAseguradoListNewAsegurado.equals(tipoPersona)) {
                        oldTipopersonaOfAseguradoListNewAsegurado.getAseguradoList().remove(aseguradoListNewAsegurado);
                        oldTipopersonaOfAseguradoListNewAsegurado = em.merge(oldTipopersonaOfAseguradoListNewAsegurado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipoPersona.getTipopersona();
                if (findTipoPersona(id) == null) {
                    throw new NonexistentEntityException("The tipoPersona with id " + id + " no longer exists.");
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
            TipoPersona tipoPersona;
            try {
                tipoPersona = em.getReference(TipoPersona.class, id);
                tipoPersona.getTipopersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPersona with id " + id + " no longer exists.", enfe);
            }
            List<Asegurado> aseguradoList = tipoPersona.getAseguradoList();
            for (Asegurado aseguradoListAsegurado : aseguradoList) {
                aseguradoListAsegurado.setTipopersona(null);
                aseguradoListAsegurado = em.merge(aseguradoListAsegurado);
            }
            em.remove(tipoPersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoPersona> findTipoPersonaEntities() {
        return findTipoPersonaEntities(true, -1, -1);
    }

    public List<TipoPersona> findTipoPersonaEntities(int maxResults, int firstResult) {
        return findTipoPersonaEntities(false, maxResults, firstResult);
    }

    private List<TipoPersona> findTipoPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPersona.class));
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

    public TipoPersona findTipoPersona(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPersona> rt = cq.from(TipoPersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
