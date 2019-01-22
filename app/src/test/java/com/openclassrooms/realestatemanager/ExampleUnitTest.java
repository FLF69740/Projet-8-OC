package com.openclassrooms.realestatemanager;

import android.view.View;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
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