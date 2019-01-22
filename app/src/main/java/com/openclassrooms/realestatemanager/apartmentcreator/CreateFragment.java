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
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.Controller.BaseActivity.BUNDLE_KEY_ACTIVE_MONEY;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.SHARED_MONEY;


public class CreateFragment extends Fragment {

    @BindView(R.id.fragment_creation_type_editText)EditText mEditTextType;
    @BindView(R.id.fragment_creation_adress_editText)EditText mEditTextAdress;
    @BindView(R.id.fragment_creation_postalcode_editText)EditText mEditTextPostalCode;
    @BindView(R.id.fragment_creation_town_editText)EditText mEditTextTown;
    @BindView(R.id.fragment_creation_price_editText)EditText mEditTextPrice;
    @BindView(R.id.fragment_creation_create_button)Button mButton;
    @BindView(R.id.textView_create_price)TextView mTextViewPrice;

    private View mView;
    private Boolean isButtonActivated = false;
    private Boolean isTypeNotEmpty = false, isAdressNotEmpty = false, isPostalCodeNotEmpty = false, isTownNotEmpty = false, isPriceNotEmpty = false;
    private String mMoney_unit = "";

    public CreateFragment() {}

    public static CreateFragment newInstance(){
        return new CreateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_create, container, false);
        ButterKnife.bind(this, mView);
        mButton.setEnabled(isButtonActivated);
        this.configurePriceUnit();
        this.configureEditText();
        return mView;
    }

    private void configurePriceUnit(){
        mMoney_unit = getActivity().getSharedPreferences(SHARED_MONEY, Context.MODE_PRIVATE).getString(BUNDLE_KEY_ACTIVE_MONEY, getString(R.string.loan_simulation_dollar));
        String priceTitle = getActivity().getString(R.string.fragment_creation_price) + " (" + mMoney_unit + ")";
        mTextViewPrice.setText(priceTitle);
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

        String finalPrice = mEditTextPrice.getText().toString();
        if (mMoney_unit.equals(getActivity().getString(R.string.loan_simulation_euro))){
            int price = Integer.valueOf(mEditTextPrice.getText().toString());
            int newPrice = Utils.convertEuroToDollar(price);
            finalPrice = String.valueOf(newPrice);
        }

        mCallback.itemClicked(this.mView, mEditTextType.getText().toString(), mEditTextAdress.getText().toString(), Integer.valueOf(mEditTextPostalCode.getText().toString()),
                mEditTextTown.getText().toString(), Integer.valueOf(finalPrice));
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
