/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import com.embuckets.controlcartera.entidades.Cliente;
import java.util.List;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Logging;
import java.util.ArrayList;
import javax.persistence.EntityManager;

/**
 *
 * @author emilio
 */
public class ClienteJpaController implements Serializable, JpaController {

    /**
     *
     */
    public ClienteJpaController() {
    }

    /**
     * Busca a los clientes por nombre y apellidos.
     * En caso de que algun parametro sea <code>null</code> o vacio se omite del query. 
     * Busca cliente que contengan parcialmente los parametros especificados, por ejemplo, si solo se especifica el nombre "em", regresera los clientes con nombre "Emilio", "Emiliano", "Emma" etc.
     * @param nombre nombre de pila
     * @param paterno apellido paterno
     * @param materno apellido materno
     * @return todos los clientes con nombre y apellidos especificados
     */
    public List<Cliente> getByName(String nombre, String paterno, String materno) {
        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();

            StringBuilder sb = new StringBuilder("SELECT a FROM Cliente a WHERE ");
            if (nombre != null && !nombre.isEmpty()) {
                sb.append("a.nombre LIKE :nombre ");
            }
            if (paterno != null && !paterno.isEmpty()) {
                if (nombre != null && !nombre.isEmpty()) {
                    sb.append("AND ");
                }
                sb.append("a.apellidopaterno LIKE :paterno ");
            }
            if (materno != null && !materno.isEmpty()) {
                if ((nombre != null && !nombre.isEmpty()) || (paterno != null && !paterno.isEmpty())) {
                    sb.append("AND ");
                }
                sb.append("a.apellidomaterno LIKE :materno ");
            }

            Query query = em.createQuery(sb.toString());
            if (nombre != null && !nombre.isEmpty()) {
                query.setParameter("nombre", "%" + nombre + "%");
            }
            if (paterno != null && !paterno.isEmpty()) {
                query.setParameter("paterno", "%" + paterno + "%");
            }
            if (materno != null && !materno.isEmpty()) {
                query.setParameter("materno", "%" + materno + "%");
            }
            return query.getResultList();

        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.Exception_MESSAGE, ex);
        }
        return new ArrayList<>();
    }

    /**
     *
     * @return
     */
    @Override
    public String getControlledClassName() {
        return Cliente.class.getSimpleName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdcliente";
    }

    /**
     *
     * @return
     */
    @Override
    public String getFindByIdParameter() {
        return "idcliente";
    }

}
