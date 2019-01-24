/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Asegurado;
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
import com.embuckets.controlcartera.entidades.DocumentoAsegurado_1;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class AseguradoJpaController implements Serializable {

    public AseguradoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asegurado asegurado) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (asegurado.getEmailList() == null) {
            asegurado.setEmailList(new ArrayList<Email>());
        }
        if (asegurado.getPolizaList() == null) {
            asegurado.setPolizaList(new ArrayList<Poliza>());
        }
        if (asegurado.getPolizaList1() == null) {
            asegurado.setPolizaList1(new ArrayList<Poliza>());
        }
        if (asegurado.getDocumentoAseguradoList() == null) {
            asegurado.setDocumentoAseguradoList(new ArrayList<DocumentoAsegurado_1>());
        }
        if (asegurado.getTelefonoList() == null) {
            asegurado.setTelefonoList(new ArrayList<Telefono>());
        }
        List<String> illegalOrphanMessages = null;
        Cliente clienteOrphanCheck = asegurado.getCliente();
        if (clienteOrphanCheck != null) {
            Asegurado oldAseguradoOfCliente = clienteOrphanCheck.getAsegurado();
            if (oldAseguradoOfCliente != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cliente " + clienteOrphanCheck + " already has an item of type Asegurado whose cliente column cannot be null. Please make another selection for the cliente field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = asegurado.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdcliente());
                asegurado.setCliente(cliente);
            }
            Domicilio iddomicilio = asegurado.getIddomicilio();
            if (iddomicilio != null) {
                iddomicilio = em.getReference(iddomicilio.getClass(), iddomicilio.getIddomicilio());
                asegurado.setIddomicilio(iddomicilio);
            }
            TipoPersona tipopersona = asegurado.getTipopersona();
            if (tipopersona != null) {
                tipopersona = em.getReference(tipopersona.getClass(), tipopersona.getTipopersona());
                asegurado.setTipopersona(tipopersona);
            }
            List<Email> attachedEmailList = new ArrayList<Email>();
            for (Email emailListEmailToAttach : asegurado.getEmailList()) {
                emailListEmailToAttach = em.getReference(emailListEmailToAttach.getClass(), emailListEmailToAttach.getEmailPK());
                attachedEmailList.add(emailListEmailToAttach);
            }
            asegurado.setEmailList(attachedEmailList);
            List<Poliza> attachedPolizaList = new ArrayList<Poliza>();
            for (Poliza polizaListPolizaToAttach : asegurado.getPolizaList()) {
                polizaListPolizaToAttach = em.getReference(polizaListPolizaToAttach.getClass(), polizaListPolizaToAttach.getIdpoliza());
                attachedPolizaList.add(polizaListPolizaToAttach);
            }
            asegurado.setPolizaList(attachedPolizaList);
            List<Poliza> attachedPolizaList1 = new ArrayList<Poliza>();
            for (Poliza polizaList1PolizaToAttach : asegurado.getPolizaList1()) {
                polizaList1PolizaToAttach = em.getReference(polizaList1PolizaToAttach.getClass(), polizaList1PolizaToAttach.getIdpoliza());
                attachedPolizaList1.add(polizaList1PolizaToAttach);
            }
            asegurado.setPolizaList1(attachedPolizaList1);
            List<DocumentoAsegurado_1> attachedDocumentoAseguradoList = new ArrayList<DocumentoAsegurado_1>();
            for (DocumentoAsegurado_1 documentoAseguradoListDocumentoAsegurado_1ToAttach : asegurado.getDocumentoAseguradoList()) {
                documentoAseguradoListDocumentoAsegurado_1ToAttach = em.getReference(documentoAseguradoListDocumentoAsegurado_1ToAttach.getClass(), documentoAseguradoListDocumentoAsegurado_1ToAttach.getDocumentoAseguradoPK());
                attachedDocumentoAseguradoList.add(documentoAseguradoListDocumentoAsegurado_1ToAttach);
            }
            asegurado.setDocumentoAseguradoList(attachedDocumentoAseguradoList);
            List<Telefono> attachedTelefonoList = new ArrayList<Telefono>();
            for (Telefono telefonoListTelefonoToAttach : asegurado.getTelefonoList()) {
                telefonoListTelefonoToAttach = em.getReference(telefonoListTelefonoToAttach.getClass(), telefonoListTelefonoToAttach.getTelefonoPK());
                attachedTelefonoList.add(telefonoListTelefonoToAttach);
            }
            asegurado.setTelefonoList(attachedTelefonoList);
            em.persist(asegurado);
            if (cliente != null) {
                cliente.setAsegurado(asegurado);
                cliente = em.merge(cliente);
            }
            if (iddomicilio != null) {
                iddomicilio.getAseguradoList().add(asegurado);
                iddomicilio = em.merge(iddomicilio);
            }
            if (tipopersona != null) {
                tipopersona.getAseguradoList().add(asegurado);
                tipopersona = em.merge(tipopersona);
            }
            for (Email emailListEmail : asegurado.getEmailList()) {
                Asegurado oldAseguradoOfEmailListEmail = emailListEmail.getAsegurado();
                emailListEmail.setAsegurado(asegurado);
                emailListEmail = em.merge(emailListEmail);
                if (oldAseguradoOfEmailListEmail != null) {
                    oldAseguradoOfEmailListEmail.getEmailList().remove(emailListEmail);
                    oldAseguradoOfEmailListEmail = em.merge(oldAseguradoOfEmailListEmail);
                }
            }
            for (Poliza polizaListPoliza : asegurado.getPolizaList()) {
                Asegurado oldTitularOfPolizaListPoliza = polizaListPoliza.getTitular();
                polizaListPoliza.setTitular(asegurado);
                polizaListPoliza = em.merge(polizaListPoliza);
                if (oldTitularOfPolizaListPoliza != null) {
                    oldTitularOfPolizaListPoliza.getPolizaList().remove(polizaListPoliza);
                    oldTitularOfPolizaListPoliza = em.merge(oldTitularOfPolizaListPoliza);
                }
            }
            for (Poliza polizaList1Poliza : asegurado.getPolizaList1()) {
                Asegurado oldContratanteOfPolizaList1Poliza = polizaList1Poliza.getContratante();
                polizaList1Poliza.setContratante(asegurado);
                polizaList1Poliza = em.merge(polizaList1Poliza);
                if (oldContratanteOfPolizaList1Poliza != null) {
                    oldContratanteOfPolizaList1Poliza.getPolizaList1().remove(polizaList1Poliza);
                    oldContratanteOfPolizaList1Poliza = em.merge(oldContratanteOfPolizaList1Poliza);
                }
            }
            for (DocumentoAsegurado_1 documentoAseguradoListDocumentoAsegurado_1 : asegurado.getDocumentoAseguradoList()) {
                Asegurado oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1 = documentoAseguradoListDocumentoAsegurado_1.getAsegurado();
                documentoAseguradoListDocumentoAsegurado_1.setAsegurado(asegurado);
                documentoAseguradoListDocumentoAsegurado_1 = em.merge(documentoAseguradoListDocumentoAsegurado_1);
                if (oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1 != null) {
                    oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1.getDocumentoAseguradoList().remove(documentoAseguradoListDocumentoAsegurado_1);
                    oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1 = em.merge(oldAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1);
                }
            }
            for (Telefono telefonoListTelefono : asegurado.getTelefonoList()) {
                Asegurado oldAseguradoOfTelefonoListTelefono = telefonoListTelefono.getAsegurado();
                telefonoListTelefono.setAsegurado(asegurado);
                telefonoListTelefono = em.merge(telefonoListTelefono);
                if (oldAseguradoOfTelefonoListTelefono != null) {
                    oldAseguradoOfTelefonoListTelefono.getTelefonoList().remove(telefonoListTelefono);
                    oldAseguradoOfTelefonoListTelefono = em.merge(oldAseguradoOfTelefonoListTelefono);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsegurado(asegurado.getIdcliente()) != null) {
                throw new PreexistingEntityException("Asegurado " + asegurado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asegurado asegurado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asegurado persistentAsegurado = em.find(Asegurado.class, asegurado.getIdcliente());
            Cliente clienteOld = persistentAsegurado.getCliente();
            Cliente clienteNew = asegurado.getCliente();
            Domicilio iddomicilioOld = persistentAsegurado.getIddomicilio();
            Domicilio iddomicilioNew = asegurado.getIddomicilio();
            TipoPersona tipopersonaOld = persistentAsegurado.getTipopersona();
            TipoPersona tipopersonaNew = asegurado.getTipopersona();
            List<Email> emailListOld = persistentAsegurado.getEmailList();
            List<Email> emailListNew = asegurado.getEmailList();
            List<Poliza> polizaListOld = persistentAsegurado.getPolizaList();
            List<Poliza> polizaListNew = asegurado.getPolizaList();
            List<Poliza> polizaList1Old = persistentAsegurado.getPolizaList1();
            List<Poliza> polizaList1New = asegurado.getPolizaList1();
            List<DocumentoAsegurado_1> documentoAseguradoListOld = persistentAsegurado.getDocumentoAseguradoList();
            List<DocumentoAsegurado_1> documentoAseguradoListNew = asegurado.getDocumentoAseguradoList();
            List<Telefono> telefonoListOld = persistentAsegurado.getTelefonoList();
            List<Telefono> telefonoListNew = asegurado.getTelefonoList();
            List<String> illegalOrphanMessages = null;
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                Asegurado oldAseguradoOfCliente = clienteNew.getAsegurado();
                if (oldAseguradoOfCliente != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cliente " + clienteNew + " already has an item of type Asegurado whose cliente column cannot be null. Please make another selection for the cliente field.");
                }
            }
            for (Email emailListOldEmail : emailListOld) {
                if (!emailListNew.contains(emailListOldEmail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Email " + emailListOldEmail + " since its asegurado field is not nullable.");
                }
            }
            for (Poliza polizaListOldPoliza : polizaListOld) {
                if (!polizaListNew.contains(polizaListOldPoliza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its titular field is not nullable.");
                }
            }
            for (Poliza polizaList1OldPoliza : polizaList1Old) {
                if (!polizaList1New.contains(polizaList1OldPoliza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Poliza " + polizaList1OldPoliza + " since its contratante field is not nullable.");
                }
            }
            for (DocumentoAsegurado_1 documentoAseguradoListOldDocumentoAsegurado_1 : documentoAseguradoListOld) {
                if (!documentoAseguradoListNew.contains(documentoAseguradoListOldDocumentoAsegurado_1)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DocumentoAsegurado_1 " + documentoAseguradoListOldDocumentoAsegurado_1 + " since its asegurado field is not nullable.");
                }
            }
            for (Telefono telefonoListOldTelefono : telefonoListOld) {
                if (!telefonoListNew.contains(telefonoListOldTelefono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telefono " + telefonoListOldTelefono + " since its asegurado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdcliente());
                asegurado.setCliente(clienteNew);
            }
            if (iddomicilioNew != null) {
                iddomicilioNew = em.getReference(iddomicilioNew.getClass(), iddomicilioNew.getIddomicilio());
                asegurado.setIddomicilio(iddomicilioNew);
            }
            if (tipopersonaNew != null) {
                tipopersonaNew = em.getReference(tipopersonaNew.getClass(), tipopersonaNew.getTipopersona());
                asegurado.setTipopersona(tipopersonaNew);
            }
            List<Email> attachedEmailListNew = new ArrayList<Email>();
            for (Email emailListNewEmailToAttach : emailListNew) {
                emailListNewEmailToAttach = em.getReference(emailListNewEmailToAttach.getClass(), emailListNewEmailToAttach.getEmailPK());
                attachedEmailListNew.add(emailListNewEmailToAttach);
            }
            emailListNew = attachedEmailListNew;
            asegurado.setEmailList(emailListNew);
            List<Poliza> attachedPolizaListNew = new ArrayList<Poliza>();
            for (Poliza polizaListNewPolizaToAttach : polizaListNew) {
                polizaListNewPolizaToAttach = em.getReference(polizaListNewPolizaToAttach.getClass(), polizaListNewPolizaToAttach.getIdpoliza());
                attachedPolizaListNew.add(polizaListNewPolizaToAttach);
            }
            polizaListNew = attachedPolizaListNew;
            asegurado.setPolizaList(polizaListNew);
            List<Poliza> attachedPolizaList1New = new ArrayList<Poliza>();
            for (Poliza polizaList1NewPolizaToAttach : polizaList1New) {
                polizaList1NewPolizaToAttach = em.getReference(polizaList1NewPolizaToAttach.getClass(), polizaList1NewPolizaToAttach.getIdpoliza());
                attachedPolizaList1New.add(polizaList1NewPolizaToAttach);
            }
            polizaList1New = attachedPolizaList1New;
            asegurado.setPolizaList1(polizaList1New);
            List<DocumentoAsegurado_1> attachedDocumentoAseguradoListNew = new ArrayList<DocumentoAsegurado_1>();
            for (DocumentoAsegurado_1 documentoAseguradoListNewDocumentoAsegurado_1ToAttach : documentoAseguradoListNew) {
                documentoAseguradoListNewDocumentoAsegurado_1ToAttach = em.getReference(documentoAseguradoListNewDocumentoAsegurado_1ToAttach.getClass(), documentoAseguradoListNewDocumentoAsegurado_1ToAttach.getDocumentoAseguradoPK());
                attachedDocumentoAseguradoListNew.add(documentoAseguradoListNewDocumentoAsegurado_1ToAttach);
            }
            documentoAseguradoListNew = attachedDocumentoAseguradoListNew;
            asegurado.setDocumentoAseguradoList(documentoAseguradoListNew);
            List<Telefono> attachedTelefonoListNew = new ArrayList<Telefono>();
            for (Telefono telefonoListNewTelefonoToAttach : telefonoListNew) {
                telefonoListNewTelefonoToAttach = em.getReference(telefonoListNewTelefonoToAttach.getClass(), telefonoListNewTelefonoToAttach.getTelefonoPK());
                attachedTelefonoListNew.add(telefonoListNewTelefonoToAttach);
            }
            telefonoListNew = attachedTelefonoListNew;
            asegurado.setTelefonoList(telefonoListNew);
            asegurado = em.merge(asegurado);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.setAsegurado(null);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.setAsegurado(asegurado);
                clienteNew = em.merge(clienteNew);
            }
            if (iddomicilioOld != null && !iddomicilioOld.equals(iddomicilioNew)) {
                iddomicilioOld.getAseguradoList().remove(asegurado);
                iddomicilioOld = em.merge(iddomicilioOld);
            }
            if (iddomicilioNew != null && !iddomicilioNew.equals(iddomicilioOld)) {
                iddomicilioNew.getAseguradoList().add(asegurado);
                iddomicilioNew = em.merge(iddomicilioNew);
            }
            if (tipopersonaOld != null && !tipopersonaOld.equals(tipopersonaNew)) {
                tipopersonaOld.getAseguradoList().remove(asegurado);
                tipopersonaOld = em.merge(tipopersonaOld);
            }
            if (tipopersonaNew != null && !tipopersonaNew.equals(tipopersonaOld)) {
                tipopersonaNew.getAseguradoList().add(asegurado);
                tipopersonaNew = em.merge(tipopersonaNew);
            }
            for (Email emailListNewEmail : emailListNew) {
                if (!emailListOld.contains(emailListNewEmail)) {
                    Asegurado oldAseguradoOfEmailListNewEmail = emailListNewEmail.getAsegurado();
                    emailListNewEmail.setAsegurado(asegurado);
                    emailListNewEmail = em.merge(emailListNewEmail);
                    if (oldAseguradoOfEmailListNewEmail != null && !oldAseguradoOfEmailListNewEmail.equals(asegurado)) {
                        oldAseguradoOfEmailListNewEmail.getEmailList().remove(emailListNewEmail);
                        oldAseguradoOfEmailListNewEmail = em.merge(oldAseguradoOfEmailListNewEmail);
                    }
                }
            }
            for (Poliza polizaListNewPoliza : polizaListNew) {
                if (!polizaListOld.contains(polizaListNewPoliza)) {
                    Asegurado oldTitularOfPolizaListNewPoliza = polizaListNewPoliza.getTitular();
                    polizaListNewPoliza.setTitular(asegurado);
                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
                    if (oldTitularOfPolizaListNewPoliza != null && !oldTitularOfPolizaListNewPoliza.equals(asegurado)) {
                        oldTitularOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
                        oldTitularOfPolizaListNewPoliza = em.merge(oldTitularOfPolizaListNewPoliza);
                    }
                }
            }
            for (Poliza polizaList1NewPoliza : polizaList1New) {
                if (!polizaList1Old.contains(polizaList1NewPoliza)) {
                    Asegurado oldContratanteOfPolizaList1NewPoliza = polizaList1NewPoliza.getContratante();
                    polizaList1NewPoliza.setContratante(asegurado);
                    polizaList1NewPoliza = em.merge(polizaList1NewPoliza);
                    if (oldContratanteOfPolizaList1NewPoliza != null && !oldContratanteOfPolizaList1NewPoliza.equals(asegurado)) {
                        oldContratanteOfPolizaList1NewPoliza.getPolizaList1().remove(polizaList1NewPoliza);
                        oldContratanteOfPolizaList1NewPoliza = em.merge(oldContratanteOfPolizaList1NewPoliza);
                    }
                }
            }
            for (DocumentoAsegurado_1 documentoAseguradoListNewDocumentoAsegurado_1 : documentoAseguradoListNew) {
                if (!documentoAseguradoListOld.contains(documentoAseguradoListNewDocumentoAsegurado_1)) {
                    Asegurado oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1 = documentoAseguradoListNewDocumentoAsegurado_1.getAsegurado();
                    documentoAseguradoListNewDocumentoAsegurado_1.setAsegurado(asegurado);
                    documentoAseguradoListNewDocumentoAsegurado_1 = em.merge(documentoAseguradoListNewDocumentoAsegurado_1);
                    if (oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1 != null && !oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1.equals(asegurado)) {
                        oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1.getDocumentoAseguradoList().remove(documentoAseguradoListNewDocumentoAsegurado_1);
                        oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1 = em.merge(oldAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1);
                    }
                }
            }
            for (Telefono telefonoListNewTelefono : telefonoListNew) {
                if (!telefonoListOld.contains(telefonoListNewTelefono)) {
                    Asegurado oldAseguradoOfTelefonoListNewTelefono = telefonoListNewTelefono.getAsegurado();
                    telefonoListNewTelefono.setAsegurado(asegurado);
                    telefonoListNewTelefono = em.merge(telefonoListNewTelefono);
                    if (oldAseguradoOfTelefonoListNewTelefono != null && !oldAseguradoOfTelefonoListNewTelefono.equals(asegurado)) {
                        oldAseguradoOfTelefonoListNewTelefono.getTelefonoList().remove(telefonoListNewTelefono);
                        oldAseguradoOfTelefonoListNewTelefono = em.merge(oldAseguradoOfTelefonoListNewTelefono);
                    }
                }
            }
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
    }

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
                illegalOrphanMessages.add("This Asegurado (" + asegurado + ") cannot be destroyed since the Poliza " + polizaListOrphanCheckPoliza + " in its polizaList field has a non-nullable titular field.");
            }
            List<Poliza> polizaList1OrphanCheck = asegurado.getPolizaList1();
            for (Poliza polizaList1OrphanCheckPoliza : polizaList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asegurado (" + asegurado + ") cannot be destroyed since the Poliza " + polizaList1OrphanCheckPoliza + " in its polizaList1 field has a non-nullable contratante field.");
            }
            List<DocumentoAsegurado_1> documentoAseguradoListOrphanCheck = asegurado.getDocumentoAseguradoList();
            for (DocumentoAsegurado_1 documentoAseguradoListOrphanCheckDocumentoAsegurado_1 : documentoAseguradoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asegurado (" + asegurado + ") cannot be destroyed since the DocumentoAsegurado_1 " + documentoAseguradoListOrphanCheckDocumentoAsegurado_1 + " in its documentoAseguradoList field has a non-nullable asegurado field.");
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
    
}
