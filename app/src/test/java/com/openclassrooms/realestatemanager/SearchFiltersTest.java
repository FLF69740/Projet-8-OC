package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.apartmentfilters.ApartmentSelector;
import com.openclassrooms.realestatemanager.apartmentfilters.BusinessApartmentFilters;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.LineSearch;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchFiltersTest {

    private Apartment mApartmentOne = new Apartment("LOFT", 250000, "1, Rue de la liberté", 69001, "LYON", "10/01/2018",1);
    private Apartment mApartmentTwo = new Apartment("DUPLEX", 345000, "50, Avenue Général De Gaulle", 75000, "Paris", "15/03/2018", 1);
    private Apartment mApartmentThree = new Apartment("HOUSE", 545000, "3, Avenue Général De Gaulle", 75000, "Paris", "07/05/2018", 1);
    private LineSearch mLineSearchInscription = new LineSearch("incription :", "01/01/2018", "22/04/2018", true, false, true);
    private LineSearch mLineSearchSold = new LineSearch("sold :", "01/01/2018", "22/09/2018", true, false, true);
    private LineSearch mType = new LineSearch("TYPE", "LOFT", null, false, false, false);
    private LineSearch mPrice = new LineSearch("PRICE", "260000", "700000", true, false, false);
    private LineSearch mSquare = new LineSearch("SQUARE", "20", "30", true, false, false);
    private LineSearch mRoomm = new LineSearch("ROOM", "2", "7", true, false, false);
    private LineSearch mTitleAdress = new LineSearch("ADRESS", "", "700000", false, true, false);
    private LineSearch mStreet = new LineSearch("STREET", "Rue", null, false, false, false);
    private LineSearch mPostal = new LineSearch("POSTAL CODE", "69001", null, false, false, false);
    private LineSearch mTown = new LineSearch("TOWN", "LYON", null, false, false, false);
    private LineSearch mPOTitle = new LineSearch("POINT OF INTEREST", "", null, false, true, false);
    private LineSearch mPoSingle = new LineSearch("*/", "école marché", null, false, false, false);
    private LineSearch mPicTitle = new LineSearch("PICTURES", "", null, false, true, false);
    private LineSearch mPicture = new LineSearch("NUMBER", "2", null, false, false, false);
    private List<LineSearch> mLineSearchList;
    private List<Apartment> mApartmentList;
    private ApartmentSelector mApartmentSelector;

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
        String[] result = BusinessApartmentFilters.getKeyWordsList(mPoSingle.getInformationFrom());
        assertEquals(2, result.length);
    }

    /**
     *  TEST
     */

    @Before
    public void createApartmentList() throws Exception{
        mApartmentList = new ArrayList<>();
        mApartmentList.add(mApartmentOne);
        mApartmentList.add(mApartmentTwo);
        mApartmentList.add(mApartmentThree);
        mApartmentSelector = new ApartmentSelector("$", "sq ft");
        mLineSearchList = new ArrayList<>();
        mLineSearchList.add(mType);
        mLineSearchList.add(mPrice);
        mLineSearchList.add(mSquare);
        mLineSearchList.add(mRoomm);
        mLineSearchList.add(mTitleAdress);
        mLineSearchList.add(mStreet);
        mLineSearchList.add(mPostal);
        mLineSearchList.add(mTown);
        mLineSearchList.add(mPOTitle);
        mLineSearchList.add(mPoSingle);
        mLineSearchList.add(mPicTitle);
        mLineSearchList.add(mPicture);
    }

    @Test
    public void testDateInscriptionInterval() throws Exception{
        mLineSearchSold.setChecked(false);
        mLineSearchInscription.setInformationFrom("01/02/2018");
        mLineSearchInscription.setInformationTo("04/09/2018");
        List<Apartment> result;
        result = mApartmentSelector.getSelectedApartments(mApartmentList,mLineSearchList, mLineSearchInscription, mLineSearchSold);
        assertEquals(2, result.size());
        assertEquals("DUPLEX", result.get(0).getType());
    }

    @Test
    public void testDateSoldInterval() throws Exception{
        mLineSearchInscription.setChecked(false);
        List<Apartment> result;
        result = mApartmentSelector.getSelectedApartments(mApartmentList,mLineSearchList, mLineSearchInscription, mLineSearchSold);
        assertEquals(1, result.size());
        assertEquals("HOUSE", result.get(0).getType());
    }

    @Test
    public void testDateInscriptionAndSoldInterval() throws Exception{
        mLineSearchSold.setInformationFrom("01/04/2018");
        List<Apartment> result;
        result = mApartmentSelector.getSelectedApartments(mApartmentList,mLineSearchList, mLineSearchInscription, mLineSearchSold);
        assertEquals(0, result.size());
    }

    @Test
    public void testPriceFilter() throws Exception{
        mLineSearchList.get(1).setChecked(true);
        List<Apartment> result;
        result = mApartmentSelector.getSelectedApartments(mApartmentList,mLineSearchList, mLineSearchInscription, mLineSearchSold);
        assertEquals(1, result.size());
        assertEquals("DUPLEX", result.get(0).getType());
    }

    @Test
    public void testTypeFilter() throws Exception{
        mLineSearchList.get(0).setChecked(true);
        mLineSearchList.get(0).setInformationFrom("DUP");
        List<Apartment> result;
        result = mApartmentSelector.getSelectedApartments(mApartmentList,mLineSearchList, mLineSearchInscription, mLineSearchSold);
        assertEquals(1, result.size());
        assertEquals("DUPLEX", result.get(0).getType());
    }

    @Test
    public void testTownFilter() throws Exception{
        mLineSearchList.get(7).setChecked(true);
        List<Apartment> result;
        result = mApartmentSelector.getSelectedApartments(mApartmentList,mLineSearchList, mLineSearchInscription, mLineSearchSold);
        assertEquals(1, result.size());
        assertEquals("LOFT", result.get(0).getType());
    }

    @Test
    public void testpointOfInterest() throws Exception{
        mApartmentList.get(0).setPoInterest("école");
        mApartmentList.get(1).setPoInterest("marché");
        mLineSearchList.get(9).setChecked(true);
        List<Apartment> result;
        result = mApartmentSelector.getSelectedApartments(mApartmentList,mLineSearchList, mLineSearchInscription, mLineSearchSold);
        assertEquals(2, result.size());

        mApartmentList.get(0).setPoInterest("écol");
        result = mApartmentSelector.getSelectedApartments(mApartmentList,mLineSearchList, mLineSearchInscription, mLineSearchSold);
        assertEquals(1, result.size());
    }



}
