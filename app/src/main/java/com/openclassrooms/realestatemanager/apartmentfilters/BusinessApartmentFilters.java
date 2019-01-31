package com.openclassrooms.realestatemanager.apartmentfilters;

import android.content.Context;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.LineSearch;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.List;

public class BusinessApartmentFilters {

    // initialisation list of section
    private static List<String> getSectionFilter(Context context, String money, String dimension){
        List<String> myList = new ArrayList<>();
        myList.add(context.getString(R.string.apartment_inscription));
        myList.add(context.getString(R.string.apartment_title_date_sold));
        myList.add(context.getString(R.string.apartment_title_type));
        myList.add(context.getString(R.string.apartment_title_price) + " " + money + " ");
        myList.add(context.getString(R.string.apartment_title_square) + " " + dimension + " ");
        myList.add(context.getString(R.string.apartment_title_room));
        myList.add(context.getString(R.string.apartment_title_adress));
        myList.add(context.getString(R.string.apartment_title_street));
        myList.add(context.getString(R.string.apartment_title_postal_code));
        myList.add(context.getString(R.string.apartment_title_town));
        myList.add(context.getString(R.string.apartment_title_po));
        myList.add(context.getString(R.string.apartment_title_po_single));
        myList.add(context.getString(R.string.apartment_title_picture));
        myList.add(context.getString(R.string.search_apartment_number_of_picture));
        return myList;
    }

    // initialisation list of informationFrom and informationTo
    private static List<String> getFromAndToFilter(){
        List<String> myList = new ArrayList<>();
        int countdown = 0;
        while (countdown != 14){
            myList.add(LineSearch.EMPTY_CASE);
            countdown++;
        }
        return myList;
    }

    // initialisation list of boolean isInformationTo
    private static List<Boolean> getIsInformationToFilter(){
        List<Boolean> myList = new ArrayList<>();
        myList.add(true);
        myList.add(true);
        myList.add(false);
        myList.add(true);
        myList.add(true);
        myList.add(true);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        return myList;
    }

    // initialisation list of boolean isInformationTo
    private static List<Boolean> getIsATitleFilter(){
        List<Boolean> myList = new ArrayList<>();
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(true);
        myList.add(false);
        myList.add(false);
        myList.add(false);
        myList.add(true);
        myList.add(false);
        myList.add(true);
        myList.add(false);
        return myList;
    }

    // initialisation list of boolean isInformationTo
    private static List<Boolean> getIsCheckedFilter(){
        List<Boolean> myList = new ArrayList<>();
        int countdown = 0;
        while (countdown != 14){
            myList.add(false);
            countdown++;
        }
        return myList;
    }

    // First Configuration
    public static List<LineSearch> createFirstSearchFilterDB(Context context, String money, String dimension){
        List<LineSearch> myList = new ArrayList<>();
        int countdown = 0;
        while (countdown != 14){
            myList.add(new LineSearch(
                    getSectionFilter(context, money, dimension).get(countdown),
                    getFromAndToFilter().get(countdown),
                    getFromAndToFilter().get(countdown),
                    getIsInformationToFilter().get(countdown),
                    getIsATitleFilter().get(countdown),
                    getIsCheckedFilter().get(countdown)));
            countdown++;
        }
        return myList;
    }

    // Get Calendar date
    public static DateTime getDateMemory(String date){
        DateTime calendar = new DateTime();
        if (!date.equals(LineSearch.EMPTY_CASE)) {
            calendar = calendar.dayOfMonth().setCopy(Utils.getDayOfMonth(date));
            calendar = calendar.monthOfYear().setCopy(Utils.getMonth(date));
            calendar = calendar.year().setCopy(Utils.getYear(date));
        }
        return calendar;
    }

    // Get count of substring into a string
    public static int getCountSubstring(String target, String theChain){
        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1){
            lastIndex = theChain.indexOf(target,lastIndex);
            if(lastIndex != -1){
                count ++;
                lastIndex += target.length();
            }
        }
        return count;
    }

    // Get list of Key words for Point of interest
    public static String[] getKeyWordsList(String points){
        if (points.contains(" ")){
            return points.split(" ");
        }else {
            return new String[]{points};
        }
    }



}
