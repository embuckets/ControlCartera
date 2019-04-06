/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.Cobranza;
import java.io.Serializable;

/**
 *
 * @author emilio
 */
public class CobranzaJpaController implements Serializable, JpaController {

    public CobranzaJpaController() {
    }

    @Override
    public void remove(Object object) throws Exception {
        //DO NOTHING
    }

    @Override
    public <T> T edit(Object object) throws Exception {
        return null;
    }

    @Override
    public void create(Object object) throws Exception {
        //DO NOTHING
    }

    @Override
    public String getControlledClassName() {
        return Cobranza.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByCobranza";
    }

    @Override
    public String getFindByIdParameter() {
        return "cobranza";
    }

}
