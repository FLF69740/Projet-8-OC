package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
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
        return (int) Math.round(dollars * 0.812);
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

    //Full adress composition (detail fragment)
    public static String getFullAdress(String adress, String postalCode, String town){
        return adress + "\n" + postalCode + "\n" + town;
    }

    //number of rooms (detail fragment)
    public static String getRooms(int numberOfRooms, View view){
        return String.valueOf(numberOfRooms) + " " + view.getContext().getString(R.string.apartment_room);
    }

    //dimension (detail fragment)
    public static String getDimension(int dimension, String unity, View view){
        if (unity.equals(view.getContext().getString(R.string.METER))){
            return dimension + " sq m";
        } else {
            return dimension + " sq ft";
        }
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

    /**
     *  CONVERTION BETWEEN SQUARE METER AND SQUARE FEET
     */

    //meters to feet
    public static int getSquareFeet(int meter){
        return (int) (meter * 10.764);
    }

    //feet to meter
    public static int getSquareMeter(int feet){
        return (int) (feet / 10.764);
    }


}
