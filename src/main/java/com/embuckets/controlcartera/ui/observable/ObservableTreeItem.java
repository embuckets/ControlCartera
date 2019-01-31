/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import javafx.beans.property.Property;

/**
 *
 * @author emilio
 */
public interface ObservableTreeItem {
    Property nombreProperty();
    Property numeroProperty();
    Property aseguradoraProperty();
    Property primaProperty();
    
}
