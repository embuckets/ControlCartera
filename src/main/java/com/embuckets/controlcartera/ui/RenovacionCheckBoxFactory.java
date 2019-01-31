/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author emilio
 */
public class RenovacionCheckBoxFactory implements Callback<TableColumn.CellDataFeatures<Boolean, CheckBox>, ObservableValue<CheckBox>>{

    @Override
    public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Boolean, CheckBox> param) {
        param.getValue();
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().setValue(false);
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            checkBox.setSelected(newValue);
        });
        return new SimpleObjectProperty<>(checkBox);
        
    }
    
}
