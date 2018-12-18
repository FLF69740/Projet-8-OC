package com.openclassrooms.realestatemanager.apartmentdetail;

import android.support.v4.app.Fragment;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;

public class SecondActivity extends BaseActivity {

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";

    @Override
    protected Fragment getFirstFragment() {
        Apartment apartment = getIntent().getParcelableExtra(BUNDLE_KEY_APARTMENT);

        return SecondFragment.newInstance(apartment);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_second;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_second;
    }

    @Override
    protected Fragment getSecondFragment() {
        return null;
    }

    @Override
    protected int getSecondFragmentLayout() {
        return 0;
    }

    @Override
    protected boolean isAChildActivity() {
        return true;
    }
}
