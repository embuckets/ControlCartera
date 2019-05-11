/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import com.embuckets.controlcartera.entidades.Domicilio;

/**
 *
 * @author emilio
 */
public class DomicilioJpaController implements Serializable, JpaController {

    /**
     *
     */
    public DomicilioJpaController() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return Domicilio.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIddomicilio";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "iddomicilio";
    }

}
