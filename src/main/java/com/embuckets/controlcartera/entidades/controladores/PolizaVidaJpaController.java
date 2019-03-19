/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Beneficiario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class PolizaVidaJpaController implements Serializable, JpaController {

    public PolizaVidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public PolizaVidaJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PolizaVida polizaVida) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (polizaVida.getClienteList() == null) {
            polizaVida.setClienteList(new ArrayList<Cliente>());
        }
        List<String> illegalOrphanMessages = null;
        Poliza polizaOrphanCheck = polizaVida.getPoliza();
        if (polizaOrphanCheck != null) {
            PolizaVida oldPolizaVidaOfPoliza = polizaOrphanCheck.getPolizaVida();
            if (oldPolizaVidaOfPoliza != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Poliza " + polizaOrphanCheck + " already has an item of type PolizaVida whose poliza column cannot be null. Please make another selection for the poliza field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Moneda sumaaseguradamoneda = polizaVida.getSumaaseguradamoneda();
            if (sumaaseguradamoneda != null) {
                sumaaseguradamoneda = em.getReference(sumaaseguradamoneda.getClass(), sumaaseguradamoneda.getMoneda());
                polizaVida.setSumaaseguradamoneda(sumaaseguradamoneda);
            }
            Poliza poliza = polizaVida.getPoliza();
            if (poliza != null) {
                poliza = em.getReference(poliza.getClass(), poliza.getIdpoliza());
                polizaVida.setPoliza(poliza);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : polizaVida.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdcliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            polizaVida.setClienteList(attachedClienteList);
            em.persist(polizaVida);
            if (sumaaseguradamoneda != null) {
                sumaaseguradamoneda.getPolizaVidaList().add(polizaVida);
                sumaaseguradamoneda = em.merge(sumaaseguradamoneda);
            }
            if (poliza != null) {
                poliza.setPolizaVida(polizaVida);
                poliza = em.merge(poliza);
            }
            for (Cliente clienteListCliente : polizaVida.getClienteList()) {
                clienteListCliente.getPolizaVidaList().add(polizaVida);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPolizaVida(polizaVida.getIdpoliza()) != null) {
                throw new PreexistingEntityException("PolizaVida " + polizaVida + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PolizaVida polizaVida) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PolizaVida persistentPolizaVida = em.find(PolizaVida.class, polizaVida.getIdpoliza());
            Moneda sumaaseguradamonedaOld = persistentPolizaVida.getSumaaseguradamoneda();
            Moneda sumaaseguradamonedaNew = polizaVida.getSumaaseguradamoneda();
            Poliza polizaOld = persistentPolizaVida.getPoliza();
            Poliza polizaNew = polizaVida.getPoliza();
            List<Cliente> clienteListOld = persistentPolizaVida.getClienteList();
            List<Cliente> clienteListNew = polizaVida.getClienteList();
            List<String> illegalOrphanMessages = null;
            if (polizaNew != null && !polizaNew.equals(polizaOld)) {
                PolizaVida oldPolizaVidaOfPoliza = polizaNew.getPolizaVida();
                if (oldPolizaVidaOfPoliza != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Poliza " + polizaNew + " already has an item of type PolizaVida whose poliza column cannot be null. Please make another selection for the poliza field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sumaaseguradamonedaNew != null) {
                sumaaseguradamonedaNew = em.getReference(sumaaseguradamonedaNew.getClass(), sumaaseguradamonedaNew.getMoneda());
                polizaVida.setSumaaseguradamoneda(sumaaseguradamonedaNew);
            }
            if (polizaNew != null) {
                polizaNew = em.getReference(polizaNew.getClass(), polizaNew.getIdpoliza());
                polizaVida.setPoliza(polizaNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdcliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            polizaVida.setClienteList(clienteListNew);
            polizaVida = em.merge(polizaVida);
            if (sumaaseguradamonedaOld != null && !sumaaseguradamonedaOld.equals(sumaaseguradamonedaNew)) {
                sumaaseguradamonedaOld.getPolizaVidaList().remove(polizaVida);
                sumaaseguradamonedaOld = em.merge(sumaaseguradamonedaOld);
            }
            if (sumaaseguradamonedaNew != null && !sumaaseguradamonedaNew.equals(sumaaseguradamonedaOld)) {
                sumaaseguradamonedaNew.getPolizaVidaList().add(polizaVida);
                sumaaseguradamonedaNew = em.merge(sumaaseguradamonedaNew);
            }
            if (polizaOld != null && !polizaOld.equals(polizaNew)) {
                polizaOld.setPolizaVida(null);
                polizaOld = em.merge(polizaOld);
            }
            if (polizaNew != null && !polizaNew.equals(polizaOld)) {
                polizaNew.setPolizaVida(polizaVida);
                polizaNew = em.merge(polizaNew);
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.getPolizaVidaList().remove(polizaVida);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    clienteListNewCliente.getPolizaVidaList().add(polizaVida);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = polizaVida.getIdpoliza();
                if (findPolizaVida(id) == null) {
                    throw new NonexistentEntityException("The polizaVida with id " + id + " no longer exists.");
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
            PolizaVida polizaVida;
            try {
                polizaVida = em.getReference(PolizaVida.class, id);
                polizaVida.getIdpoliza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The polizaVida with id " + id + " no longer exists.", enfe);
            }
            Moneda sumaaseguradamoneda = polizaVida.getSumaaseguradamoneda();
            if (sumaaseguradamoneda != null) {
                sumaaseguradamoneda.getPolizaVidaList().remove(polizaVida);
                sumaaseguradamoneda = em.merge(sumaaseguradamoneda);
            }
            Poliza poliza = polizaVida.getPoliza();
            if (poliza != null) {
                poliza.setPolizaVida(null);
                poliza = em.merge(poliza);
            }
            List<Cliente> clienteList = polizaVida.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.getPolizaVidaList().remove(polizaVida);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(polizaVida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PolizaVida> findPolizaVidaEntities() {
        return findPolizaVidaEntities(true, -1, -1);
    }

    public List<PolizaVida> findPolizaVidaEntities(int maxResults, int firstResult) {
        return findPolizaVidaEntities(false, maxResults, firstResult);
    }

    private List<PolizaVida> findPolizaVidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PolizaVida.class));
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

    public PolizaVida findPolizaVida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PolizaVida.class, id);
        } finally {
            em.close();
        }
    }

    public int getPolizaVidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PolizaVida> rt = cq.from(PolizaVida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(Object object) throws Exception {
        PolizaVida polizaVida = (PolizaVida) object;
        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }
            BeneficiarioJpaController beneficiarioJpaController = new BeneficiarioJpaController();
            for (Cliente cliente : polizaVida.getClienteList()) {
                beneficiarioJpaController.remove(new Beneficiario(cliente, polizaVida));
            }
//            Poliza parent = polizaVida.getPoliza();
//            parent.setPolizaVida(null);
//            em.merge(parent);
//            em.remove(polizaVida);
            if (!isSubTransaction) {
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public String getControlledClassName() {
        return PolizaVida.class.getSimpleName();
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
