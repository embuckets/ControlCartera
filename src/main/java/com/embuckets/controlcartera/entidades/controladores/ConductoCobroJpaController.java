/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.ConductoCobro;
import java.io.Serializable;
import com.embuckets.controlcartera.exceptions.PreexistingEntityException;

/**
 *
 * @author emilio
 */
public class ConductoCobroJpaController implements Serializable, JpaController {

    public ConductoCobroJpaController() {
    }

    @Override
    public void create(Object object) throws PreexistingEntityException, Exception {
        //DO NOTHING
    }

    @Override
    public <T> T edit(Object object) throws Exception {
        return null;
    }

    @Override
    public void remove(Object object) {
        //DO NOTHING
    }

    @Override
    public String getControlledClassName() {
        return ConductoCobro.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByConductocobro";
    }

    @Override
    public String getFindByIdParameter() {
        return "conductocobro";
    }

}
