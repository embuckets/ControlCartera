/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import com.embuckets.controlcartera.entidades.globals.Globals;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author emilio
 */
public class UitilityTest {

    public UitilityTest() {
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

    @Test
    public void testPhoneFilter() {
        String num1 = "5521195514";
        num1.matches("[0-9]+");
        String phone = "" + num1.charAt(0) + num1.charAt(1);
        for (int i = 2; i < num1.length(); i++) {
            phone += '-' + num1.charAt(i);
        }
        System.out.println(phone);
    }

    @Test
    public void testDateUntil() {
        LocalDate today = LocalDate.now();
        LocalDate firstJan = LocalDate.of(2019, Month.JANUARY, 1);
        LocalDate thirtyOneDic = LocalDate.of(2018, Month.DECEMBER, 31);
        LocalDate firstDic = LocalDate.of(2018, Month.DECEMBER, 1);
        System.out.println("today until firstJan = " + today.until(firstJan).toString());
        System.out.println("firstJan until thirtyOneDic = " + firstJan.until(thirtyOneDic).toString());
        System.out.println("thirtyOneDic until firstJan = " + thirtyOneDic.until(firstJan).toString());
        System.out.println("firstDic until thirtyOneDic = " + firstDic.until(thirtyOneDic).toString());
    }

    @Test
    public void testNumberFormatter() {
        DecimalFormat formatter = new DecimalFormat("$###,###,###.###");
//        System.out.println(formatter.format(Float.valueOf("1000000")));
//        System.out.println(formatter.format(456789));
//        System.out.println(formatter.format("SIN LIMITE"));

        System.out.println(Globals.formatCantidad(Float.valueOf("1000000")));
        System.out.println(Globals.formatCantidad(456789));
        System.out.println(Globals.formatCantidad(new BigDecimal(123456.45646)));
        System.out.println(Globals.formatCantidad("SIN LIMITE"));

    }

}
