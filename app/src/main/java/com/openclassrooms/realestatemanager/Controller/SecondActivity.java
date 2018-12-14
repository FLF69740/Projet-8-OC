package com.openclassrooms.realestatemanager.Controller;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;

public class SecondActivity extends BaseActivity {

    public static final String EXTRA_APARTMENT_LINK = "EXTRA_APARTMENT_LINK";

    @Override
    protected int getContentView() {
        return R.layout.activity_second;
    }

    @Override
    protected Fragment newInstance() {
        SecondFragment secondFragment = new SecondFragment();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = new Bundle();
            Apartment apartment = getIntent().getParcelableExtra("apartment");
            if (apartment != null) {
                bundle.putParcelable("apartment", apartment);
                secondFragment.setArguments(bundle);
            }
        }
        return secondFragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_second;
    }

    @Override
    protected Fragment secondInstance() {
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
}
