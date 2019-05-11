/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Beneficiario;
import java.io.Serializable;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.PolizaVida;



import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import javax.persistence.EntityManager;

/**
 *
 * @author emilio
 */
public class PolizaVidaJpaController implements Serializable, JpaController {

    /**
     *
     */
    public PolizaVidaJpaController() {
    }

    /**
     * Elimina los enlaces de todos los beneficiarios y elimina la poliza de vida
     * @param object poliza de vida a eliminar
     * @throws Exception - si falla la operacion
     */
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

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return PolizaVida.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdpoliza";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "idpoliza";
    }

}
