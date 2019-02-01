package com.openclassrooms.realestatemanager.profilemanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.apartmentmodifier.ModifierFragment;
import com.openclassrooms.realestatemanager.models.Item;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.photomanager.PhotoModifierActivity;
import com.openclassrooms.realestatemanager.photomanager.PhotoProfileActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.openclassrooms.realestatemanager.apartmentmodifier.ModifierActivity.BUNDLE_KEY_USER;
import static com.openclassrooms.realestatemanager.profilemanager.ProfileManagerDetailFragment.BUNDLE_USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileManagerChangeFragment extends Fragment {

    private View mView;
    private User mUser;
    private String mPhotoName = User.EMPTY_CASE;
    private boolean mIsPhotoSelected;

    private static final String BUNDLE_PHOTO_RESTORE = "BUNDLE_PHOTO_RESTORE";

    @BindView(R.id.manager_change_photo_user)ImageView mImageViewChangePhotoUser;
    @BindView(R.id.manager_change_name_user)EditText mEditTextChangeNameUser;
    @BindView(R.id.manager_change_description_user)EditText mEditTextChangeDescriptionUser;

    public ProfileManagerChangeFragment() {}

    public static ProfileManagerChangeFragment newInstance(User user, Long userId){
        ProfileManagerChangeFragment profileManagerChangeFragment = new ProfileManagerChangeFragment();
        Bundle args = new Bundle(2);
        args.putParcelable(BUNDLE_KEY_USER, user);
        args.putLong(BUNDLE_USER_ID, userId);
        profileManagerChangeFragment.setArguments(args);

        return profileManagerChangeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile_manager_change, container, false);
        ButterKnife.bind(this, mView);
        mIsPhotoSelected = false;
        if (getArguments() != null) {
            if (getArguments().getParcelable(BUNDLE_KEY_USER) != null && getArguments().getLong(BUNDLE_USER_ID) != 0) {
                configureFragment(getArguments().getParcelable(BUNDLE_KEY_USER), getArguments().getLong(BUNDLE_USER_ID));
            }
        }
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(BUNDLE_PHOTO_RESTORE)){
                mIsPhotoSelected = true;
                mPhotoName = savedInstanceState.getString(BUNDLE_PHOTO_RESTORE);
                this.mImageViewChangePhotoUser.setImageBitmap(BitmapStorage.loadImage(getContext(), mPhotoName));
            }
        }

        return mView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_KEY_USER, mUser);
        if (mIsPhotoSelected){
            outState.putString(BUNDLE_PHOTO_RESTORE, mPhotoName);
        }
    }

    // set elements of View
    private void configureFragment(User user, Long userId){
        mUser = user;
        mUser.setId(userId);
        mEditTextChangeNameUser.setText(user.getUsername());

        if (!user.getUserDescription().equals(User.EMPTY_CASE)){
            mEditTextChangeDescriptionUser.setText(user.getUserDescription());
        } else {
            mEditTextChangeDescriptionUser.setText(mView.getContext().getString(R.string.nav_drawer_item_about));
        }

        if (!user.getUrlPicture().equals(User.EMPTY_CASE) && BitmapStorage.isFileExist(getContext(), user.getUrlPicture())) {
            this.mImageViewChangePhotoUser.setImageBitmap(BitmapStorage.loadImage(getContext(), user.getUrlPicture()));
        } else {
            this.mImageViewChangePhotoUser.setImageResource(R.drawable.bk_photo);
        }
    }

    /**
     *  PHOTO CHANGE
     */

    public static final int RC_PHOTO_USER_UPLOAD = 1001;

    @OnClick(R.id.manager_change_photo_user)
    public void changePhotoUser(){
        Intent intent = new Intent(getActivity(), PhotoProfileActivity.class);
        intent.putExtra(BUNDLE_USER_ID, mUser.getId());
        startActivityForResult(intent, RC_PHOTO_USER_UPLOAD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_USER_UPLOAD){
            if (resultCode == RESULT_OK){
                mIsPhotoSelected = true;
                mPhotoName = data.getStringExtra(PhotoModifierActivity.BUNDLE_NAME_UPDATE);
                this.mImageViewChangePhotoUser.setImageBitmap(BitmapStorage.loadImage(getContext(), mPhotoName));
            }
        }
    }

    /**
     *  CALLBACK
     */

    @OnClick(R.id.manager_update_button_change)
    public void updateUserProfile(){
        this.mUser.setUsername(mEditTextChangeNameUser.getText().toString());
        this.mUser.setUserDescription(mEditTextChangeDescriptionUser.getText().toString());
        if (!mPhotoName.equals(User.EMPTY_CASE)) {
            this.mUser.setUrlPicture(mPhotoName);
        }
        mCallback.itemClicked(this.mView, this.mUser);
    }

    // interface for button clicked
    public interface ItemChangeDetailUserClickedListener{
        void itemClicked(View view, User user);
    }

    //callback for button clicked
    private ItemChangeDetailUserClickedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ItemChangeDetailUserClickedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemClickedListener");
        }
    }


}
