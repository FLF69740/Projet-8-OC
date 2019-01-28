package com.openclassrooms.realestatemanager.apartmentfilters;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchApartmentFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.search_checkBox_inscription_date)CheckBox mCheckBoxSearchInscriptionDate;
    @BindView(R.id.search_inscription_from)Button mButtonInscriptionFrom;
    @BindView(R.id.search_inscription_to)Button mButtonInscriptionTo;
    @BindView(R.id.search_radioGroup)RadioGroup mRadioGroup;
    @BindView(R.id.search_radio_button_sold)RadioButton mRadioButtonSold;
    @BindView(R.id.search_sold_from)Button mButtonSoldFrom;
    @BindView(R.id.search_textView_to)TextView mTextViewLinkSoldTo;
    @BindView(R.id.search_sold_to)Button mButtonSoldTo;
    @BindView(R.id.search_load_button)Button mButtonLoad;
    @BindView(R.id.search_bottom_background)ImageView mImageViewBottomBackground;
    @BindView(R.id.checkbox_search_bottom)CheckBox mCheckBoxBottom;
    @BindView(R.id.textView_title_search)TextView mTextViewTitleBottom;
    @BindView(R.id.editText_information_search_from)EditText mEditTextInformationFrom;
    @BindView(R.id.editText_information_search_to)EditText mEditTextInformationTo;
    @BindView(R.id.button_validation_search)ImageView mImageViewValidationLine;

    public SearchApartmentFragment() {}

    public static SearchApartmentFragment newInstance(){
        return new SearchApartmentFragment();
    }

    private View mView;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_apartment, container, false);
        ButterKnife.bind(this, mView);

        mRadioGroup.setOnCheckedChangeListener(this);
        this.showSoldPanel(false);

        this.editCalendars();
        this.checkBoxInscriptionConfiguration(false);
        this.panelChoiceVisibility(true);
        return mView;
    }

    // Show or Gone Banckground panel/load button
    private void panelChoiceVisibility(boolean change){
        int a = 4, b = 0;
        if (change){
            a = 0;
            b = 4;
        }
        mButtonLoad.setVisibility(a);
        mImageViewBottomBackground.setVisibility(b);
        mCheckBoxBottom.setVisibility(b);
        mTextViewTitleBottom.setVisibility(b);
        mEditTextInformationFrom.setVisibility(b);
        mEditTextInformationTo.setVisibility(b);
        mImageViewValidationLine.setVisibility(b);
    }

    /**
     *  DATE SOLD DEFINITION
     */

    // Show or Gone sold button
    private void showSoldPanel(boolean show){
        int bool = 8;
        if (show) bool =0;
        mButtonSoldFrom.setVisibility(bool);
        mButtonSoldTo.setVisibility(bool);
        mTextViewLinkSoldTo.setVisibility(bool);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = mView.findViewById(checkedId);
        switch (radioButton.getId()){
            case R.id.search_radio_button_for_sale:
                showSoldPanel(false);
                break;
            case R.id.search_radio_button_sold:
                showSoldPanel(true);
                break;
        }
    }

    /**
     *  DATE INSCRIPTION DEFINITION
     */

    private void checkBoxInscriptionConfiguration(boolean checked){
        this.buttonsInscriptionState(checked);
        mCheckBoxSearchInscriptionDate.setOnCheckedChangeListener((buttonView, isChecked) -> buttonsInscriptionState(isChecked));
    }

    // Buttons inscription state
    private void buttonsInscriptionState(boolean checked){
        mButtonInscriptionFrom.setEnabled(checked);
        mButtonInscriptionTo.setEnabled(checked);
        if (checked){
            mButtonInscriptionTo.setTextColor(Color.WHITE);
            mButtonInscriptionFrom.setTextColor(Color.WHITE);
        }else {
            mButtonInscriptionTo.setTextColor(getResources().getColor(R.color.colorPrimary));
            mButtonInscriptionFrom.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     *  DATE PICKER DEFINITION
     */

    private Calendar mCalendarInscriptionFrom = Calendar.getInstance();
    private Calendar mCalendarInscriptionTo = Calendar.getInstance();
    private Calendar mCalendarSoldFrom = Calendar.getInstance();
    private Calendar mCalendarSoldTo = Calendar.getInstance();
    private String mStringInscriptionFrom = "", mStringInscriptionTo = "", mStringSoldFrom = "", mStringSoldTo = "";

    DatePickerDialog.OnDateSetListener datePickerInscriptionFrom = (view, year, month, dayOfMonth) -> {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setDateString(calendar,0);
        if (getComparationDates(calendar, mCalendarInscriptionTo)){
            updateLabel(0);
            mCalendarInscriptionFrom = calendar;
        }
        else alertDateMessage();
    };

    DatePickerDialog.OnDateSetListener datePickerInscriptionTo = (view, year, month, dayOfMonth) -> {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setDateString(calendar,1);
        if (getComparationDates(mCalendarInscriptionFrom, calendar)){
            updateLabel(1);
            mCalendarInscriptionTo = calendar;
        }
        else alertDateMessage();
    };

    DatePickerDialog.OnDateSetListener datePickerSoldFrom = (view, year, month, dayOfMonth) -> {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setDateString(calendar,2);
        if (getComparationDates(calendar, mCalendarSoldTo)){
            updateLabel(2);
            mCalendarSoldFrom = calendar;
        }
        else alertDateMessage();
    };

    DatePickerDialog.OnDateSetListener datePickerSoldTo = (view, year, month, dayOfMonth) -> {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setDateString(calendar,3);
        if (getComparationDates(mCalendarSoldFrom, calendar)){
            updateLabel(3);
            mCalendarSoldTo = calendar;
        }
        else alertDateMessage();
    };

    // Alert user if Start and end dates are reversed
    private void alertDateMessage(){
        Toast.makeText(this.getContext(),"Start and end dates are reversed", Toast.LENGTH_SHORT).show();
    }

    // Define Listeners from Calendars (Begin and End Dates)
    private void editCalendars(){
        mButtonInscriptionFrom.setText(sdf.format(mCalendarInscriptionFrom.getTime()));
        mButtonInscriptionTo.setText(sdf.format(mCalendarInscriptionTo.getTime()));
        mButtonSoldFrom.setText(sdf.format(mCalendarSoldFrom.getTime()));
        mButtonSoldTo.setText(sdf.format(mCalendarSoldTo.getTime()));

        mButtonInscriptionFrom.setOnClickListener(v -> new DatePickerDialog(getContext(), datePickerInscriptionFrom,
                mCalendarInscriptionFrom.get(Calendar.YEAR), mCalendarInscriptionFrom.get(Calendar.MONTH), mCalendarInscriptionFrom.get(Calendar.DAY_OF_MONTH)).show());

        mButtonInscriptionTo.setOnClickListener(v -> new DatePickerDialog(getContext(), datePickerInscriptionTo,
                mCalendarInscriptionTo.get(Calendar.YEAR), mCalendarInscriptionTo.get(Calendar.MONTH), mCalendarInscriptionTo.get(Calendar.DAY_OF_MONTH)).show());

        mButtonSoldFrom.setOnClickListener(v -> new DatePickerDialog(getContext(), datePickerSoldFrom,
                mCalendarSoldFrom.get(Calendar.YEAR), mCalendarSoldFrom.get(Calendar.MONTH), mCalendarSoldFrom.get(Calendar.DAY_OF_MONTH)).show());

        mButtonSoldTo.setOnClickListener(v -> new DatePickerDialog(getContext(), datePickerSoldTo,
                mCalendarSoldTo.get(Calendar.YEAR), mCalendarSoldTo.get(Calendar.MONTH), mCalendarSoldTo.get(Calendar.DAY_OF_MONTH)).show());
    }

    // Subscribe the date String into the the concerned editText (defined with his position)
    private void updateLabel(int pos) {
        switch (pos){
            case 0 : mButtonInscriptionFrom.setText(getDateString(pos)); break;
            case 1 : mButtonInscriptionTo.setText(getDateString(pos)); break;
            case 2 : mButtonSoldFrom.setText(getDateString(pos)); break;
            case 3 : mButtonSoldTo.setText(getDateString(pos));
        }
        if ((pos == 1) && (mButtonInscriptionFrom.getText().toString().isEmpty())) {
            mButtonInscriptionFrom.setText(getDateString(pos));
            mCalendarInscriptionFrom = mCalendarInscriptionTo;
        }
        if ((pos == 3) && (mButtonSoldFrom.getText().toString().isEmpty())) {
            mButtonSoldFrom.setText(getDateString(pos));
            mCalendarSoldFrom = mCalendarSoldTo;
        }
    }

    // Define the String of the target date
    public void setDateString(Calendar calendar, int position){
        if (position == 0) mStringInscriptionFrom = sdf.format(calendar.getTime());
        if (position == 1) mStringInscriptionTo = sdf.format(calendar.getTime());
        if (position == 2) mStringSoldFrom = sdf.format(calendar.getTime());
        else mStringSoldTo = sdf.format(calendar.getTime());
    }

    // Return the String of the target date
    public String getDateString(int position) {
        if (position == 0) return mStringInscriptionFrom;
        if (position == 1) return mStringInscriptionTo;
        if (position == 2) return mStringSoldFrom;
        else return mStringSoldTo;
    }

    // Comparation between the dates
    public boolean getComparationDates(Calendar calendar1, Calendar calendar2){
        if (calendar1.getTimeInMillis() > calendar2.getTimeInMillis()) return false;
        else return true;
    }



}
