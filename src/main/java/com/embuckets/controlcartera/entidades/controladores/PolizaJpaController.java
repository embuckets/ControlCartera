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
import com.embuckets.controlcartera.entidades.Caratula;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.Auto;
import com.embuckets.controlcartera.entidades.Beneficiario;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.ConductoCobro;
import com.embuckets.controlcartera.entidades.Dependiente;
import com.embuckets.controlcartera.entidades.EstadoPoliza;
import com.embuckets.controlcartera.entidades.FormaPago;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.Recibo;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class PolizaJpaController implements Serializable, JpaController {

    public PolizaJpaController() {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

//    public void create(Poliza poliza) {
//        if (poliza.getReciboList() == null) {
//            poliza.setReciboList(new ArrayList<Recibo>());
//        }
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Caratula caratula = poliza.getCaratula();
//            if (caratula != null) {
//                caratula = em.getReference(caratula.getClass(), caratula.getIdpoliza());
//                poliza.setCaratula(caratula);
//            }
//            PolizaAuto polizaAuto = poliza.getPolizaAuto();
//            if (polizaAuto != null) {
//                polizaAuto = em.getReference(polizaAuto.getClass(), polizaAuto.getIdpoliza());
//                poliza.setPolizaAuto(polizaAuto);
//            }
//            Asegurado contratante = poliza.getContratante();
//            if (contratante != null) {
//                contratante = em.getReference(contratante.getClass(), contratante.getIdcliente());
//                poliza.setContratante(contratante);
//            }
//            Aseguradora aseguradora = poliza.getAseguradora();
//            if (aseguradora != null) {
//                aseguradora = em.getReference(aseguradora.getClass(), aseguradora.getAseguradora());
//                poliza.setAseguradora(aseguradora);
//            }
//            Cliente titular = poliza.getTitular();
//            if (titular != null) {
//                titular = em.getReference(titular.getClass(), titular.getIdcliente());
//                poliza.setTitular(titular);
//            }
//            ConductoCobro conductocobro = poliza.getConductocobro();
//            if (conductocobro != null) {
//                conductocobro = em.getReference(conductocobro.getClass(), conductocobro.getConductocobro());
//                poliza.setConductocobro(conductocobro);
//            }
//            EstadoPoliza estado = poliza.getEstado();
//            if (estado != null) {
//                estado = em.getReference(estado.getClass(), estado.getEstado());
//                poliza.setEstado(estado);
//            }
//            FormaPago formapago = poliza.getFormapago();
//            if (formapago != null) {
//                formapago = em.getReference(formapago.getClass(), formapago.getFormapago());
//                poliza.setFormapago(formapago);
//            }
//            Moneda primamoneda = poliza.getPrimamoneda();
//            if (primamoneda != null) {
//                primamoneda = em.getReference(primamoneda.getClass(), primamoneda.getMoneda());
//                poliza.setPrimamoneda(primamoneda);
//            }
//            Ramo ramo = poliza.getRamo();
//            if (ramo != null) {
//                ramo = em.getReference(ramo.getClass(), ramo.getRamo());
//                poliza.setRamo(ramo);
//            }
//            PolizaVida polizaVida = poliza.getPolizaVida();
//            if (polizaVida != null) {
//                polizaVida = em.getReference(polizaVida.getClass(), polizaVida.getIdpoliza());
//                poliza.setPolizaVida(polizaVida);
//            }
//            PolizaGmm polizaGmm = poliza.getPolizaGmm();
//            if (polizaGmm != null) {
//                polizaGmm = em.getReference(polizaGmm.getClass(), polizaGmm.getIdpoliza());
//                poliza.setPolizaGmm(polizaGmm);
//            }
//            List<Recibo> attachedReciboList = new ArrayList<Recibo>();
//            for (Recibo reciboListReciboToAttach : poliza.getReciboList()) {
//                reciboListReciboToAttach = em.getReference(reciboListReciboToAttach.getClass(), reciboListReciboToAttach.getIdrecibo());
//                attachedReciboList.add(reciboListReciboToAttach);
//            }
//            poliza.setReciboList(attachedReciboList);
//            em.persist(poliza);
//            if (caratula != null) {
//                Poliza oldPolizaOfCaratula = caratula.getPoliza();
//                if (oldPolizaOfCaratula != null) {
//                    oldPolizaOfCaratula.setCaratula(null);
//                    oldPolizaOfCaratula = em.merge(oldPolizaOfCaratula);
//                }
//                caratula.setPoliza(poliza);
//                caratula = em.merge(caratula);
//            }
//            if (polizaAuto != null) {
//                Poliza oldPolizaOfPolizaAuto = polizaAuto.getPoliza();
//                if (oldPolizaOfPolizaAuto != null) {
//                    oldPolizaOfPolizaAuto.setPolizaAuto(null);
//                    oldPolizaOfPolizaAuto = em.merge(oldPolizaOfPolizaAuto);
//                }
//                polizaAuto.setPoliza(poliza);
//                polizaAuto = em.merge(polizaAuto);
//            }
//            if (contratante != null) {
//                contratante.getPolizaList().add(poliza);
//                contratante = em.merge(contratante);
//            }
//            if (aseguradora != null) {
//                aseguradora.getPolizaList().add(poliza);
//                aseguradora = em.merge(aseguradora);
//            }
//            if (titular != null) {
//                titular.getPolizaList().add(poliza);
//                titular = em.merge(titular);
//            }
//            if (conductocobro != null) {
//                conductocobro.getPolizaList().add(poliza);
//                conductocobro = em.merge(conductocobro);
//            }
//            if (estado != null) {
//                estado.getPolizaList().add(poliza);
//                estado = em.merge(estado);
//            }
//            if (formapago != null) {
//                formapago.getPolizaList().add(poliza);
//                formapago = em.merge(formapago);
//            }
//            if (primamoneda != null) {
//                primamoneda.getPolizaList().add(poliza);
//                primamoneda = em.merge(primamoneda);
//            }
//            if (ramo != null) {
//                ramo.getPolizaList().add(poliza);
//                ramo = em.merge(ramo);
//            }
//            if (polizaVida != null) {
//                Poliza oldPolizaOfPolizaVida = polizaVida.getPoliza();
//                if (oldPolizaOfPolizaVida != null) {
//                    oldPolizaOfPolizaVida.setPolizaVida(null);
//                    oldPolizaOfPolizaVida = em.merge(oldPolizaOfPolizaVida);
//                }
//                polizaVida.setPoliza(poliza);
//                polizaVida = em.merge(polizaVida);
//            }
//            if (polizaGmm != null) {
//                Poliza oldPolizaOfPolizaGmm = polizaGmm.getPoliza();
//                if (oldPolizaOfPolizaGmm != null) {
//                    oldPolizaOfPolizaGmm.setPolizaGmm(null);
//                    oldPolizaOfPolizaGmm = em.merge(oldPolizaOfPolizaGmm);
//                }
//                polizaGmm.setPoliza(poliza);
//                polizaGmm = em.merge(polizaGmm);
//            }
//            for (Recibo reciboListRecibo : poliza.getReciboList()) {
//                Poliza oldIdpolizaOfReciboListRecibo = reciboListRecibo.getIdpoliza();
//                reciboListRecibo.setIdpoliza(poliza);
//                reciboListRecibo = em.merge(reciboListRecibo);
//                if (oldIdpolizaOfReciboListRecibo != null) {
//                    oldIdpolizaOfReciboListRecibo.getReciboList().remove(reciboListRecibo);
//                    oldIdpolizaOfReciboListRecibo = em.merge(oldIdpolizaOfReciboListRecibo);
//                }
//            }
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//    }
    public void edit(Poliza poliza) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poliza persistentPoliza = em.find(Poliza.class, poliza.getIdpoliza());
            Caratula caratulaOld = persistentPoliza.getCaratula();
            Caratula caratulaNew = poliza.getCaratula();
            PolizaAuto polizaAutoOld = persistentPoliza.getPolizaAuto();
            PolizaAuto polizaAutoNew = poliza.getPolizaAuto();
            Asegurado contratanteOld = persistentPoliza.getContratante();
            Asegurado contratanteNew = poliza.getContratante();
            Aseguradora aseguradoraOld = persistentPoliza.getAseguradora();
            Aseguradora aseguradoraNew = poliza.getAseguradora();
            Cliente titularOld = persistentPoliza.getTitular();
            Cliente titularNew = poliza.getTitular();
            ConductoCobro conductocobroOld = persistentPoliza.getConductocobro();
            ConductoCobro conductocobroNew = poliza.getConductocobro();
            EstadoPoliza estadoOld = persistentPoliza.getEstado();
            EstadoPoliza estadoNew = poliza.getEstado();
            FormaPago formapagoOld = persistentPoliza.getFormapago();
            FormaPago formapagoNew = poliza.getFormapago();
            Moneda primamonedaOld = persistentPoliza.getPrimamoneda();
            Moneda primamonedaNew = poliza.getPrimamoneda();
            Ramo ramoOld = persistentPoliza.getRamo();
            Ramo ramoNew = poliza.getRamo();
            PolizaVida polizaVidaOld = persistentPoliza.getPolizaVida();
            PolizaVida polizaVidaNew = poliza.getPolizaVida();
            PolizaGmm polizaGmmOld = persistentPoliza.getPolizaGmm();
            PolizaGmm polizaGmmNew = poliza.getPolizaGmm();
            List<Recibo> reciboListOld = persistentPoliza.getReciboList();
            List<Recibo> reciboListNew = poliza.getReciboList();
            List<String> illegalOrphanMessages = null;
            if (caratulaOld != null && !caratulaOld.equals(caratulaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Caratula " + caratulaOld + " since its poliza field is not nullable.");
            }
            if (polizaAutoOld != null && !polizaAutoOld.equals(polizaAutoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PolizaAuto " + polizaAutoOld + " since its poliza field is not nullable.");
            }
            if (polizaVidaOld != null && !polizaVidaOld.equals(polizaVidaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PolizaVida " + polizaVidaOld + " since its poliza field is not nullable.");
            }
            if (polizaGmmOld != null && !polizaGmmOld.equals(polizaGmmNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PolizaGmm " + polizaGmmOld + " since its poliza field is not nullable.");
            }
            for (Recibo reciboListOldRecibo : reciboListOld) {
                if (!reciboListNew.contains(reciboListOldRecibo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Recibo " + reciboListOldRecibo + " since its idpoliza field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (caratulaNew != null) {
                caratulaNew = em.getReference(caratulaNew.getClass(), caratulaNew.getIdpoliza());
                poliza.setCaratula(caratulaNew);
            }
            if (polizaAutoNew != null) {
                polizaAutoNew = em.getReference(polizaAutoNew.getClass(), polizaAutoNew.getIdpoliza());
                poliza.setPolizaAuto(polizaAutoNew);
            }
            if (contratanteNew != null) {
                contratanteNew = em.getReference(contratanteNew.getClass(), contratanteNew.getIdcliente());
                poliza.setContratante(contratanteNew);
            }
            if (aseguradoraNew != null) {
                aseguradoraNew = em.getReference(aseguradoraNew.getClass(), aseguradoraNew.getAseguradora());
                poliza.setAseguradora(aseguradoraNew);
            }
            if (titularNew != null) {
                titularNew = em.getReference(titularNew.getClass(), titularNew.getIdcliente());
                poliza.setTitular(titularNew);
            }
            if (conductocobroNew != null) {
                conductocobroNew = em.getReference(conductocobroNew.getClass(), conductocobroNew.getConductocobro());
                poliza.setConductocobro(conductocobroNew);
            }
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getEstado());
                poliza.setEstado(estadoNew);
            }
            if (formapagoNew != null) {
                formapagoNew = em.getReference(formapagoNew.getClass(), formapagoNew.getFormapago());
                poliza.setFormapago(formapagoNew);
            }
            if (primamonedaNew != null) {
                primamonedaNew = em.getReference(primamonedaNew.getClass(), primamonedaNew.getMoneda());
                poliza.setPrimamoneda(primamonedaNew);
            }
            if (ramoNew != null) {
                ramoNew = em.getReference(ramoNew.getClass(), ramoNew.getRamo());
                poliza.setRamo(ramoNew);
            }
            if (polizaVidaNew != null) {
                polizaVidaNew = em.getReference(polizaVidaNew.getClass(), polizaVidaNew.getIdpoliza());
                poliza.setPolizaVida(polizaVidaNew);
            }
            if (polizaGmmNew != null) {
                polizaGmmNew = em.getReference(polizaGmmNew.getClass(), polizaGmmNew.getIdpoliza());
                poliza.setPolizaGmm(polizaGmmNew);
            }
            List<Recibo> attachedReciboListNew = new ArrayList<Recibo>();
            for (Recibo reciboListNewReciboToAttach : reciboListNew) {
                reciboListNewReciboToAttach = em.getReference(reciboListNewReciboToAttach.getClass(), reciboListNewReciboToAttach.getIdrecibo());
                attachedReciboListNew.add(reciboListNewReciboToAttach);
            }
            reciboListNew = attachedReciboListNew;
            poliza.setReciboList(reciboListNew);
            poliza = em.merge(poliza);
            if (caratulaNew != null && !caratulaNew.equals(caratulaOld)) {
                Poliza oldPolizaOfCaratula = caratulaNew.getPoliza();
                if (oldPolizaOfCaratula != null) {
                    oldPolizaOfCaratula.setCaratula(null);
                    oldPolizaOfCaratula = em.merge(oldPolizaOfCaratula);
                }
                caratulaNew.setPoliza(poliza);
                caratulaNew = em.merge(caratulaNew);
            }
            if (polizaAutoNew != null && !polizaAutoNew.equals(polizaAutoOld)) {
                Poliza oldPolizaOfPolizaAuto = polizaAutoNew.getPoliza();
                if (oldPolizaOfPolizaAuto != null) {
                    oldPolizaOfPolizaAuto.setPolizaAuto(null);
                    oldPolizaOfPolizaAuto = em.merge(oldPolizaOfPolizaAuto);
                }
                polizaAutoNew.setPoliza(poliza);
                polizaAutoNew = em.merge(polizaAutoNew);
            }
            if (contratanteOld != null && !contratanteOld.equals(contratanteNew)) {
                contratanteOld.getPolizaList().remove(poliza);
                contratanteOld = em.merge(contratanteOld);
            }
            if (contratanteNew != null && !contratanteNew.equals(contratanteOld)) {
                contratanteNew.getPolizaList().add(poliza);
                contratanteNew = em.merge(contratanteNew);
            }
            if (aseguradoraOld != null && !aseguradoraOld.equals(aseguradoraNew)) {
                aseguradoraOld.getPolizaList().remove(poliza);
                aseguradoraOld = em.merge(aseguradoraOld);
            }
            if (aseguradoraNew != null && !aseguradoraNew.equals(aseguradoraOld)) {
                aseguradoraNew.getPolizaList().add(poliza);
                aseguradoraNew = em.merge(aseguradoraNew);
            }
            if (titularOld != null && !titularOld.equals(titularNew)) {
                titularOld.getPolizaList().remove(poliza);
                titularOld = em.merge(titularOld);
            }
            if (titularNew != null && !titularNew.equals(titularOld)) {
                titularNew.getPolizaList().add(poliza);
                titularNew = em.merge(titularNew);
            }
            if (conductocobroOld != null && !conductocobroOld.equals(conductocobroNew)) {
                conductocobroOld.getPolizaList().remove(poliza);
                conductocobroOld = em.merge(conductocobroOld);
            }
            if (conductocobroNew != null && !conductocobroNew.equals(conductocobroOld)) {
                conductocobroNew.getPolizaList().add(poliza);
                conductocobroNew = em.merge(conductocobroNew);
            }
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getPolizaList().remove(poliza);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getPolizaList().add(poliza);
                estadoNew = em.merge(estadoNew);
            }
            if (formapagoOld != null && !formapagoOld.equals(formapagoNew)) {
                formapagoOld.getPolizaList().remove(poliza);
                formapagoOld = em.merge(formapagoOld);
            }
            if (formapagoNew != null && !formapagoNew.equals(formapagoOld)) {
                formapagoNew.getPolizaList().add(poliza);
                formapagoNew = em.merge(formapagoNew);
            }
            if (primamonedaOld != null && !primamonedaOld.equals(primamonedaNew)) {
                primamonedaOld.getPolizaList().remove(poliza);
                primamonedaOld = em.merge(primamonedaOld);
            }
            if (primamonedaNew != null && !primamonedaNew.equals(primamonedaOld)) {
                primamonedaNew.getPolizaList().add(poliza);
                primamonedaNew = em.merge(primamonedaNew);
            }
            if (ramoOld != null && !ramoOld.equals(ramoNew)) {
                ramoOld.getPolizaList().remove(poliza);
                ramoOld = em.merge(ramoOld);
            }
            if (ramoNew != null && !ramoNew.equals(ramoOld)) {
                ramoNew.getPolizaList().add(poliza);
                ramoNew = em.merge(ramoNew);
            }
            if (polizaVidaNew != null && !polizaVidaNew.equals(polizaVidaOld)) {
                Poliza oldPolizaOfPolizaVida = polizaVidaNew.getPoliza();
                if (oldPolizaOfPolizaVida != null) {
                    oldPolizaOfPolizaVida.setPolizaVida(null);
                    oldPolizaOfPolizaVida = em.merge(oldPolizaOfPolizaVida);
                }
                polizaVidaNew.setPoliza(poliza);
                polizaVidaNew = em.merge(polizaVidaNew);
            }
            if (polizaGmmNew != null && !polizaGmmNew.equals(polizaGmmOld)) {
                Poliza oldPolizaOfPolizaGmm = polizaGmmNew.getPoliza();
                if (oldPolizaOfPolizaGmm != null) {
                    oldPolizaOfPolizaGmm.setPolizaGmm(null);
                    oldPolizaOfPolizaGmm = em.merge(oldPolizaOfPolizaGmm);
                }
                polizaGmmNew.setPoliza(poliza);
                polizaGmmNew = em.merge(polizaGmmNew);
            }
            for (Recibo reciboListNewRecibo : reciboListNew) {
                if (!reciboListOld.contains(reciboListNewRecibo)) {
                    Poliza oldIdpolizaOfReciboListNewRecibo = reciboListNewRecibo.getIdpoliza();
                    reciboListNewRecibo.setIdpoliza(poliza);
                    reciboListNewRecibo = em.merge(reciboListNewRecibo);
                    if (oldIdpolizaOfReciboListNewRecibo != null && !oldIdpolizaOfReciboListNewRecibo.equals(poliza)) {
                        oldIdpolizaOfReciboListNewRecibo.getReciboList().remove(reciboListNewRecibo);
                        oldIdpolizaOfReciboListNewRecibo = em.merge(oldIdpolizaOfReciboListNewRecibo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = poliza.getIdpoliza();
                if (findPoliza(id) == null) {
                    throw new NonexistentEntityException("The poliza with id " + id + " no longer exists.");
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
            Poliza poliza;
            try {
                poliza = em.getReference(Poliza.class, id);
                poliza.getIdpoliza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The poliza with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Caratula caratulaOrphanCheck = poliza.getCaratula();
            if (caratulaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the Caratula " + caratulaOrphanCheck + " in its caratula field has a non-nullable poliza field.");
            }
            PolizaAuto polizaAutoOrphanCheck = poliza.getPolizaAuto();
            if (polizaAutoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the PolizaAuto " + polizaAutoOrphanCheck + " in its polizaAuto field has a non-nullable poliza field.");
            }
            PolizaVida polizaVidaOrphanCheck = poliza.getPolizaVida();
            if (polizaVidaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the PolizaVida " + polizaVidaOrphanCheck + " in its polizaVida field has a non-nullable poliza field.");
            }
            PolizaGmm polizaGmmOrphanCheck = poliza.getPolizaGmm();
            if (polizaGmmOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the PolizaGmm " + polizaGmmOrphanCheck + " in its polizaGmm field has a non-nullable poliza field.");
            }
            List<Recibo> reciboListOrphanCheck = poliza.getReciboList();
            for (Recibo reciboListOrphanCheckRecibo : reciboListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the Recibo " + reciboListOrphanCheckRecibo + " in its reciboList field has a non-nullable idpoliza field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Asegurado contratante = poliza.getContratante();
            if (contratante != null) {
                contratante.getPolizaList().remove(poliza);
                contratante = em.merge(contratante);
            }
            Aseguradora aseguradora = poliza.getAseguradora();
            if (aseguradora != null) {
                aseguradora.getPolizaList().remove(poliza);
                aseguradora = em.merge(aseguradora);
            }
            Cliente titular = poliza.getTitular();
            if (titular != null) {
                titular.getPolizaList().remove(poliza);
                titular = em.merge(titular);
            }
            ConductoCobro conductocobro = poliza.getConductocobro();
            if (conductocobro != null) {
                conductocobro.getPolizaList().remove(poliza);
                conductocobro = em.merge(conductocobro);
            }
            EstadoPoliza estado = poliza.getEstado();
            if (estado != null) {
                estado.getPolizaList().remove(poliza);
                estado = em.merge(estado);
            }
            FormaPago formapago = poliza.getFormapago();
            if (formapago != null) {
                formapago.getPolizaList().remove(poliza);
                formapago = em.merge(formapago);
            }
            Moneda primamoneda = poliza.getPrimamoneda();
            if (primamoneda != null) {
                primamoneda.getPolizaList().remove(poliza);
                primamoneda = em.merge(primamoneda);
            }
            Ramo ramo = poliza.getRamo();
            if (ramo != null) {
                ramo.getPolizaList().remove(poliza);
                ramo = em.merge(ramo);
            }
            em.remove(poliza);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void remove(Object object) throws IllegalOrphanException, NonexistentEntityException, Exception {
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
            Poliza poliza = (Poliza) object;
            int id = poliza.getIdpoliza();
            try {
                poliza = em.getReference(Poliza.class, poliza.getIdpoliza());
                poliza.getIdpoliza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The poliza with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            Caratula caratulaOrphanCheck = poliza.getCaratula();
//            if (caratulaOrphanCheck != null) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the Caratula " + caratulaOrphanCheck + " in its caratula field has a non-nullable poliza field.");
//            }
//            PolizaAuto polizaAutoOrphanCheck = poliza.getPolizaAuto();
//            if (polizaAutoOrphanCheck != null) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the PolizaAuto " + polizaAutoOrphanCheck + " in its polizaAuto field has a non-nullable poliza field.");
//            }
//            PolizaVida polizaVidaOrphanCheck = poliza.getPolizaVida();
//            if (polizaVidaOrphanCheck != null) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the PolizaVida " + polizaVidaOrphanCheck + " in its polizaVida field has a non-nullable poliza field.");
//            }
//            PolizaGmm polizaGmmOrphanCheck = poliza.getPolizaGmm();
//            if (polizaGmmOrphanCheck != null) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the PolizaGmm " + polizaGmmOrphanCheck + " in its polizaGmm field has a non-nullable poliza field.");
//            }
//            List<Recibo> reciboListOrphanCheck = poliza.getReciboList();
//            for (Recibo reciboListOrphanCheckRecibo : reciboListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Poliza (" + poliza + ") cannot be destroyed since the Recibo " + reciboListOrphanCheckRecibo + " in its reciboList field has a non-nullable idpoliza field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            Caratula caratulaOrphanCheck = poliza.getCaratula();
            if (caratulaOrphanCheck != null) {
                caratulaOrphanCheck.setPoliza(null);
                caratulaOrphanCheck = em.merge(caratulaOrphanCheck);
            }
            PolizaAuto polizaAutoOrphanCheck = poliza.getPolizaAuto();
            if (polizaAutoOrphanCheck != null) {
                polizaAutoOrphanCheck.setPoliza(null);
                polizaAutoOrphanCheck = em.merge(polizaAutoOrphanCheck);
            }
            PolizaVida polizaVidaOrphanCheck = poliza.getPolizaVida();
            if (polizaVidaOrphanCheck != null) {
                BeneficiarioJpaController beneficiarioJpaController = new BeneficiarioJpaController();
                for (Cliente cliente : polizaVidaOrphanCheck.getClienteList()){
                    beneficiarioJpaController.remove(new Beneficiario(cliente, polizaVidaOrphanCheck));
                }
                polizaVidaOrphanCheck.setPoliza(null);
                polizaVidaOrphanCheck = em.merge(polizaVidaOrphanCheck);
            }
            PolizaGmm polizaGmmOrphanCheck = poliza.getPolizaGmm();
            if (polizaGmmOrphanCheck != null) {
                DependienteJpaController dependienteJpaController = new DependienteJpaController();
                for (Cliente cliente : polizaGmmOrphanCheck.getClienteList()){
                    dependienteJpaController.remove(new Dependiente(cliente, polizaGmmOrphanCheck));
                }
                polizaGmmOrphanCheck.setPoliza(null);
                polizaGmmOrphanCheck = em.merge(polizaGmmOrphanCheck);
            }
            List<Recibo> reciboListOrphanCheck = poliza.getReciboList();
            for (Recibo reciboListOrphanCheckRecibo : reciboListOrphanCheck) {
                reciboListOrphanCheckRecibo.setIdpoliza(null);
                reciboListOrphanCheckRecibo = em.merge(reciboListOrphanCheckRecibo);
            }
            Asegurado contratante = poliza.getContratante();
            if (contratante != null) {
                contratante.getPolizaList().remove(poliza);
                contratante = em.merge(contratante);
            }
            Aseguradora aseguradora = poliza.getAseguradora();
            if (aseguradora != null) {
                aseguradora.getPolizaList().remove(poliza);
                aseguradora = em.merge(aseguradora);
            }
            Cliente titular = poliza.getTitular();
            if (titular != null) {
                titular.getPolizaList().remove(poliza);
                titular = em.merge(titular);
            }
            ConductoCobro conductocobro = poliza.getConductocobro();
            if (conductocobro != null) {
                conductocobro.getPolizaList().remove(poliza);
                conductocobro = em.merge(conductocobro);
            }
            EstadoPoliza estado = poliza.getEstado();
            if (estado != null) {
                estado.getPolizaList().remove(poliza);
                estado = em.merge(estado);
            }
            FormaPago formapago = poliza.getFormapago();
            if (formapago != null) {
                formapago.getPolizaList().remove(poliza);
                formapago = em.merge(formapago);
            }
            Moneda primamoneda = poliza.getPrimamoneda();
            if (primamoneda != null) {
                primamoneda.getPolizaList().remove(poliza);
                primamoneda = em.merge(primamoneda);
            }
            Ramo ramo = poliza.getRamo();
            if (ramo != null) {
                ramo.getPolizaList().remove(poliza);
                ramo = em.merge(ramo);
            }
            em.remove(poliza);
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

    public List<Poliza> findPolizaEntities() {
        return findPolizaEntities(true, -1, -1);
    }

    public List<Poliza> findPolizaEntities(int maxResults, int firstResult) {
        return findPolizaEntities(false, maxResults, firstResult);
    }

    private List<Poliza> findPolizaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Poliza.class));
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

    public Poliza findPoliza(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Poliza.class, id);
        } finally {
            em.close();
        }
    }

    public int getPolizaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Poliza> rt = cq.from(Poliza.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public void create(Object object) throws PreexistingEntityException, Exception {
        Poliza poliza = (Poliza) object;
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

            em.persist(poliza);
            
            if (poliza.getCaratula() != null){
                Caratula caratula = poliza.getCaratula();
                caratula.setPoliza(poliza);
                caratula.setIdpoliza(poliza.getId());
                em.persist(caratula);
            }

            for (Recibo recibo : poliza.getReciboList()) {
                NotificacionRecibo notificacion = new NotificacionRecibo(recibo, Globals.NOTIFICACION_ESTADO_PENDIENTE);
                notificacion.setIdrecibo(recibo.getIdrecibo());
                em.persist(notificacion);
                recibo.setNotificacionRecibo(notificacion);
            }

            if (poliza.getPolizaAuto() != null) {
                poliza.getPolizaAuto().setPoliza(poliza);
                poliza.getPolizaAuto().setIdpoliza(poliza.getIdpoliza());
                em.persist(poliza.getPolizaAuto());
                for (Auto auto : poliza.getPolizaAuto().getAutoList()) {
                    em.persist(auto);
                }
            }
            if (poliza.getPolizaGmm() != null) {
                poliza.getPolizaGmm().setPoliza(poliza);
                poliza.getPolizaGmm().setIdpoliza(poliza.getIdpoliza());
                em.persist(poliza.getPolizaGmm());
                for (Cliente cliente : poliza.getPolizaGmm().getClienteList()) {
                    em.persist(cliente);
                    Query query = em.createNativeQuery("INSERT INTO APP.DEPENDIENTE (IDCLIENTE, IDPOLIZA) VALUES (:idcliente, :idpoliza)");
                    query.setParameter("idcliente", cliente.getIdcliente());
                    query.setParameter("idpoliza", poliza.getIdpoliza());
                    query.executeUpdate();
                }
            }
            if (poliza.getPolizaVida() != null) {
                poliza.getPolizaVida().setPoliza(poliza);
                poliza.getPolizaVida().setIdpoliza(poliza.getIdpoliza());
                em.persist(poliza.getPolizaVida());
                for (Cliente cliente : poliza.getPolizaVida().getClienteList()) {
                    em.persist(cliente);
                    Query query = em.createNativeQuery("INSERT INTO APP.BENEFICIARIO (IDCLIENTE, IDPOLIZA) VALUES (:idcliente, :idpoliza)");
                    query.setParameter("idcliente", cliente.getIdcliente());
                    query.setParameter("idpoliza", poliza.getIdpoliza());
                    query.executeUpdate();
                }
            }
            Asegurado contratante = poliza.getContratante();
            contratante.getPolizaList().add(poliza);
            contratante = em.merge(contratante);

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

//    @Override
//    public void remove(Object object) throws Exception {
//        Poliza poliza = (Poliza) object;
//        boolean isSubTransaction = false;
//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            if (em.getTransaction().isActive()) {
//                isSubTransaction = true;
//            }
//            if (!isSubTransaction) {
//                em.getTransaction().begin();
//            }
//
//            if (poliza.getPolizaVida() != null) {
//                PolizaVidaJpaController polizaVidaJpaController = new PolizaVidaJpaController();
//                polizaVidaJpaController.remove(poliza.getPolizaVida());
//            }
//            em.remove(poliza);
//            if (!isSubTransaction) {
//                em.getTransaction().commit();
//            }
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
//    }
    public void renovar(Poliza vieja, Poliza nueva) throws Exception {
        Poliza polizaVieja = (Poliza) vieja;
        Poliza polizaNueva = (Poliza) nueva;
        EstadoPoliza oldEstado = vieja.getEstado();
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

            polizaVieja.setEstado(new EstadoPoliza(Globals.POLIZA_ESTADO_RENOVADA));
            em.merge(polizaVieja);
            create(polizaNueva);

            if (!isSubTransaction) {
                em.getTransaction().commit();
            }
            polizaNueva.getContratante().getPolizaList().add(polizaNueva);
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                polizaVieja.setEstado(oldEstado);
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    public List<Poliza> getRenovacionesProximas() {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            Query query = em.createQuery("SELECT p FROM Poliza p WHERE p.finvigencia BETWEEN :today AND :next");
            query.setParameter("today", LocalDate.now());
            query.setParameter("next", LocalDate.now().plusMonths(1));
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

//    @Override
//    public void remove(Object object) throws Exception {
//        Poliza poliza = (Poliza) object;
//        boolean isSubTransaction = false;
//        EntityManager em = null;
//        try {
//            em = BaseDeDatos.getInstance().getEntityManager();
//            if (em.getTransaction().isActive()) {
//                isSubTransaction = true;
//            }
//            if (!isSubTransaction) {
//                em.getTransaction().begin();
//            }
//
//            if (poliza.getPolizaVida() != null) {
//                BeneficiarioJpaController beneficiarioJpaController = new BeneficiarioJpaController();
//                for (Cliente cliente : poliza.getPolizaVida().getClienteList()) {
//                    Beneficiario benef = new Beneficiario(cliente, poliza.getPolizaVida());
//                    beneficiarioJpaController.remove(benef);
//                }
//                em.remove(poliza.getPolizaVida());
//            }
//            if (poliza.getPolizaGmm() != null) {
//                DependienteJpaController dependienteJpaController = new DependienteJpaController();
//                for (Cliente cliente : poliza.getPolizaVida().getClienteList()) {
//                    dependienteJpaController.remove(cliente);
//                }
//                em.remove(poliza.getPolizaGmm());
//            }
//            if (poliza.getPolizaAuto() != null) {
//                for (Auto auto : poliza.getPolizaAuto().getAutoList()) {
//                    em.remove(auto);
//                }
//                em.remove(poliza.getPolizaAuto());
//            }
//            for (Recibo recibo : poliza.getReciboList()) {
//                em.remove(recibo);
//            }
//            if (poliza.getCaratula() != null) {
//                CaratulaJpaController caratulaJpaController = new CaratulaJpaController();
//                caratulaJpaController.remove(poliza.getCaratula());
//            }
//            em.remove(poliza);
//
//            if (!isSubTransaction) {
//                em.getTransaction().commit();
//            }
//        } catch (Exception ex) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw ex;
//        }
//    }
    @Override
    public String getControlledClassName() {
        return Poliza.class.getSimpleName();
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
