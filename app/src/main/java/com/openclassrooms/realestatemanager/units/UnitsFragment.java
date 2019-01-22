package com.openclassrooms.realestatemanager.units;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.realestatemanager.Controller.BaseActivity.BUNDLE_KEY_ACTIVE_DIMENSION;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.BUNDLE_KEY_ACTIVE_MONEY;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.SHARED_DIMENSION;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.SHARED_MONEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnitsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private View mView;

    @BindView(R.id.radioGroup_money) RadioGroup mRadioGroupMoney;
    @BindView(R.id.radioGroup_dimension) RadioGroup mRadioGroupDimension;
    @BindView(R.id.radio_button_dollar)RadioButton mRadioButtonDollar;
    @BindView(R.id.radio_button_euros)RadioButton mRadioButtonEuro;
    @BindView(R.id.radio_button_square)RadioButton mRadioButtonSquare;
    @BindView(R.id.radio_button_meter)RadioButton mRadioButtonMeter;

    public UnitsFragment() {}

    public static UnitsFragment newInstance(){
        return new UnitsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_units, container, false);
        ButterKnife.bind(this, mView);
        this.initUnitsSave();
        mRadioGroupMoney.setOnCheckedChangeListener(this);
        mRadioGroupDimension.setOnCheckedChangeListener(this);
        return mView;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SharedPreferences sharedPreferencesMoney = getActivity().getSharedPreferences(SHARED_MONEY, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesDimension = getActivity().getSharedPreferences(SHARED_DIMENSION, Context.MODE_PRIVATE);
        RadioButton radioButton = mView.findViewById(checkedId);
        switch (radioButton.getId()){
            case R.id.radio_button_dollar :
                Toast.makeText(getContext(), getString(R.string.units_activated_message) + " " + getString(R.string.loan_simulation_dollar), Toast.LENGTH_SHORT).show();
                sharedPreferencesMoney.edit().putString(BUNDLE_KEY_ACTIVE_MONEY, getString(R.string.loan_simulation_dollar)).apply();
                break;
            case R.id.radio_button_euros :
                Toast.makeText(getContext(), getString(R.string.units_activated_message) + " " + getString(R.string.loan_simulation_euro), Toast.LENGTH_SHORT).show();
                sharedPreferencesMoney.edit().putString(BUNDLE_KEY_ACTIVE_MONEY, getString(R.string.loan_simulation_euro)).apply();
                break;
            case R.id.radio_button_meter :
                Toast.makeText(getContext(), getString(R.string.units_activated_message) + " " + getString(R.string.units_meters), Toast.LENGTH_SHORT).show();
                sharedPreferencesDimension.edit().putString(BUNDLE_KEY_ACTIVE_DIMENSION, getString(R.string.units_meters)).apply();
                break;
            case R.id.radio_button_square :
                Toast.makeText(getContext(), getString(R.string.units_activated_message) + " " + getString(R.string.units_square), Toast.LENGTH_SHORT).show();
                sharedPreferencesDimension.edit().putString(BUNDLE_KEY_ACTIVE_DIMENSION, getString(R.string.units_square)).apply();
                break;
        }
    }

    private void initUnitsSave(){
        String money_unit = getActivity().getSharedPreferences(SHARED_MONEY, Context.MODE_PRIVATE).getString(BUNDLE_KEY_ACTIVE_MONEY, getString(R.string.loan_simulation_dollar));
        String dimension_unit = getActivity().getSharedPreferences(SHARED_DIMENSION, Context.MODE_PRIVATE).getString(BUNDLE_KEY_ACTIVE_DIMENSION, getString(R.string.units_square));
        assert money_unit != null;
        if (money_unit.equals(getString(R.string.loan_simulation_dollar))){
            mRadioButtonDollar.setChecked(true);
        } else {
            mRadioButtonEuro.setChecked(true);
        }
        assert dimension_unit != null;
        if (dimension_unit.equals(getString(R.string.units_square))){
            mRadioButtonSquare.setChecked(true);
        } else {
            mRadioButtonMeter.setChecked(true);
        }
    }
}
