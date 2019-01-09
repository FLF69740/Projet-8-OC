package com.openclassrooms.realestatemanager.appartmentlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.apartmentdetail.SecondActivity;
import com.openclassrooms.realestatemanager.apartmentdetail.SecondFragment;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.apartmentcreator.CreateActivity;
import com.openclassrooms.realestatemanager.apartmentmodifier.ModifierActivity;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainFragment.ItemClickedListener, NavigationView.OnNavigationItemSelectedListener
{

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";

    TextView mNavUserName;
    ImageView mNavUserPhoto;

    @BindView(R.id.activity_main_nav_view)NavigationView mNavigationView;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected Fragment getFirstFragment() {
        return MainFragment.newInstance(mApartmentList, mAdapterPosition);
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_main;
    }

    @Override
    protected Fragment getSecondFragment() {
        return SecondFragment.newInstance(mApartment, mUser);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        this.configureDrawerLayout();
        this.configureNavigationView();

    }

    @Override
    protected void userUpdate(User user) {
        mUser = user;
        mNavUserName.setText(user.getUsername());
        BitmapStorage.showImageInformations(this, user.getUrlPicture());
        mNavUserPhoto.setImageResource(R.drawable.bk_photo);
    }

    /**
     *  AFTER CREATE ACTIVITY
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (CREATE_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            String type = data.getStringExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_TYPE);
            String adress = data.getStringExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_ADRESS);
            int postalCode = data.getIntExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_PC,10000);
            String town = data.getStringExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_TOWN);
            int price = data.getIntExtra(CreateActivity.BUNDLE_APARTMENT_CREATION_PRICE, 1);
            Apartment apartment = new Apartment(type, price, adress, postalCode, town, Utils.getTodayDate(), 1);

            createApartment(apartment);
        }
        if (MODIFIER_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            Apartment apartment = data.getParcelableExtra(ModifierActivity.BUNDLE_APARTMENT_UPDATE);
            updateApartment(apartment);
            mApartment = apartment;
            SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout());
            if (secondFragment != null && secondFragment.isVisible()) {
                secondFragment.updateDoubleScreen(mApartment, mUser);
            }
        }
    }

    @Override
    public void itemClicked(View view, Apartment apartment, String adapterPosition) {
        mApartment = apartment;
        mAdapterPosition = adapterPosition;
        SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout());
        if (secondFragment != null && secondFragment.isVisible()){
            secondFragment.updateDoubleScreen(mApartment, mUser);
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(BUNDLE_KEY_APARTMENT, apartment);
            intent.putExtra(BUNDLE_KEY_USER, mUser);
            startActivity(intent);
        }
    }

    //Configure Drawer Layout
    private void configureDrawerLayout(){
        DrawerLayout drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    //Configure NavigationView
    private void configureNavigationView(){
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mViewHeader = mNavigationView.getHeaderView(0);
        mNavUserName = mViewHeader.findViewById(R.id.nav_userName);
        mNavUserPhoto = mViewHeader.findViewById(R.id.nav_userPhoto);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
