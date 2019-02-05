package com.openclassrooms.realestatemanager.apartmentdetail;

import android.content.Context;

import com.openclassrooms.realestatemanager.R;

public class BusinessSecondFragment {

    public static String getGoogleAdressCode(String adress, String town, Context context){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://maps.googleapis.com/maps/api/staticmap?center=");
        stringBuilder.append(getAdressSyntax(adress, town));
        stringBuilder.append("&zoom=16&size=400x200&format=jpg&markers=size:mid%7Ccolor:red%7Clabel:C%7C");
        stringBuilder.append(getAdressSyntax(adress, town));
        stringBuilder.append("&key=");
        stringBuilder.append(context.getString(R.string.api_key));
        return stringBuilder.toString();
    }

    //get adress syntax
    private static String getAdressSyntax(String address, String town){
        String result = address.replace(' ', '+');
        result += "+" + town;
        return result;
    }

}
