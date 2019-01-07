package com.openclassrooms.realestatemanager.apartmentdetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int mSize;
    private String mListString;

    public PageAdapter(FragmentManager fm, int size, String listString) {
        super(fm);
        this.mSize = size;
        this.mListString = listString;
    }

    @Override
    public Fragment getItem(int i) {
        return PhotoPageFragment.newInstance(mListString, i);
    }

    @Override
    public int getCount() {
        return mSize;
    }
}
