/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Delegacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class DelegacionJpaController implements Serializable, JpaController {

    public DelegacionJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
//
//    @Override
//    public <T> List<T> getAll() {
//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            return em.createNamedQuery("Delegacion.findAll").getResultList();
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
//    }

//    @Override
//    public <T> T getById(int id) throws Exception {
//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            Query query = em.createNamedQuery("Delegacion.findByDelegacion");
//            query.setParameter("delegacion", id);
//            return (T) query.getSingleResult();
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
//    }
    @Override
    public <T> T edit(Object object) {
        //SHOULD DO NOTHING
        return null;
//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            return em.merge((T) object);
////            Query query = em.createNamedQuery("Asegurado.findByIdcliente");
////            query.setParameter("idcliente", id);
////            return (T) query.getSingleResult();
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
    }

    @Override
    public void remove(Object object) {
        //SHOULD DO NOTHING

//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            em.getTransaction().begin();
//            em.remove(object);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
    }

    @Override
    public void create(Object object) throws PreexistingEntityException, Exception {
        //SHOULD DO NOTHING

//        Delegacion delegacion = (Delegacion) object;
//        if (delegacion.getDomicilioList() == null) {
//            delegacion.setDomicilioList(new ArrayList<Domicilio>());
//        }
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            List<Domicilio> attachedDomicilioList = new ArrayList<Domicilio>();
//            for (Domicilio domicilioListDomicilioToAttach : delegacion.getDomicilioList()) {
//                domicilioListDomicilioToAttach = em.getReference(domicilioListDomicilioToAttach.getClass(), domicilioListDomicilioToAttach.getIddomicilio());
//                attachedDomicilioList.add(domicilioListDomicilioToAttach);
//            }
//            delegacion.setDomicilioList(attachedDomicilioList);
//            em.persist(delegacion);
//            for (Domicilio domicilioListDomicilio : delegacion.getDomicilioList()) {
//                Delegacion oldDelegacionOfDomicilioListDomicilio = domicilioListDomicilio.getDelegacion();
//                domicilioListDomicilio.setDelegacion(delegacion);
//                domicilioListDomicilio = em.merge(domicilioListDomicilio);
//                if (oldDelegacionOfDomicilioListDomicilio != null) {
//                    oldDelegacionOfDomicilioListDomicilio.getDomicilioList().remove(domicilioListDomicilio);
//                    oldDelegacionOfDomicilioListDomicilio = em.merge(oldDelegacionOfDomicilioListDomicilio);
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findDelegacion(delegacion.getDelegacion()) != null) {
//                throw new PreexistingEntityException("Delegacion " + delegacion + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

//    public void edit(Delegacion delegacion) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Delegacion persistentDelegacion = em.find(Delegacion.class, delegacion.getDelegacion());
//            List<Domicilio> domicilioListOld = persistentDelegacion.getDomicilioList();
//            List<Domicilio> domicilioListNew = delegacion.getDomicilioList();
//            List<Domicilio> attachedDomicilioListNew = new ArrayList<Domicilio>();
//            for (Domicilio domicilioListNewDomicilioToAttach : domicilioListNew) {
//                domicilioListNewDomicilioToAttach = em.getReference(domicilioListNewDomicilioToAttach.getClass(), domicilioListNewDomicilioToAttach.getIddomicilio());
//                attachedDomicilioListNew.add(domicilioListNewDomicilioToAttach);
//            }
//            domicilioListNew = attachedDomicilioListNew;
//            delegacion.setDomicilioList(domicilioListNew);
//            delegacion = em.merge(delegacion);
//            for (Domicilio domicilioListOldDomicilio : domicilioListOld) {
//                if (!domicilioListNew.contains(domicilioListOldDomicilio)) {
//                    domicilioListOldDomicilio.setDelegacion(null);
//                    domicilioListOldDomicilio = em.merge(domicilioListOldDomicilio);
//                }
//            }
//            for (Domicilio domicilioListNewDomicilio : domicilioListNew) {
//                if (!domicilioListOld.contains(domicilioListNewDomicilio)) {
//                    Delegacion oldDelegacionOfDomicilioListNewDomicilio = domicilioListNewDomicilio.getDelegacion();
//                    domicilioListNewDomicilio.setDelegacion(delegacion);
//                    domicilioListNewDomicilio = em.merge(domicilioListNewDomicilio);
//                    if (oldDelegacionOfDomicilioListNewDomicilio != null && !oldDelegacionOfDomicilioListNewDomicilio.equals(delegacion)) {
//                        oldDelegacionOfDomicilioListNewDomicilio.getDomicilioList().remove(domicilioListNewDomicilio);
//                        oldDelegacionOfDomicilioListNewDomicilio = em.merge(oldDelegacionOfDomicilioListNewDomicilio);
//                    }
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                String id = delegacion.getDelegacion();
//                if (findDelegacion(id) == null) {
//                    throw new NonexistentEntityException("The delegacion with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public void destroy(String id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Delegacion delegacion;
//            try {
//                delegacion = em.getReference(Delegacion.class, id);
//                delegacion.getDelegacion();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The delegacion with id " + id + " no longer exists.", enfe);
//            }
//            List<Domicilio> domicilioList = delegacion.getDomicilioList();
//            for (Domicilio domicilioListDomicilio : domicilioList) {
//                domicilioListDomicilio.setDelegacion(null);
//                domicilioListDomicilio = em.merge(domicilioListDomicilio);
//            }
//            em.remove(delegacion);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
    public List<Delegacion> findDelegacionEntities() {
        return findDelegacionEntities(true, -1, -1);
    }

    public List<Delegacion> findDelegacionEntities(int maxResults, int firstResult) {
        return findDelegacionEntities(false, maxResults, firstResult);
    }

    private List<Delegacion> findDelegacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Delegacion.class));
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

    public Delegacion findDelegacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Delegacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDelegacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Delegacion> rt = cq.from(Delegacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

//    @Override
//    public <T> List<T> getAllById(int id) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
    public String getControlledClassName() {
        return Delegacion.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByDelegacion";
    }

    @Override
    public String getFindByIdParameter() {
        return "delegacion";
    }

}
