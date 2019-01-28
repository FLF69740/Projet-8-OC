package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.SearchFilterDao;
import com.openclassrooms.realestatemanager.models.LineSearch;

import java.util.List;

public class LineSearchDataRepository {

    private final SearchFilterDao mSearchFilterDao;

    public LineSearchDataRepository(SearchFilterDao searchFilterDao) {
        this.mSearchFilterDao = searchFilterDao;
    }

    // GET LINES SEARCH
    public LiveData<List<LineSearch>> getLinesSearch(){
        return this.mSearchFilterDao.getLinesSearch();
    }

    // CREATE LINE SEARCH
    public void createLineSearch(LineSearch lineSearch){
        mSearchFilterDao.createLineSearch(lineSearch);
    }

    // UPDATE LINE SEARCH
    public void updateLineSearch(LineSearch lineSearch){
        mSearchFilterDao.updateLineSearch(lineSearch);
    }


}
