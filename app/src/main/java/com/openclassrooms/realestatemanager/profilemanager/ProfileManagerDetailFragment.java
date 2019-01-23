package com.openclassrooms.realestatemanager.profilemanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.Controller.BaseActivity.BUNDLE_KEY_ACTIVE_DIMENSION;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.BUNDLE_KEY_ACTIVE_USER;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.SHARED_ID;


public class ProfileManagerDetailFragment extends Fragment {

    @BindView(R.id.manager_detail_name_user)TextView mTextViewNameUser;
    @BindView(R.id.manager_detail_photo_user)ImageView mImageViewPhotoUser;
    @BindView(R.id.manager_detail_description_user)TextView mTextViewDescriptionUser;
    @BindView(R.id.manager_detail_active_profile)ImageView mImageViewActiveProfile;

    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";
    public static final String BUNDLE_USER_ID = "BUNDLE_USER_ID";

    private View mView;
    private User mUser;

    public ProfileManagerDetailFragment() {}

    public static ProfileManagerDetailFragment newInstance(){
        return new ProfileManagerDetailFragment();
    }

    public static ProfileManagerDetailFragment newInstance(User user, Long userId){
        ProfileManagerDetailFragment profileManagerDetailFragment = new ProfileManagerDetailFragment();
        Bundle args = new Bundle(2);
        args.putParcelable(BUNDLE_KEY_USER, user);
        args.putLong(BUNDLE_USER_ID, userId);
        profileManagerDetailFragment.setArguments(args);

        return profileManagerDetailFragment;
    }

    /**
     *  VIEW
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_profile_manager_detail, container, false);
        ButterKnife.bind(this, mView);

        if (getArguments() != null) {
            if (getArguments().getParcelable(BUNDLE_KEY_USER) != null && getArguments().getLong(BUNDLE_USER_ID) != 0) {
                updateFragmentScreen(getArguments().getParcelable(BUNDLE_KEY_USER), getArguments().getLong(BUNDLE_USER_ID));
            }
        }

        return mView;
    }

    public void updateFragmentScreen(User user, Long userId) {
        mUser = user;
        mUser.setId(userId);
        mTextViewNameUser.setText(user.getUsername());

        if (userId == getActivity().getSharedPreferences(SHARED_ID, Context.MODE_PRIVATE).getLong(BUNDLE_KEY_ACTIVE_USER, 0)){
            mImageViewActiveProfile.setVisibility(View.VISIBLE);
        }else {
            mImageViewActiveProfile.setVisibility(View.INVISIBLE);
        }

        if (!user.getUserDescription().equals(User.EMPTY_CASE)){
            mTextViewDescriptionUser.setText(user.getUserDescription());
        } else {
            mTextViewDescriptionUser.setText(mView.getContext().getString(R.string.nav_drawer_item_about));
        }

        if (!user.getUrlPicture().equals(User.EMPTY_CASE) && BitmapStorage.isFileExist(getContext(), user.getUrlPicture())) {
            this.mImageViewPhotoUser.setImageBitmap(BitmapStorage.loadImage(getContext(), user.getUrlPicture()));
        } else {
            this.mImageViewPhotoUser.setImageResource(R.drawable.bk_photo);
        }
    }

    /**
     *  BUTTONS
     */

    @OnClick(R.id.manager_detail_button_change)
    public void launchChangeActivity(){
        Intent intent = new Intent(getActivity(), ProfileManagerChangeActivity.class);
        intent.putExtra(ProfileManagerActivity.BUNDLE_PROFILE_USER, mUser);
        intent.putExtra(ProfileManagerActivity.BUNDLE_PROFILE_USER_ID, mUser.getId());
        startActivity(intent);
    }

    @OnClick(R.id.manager_detail_button_active_profile)
    public void loadActiveUser(){
        mCallback.activeUserClicked(this.mView, this.mUser.getId());
        mImageViewActiveProfile.setVisibility(View.VISIBLE);
    }

    /**
     *  CALLBACK
     */

    // interface for button clicked
    public interface ActiveUserClickedListener{
        void activeUserClicked(View view, long userId);
    }

    //callback for button clicked
    private ActiveUserClickedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ActiveUserClickedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemUserClickedListener\n" + getActivity().toString());
        }
    }


}
