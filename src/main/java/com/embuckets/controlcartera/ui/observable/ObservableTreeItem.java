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

    /**
     *
     * @return
     */
    int getId();

    /**
     *
     * @return
     */
    StringProperty nombreProperty();

    /**
     *
     * @return
     */
    StringProperty numeroProperty();

    /**
     *
     * @return
     */
    StringProperty aseguradoraProperty();

    /**
     *
     * @return
     */
    StringProperty ramoProperty();

    /**
     *
     * @return
     */
    StringProperty productoProperty();

    /**
     *
     * @return
     */
    StringProperty planProperty();

    /**
     *
     * @return
     */
    StringProperty primaProperty();

    /**
     *
     * @return
     */
    StringProperty estadoProperty();

    /**
     *
     * @return
     */
    List<? extends ObservableTreeItem> getPolizaListProperty();

}
