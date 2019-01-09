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
import android.view.View;
import android.widget.Toast;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.apartmentcreator.CreateActivity;
import com.openclassrooms.realestatemanager.apartmentdetail.SecondFragment;
import com.openclassrooms.realestatemanager.apartmentmodifier.ModifierActivity;
import com.openclassrooms.realestatemanager.appartmentlist.MainFragment;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.viewmodel.ListingViewModel;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    private static final String BUNDLE_KEY_ADAPTER_POSITION = "BUNDLE_KEY_ADAPTER_POSITION";
    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";

    protected static final int CREATE_ACTIVITY_REQUEST_CODE = 10;
    protected static final int MODIFIER_ACTIVITY_REQUEST_CODE = 20;

    protected static int USER_ID = 1;
    protected static final String BUNDLE_KEY_PREF_INT_USER = "BUNDLE_KEY_PREF_INT_USER";
    protected View mViewHeader;
    protected ListingViewModel mListingViewModel;
    protected List<Apartment> mApartmentList;
    protected Apartment mApartment;
    protected User mUser;
    protected int mUserId;
    protected String mAdapterPosition;
    protected Toolbar toolbar;


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
            mUser = savedInstanceState.getParcelable(BUNDLE_KEY_USER);
            mUserId = savedInstanceState.getInt(BUNDLE_KEY_PREF_INT_USER);
        } else {

            mUserId = getPreferences(MODE_PRIVATE).getInt(BUNDLE_KEY_PREF_INT_USER, USER_ID);
        }
        this.configureViewModel();
        this.getApartments(mUserId);

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
        outState.putParcelable(BUNDLE_KEY_USER, mUser);
        outState.putInt(BUNDLE_KEY_PREF_INT_USER, mUserId);
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
        toolbar = findViewById(R.id.toolbar);
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
                Intent intentModifier = new Intent(this, ModifierActivity.class);
                intentModifier.putExtra(ModifierActivity.BUNDLE_KEY_APARTMENT, mApartment);
                intentModifier.putExtra(ModifierActivity.BUNDLE_KEY_USER, mUser);
                startActivityForResult(intentModifier, MODIFIER_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.menu_toolbar_add:
                Intent intentCreate = new Intent(this, CreateActivity.class);
                startActivityForResult(intentCreate, CREATE_ACTIVITY_REQUEST_CODE);
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
        this.mListingViewModel.init(mUserId);
        this.getUser(mUserId);
    }

    // Configure current user
    protected void getUser(int userId){
        this.mListingViewModel.getUser(userId).observe(this, this::updateActifUser);
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
    protected void updateApartment(Apartment apartment){
        this.mListingViewModel.updateApartment(apartment);
    }

    /**
     *  UI
     */

    // update the list of apartments
    protected void updateApartmentsList(List<Apartment> apartments){
        this.mApartmentList = apartments;
        replaceFragment();
    }

    // update the name of user
    protected void updateActifUser(User user){
        this.mUser = user;
        userUpdate(user);
    }

    protected abstract void userUpdate(User user);

}
