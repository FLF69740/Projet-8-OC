package com.openclassrooms.realestatemanager.apartmentdetail;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;

import static com.openclassrooms.realestatemanager.models.TransformerApartmentItems.ENTITY_SEPARATOR;

public class ViewPagerPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_photo);

        Intent intent = getIntent();
        String list = intent.getStringExtra(SecondFragment.BUNDLE_KEY_LIST_PHOTO);
        int size = list.split(ENTITY_SEPARATOR).length;

        ViewPager viewPager = findViewById(R.id.viewPagerPhoto);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), size, list));

        TextView textView = findViewById(R.id.closeViewPager);
        textView.setOnClickListener(v -> finish());
    }

}
