/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.EstadoNotificacion;
import java.io.Serializable;

/**
 *
 * @author emilio
 */
public class EstadoNotificacionJpaController implements Serializable, JpaController {

    /**
     *
     */
    public EstadoNotificacionJpaController() {
    }
    
    /**
     *
     * @param object
     */
    @Override
    public void remove(Object object) {
        //DO NOTHING
    }

    /**
     *
     * @param <T>
     * @param object
     * @return
     * @throws Exception
     */
    @Override
    public <T> T edit(Object object) throws Exception {
        //DO NOTHING
        return null;
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void create(Object object) throws  Exception {
        //DO NOTHING
    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return EstadoNotificacion.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByEstadonotificacion";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "estadonotificacion";
    }

}
