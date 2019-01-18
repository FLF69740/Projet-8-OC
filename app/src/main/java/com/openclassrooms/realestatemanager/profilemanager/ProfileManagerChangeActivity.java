package com.openclassrooms.realestatemanager.profilemanager;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;

public class ProfileManagerChangeActivity extends BaseActivity implements ProfileManagerChangeFragment.ItemChangeDetailUserClickedListener{

    @Override
    protected Fragment getFirstFragment() {
        User user = getIntent().getParcelableExtra(ProfileManagerActivity.BUNDLE_PROFILE_USER);
        Long userId = getIntent().getLongExtra(ProfileManagerActivity.BUNDLE_PROFILE_USER_ID, 0);
        return ProfileManagerChangeFragment.newInstance(user, userId);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_profile_manager_change;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_profile_manager_change;
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
    public void itemClicked(View view, User user) {
        BitmapStorage.deleteTempCameraCapture(this);
        updateUser(user);
        Intent intent = new Intent(this, ProfileManagerActivity.class);
        intent.putExtra(BaseActivity.BUNDLE_USERLIST_TO_PROFILEMANAGER_ACTIVITY, user);
        startActivity(intent);
        finish();
    }
}
