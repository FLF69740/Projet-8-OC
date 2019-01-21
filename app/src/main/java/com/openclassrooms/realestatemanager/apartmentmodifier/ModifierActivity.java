package com.openclassrooms.realestatemanager.apartmentmodifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.appartmentlist.MainActivity;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.Item;
import com.openclassrooms.realestatemanager.models.TransformerApartmentItems;
import com.openclassrooms.realestatemanager.models.User;

import java.util.List;

public class ModifierActivity extends BaseActivity implements ModifierFragment.ItemModifierClickedListener {

    public static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";
    public static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";

    @Override
    protected Fragment getFirstFragment() {
        Apartment apartment = getIntent().getParcelableExtra(BUNDLE_KEY_APARTMENT);
        User user = getIntent().getParcelableExtra(BUNDLE_KEY_USER);
        mUser = user;
        return ModifierFragment.newInstance(apartment, user);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modifier;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_modifier;
    }

    @Override
    protected Fragment getSecondFragment() {
        return null;
    }

    @Override
    protected int getSecondFragmentLayout() {
        return 0;
    }

    @Override
    protected boolean isAChildActivity() {
        return true;
    }

    @Override
    public void itemClicked(View view, List<Item> itemList, String dateInscription, long id, long userId, Boolean isSold, String dateSold) {
        TransformerApartmentItems transformerApartmentItems = new TransformerApartmentItems();
        transformerApartmentItems.createApartment(itemList, view.getContext(), id, userId);
        Apartment apartment = transformerApartmentItems.getApartment();
        apartment.setDateInscription(dateInscription);
        if (isSold){
            apartment.setDateSold(dateSold);
        }else {
            apartment.setDateSold(Apartment.EMPTY_CASE);
        }
        apartment.setSold(isSold);
        updateApartment(apartment);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
