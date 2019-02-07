package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
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

    public Apartment(){}

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
    public String getType() {
        return mType;
    }
    public int getPrice() {
        return mPrice;
    }
    public int getDimension() {
        return mDimension;
    }
    public int getRoomNumber() {
        return mRoomNumber;
    }
    public String getDescription() {
        return mDescription;
    }
    public String getUrlPicture() {
        return mUrlPicture;
    }
    public String getAdress() {
        return mAdress;
    }
    public String getTown() {
        return mTown;
    }
    public int getPostalCode() {
        return mPostalCode;
    }
    public String getPoInterest() {
        return mPoInterest;
    }
    public Boolean getSold() {
        return mSold;
    }
    public String getDateInscription() {
        return mDateInscription;
    }
    public String getDateSold() {
        return mDateSold;
    }
    public long getUserId() {
        return mUserId;
    }


    public void setId(long id) {
        mId = id;
    }
    public void setType(String type) {
        mType = type;
    }
    public void setPrice(int price) {
        mPrice = price;
    }
    public void setDimension(int dimension) {
        mDimension = dimension;
    }
    public void setRoomNumber(int roomNumber) {
        mRoomNumber = roomNumber;
    }
    public void setDescription(String description) {
        mDescription = description;
    }
    public void setUrlPicture(String urlPicture) {
        mUrlPicture = urlPicture;
    }
    public void setAdress(String adress) {
        mAdress = adress;
    }
    public void setTown(String town) {
        mTown = town;
    }
    public void setPostalCode(int postalCode) {
        mPostalCode = postalCode;
    }
    public void setPoInterest(String poInterest) {
        mPoInterest = poInterest;
    }
    public void setSold(Boolean sold) {
        mSold = sold;
    }
    public void setDateInscription(String dateInscription) {
        mDateInscription = dateInscription;
    }
    public void setDateSold(String dateSold) {
        mDateSold = dateSold;
    }
    public void setUserId(long userId) {
        mUserId = userId;
    }

    // PROVIDER
    public static Apartment fromContentValues(ContentValues values){
        final Apartment apartment = new Apartment();
        if (values.containsKey("mType")) apartment.setType(values.getAsString("mType"));
        if (values.containsKey("mPrice")) apartment.setPrice(values.getAsInteger("mPrice"));
        if (values.containsKey("mDimension")) apartment.setDimension(values.getAsInteger("mDimension"));
        if (values.containsKey("mRoomNumber")) apartment.setRoomNumber(values.getAsInteger("mRoomNumber"));
        if (values.containsKey("mDescription")) apartment.setDescription(values.getAsString("mDescription"));
        if (values.containsKey("mUrlPicture")) apartment.setUrlPicture(values.getAsString("mUrlPicture"));
        if (values.containsKey("mAdress")) apartment.setAdress(values.getAsString("mAdress"));
        if (values.containsKey("mTown")) apartment.setTown(values.getAsString("mTown"));
        if (values.containsKey("mPostalCode")) apartment.setPostalCode(values.getAsInteger("mPostalCode"));
        if (values.containsKey("mPoInterest")) apartment.setPoInterest(values.getAsString("mPoInterest"));
        if (values.containsKey("mSold")) apartment.setSold(values.getAsBoolean("mSold"));
        if (values.containsKey("mDateInscription")) apartment.setDateInscription(values.getAsString("mDateInscription"));
        if (values.containsKey("mDateSold")) apartment.setDateSold(values.getAsString("mDateSold"));
        if (values.containsKey("mUserId")) apartment.setUserId(values.getAsLong("mUserId"));
        return apartment;
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
