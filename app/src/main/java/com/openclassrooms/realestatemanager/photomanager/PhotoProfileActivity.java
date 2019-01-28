package com.openclassrooms.realestatemanager.photomanager;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.apartmentmodifier.TransformerApartmentItems;
import com.openclassrooms.realestatemanager.profilemanager.ProfileManagerDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PhotoProfileActivity extends AppCompatActivity {

    @BindView(R.id.camera_user_button_modify)ImageView mCameraUserButtonModify;
    @BindView(R.id.photo_user_button_modify)ImageView mPhotoUserButtonModify;
    @BindView(R.id.activity_photo_validate_user_btn)Button mUserButtonValidate;
    @BindView(R.id.activity_photo_user_photo)ImageView mImageViewUserPhoto;

    private static final String IMAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 101;
    private static final int RC_CHOOSE_PHOTO = 102;
    private static final int RC_CAMERA_CAPTURE = 103;

    public static final String BUNDLE_PHOTO_UPDATE = "BUNDLE_PHOTO_UPDATE";
    public static final String BUNDLE_NAME_UPDATE = "BUNDLE_NAME_UPDATE";

    private Uri mUriPicture;
    private Boolean mIsPhotoSelected;
    private long mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_profile);
        mId = getIntent().getLongExtra(ProfileManagerDetailFragment.BUNDLE_USER_ID, 0);
        mIsPhotoSelected = false;
        ButterKnife.bind(this);
    }


    //PERMISSION
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @OnClick(R.id.activity_photo_cancel_user_btn)
    public void cancelButtonClicked(){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @OnClick(R.id.activity_photo_validate_user_btn)
    public void validateButtonClicked(){

        if (mIsPhotoSelected) {
            String fileName = String.valueOf(mId) + TransformerApartmentItems.PICTURE_TITLE_CHARACTERE + "USER_PHOTO_PROFILE";
            BitmapStorage.saveImageInternalStorage(this, fileName, mUriPicture);
            BitmapStorage.showImageInformations(this, fileName);
            Intent intent = new Intent();
            intent.putExtra(BUNDLE_NAME_UPDATE, fileName);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     *  CHOOSE CAMERA CAPTURE
     */

    @OnClick(R.id.camera_user_button_modify)
    public void onClickCaptureCamera(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RC_CAMERA_CAPTURE);
    }

    /**
     *  CHOOSE IMAGE MEDIASTORE
     */

    @OnClick(R.id.photo_user_button_modify)
    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void onClickAddPicture(){
        if (!EasyPermissions.hasPermissions(this, IMAGE_PERMISSION)){
            EasyPermissions.requestPermissions(this, "OK", RC_IMAGE_PERMS, IMAGE_PERMISSION);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RC_CHOOSE_PHOTO);
    }

    // On Activity Result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CHOOSE_PHOTO){
            if (resultCode == RESULT_OK){
                this.mUriPicture = data.getData();
                mIsPhotoSelected = true;
                Glide.with(this).load(this.mUriPicture).apply(RequestOptions.centerCropTransform()).into(mImageViewUserPhoto);
            }
        } else if (requestCode == RC_CAMERA_CAPTURE){
            if (resultCode == RESULT_OK){
                Bundle extra = data.getExtras();
                Bitmap bitmap = (Bitmap) extra.get("data");
                mUriPicture = BitmapStorage.getImageUri(this, bitmap);
                mIsPhotoSelected = true;
                Glide.with(this).load(mUriPicture).apply(RequestOptions.centerCropTransform()).into(mImageViewUserPhoto);
            }
        }
    }
}
