/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import com.embuckets.controlcartera.entidades.Cliente;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public interface ObservableCliente {

    StringProperty nombreProperty();

    StringProperty primerNombreProperty();

    StringProperty paternoProperty();

    StringProperty maternoProperty();

    StringProperty nacimientoProperty();

}
