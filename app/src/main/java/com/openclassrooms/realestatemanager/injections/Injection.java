package com.openclassrooms.realestatemanager.injections;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.repositories.ApartmentDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static ApartmentDataRepository provideApartmentDataSource(Context context){
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new ApartmentDataRepository(database.mApartmentDao());
    }

    public static UserDataRepository provideUserDataSource(Context context){
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
        return new UserDataRepository(database.mUserDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    //ViewModel creation
    public static ViewModelFactory provideViewModelFactory(Context context){
        ApartmentDataRepository apartmentDataRepository = provideApartmentDataSource(context);
        UserDataRepository userDataRepository = provideUserDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(apartmentDataRepository, userDataRepository, executor);
    }

}
