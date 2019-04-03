/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Aseguradora;
import com.embuckets.controlcartera.entidades.Beneficiario;
import com.embuckets.controlcartera.entidades.Caratula;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.ConductoCobro;
import com.embuckets.controlcartera.entidades.Dependiente;
import com.embuckets.controlcartera.entidades.Domicilio;
import com.embuckets.controlcartera.entidades.TipoPersona;
import com.embuckets.controlcartera.entidades.Email;
import java.util.List;
import com.embuckets.controlcartera.entidades.Poliza;
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.EstadoPoliza;
import com.embuckets.controlcartera.entidades.FormaPago;
import com.embuckets.controlcartera.entidades.Moneda;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import com.embuckets.controlcartera.entidades.PolizaAuto;
import com.embuckets.controlcartera.entidades.PolizaGmm;
import com.embuckets.controlcartera.entidades.PolizaVida;
import com.embuckets.controlcartera.entidades.Ramo;
import com.embuckets.controlcartera.entidades.Recibo;
import com.embuckets.controlcartera.entidades.Telefono;
import com.embuckets.controlcartera.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import com.embuckets.controlcartera.entidades.globals.Logging;
import com.embuckets.controlcartera.exceptions.BaseDeDatosException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emilio
 */
public class AseguradoJpaController implements Serializable, JpaController {

    private static final Logger logger = LogManager.getLogger(AseguradoJpaController.class);

    public AseguradoJpaController() {
    }

    @Override
    public void create(Object object) throws BaseDeDatosException {
        Asegurado asegurado = (Asegurado) object;
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
            Cliente cliente = asegurado.getCliente();
            //save cliente
            em.persist(cliente);
            NotificacionCumple notificacionCumple = new NotificacionCumple(cliente, Globals.NOTIFICACION_ESTADO_PENDIENTE);
            notificacionCumple.setIdcliente(cliente.getIdcliente());
            em.persist(notificacionCumple);
            cliente.setNotificacionCumple(notificacionCumple);
            asegurado.setIdcliente(cliente.getIdcliente());
            if (asegurado.getIddomicilio() != null) {
                Domicilio domicilio = asegurado.getIddomicilio();
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

            if (!isSubTransaction) {
                em.getTransaction().commit();
            }

        } //end try
        catch (IllegalStateException ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.IllegalStateException_MESSAGE, ex);
            throw new BaseDeDatosException(Logging.IllegalStateException_MESSAGE, ex);
        } catch (EntityExistsException ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new BaseDeDatosException(Logging.EntityExistsException_MESSAGE, ex);
        } catch (IllegalArgumentException ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new BaseDeDatosException(Logging.EntityExistsException_MESSAGE, ex);
        } catch (PersistenceException ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new BaseDeDatosException(Logging.EntityExistsException_MESSAGE, ex);
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.Exception_MESSAGE, ex);
            throw new BaseDeDatosException(Logging.Exception_MESSAGE, ex);
        }
    }

    public void importarAsegurados(List<Asegurado> asegurados) throws Exception {
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
            PolizaJpaController polizaJpaController = new PolizaJpaController();
            for (Asegurado a : asegurados) {
                List<Poliza> importedPolizas = a.getPolizaList();
                List<Asegurado> existentes = getByName(a.getCliente().getNombre(), a.getCliente().getApellidopaterno(), a.getCliente().getApellidomaterno());
                if (existentes.isEmpty()) {
                    create(a);
                } else {
                    importedPolizas.stream().forEach(p -> p.setContratante(existentes.get(0)));
                }
                for (Poliza poliza : importedPolizas) {
                    polizaJpaController.create(poliza);
                }
            }
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
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

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

            Poliza[] polizaListOrphanCheck = new Poliza[asegurado.getPolizaList().size()];
            for (int i = 0; i < asegurado.getPolizaList().size(); i++) {
                polizaListOrphanCheck[i] = asegurado.getPolizaList().get(i);
            }
            PolizaJpaController polizaJpaController = new PolizaJpaController();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                polizaJpaController.remove(polizaListOrphanCheckPoliza);
            }

//            List<Poliza> polizaListOrphanCheck = asegurado.getPolizaList();
//            PolizaJpaController polizaJpaController = new PolizaJpaController();
//            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
//                polizaJpaController.remove(polizaListOrphanCheckPoliza);
//            }
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

    private void removePoliza(Poliza poliza) throws Exception {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
//            em.getTransaction().begin();//if active throws exception
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
                for (Cliente cliente : polizaVidaOrphanCheck.getClienteList()) {
                    beneficiarioJpaController.remove(new Beneficiario(cliente, polizaVidaOrphanCheck));
                }
                polizaVidaOrphanCheck.setPoliza(null);
                polizaVidaOrphanCheck = em.merge(polizaVidaOrphanCheck);
            }
            PolizaGmm polizaGmmOrphanCheck = poliza.getPolizaGmm();
            if (polizaGmmOrphanCheck != null) {
                DependienteJpaController dependienteJpaController = new DependienteJpaController();
                for (Cliente cliente : polizaGmmOrphanCheck.getClienteList()) {
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
//                contratante.getPolizaList().remove(poliza);
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
            //TODO: Aparentemente si elimina todos los recibos
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    public List<Asegurado> getByName(String nombre, String paterno, String materno) {
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

            StringBuilder sb = new StringBuilder("SELECT a FROM Asegurado a WHERE ");
            if (nombre != null && !nombre.isEmpty()) {
                sb.append("a.cliente.nombre LIKE :nombre ");
            }
            if (paterno != null && !paterno.isEmpty()) {
                if (nombre != null && !nombre.isEmpty()) {
                    sb.append("AND ");
                }
                sb.append("a.cliente.apellidopaterno LIKE :paterno ");
            }
            if (materno != null && !materno.isEmpty()) {
                if ((nombre != null && !nombre.isEmpty()) || (paterno != null && !paterno.isEmpty())) {
                    sb.append("AND ");
                }
                sb.append("a.cliente.apellidomaterno LIKE :materno ");
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
