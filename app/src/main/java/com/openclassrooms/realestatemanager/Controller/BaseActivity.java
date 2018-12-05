package com.openclassrooms.realestatemanager.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.openclassrooms.realestatemanager.R;

public abstract class BaseActivity extends AppCompatActivity {

    // abstract methods
    protected abstract int getContentView();
    protected abstract Fragment newInstance();
    protected abstract int getFragmentLayout();
    protected abstract Fragment secondInstance();
    protected abstract int getSecondFragmentLayout();
    protected abstract boolean isAChildActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        configureFragment(savedInstanceState);

        this.configureToolbar();
    }

    protected void configureFragment(Bundle savedInstanceState){
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(getFragmentLayout(),newInstance())
                    .commit();
        }

        if (savedInstanceState == null && findViewById(getSecondFragmentLayout())!= null){
            getSupportFragmentManager().beginTransaction()
                    .add(getSecondFragmentLayout(), secondInstance())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (newInstance() instanceof MainFragment && findViewById(getSecondFragmentLayout()) != null) {
            getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        } else if (newInstance() instanceof MainFragment && findViewById(getSecondFragmentLayout()) == null) {
            getMenuInflater().inflate(R.menu.menu_toolbar_main_single, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_toolbar_second_single, menu);
        }
        return true;
    }

    /**
     *  TOOLBAR
     */

    private void configureToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isAChildActivity()){
            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
