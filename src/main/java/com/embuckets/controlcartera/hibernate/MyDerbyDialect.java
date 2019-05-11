/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.hibernate;

import org.hibernate.dialect.DerbyTenSevenDialect;
import org.hibernate.dialect.function.StandardJDBCEscapeFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * Extiende las funcionalidades del dialecto Derby
 * @author emilio
 */
public class MyDerbyDialect extends DerbyTenSevenDialect {

    /**
     *
     */
    public MyDerbyDialect() {
        super();
        registerFunction("timeDiff", new StandardJDBCEscapeFunction("TIMESTAMPDIFF", StandardBasicTypes.INTEGER));
    }

}
