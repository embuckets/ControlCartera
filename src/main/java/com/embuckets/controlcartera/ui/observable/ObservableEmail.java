/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui.observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class ObservableEmail {

    private StringProperty emailProperty;
    private StringProperty tipoProperty;

    public ObservableEmail(String emailProperty, String tipoProperty) {
        this.emailProperty = new SimpleStringProperty(emailProperty);
        this.tipoProperty = new SimpleStringProperty(tipoProperty);
    }

    public StringProperty emailProperty() {
        return emailProperty;
    }

    public void setEmailProperty(StringProperty emailProperty) {
        this.emailProperty = emailProperty;
    }

    public StringProperty tipoProperty() {
        return tipoProperty;
    }

    public void setTipoProperty(StringProperty tipoProperty) {
        this.tipoProperty = tipoProperty;
    }

}
