/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

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
        alert.setContentText(ex.getLocalizedMessage());
        return alert;
    }

    public static Alert makeAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert;
    }

    public static Alert makeAlert(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        String title = getTitle(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert;
    }

    public static Alert makeAlert(Alert.AlertType alertType, String header, Exception ex) {
        Alert alert = new Alert(alertType);
        String title = getTitle(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(ex.getLocalizedMessage());
        return alert;
    }

    private static String getTitle(Alert.AlertType alertType) {
        switch (alertType) {
            case CONFIRMATION:
                return "Confirmación";
            case ERROR:
                return "Error";
            case INFORMATION:
                return "Información";
            case NONE:
                return "";
            case WARNING:
                return "Advertencia";
            default:
                return "";
        }

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
