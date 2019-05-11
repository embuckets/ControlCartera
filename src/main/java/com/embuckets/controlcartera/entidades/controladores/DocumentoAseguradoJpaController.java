/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import javax.persistence.EntityManager;

/**
 *
 * @author emilio
 */
public class DocumentoAseguradoJpaController implements Serializable, JpaController {

    /**
     *
     */
    public DocumentoAseguradoJpaController() {
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void remove(Object object) throws Exception {
        DocumentoAsegurado documentoAsegurado = (DocumentoAsegurado) object;
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
            Query query = em.createNativeQuery("DELETE FROM APP.Documento_Asegurado WHERE nombre = :nombre AND tipodocumento = :tipo AND idcliente = :idcliente");
            query.setParameter("nombre", documentoAsegurado.getDocumentoAseguradoPK().getNombre());
            query.setParameter("tipo", documentoAsegurado.getTipoDocumentoAsegurado().getTipodocumento());
            query.setParameter("idcliente", documentoAsegurado.getAsegurado().getIdcliente());
            query.executeUpdate();

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

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return DocumentoAsegurado.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdcliente";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "idcliente";
    }

}
