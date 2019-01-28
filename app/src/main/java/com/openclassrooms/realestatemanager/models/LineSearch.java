package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LineSearch {

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
}
