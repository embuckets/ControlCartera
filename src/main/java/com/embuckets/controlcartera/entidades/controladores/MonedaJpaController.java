/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Moneda;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.Poliza;
import java.util.ArrayList;
import java.util.List;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class MonedaJpaController implements Serializable, JpaController {

    public MonedaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public MonedaJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Moneda moneda) throws PreexistingEntityException, Exception {
        if (moneda.getPolizaList() == null) {
            moneda.setPolizaList(new ArrayList<Poliza>());
        }
        if (moneda.getPolizaVidaList() == null) {
            moneda.setPolizaVidaList(new ArrayList<PolizaVida>());
        }
        if (moneda.getPolizaGmmList() == null) {
            moneda.setPolizaGmmList(new ArrayList<PolizaGmm>());
        }
        if (moneda.getPolizaGmmList1() == null) {
            moneda.setPolizaGmmList1(new ArrayList<PolizaGmm>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Poliza> attachedPolizaList = new ArrayList<Poliza>();
            for (Poliza polizaListPolizaToAttach : moneda.getPolizaList()) {
                polizaListPolizaToAttach = em.getReference(polizaListPolizaToAttach.getClass(), polizaListPolizaToAttach.getIdpoliza());
                attachedPolizaList.add(polizaListPolizaToAttach);
            }
            moneda.setPolizaList(attachedPolizaList);
            List<PolizaVida> attachedPolizaVidaList = new ArrayList<PolizaVida>();
            for (PolizaVida polizaVidaListPolizaVidaToAttach : moneda.getPolizaVidaList()) {
                polizaVidaListPolizaVidaToAttach = em.getReference(polizaVidaListPolizaVidaToAttach.getClass(), polizaVidaListPolizaVidaToAttach.getIdpoliza());
                attachedPolizaVidaList.add(polizaVidaListPolizaVidaToAttach);
            }
            moneda.setPolizaVidaList(attachedPolizaVidaList);
            List<PolizaGmm> attachedPolizaGmmList = new ArrayList<PolizaGmm>();
            for (PolizaGmm polizaGmmListPolizaGmmToAttach : moneda.getPolizaGmmList()) {
                polizaGmmListPolizaGmmToAttach = em.getReference(polizaGmmListPolizaGmmToAttach.getClass(), polizaGmmListPolizaGmmToAttach.getIdpoliza());
                attachedPolizaGmmList.add(polizaGmmListPolizaGmmToAttach);
            }
            moneda.setPolizaGmmList(attachedPolizaGmmList);
            List<PolizaGmm> attachedPolizaGmmList1 = new ArrayList<PolizaGmm>();
            for (PolizaGmm polizaGmmList1PolizaGmmToAttach : moneda.getPolizaGmmList1()) {
                polizaGmmList1PolizaGmmToAttach = em.getReference(polizaGmmList1PolizaGmmToAttach.getClass(), polizaGmmList1PolizaGmmToAttach.getIdpoliza());
                attachedPolizaGmmList1.add(polizaGmmList1PolizaGmmToAttach);
            }
            moneda.setPolizaGmmList1(attachedPolizaGmmList1);
            em.persist(moneda);
            for (Poliza polizaListPoliza : moneda.getPolizaList()) {
                Moneda oldPrimamonedaOfPolizaListPoliza = polizaListPoliza.getPrimamoneda();
                polizaListPoliza.setPrimamoneda(moneda);
                polizaListPoliza = em.merge(polizaListPoliza);
                if (oldPrimamonedaOfPolizaListPoliza != null) {
                    oldPrimamonedaOfPolizaListPoliza.getPolizaList().remove(polizaListPoliza);
                    oldPrimamonedaOfPolizaListPoliza = em.merge(oldPrimamonedaOfPolizaListPoliza);
                }
            }
            for (PolizaVida polizaVidaListPolizaVida : moneda.getPolizaVidaList()) {
                Moneda oldSumaaseguradamonedaOfPolizaVidaListPolizaVida = polizaVidaListPolizaVida.getSumaaseguradamoneda();
                polizaVidaListPolizaVida.setSumaaseguradamoneda(moneda);
                polizaVidaListPolizaVida = em.merge(polizaVidaListPolizaVida);
                if (oldSumaaseguradamonedaOfPolizaVidaListPolizaVida != null) {
                    oldSumaaseguradamonedaOfPolizaVidaListPolizaVida.getPolizaVidaList().remove(polizaVidaListPolizaVida);
                    oldSumaaseguradamonedaOfPolizaVidaListPolizaVida = em.merge(oldSumaaseguradamonedaOfPolizaVidaListPolizaVida);
                }
            }
            for (PolizaGmm polizaGmmListPolizaGmm : moneda.getPolizaGmmList()) {
                Moneda oldSumaaseguradamondedaOfPolizaGmmListPolizaGmm = polizaGmmListPolizaGmm.getSumaaseguradamondeda();
                polizaGmmListPolizaGmm.setSumaaseguradamondeda(moneda);
                polizaGmmListPolizaGmm = em.merge(polizaGmmListPolizaGmm);
                if (oldSumaaseguradamondedaOfPolizaGmmListPolizaGmm != null) {
                    oldSumaaseguradamondedaOfPolizaGmmListPolizaGmm.getPolizaGmmList().remove(polizaGmmListPolizaGmm);
                    oldSumaaseguradamondedaOfPolizaGmmListPolizaGmm = em.merge(oldSumaaseguradamondedaOfPolizaGmmListPolizaGmm);
                }
            }
            for (PolizaGmm polizaGmmList1PolizaGmm : moneda.getPolizaGmmList1()) {
                Moneda oldDeduciblemonedaOfPolizaGmmList1PolizaGmm = polizaGmmList1PolizaGmm.getDeduciblemoneda();
                polizaGmmList1PolizaGmm.setDeduciblemoneda(moneda);
                polizaGmmList1PolizaGmm = em.merge(polizaGmmList1PolizaGmm);
                if (oldDeduciblemonedaOfPolizaGmmList1PolizaGmm != null) {
                    oldDeduciblemonedaOfPolizaGmmList1PolizaGmm.getPolizaGmmList1().remove(polizaGmmList1PolizaGmm);
                    oldDeduciblemonedaOfPolizaGmmList1PolizaGmm = em.merge(oldDeduciblemonedaOfPolizaGmmList1PolizaGmm);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMoneda(moneda.getMoneda()) != null) {
                throw new PreexistingEntityException("Moneda " + moneda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Moneda moneda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Moneda persistentMoneda = em.find(Moneda.class, moneda.getMoneda());
            List<Poliza> polizaListOld = persistentMoneda.getPolizaList();
            List<Poliza> polizaListNew = moneda.getPolizaList();
            List<PolizaVida> polizaVidaListOld = persistentMoneda.getPolizaVidaList();
            List<PolizaVida> polizaVidaListNew = moneda.getPolizaVidaList();
            List<PolizaGmm> polizaGmmListOld = persistentMoneda.getPolizaGmmList();
            List<PolizaGmm> polizaGmmListNew = moneda.getPolizaGmmList();
            List<PolizaGmm> polizaGmmList1Old = persistentMoneda.getPolizaGmmList1();
            List<PolizaGmm> polizaGmmList1New = moneda.getPolizaGmmList1();
            List<String> illegalOrphanMessages = null;
            for (Poliza polizaListOldPoliza : polizaListOld) {
                if (!polizaListNew.contains(polizaListOldPoliza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its primamoneda field is not nullable.");
                }
            }
            for (PolizaVida polizaVidaListOldPolizaVida : polizaVidaListOld) {
                if (!polizaVidaListNew.contains(polizaVidaListOldPolizaVida)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PolizaVida " + polizaVidaListOldPolizaVida + " since its sumaaseguradamoneda field is not nullable.");
                }
            }
            for (PolizaGmm polizaGmmListOldPolizaGmm : polizaGmmListOld) {
                if (!polizaGmmListNew.contains(polizaGmmListOldPolizaGmm)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PolizaGmm " + polizaGmmListOldPolizaGmm + " since its sumaaseguradamondeda field is not nullable.");
                }
            }
            for (PolizaGmm polizaGmmList1OldPolizaGmm : polizaGmmList1Old) {
                if (!polizaGmmList1New.contains(polizaGmmList1OldPolizaGmm)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PolizaGmm " + polizaGmmList1OldPolizaGmm + " since its deduciblemoneda field is not nullable.");
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
            moneda.setPolizaList(polizaListNew);
            List<PolizaVida> attachedPolizaVidaListNew = new ArrayList<PolizaVida>();
            for (PolizaVida polizaVidaListNewPolizaVidaToAttach : polizaVidaListNew) {
                polizaVidaListNewPolizaVidaToAttach = em.getReference(polizaVidaListNewPolizaVidaToAttach.getClass(), polizaVidaListNewPolizaVidaToAttach.getIdpoliza());
                attachedPolizaVidaListNew.add(polizaVidaListNewPolizaVidaToAttach);
            }
            polizaVidaListNew = attachedPolizaVidaListNew;
            moneda.setPolizaVidaList(polizaVidaListNew);
            List<PolizaGmm> attachedPolizaGmmListNew = new ArrayList<PolizaGmm>();
            for (PolizaGmm polizaGmmListNewPolizaGmmToAttach : polizaGmmListNew) {
                polizaGmmListNewPolizaGmmToAttach = em.getReference(polizaGmmListNewPolizaGmmToAttach.getClass(), polizaGmmListNewPolizaGmmToAttach.getIdpoliza());
                attachedPolizaGmmListNew.add(polizaGmmListNewPolizaGmmToAttach);
            }
            polizaGmmListNew = attachedPolizaGmmListNew;
            moneda.setPolizaGmmList(polizaGmmListNew);
            List<PolizaGmm> attachedPolizaGmmList1New = new ArrayList<PolizaGmm>();
            for (PolizaGmm polizaGmmList1NewPolizaGmmToAttach : polizaGmmList1New) {
                polizaGmmList1NewPolizaGmmToAttach = em.getReference(polizaGmmList1NewPolizaGmmToAttach.getClass(), polizaGmmList1NewPolizaGmmToAttach.getIdpoliza());
                attachedPolizaGmmList1New.add(polizaGmmList1NewPolizaGmmToAttach);
            }
            polizaGmmList1New = attachedPolizaGmmList1New;
            moneda.setPolizaGmmList1(polizaGmmList1New);
            moneda = em.merge(moneda);
            for (Poliza polizaListNewPoliza : polizaListNew) {
                if (!polizaListOld.contains(polizaListNewPoliza)) {
                    Moneda oldPrimamonedaOfPolizaListNewPoliza = polizaListNewPoliza.getPrimamoneda();
                    polizaListNewPoliza.setPrimamoneda(moneda);
                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
                    if (oldPrimamonedaOfPolizaListNewPoliza != null && !oldPrimamonedaOfPolizaListNewPoliza.equals(moneda)) {
                        oldPrimamonedaOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
                        oldPrimamonedaOfPolizaListNewPoliza = em.merge(oldPrimamonedaOfPolizaListNewPoliza);
                    }
                }
            }
            for (PolizaVida polizaVidaListNewPolizaVida : polizaVidaListNew) {
                if (!polizaVidaListOld.contains(polizaVidaListNewPolizaVida)) {
                    Moneda oldSumaaseguradamonedaOfPolizaVidaListNewPolizaVida = polizaVidaListNewPolizaVida.getSumaaseguradamoneda();
                    polizaVidaListNewPolizaVida.setSumaaseguradamoneda(moneda);
                    polizaVidaListNewPolizaVida = em.merge(polizaVidaListNewPolizaVida);
                    if (oldSumaaseguradamonedaOfPolizaVidaListNewPolizaVida != null && !oldSumaaseguradamonedaOfPolizaVidaListNewPolizaVida.equals(moneda)) {
                        oldSumaaseguradamonedaOfPolizaVidaListNewPolizaVida.getPolizaVidaList().remove(polizaVidaListNewPolizaVida);
                        oldSumaaseguradamonedaOfPolizaVidaListNewPolizaVida = em.merge(oldSumaaseguradamonedaOfPolizaVidaListNewPolizaVida);
                    }
                }
            }
            for (PolizaGmm polizaGmmListNewPolizaGmm : polizaGmmListNew) {
                if (!polizaGmmListOld.contains(polizaGmmListNewPolizaGmm)) {
                    Moneda oldSumaaseguradamondedaOfPolizaGmmListNewPolizaGmm = polizaGmmListNewPolizaGmm.getSumaaseguradamondeda();
                    polizaGmmListNewPolizaGmm.setSumaaseguradamondeda(moneda);
                    polizaGmmListNewPolizaGmm = em.merge(polizaGmmListNewPolizaGmm);
                    if (oldSumaaseguradamondedaOfPolizaGmmListNewPolizaGmm != null && !oldSumaaseguradamondedaOfPolizaGmmListNewPolizaGmm.equals(moneda)) {
                        oldSumaaseguradamondedaOfPolizaGmmListNewPolizaGmm.getPolizaGmmList().remove(polizaGmmListNewPolizaGmm);
                        oldSumaaseguradamondedaOfPolizaGmmListNewPolizaGmm = em.merge(oldSumaaseguradamondedaOfPolizaGmmListNewPolizaGmm);
                    }
                }
            }
            for (PolizaGmm polizaGmmList1NewPolizaGmm : polizaGmmList1New) {
                if (!polizaGmmList1Old.contains(polizaGmmList1NewPolizaGmm)) {
                    Moneda oldDeduciblemonedaOfPolizaGmmList1NewPolizaGmm = polizaGmmList1NewPolizaGmm.getDeduciblemoneda();
                    polizaGmmList1NewPolizaGmm.setDeduciblemoneda(moneda);
                    polizaGmmList1NewPolizaGmm = em.merge(polizaGmmList1NewPolizaGmm);
                    if (oldDeduciblemonedaOfPolizaGmmList1NewPolizaGmm != null && !oldDeduciblemonedaOfPolizaGmmList1NewPolizaGmm.equals(moneda)) {
                        oldDeduciblemonedaOfPolizaGmmList1NewPolizaGmm.getPolizaGmmList1().remove(polizaGmmList1NewPolizaGmm);
                        oldDeduciblemonedaOfPolizaGmmList1NewPolizaGmm = em.merge(oldDeduciblemonedaOfPolizaGmmList1NewPolizaGmm);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = moneda.getMoneda();
                if (findMoneda(id) == null) {
                    throw new NonexistentEntityException("The moneda with id " + id + " no longer exists.");
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
            Moneda moneda;
            try {
                moneda = em.getReference(Moneda.class, id);
                moneda.getMoneda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The moneda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Poliza> polizaListOrphanCheck = moneda.getPolizaList();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Moneda (" + moneda + ") cannot be destroyed since the Poliza " + polizaListOrphanCheckPoliza + " in its polizaList field has a non-nullable primamoneda field.");
            }
            List<PolizaVida> polizaVidaListOrphanCheck = moneda.getPolizaVidaList();
            for (PolizaVida polizaVidaListOrphanCheckPolizaVida : polizaVidaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Moneda (" + moneda + ") cannot be destroyed since the PolizaVida " + polizaVidaListOrphanCheckPolizaVida + " in its polizaVidaList field has a non-nullable sumaaseguradamoneda field.");
            }
            List<PolizaGmm> polizaGmmListOrphanCheck = moneda.getPolizaGmmList();
            for (PolizaGmm polizaGmmListOrphanCheckPolizaGmm : polizaGmmListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Moneda (" + moneda + ") cannot be destroyed since the PolizaGmm " + polizaGmmListOrphanCheckPolizaGmm + " in its polizaGmmList field has a non-nullable sumaaseguradamondeda field.");
            }
            List<PolizaGmm> polizaGmmList1OrphanCheck = moneda.getPolizaGmmList1();
            for (PolizaGmm polizaGmmList1OrphanCheckPolizaGmm : polizaGmmList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Moneda (" + moneda + ") cannot be destroyed since the PolizaGmm " + polizaGmmList1OrphanCheckPolizaGmm + " in its polizaGmmList1 field has a non-nullable deduciblemoneda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(moneda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Moneda> findMonedaEntities() {
        return findMonedaEntities(true, -1, -1);
    }

    public List<Moneda> findMonedaEntities(int maxResults, int firstResult) {
        return findMonedaEntities(false, maxResults, firstResult);
    }

    private List<Moneda> findMonedaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Moneda.class));
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

    public Moneda findMoneda(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Moneda.class, id);
        } finally {
            em.close();
        }
    }

    public int getMonedaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Moneda> rt = cq.from(Moneda.class);
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
        return null;
    }

    @Override
    public void remove(Object object) {
        //DO NOTHING
    }

    @Override
    public String getControlledClassName() {
        return Moneda.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByMoneda";
    }

    @Override
    public String getFindByIdParameter() {
        return "moneda";
    }

}
