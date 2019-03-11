/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.globals;

import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.Delegacion;
import com.embuckets.controlcartera.entidades.Estado;
import com.embuckets.controlcartera.entidades.controladores.AseguradoJpaController;
import com.embuckets.controlcartera.entidades.controladores.DelegacionJpaController;
import com.embuckets.controlcartera.entidades.controladores.EstadoJpaController;
import com.embuckets.controlcartera.entidades.controladores.JpaController;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

/**
 *
 * @author emilio
 */
public class BaseDeDatos {

    private static BaseDeDatos bd;
    private EntityManagerFactory emf;
    private EntityManager em;
    private Map<Class, JpaController> controllers;

    private BaseDeDatos() {
        this.emf = Persistence.createEntityManagerFactory("cartera");
        this.em = this.emf.createEntityManager();
        controllers = createControllers();
    }

    public static BaseDeDatos getInstance() {
        if (bd == null) {
            bd = new BaseDeDatos();
        }
        return bd;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(Object object) {
        try {
            controllers.get(object.getClass()).create(object);
        } catch (Exception ex) {
            Logger.getLogger(BaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <T> List<T> getAll(Class clazz) {
        try {
            return controllers.get(clazz).getAll();
        } catch (Exception ex) {
            Logger.getLogger(BaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public <T> T getById(Class clazz, int id) {
        try {
            return controllers.get(clazz).getById(id);
        } catch (Exception ex) {
            Logger.getLogger(BaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public <T> T edit(Object object) {
        try {
            return controllers.get(object.getClass()).edit(object);
        } catch (Exception ex) {
            Logger.getLogger(BaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void remove(Object object) {
        controllers.get(object.getClass()).remove(object);
    }

    public void close() {
        if (em != null) {
            if (em.getTransaction().isActive()) {
                try {
                    em.getTransaction().commit();
                } catch (RollbackException ex) {
                    Logger.getLogger(BaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            em.close();
            em = null;
        }
        if (emf != null) {
            emf.close();
            emf = null;
        }
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            if (((ex.getErrorCode() == 50000)
                    && ("XJ015".equals(ex.getSQLState())))) {
                // we got the expected exception
//                System.out.println("Derby shut down normally");
                // Note that for single database shutdown, the expected
                // SQL state is "08006", and the error code is 45000.
            } else {
                // if the error code or SQLState is different, we have
                // an unexpected exception (shutdown failed)
//                System.err.println("Derby did not shut down normally");
                Logger.getLogger(BaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Map<Class, JpaController> createControllers() {
        Map<Class, JpaController> map = new HashMap<>();
        map.put(Asegurado.class, new AseguradoJpaController());
        map.put(Delegacion.class, new DelegacionJpaController());
        map.put(Estado.class, new EstadoJpaController());
        return map;
    }

}
