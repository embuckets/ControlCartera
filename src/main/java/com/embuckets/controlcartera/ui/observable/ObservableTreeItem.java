/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import java.util.List;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public interface ObservableTreeItem {

    int getId();

    StringProperty nombreProperty();

    StringProperty numeroProperty();

    StringProperty aseguradoraProperty();

    StringProperty ramoProperty();

    StringProperty productoProperty();

    StringProperty planProperty();

    StringProperty primaProperty();

    List<? extends ObservableTreeItem> getPolizaListProperty();

}
