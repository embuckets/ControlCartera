/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Auto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.Email;
import java.util.ArrayList;
import java.util.List;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import jdk.nashorn.internal.objects.Global;

/**
 *
 * @author emilio
 */
public class AseguradoJpaController implements Serializable, JpaController {

//    public AseguradoJpaController(EntityManagerFactory emf) {
//        this.emf = emf;
//    }
    public AseguradoJpaController() {
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Object object) throws EntityExistsException, Exception {
        Asegurado asegurado = (Asegurado) object;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = asegurado.getCliente();
            //save cliente
            em.persist(cliente);
            NotificacionCumple notificacionCumple = new NotificacionCumple(cliente, Globals.NOTIFICACION_ESTADO_PENDIENTE);
            notificacionCumple.setIdcliente(cliente.getIdcliente());
            em.persist(notificacionCumple);
            cliente.setNotificacionCumple(notificacionCumple);
            asegurado.setIdcliente(cliente.getIdcliente());
//            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
//            clienteJpaController.create(cliente);
//            asegurado.setCliente(cliente);
            //save domicilio
            if (asegurado.getIddomicilio() != null) {
                Domicilio domicilio = asegurado.getIddomicilio();
//                DomicilioJpaController domicilioJpaController = new DomicilioJpaController(emf);
//                domicilioJpaController.create(domicilio);
//                asegurado.setIddomicilio(domicilio);
                em.persist(domicilio);
            }
            //Cascade emails y telefono???
            em.persist(asegurado);
            //save emails
            if (asegurado.getEmailList() != null) {
                List<Email> emailsDeAsegurado = asegurado.getEmailList();
                for (Email email : emailsDeAsegurado) {
                    email.getEmailPK().setIdcliente(asegurado.getIdcliente());
                    em.persist(email);
                }
            }
            //save telefonos
            if (asegurado.getTelefonoList() != null) {
                List<Telefono> telefonosDeAsegurado = asegurado.getTelefonoList();
                for (Telefono telefono : telefonosDeAsegurado) {
                    telefono.getTelefonoPK().setIdcliente(asegurado.getIdcliente());
                    em.persist(telefono);
                }
            }
            for (DocumentoAsegurado doc : asegurado.getDocumentoAseguradoList()) {
                doc.getDocumentoAseguradoPK().setIdcliente(cliente.getIdcliente());
                em.persist(doc);
            }

            em.getTransaction().commit();

        } catch (Exception ex) {
//            if (findAsegurado(asegurado.getIdcliente()) != null) {
//                throw new PreexistingEntityException("Asegurado " + asegurado + " already exists.", ex);
//            }
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
//        finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    @Override
    public <T> T edit(Object object) throws Exception {
        Asegurado asegurado = (Asegurado) object;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();

            if (asegurado.getIddomicilio() != null) {
                if (asegurado.getIddomicilio().getCalle() != null) {
                    em.merge(asegurado.getIddomicilio());

                } else {
                    asegurado.getIddomicilio().setAseguradoList(null);
                    asegurado.setIddomicilio(null);
                }
            }
            for (Telefono tel : asegurado.getTelefonoList()) {
                em.merge(tel);
            }
            for (Email tel : asegurado.getEmailList()) {
                em.merge(tel);
            }
            em.merge(asegurado.getCliente());
            Asegurado merged = em.merge(asegurado);
            em.getTransaction().commit();
            return (T) merged;
        } catch (Exception ex) {
//            if (findAsegurado(asegurado.getIdcliente()) != null) {
//                throw new PreexistingEntityException("Asegurado " + asegurado + " already exists.", ex);
//            }
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

//    @Override
//    public <T> List<T> getAll() {
//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            return em.createNamedQuery("Asegurado.findAll").getResultList();
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
//            Query query = em.createNamedQuery("Asegurado.findByIdcliente");
//            query.setParameter("idcliente", id);
//            return (T) query.getSingleResult();
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
//    }
//    @Override
//    public <T> List<T> getAllById(int id) throws Exception {
//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            Query query = em.createNamedQuery("Asegurado.findByIdcliente");
//            query.setParameter("idcliente", id);
//            return query.getResultList();
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
//    }
//    @Override
//    public <T> T edit(Object object) {
//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            em.getTransaction().begin();
//            T merged = em.merge((T) object);
//            em.getTransaction().commit();
//            return merged;
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
//    }
//    @Override
//    public void remove(Object object) {
//        EntityManager em = null;
//        Asegurado asegurado = (Asegurado) object;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            em.getTransaction().begin();
//
//            //
//            if (asegurado.getIddomicilio() != null) {
//                em.remove(asegurado.getIddomicilio());
//            }
//            for (Email email : asegurado.getEmailList()) {
//                em.remove(email);
//            }
//            for (Telefono telefono : asegurado.getTelefonoList()) {
//                em.remove(telefono);
//            }
//            for (DocumentoAsegurado doc : asegurado.getDocumentoAseguradoList()) {
//                em.remove(doc);
//            }
//            for (Poliza poliza : asegurado.getPolizaList()) {
//                //TODO: llamar PolizaJpaController para que elimine la poliza con sus hijos
//                removePoliza(poliza);
//            }
////            em.remove(asegurado.getTipopersona());
//            em.remove(asegurado.getCliente());
//            em.remove(asegurado);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
//    }
    @Override
    public void remove(Object object) throws Exception {
        EntityManager em = null;
        boolean isSubTransaction = false;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }

            Asegurado asegurado = (Asegurado) object;
            int id = asegurado.getIdcliente();
            try {
                asegurado = em.getReference(Asegurado.class, id);
                asegurado.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asegurado with id " + id + " no longer exists.", enfe);
            }
            
            List<String> illegalOrphanMessages = null;
            List<Email> emailListOrphanCheck = asegurado.getEmailList();
            for (Email emailListOrphanCheckEmail : emailListOrphanCheck) {
                emailListOrphanCheckEmail.setAsegurado(null);
                emailListOrphanCheckEmail = em.merge(emailListOrphanCheckEmail);
            }
            List<Poliza> polizaListOrphanCheck = asegurado.getPolizaList();
            PolizaJpaController polizaJpaController = new PolizaJpaController();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                polizaJpaController.remove(polizaListOrphanCheckPoliza);
            }
            List<DocumentoAsegurado> documentoAseguradoListOrphanCheck = asegurado.getDocumentoAseguradoList();
            for (DocumentoAsegurado documentoAseguradoListOrphanCheckDocumentoAsegurado : documentoAseguradoListOrphanCheck) {
                documentoAseguradoListOrphanCheckDocumentoAsegurado.setAsegurado(null);
                documentoAseguradoListOrphanCheckDocumentoAsegurado = em.merge(documentoAseguradoListOrphanCheckDocumentoAsegurado);
            }
            List<Telefono> telefonoListOrphanCheck = asegurado.getTelefonoList();
            for (Telefono telefonoListOrphanCheckTelefono : telefonoListOrphanCheck) {
                telefonoListOrphanCheckTelefono.setAsegurado(null);
                telefonoListOrphanCheckTelefono = em.merge(telefonoListOrphanCheckTelefono);
            }
            Cliente cliente = asegurado.getCliente();
            if (cliente != null) {
                cliente.setAsegurado(null);
                cliente = em.merge(cliente);
            }
            Domicilio iddomicilio = asegurado.getIddomicilio();
            if (iddomicilio != null) {
                iddomicilio.getAseguradoList().remove(asegurado);
                iddomicilio = em.merge(iddomicilio);
            }
            TipoPersona tipopersona = asegurado.getTipopersona();
            if (tipopersona != null) {
                tipopersona.getAseguradoList().remove(asegurado);
                tipopersona = em.merge(tipopersona);
            }
            em.remove(asegurado);
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

    private void removePoliza(Poliza poliza) {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
//            em.getTransaction().begin();//if active throws exception
            if (poliza.getPolizaVida() != null) {
                for (Cliente benef : poliza.getPolizaVida().getClienteList()) {
                    em.remove(benef);
                }
                em.remove(poliza.getPolizaVida());
            }
            if (poliza.getPolizaGmm() != null) {
                for (Cliente benef : poliza.getPolizaVida().getClienteList()) {
                    em.remove(benef);
                }
                em.remove(poliza.getPolizaGmm());
            }
            if (poliza.getPolizaAuto() != null) {
                for (Auto auto : poliza.getPolizaAuto().getAutoList()) {
                    em.remove(auto);
                }
                em.remove(poliza.getPolizaAuto());
            }
            //TODO: Aparentemente si elimina todos los recibos
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    public Asegurado edit(Asegurado asegurado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        Asegurado merged = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            merged = em.merge(asegurado);
//             Asegurado persistentAsegurado = em.find(Asegurado.class, asegurado.getIdcliente());
//             //cambio el nombre
//             persistentAsegurado.getCliente().setNombre(asegurado.getCliente().getNombre());
//             persistentAsegurado.getCliente().setApellidopaterno(asegurado.getCliente().getApellidopaterno());
//             persistentAsegurado.setRfc(asegurado.getRfc());

            //cambio el asegurado
            //cambio del domicilio
            //cambio el telefono
            //cambio el email
//            em.merge(asegurado);
//            Cliente clienteOld = persistentAsegurado.getCliente();
//            Cliente clienteNew = asegurado.getCliente();
//            Domicilio iddomicilioOld = persistentAsegurado.getIddomicilio();
//            Domicilio iddomicilioNew = asegurado.getIddomicilio();
//            TipoPersona tipopersonaOld = persistentAsegurado.getTipopersona();
//            TipoPersona tipopersonaNew = asegurado.getTipopersona();
//            List<Email> emailListOld = persistentAsegurado.getEmailList();
//            List<Email> emailListNew = asegurado.getEmailList();
//            List<Poliza> polizaListOld = persistentAsegurado.getPolizaList();
//            List<Poliza> polizaListNew = asegurado.getPolizaList();
//            List<DocumentoAsegurado> documentoAseguradoListOld = persistentAsegurado.getDocumentoAseguradoList();
//            List<DocumentoAsegurado> documentoAseguradoListNew = asegurado.getDocumentoAseguradoList();
//            List<Telefono> telefonoListOld = persistentAsegurado.getTelefonoList();
//            List<Telefono> telefonoListNew = asegurado.getTelefonoList();
//            List<String> illegalOrphanMessages = null;
//            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
//                Asegurado oldAseguradoOfCliente = clienteNew.getAsegurado();
//                if (oldAseguradoOfCliente != null) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("The Cliente " + clienteNew + " already has an item of type Asegurado whose cliente column cannot be null. Please make another selection for the cliente field.");
//                }
//            }
//            for (Email emailListOldEmail : emailListOld) {
//                if (!emailListNew.contains(emailListOldEmail)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Email " + emailListOldEmail + " since its asegurado field is not nullable.");
//                }
//            }
//            for (Poliza polizaListOldPoliza : polizaListOld) {
//                if (!polizaListNew.contains(polizaListOldPoliza)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its contratante field is not nullable.");
//                }
//            }
//            for (DocumentoAsegurado documentoAseguradoListOldDocumentoAsegurado : documentoAseguradoListOld) {
//                if (!documentoAseguradoListNew.contains(documentoAseguradoListOldDocumentoAsegurado)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain DocumentoAsegurado " + documentoAseguradoListOldDocumentoAsegurado + " since its asegurado field is not nullable.");
//                }
//            }
//            for (Telefono telefonoListOldTelefono : telefonoListOld) {
//                if (!telefonoListNew.contains(telefonoListOldTelefono)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Telefono " + telefonoListOldTelefono + " since its asegurado field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            if (clienteNew != null) {
//                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdcliente());
//                asegurado.setCliente(clienteNew);
//            }
//            if (iddomicilioNew != null) {
//                iddomicilioNew = em.getReference(iddomicilioNew.getClass(), iddomicilioNew.getIddomicilio());
//                asegurado.setIddomicilio(iddomicilioNew);
//            }
//            if (tipopersonaNew != null) {
//                tipopersonaNew = em.getReference(tipopersonaNew.getClass(), tipopersonaNew.getTipopersona());
//                asegurado.setTipopersona(tipopersonaNew);
//            }
//            List<Email> attachedEmailListNew = new ArrayList<Email>();
//            for (Email emailListNewEmailToAttach : emailListNew) {
//                emailListNewEmailToAttach = em.getReference(emailListNewEmailToAttach.getClass(), emailListNewEmailToAttach.getEmailPK());
//                attachedEmailListNew.add(emailListNewEmailToAttach);
//            }
//            emailListNew = attachedEmailListNew;
//            asegurado.setEmailList(emailListNew);
//            List<Poliza> attachedPolizaListNew = new ArrayList<Poliza>();
//            for (Poliza polizaListNewPolizaToAttach : polizaListNew) {
//                polizaListNewPolizaToAttach = em.getReference(polizaListNewPolizaToAttach.getClass(), polizaListNewPolizaToAttach.getIdpoliza());
//                attachedPolizaListNew.add(polizaListNewPolizaToAttach);
//            }
//            polizaListNew = attachedPolizaListNew;
//            asegurado.setPolizaList(polizaListNew);
//            List<DocumentoAsegurado> attachedDocumentoAseguradoListNew = new ArrayList<DocumentoAsegurado>();
//            for (DocumentoAsegurado documentoAseguradoListNewDocumentoAseguradoToAttach : documentoAseguradoListNew) {
//                documentoAseguradoListNewDocumentoAseguradoToAttach = em.getReference(documentoAseguradoListNewDocumentoAseguradoToAttach.getClass(), documentoAseguradoListNewDocumentoAseguradoToAttach.getDocumentoAseguradoPK());
//                attachedDocumentoAseguradoListNew.add(documentoAseguradoListNewDocumentoAseguradoToAttach);
//            }
//            documentoAseguradoListNew = attachedDocumentoAseguradoListNew;
//            asegurado.setDocumentoAseguradoList(documentoAseguradoListNew);
//            List<Telefono> attachedTelefonoListNew = new ArrayList<Telefono>();
//            for (Telefono telefonoListNewTelefonoToAttach : telefonoListNew) {
//                telefonoListNewTelefonoToAttach = em.getReference(telefonoListNewTelefonoToAttach.getClass(), telefonoListNewTelefonoToAttach.getTelefonoPK());
//                attachedTelefonoListNew.add(telefonoListNewTelefonoToAttach);
//            }
//            telefonoListNew = attachedTelefonoListNew;
//            asegurado.setTelefonoList(telefonoListNew);
//            asegurado = em.merge(asegurado);
//            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
//                clienteOld.setAsegurado(null);
//                clienteOld = em.merge(clienteOld);
//            }
//            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
//                clienteNew.setAsegurado(asegurado);
//                clienteNew = em.merge(clienteNew);
//            }
//            if (iddomicilioOld != null && !iddomicilioOld.equals(iddomicilioNew)) {
//                iddomicilioOld.getAseguradoList().remove(asegurado);
//                iddomicilioOld = em.merge(iddomicilioOld);
//            }
//            if (iddomicilioNew != null && !iddomicilioNew.equals(iddomicilioOld)) {
//                iddomicilioNew.getAseguradoList().add(asegurado);
//                iddomicilioNew = em.merge(iddomicilioNew);
//            }
//            if (tipopersonaOld != null && !tipopersonaOld.equals(tipopersonaNew)) {
//                tipopersonaOld.getAseguradoList().remove(asegurado);
//                tipopersonaOld = em.merge(tipopersonaOld);
//            }
//            if (tipopersonaNew != null && !tipopersonaNew.equals(tipopersonaOld)) {
//                tipopersonaNew.getAseguradoList().add(asegurado);
//                tipopersonaNew = em.merge(tipopersonaNew);
//            }
//            for (Email emailListNewEmail : emailListNew) {
//                if (!emailListOld.contains(emailListNewEmail)) {
//                    Asegurado oldAseguradoOfEmailListNewEmail = emailListNewEmail.getAsegurado();
//                    emailListNewEmail.setAsegurado(asegurado);
//                    emailListNewEmail = em.merge(emailListNewEmail);
//                    if (oldAseguradoOfEmailListNewEmail != null && !oldAseguradoOfEmailListNewEmail.equals(asegurado)) {
//                        oldAseguradoOfEmailListNewEmail.getEmailList().remove(emailListNewEmail);
//                        oldAseguradoOfEmailListNewEmail = em.merge(oldAseguradoOfEmailListNewEmail);
//                    }
//                }
//            }
//            for (Poliza polizaListNewPoliza : polizaListNew) {
//                if (!polizaListOld.contains(polizaListNewPoliza)) {
//                    Asegurado oldContratanteOfPolizaListNewPoliza = polizaListNewPoliza.getContratante();
//                    polizaListNewPoliza.setContratante(asegurado);
//                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
//                    if (oldContratanteOfPolizaListNewPoliza != null && !oldContratanteOfPolizaListNewPoliza.equals(asegurado)) {
//                        oldContratanteOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
//                        oldContratanteOfPolizaListNewPoliza = em.merge(oldContratanteOfPolizaListNewPoliza);
//                    }
//                }
//            }
//            for (DocumentoAsegurado documentoAseguradoListNewDocumentoAsegurado : documentoAseguradoListNew) {
//                if (!documentoAseguradoListOld.contains(documentoAseguradoListNewDocumentoAsegurado)) {
//                    Asegurado oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado = documentoAseguradoListNewDocumentoAsegurado.getAsegurado();
//                    documentoAseguradoListNewDocumentoAsegurado.setAsegurado(asegurado);
//                    documentoAseguradoListNewDocumentoAsegurado = em.merge(documentoAseguradoListNewDocumentoAsegurado);
//                    if (oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado != null && !oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado.equals(asegurado)) {
//                        oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado.getDocumentoAseguradoList().remove(documentoAseguradoListNewDocumentoAsegurado);
//                        oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado = em.merge(oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado);
//                    }
//                }
//            }
//            for (Telefono telefonoListNewTelefono : telefonoListNew) {
//                if (!telefonoListOld.contains(telefonoListNewTelefono)) {
//                    Asegurado oldAseguradoOfTelefonoListNewTelefono = telefonoListNewTelefono.getAsegurado();
//                    telefonoListNewTelefono.setAsegurado(asegurado);
//                    telefonoListNewTelefono = em.merge(telefonoListNewTelefono);
//                    if (oldAseguradoOfTelefonoListNewTelefono != null && !oldAseguradoOfTelefonoListNewTelefono.equals(asegurado)) {
//                        oldAseguradoOfTelefonoListNewTelefono.getTelefonoList().remove(telefonoListNewTelefono);
//                        oldAseguradoOfTelefonoListNewTelefono = em.merge(oldAseguradoOfTelefonoListNewTelefono);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asegurado.getIdcliente();
                if (findAsegurado(id) == null) {
                    throw new NonexistentEntityException("The asegurado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return merged;
    }

//    public void create(Asegurado asegurado) throws IllegalOrphanException, PreexistingEntityException, Exception {
//        if (asegurado.getEmailList() == null) {
//            asegurado.setEmailList(new ArrayList<Email>());
//        }
//        if (asegurado.getPolizaList() == null) {
//            asegurado.setPolizaList(new ArrayList<Poliza>());
//        }
//        if (asegurado.getDocumentoAseguradoList() == null) {
//            asegurado.setDocumentoAseguradoList(new ArrayList<DocumentoAsegurado>());
//        }
//        if (asegurado.getTelefonoList() == null) {
//            asegurado.setTelefonoList(new ArrayList<Telefono>());
//        }
//        List<String> illegalOrphanMessages = null;
//        Cliente clienteOrphanCheck = asegurado.getCliente();
//        if (clienteOrphanCheck != null) {
//            Asegurado oldAseguradoOfCliente = clienteOrphanCheck.getAsegurado();
//            if (oldAseguradoOfCliente != null) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("The Cliente " + clienteOrphanCheck + " already has an item of type Asegurado whose cliente column cannot be null. Please make another selection for the cliente field.");
//            }
//        }
//        if (illegalOrphanMessages != null) {
//            throw new IllegalOrphanException(illegalOrphanMessages);
//        }
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Cliente cliente = asegurado.getCliente();
//            if (cliente != null) {
//                try {
//                    // Si el cliente no existe
//                    cliente = em.getReference(cliente.getClass(), cliente.getIdcliente());
//                } catch (Exception exception) {
//                    ClienteJpaController clienteJpaController = new ClienteJpaController(getEntityManager().getEntityManagerFactory());
//                    clienteJpaController.create(cliente);
//                }
//                asegurado.setCliente(cliente);
//            }
//            Domicilio iddomicilio = asegurado.getIddomicilio();
//            if (iddomicilio != null) {
//                try {
//                    // Si no existe el domicilio
//                    iddomicilio = em.getReference(iddomicilio.getClass(), iddomicilio.getIddomicilio());
//                } catch (Exception exception) {
//                    DomicilioJpaController domicilioJpaController = new DomicilioJpaController(getEntityManager().getEntityManagerFactory());
//                    domicilioJpaController.create(iddomicilio);
//                }
//                asegurado.setIddomicilio(iddomicilio);
//            }
//            TipoPersona tipopersona = asegurado.getTipopersona();
//            if (tipopersona != null) {
//                tipopersona = em.getReference(tipopersona.getClass(), tipopersona.getTipopersona());
//                asegurado.setTipopersona(tipopersona);
//            }
//            List<Email> attachedEmailList = new ArrayList<Email>();
//            for (Email emailListEmailToAttach : asegurado.getEmailList()) {
//                emailListEmailToAttach = em.getReference(emailListEmailToAttach.getClass(), emailListEmailToAttach.getEmailPK());
//                attachedEmailList.add(emailListEmailToAttach);
//            }
//            asegurado.setEmailList(attachedEmailList);
//            List<Poliza> attachedPolizaList = new ArrayList<Poliza>();
//            for (Poliza polizaListPolizaToAttach : asegurado.getPolizaList()) {
//                polizaListPolizaToAttach = em.getReference(polizaListPolizaToAttach.getClass(), polizaListPolizaToAttach.getIdpoliza());
//                attachedPolizaList.add(polizaListPolizaToAttach);
//            }
//            asegurado.setPolizaList(attachedPolizaList);
//            List<DocumentoAsegurado> attachedDocumentoAseguradoList = new ArrayList<DocumentoAsegurado>();
//            for (DocumentoAsegurado documentoAseguradoListDocumentoAseguradoToAttach : asegurado.getDocumentoAseguradoList()) {
//                documentoAseguradoListDocumentoAseguradoToAttach = em.getReference(documentoAseguradoListDocumentoAseguradoToAttach.getClass(), documentoAseguradoListDocumentoAseguradoToAttach.getDocumentoAseguradoPK());
//                attachedDocumentoAseguradoList.add(documentoAseguradoListDocumentoAseguradoToAttach);
//            }
//            asegurado.setDocumentoAseguradoList(attachedDocumentoAseguradoList);
//            List<Telefono> attachedTelefonoList = new ArrayList<Telefono>();
//            for (Telefono telefonoListTelefonoToAttach : asegurado.getTelefonoList()) {
//                telefonoListTelefonoToAttach = em.getReference(telefonoListTelefonoToAttach.getClass(), telefonoListTelefonoToAttach.getTelefonoPK());
//                attachedTelefonoList.add(telefonoListTelefonoToAttach);
//            }
//            asegurado.setTelefonoList(attachedTelefonoList);
//            em.persist(asegurado);
//            if (cliente != null) {
//                cliente.setAsegurado(asegurado);
//                cliente = em.merge(cliente);
//            }
//            if (iddomicilio != null) {
//                iddomicilio.getAseguradoList().add(asegurado);
//                iddomicilio = em.merge(iddomicilio);
//            }
//            if (tipopersona != null) {
//                tipopersona.getAseguradoList().add(asegurado);
//                tipopersona = em.merge(tipopersona);
//            }
//            for (Email emailListEmail : asegurado.getEmailList()) {
//                Asegurado oldAseguradoOfEmailListEmail = emailListEmail.getAsegurado();
//                emailListEmail.setAsegurado(asegurado);
//                emailListEmail = em.merge(emailListEmail);
//                if (oldAseguradoOfEmailListEmail != null) {
//                    oldAseguradoOfEmailListEmail.getEmailList().remove(emailListEmail);
//                    oldAseguradoOfEmailListEmail = em.merge(oldAseguradoOfEmailListEmail);
//                }
//            }
//            for (Poliza polizaListPoliza : asegurado.getPolizaList()) {
//                Asegurado oldContratanteOfPolizaListPoliza = polizaListPoliza.getContratante();
//                polizaListPoliza.setContratante(asegurado);
//                polizaListPoliza = em.merge(polizaListPoliza);
//                if (oldContratanteOfPolizaListPoliza != null) {
//                    oldContratanteOfPolizaListPoliza.getPolizaList().remove(polizaListPoliza);
//                    oldContratanteOfPolizaListPoliza = em.merge(oldContratanteOfPolizaListPoliza);
//                }
//            }
//            for (DocumentoAsegurado documentoAseguradoListDocumentoAsegurado : asegurado.getDocumentoAseguradoList()) {
//                Asegurado oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado = documentoAseguradoListDocumentoAsegurado.getAsegurado();
//                documentoAseguradoListDocumentoAsegurado.setAsegurado(asegurado);
//                documentoAseguradoListDocumentoAsegurado = em.merge(documentoAseguradoListDocumentoAsegurado);
//                if (oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado != null) {
//                    oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado.getDocumentoAseguradoList().remove(documentoAseguradoListDocumentoAsegurado);
//                    oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado = em.merge(oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado);
//                }
//            }
//            for (Telefono telefonoListTelefono : asegurado.getTelefonoList()) {
//                Asegurado oldAseguradoOfTelefonoListTelefono = telefonoListTelefono.getAsegurado();
//                telefonoListTelefono.setAsegurado(asegurado);
//                telefonoListTelefono = em.merge(telefonoListTelefono);
//                if (oldAseguradoOfTelefonoListTelefono != null) {
//                    oldAseguradoOfTelefonoListTelefono.getTelefonoList().remove(telefonoListTelefono);
//                    oldAseguradoOfTelefonoListTelefono = em.merge(oldAseguradoOfTelefonoListTelefono);
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findAsegurado(asegurado.getIdcliente()) != null) {
//                throw new PreexistingEntityException("Asegurado " + asegurado + " already exists.", ex);
//            }
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//    public void edit(Asegurado asegurado) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Asegurado persistentAsegurado = em.find(Asegurado.class, asegurado.getIdcliente());
//            Cliente clienteOld = persistentAsegurado.getCliente();
//            Cliente clienteNew = asegurado.getCliente();
//            Domicilio iddomicilioOld = persistentAsegurado.getIddomicilio();
//            Domicilio iddomicilioNew = asegurado.getIddomicilio();
//            TipoPersona tipopersonaOld = persistentAsegurado.getTipopersona();
//            TipoPersona tipopersonaNew = asegurado.getTipopersona();
//            List<Email> emailListOld = persistentAsegurado.getEmailList();
//            List<Email> emailListNew = asegurado.getEmailList();
//            List<Poliza> polizaListOld = persistentAsegurado.getPolizaList();
//            List<Poliza> polizaListNew = asegurado.getPolizaList();
//            List<DocumentoAsegurado> documentoAseguradoListOld = persistentAsegurado.getDocumentoAseguradoList();
//            List<DocumentoAsegurado> documentoAseguradoListNew = asegurado.getDocumentoAseguradoList();
//            List<Telefono> telefonoListOld = persistentAsegurado.getTelefonoList();
//            List<Telefono> telefonoListNew = asegurado.getTelefonoList();
//            List<String> illegalOrphanMessages = null;
//            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
//                Asegurado oldAseguradoOfCliente = clienteNew.getAsegurado();
//                if (oldAseguradoOfCliente != null) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("The Cliente " + clienteNew + " already has an item of type Asegurado whose cliente column cannot be null. Please make another selection for the cliente field.");
//                }
//            }
//            for (Email emailListOldEmail : emailListOld) {
//                if (!emailListNew.contains(emailListOldEmail)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Email " + emailListOldEmail + " since its asegurado field is not nullable.");
//                }
//            }
//            for (Poliza polizaListOldPoliza : polizaListOld) {
//                if (!polizaListNew.contains(polizaListOldPoliza)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its contratante field is not nullable.");
//                }
//            }
//            for (DocumentoAsegurado documentoAseguradoListOldDocumentoAsegurado : documentoAseguradoListOld) {
//                if (!documentoAseguradoListNew.contains(documentoAseguradoListOldDocumentoAsegurado)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain DocumentoAsegurado " + documentoAseguradoListOldDocumentoAsegurado + " since its asegurado field is not nullable.");
//                }
//            }
//            for (Telefono telefonoListOldTelefono : telefonoListOld) {
//                if (!telefonoListNew.contains(telefonoListOldTelefono)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Telefono " + telefonoListOldTelefono + " since its asegurado field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            if (clienteNew != null) {
//                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdcliente());
//                asegurado.setCliente(clienteNew);
//            }
//            if (iddomicilioNew != null) {
//                iddomicilioNew = em.getReference(iddomicilioNew.getClass(), iddomicilioNew.getIddomicilio());
//                asegurado.setIddomicilio(iddomicilioNew);
//            }
//            if (tipopersonaNew != null) {
//                tipopersonaNew = em.getReference(tipopersonaNew.getClass(), tipopersonaNew.getTipopersona());
//                asegurado.setTipopersona(tipopersonaNew);
//            }
//            List<Email> attachedEmailListNew = new ArrayList<Email>();
//            for (Email emailListNewEmailToAttach : emailListNew) {
//                emailListNewEmailToAttach = em.getReference(emailListNewEmailToAttach.getClass(), emailListNewEmailToAttach.getEmailPK());
//                attachedEmailListNew.add(emailListNewEmailToAttach);
//            }
//            emailListNew = attachedEmailListNew;
//            asegurado.setEmailList(emailListNew);
//            List<Poliza> attachedPolizaListNew = new ArrayList<Poliza>();
//            for (Poliza polizaListNewPolizaToAttach : polizaListNew) {
//                polizaListNewPolizaToAttach = em.getReference(polizaListNewPolizaToAttach.getClass(), polizaListNewPolizaToAttach.getIdpoliza());
//                attachedPolizaListNew.add(polizaListNewPolizaToAttach);
//            }
//            polizaListNew = attachedPolizaListNew;
//            asegurado.setPolizaList(polizaListNew);
//            List<DocumentoAsegurado> attachedDocumentoAseguradoListNew = new ArrayList<DocumentoAsegurado>();
//            for (DocumentoAsegurado documentoAseguradoListNewDocumentoAseguradoToAttach : documentoAseguradoListNew) {
//                documentoAseguradoListNewDocumentoAseguradoToAttach = em.getReference(documentoAseguradoListNewDocumentoAseguradoToAttach.getClass(), documentoAseguradoListNewDocumentoAseguradoToAttach.getDocumentoAseguradoPK());
//                attachedDocumentoAseguradoListNew.add(documentoAseguradoListNewDocumentoAseguradoToAttach);
//            }
//            documentoAseguradoListNew = attachedDocumentoAseguradoListNew;
//            asegurado.setDocumentoAseguradoList(documentoAseguradoListNew);
//            List<Telefono> attachedTelefonoListNew = new ArrayList<Telefono>();
//            for (Telefono telefonoListNewTelefonoToAttach : telefonoListNew) {
//                telefonoListNewTelefonoToAttach = em.getReference(telefonoListNewTelefonoToAttach.getClass(), telefonoListNewTelefonoToAttach.getTelefonoPK());
//                attachedTelefonoListNew.add(telefonoListNewTelefonoToAttach);
//            }
//            telefonoListNew = attachedTelefonoListNew;
//            asegurado.setTelefonoList(telefonoListNew);
//            asegurado = em.merge(asegurado);
//            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
//                clienteOld.setAsegurado(null);
//                clienteOld = em.merge(clienteOld);
//            }
//            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
//                clienteNew.setAsegurado(asegurado);
//                clienteNew = em.merge(clienteNew);
//            }
//            if (iddomicilioOld != null && !iddomicilioOld.equals(iddomicilioNew)) {
//                iddomicilioOld.getAseguradoList().remove(asegurado);
//                iddomicilioOld = em.merge(iddomicilioOld);
//            }
//            if (iddomicilioNew != null && !iddomicilioNew.equals(iddomicilioOld)) {
//                iddomicilioNew.getAseguradoList().add(asegurado);
//                iddomicilioNew = em.merge(iddomicilioNew);
//            }
//            if (tipopersonaOld != null && !tipopersonaOld.equals(tipopersonaNew)) {
//                tipopersonaOld.getAseguradoList().remove(asegurado);
//                tipopersonaOld = em.merge(tipopersonaOld);
//            }
//            if (tipopersonaNew != null && !tipopersonaNew.equals(tipopersonaOld)) {
//                tipopersonaNew.getAseguradoList().add(asegurado);
//                tipopersonaNew = em.merge(tipopersonaNew);
//            }
//            for (Email emailListNewEmail : emailListNew) {
//                if (!emailListOld.contains(emailListNewEmail)) {
//                    Asegurado oldAseguradoOfEmailListNewEmail = emailListNewEmail.getAsegurado();
//                    emailListNewEmail.setAsegurado(asegurado);
//                    emailListNewEmail = em.merge(emailListNewEmail);
//                    if (oldAseguradoOfEmailListNewEmail != null && !oldAseguradoOfEmailListNewEmail.equals(asegurado)) {
//                        oldAseguradoOfEmailListNewEmail.getEmailList().remove(emailListNewEmail);
//                        oldAseguradoOfEmailListNewEmail = em.merge(oldAseguradoOfEmailListNewEmail);
//                    }
//                }
//            }
//            for (Poliza polizaListNewPoliza : polizaListNew) {
//                if (!polizaListOld.contains(polizaListNewPoliza)) {
//                    Asegurado oldContratanteOfPolizaListNewPoliza = polizaListNewPoliza.getContratante();
//                    polizaListNewPoliza.setContratante(asegurado);
//                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
//                    if (oldContratanteOfPolizaListNewPoliza != null && !oldContratanteOfPolizaListNewPoliza.equals(asegurado)) {
//                        oldContratanteOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
//                        oldContratanteOfPolizaListNewPoliza = em.merge(oldContratanteOfPolizaListNewPoliza);
//                    }
//                }
//            }
//            for (DocumentoAsegurado documentoAseguradoListNewDocumentoAsegurado : documentoAseguradoListNew) {
//                if (!documentoAseguradoListOld.contains(documentoAseguradoListNewDocumentoAsegurado)) {
//                    Asegurado oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado = documentoAseguradoListNewDocumentoAsegurado.getAsegurado();
//                    documentoAseguradoListNewDocumentoAsegurado.setAsegurado(asegurado);
//                    documentoAseguradoListNewDocumentoAsegurado = em.merge(documentoAseguradoListNewDocumentoAsegurado);
//                    if (oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado != null && !oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado.equals(asegurado)) {
//                        oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado.getDocumentoAseguradoList().remove(documentoAseguradoListNewDocumentoAsegurado);
//                        oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado = em.merge(oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado);
//                    }
//                }
//            }
//            for (Telefono telefonoListNewTelefono : telefonoListNew) {
//                if (!telefonoListOld.contains(telefonoListNewTelefono)) {
//                    Asegurado oldAseguradoOfTelefonoListNewTelefono = telefonoListNewTelefono.getAsegurado();
//                    telefonoListNewTelefono.setAsegurado(asegurado);
//                    telefonoListNewTelefono = em.merge(telefonoListNewTelefono);
//                    if (oldAseguradoOfTelefonoListNewTelefono != null && !oldAseguradoOfTelefonoListNewTelefono.equals(asegurado)) {
//                        oldAseguradoOfTelefonoListNewTelefono.getTelefonoList().remove(telefonoListNewTelefono);
//                        oldAseguradoOfTelefonoListNewTelefono = em.merge(oldAseguradoOfTelefonoListNewTelefono);
//                    }
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = asegurado.getIdcliente();
//                if (findAsegurado(id) == null) {
//                    throw new NonexistentEntityException("The asegurado with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asegurado asegurado;
            try {
                asegurado = em.getReference(Asegurado.class, id);
                asegurado.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asegurado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Email> emailListOrphanCheck = asegurado.getEmailList();
            for (Email emailListOrphanCheckEmail : emailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asegurado (" + asegurado + ") cannot be destroyed since the Email " + emailListOrphanCheckEmail + " in its emailList field has a non-nullable asegurado field.");
            }
            List<Poliza> polizaListOrphanCheck = asegurado.getPolizaList();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asegurado (" + asegurado + ") cannot be destroyed since the Poliza " + polizaListOrphanCheckPoliza + " in its polizaList field has a non-nullable contratante field.");
            }
            List<DocumentoAsegurado> documentoAseguradoListOrphanCheck = asegurado.getDocumentoAseguradoList();
            for (DocumentoAsegurado documentoAseguradoListOrphanCheckDocumentoAsegurado : documentoAseguradoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asegurado (" + asegurado + ") cannot be destroyed since the DocumentoAsegurado " + documentoAseguradoListOrphanCheckDocumentoAsegurado + " in its documentoAseguradoList field has a non-nullable asegurado field.");
            }
            List<Telefono> telefonoListOrphanCheck = asegurado.getTelefonoList();
            for (Telefono telefonoListOrphanCheckTelefono : telefonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asegurado (" + asegurado + ") cannot be destroyed since the Telefono " + telefonoListOrphanCheckTelefono + " in its telefonoList field has a non-nullable asegurado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente cliente = asegurado.getCliente();
            if (cliente != null) {
                cliente.setAsegurado(null);
                cliente = em.merge(cliente);
            }
            Domicilio iddomicilio = asegurado.getIddomicilio();
            if (iddomicilio != null) {
                iddomicilio.getAseguradoList().remove(asegurado);
                iddomicilio = em.merge(iddomicilio);
            }
            TipoPersona tipopersona = asegurado.getTipopersona();
            if (tipopersona != null) {
                tipopersona.getAseguradoList().remove(asegurado);
                tipopersona = em.merge(tipopersona);
            }
            em.remove(asegurado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asegurado> findAseguradoEntities() {
        return findAseguradoEntities(true, -1, -1);
    }

    public List<Asegurado> findAseguradoEntities(int maxResults, int firstResult) {
        return findAseguradoEntities(false, maxResults, firstResult);
    }

    private List<Asegurado> findAseguradoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asegurado.class));
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

    public Asegurado findAsegurado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asegurado.class, id);
        } finally {
            em.close();
        }
    }

    public int getAseguradoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asegurado> rt = cq.from(Asegurado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Asegurado findAseguradoCompleto(int idcliente) {
        EntityManager em = getEntityManager();
        try {
            EntityGraph graph = em.getEntityGraph("asegurado-graph-full-noDoc-noDom");
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.pesistence.loadgraph", graph);
            return em.find(Asegurado.class, idcliente, properties);
        } finally {
            em.close();
        }
    }

    public Asegurado findAseguradoCompletoConApi(int idcliente) {
        EntityManager em = getEntityManager();
        try {
            EntityGraph graph = em.getEntityGraph("asegurado-IncludeAll");
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.pesistence.loadgraph", graph);
            return em.find(Asegurado.class, idcliente, properties);
        } finally {
            em.close();
        }
    }

    public Asegurado findAseguradoCompletoConQuery(int idcliente) {
        EntityManager em = getEntityManager();
        try {
//            TypedQuery<Asegurado> createQuery = em.createQuery("SELECT a FROM Asegurado a JOIN FETCH a.cliente WHERE a.idcliente = :idcliente", Asegurado.class);
            TypedQuery<Asegurado> createQuery = em.createQuery("SELECT a FROM Asegurado a JOIN FETCH a.cliente c JOIN FETCH a.tipopersona t JOIN FETCH a.emailList e JOIN FETCH e.tipoemail WHERE a.idcliente = :idcliente", Asegurado.class);
            createQuery.setParameter("idcliente", idcliente);
            return createQuery.getSingleResult();
//            EntityGraph graph = em.getEntityGraph("asegurado-IncludeAll");
//            Map<String, Object> properties = new HashMap<>();
//            properties.put("javax.pesistence.loadgraph", graph);
//            return em.find(Asegurado.class, idcliente, properties);
        } finally {
            em.close();
        }
    }

    public Asegurado findAseguradoConCliente(int idcliente) {
        EntityManager em = getEntityManager();
        try {
            EntityGraph graph = em.createEntityGraph(Asegurado.class);
            graph.addSubgraph("cliente").addAttributeNodes("idcliente", "nombre", "apellidopaterno", "apellidomaterno", "nacimiento", "polizaGmmList", "polizaVidaList", "asegurado", "notificacionCumple", "polizaList");
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.pesistence.loadgraph", graph);
            return em.find(Asegurado.class, idcliente, properties);
        } finally {
            em.close();
        }
    }

    @Override
    public String getControlledClassName() {
        return Asegurado.class.getSimpleName();
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
