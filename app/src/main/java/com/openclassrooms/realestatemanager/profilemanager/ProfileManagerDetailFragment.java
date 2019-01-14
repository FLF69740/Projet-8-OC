package com.openclassrooms.realestatemanager.profilemanager;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileManagerDetailFragment extends Fragment {

    @BindView(R.id.manager_detail_name_user)EditText mEditTextNameUser;
    @BindView(R.id.manager_detail_photo_user)ImageView mImageViewPhotoUser;
    @BindView(R.id.manager_detail_description_user)EditText mEditTextDescriptionUser;

    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";

    private View mView;
    private User mUser;

    public ProfileManagerDetailFragment() {}

    public static ProfileManagerDetailFragment newInstance(){
        return new ProfileManagerDetailFragment();
    }

    public static ProfileManagerDetailFragment newInstance(User user){
        ProfileManagerDetailFragment profileManagerDetailFragment = new ProfileManagerDetailFragment();
        Bundle args = new Bundle(1);
        args.putParcelable(BUNDLE_KEY_USER, user);
        profileManagerDetailFragment.setArguments(args);

        return profileManagerDetailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_profile_manager_detail, container, false);
        ButterKnife.bind(this, mView);

        if (getArguments() != null) {
            if (getArguments().getParcelable(BUNDLE_KEY_USER) != null) {
                updateFragmentScreen(getArguments().getParcelable(BUNDLE_KEY_USER));
            }
        }

        return mView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_KEY_USER, mUser);
    }

    public void updateFragmentScreen(User user) {
        mUser = user;
        mEditTextNameUser.setText(user.getUsername());

        if (!user.getUserDescription().equals(User.EMPTY_CASE)){
            mEditTextDescriptionUser.setText(user.getUserDescription());
        } else {
            mEditTextDescriptionUser.setText(mView.getContext().getString(R.string.nav_drawer_item_about));
        }

        if (!user.getUrlPicture().equals(User.EMPTY_CASE) && BitmapStorage.isFileExist(getContext(), user.getUrlPicture())) {
            this.mImageViewPhotoUser.setImageBitmap(BitmapStorage.loadImage(getContext(), user.getUrlPicture()));
        } else {
            this.mImageViewPhotoUser.setImageResource(R.drawable.bk_photo);
        }
    }
}
