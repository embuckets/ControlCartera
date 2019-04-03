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
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.exceptions.IllegalOrphanException;
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
public class PolizaGmmJpaController implements Serializable, JpaController {

    public PolizaGmmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public PolizaGmmJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PolizaGmm polizaGmm) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (polizaGmm.getClienteList() == null) {
            polizaGmm.setClienteList(new ArrayList<Cliente>());
        }
        List<String> illegalOrphanMessages = null;
        Poliza polizaOrphanCheck = polizaGmm.getPoliza();
        if (polizaOrphanCheck != null) {
            PolizaGmm oldPolizaGmmOfPoliza = polizaOrphanCheck.getPolizaGmm();
            if (oldPolizaGmmOfPoliza != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Poliza " + polizaOrphanCheck + " already has an item of type PolizaGmm whose poliza column cannot be null. Please make another selection for the poliza field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Moneda sumaaseguradamondeda = polizaGmm.getSumaaseguradamondeda();
            if (sumaaseguradamondeda != null) {
                sumaaseguradamondeda = em.getReference(sumaaseguradamondeda.getClass(), sumaaseguradamondeda.getMoneda());
                polizaGmm.setSumaaseguradamondeda(sumaaseguradamondeda);
            }
            Moneda deduciblemoneda = polizaGmm.getDeduciblemoneda();
            if (deduciblemoneda != null) {
                deduciblemoneda = em.getReference(deduciblemoneda.getClass(), deduciblemoneda.getMoneda());
                polizaGmm.setDeduciblemoneda(deduciblemoneda);
            }
            Poliza poliza = polizaGmm.getPoliza();
            if (poliza != null) {
                poliza = em.getReference(poliza.getClass(), poliza.getIdpoliza());
                polizaGmm.setPoliza(poliza);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : polizaGmm.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdcliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            polizaGmm.setClienteList(attachedClienteList);
            em.persist(polizaGmm);
            if (sumaaseguradamondeda != null) {
                sumaaseguradamondeda.getPolizaGmmList().add(polizaGmm);
                sumaaseguradamondeda = em.merge(sumaaseguradamondeda);
            }
            if (deduciblemoneda != null) {
                deduciblemoneda.getPolizaGmmList().add(polizaGmm);
                deduciblemoneda = em.merge(deduciblemoneda);
            }
            if (poliza != null) {
                poliza.setPolizaGmm(polizaGmm);
                poliza = em.merge(poliza);
            }
            for (Cliente clienteListCliente : polizaGmm.getClienteList()) {
                clienteListCliente.getPolizaGmmList().add(polizaGmm);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPolizaGmm(polizaGmm.getIdpoliza()) != null) {
                throw new PreexistingEntityException("PolizaGmm " + polizaGmm + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PolizaGmm polizaGmm) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PolizaGmm persistentPolizaGmm = em.find(PolizaGmm.class, polizaGmm.getIdpoliza());
            Moneda sumaaseguradamondedaOld = persistentPolizaGmm.getSumaaseguradamondeda();
            Moneda sumaaseguradamondedaNew = polizaGmm.getSumaaseguradamondeda();
            Moneda deduciblemonedaOld = persistentPolizaGmm.getDeduciblemoneda();
            Moneda deduciblemonedaNew = polizaGmm.getDeduciblemoneda();
            Poliza polizaOld = persistentPolizaGmm.getPoliza();
            Poliza polizaNew = polizaGmm.getPoliza();
            List<Cliente> clienteListOld = persistentPolizaGmm.getClienteList();
            List<Cliente> clienteListNew = polizaGmm.getClienteList();
            List<String> illegalOrphanMessages = null;
            if (polizaNew != null && !polizaNew.equals(polizaOld)) {
                PolizaGmm oldPolizaGmmOfPoliza = polizaNew.getPolizaGmm();
                if (oldPolizaGmmOfPoliza != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Poliza " + polizaNew + " already has an item of type PolizaGmm whose poliza column cannot be null. Please make another selection for the poliza field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sumaaseguradamondedaNew != null) {
                sumaaseguradamondedaNew = em.getReference(sumaaseguradamondedaNew.getClass(), sumaaseguradamondedaNew.getMoneda());
                polizaGmm.setSumaaseguradamondeda(sumaaseguradamondedaNew);
            }
            if (deduciblemonedaNew != null) {
                deduciblemonedaNew = em.getReference(deduciblemonedaNew.getClass(), deduciblemonedaNew.getMoneda());
                polizaGmm.setDeduciblemoneda(deduciblemonedaNew);
            }
            if (polizaNew != null) {
                polizaNew = em.getReference(polizaNew.getClass(), polizaNew.getIdpoliza());
                polizaGmm.setPoliza(polizaNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdcliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            polizaGmm.setClienteList(clienteListNew);
            polizaGmm = em.merge(polizaGmm);
            if (sumaaseguradamondedaOld != null && !sumaaseguradamondedaOld.equals(sumaaseguradamondedaNew)) {
                sumaaseguradamondedaOld.getPolizaGmmList().remove(polizaGmm);
                sumaaseguradamondedaOld = em.merge(sumaaseguradamondedaOld);
            }
            if (sumaaseguradamondedaNew != null && !sumaaseguradamondedaNew.equals(sumaaseguradamondedaOld)) {
                sumaaseguradamondedaNew.getPolizaGmmList().add(polizaGmm);
                sumaaseguradamondedaNew = em.merge(sumaaseguradamondedaNew);
            }
            if (deduciblemonedaOld != null && !deduciblemonedaOld.equals(deduciblemonedaNew)) {
                deduciblemonedaOld.getPolizaGmmList().remove(polizaGmm);
                deduciblemonedaOld = em.merge(deduciblemonedaOld);
            }
            if (deduciblemonedaNew != null && !deduciblemonedaNew.equals(deduciblemonedaOld)) {
                deduciblemonedaNew.getPolizaGmmList().add(polizaGmm);
                deduciblemonedaNew = em.merge(deduciblemonedaNew);
            }
            if (polizaOld != null && !polizaOld.equals(polizaNew)) {
                polizaOld.setPolizaGmm(null);
                polizaOld = em.merge(polizaOld);
            }
            if (polizaNew != null && !polizaNew.equals(polizaOld)) {
                polizaNew.setPolizaGmm(polizaGmm);
                polizaNew = em.merge(polizaNew);
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.getPolizaGmmList().remove(polizaGmm);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    clienteListNewCliente.getPolizaGmmList().add(polizaGmm);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = polizaGmm.getIdpoliza();
                if (findPolizaGmm(id) == null) {
                    throw new NonexistentEntityException("The polizaGmm with id " + id + " no longer exists.");
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
            PolizaGmm polizaGmm;
            try {
                polizaGmm = em.getReference(PolizaGmm.class, id);
                polizaGmm.getIdpoliza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The polizaGmm with id " + id + " no longer exists.", enfe);
            }
            Moneda sumaaseguradamondeda = polizaGmm.getSumaaseguradamondeda();
            if (sumaaseguradamondeda != null) {
                sumaaseguradamondeda.getPolizaGmmList().remove(polizaGmm);
                sumaaseguradamondeda = em.merge(sumaaseguradamondeda);
            }
            Moneda deduciblemoneda = polizaGmm.getDeduciblemoneda();
            if (deduciblemoneda != null) {
                deduciblemoneda.getPolizaGmmList().remove(polizaGmm);
                deduciblemoneda = em.merge(deduciblemoneda);
            }
            Poliza poliza = polizaGmm.getPoliza();
            if (poliza != null) {
                poliza.setPolizaGmm(null);
                poliza = em.merge(poliza);
            }
            List<Cliente> clienteList = polizaGmm.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.getPolizaGmmList().remove(polizaGmm);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(polizaGmm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PolizaGmm> findPolizaGmmEntities() {
        return findPolizaGmmEntities(true, -1, -1);
    }

    public List<PolizaGmm> findPolizaGmmEntities(int maxResults, int firstResult) {
        return findPolizaGmmEntities(false, maxResults, firstResult);
    }

    private List<PolizaGmm> findPolizaGmmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PolizaGmm.class));
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

    public PolizaGmm findPolizaGmm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PolizaGmm.class, id);
        } finally {
            em.close();
        }
    }

    public int getPolizaGmmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PolizaGmm> rt = cq.from(PolizaGmm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public String getControlledClassName() {
        return PolizaGmm.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdpoliza";
    }

    @Override
    public String getFindByIdParameter() {
        return "idpoliza";
    }

}
