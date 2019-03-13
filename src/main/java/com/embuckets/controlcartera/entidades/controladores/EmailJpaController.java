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
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.EmailPK;
import com.embuckets.controlcartera.entidades.TipoEmail;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class EmailJpaController implements Serializable, JpaController {

    public EmailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EmailJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Email email) throws PreexistingEntityException, Exception {
        if (email.getEmailPK() == null) {
            email.setEmailPK(new EmailPK());
        }
        email.getEmailPK().setIdcliente(email.getAsegurado().getIdcliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asegurado asegurado = email.getAsegurado();
            if (asegurado != null) {
                asegurado = em.getReference(asegurado.getClass(), asegurado.getIdcliente());
                email.setAsegurado(asegurado);
            }
            TipoEmail tipoemail = email.getTipoemail();
            if (tipoemail != null) {
                tipoemail = em.getReference(tipoemail.getClass(), tipoemail.getTipoemail());
                email.setTipoemail(tipoemail);
            }
            em.persist(email);
            if (asegurado != null) {
                asegurado.getEmailList().add(email);
                asegurado = em.merge(asegurado);
            }
            if (tipoemail != null) {
                tipoemail.getEmailList().add(email);
                tipoemail = em.merge(tipoemail);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmail(email.getEmailPK()) != null) {
                throw new PreexistingEntityException("Email " + email + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Email email) throws NonexistentEntityException, Exception {
        email.getEmailPK().setIdcliente(email.getAsegurado().getIdcliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Email persistentEmail = em.find(Email.class, email.getEmailPK());
            Asegurado aseguradoOld = persistentEmail.getAsegurado();
            Asegurado aseguradoNew = email.getAsegurado();
            TipoEmail tipoemailOld = persistentEmail.getTipoemail();
            TipoEmail tipoemailNew = email.getTipoemail();
            if (aseguradoNew != null) {
                aseguradoNew = em.getReference(aseguradoNew.getClass(), aseguradoNew.getIdcliente());
                email.setAsegurado(aseguradoNew);
            }
            if (tipoemailNew != null) {
                tipoemailNew = em.getReference(tipoemailNew.getClass(), tipoemailNew.getTipoemail());
                email.setTipoemail(tipoemailNew);
            }
            email = em.merge(email);
            if (aseguradoOld != null && !aseguradoOld.equals(aseguradoNew)) {
                aseguradoOld.getEmailList().remove(email);
                aseguradoOld = em.merge(aseguradoOld);
            }
            if (aseguradoNew != null && !aseguradoNew.equals(aseguradoOld)) {
                aseguradoNew.getEmailList().add(email);
                aseguradoNew = em.merge(aseguradoNew);
            }
            if (tipoemailOld != null && !tipoemailOld.equals(tipoemailNew)) {
                tipoemailOld.getEmailList().remove(email);
                tipoemailOld = em.merge(tipoemailOld);
            }
            if (tipoemailNew != null && !tipoemailNew.equals(tipoemailOld)) {
                tipoemailNew.getEmailList().add(email);
                tipoemailNew = em.merge(tipoemailNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EmailPK id = email.getEmailPK();
                if (findEmail(id) == null) {
                    throw new NonexistentEntityException("The email with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmailPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Email email;
            try {
                email = em.getReference(Email.class, id);
                email.getEmailPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The email with id " + id + " no longer exists.", enfe);
            }
            Asegurado asegurado = email.getAsegurado();
            if (asegurado != null) {
                asegurado.getEmailList().remove(email);
                asegurado = em.merge(asegurado);
            }
            TipoEmail tipoemail = email.getTipoemail();
            if (tipoemail != null) {
                tipoemail.getEmailList().remove(email);
                tipoemail = em.merge(tipoemail);
            }
            em.remove(email);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Email> findEmailEntities() {
        return findEmailEntities(true, -1, -1);
    }

    public List<Email> findEmailEntities(int maxResults, int firstResult) {
        return findEmailEntities(false, maxResults, firstResult);
    }

    private List<Email> findEmailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Email.class));
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

    public Email findEmail(EmailPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Email.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Email> rt = cq.from(Email.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public String getControlledClassName() {
        return Email.class.getSimpleName();
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
