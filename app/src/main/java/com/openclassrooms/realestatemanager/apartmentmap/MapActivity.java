package com.openclassrooms.realestatemanager.apartmentmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;

import java.util.ArrayList;
import java.util.List;


public class MapActivity extends BaseActivity  implements MapFragment.OnClickedResultMarker{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 12340;

    @Override
    protected Fragment getFirstFragment() {
        return MapFragment.newInstance();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_map;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_map;
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
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.askLocationPermission();
    }




    // getting location permissions
    private void askLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        getSupportFragmentManager().beginTransaction().replace(getFragmentLayout(), getFirstFragment()).commit();
                        return;
                    }
                }
            }}
    }


    @Override
    public void onResultMarkerTransmission(View view, String title) {

    }

    @Override
    public void executePlacesCallback(View view, String coordinates) {

    }
}
