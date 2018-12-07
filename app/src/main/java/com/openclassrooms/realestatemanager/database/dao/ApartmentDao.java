package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.Apartment;

import java.util.List;

@Dao
public interface ApartmentDao {

    @Query("SELECT * FROM Apartment WHERE mUserId = :userId")
    LiveData<List<Apartment>> getApartments(long userId);

    @Insert long insertApartment(Apartment apartment);

    @Update int updateApartment(Apartment apartment);

}
