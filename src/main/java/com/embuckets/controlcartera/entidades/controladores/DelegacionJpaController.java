/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Delegacion;
import java.io.Serializable;
import com.embuckets.controlcartera.exceptions.PreexistingEntityException;

/**
 *
 * @author emilio
 */
public class DelegacionJpaController implements Serializable, JpaController {

    public DelegacionJpaController() {
    }

    @Override
    public <T> T edit(Object object) throws Exception {
        //SHOULD DO NOTHING
        return null;
    }

    @Override
    public void remove(Object object) throws Exception {
        //SHOULD DO NOTHING
    }

    @Override
    public void create(Object object) throws PreexistingEntityException, Exception {
        //SHOULD DO NOTHING

    }

    @Override
    public String getControlledClassName() {
        return Delegacion.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByDelegacion";
    }

    @Override
    public String getFindByIdParameter() {
        return "delegacion";
    }

}
