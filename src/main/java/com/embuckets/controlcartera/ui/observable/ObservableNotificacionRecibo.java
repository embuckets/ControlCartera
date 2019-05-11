/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public interface ObservableNotificacionRecibo {

    /**
     *
     * @return
     */
    StringProperty polizaProperty();

    /**
     *
     * @return
     */
    StringProperty aseguradoProperty();

    /**
     *
     * @return
     */
    StringProperty cubreDesdeProperty();

    /**
     *
     * @return
     */
    StringProperty cubreHastaProperty();

    /**
     *
     * @return
     */
    StringProperty importeProperty();
    
    /**
     *
     * @return
     */
    StringProperty cobranzaProperty();

    /**
     *
     * @return
     */
    StringProperty enviadoProperty();
    
    /**
     *
     * @return
     */
    StringProperty documentoProperty();
    
}
