package com.openclassrooms.realestatemanager.apartmentfilters;

import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.apartmentmodifier.TransformerApartmentItems;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.LineSearch;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import java.util.ArrayList;
import java.util.List;

public class ApartmentSelector {

    private String mMoney;
    private String mDimension;

    public ApartmentSelector(String money, String dimension) {
        this.mMoney = money;
        this.mDimension = dimension;
    }

    private static final String CHECK_STRING_CONTAINS = "CHECK_STRING_CONTAINS";
    private static final String CHECK_MULTIPLE_STRING_CONTAINS = "CHECK_MULTIPLE_STRING_CONTAINS";
    private static final String CHECK_STRING_EQUALS = "CHECK_STRING_EQUALS";
    private static final String CHECK_INTEGER_TOLERANCES = "CHECK_INTEGER_TOLERANCES";
    private static final String NO_CHECK = "NO_CHECK";

    public List<Apartment> getSelectedApartments(List<Apartment> apartmentList, List<LineSearch> lineSearchList, LineSearch lineSearchInscription, LineSearch lineSearchSold){
        if (lineSearchInscription.isChecked()) {
            inscriptionOrSoldValidation(apartmentList, lineSearchInscription, 1);
        }
        if (lineSearchSold.isChecked()){
            inscriptionOrSoldValidation(apartmentList, lineSearchSold, 2);
        }
        for (int i = 0 ; i < lineSearchList.size() ; i++){
            apartmentsAfterFilters(apartmentList, lineSearchList.get(i), i);
        }
        return apartmentList;
    }

    // remove apartments outside inscription or sold tolerances
    private void inscriptionOrSoldValidation(List<Apartment> apartmentList, LineSearch lineSearchDelta, int position){
        DateTime target = new DateTime();
        DateTime inf = new DateTime();
        DateTime sup = new DateTime();
        if (lineSearchDelta.getInformationFrom().equals(LineSearch.EMPTY_CASE)){
            lineSearchDelta.setInformationFrom(inf.toString("dd/MM/yyyy"));
        }
        if (lineSearchDelta.getInformationTo().equals(LineSearch.EMPTY_CASE)) {
            lineSearchDelta.setInformationTo(sup.toString("dd/MM/yyyy"));
        }
        inf = DateTime.parse(lineSearchDelta.getInformationFrom(), DateTimeFormat.forPattern("dd/MM/yyyy"));
        sup = DateTime.parse(lineSearchDelta.getInformationTo(), DateTimeFormat.forPattern("dd/MM/yyyy"));

        for (int i = apartmentList.size()-1 ; i >= 0 ; i-- ){
            if ((apartmentList.get(i).getDateInscription().equals(LineSearch.EMPTY_CASE) && position == 1) ||
                    (apartmentList.get(i).getDateSold().equals(LineSearch.EMPTY_CASE) && position == 2)){
                apartmentList.remove(i);
            }else {
                if (position == 1) {
                    target = DateTime.parse(apartmentList.get(i).getDateInscription(), DateTimeFormat.forPattern("dd/MM/yyyy"));
                }
                if (position == 2) {
                    target = DateTime.parse(apartmentList.get(i).getDateSold(), DateTimeFormat.forPattern("dd/MM/yyyy"));
                }
                if (target.isBefore(inf) || sup.isBefore(target)) {
                    apartmentList.remove(i);
                }
            }
        }
    }

    // remove apartments with lineSearchList criteria
    private void apartmentsAfterFilters(List<Apartment> apartmentList, LineSearch lineSearch, int position){
        if (lineSearch.isChecked()) {
            switch (checkTypeOfControl(position, false, null)) {
                case CHECK_INTEGER_TOLERANCES:
                    control_CHECK_INTEGER_TOLERANCES(apartmentList, lineSearch, position);
                    break;
                case CHECK_STRING_CONTAINS:
                    control_CHECK_STRING_CONTAINS(apartmentList, lineSearch, position);
                    break;
                case CHECK_MULTIPLE_STRING_CONTAINS:
                    control_CHECK_MULTIPLE_STRING_CONTAINS(apartmentList, lineSearch);
                    break;
                case CHECK_STRING_EQUALS:
                    control_CHECK_STRING_EQUALS(apartmentList, lineSearch, position);
                    break;
            }
        }
    }

    // check type of control after line search section information
    private String checkTypeOfControl(int position, boolean isApartmentEntry, Apartment apartment){
        String result = NO_CHECK;
        switch (position){
            case 0 :
                if (isApartmentEntry){
                    result = apartment.getType();
                }else {
                    result = CHECK_STRING_CONTAINS;
                }
                break;
            case 1 :
                if (isApartmentEntry){
                    if (mMoney.equals("€")){
                        result = String.valueOf(Utils.convertDollarToEuro(apartment.getPrice()));
                    }else {
                        result = String.valueOf(apartment.getPrice());
                    }
                }else {
                    result = CHECK_INTEGER_TOLERANCES;
                }
                break;
            case 2 :
                if (isApartmentEntry){
                    if (mDimension.equals("m²")){
                        result = String.valueOf(Utils.getSquareMeter(apartment.getDimension()));
                    }else {
                        result = String.valueOf(apartment.getDimension());
                    }
                }else {
                    result = CHECK_INTEGER_TOLERANCES;
                }
                break;
            case 3 :
                if (isApartmentEntry){
                    result = String.valueOf(apartment.getRoomNumber());
                }else {
                    result = CHECK_INTEGER_TOLERANCES;
                }
                break;
            case 5 :
                if (isApartmentEntry){
                    result = apartment.getAdress();
                }else {
                    result = CHECK_STRING_CONTAINS;
                }
                break;
            case 6 :
                if (isApartmentEntry){
                    result = String.valueOf(apartment.getPostalCode());
                }else {
                    result = CHECK_STRING_EQUALS;
                }
                break;
            case 7 :
                if (isApartmentEntry){
                    result = apartment.getTown();
                }else {
                    result = CHECK_STRING_EQUALS;
                }
                break;
            case 9 :
                if (isApartmentEntry){
                    result = apartment.getPoInterest();
                }else {
                    result = CHECK_MULTIPLE_STRING_CONTAINS;
                }
                break;
            case 11 :
                if (isApartmentEntry){
                    result = String.valueOf(BusinessApartmentFilters.getCountSubstring(TransformerApartmentItems.ENTITY_SEPARATOR, apartment.getUrlPicture())+1);
                }else {
                    result = CHECK_STRING_EQUALS;
                }
                break;
        }
        return result;
    }

    // control realisation with CHECK_INTEGER_TOLERANCES
    private void control_CHECK_INTEGER_TOLERANCES(List<Apartment> apartmentList, LineSearch lineSearch, int position){
        int inf = Integer.valueOf(lineSearch.getInformationFrom());
        int sup = Integer.valueOf(lineSearch.getInformationTo());
        for (int i = apartmentList.size()-1 ; i >= 0 ; i-- ){
            int target = Integer.valueOf(checkTypeOfControl(position, true, apartmentList.get(i)));
            if (target < inf || sup < target){
                apartmentList.remove(i);
            }
        }
    }

    // control realisation with CHECK_INTEGER_TOLERANCES
    private void control_CHECK_STRING_CONTAINS(List<Apartment> apartmentList, LineSearch lineSearch, int position){
        String subString = lineSearch.getInformationFrom();
        for (int i = apartmentList.size()-1 ; i >= 0 ; i-- ){
            String target = checkTypeOfControl(position, true, apartmentList.get(i));
            if (!target.contains(subString)){
                apartmentList.remove(i);
            }
        }
    }

    // control realisation with CHECK_INTEGER_TOLERANCES
    private void control_CHECK_MULTIPLE_STRING_CONTAINS(List<Apartment> apartmentList, LineSearch lineSearch){
        String[] keyWords = BusinessApartmentFilters.getKeyWordsList(lineSearch.getInformationFrom());
        List<Boolean> apartmentChecked = new ArrayList<>();
        for (int i = 0; i < apartmentList.size() ; i++){
            apartmentChecked.add(false);
        }
        for (String keyWord : keyWords) {
            for (int i = 0; i < apartmentList.size(); i++) {
                if (apartmentList.get(i).getPoInterest().contains(keyWord)) {
                    apartmentChecked.set(i, true);
                }
            }
        }
        for (int i = apartmentList.size()-1 ; i >= 0 ; i-- ){
            if (!apartmentChecked.get(i).equals(true)){
                apartmentList.remove(i);
            }
        }
    }

    // control realisation with CHECK_INTEGER_TOLERANCES
    private void control_CHECK_STRING_EQUALS(List<Apartment> apartmentList, LineSearch lineSearch, int position){
        String subString = lineSearch.getInformationFrom();
        for (int i = apartmentList.size()-1 ; i >= 0 ; i-- ){
            String target = checkTypeOfControl(position, true, apartmentList.get(i));
            if (!target.equals(subString)){
                apartmentList.remove(i);
            }
        }
    }
}