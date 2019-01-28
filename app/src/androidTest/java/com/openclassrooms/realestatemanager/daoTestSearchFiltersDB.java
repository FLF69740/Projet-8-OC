package com.openclassrooms.realestatemanager;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.database.SearchFilterDatabase;
import com.openclassrooms.realestatemanager.models.LineSearch;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class daoTestSearchFiltersDB {

    private SearchFilterDatabase mDatabase;
    private static LineSearch LINE_SEARCH_DEMO_1 = new LineSearch("INSCRIPTION","25/01/2018", "12/02/2018", true, false, true);
    private static LineSearch LINE_SEARCH_DEMO_2 = new LineSearch("INFORMATIONS",LineSearch.EMPTY_CASE, LineSearch.EMPTY_CASE, false, true, false);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception{
        this.mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), SearchFilterDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception{
        this.mDatabase.close();
    }

    /**
     *  TEST DATABASE
     */

    @Test
    public void getListOfLines() throws Exception{
        this.mDatabase.mSearchFilterDao().createLineSearch(LINE_SEARCH_DEMO_1);
        this.mDatabase.mSearchFilterDao().createLineSearch(LINE_SEARCH_DEMO_2);

        List<LineSearch> lineSearchList = LiveDataTestUtil.getValue(this.mDatabase.mSearchFilterDao().getLinesSearch());

        assertEquals(lineSearchList.size(), 2);
        assertEquals(lineSearchList.get(0).getSectionName(), "INSCRIPTION");
        assertTrue(lineSearchList.get(1).isATitle());
    }

    @Test
    public void insertAndUpdateLineSearch() throws Exception{
        this.mDatabase.mSearchFilterDao().createLineSearch(LINE_SEARCH_DEMO_1);

        List<LineSearch> lineSearchList = LiveDataTestUtil.getValue(this.mDatabase.mSearchFilterDao().getLinesSearch());

        assertEquals(lineSearchList.get(0).getInformationFrom(), "25/01/2018");

        lineSearchList.get(0).setInformationFrom("27/01/2018");
        this.mDatabase.mSearchFilterDao().updateLineSearch(lineSearchList.get(0));
        List<LineSearch> lineSearchListVerif = LiveDataTestUtil.getValue(this.mDatabase.mSearchFilterDao().getLinesSearch());

        assertEquals(lineSearchListVerif.get(0).getInformationFrom(), "27/01/2018");

    }
















}
