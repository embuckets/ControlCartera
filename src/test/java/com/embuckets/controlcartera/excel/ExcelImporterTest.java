/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.excel;

import com.embuckets.controlcartera.entidades.Asegurado;
import java.io.File;
import java.util.List;

import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emilio
 */
public class ExcelImporterTest {

    public ExcelImporterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of importar method, of class ExcelImporter.
     */
    @Test
    public void testImportar() {
        System.out.println("importar");
        File file = new File("C:/Users/emilio/Desktop/plantilla-cartera-copy.xlsx");
        ExcelImporter instance = new ExcelImporter();
        List<Asegurado> result = null;
        try {
            result = instance.importar(file);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Se encontraron erroes");
        }
        assertNotNull(result);
        System.out.println();
        result.stream().forEach(a -> {
            System.out.println(a.nombreProperty().get());
            a.getPolizaList().stream().forEach(p -> {
                System.out.println("\t" + p.getNumero() + ", " + p.getAseguradora().getAseguradora());
            });
        });
        // TODO review the generated test code and remove the default call to fail.
    }

}
