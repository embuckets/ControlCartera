/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.DocumentoRecibo;
import java.io.Serializable;
import javax.persistence.Query;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import javax.persistence.EntityManager;

/**
 *
 * @author emilio
 */
public class DocumentoReciboJpaController implements Serializable, JpaController {

    /**
     *
     */
    public DocumentoReciboJpaController() {
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void remove(Object object) throws Exception {
        DocumentoRecibo documentoRecibo = (DocumentoRecibo) object;
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
            Query query = em.createNativeQuery("DELETE FROM APP.Documento_Recibo WHERE idrecibo = :idrecibo");
            query.setParameter("idrecibo", documentoRecibo.getIdrecibo());
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
        return DocumentoRecibo.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdrecibo";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "idrecibo";
    }

}
