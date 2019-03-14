/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.ui;

import javafx.scene.control.Alert;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author emilio
 */
public class Utilities {

    public static Alert makeAlert(Exception ex, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(ex.getMessage());
        return alert;
    }

    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }
}
