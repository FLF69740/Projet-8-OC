package com.openclassrooms.realestatemanager.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.repositories.LineSearchDataRepository;
import com.openclassrooms.realestatemanager.viewmodel.ListingViewModel;
import com.openclassrooms.realestatemanager.repositories.ApartmentDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ApartmentDataRepository mApartmentDataSource;
    private final UserDataRepository mUserDataSource;
    private final Executor mExecutor;

    public ViewModelFactory(ApartmentDataRepository apartmentDataRepository, UserDataRepository userDataRepository, Executor executor){
        this.mApartmentDataSource = apartmentDataRepository;
        this.mUserDataSource = userDataRepository;
        this.mExecutor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListingViewModel.class)){
            return (T) new ListingViewModel(mApartmentDataSource, mUserDataSource, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
