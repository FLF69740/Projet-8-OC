package com.openclassrooms.realestatemanager;

import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;


public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void loanTest() throws Exception{
        int nbYear = 5;
        int price = 20000;
        double rate = 4.2;

        int result = (int) Utils.getLoaninterestResult(price, nbYear, rate);

        assertEquals("Result", 4200, result);
    }

    @Test
    public void testEuroToDollarConversion() throws Exception{
        assertEquals (265, Utils.convertEuroToDollar(215));
    }

    @Test
    public void testDateFormatToFrenchFormat() throws Exception{
        DateTime today = new DateTime();
        assertEquals(today.toString("dd/MM/yyyy"), Utils.getTodayDate());
    }

    @Test
    public void testIfIntegerIsDividedBySectionEachThree() throws Exception{
        assertEquals("1", Utils.getPriceFormat(1));
        assertEquals("10", Utils.getPriceFormat(10));
        assertEquals("100", Utils.getPriceFormat(100));
        assertEquals("1,000", Utils.getPriceFormat(1000));
        assertEquals("10,000", Utils.getPriceFormat(10000));
        assertEquals("100,000", Utils.getPriceFormat(100000));
        assertEquals("1,000,000", Utils.getPriceFormat(1000000));
        assertEquals("10,000,000", Utils.getPriceFormat(10000000));
        assertEquals("100,000,000", Utils.getPriceFormat(100000000));
        assertEquals("1,000,000,000", Utils.getPriceFormat(1000000000));
    }
}