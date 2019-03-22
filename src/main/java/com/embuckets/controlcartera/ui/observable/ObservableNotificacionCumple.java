/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public interface ObservableNotificacionCumple {

    StringProperty nombreCompletoProperty();

    StringProperty nacimientoProperty();

    StringProperty faltanProperty();

    StringProperty estadoProperty();

}
