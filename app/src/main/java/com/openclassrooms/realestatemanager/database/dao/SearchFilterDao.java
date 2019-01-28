package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.LineSearch;

import java.util.List;

@Dao
public interface SearchFilterDao {

    @Query("SELECT * FROM LineSearch")
    LiveData<List<LineSearch>> getLinesSearch();

    @Insert
    long createLineSearch(LineSearch lineSearch);

    @Update
    int updateLineSearch(LineSearch lineSearch);

}
