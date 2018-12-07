package com.openclassrooms.realestatemanager.appartmentlist;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.Controller.SecondActivity;
import com.openclassrooms.realestatemanager.Controller.SecondFragment;
import com.openclassrooms.realestatemanager.R;

public class MainActivity extends BaseActivity //implements MainFragment.ItemClickedListener
{


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected Fragment newInstance() {
        return new MainFragment();
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
/*
    @Override
    public void itemClicked(View view) {
        SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout());
        if (secondFragment != null && secondFragment.isVisible()){
            secondFragment.changeText();
        } else {
            startActivity(new Intent(this, SecondActivity.class));
        }
    }
*/
}
