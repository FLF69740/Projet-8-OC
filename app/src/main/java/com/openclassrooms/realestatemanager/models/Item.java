package com.openclassrooms.realestatemanager.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    private String mInformation;
    private String mTitle;
    private String mUrlPicture;
    private boolean isATitle;
    private boolean isAPicture;

    public Item(String information, String title, String urlPicture, boolean isATitle, boolean isAPicture) {
        mInformation = information;
        mTitle = title;
        mUrlPicture = urlPicture;
        this.isATitle = isATitle;
        this.isAPicture = isAPicture;
    }

    public String getInformation() {
        return mInformation;
    }

    public void setInformation(String information) {
        mInformation = information;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrlPicture() {
        return mUrlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        mUrlPicture = urlPicture;
    }

    public boolean getATitle() {
        return isATitle;
    }

    public void setATitle(Boolean ATitle) {
        isATitle = ATitle;
    }

    public boolean getAPicture() {
        return isAPicture;
    }

    public void setAPicture(Boolean APicture) {
        isAPicture = APicture;
    }

    protected Item(Parcel in) {
        mInformation = in.readString();
        mTitle = in.readString();
        mUrlPicture = in.readString();
        isATitle = in.readByte() != 0x00;
        isAPicture = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mInformation);
        dest.writeString(mTitle);
        dest.writeString(mUrlPicture);
        dest.writeByte((byte) (isATitle ? 0x01 : 0x00));
        dest.writeByte((byte) (isAPicture ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
