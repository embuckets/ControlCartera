/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.service;

import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author emilio
 */
public class TemplateGeneratorTest {

    BaseDeDatos bd;

    public TemplateGeneratorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        bd = BaseDeDatos.getInstance();
    }

    @After
    public void tearDown() {
        bd.close();
    }

}
