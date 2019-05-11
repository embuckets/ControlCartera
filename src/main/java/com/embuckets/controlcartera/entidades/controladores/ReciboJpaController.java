/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import com.embuckets.controlcartera.entidades.Recibo;



/**
 *
 * @author emilio
 */
public class ReciboJpaController implements Serializable, JpaController {

    /**
     *
     */
    public ReciboJpaController() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return Recibo.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdrecibo";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "idrecibo";
    }

}
