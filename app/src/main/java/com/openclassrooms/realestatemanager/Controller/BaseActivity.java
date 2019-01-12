package com.openclassrooms.realestatemanager.Controller;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.apartmentcreator.CreateActivity;
import com.openclassrooms.realestatemanager.apartmentdetail.SecondFragment;
import com.openclassrooms.realestatemanager.apartmentmodifier.ModifierActivity;
import com.openclassrooms.realestatemanager.appartmentlist.MainActivity;
import com.openclassrooms.realestatemanager.appartmentlist.MainFragment;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.profilemanager.ProfileManagerActivity;
import com.openclassrooms.realestatemanager.profilemanager.ProfileManagerFragment;
import com.openclassrooms.realestatemanager.viewmodel.ListingViewModel;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    protected List<User> mUserList;
    protected Apartment mApartment;
    protected User mUser;
    protected int mUserId;
    protected String mAdapterPosition;
    protected Toolbar toolbar;

    private TextView mNavUserName;
    private ImageView mNavUserPhoto;
    private DrawerLayout mDrawerLayout;

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

    /**
     *  NAVIGATION DRAWER
     */

    //Configure Drawer Layout
    private void configureDrawerLayout(){
        mDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    //Configure NavigationView
    private void configureNavigationView(){
        NavigationView mNavigationView = findViewById(R.id.activity_main_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mViewHeader = mNavigationView.getHeaderView(0);
        mNavUserName = mViewHeader.findViewById(R.id.nav_userName);
        mNavUserPhoto = mViewHeader.findViewById(R.id.nav_userPhoto);
    }

    //Update header
    private void updateHeader(){
        mNavUserName.setText(mUser.getUsername());
        BitmapStorage.showImageInformations(this, mUser.getUrlPicture());
        mNavUserPhoto.setImageResource(R.drawable.bk_photo);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id){
            case R.id.drawer_item_profileManager: startActivity(new Intent(this, ProfileManagerActivity.class)); break;
            case R.id.drawer_item_apartment_manager: startActivity(new Intent(this, MainActivity.class)); break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

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
        } else {
            this.configureDrawerLayout();
            this.configureNavigationView();
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
     *  FRAGMENT MANAGER
     */


    protected void configureFragment(){

        getSupportFragmentManager().beginTransaction()
                .add(getFragmentLayout(), getFirstFragment())
                .commit();

        if ((getFirstFragment() instanceof MainFragment || getFirstFragment() instanceof ProfileManagerFragment)&& findViewById(getSecondFragmentLayout())!= null){
            getSupportFragmentManager().beginTransaction()
                    .add(getSecondFragmentLayout(), getSecondFragment())
                    .commit();
        }
    }

    protected void updateFragment(){
        // MainFragment UPDATE
        if (getFirstFragment() instanceof MainFragment && this.mApartmentList != null && !this.mApartmentList.isEmpty() && this.mUser != null){
            this.mApartment = mApartmentList.get(0);
            ((MainFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(mApartmentList);
        }
        // ProfileFragment UPDATE
        else if (getFirstFragment() instanceof ProfileManagerFragment && mUserList != null && mUser != null) {
            ((ProfileManagerFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(mUser, mUserList);
        }

        // SECOND FRAGMENT LAYOUT UPDATE
        if (getFirstFragment() instanceof MainFragment && findViewById(getSecondFragmentLayout()) != null){
            // SecondFragment UPDATE
            if (getSecondFragment() instanceof  SecondFragment && this.mApartmentList != null && !this.mApartmentList.isEmpty() && this.mUser != null){
                ((SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout())).refresh(mApartmentList.get(0), mUser);
            }
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
        this.mListingViewModel.getUser(userId).observe(this, this::updateActiveUser);
    }

    // Configure list of users
    protected void getUsers(){
        this.mListingViewModel.getUsers().observe(this, this::updateUsersList);
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
        updateFragment();
    }

    // update the list of users
    protected void updateUsersList(List<User> users){
        this.mUserList = users;
        updateFragment();
    }

    // update the name of user
    protected void updateActiveUser(User user){
        this.mUser = user;
        updateFragment();
        if (!isAChildActivity()) updateHeader();
    }

}
