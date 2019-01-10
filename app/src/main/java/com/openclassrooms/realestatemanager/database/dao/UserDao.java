package com.openclassrooms.realestatemanager.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    LiveData<List<User>> getUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE) void createUser(User user);

    @Query("SELECT * FROM User WHERE mId = :userId")
    LiveData<User> getUser(long userId);

    @Update int updateUser(User user);

}
