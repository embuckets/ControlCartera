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
import com.embuckets.controlcartera.entidades.Email;
import com.embuckets.controlcartera.entidades.TipoEmail;
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
public class TipoEmailJpaController implements Serializable, JpaController {

    public TipoEmailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public TipoEmailJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEmail tipoEmail) throws PreexistingEntityException, Exception {
        if (tipoEmail.getEmailList() == null) {
            tipoEmail.setEmailList(new ArrayList<Email>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Email> attachedEmailList = new ArrayList<Email>();
            for (Email emailListEmailToAttach : tipoEmail.getEmailList()) {
                emailListEmailToAttach = em.getReference(emailListEmailToAttach.getClass(), emailListEmailToAttach.getEmailPK());
                attachedEmailList.add(emailListEmailToAttach);
            }
            tipoEmail.setEmailList(attachedEmailList);
            em.persist(tipoEmail);
            for (Email emailListEmail : tipoEmail.getEmailList()) {
                TipoEmail oldTipoemailOfEmailListEmail = emailListEmail.getTipoemail();
                emailListEmail.setTipoemail(tipoEmail);
                emailListEmail = em.merge(emailListEmail);
                if (oldTipoemailOfEmailListEmail != null) {
                    oldTipoemailOfEmailListEmail.getEmailList().remove(emailListEmail);
                    oldTipoemailOfEmailListEmail = em.merge(oldTipoemailOfEmailListEmail);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoEmail(tipoEmail.getTipoemail()) != null) {
                throw new PreexistingEntityException("TipoEmail " + tipoEmail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEmail tipoEmail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEmail persistentTipoEmail = em.find(TipoEmail.class, tipoEmail.getTipoemail());
            List<Email> emailListOld = persistentTipoEmail.getEmailList();
            List<Email> emailListNew = tipoEmail.getEmailList();
            List<Email> attachedEmailListNew = new ArrayList<Email>();
            for (Email emailListNewEmailToAttach : emailListNew) {
                emailListNewEmailToAttach = em.getReference(emailListNewEmailToAttach.getClass(), emailListNewEmailToAttach.getEmailPK());
                attachedEmailListNew.add(emailListNewEmailToAttach);
            }
            emailListNew = attachedEmailListNew;
            tipoEmail.setEmailList(emailListNew);
            tipoEmail = em.merge(tipoEmail);
            for (Email emailListOldEmail : emailListOld) {
                if (!emailListNew.contains(emailListOldEmail)) {
                    emailListOldEmail.setTipoemail(null);
                    emailListOldEmail = em.merge(emailListOldEmail);
                }
            }
            for (Email emailListNewEmail : emailListNew) {
                if (!emailListOld.contains(emailListNewEmail)) {
                    TipoEmail oldTipoemailOfEmailListNewEmail = emailListNewEmail.getTipoemail();
                    emailListNewEmail.setTipoemail(tipoEmail);
                    emailListNewEmail = em.merge(emailListNewEmail);
                    if (oldTipoemailOfEmailListNewEmail != null && !oldTipoemailOfEmailListNewEmail.equals(tipoEmail)) {
                        oldTipoemailOfEmailListNewEmail.getEmailList().remove(emailListNewEmail);
                        oldTipoemailOfEmailListNewEmail = em.merge(oldTipoemailOfEmailListNewEmail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipoEmail.getTipoemail();
                if (findTipoEmail(id) == null) {
                    throw new NonexistentEntityException("The tipoEmail with id " + id + " no longer exists.");
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
            TipoEmail tipoEmail;
            try {
                tipoEmail = em.getReference(TipoEmail.class, id);
                tipoEmail.getTipoemail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEmail with id " + id + " no longer exists.", enfe);
            }
            List<Email> emailList = tipoEmail.getEmailList();
            for (Email emailListEmail : emailList) {
                emailListEmail.setTipoemail(null);
                emailListEmail = em.merge(emailListEmail);
            }
            em.remove(tipoEmail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEmail> findTipoEmailEntities() {
        return findTipoEmailEntities(true, -1, -1);
    }

    public List<TipoEmail> findTipoEmailEntities(int maxResults, int firstResult) {
        return findTipoEmailEntities(false, maxResults, firstResult);
    }

    private List<TipoEmail> findTipoEmailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEmail.class));
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

    public TipoEmail findTipoEmail(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEmail.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEmailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEmail> rt = cq.from(TipoEmail.class);
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
        return TipoEmail.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByTipoemail";
    }

    @Override
    public String getFindByIdParameter() {
        return "tipoemail";
    }

}
