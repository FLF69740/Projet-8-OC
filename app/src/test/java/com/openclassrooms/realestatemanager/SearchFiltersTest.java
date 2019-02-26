package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.apartmentfilters.ApartmentSelector;
import com.openclassrooms.realestatemanager.apartmentfilters.BusinessApartmentFilters;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.LineSearch;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class SearchFiltersTest {

    private static final int NO_CHECK_POINT = 100;

    /**
     *  OBJECT INITIALISATION
     */

    private Apartment getApartmentOne(boolean dateSoldTest){
        Apartment apartment = new Apartment("LOFT", 250000, "1, Rue de la liberté", 69001, "LYON", "10/01/2018",1);
        if (dateSoldTest) apartment.setDateSold("01/01/2013");
        else apartment.setDateSold(Apartment.EMPTY_CASE);
        return apartment;
    }

    private Apartment getApartmentTwo(boolean dateSoldTest){
        Apartment apartment = new Apartment("DUPLEX", 345000, "50, Avenue Général De Gaulle", 75000, "Paris", "15/03/2018", 1);
        if (dateSoldTest) apartment.setDateSold("01/01/2013");
        else apartment.setDateSold(Apartment.EMPTY_CASE);
        return apartment;
    }

    private Apartment getApartmentThree(boolean dateSoldTest){
        Apartment apartment = new Apartment("HOUSE", 545000, "3, Avenue Général De Gaulle", 75000, "Paris", "07/05/2018", 1);
        if (dateSoldTest) apartment.setDateSold("01/03/2018");
        else apartment.setDateSold(Apartment.EMPTY_CASE);
        return apartment;
    }

    private List<Apartment> getApartmentList(boolean dateSoldTest){
        List<Apartment> apartmentList = new ArrayList<>();
        apartmentList.add(getApartmentOne(dateSoldTest));
        apartmentList.add(getApartmentTwo(dateSoldTest));
        apartmentList.add(getApartmentThree(dateSoldTest));
        return apartmentList;
    }

    private List<LineSearch> getLineSearchList(int checkPoint){
        List<LineSearch> lineSearchList = new ArrayList<>();
        lineSearchList.add(new LineSearch("TYPE", "LOFT", null, false, false, false));
        lineSearchList.add(new LineSearch("PRICE", "260000", "700000", true, false, false));
        lineSearchList.add(new LineSearch("SQUARE", "20", "30", true, false, false));
        lineSearchList.add(new LineSearch("ROOM", "2", "7", true, false, false));
        lineSearchList.add(new LineSearch("ADRESS", "", "700000", false, true, false));
        lineSearchList.add(new LineSearch("STREET", "Rue", null, false, false, false));
        lineSearchList.add(new LineSearch("POSTAL CODE", "69001", null, false, false, false));
        lineSearchList.add(new LineSearch("TOWN", "LYON", null, false, false, false));
        lineSearchList.add(new LineSearch("POINT OF INTEREST", "", null, false, true, false));
        lineSearchList.add(new LineSearch("*/", "école marché", null, false, false, false));
        lineSearchList.add(new LineSearch("PICTURES", "", null, false, true, false));
        lineSearchList.add(new LineSearch("NUMBER", "2", null, false, false, false));
        if (checkPoint != NO_CHECK_POINT) {
            lineSearchList.get(checkPoint).setChecked(true);
        }
        return lineSearchList;
    }

    private LineSearch getLineSearchInscription(boolean check){
        return new LineSearch("inscription :", "01/03/2018", "22/04/2018", true, false, check);
    }

    private LineSearch getLineSearchSold(boolean check){
        return new LineSearch("sold :", "01/03/2018", "22/09/2018", true, false, check);
    }

    private ApartmentSelector getApartmentSelector(){
        return new ApartmentSelector("$", "sq ft");
    }

    /**
     *  TEST DIVERSE
     */

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDateTime() throws Exception{
        DateTime dateTime = DateTime.parse("01/01/2018", DateTimeFormat.forPattern("dd/MM/yyyy"));
        assertEquals("01/01/2018", dateTime.toString("dd/MM/yyyy"));
    }

    @Test
    public void testBusinessCountSubstring() throws Exception{
        int count = BusinessApartmentFilters.getCountSubstring("hello","helloslkhellodjladfjhello");
        assertEquals(3, count);
    }

    @Test
    public void testBusinessKeyWordList() throws Exception{
        List<LineSearch> lineSearchList = getLineSearchList(NO_CHECK_POINT);
        String[] result = BusinessApartmentFilters.getKeyWordsList(lineSearchList.get(9).getInformationFrom());
        assertEquals(2, result.length);
    }

    /**
     *  TEST APARTMENT SELECTOR
     */

    @Test
    public void testDateInscriptionInterval() throws Exception{
        List<Apartment> apartmentList = new ArrayList<>(getApartmentList(false));
        List<LineSearch> lineSearchList = new ArrayList<>(getLineSearchList(NO_CHECK_POINT));
        LineSearch lineSearchInscription = getLineSearchInscription(true);
        LineSearch lineSearchSold = getLineSearchSold(false);
        ApartmentSelector apartmentSelector = getApartmentSelector();

        List<Apartment> result = apartmentSelector.getSelectedApartments(apartmentList, lineSearchList, lineSearchInscription, lineSearchSold);
        assertEquals(1, result.size());
        assertEquals("DUPLEX", result.get(0).getType());
    }

    @Test
    public void testDateSoldInterval() throws Exception{
        List<Apartment> apartmentList = new ArrayList<>(getApartmentList(true));
        List<LineSearch> lineSearchList = new ArrayList<>(getLineSearchList(NO_CHECK_POINT));
        LineSearch lineSearchInscription = getLineSearchInscription(false);
        LineSearch lineSearchSold = getLineSearchSold(true);
        ApartmentSelector apartmentSelector = getApartmentSelector();

        List<Apartment> result = apartmentSelector.getSelectedApartments(apartmentList, lineSearchList, lineSearchInscription, lineSearchSold);
        assertEquals(1, result.size());
        assertEquals("HOUSE", result.get(0).getType());
    }

    @Test
    public void testDateInscriptionAndSoldInterval() throws Exception{
        List<Apartment> apartmentList = new ArrayList<>(getApartmentList(false));
        List<LineSearch> lineSearchList = new ArrayList<>(getLineSearchList(NO_CHECK_POINT));
        LineSearch lineSearchInscription = getLineSearchInscription(true);
        LineSearch lineSearchSold = getLineSearchSold(true);
        ApartmentSelector apartmentSelector = getApartmentSelector();

        List<Apartment> result = apartmentSelector.getSelectedApartments(apartmentList, lineSearchList, lineSearchInscription, lineSearchSold);
        assertEquals(0, result.size());
    }

    @Test
    public void testPriceFilter() throws Exception{
        List<Apartment> apartmentList = new ArrayList<>(getApartmentList(false));
        List<LineSearch> lineSearchList = new ArrayList<>(getLineSearchList(1));
        LineSearch lineSearchInscription = getLineSearchInscription(false);
        LineSearch lineSearchSold = getLineSearchSold(false);
        ApartmentSelector apartmentSelector = getApartmentSelector();

        List<Apartment> result = apartmentSelector.getSelectedApartments(apartmentList, lineSearchList, lineSearchInscription, lineSearchSold);
        assertEquals(2, result.size());
        assertEquals("DUPLEX", result.get(0).getType());
    }

    @Test
    public void testTypeFilter() throws Exception{
        List<Apartment> apartmentList = new ArrayList<>(getApartmentList(false));
        List<LineSearch> lineSearchList = new ArrayList<>(getLineSearchList(0));
        lineSearchList.get(0).setInformationFrom("DUP");
        LineSearch lineSearchInscription = getLineSearchInscription(false);
        LineSearch lineSearchSold = getLineSearchSold(false);
        ApartmentSelector apartmentSelector = getApartmentSelector();

        List<Apartment> result = apartmentSelector.getSelectedApartments(apartmentList, lineSearchList, lineSearchInscription, lineSearchSold);
        assertEquals(1, result.size());
        assertEquals("DUPLEX", result.get(0).getType());
    }

    @Test
    public void testTownFilter() throws Exception{
        List<Apartment> apartmentList = new ArrayList<>(getApartmentList(false));
        List<LineSearch> lineSearchList = new ArrayList<>(getLineSearchList(7));
        LineSearch lineSearchInscription = getLineSearchInscription(false);
        LineSearch lineSearchSold = getLineSearchSold(false);
        ApartmentSelector apartmentSelector = getApartmentSelector();

        List<Apartment> result = apartmentSelector.getSelectedApartments(apartmentList, lineSearchList, lineSearchInscription, lineSearchSold);
        assertEquals(1, result.size());
        assertEquals("LOFT", result.get(0).getType());
    }

    @Test
    public void testpointOfInterest() throws Exception{
        List<Apartment> apartmentList = new ArrayList<>(getApartmentList(false));
        apartmentList.get(0).setPoInterest("école");
        apartmentList.get(1).setPoInterest("marché");
        List<LineSearch> lineSearchList = new ArrayList<>(getLineSearchList(9));
        LineSearch lineSearchInscription = getLineSearchInscription(false);
        LineSearch lineSearchSold = getLineSearchSold(false);
        ApartmentSelector apartmentSelector = getApartmentSelector();

        List<Apartment> result = apartmentSelector.getSelectedApartments(apartmentList, lineSearchList, lineSearchInscription, lineSearchSold);
        assertEquals(2, result.size());

        apartmentList.get(0).setPoInterest("écol");
        result = apartmentSelector.getSelectedApartments(apartmentList, lineSearchList, lineSearchInscription, lineSearchSold);
        assertEquals(1, result.size());
    }



}