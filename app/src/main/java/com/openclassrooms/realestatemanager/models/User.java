package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class User implements Parcelable {

    public static final String EMPTY_CASE = "EMPTY_CASE";

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

    /**
     *  Parcel
     */

    protected User(Parcel in) {
        mUsername = in.readString();
        mUrlPicture = in.readString();
        mUserDescription = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUsername);
        dest.writeString(mUrlPicture);
        dest.writeString(mUserDescription);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
