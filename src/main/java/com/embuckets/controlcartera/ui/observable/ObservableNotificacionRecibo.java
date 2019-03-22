/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Cliente;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Poliza;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public interface ObservableNotificacionRecibo {

    StringProperty polizaProperty();

    StringProperty aseguradoProperty();

    StringProperty cubreDesdeProperty();

    StringProperty cubreHastaProperty();

    StringProperty importeProperty();
    
    StringProperty cobranzaProperty();

    StringProperty enviadoProperty();
    
    StringProperty documentoProperty();
    
}
