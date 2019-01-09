package com.openclassrooms.realestatemanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.ApartmentDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;
import java.util.List;
import java.util.concurrent.Executor;

public class ListingViewModel extends ViewModel {

    // REPOSITORIES
    private final ApartmentDataRepository mApartmentDataSource;
    private final UserDataRepository mUserDataSource;
    private final Executor mExecutor;

    @Nullable private LiveData<User> mCurrentUser;

    public ListingViewModel(ApartmentDataRepository apartmentDataRepository, UserDataRepository userDataRepository, Executor executor){
        this.mApartmentDataSource = apartmentDataRepository;
        this.mUserDataSource = userDataRepository;
        this.mExecutor = executor;
    }

    public void init(long userId){
        if (this.mCurrentUser != null){
            return;
        }
        this.getUser(userId);
    }

    /**
     *  USER
     */

    //GET
    public LiveData<User> getUser(long userId){
     //   return this.mCurrentUser;
        return mUserDataSource.getUser(userId);
    }

    //CREATE
    public void createUser(User user){
        mExecutor.execute(()-> mUserDataSource.createUser(user));
    }

    //UPDATE
    public void updateUser(User user){
        mExecutor.execute(()-> mUserDataSource.updateUser(user));
    }

    /**
     *  APARTMENT
     */

    //GET
    public LiveData<List<Apartment>> getApartments(long userId){
        return mApartmentDataSource.getApartments(userId);
    }

    //CREATE
    public void createApartment(Apartment apartment){
        mExecutor.execute(()->mApartmentDataSource.createApartment(apartment));
    }

    //UPDATE
    public void updateApartment(Apartment apartment){
        mExecutor.execute(()->mApartmentDataSource.updateApartment(apartment));
    }
}
