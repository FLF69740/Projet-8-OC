package com.openclassrooms.realestatemanager.apartmentdetail;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.apartmentmodifier.ModifierActivity;
import com.openclassrooms.realestatemanager.models.Apartment;

public class SecondActivity extends BaseActivity {

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";

    @Override
    protected Fragment getFirstFragment() {
        Apartment apartment = getIntent().getParcelableExtra(BUNDLE_KEY_APARTMENT);
        mApartment = apartment;
        return SecondFragment.newInstance(apartment);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_second;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_second;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MODIFIER_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            Apartment apartment = data.getParcelableExtra(ModifierActivity.BUNDLE_APARTMENT_UPDATE);
            Toast.makeText(this, apartment.getDescription(), Toast.LENGTH_LONG).show();
            updateApartment(apartment);
        }
    }
}
