package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "mId", childColumns = "mUserId"))
public class Apartment implements Parcelable {

    public static final String EMPTY_CASE = "EMPTY";

    @PrimaryKey(autoGenerate = true) private long mId;
    private String mType;
    private int mPrice;
    private int mDimension;
    private int mRoomNumber;
    private String mDescription;
    private String mUrlPicture;
    private String mAdress;
    private String mTown;
    private int mPostalCode;
    private String mPoInterest;
    private Boolean mSold;
    private String mDateInscription;
    private String mDateSold;
    private long mUserId;

    public Apartment(String type, int price, String adress, int postalCode, String town, String dateInscription, long userId) {
        this.mType = type;
        this.mPrice = price;
        this.mAdress = adress;
        this.mPostalCode = postalCode;
        this.mTown = town;
        this.mDateInscription = dateInscription;
        this.mUserId = userId;
        this.mDimension = 0;
        this.mRoomNumber = 0;
        this.mDescription = EMPTY_CASE;
        this.mUrlPicture = EMPTY_CASE;
        this.mPoInterest = EMPTY_CASE;
        this.mSold = false;
        this.mDateSold = EMPTY_CASE;
    }

    /**
     *  PARCELABLE
     */

    protected Apartment(Parcel in) {
        mId = in.readLong();
        mType = in.readString();
        mPrice = in.readInt();
        mDimension = in.readInt();
        mRoomNumber = in.readInt();
        mDescription = in.readString();
        mUrlPicture = in.readString();
        mAdress = in.readString();
        mTown = in.readString();
        mPostalCode = in.readInt();
        mPoInterest = in.readString();
        byte mSoldVal = in.readByte();
        mSold = mSoldVal == 0x02 ? null : mSoldVal != 0x00;
        mDateInscription = in.readString();
        mDateSold = in.readString();
        mUserId = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mType);
        dest.writeInt(mPrice);
        dest.writeInt(mDimension);
        dest.writeInt(mRoomNumber);
        dest.writeString(mDescription);
        dest.writeString(mUrlPicture);
        dest.writeString(mAdress);
        dest.writeString(mTown);
        dest.writeInt(mPostalCode);
        dest.writeString(mPoInterest);
        if (mSold == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (mSold ? 0x01 : 0x00));
        }
        dest.writeString(mDateInscription);
        dest.writeString(mDateSold);
        dest.writeLong(mUserId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Apartment> CREATOR = new Parcelable.Creator<Apartment>() {
        @Override
        public Apartment createFromParcel(Parcel in) {
            return new Apartment(in);
        }

        @Override
        public Apartment[] newArray(int size) {
            return new Apartment[size];
        }
    };

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

    public String getUrlPicture() {
        return mUrlPicture;
    }

    public void setUrlPicture(String urlPicture) {
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

    public String getPoInterest() {
        return mPoInterest;
    }

    public void setPoInterest(String poInterest) {
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

    @Override
    public String toString() {
        return "Apartment{" +
                "mId=" + mId +
                ", mType='" + mType + '\'' +
                ", mPrice=" + mPrice +
                ", mDimension=" + mDimension +
                ", mRoomNumber=" + mRoomNumber +
                ", mDescription='" + mDescription + '\'' +
                ", mUrlPicture='" + mUrlPicture + '\'' +
                ", mAdress='" + mAdress + '\'' +
                ", mTown='" + mTown + '\'' +
                ", mPostalCode=" + mPostalCode +
                ", mPoInterest='" + mPoInterest + '\'' +
                ", mSold=" + mSold +
                ", mDateInscription='" + mDateInscription + '\'' +
                ", mDateSold='" + mDateSold + '\'' +
                ", mUserId=" + mUserId +
                '}';
    }
}
