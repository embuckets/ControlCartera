/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.exceptions.PreexistingEntityException;

/**
 *
 * @author emilio
 */
public class EstadoJpaController implements Serializable, JpaController {

    public EstadoJpaController() {
    }

    @Override
    public void create(Object object) throws PreexistingEntityException, Exception {
        //TODO: INSERT NOTHING
    }

    @Override
    public <T> T edit(Object object) {
        //SHOULD DO NOTHING
        return null;

    }

    @Override
    public void remove(Object object) {
        //SHOULD DO NOTHING

    }

    @Override
    public String getControlledClassName() {
        return Estado.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByEstado";
    }

    @Override
    public String getFindByIdParameter() {
        return "estado";
    }

}
