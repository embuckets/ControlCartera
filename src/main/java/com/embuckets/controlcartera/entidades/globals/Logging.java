/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import com.embuckets.controlcartera.exceptions.BaseDeDatosException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.PersistenceException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emilio
 */
public class Logging {

    private static Logging instance;
    private Map<String, String> sqlStates;

    public static final String TRANSACTION_ROLLED_BACK_MESSAGE = "Transaction rolled-back";

    //SQLSTATE MESSAGES
    public static final String SQLSTATE_23505_STATE = "23505";
    public static final String SQLSTATE_23505_MESSAGE = "Error al guardar porque el valor ya existe";
    public static final String EntityExistsException_MESSAGE = "Error al guardar porque el valor ya existe";
    public static final String IllegalStateException_MESSAGE = "Ha ocurrido un error inesperado";
    public static final String Exception_MESSAGE = "Ha ocurrido una error";

    private Logging() {
        createSQLStates();
    }

    public static Logging getInstance() {
        if (instance == null) {
            instance = new Logging();
        }
        return instance;
    }

    private void createSQLStates() {
        sqlStates = new HashMap<>();
        sqlStates.put(SQLSTATE_23505_STATE, SQLSTATE_23505_MESSAGE);
    }

    public Exception makeReadableException(Exception ex) {
        if (ex instanceof PersistenceException) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) ex.getCause();
                DerbySQLIntegrityConstraintViolationException dex = (DerbySQLIntegrityConstraintViolationException) cve.getCause();
                return new BaseDeDatosException(sqlStates.get(dex.getSQLState()), dex);
            }

        }
        return ex;
    }

    public void trace(Logger logger, String message, Throwable ex) {
//        logger.trace(message, );
    }

}
