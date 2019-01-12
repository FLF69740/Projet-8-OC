package com.openclassrooms.realestatemanager.profilemanager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;

public class ProfileManagerActivity extends BaseActivity implements ProfileManagerFragment.ItemUserClickedListener {

    @Override
    protected Fragment getFirstFragment() {
        return ProfileManagerFragment.newInstance();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_profile_manager;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_profile_manager;
    }

    @Override
    protected Fragment getSecondFragment() {
        return new ProfileManagerDetailFragment();
    }

    @Override
    protected int getSecondFragmentLayout() {
        return R.id.frame_layout_profile_manager_detail;
    }

    @Override
    protected boolean isAChildActivity() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getUsers();
    }

    @Override
    public void itemUserClicked(View view, User user, String adapterPosition) {
        Toast.makeText(this, user.getUsername(), Toast.LENGTH_SHORT).show();
    }
}
