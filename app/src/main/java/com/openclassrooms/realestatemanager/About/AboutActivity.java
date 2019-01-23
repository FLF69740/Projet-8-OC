package com.openclassrooms.realestatemanager.About;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView closeBtn = findViewById(R.id.about_close_btn);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
