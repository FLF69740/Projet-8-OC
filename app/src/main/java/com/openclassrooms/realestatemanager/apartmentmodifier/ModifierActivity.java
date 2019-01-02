package com.openclassrooms.realestatemanager.apartmentmodifier;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.Item;
import com.openclassrooms.realestatemanager.models.TransformerApartmentItems;

import java.util.List;

public class ModifierActivity extends BaseActivity implements ModifierFragment.ItemModifierClickedListener {

    public static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    public static final String BUNDLE_APARTMENT_UPDATE = "BUNDLE_APARTMENT_UPDATE";

    @Override
    protected Fragment getFirstFragment() {
        Apartment apartment = getIntent().getParcelableExtra(BUNDLE_KEY_APARTMENT);
        return ModifierFragment.newInstance(apartment);
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
    public void itemClicked(View view, List<Item> itemList, String dateInscription, long id, long userId) {
        TransformerApartmentItems transformerApartmentItems = new TransformerApartmentItems();
        transformerApartmentItems.createApartment(itemList, view.getContext(), id, userId);
        Apartment apartment = transformerApartmentItems.getApartment();
        apartment.setDateInscription(dateInscription);
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_APARTMENT_UPDATE, apartment);
        setResult(RESULT_OK, intent);
        finish();
    }
}
