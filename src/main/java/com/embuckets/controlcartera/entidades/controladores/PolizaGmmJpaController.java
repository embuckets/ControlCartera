/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import com.embuckets.controlcartera.entidades.PolizaGmm;




/**
 *
 * @author emilio
 */
public class PolizaGmmJpaController implements Serializable, JpaController {

    /**
     *
     */
    public PolizaGmmJpaController() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return PolizaGmm.class.getSimpleName();
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
