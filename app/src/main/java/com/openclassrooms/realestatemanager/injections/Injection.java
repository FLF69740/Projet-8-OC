package com.openclassrooms.realestatemanager.injections;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.database.SearchFilterDatabase;
import com.openclassrooms.realestatemanager.repositories.ApartmentDataRepository;
import com.openclassrooms.realestatemanager.repositories.LineSearchDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    /**
     *  APARTMENT AND USER INJECTION
     */

    public static ApartmentDataRepository provideApartmentDataSource(Context context){
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new ApartmentDataRepository(database.mApartmentDao());
    }

    public static UserDataRepository provideUserDataSource(Context context){
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new UserDataRepository(database.mUserDao());
    }

    //ViewModel creation
    public static ViewModelFactory provideViewModelFactory(Context context){
        ApartmentDataRepository apartmentDataRepository = provideApartmentDataSource(context);
        UserDataRepository userDataRepository = provideUserDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(apartmentDataRepository, userDataRepository, executor);
    }

    /**
     *  SEARCH FILTER INJECTION
     */

    public static LineSearchDataRepository provideSearchDataSource(Context context){
        SearchFilterDatabase database = SearchFilterDatabase.getInstance(context);
        return new LineSearchDataRepository(database.mSearchFilterDao());
    }

    //ViewModel creation
    public static ViewModelSearchFactory provideViewModelSearchFactory(Context context){
        LineSearchDataRepository lineSearchDataRepository = provideSearchDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelSearchFactory(lineSearchDataRepository, executor);
    }

}
