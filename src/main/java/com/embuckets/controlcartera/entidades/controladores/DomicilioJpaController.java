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
import com.embuckets.controlcartera.entidades.Delegacion;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class DomicilioJpaController implements Serializable, JpaController {

    public DomicilioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public DomicilioJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Domicilio domicilio) {
        if (domicilio.getAseguradoList() == null) {
            domicilio.setAseguradoList(new ArrayList<Asegurado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Delegacion delegacion = domicilio.getDelegacion();
            if (delegacion != null) {
                delegacion = em.getReference(delegacion.getClass(), delegacion.getDelegacion());
                domicilio.setDelegacion(delegacion);
            }
            Estado estado = domicilio.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getEstado());
                domicilio.setEstado(estado);
            }
            List<Asegurado> attachedAseguradoList = new ArrayList<Asegurado>();
            for (Asegurado aseguradoListAseguradoToAttach : domicilio.getAseguradoList()) {
                aseguradoListAseguradoToAttach = em.getReference(aseguradoListAseguradoToAttach.getClass(), aseguradoListAseguradoToAttach.getIdcliente());
                attachedAseguradoList.add(aseguradoListAseguradoToAttach);
            }
            domicilio.setAseguradoList(attachedAseguradoList);
            em.persist(domicilio);
            if (delegacion != null) {
                delegacion.getDomicilioList().add(domicilio);
                delegacion = em.merge(delegacion);
            }
            if (estado != null) {
                estado.getDomicilioList().add(domicilio);
                estado = em.merge(estado);
            }
            for (Asegurado aseguradoListAsegurado : domicilio.getAseguradoList()) {
                Domicilio oldIddomicilioOfAseguradoListAsegurado = aseguradoListAsegurado.getIddomicilio();
                aseguradoListAsegurado.setIddomicilio(domicilio);
                aseguradoListAsegurado = em.merge(aseguradoListAsegurado);
                if (oldIddomicilioOfAseguradoListAsegurado != null) {
                    oldIddomicilioOfAseguradoListAsegurado.getAseguradoList().remove(aseguradoListAsegurado);
                    oldIddomicilioOfAseguradoListAsegurado = em.merge(oldIddomicilioOfAseguradoListAsegurado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Domicilio domicilio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Domicilio persistentDomicilio = em.find(Domicilio.class, domicilio.getIddomicilio());
            Delegacion delegacionOld = persistentDomicilio.getDelegacion();
            Delegacion delegacionNew = domicilio.getDelegacion();
            Estado estadoOld = persistentDomicilio.getEstado();
            Estado estadoNew = domicilio.getEstado();
            List<Asegurado> aseguradoListOld = persistentDomicilio.getAseguradoList();
            List<Asegurado> aseguradoListNew = domicilio.getAseguradoList();
            if (delegacionNew != null) {
                delegacionNew = em.getReference(delegacionNew.getClass(), delegacionNew.getDelegacion());
                domicilio.setDelegacion(delegacionNew);
            }
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getEstado());
                domicilio.setEstado(estadoNew);
            }
            List<Asegurado> attachedAseguradoListNew = new ArrayList<Asegurado>();
            for (Asegurado aseguradoListNewAseguradoToAttach : aseguradoListNew) {
                aseguradoListNewAseguradoToAttach = em.getReference(aseguradoListNewAseguradoToAttach.getClass(), aseguradoListNewAseguradoToAttach.getIdcliente());
                attachedAseguradoListNew.add(aseguradoListNewAseguradoToAttach);
            }
            aseguradoListNew = attachedAseguradoListNew;
            domicilio.setAseguradoList(aseguradoListNew);
            domicilio = em.merge(domicilio);
            if (delegacionOld != null && !delegacionOld.equals(delegacionNew)) {
                delegacionOld.getDomicilioList().remove(domicilio);
                delegacionOld = em.merge(delegacionOld);
            }
            if (delegacionNew != null && !delegacionNew.equals(delegacionOld)) {
                delegacionNew.getDomicilioList().add(domicilio);
                delegacionNew = em.merge(delegacionNew);
            }
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getDomicilioList().remove(domicilio);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getDomicilioList().add(domicilio);
                estadoNew = em.merge(estadoNew);
            }
            for (Asegurado aseguradoListOldAsegurado : aseguradoListOld) {
                if (!aseguradoListNew.contains(aseguradoListOldAsegurado)) {
                    aseguradoListOldAsegurado.setIddomicilio(null);
                    aseguradoListOldAsegurado = em.merge(aseguradoListOldAsegurado);
                }
            }
            for (Asegurado aseguradoListNewAsegurado : aseguradoListNew) {
                if (!aseguradoListOld.contains(aseguradoListNewAsegurado)) {
                    Domicilio oldIddomicilioOfAseguradoListNewAsegurado = aseguradoListNewAsegurado.getIddomicilio();
                    aseguradoListNewAsegurado.setIddomicilio(domicilio);
                    aseguradoListNewAsegurado = em.merge(aseguradoListNewAsegurado);
                    if (oldIddomicilioOfAseguradoListNewAsegurado != null && !oldIddomicilioOfAseguradoListNewAsegurado.equals(domicilio)) {
                        oldIddomicilioOfAseguradoListNewAsegurado.getAseguradoList().remove(aseguradoListNewAsegurado);
                        oldIddomicilioOfAseguradoListNewAsegurado = em.merge(oldIddomicilioOfAseguradoListNewAsegurado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = domicilio.getIddomicilio();
                if (findDomicilio(id) == null) {
                    throw new NonexistentEntityException("The domicilio with id " + id + " no longer exists.");
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
            Domicilio domicilio;
            try {
                domicilio = em.getReference(Domicilio.class, id);
                domicilio.getIddomicilio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The domicilio with id " + id + " no longer exists.", enfe);
            }
            Delegacion delegacion = domicilio.getDelegacion();
            if (delegacion != null) {
                delegacion.getDomicilioList().remove(domicilio);
                delegacion = em.merge(delegacion);
            }
            Estado estado = domicilio.getEstado();
            if (estado != null) {
                estado.getDomicilioList().remove(domicilio);
                estado = em.merge(estado);
            }
            List<Asegurado> aseguradoList = domicilio.getAseguradoList();
            for (Asegurado aseguradoListAsegurado : aseguradoList) {
                aseguradoListAsegurado.setIddomicilio(null);
                aseguradoListAsegurado = em.merge(aseguradoListAsegurado);
            }
            em.remove(domicilio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Domicilio> findDomicilioEntities() {
        return findDomicilioEntities(true, -1, -1);
    }

    public List<Domicilio> findDomicilioEntities(int maxResults, int firstResult) {
        return findDomicilioEntities(false, maxResults, firstResult);
    }

    private List<Domicilio> findDomicilioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Domicilio.class));
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

    public Domicilio findDomicilio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Domicilio.class, id);
        } finally {
            em.close();
        }
    }

    public int getDomicilioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Domicilio> rt = cq.from(Domicilio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public String getControlledClassName() {
        return Domicilio.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByIddomicilio";
    }

    @Override
    public String getFindByIdParameter() {
        return "iddomicilio";
    }

}
