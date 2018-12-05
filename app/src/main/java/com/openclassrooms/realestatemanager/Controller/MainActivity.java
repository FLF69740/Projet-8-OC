package com.openclassrooms.realestatemanager.Controller;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;

public class MainActivity extends BaseActivity implements MainFragment.ItemClickedListener {


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

    @Override
    public void itemClicked(View view) {
        SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout());
        if (secondFragment != null && secondFragment.isVisible()){
            secondFragment.changeText();
        } else {
            startActivity(new Intent(this, SecondActivity.class));
        }
    }

}
