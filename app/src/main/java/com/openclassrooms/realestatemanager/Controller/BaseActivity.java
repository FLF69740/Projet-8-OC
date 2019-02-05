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
import com.openclassrooms.realestatemanager.About.AboutActivity;
import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.apartmentcreator.CreateActivity;
import com.openclassrooms.realestatemanager.apartmentdetail.SecondFragment;
import com.openclassrooms.realestatemanager.apartmentfilters.SearchApartmentActivity;
import com.openclassrooms.realestatemanager.apartmentmap.MapActivity;
import com.openclassrooms.realestatemanager.apartmentmap.MapFragment;
import com.openclassrooms.realestatemanager.apartmentmodifier.ModifierActivity;
import com.openclassrooms.realestatemanager.appartmentlist.MainActivity;
import com.openclassrooms.realestatemanager.appartmentlist.MainFragment;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.loansimulator.LoanSimulationActivity;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.profilemanager.ProfileManagerActivity;
import com.openclassrooms.realestatemanager.profilemanager.ProfileManagerDetailFragment;
import com.openclassrooms.realestatemanager.profilemanager.ProfileManagerFragment;
import com.openclassrooms.realestatemanager.profilemanager.UserCreationActivity;
import com.openclassrooms.realestatemanager.units.UnitsActivity;
import com.openclassrooms.realestatemanager.viewmodel.ListingViewModel;

import java.io.Serializable;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    private static final String BUNDLE_KEY_ADAPTER_POSITION = "BUNDLE_KEY_ADAPTER_POSITION";
    protected static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";
    protected static final String BUNDLE_KEY_OUTSTATE_INT_USER = "BUNDLE_KEY_OUTSTATE_INT_USER";
    public static final String BUNDLE_USERLIST_TO_PROFILEMANAGER_ACTIVITY = "BUNDLE_USERLIST_TO_PROFILEMANAGER_ACTIVITY";
    public static final String BUNDLE_APARTMENTLIST_TO_SEARCH_ACTIVITY = "BUNDLE_APARTMENTLIST_TO_SEARCH_ACTIVITY";
    public static final String BUNDLE_APARTMENTLIST_TO_MAP_ACTIVITY = "BUNDLE_APARTMENTLIST_TO_MAP_ACTIVITY";
    protected static final int CREATE_ACTIVITY_REQUEST_CODE = 10;
    protected static final int SEARCH_ACTIVITY_REQUEST_CODE = 20;
    protected static final int MAP_ACTIVITY_REQUEST_CODE = 41;
    protected static final int CREATE_USER_REQUEST_CODE = 30;

    //Shared Preferences
    public static final String BUNDLE_KEY_ACTIVE_USER = "BUNDLE_KEY_ACTIVE_USER";
    public static final String SHARED_ID = "SHARED_ID";
    public static final String BUNDLE_KEY_ACTIVE_MONEY = "BUNDLE_KEY_ACTIVE_MONEY";
    public static final String SHARED_MONEY = "SHARED_MONEY";
    public static final String BUNDLE_KEY_ACTIVE_DIMENSION = "BUNDLE_KEY_ACTIVE_DIMENSION";
    public static final String SHARED_DIMENSION = "SHARED_DIMENSION";

    protected static long DEFAULT_USER_ID = 1;
    protected View mViewHeader;
    protected ListingViewModel mListingViewModel;
    protected List<Apartment> mApartmentList;
    protected List<User> mUserList;
    protected Apartment mApartment;
    protected User mUser;
    protected long mUserId;
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
            mUserId = savedInstanceState.getLong(BUNDLE_KEY_OUTSTATE_INT_USER);
        }else {
            mUserId = getSharedPreferences(SHARED_ID, MODE_PRIVATE).getLong(BUNDLE_KEY_ACTIVE_USER, DEFAULT_USER_ID);
        }
        this.configureViewModel();
        this.getApartments(mUserId);
        this.getUsers();
        this.configureFragment(savedInstanceState);
        this.configureToolbar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_KEY_APARTMENT, mApartment);
        outState.putString(BUNDLE_KEY_ADAPTER_POSITION, mAdapterPosition);
        outState.putParcelable(BUNDLE_KEY_USER, mUser);
        outState.putLong(BUNDLE_KEY_OUTSTATE_INT_USER, mUserId);
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
    protected void updateHeader(User user){
        mNavUserName.setText(user.getUsername());
        BitmapStorage.showImageInformations(this, user.getUrlPicture());
        if (!user.getUrlPicture().equals(User.EMPTY_CASE) && BitmapStorage.isFileExist(this, user.getUrlPicture())) {
            this.mNavUserPhoto.setImageBitmap(BitmapStorage.loadImage(this, user.getUrlPicture()));
        } else {
            this.mNavUserPhoto.setImageResource(R.drawable.bk_photo);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id){
            case R.id.drawer_item_profileManager:
                startActivity(new Intent(this, ProfileManagerActivity.class));
                break;
            case R.id.drawer_item_apartment_manager:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.drawer_item_loanSimulation:
                startActivity(new Intent(this, LoanSimulationActivity.class));
                break;
            case R.id.drawer_item_units:
                startActivity(new Intent(this, UnitsActivity.class));
                break;
            case R.id.drawer_item_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.drawer_item_map:
                Intent intentMap = new Intent(this, MapActivity.class);
                startActivityForResult(intentMap, MAP_ACTIVITY_REQUEST_CODE);
                break;
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
                if (mApartment != null) {
                    Intent intentModifier = new Intent(this, ModifierActivity.class);
                    intentModifier.putExtra(ModifierActivity.BUNDLE_KEY_APARTMENT, mApartment);
                    intentModifier.putExtra(ModifierActivity.BUNDLE_KEY_USER, mUser);
                    startActivity(intentModifier);
                }
                return true;
            case R.id.menu_toolbar_add:
                Intent intentCreate = new Intent(this, CreateActivity.class);
                startActivityForResult(intentCreate, CREATE_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.menu_toolbar_search:
                Intent intentSearch = new Intent(this, SearchApartmentActivity.class);
                intentSearch.putExtra(BUNDLE_APARTMENTLIST_TO_SEARCH_ACTIVITY, (Serializable) mApartmentList);
                startActivityForResult(intentSearch, SEARCH_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.menu_toolbar_add_profile:
                Intent intentNewUser = new Intent(this, UserCreationActivity.class);
                startActivityForResult(intentNewUser, CREATE_USER_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *  FRAGMENT MANAGER
     */


    // configure fragment with fragment manager
    protected void configureFragment(Bundle state){
        if (state == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(getFragmentLayout(), getFirstFragment())
                    .commit();
            if ((getFirstFragment() instanceof MainFragment || getFirstFragment() instanceof ProfileManagerFragment) && findViewById(getSecondFragmentLayout()) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(getSecondFragmentLayout(), getSecondFragment())
                        .commit();
            }
        }
    }

    // Update fragment(s)
    protected void updateFragment(){
        // MainFragment UPDATE
        if (getFirstFragment() instanceof MainFragment && this.mApartmentList != null && !this.mApartmentList.isEmpty() && this.mUser != null){
            if (this.mApartment == null) {
                this.mApartment = mApartmentList.get(0);
            }
            ((MainFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(mApartmentList, mApartment.getId());
        }
        // ProfileFragment UPDATE
        if (getFirstFragment() instanceof ProfileManagerFragment && mUserList != null) {
            ((ProfileManagerFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(mUserList.get((int) mUserId-1), mUserList, mUserId);
        }
        if (getFirstFragment() instanceof MapFragment && mApartmentList != null) {
            ((MapFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(mApartmentList);
        }
        // SECOND FRAGMENT LAYOUT UPDATE
        if ((getFirstFragment() instanceof MainFragment || getFirstFragment() instanceof  ProfileManagerFragment)&& findViewById(getSecondFragmentLayout()) != null){
            // SecondFragment UPDATE
            if (getSecondFragment() instanceof  SecondFragment && this.mApartmentList != null && !this.mApartmentList.isEmpty() && this.mUser != null){
                if (this.mApartment == null) {
                    this.mApartment = mApartmentList.get(0);
                }
                ((SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout())).updateFragmentScreen(mApartment, mUser);
            }
            // ProfileFragmentDetail UPDATE
            else if (getSecondFragment() instanceof ProfileManagerDetailFragment && mUserList != null){
                ((ProfileManagerDetailFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout())).updateFragmentScreen(mUserList.get((int) mUserId-1), mUserId);
            }
        }
    }

    // Update Main and Second Fragment after search filters activated
    protected void updateFragmentWithSearchFilter(List<Apartment> apartmentList){
        if (apartmentList.isEmpty()){
            ((MainFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(apartmentList, 1);
            if (findViewById(getSecondFragmentLayout()) != null) {
                ((SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout())).updateFragmentScreen(null, mUser);
            }
        }else {
            ((MainFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(apartmentList, 1);
            if (findViewById(getSecondFragmentLayout()) != null) {
                ((SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout())).updateFragmentScreen(apartmentList.get(0), mUser);
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
        } else if (getFirstFragment() instanceof ProfileManagerFragment){
            getMenuInflater().inflate(R.menu.menu_profile, menu);
        }
        return true;
    }

    /**
     *  DATA
     */

    // Configure ViewModel
    protected void configureViewModel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.mListingViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListingViewModel.class);
        this.mListingViewModel.init(mUserId);
        this.getUser(mUserId);
    }

    // Configure current user
    protected void getUser(long userId){
        this.mListingViewModel.getUser(userId).observe(this, this::updateActiveUser);
    }

    // create an user
    protected void createUser(User user){
        this.mListingViewModel.createUser(user);
    }

    // update an user
    protected void updateUser(User user){
        this.mListingViewModel.updateUser(user);
    }

    // Configure list of users
    protected void getUsers(){
        this.mListingViewModel.getUsers().observe(this, this::updateUsersList);
    }

    // Configure apartments list for a user
    protected void getApartments(long userId){
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
        mUser = mUserList.get((int)mUserId-1);
        updateFragment();
    }

    // update the name of user
    protected void updateActiveUser(User user){
        this.mUser = user;
        updateFragment();
        if (!isAChildActivity()) updateHeader(user);
    }

}
