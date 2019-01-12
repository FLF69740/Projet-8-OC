package com.openclassrooms.realestatemanager.apartmentcreator;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;

public class CreateActivity extends BaseActivity implements CreateFragment.ItemClickedListener {

    public static final String BUNDLE_APARTMENT_CREATION_TYPE = "BUNDLE_APARTMENT_CREATION_TYPE";
    public static final String BUNDLE_APARTMENT_CREATION_ADRESS = "BUNDLE_APARTMENT_CREATION_ADRESS";
    public static final String BUNDLE_APARTMENT_CREATION_PC = "BUNDLE_APARTMENT_CREATION_PC";
    public static final String BUNDLE_APARTMENT_CREATION_TOWN = "BUNDLE_APARTMENT_CREATION_TOWN";
    public static final String BUNDLE_APARTMENT_CREATION_PRICE = "BUNDLE_APARTMENT_CREATION_PRICE";

    @Override
    protected Fragment getFirstFragment() {
        return CreateFragment.newInstance();
    }

    @Override
    protected int getContentView() {return R.layout.activity_create;}

    @Override
    protected int getFragmentLayout() {return R.id.frame_layout_create;}

    @Override
    protected Fragment getSecondFragment() {
        return null;
    }

    @Override
    protected int getSecondFragmentLayout() {return 0;}

    @Override
    protected boolean isAChildActivity() {return true;}

    @Override
    public void itemClicked(View view, String type, String adress, int postalCode, String town, int price) {
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_APARTMENT_CREATION_TYPE, type);
        intent.putExtra(BUNDLE_APARTMENT_CREATION_ADRESS, adress);
        intent.putExtra(BUNDLE_APARTMENT_CREATION_PC, postalCode);
        intent.putExtra(BUNDLE_APARTMENT_CREATION_TOWN, town);
        intent.putExtra(BUNDLE_APARTMENT_CREATION_PRICE, price);
        setResult(RESULT_OK, intent);
        finish();
    }
}
