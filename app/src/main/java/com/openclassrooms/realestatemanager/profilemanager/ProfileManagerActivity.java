package com.openclassrooms.realestatemanager.profilemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;

public class ProfileManagerActivity extends BaseActivity implements ProfileManagerFragment.ItemUserClickedListener, ProfileManagerDetailFragment.ActiveUserClickedListener {

    public static final String BUNDLE_PROFILE_USER = "BUNDLE_PROFILE_USER";
    public static final String BUNDLE_PROFILE_USER_ID = "BUNDLE_PROFILE_USER_ID";

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

    /**
     *  RECYCLERVIEW CLICK
     */

    @Override
    public void itemUserClicked(View view, User user, String adapterPosition) {
        mUser = user;
        mUserId = user.getId();
        mAdapterPosition = adapterPosition;
        ProfileManagerDetailFragment profileManagerDetailFragment = (ProfileManagerDetailFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout());
        if (profileManagerDetailFragment != null && profileManagerDetailFragment.isVisible()){
            profileManagerDetailFragment.updateFragmentScreen(user, user.getId());
        } else {
            Intent intent = new Intent(this, ProfileManagerDetailActivity.class);
            intent.putExtra(BUNDLE_PROFILE_USER, user);
            intent.putExtra(BUNDLE_PROFILE_USER_ID, user.getId());
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


    @Override
    public void activeUserClicked(View view, long userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_ID, MODE_PRIVATE);
        sharedPreferences.edit().putLong(BUNDLE_KEY_ACTIVE_USER, userId).apply();
        this.updateFragment();
        this.updateHeader(mUser);
    }
}
