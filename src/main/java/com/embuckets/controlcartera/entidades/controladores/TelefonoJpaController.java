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
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.TelefonoPK;
import com.embuckets.controlcartera.entidades.TipoTelefono;
import com.embuckets.controlcartera.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.exceptions.PreexistingEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Utilities;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class TelefonoJpaController implements Serializable, JpaController {

    public TelefonoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public TelefonoJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telefono telefono) throws PreexistingEntityException, Exception {
        if (telefono.getTelefonoPK() == null) {
            telefono.setTelefonoPK(new TelefonoPK());
        }
        telefono.getTelefonoPK().setIdcliente(telefono.getAsegurado().getIdcliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asegurado asegurado = telefono.getAsegurado();
            if (asegurado != null) {
                asegurado = em.getReference(asegurado.getClass(), asegurado.getIdcliente());
                telefono.setAsegurado(asegurado);
            }
            TipoTelefono tipotelefono = telefono.getTipotelefono();
            if (tipotelefono != null) {
                tipotelefono = em.getReference(tipotelefono.getClass(), tipotelefono.getTipotelefono());
                telefono.setTipotelefono(tipotelefono);
            }
            em.persist(telefono);
            if (asegurado != null) {
                asegurado.getTelefonoList().add(telefono);
                asegurado = em.merge(asegurado);
            }
            if (tipotelefono != null) {
                tipotelefono.getTelefonoList().add(telefono);
                tipotelefono = em.merge(tipotelefono);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTelefono(telefono.getTelefonoPK()) != null) {
                throw new PreexistingEntityException("Telefono " + telefono + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefono telefono) throws NonexistentEntityException, Exception {
        telefono.getTelefonoPK().setIdcliente(telefono.getAsegurado().getIdcliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefono persistentTelefono = em.find(Telefono.class, telefono.getTelefonoPK());
            Asegurado aseguradoOld = persistentTelefono.getAsegurado();
            Asegurado aseguradoNew = telefono.getAsegurado();
            TipoTelefono tipotelefonoOld = persistentTelefono.getTipotelefono();
            TipoTelefono tipotelefonoNew = telefono.getTipotelefono();
            if (aseguradoNew != null) {
                aseguradoNew = em.getReference(aseguradoNew.getClass(), aseguradoNew.getIdcliente());
                telefono.setAsegurado(aseguradoNew);
            }
            if (tipotelefonoNew != null) {
                tipotelefonoNew = em.getReference(tipotelefonoNew.getClass(), tipotelefonoNew.getTipotelefono());
                telefono.setTipotelefono(tipotelefonoNew);
            }
            telefono = em.merge(telefono);
            if (aseguradoOld != null && !aseguradoOld.equals(aseguradoNew)) {
                aseguradoOld.getTelefonoList().remove(telefono);
                aseguradoOld = em.merge(aseguradoOld);
            }
            if (aseguradoNew != null && !aseguradoNew.equals(aseguradoOld)) {
                aseguradoNew.getTelefonoList().add(telefono);
                aseguradoNew = em.merge(aseguradoNew);
            }
            if (tipotelefonoOld != null && !tipotelefonoOld.equals(tipotelefonoNew)) {
                tipotelefonoOld.getTelefonoList().remove(telefono);
                tipotelefonoOld = em.merge(tipotelefonoOld);
            }
            if (tipotelefonoNew != null && !tipotelefonoNew.equals(tipotelefonoOld)) {
                tipotelefonoNew.getTelefonoList().add(telefono);
                tipotelefonoNew = em.merge(tipotelefonoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TelefonoPK id = telefono.getTelefonoPK();
                if (findTelefono(id) == null) {
                    throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TelefonoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefono telefono;
            try {
                telefono = em.getReference(Telefono.class, id);
                telefono.getTelefonoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.", enfe);
            }
            Asegurado asegurado = telefono.getAsegurado();
            if (asegurado != null) {
                asegurado.getTelefonoList().remove(telefono);
                asegurado = em.merge(asegurado);
            }
            TipoTelefono tipotelefono = telefono.getTipotelefono();
            if (tipotelefono != null) {
                tipotelefono.getTelefonoList().remove(telefono);
                tipotelefono = em.merge(tipotelefono);
            }
            em.remove(telefono);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefono> findTelefonoEntities() {
        return findTelefonoEntities(true, -1, -1);
    }

    public List<Telefono> findTelefonoEntities(int maxResults, int firstResult) {
        return findTelefonoEntities(false, maxResults, firstResult);
    }

    private List<Telefono> findTelefonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefono.class));
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

    public Telefono findTelefono(TelefonoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefono.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefono> rt = cq.from(Telefono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(Object object) {
        EntityManager em = null;
        Telefono telefono = (Telefono) object;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            List<Telefono> asegUnproxy = Utilities.initializeAndUnproxy(telefono.getAsegurado().getTelefonoList());
            asegUnproxy.remove(telefono);
//            email.getAsegurado().getEmailList().remove(email);
            em.merge(telefono.getAsegurado());
            em.remove(telefono);
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
        return Telefono.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdcliente";
    }

    @Override
    public String getFindByIdParameter() {
        return "idcliente";
    }

}
