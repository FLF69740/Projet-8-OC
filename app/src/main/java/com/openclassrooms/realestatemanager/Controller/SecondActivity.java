package com.openclassrooms.realestatemanager.Controller;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;

public class SecondActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_second;
    }

    @Override
    protected Fragment newInstance() {
        return new SecondFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_second;
    }

    @Override
    protected boolean isAChildActivity() {
        return true;
    }
}
