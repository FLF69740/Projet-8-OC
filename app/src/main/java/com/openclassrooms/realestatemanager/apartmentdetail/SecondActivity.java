package com.openclassrooms.realestatemanager.apartmentdetail;

import android.support.v4.app.Fragment;
import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;

public class SecondActivity extends BaseActivity {

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";

    @Override
    protected Fragment getFirstFragment() {
        Apartment apartment = getIntent().getParcelableExtra(BUNDLE_KEY_APARTMENT);
        User user = getIntent().getParcelableExtra(BUNDLE_KEY_USER);
        mApartment = apartment;
        mUser = user;
        return SecondFragment.newInstance(apartment, user);
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
