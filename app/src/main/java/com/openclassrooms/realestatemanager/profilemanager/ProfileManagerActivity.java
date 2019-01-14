package com.openclassrooms.realestatemanager.profilemanager;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;

public class ProfileManagerActivity extends BaseActivity implements ProfileManagerFragment.ItemUserClickedListener {

    public static final String BUNDLE_PROFILE_USER = "BUNDLE_PROFILE_USER";

    @Override
    protected Fragment getFirstFragment() {
        int activeUser = getIntent().getIntExtra(BUNDLE_KEY_PREF_INT_USER, 0);
        return ProfileManagerFragment.newInstance(activeUser);
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

    /**
     *  RECYCLERVIEW CLICK
     */

    @Override
    public void itemUserClicked(View view, User user, String adapterPosition) {
        ProfileManagerDetailFragment profileManagerDetailFragment = (ProfileManagerDetailFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout());
        if (profileManagerDetailFragment != null && profileManagerDetailFragment.isVisible()){
            profileManagerDetailFragment.updateFragmentScreen(user);
        } else {
            Intent intent = new Intent(this, ProfileManagerDetailActivity.class);
            intent.putExtra(BUNDLE_PROFILE_USER, user);
            startActivity(intent);
        }
    }

    /**
     *  AFTER CREATE NEW USER
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (CREATE_USER_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            if (data != null) {
                String username = data.getStringExtra(UserCreationActivity.BUNDLE_NEW_USER_NAME);

                User user = new User(username, User.EMPTY_CASE, User.EMPTY_CASE);

                createUser(user);
            }
        }
    }
}
