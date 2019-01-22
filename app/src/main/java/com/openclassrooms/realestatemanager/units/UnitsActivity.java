package com.openclassrooms.realestatemanager.units;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;

public class UnitsActivity extends BaseActivity {

    @Override
    protected Fragment getFirstFragment() {
        return UnitsFragment.newInstance();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_units;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_units;
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

}
