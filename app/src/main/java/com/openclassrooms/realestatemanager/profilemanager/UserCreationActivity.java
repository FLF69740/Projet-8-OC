package com.openclassrooms.realestatemanager.profilemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;

import butterknife.ButterKnife;

public class UserCreationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditTextName;

    public static final String BUNDLE_NEW_USER_NAME = "BUNDLE_NEW_USER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        mEditTextName = findViewById(R.id.new_user_name);

        Button okBtn = findViewById(R.id.new_user_button_validation);
        Button cancelBtn = findViewById(R.id.new_user_button_cancel);

        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getTag().toString()){
            case "1" :
                Intent intent = new Intent();
                intent.putExtra(BUNDLE_NEW_USER_NAME, mEditTextName.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case "0" :
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
