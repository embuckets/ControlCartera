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
public interface ObservableTelefono {

    StringProperty telefonoProperty();

    StringProperty extensionProperty();

    StringProperty tipoProperty();

}
