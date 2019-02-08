package com.openclassrooms.realestatemanager.help;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;

public class HelpActivity extends BaseActivity {


    @Override
    protected Fragment getFirstFragment() {
        return HelpFragment.newInstance();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_help;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_help;
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
