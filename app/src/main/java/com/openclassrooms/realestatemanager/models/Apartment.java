package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import java.util.List;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "mId", childColumns = "mUserId"))
public class Apartment {

    @PrimaryKey(autoGenerate = true) private long mId;
    private String mType;
    private int mPrice;
    private int mDimension;
    private int mRoomNumber;
    private String mDescription;
    private List<String> mUrlPicture;
    private String mAdress;
    private String mTown;
    private int mPostalCode;
    private List<String> mPoInterest;
    private Boolean mSold;
    private String mDateInscription;
    private String mDateSold;
    private long mUserId;

    public Apartment(long id, String type, int price, String adress, int postalCode, String town, String dateInscription, long userId) {
        this.mId = id;
        this.mType = type;
        this.mPrice = price;
        this.mAdress = adress;
        this.mPostalCode = postalCode;
        this.mTown = town;
        this.mDateInscription = dateInscription;
        this.mUserId = userId;
    }

    /**
     *  GETTER AND SETTER
     */

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getDimension() {
        return mDimension;
    }

    public void setDimension(int dimension) {
        mDimension = dimension;
    }

    public int getRoomNumber() {
        return mRoomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        mRoomNumber = roomNumber;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<String> getUrlPicture() {
        return mUrlPicture;
    }

    public void setUrlPicture(List<String> urlPicture) {
        mUrlPicture = urlPicture;
    }

    public String getAdress() {
        return mAdress;
    }

    public void setAdress(String adress) {
        mAdress = adress;
    }

    public String getTown() {
        return mTown;
    }

    public void setTown(String town) {
        mTown = town;
    }

    public int getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(int postalCode) {
        mPostalCode = postalCode;
    }

    public List<String> getPoInterest() {
        return mPoInterest;
    }

    public void setPoInterest(List<String> poInterest) {
        mPoInterest = poInterest;
    }

    public Boolean getSold() {
        return mSold;
    }

    public void setSold(Boolean sold) {
        mSold = sold;
    }

    public String getDateInscription() {
        return mDateInscription;
    }

    public void setDateInscription(String dateInscription) {
        mDateInscription = dateInscription;
    }

    public String getDateSold() {
        return mDateSold;
    }

    public void setDateSold(String dateSold) {
        mDateSold = dateSold;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        mUserId = userId;
    }
}
