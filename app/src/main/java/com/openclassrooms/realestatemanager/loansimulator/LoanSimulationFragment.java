package com.openclassrooms.realestatemanager.loansimulator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.realestatemanager.Controller.BaseActivity.BUNDLE_KEY_ACTIVE_MONEY;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.SHARED_MONEY;


public class LoanSimulationFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.loan_editText_contribution)EditText mEditTextContribution;
    @BindView(R.id.loan_editText_price)EditText mEditTextPrice;
    @BindView(R.id.loan_editText_rate)EditText mEditTextRate;
    @BindView(R.id.loan_editText_year)EditText mEditTextYear;
    @BindView(R.id.loan_btn)Button mResultBtn;
    @BindView(R.id.loan_result)TextView mTextViewResult;
    @BindView(R.id.loan_total_amount)TextView mTextViewTotalAmount;
    @BindView(R.id.loan_unity_price)TextView mTextViewPriceUnity;
    @BindView(R.id.loan_result_unity)TextView mTextViewResultUnity;
    @BindView(R.id.loan_unity_contribution)TextView mTextViewContributionUnity;

    public LoanSimulationFragment() {}

    public static LoanSimulationFragment newInstance(){
        return new LoanSimulationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_simulation, container, false);
        ButterKnife.bind(this, view);
        mResultBtn.setOnClickListener(this);
        this.moneyUnitConfiguration();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (isEditTextOk()){
            double totalAmount = Integer.valueOf(mEditTextPrice.getText().toString()) - Integer.valueOf(mEditTextContribution.getText().toString()) + Utils.getLoaninterestResult(
                    Integer.valueOf(mEditTextPrice.getText().toString()),
                    Integer.valueOf(mEditTextYear.getText().toString()),
                    Double.valueOf(mEditTextRate.getText().toString()));

            mTextViewTotalAmount.setText(String.valueOf((int) totalAmount));
            mTextViewResult.setText(String.valueOf((int) Utils.getMonthRefund(totalAmount, Integer.valueOf(mEditTextYear.getText().toString()))));
        }
    }

    //Verification of all editText
    private boolean isEditTextOk(){
        boolean result = true;

        if (mEditTextPrice.getText().toString().isEmpty()){
            result = false;
        }
        if (mEditTextRate.getText().toString().isEmpty()){
            result = false;
        }
        if (mEditTextYear.getText().toString().isEmpty() || Integer.valueOf(mEditTextYear.getText().toString()) == 0){
            result = false;
        }
        if (mEditTextContribution.getText().toString().isEmpty()){
            mEditTextContribution.setText("0");
        }
        return result;
    }

    // money unit configuration
    private void moneyUnitConfiguration(){
        String money_unit = getActivity().getSharedPreferences(SHARED_MONEY, Context.MODE_PRIVATE).getString(BUNDLE_KEY_ACTIVE_MONEY, getString(R.string.loan_simulation_dollar));
        mTextViewPriceUnity.setText(money_unit);
        mTextViewContributionUnity.setText(money_unit);
        mTextViewResultUnity.setText(money_unit);
    }
}
