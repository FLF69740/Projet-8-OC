package com.openclassrooms.realestatemanager.profilemanager;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;

public class ProfileManagerDetailActivity extends BaseActivity {

    @Override
    protected Fragment getFirstFragment() {
        User user = getIntent().getParcelableExtra(ProfileManagerActivity.BUNDLE_PROFILE_USER);
        return ProfileManagerDetailFragment.newInstance(user);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_profile_manager_detail;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_profile_manager_detail;
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
