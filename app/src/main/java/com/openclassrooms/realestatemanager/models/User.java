package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true) private Long mId;
    private String mUsername;
    private String mUrlPicture;
    private String mUserDescription;

    public User(String username, String urlPicture, String userDescription) {
        this.mUsername = username;
        this.mUrlPicture = urlPicture;
        this.mUserDescription = userDescription;
    }

    /**
     *  GETTER AND SETTER
     */

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getUrlPicture() {
        return mUrlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        mUrlPicture = urlPicture;
    }

    public String getUserDescription() {
        return mUserDescription;
    }

    public void setUserDescription(String userDescription) {
        mUserDescription = userDescription;
    }
}
