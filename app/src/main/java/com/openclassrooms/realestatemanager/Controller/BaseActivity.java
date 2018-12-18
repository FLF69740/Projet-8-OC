package com.openclassrooms.realestatemanager.Controller;

import android.arch.lifecycle.ViewModelProviders;
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
import com.openclassrooms.realestatemanager.apartmentcreator.CreateActivity;
import com.openclassrooms.realestatemanager.apartmentdetail.SecondFragment;
import com.openclassrooms.realestatemanager.appartmentlist.MainFragment;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.viewmodel.ListingViewModel;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    private static final String BUNDLE_KEY_ADAPTER_POSITION = "BUNDLE_KEY_ADAPTER_POSITION";
    protected static final int CREATE_ACTIVITY_REQUEST_CODE = 10;
    protected static int USER_ID = 1;
    protected ListingViewModel mListingViewModel;
    protected List<Apartment> mApartmentList;
    protected Apartment mApartment;
    protected String mAdapterPosition;


    // abstract methods
    protected abstract Fragment getFirstFragment();
    protected abstract int getContentView();
    protected abstract int getFragmentLayout();
    protected abstract Fragment getSecondFragment();
    protected abstract int getSecondFragmentLayout();
    protected abstract boolean isAChildActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        if (savedInstanceState != null){
            mApartment = savedInstanceState.getParcelable(BUNDLE_KEY_APARTMENT);
            mAdapterPosition = savedInstanceState.getString(BUNDLE_KEY_ADAPTER_POSITION);
        }

        this.configureViewModel();
        this.getApartments(USER_ID);

        if (savedInstanceState == null) {
            this.configureFragment();
        }

        this.configureToolbar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_KEY_APARTMENT, mApartment);
        outState.putString(BUNDLE_KEY_ADAPTER_POSITION, mAdapterPosition);
    }



    protected void configureFragment(){

        getSupportFragmentManager().beginTransaction()
                .add(getFragmentLayout(), getFirstFragment())
                .commit();

        if (getFirstFragment() instanceof MainFragment && findViewById(getSecondFragmentLayout())!= null){
            getSupportFragmentManager().beginTransaction()
                    .add(getSecondFragmentLayout(), getSecondFragment())
                    .commit();
        }
    }

    protected void replaceFragment(){
        getSupportFragmentManager().beginTransaction().replace(getFragmentLayout(), getFirstFragment()).commit();
        if (getFirstFragment() instanceof MainFragment && findViewById(getSecondFragmentLayout())!=null){
            getSupportFragmentManager().beginTransaction().replace(getSecondFragmentLayout(), getSecondFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getFirstFragment() instanceof MainFragment && findViewById(getSecondFragmentLayout()) != null) {
            getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        } else if (getFirstFragment() instanceof MainFragment && findViewById(getSecondFragmentLayout()) == null) {
            getMenuInflater().inflate(R.menu.menu_toolbar_main_single, menu);
        } else if (getFirstFragment() instanceof SecondFragment){
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
                startActivityForResult(intent, CREATE_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.menu_toolbar_search:
                Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *  DATA
     */

    // Configure ViewModel
    private void configureViewModel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.mListingViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListingViewModel.class);
        this.mListingViewModel.init(USER_ID);
    }

    // Configure current user
    protected void getCurrentUser(int userId){
        //----------------------SOON-----------------------//
    }

    // Configure apartments list for a user
    protected void getApartments(int userId){
        this.mListingViewModel.getApartments(userId).observe(this, this::updateApartmentsList);
    }

    // Create an apartment
    protected void createApartment(Apartment apartment){
        this.mListingViewModel.createApartment(apartment);
    }

    // Update an apartment
    protected void updateApartment(int userId){
        //----------------------SOON-----------------------//
    }

    /**
     *  UI
     */

    protected void updateApartmentsList(List<Apartment> apartments){
        this.mApartmentList = apartments;
        replaceFragment();
    }


}
