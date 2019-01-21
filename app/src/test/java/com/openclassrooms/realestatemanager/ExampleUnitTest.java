package com.openclassrooms.realestatemanager;

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
}