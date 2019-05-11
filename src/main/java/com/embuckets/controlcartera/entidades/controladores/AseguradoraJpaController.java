/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Aseguradora;
import java.io.Serializable;

/**
 *
 * @author emilio
 */
public class AseguradoraJpaController implements Serializable, JpaController {

    /**
     *
     */
    public AseguradoraJpaController() {
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
        //
        return null;
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void create(Object object) throws Exception {
        //DO NOTHING
    }

    /**
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void remove(Object object) throws Exception {
        //DO NOTHING
    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return Aseguradora.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByAseguradora";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "aseguradora";
    }

}
