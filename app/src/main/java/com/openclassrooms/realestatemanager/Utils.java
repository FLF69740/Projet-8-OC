package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.text.InputType;
import android.view.View;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        if (dollars != 0) {
            return (int) Math.round(dollars * 0.812);
        }else {
            return dollars;
        }
    }

    public static int convertEuroToDollar(int euros){
        if (euros != 0) {
            return (int) Math.round(euros / 0.812);
        }else {
            return euros;
        }
    }

    //Price format (main & detail fragment)
    public static String getPriceFormat(int price){
        StringBuilder resultTemp = new StringBuilder(String.valueOf(price));
        resultTemp.reverse();
        char[] priceChars = resultTemp.toString().toCharArray();
        StringBuilder result = new StringBuilder();
        int sectionChar = 0;
        for (int i = 0 ; i < priceChars.length ; i++){
            sectionChar++;
            if (sectionChar == 4){
                result.append(',');
                sectionChar = 1;
            }
            result .append(priceChars[i]);
        }
        result.reverse();
        return result.toString();
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    //Get day of month
    public static int getDayOfMonth(String date){
        String[] divider = date.split("/");
        return Integer.valueOf(divider[0]);
    }

    //Get day of month
    public static int getMonth(String date){
        String[] divider = date.split("/");
        return Integer.valueOf(divider[1]);
    }

    //Get day of month
    public static int getYear(String date){
        String[] divider = date.split("/");
        return Integer.valueOf(divider[2]);
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context){
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    /**
     *  APARTMENT DETAILS FORMAT
     */

    //Full adress composition (detail fragment)
    public static String getFullAdress(String adress, String postalCode, String town){
        return adress + "\n" + postalCode + "\n" + town;
    }

    //number of rooms (detail fragment)
    public static String getRooms(int numberOfRooms, View view){
        return String.valueOf(numberOfRooms) + " " + view.getContext().getString(R.string.apartment_room);
    }

    //sold text return
    public static String getStringSold(Boolean sold, View view){
        if (sold){
            return view.getContext().getString(R.string.apartment_sold);
        } else {
            return view.getContext().getString(R.string.apartment_for_sale);
        }
    }

    //sold color return
    public static int getColorSold(Boolean sold, View view){
        if (sold){
            return Color.parseColor("red");
        } else {
            return Color.parseColor("green");
        }
    }

    // return the state of an EditText depending of entry type
    public static int getInputType(Context context, String type){
        if (type.contains(context.getString(R.string.apartment_title_price)) ||
                type.contains(context.getString(R.string.apartment_title_square)) ||
                type.equals(context.getString(R.string.apartment_title_room)) ||
                type.equals(context.getString(R.string.apartment_title_postal_code))){
            return InputType.TYPE_CLASS_NUMBER;
        }else if (type.equals(context.getString(R.string.apartment_title_town))){
            return InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;
        }else{
            return InputType.TYPE_CLASS_TEXT;
        }
    }

    /**
     *  CONVERTION BETWEEN SQUARE METER AND SQUARE FEET
     */

    //meters to feet
    public static int getSquareFeet(int meter){
        if (meter != 0) {
            return (int) Math.round(meter * 10.764);
        } else {
            return meter;
        }
    }

    //feet to meter
    public static int getSquareMeter(int feet){
        if (feet != 0) {
            return (int) Math.round(feet / 10.764);
        } else {
            return feet;
        }
    }

    //dimension (detail fragment)
    public static String getDimension(int dimension, String unity, View view){
        if (unity.equals(view.getContext().getString(R.string.METER))){
            return dimension + view.getContext().getString(R.string.units_meters);
        } else {
            return dimension + view.getContext().getString(R.string.units_square);
        }
    }

    /**
     *  LOAN SIMULATION
     */

    public static double getLoaninterestResult(int price, int nbYear, double rate){
        // exemple 200000$ during 5 years with 1.1 rate : (200000 x 1.1 / 100) x 5
        return price*rate/100*nbYear;
    }

    public static double getMonthRefund(double amount, int year){
        return amount / (year * 12);
    }

}
