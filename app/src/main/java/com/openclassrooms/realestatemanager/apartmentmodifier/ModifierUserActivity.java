package com.openclassrooms.realestatemanager.apartmentmodifier;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.viewmodel.ListingViewModel;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifierUserActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private ListingViewModel mListingViewModel;
    private List<String> mStringUserList;
    private String mStringStartSelectedUser;
    private RadioGroup mRadioGroup;
    private int mActiveTag;

    public static final String BUNDLE_CHANGE_USER_TAG = "BUNDLE_CHANGE_USER_TAG";
    public static final String BUNDLE_CHANGE_USER_NAME = "BUNDLE_CHANGE_USER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_user);
        ButterKnife.bind(this);
        mStringStartSelectedUser = getIntent().getStringExtra(ModifierFragment.MANAGER_NAME);
        mStringUserList = new ArrayList<>();

        mRadioGroup = findViewById(R.id.activity_modifier_user_radiogroup);

        this.configureViewModel();
        this.getUsersListing();

    }

    private void configureViewModel(){
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.mListingViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListingViewModel.class);
    }

    private void getUsersListing(){
        this.mListingViewModel.getUsers().observe(this, this::createRadioGroup);
    }

    private void createRadioGroup(List<User> users) {
        mRadioGroup.setOnCheckedChangeListener(null);
        mRadioGroup.clearCheck();
        for (int i = 0; i < users.size(); i++){
            mStringUserList.add(i, users.get(i).getUsername());
        }

        mStringUserList.add(1, "Yellow");
        mStringUserList.add(2, "Love");

        for (int i = 0 ; i < mStringUserList.size(); i++) {
            mRadioGroup.addView(new RadioButton(ModifierUserActivity.this),i);
            ((RadioButton) mRadioGroup.getChildAt(i)).setText(mStringUserList.get(i));
            mRadioGroup.getChildAt(i).setTag(i);

            if (((RadioButton) mRadioGroup.getChildAt(i)).getText().toString().equals(mStringStartSelectedUser)){
                ((RadioButton) mRadioGroup.getChildAt(i)).setChecked(true);
                mActiveTag = i + 1;
            }
        }

        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @OnClick(R.id.activity_modifier_user_ok_button)
    public void activityModifierUserValidationClick(){
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_CHANGE_USER_TAG, mActiveTag);
        intent.putExtra(BUNDLE_CHANGE_USER_NAME, mStringStartSelectedUser);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.activity_modifier_user_cancel_button)
    public void activityModifierUserCancelClick(){
        finish();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = this.findViewById(checkedId);
        mStringStartSelectedUser = radioButton.getText().toString();

        for (int i = 0; i < mStringUserList.size(); i++){
            if (mStringUserList.get(i).equals(mStringStartSelectedUser)){
                mActiveTag = i +1;
            }
        }

        Toast.makeText(getApplicationContext(), "Box NAME " + mActiveTag + " : " + mStringStartSelectedUser, Toast.LENGTH_LONG).show();
    }
}
