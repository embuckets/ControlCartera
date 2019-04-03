/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Auto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.exceptions.PreexistingEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class AutoJpaController implements Serializable, JpaController {

    public AutoJpaController() {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Auto auto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PolizaAuto idpoliza = auto.getIdpoliza();
            if (idpoliza != null) {
                idpoliza = em.getReference(idpoliza.getClass(), idpoliza.getIdpoliza());
                auto.setIdpoliza(idpoliza);
            }
            em.persist(auto);
            if (idpoliza != null) {
                idpoliza.getAutoList().add(auto);
                idpoliza = em.merge(idpoliza);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Auto auto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Auto persistentAuto = em.find(Auto.class, auto.getIdauto());
            PolizaAuto idpolizaOld = persistentAuto.getIdpoliza();
            PolizaAuto idpolizaNew = auto.getIdpoliza();
            if (idpolizaNew != null) {
                idpolizaNew = em.getReference(idpolizaNew.getClass(), idpolizaNew.getIdpoliza());
                auto.setIdpoliza(idpolizaNew);
            }
            auto = em.merge(auto);
            if (idpolizaOld != null && !idpolizaOld.equals(idpolizaNew)) {
                idpolizaOld.getAutoList().remove(auto);
                idpolizaOld = em.merge(idpolizaOld);
            }
            if (idpolizaNew != null && !idpolizaNew.equals(idpolizaOld)) {
                idpolizaNew.getAutoList().add(auto);
                idpolizaNew = em.merge(idpolizaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = auto.getIdauto();
                if (findAuto(id) == null) {
                    throw new NonexistentEntityException("The auto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Auto auto;
            try {
                auto = em.getReference(Auto.class, id);
                auto.getIdauto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auto with id " + id + " no longer exists.", enfe);
            }
            PolizaAuto idpoliza = auto.getIdpoliza();
            if (idpoliza != null) {
                idpoliza.getAutoList().remove(auto);
                idpoliza = em.merge(idpoliza);
            }
            em.remove(auto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Auto> findAutoEntities() {
        return findAutoEntities(true, -1, -1);
    }

    public List<Auto> findAutoEntities(int maxResults, int firstResult) {
        return findAutoEntities(false, maxResults, firstResult);
    }

    private List<Auto> findAutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Auto.class));
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

    public Auto findAuto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Auto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Auto> rt = cq.from(Auto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

//    @Override
//    public void create(Object object) throws PreexistingEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            em.getTransaction().begin();
//            em.persist(object);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
//    }
//    @Override
//    public void remove(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
    public void remove(Object object) {
        EntityManager em = null;
        Auto auto = (Auto) object;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            auto.getIdpoliza().getAutoList().remove(auto);
            em.merge(auto.getIdpoliza());
            em.remove(auto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public String getControlledClassName() {
        return Auto.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdauto";
    }

    @Override
    public String getFindByIdParameter() {
        return "idauto";
    }

}
