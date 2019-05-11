/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import com.embuckets.controlcartera.entidades.Estado;

/**
 *
 * @author emilio
 */
public class EstadoJpaController implements Serializable, JpaController {

    /**
     *
     */
    public EstadoJpaController() {
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void create(Object object) throws Exception {
        //TODO: INSERT NOTHING
    }

    /**
     *
     * @param <T>
     * @param object
     * @return
     */
    @Override
    public <T> T edit(Object object) {
        //SHOULD DO NOTHING
        return null;

    }

    /**
     *
     * @param object
     */
    @Override
    public void remove(Object object) {
        //SHOULD DO NOTHING

    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return Estado.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByEstado";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "estado";
    }

}
