package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.ApartmentDao;
import com.openclassrooms.realestatemanager.models.Apartment;

import java.util.List;

public class ApartmentDataRepository {

    private final ApartmentDao mApartmentDao;

    public ApartmentDataRepository(ApartmentDao apartmentDao){
        this.mApartmentDao = apartmentDao;
    }

    // GET APARTMENTS
    public LiveData<List<Apartment>> getApartments(long userId){
        return this.mApartmentDao.getApartments(userId);
    }

    // CREATE APARTMENT
    public void createApartment(Apartment apartment){
        mApartmentDao.insertApartment(apartment);
    }

    // UPDATE APARTMENT
    public void updateApartment(Apartment apartment){
        mApartmentDao.updateApartment(apartment);
    }
}
