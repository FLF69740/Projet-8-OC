package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class LineSearch implements Parcelable {

    public static final String EMPTY_CASE = "EMPTY_CASE";
    public static final String BLANK = "_____";

    @PrimaryKey(autoGenerate = true) private long mId;
    private String mSectionName;
    private String mInformationFrom;
    private String mInformationTo;
    private boolean mIsInformationTo;
    private boolean isATitle;
    private boolean isChecked;

    public LineSearch(String sectionName, String informationFrom, String informationTo, boolean isInformationTo, boolean isATitle, boolean isChecked) {
        mSectionName = sectionName;
        mInformationFrom = informationFrom;
        mInformationTo = informationTo;
        this.mIsInformationTo = isInformationTo;
        this.isATitle = isATitle;
        this.isChecked = isChecked;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public boolean isInformationTo() {
        return mIsInformationTo;
    }

    public void setInformationTo(boolean informationTo) {
        mIsInformationTo = informationTo;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public void setSectionName(String sectionName) {
        mSectionName = sectionName;
    }

    public String getInformationFrom() {
        return mInformationFrom;
    }

    public void setInformationFrom(String informationFrom) {
        mInformationFrom = informationFrom;
    }

    public String getInformationTo() {
        return mInformationTo;
    }

    public void setInformationTo(String informationTo) {
        mInformationTo = informationTo;
    }

    public boolean isATitle() {
        return isATitle;
    }

    public void setATitle(boolean ATitle) {
        isATitle = ATitle;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    protected LineSearch(Parcel in) {
        mSectionName = in.readString();
        mInformationFrom = in.readString();
        mInformationTo = in.readString();
        mIsInformationTo = in.readByte() != 0x00;
        isATitle = in.readByte() != 0x00;
        isChecked = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSectionName);
        dest.writeString(mInformationFrom);
        dest.writeString(mInformationTo);
        dest.writeByte((byte) (mIsInformationTo ? 0x01 : 0x00));
        dest.writeByte((byte) (isATitle ? 0x01 : 0x00));
        dest.writeByte((byte) (isChecked ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LineSearch> CREATOR = new Parcelable.Creator<LineSearch>() {
        @Override
        public LineSearch createFromParcel(Parcel in) {
            return new LineSearch(in);
        }

        @Override
        public LineSearch[] newArray(int size) {
            return new LineSearch[size];
        }
    };
}
