package com.openclassrooms.realestatemanager.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.apartmentcreator.CreateActivity;
import com.openclassrooms.realestatemanager.appartmentlist.MainFragment;
import com.openclassrooms.realestatemanager.models.Apartment;

public abstract class BaseActivity extends AppCompatActivity {

    protected static final String CODE_CURRENT_USER = "CODE_CURRENT_USER";
    private static final int CREATE_ACTIVITY_REQUEST_CODE = 10;

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

        if (newInstance() instanceof MainFragment && findViewById(getSecondFragmentLayout())!= null){
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
        } else if (newInstance() instanceof SecondFragment){
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_toolbar_modify:
                Toast.makeText(this, "MODIFY", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_toolbar_add:
                Intent intent = new Intent(this, CreateActivity.class);
             //   intent.putExtra(CODE_CURRENT_USER, );
                startActivityForResult(intent, CREATE_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.menu_toolbar_search:
                Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //DATA

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (CREATE_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            String type = data.getStringExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_TYPE);
            String adress = data.getStringExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_ADRESS);
            int postalCode = data.getIntExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_PC,10000);
            String town = data.getStringExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_TOWN);
            int price = data.getIntExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_PRICE, 1);
            Apartment apartment = new Apartment(type, price, adress, postalCode, town, Utils.getTodayDate(), 1);
            if (apartment.getId() == 0){
                apartment.setId(1);
            }
            Toast.makeText(this, apartment.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
