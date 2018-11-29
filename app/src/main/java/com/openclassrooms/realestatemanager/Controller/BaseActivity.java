package com.openclassrooms.realestatemanager.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentView();
    protected abstract Fragment newInstance();
    protected abstract int getFragmentLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        configureFragment(savedInstanceState);
    }

    protected void configureFragment(Bundle savedInstanceState){
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(getFragmentLayout(),newInstance())
                    .commit();
        }
    }


}
