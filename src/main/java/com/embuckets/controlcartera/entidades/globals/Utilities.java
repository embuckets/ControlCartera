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

    /**
     * crea una ventana de alerta mostrando el mensaje de la excepcion y el header especificado
     * @param ex excepcion a mostrar
     * @param header header de la alerta
     * @return ventana de tipo error con el header y contenido principla con el mensaje de la excepcion
     */
    public static Alert makeAlert(Exception ex, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(ex.getLocalizedMessage());
        return alert;
    }

    /**
     * crea una ventana con el tipo de alerta, titulo, header, mensaje especificado
     * @param alertType
     * @param title
     * @param header
     * @param message
     * @return 
     */
    public static Alert makeAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert;
    }

    /**
     * crea una ventana con el tipo de alerta, header y mensaje especificado
     * @param alertType
     * @param header
     * @param message
     * @return
     */
    public static Alert makeAlert(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        String title = getTitle(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert;
    }

    /**
     * crea una ventana con el tipo de alerta, header y mensaje de la excepcion especificados
     * @param alertType
     * @param header
     * @param ex
     * @return
     */
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

    /**
     * obtiene la entidad que envuelve el proxy. solo desenvuelve la entidad principal, no desenvuelve sus atributos.
     * @param <T>
     * @param entity
     * @return 
     */
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
