package com.openclassrooms.realestatemanager.profilemanager;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;

public class ProfileManagerDetailActivity extends BaseActivity implements ProfileManagerDetailFragment.ActiveUserClickedListener {

    @Override
    protected Fragment getFirstFragment() {
        User user = getIntent().getParcelableExtra(ProfileManagerActivity.BUNDLE_PROFILE_USER);
        long userId = getIntent().getLongExtra(ProfileManagerActivity.BUNDLE_PROFILE_USER_ID, 0);
        return ProfileManagerDetailFragment.newInstance(user, userId);
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


    @Override
    public void activeUserClicked(View view, long userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_ID, MODE_PRIVATE);
        sharedPreferences.edit().putLong(BUNDLE_KEY_ACTIVE_USER, userId).apply();
        this.updateFragment();
    }
}
