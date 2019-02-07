package com.openclassrooms.realestatemanager.appartmentlist;

import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.apartmentdetail.SecondActivity;
import com.openclassrooms.realestatemanager.apartmentdetail.SecondFragment;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.apartmentcreator.CreateActivity;
import com.openclassrooms.realestatemanager.apartmentfilters.SearchApartmentActivity;
import com.openclassrooms.realestatemanager.apartmentmap.MapActivity;
import com.openclassrooms.realestatemanager.models.Apartment;

import java.util.List;

import static com.openclassrooms.realestatemanager.MainApplication.CHANNEL;

public class MainActivity extends BaseActivity implements MainFragment.ItemClickedListener
{

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";

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
            Apartment apartment = new Apartment(type, price, adress, postalCode, town, Utils.getTodayDate(), mUserId);

            createApartment(apartment);

            this.sendNotification(apartment);
        }
        if (SEARCH_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            mApartmentList = (List<Apartment>) data.getSerializableExtra(SearchApartmentActivity.BUNDLE_APARTMENT_LIST_SEARCH);
            updateFragmentWithSearchFilter(mApartmentList);
        }
        if (MAP_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            Apartment apartment = data.getParcelableExtra(MapActivity.BUNDLE_APARTMENT_MAP);
            this.itemMap(apartment, String.valueOf(apartment.getId()));
        }
    }

    // send a notification
    private void sendNotification(Apartment apartment){
        String body = getString(R.string.fragment_creation_notification_content_body_first) + apartment.getAdress() + getString(R.string.fragment_creation_notification_content_body_second);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL)
                .setSmallIcon(R.drawable.image_realestate)
                .setContentTitle(getString(R.string.fragment_creation_notification_content_title))
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1001, notification);
    }

    // initialisation of fragment(s) after map callback
    private void itemMap(Apartment apartment, String adapterPosition) {
        mApartment = apartment;
        mAdapterPosition = adapterPosition;
        SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout());
        if (secondFragment != null && secondFragment.isVisible()){
            secondFragment.updateFragmentScreen(mApartment, mUser);
            ((MainFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(mApartmentList, mApartment.getId());
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(BUNDLE_KEY_APARTMENT, apartment);
            intent.putExtra(BUNDLE_KEY_USER, mUser);
            startActivity(intent);
        }
    }

    /**
     *  RECYCLERVIEW CLICK
     */

    @Override
    public void itemClicked(View view, Apartment apartment, String adapterPosition) {
        mApartment = apartment;
        mAdapterPosition = adapterPosition;
        SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentById(getSecondFragmentLayout());
        if (secondFragment != null && secondFragment.isVisible()){
            secondFragment.updateFragmentScreen(mApartment, mUser);
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(BUNDLE_KEY_APARTMENT, apartment);
            intent.putExtra(BUNDLE_KEY_USER, mUser);
            startActivity(intent);
        }
    }
}
