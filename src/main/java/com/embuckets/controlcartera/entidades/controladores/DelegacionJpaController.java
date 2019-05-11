/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Delegacion;
import java.io.Serializable;

/**
 *
 * @author emilio
 */
public class DelegacionJpaController implements Serializable, JpaController {

    /**
     *
     */
    public DelegacionJpaController() {
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
        //SHOULD DO NOTHING
        return null;
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void remove(Object object) throws Exception {
        //SHOULD DO NOTHING
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void create(Object object) throws  Exception {
        //SHOULD DO NOTHING

    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return Delegacion.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByDelegacion";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "delegacion";
    }

}
