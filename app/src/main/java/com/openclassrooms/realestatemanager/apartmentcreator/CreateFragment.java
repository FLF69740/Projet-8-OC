package com.openclassrooms.realestatemanager.apartmentcreator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Apartment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CreateFragment extends Fragment {

    @BindView(R.id.fragment_creation_type_editText)EditText mEditTextType;
    @BindView(R.id.fragment_creation_adress_editText)EditText mEditTextAdress;
    @BindView(R.id.fragment_creation_postalcode_editText)EditText mEditTextPostalCode;
    @BindView(R.id.fragment_creation_town_editText)EditText mEditTextTown;
    @BindView(R.id.fragment_creation_price_editText)EditText mEditTextPrice;
    @BindView(R.id.fragment_creation_create_button)Button mButton;

    View mView;
    Boolean isButtonActivated = false;
    Boolean isTypeNotEmpty = false, isAdressNotEmpty = false, isPostalCodeNotEmpty = false, isTownNotEmpty = false, isPriceNotEmpty = false;

    public CreateFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_create, container, false);
        ButterKnife.bind(this, mView);
        mButton.setEnabled(isButtonActivated);
        configureEditText();
        return mView;
    }

    private void configureEditText(){
        mEditTextAdress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isAdressNotEmpty = s.toString().length() > 5;
                changeButtonState();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mEditTextPostalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPostalCodeNotEmpty = s.toString().length() == 5;
                changeButtonState();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mEditTextTown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isTownNotEmpty = s.toString().length() > 2;
                changeButtonState();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mEditTextPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPriceNotEmpty = s.toString().length() != 0;
                changeButtonState();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mEditTextType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isTypeNotEmpty = s.toString().length() > 3;
                changeButtonState();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void changeButtonState(){
        if (isAdressNotEmpty && isPostalCodeNotEmpty && isPriceNotEmpty && isTownNotEmpty && isTypeNotEmpty){
            mButton.setEnabled(true);
        } else {
            mButton.setEnabled(false);
        }
    }

    @OnClick(R.id.fragment_creation_create_button) public void buttonClicked(){
        mCallback.itemClicked(this.mView, mEditTextType.getText().toString(), mEditTextAdress.getText().toString(), Integer.valueOf(mEditTextPostalCode.getText().toString()),
                mEditTextTown.getText().toString(), Integer.valueOf(mEditTextPrice.getText().toString()));
    }

    /**
    *  Callback
    */

    // interface for button clicked
    public interface ItemClickedListener{
        void itemClicked(View view, String type, String adress, int postalCode, String town, int price);
    }

    //callback for button clicked
    private ItemClickedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ItemClickedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemClickedListener");
        }
    }

}
