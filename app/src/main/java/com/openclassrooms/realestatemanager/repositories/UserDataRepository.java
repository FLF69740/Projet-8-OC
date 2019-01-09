package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.UserDao;
import com.openclassrooms.realestatemanager.models.User;

public class UserDataRepository {

    private final UserDao mUserDao;

    public UserDataRepository(UserDao userDao){
        this.mUserDao = userDao;
    }

    // GET USER
    public LiveData<User> getUser(long userId){
        return this.mUserDao.getUser(userId);
    }

    // CREATE USER
    public void createUser(User user){
        mUserDao.createUser(user);
    }

    // UPDATE USER
    public void updateUser(User user){
        mUserDao.updateUser(user);
    }
}
