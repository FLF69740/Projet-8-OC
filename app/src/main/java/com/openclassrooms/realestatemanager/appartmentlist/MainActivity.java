package com.openclassrooms.realestatemanager.appartmentlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;
import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.Controller.SecondFragment;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.apartmentcreator.CreateActivity;
import com.openclassrooms.realestatemanager.models.Apartment;

import java.io.Serializable;

public class MainActivity extends BaseActivity implements MainFragment.ItemClickedListener
{
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected Fragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable("list", (Serializable) mApartmentList);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_main;
    }

    @Override
    protected Fragment secondInstance() {
        return new SecondFragment();
    }

    @Override
    protected int getSecondFragmentLayout() {
        return R.id.frame_layout_second;
    }

    @Override
    protected boolean isAChildActivity() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     *  AFTER CREATE ACTIVITY
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (CREATE_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            String type = data.getStringExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_TYPE);
            String adress = data.getStringExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_ADRESS);
            int postalCode = data.getIntExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_PC,10000);
            String town = data.getStringExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_TOWN);
            int price = data.getIntExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_PRICE, 1);
            Apartment apartment = new Apartment(type, price, adress, postalCode, town, Utils.getTodayDate(), 1);

            createApartment(apartment);
        }
    }

    @Override
    public void itemClicked(View view, Apartment apartment) {
        
    }
}
