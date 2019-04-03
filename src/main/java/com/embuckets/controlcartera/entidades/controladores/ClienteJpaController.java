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
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import java.util.ArrayList;
import java.util.List;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class ClienteJpaController implements Serializable, JpaController {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public ClienteJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Cliente> getByName(String nombre, String paterno, String materno) {
        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
//            if (em.getTransaction().isActive()) {
//                isSubTransaction = true;
//            }
//            if (!isSubTransaction) {
//                em.getTransaction().begin();
//            }

            StringBuilder sb = new StringBuilder("SELECT a FROM Cliente a WHERE ");
            if (nombre != null && !nombre.isEmpty()) {
                sb.append("a.nombre LIKE :nombre ");
            }
            if (paterno != null && !paterno.isEmpty()) {
                if (nombre != null && !nombre.isEmpty()){
                    sb.append("AND ");
                }
                sb.append("a.apellidopaterno LIKE :paterno ");
            }
            if (materno != null && !materno.isEmpty()) {
                if ((nombre != null && !nombre.isEmpty()) || (paterno != null && !paterno.isEmpty())){
                    sb.append("AND ");
                }
                sb.append("a.apellidomaterno LIKE :materno ");
            }

            Query query = em.createQuery(sb.toString());
            if (nombre != null && !nombre.isEmpty()) {
                query.setParameter("nombre", "%" + nombre + "%");
            }
            if (paterno != null && !paterno.isEmpty()) {
                query.setParameter("paterno", "%" + paterno + "%");
            }
            if (materno != null && !materno.isEmpty()) {
                query.setParameter("materno", "%" + materno + "%");
            }
            return query.getResultList();

//            if (!isSubTransaction) {
//                em.getTransaction().commit();
//            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    public void create(Cliente cliente) {
        if (cliente.getPolizaGmmList() == null) {
            cliente.setPolizaGmmList(new ArrayList<PolizaGmm>());
        }
        if (cliente.getPolizaVidaList() == null) {
            cliente.setPolizaVidaList(new ArrayList<PolizaVida>());
        }
        if (cliente.getPolizaList() == null) {
            cliente.setPolizaList(new ArrayList<Poliza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asegurado asegurado = cliente.getAsegurado();
            if (asegurado != null) {
                asegurado = em.getReference(asegurado.getClass(), asegurado.getIdcliente());
                cliente.setAsegurado(asegurado);
            }
            NotificacionCumple notificacionCumple = cliente.getNotificacionCumple();
            if (notificacionCumple != null) {
                notificacionCumple = em.getReference(notificacionCumple.getClass(), notificacionCumple.getIdcliente());
                cliente.setNotificacionCumple(notificacionCumple);
            }
            List<PolizaGmm> attachedPolizaGmmList = new ArrayList<PolizaGmm>();
            for (PolizaGmm polizaGmmListPolizaGmmToAttach : cliente.getPolizaGmmList()) {
                polizaGmmListPolizaGmmToAttach = em.getReference(polizaGmmListPolizaGmmToAttach.getClass(), polizaGmmListPolizaGmmToAttach.getIdpoliza());
                attachedPolizaGmmList.add(polizaGmmListPolizaGmmToAttach);
            }
            cliente.setPolizaGmmList(attachedPolizaGmmList);
            List<PolizaVida> attachedPolizaVidaList = new ArrayList<PolizaVida>();
            for (PolizaVida polizaVidaListPolizaVidaToAttach : cliente.getPolizaVidaList()) {
                polizaVidaListPolizaVidaToAttach = em.getReference(polizaVidaListPolizaVidaToAttach.getClass(), polizaVidaListPolizaVidaToAttach.getIdpoliza());
                attachedPolizaVidaList.add(polizaVidaListPolizaVidaToAttach);
            }
            cliente.setPolizaVidaList(attachedPolizaVidaList);
            List<Poliza> attachedPolizaList = new ArrayList<Poliza>();
            for (Poliza polizaListPolizaToAttach : cliente.getPolizaList()) {
                polizaListPolizaToAttach = em.getReference(polizaListPolizaToAttach.getClass(), polizaListPolizaToAttach.getIdpoliza());
                attachedPolizaList.add(polizaListPolizaToAttach);
            }
            cliente.setPolizaList(attachedPolizaList);
            em.persist(cliente);
            if (asegurado != null) {
                Cliente oldClienteOfAsegurado = asegurado.getCliente();
                if (oldClienteOfAsegurado != null) {
                    oldClienteOfAsegurado.setAsegurado(null);
                    oldClienteOfAsegurado = em.merge(oldClienteOfAsegurado);
                }
                asegurado.setCliente(cliente);
                asegurado = em.merge(asegurado);
            }
            if (notificacionCumple != null) {
                Cliente oldClienteOfNotificacionCumple = notificacionCumple.getCliente();
                if (oldClienteOfNotificacionCumple != null) {
                    oldClienteOfNotificacionCumple.setNotificacionCumple(null);
                    oldClienteOfNotificacionCumple = em.merge(oldClienteOfNotificacionCumple);
                }
                notificacionCumple.setCliente(cliente);
                notificacionCumple = em.merge(notificacionCumple);
            }
            for (PolizaGmm polizaGmmListPolizaGmm : cliente.getPolizaGmmList()) {
                polizaGmmListPolizaGmm.getClienteList().add(cliente);
                polizaGmmListPolizaGmm = em.merge(polizaGmmListPolizaGmm);
            }
            for (PolizaVida polizaVidaListPolizaVida : cliente.getPolizaVidaList()) {
                polizaVidaListPolizaVida.getClienteList().add(cliente);
                polizaVidaListPolizaVida = em.merge(polizaVidaListPolizaVida);
            }
            for (Poliza polizaListPoliza : cliente.getPolizaList()) {
                Cliente oldTitularOfPolizaListPoliza = polizaListPoliza.getTitular();
                polizaListPoliza.setTitular(cliente);
                polizaListPoliza = em.merge(polizaListPoliza);
                if (oldTitularOfPolizaListPoliza != null) {
                    oldTitularOfPolizaListPoliza.getPolizaList().remove(polizaListPoliza);
                    oldTitularOfPolizaListPoliza = em.merge(oldTitularOfPolizaListPoliza);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
            Asegurado aseguradoOld = persistentCliente.getAsegurado();
            Asegurado aseguradoNew = cliente.getAsegurado();
            NotificacionCumple notificacionCumpleOld = persistentCliente.getNotificacionCumple();
            NotificacionCumple notificacionCumpleNew = cliente.getNotificacionCumple();
            List<PolizaGmm> polizaGmmListOld = persistentCliente.getPolizaGmmList();
            List<PolizaGmm> polizaGmmListNew = cliente.getPolizaGmmList();
            List<PolizaVida> polizaVidaListOld = persistentCliente.getPolizaVidaList();
            List<PolizaVida> polizaVidaListNew = cliente.getPolizaVidaList();
            List<Poliza> polizaListOld = persistentCliente.getPolizaList();
            List<Poliza> polizaListNew = cliente.getPolizaList();
            List<String> illegalOrphanMessages = null;
            if (aseguradoOld != null && !aseguradoOld.equals(aseguradoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Asegurado " + aseguradoOld + " since its cliente field is not nullable.");
            }
            if (notificacionCumpleOld != null && !notificacionCumpleOld.equals(notificacionCumpleNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain NotificacionCumple " + notificacionCumpleOld + " since its cliente field is not nullable.");
            }
            for (Poliza polizaListOldPoliza : polizaListOld) {
                if (!polizaListNew.contains(polizaListOldPoliza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its titular field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (aseguradoNew != null) {
                aseguradoNew = em.getReference(aseguradoNew.getClass(), aseguradoNew.getIdcliente());
                cliente.setAsegurado(aseguradoNew);
            }
            if (notificacionCumpleNew != null) {
                notificacionCumpleNew = em.getReference(notificacionCumpleNew.getClass(), notificacionCumpleNew.getIdcliente());
                cliente.setNotificacionCumple(notificacionCumpleNew);
            }
            List<PolizaGmm> attachedPolizaGmmListNew = new ArrayList<PolizaGmm>();
            for (PolizaGmm polizaGmmListNewPolizaGmmToAttach : polizaGmmListNew) {
                polizaGmmListNewPolizaGmmToAttach = em.getReference(polizaGmmListNewPolizaGmmToAttach.getClass(), polizaGmmListNewPolizaGmmToAttach.getIdpoliza());
                attachedPolizaGmmListNew.add(polizaGmmListNewPolizaGmmToAttach);
            }
            polizaGmmListNew = attachedPolizaGmmListNew;
            cliente.setPolizaGmmList(polizaGmmListNew);
            List<PolizaVida> attachedPolizaVidaListNew = new ArrayList<PolizaVida>();
            for (PolizaVida polizaVidaListNewPolizaVidaToAttach : polizaVidaListNew) {
                polizaVidaListNewPolizaVidaToAttach = em.getReference(polizaVidaListNewPolizaVidaToAttach.getClass(), polizaVidaListNewPolizaVidaToAttach.getIdpoliza());
                attachedPolizaVidaListNew.add(polizaVidaListNewPolizaVidaToAttach);
            }
            polizaVidaListNew = attachedPolizaVidaListNew;
            cliente.setPolizaVidaList(polizaVidaListNew);
            List<Poliza> attachedPolizaListNew = new ArrayList<Poliza>();
            for (Poliza polizaListNewPolizaToAttach : polizaListNew) {
                polizaListNewPolizaToAttach = em.getReference(polizaListNewPolizaToAttach.getClass(), polizaListNewPolizaToAttach.getIdpoliza());
                attachedPolizaListNew.add(polizaListNewPolizaToAttach);
            }
            polizaListNew = attachedPolizaListNew;
            cliente.setPolizaList(polizaListNew);
            cliente = em.merge(cliente);
            if (aseguradoNew != null && !aseguradoNew.equals(aseguradoOld)) {
                Cliente oldClienteOfAsegurado = aseguradoNew.getCliente();
                if (oldClienteOfAsegurado != null) {
                    oldClienteOfAsegurado.setAsegurado(null);
                    oldClienteOfAsegurado = em.merge(oldClienteOfAsegurado);
                }
                aseguradoNew.setCliente(cliente);
                aseguradoNew = em.merge(aseguradoNew);
            }
            if (notificacionCumpleNew != null && !notificacionCumpleNew.equals(notificacionCumpleOld)) {
                Cliente oldClienteOfNotificacionCumple = notificacionCumpleNew.getCliente();
                if (oldClienteOfNotificacionCumple != null) {
                    oldClienteOfNotificacionCumple.setNotificacionCumple(null);
                    oldClienteOfNotificacionCumple = em.merge(oldClienteOfNotificacionCumple);
                }
                notificacionCumpleNew.setCliente(cliente);
                notificacionCumpleNew = em.merge(notificacionCumpleNew);
            }
            for (PolizaGmm polizaGmmListOldPolizaGmm : polizaGmmListOld) {
                if (!polizaGmmListNew.contains(polizaGmmListOldPolizaGmm)) {
                    polizaGmmListOldPolizaGmm.getClienteList().remove(cliente);
                    polizaGmmListOldPolizaGmm = em.merge(polizaGmmListOldPolizaGmm);
                }
            }
            for (PolizaGmm polizaGmmListNewPolizaGmm : polizaGmmListNew) {
                if (!polizaGmmListOld.contains(polizaGmmListNewPolizaGmm)) {
                    polizaGmmListNewPolizaGmm.getClienteList().add(cliente);
                    polizaGmmListNewPolizaGmm = em.merge(polizaGmmListNewPolizaGmm);
                }
            }
            for (PolizaVida polizaVidaListOldPolizaVida : polizaVidaListOld) {
                if (!polizaVidaListNew.contains(polizaVidaListOldPolizaVida)) {
                    polizaVidaListOldPolizaVida.getClienteList().remove(cliente);
                    polizaVidaListOldPolizaVida = em.merge(polizaVidaListOldPolizaVida);
                }
            }
            for (PolizaVida polizaVidaListNewPolizaVida : polizaVidaListNew) {
                if (!polizaVidaListOld.contains(polizaVidaListNewPolizaVida)) {
                    polizaVidaListNewPolizaVida.getClienteList().add(cliente);
                    polizaVidaListNewPolizaVida = em.merge(polizaVidaListNewPolizaVida);
                }
            }
            for (Poliza polizaListNewPoliza : polizaListNew) {
                if (!polizaListOld.contains(polizaListNewPoliza)) {
                    Cliente oldTitularOfPolizaListNewPoliza = polizaListNewPoliza.getTitular();
                    polizaListNewPoliza.setTitular(cliente);
                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
                    if (oldTitularOfPolizaListNewPoliza != null && !oldTitularOfPolizaListNewPoliza.equals(cliente)) {
                        oldTitularOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
                        oldTitularOfPolizaListNewPoliza = em.merge(oldTitularOfPolizaListNewPoliza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Asegurado aseguradoOrphanCheck = cliente.getAsegurado();
            if (aseguradoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Asegurado " + aseguradoOrphanCheck + " in its asegurado field has a non-nullable cliente field.");
            }
            NotificacionCumple notificacionCumpleOrphanCheck = cliente.getNotificacionCumple();
            if (notificacionCumpleOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the NotificacionCumple " + notificacionCumpleOrphanCheck + " in its notificacionCumple field has a non-nullable cliente field.");
            }
            List<Poliza> polizaListOrphanCheck = cliente.getPolizaList();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Poliza " + polizaListOrphanCheckPoliza + " in its polizaList field has a non-nullable titular field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PolizaGmm> polizaGmmList = cliente.getPolizaGmmList();
            for (PolizaGmm polizaGmmListPolizaGmm : polizaGmmList) {
                polizaGmmListPolizaGmm.getClienteList().remove(cliente);
                polizaGmmListPolizaGmm = em.merge(polizaGmmListPolizaGmm);
            }
            List<PolizaVida> polizaVidaList = cliente.getPolizaVidaList();
            for (PolizaVida polizaVidaListPolizaVida : polizaVidaList) {
                polizaVidaListPolizaVida.getClienteList().remove(cliente);
                polizaVidaListPolizaVida = em.merge(polizaVidaListPolizaVida);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public String getControlledClassName() {
        return Cliente.class.getSimpleName();
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
