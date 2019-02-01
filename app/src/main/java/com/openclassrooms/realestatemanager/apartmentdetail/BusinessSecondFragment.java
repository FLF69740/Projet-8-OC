package com.openclassrooms.realestatemanager.apartmentdetail;

public class BusinessSecondFragment {

    public static String getGoogleAdressCode(String adress, String town){

        String result = adress.replace(' ', '+');
        result += "+" + town;



        return result;
    }
}
