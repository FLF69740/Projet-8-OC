package com.openclassrooms.realestatemanager.models;

public class Item {

    private String mInformation;
    private String mTitle;
    private String mUrlPicture;
    private Boolean isATitle;
    private Boolean isAPicture;

    public Item(String information, String title, String urlPicture, Boolean isATitle, Boolean isAPicture) {
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

    public Boolean getATitle() {
        return isATitle;
    }

    public void setATitle(Boolean ATitle) {
        isATitle = ATitle;
    }

    public Boolean getAPicture() {
        return isAPicture;
    }

    public void setAPicture(Boolean APicture) {
        isAPicture = APicture;
    }
}
