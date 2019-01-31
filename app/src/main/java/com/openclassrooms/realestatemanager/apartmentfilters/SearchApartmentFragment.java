package com.openclassrooms.realestatemanager.apartmentfilters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.appartmentlist.RecyclerViewClickSupport;
import com.openclassrooms.realestatemanager.models.LineSearch;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchApartmentFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.search_checkBox_inscription_date)CheckBox mCheckBoxSearchInscriptionDate;
    @BindView(R.id.search_inscription_from)Button mButtonInscriptionFrom;
    @BindView(R.id.search_textView_to)TextView mDateSoldTo;
    @BindView(R.id.search_inscription_to)Button mButtonInscriptionTo;
    @BindView(R.id.search_radioGroup)RadioGroup mRadioGroup;
    @BindView(R.id.search_radio_button_sold)RadioButton mRadioButtonSold;
    @BindView(R.id.search_sold_from)Button mButtonSoldFrom;
    @BindView(R.id.search_sold_to)Button mButtonSoldTo;
    @BindView(R.id.search_load_button)Button mButtonLoad;
    @BindView(R.id.search_bottom_background)ImageView mImageViewBottomBackground;
    @BindView(R.id.checkbox_search_bottom)CheckBox mCheckBoxBottom;
    @BindView(R.id.textView_title_search)TextView mTextViewTitleBottom;
    @BindView(R.id.editText_information_search_from)EditText mEditTextInformationFrom;
    @BindView(R.id.textView_search_to)TextView mTextViewTo;
    @BindView(R.id.editText_information_search_to)EditText mEditTextInformationTo;
    @BindView(R.id.button_validation_search)ImageView mImageViewValidationLine;
    @BindView(R.id.search_recycler_view)RecyclerView mRecyclerView;

    public SearchApartmentFragment() {}

    public static SearchApartmentFragment newInstance(){
        return new SearchApartmentFragment();
    }

    private View mView;
    private List<LineSearch> mLineSearchList;
    private LineSearch mLineSearchInscription, mLineSearchSold;
    private SearchFilterAdapter mAdapter;
    private DateTime mCalendarInscriptionFrom = new DateTime();
    private DateTime mCalendarInscriptionTo = new DateTime();
    private DateTime mCalendarSoldFrom = new DateTime();
    private DateTime mCalendarSoldTo = new DateTime();
    private String mStringInscriptionFrom = "", mStringInscriptionTo = "", mStringSoldFrom = "", mStringSoldTo = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_apartment, container, false);
        ButterKnife.bind(this, mView);
        mRadioGroup.setOnCheckedChangeListener(this);
        this.showSoldPanel(false);
        this.configureOnClickRecyclerView();
        this.panelChoiceVisibility(true);
        return mView;
    }

    // get LineSearch List and configure RecyclerView
    public void refresh(List<LineSearch> lineSearchList, LineSearch lineSearchInscription, LineSearch lineSearchSold) {
        mLineSearchList = new ArrayList<>(lineSearchList);
        mLineSearchInscription = lineSearchInscription;
        mLineSearchSold = lineSearchSold;
        if (mLineSearchInscription.isChecked()){
            mCheckBoxSearchInscriptionDate.setChecked(true);
        }
        this.checkBoxInscriptionConfiguration(mLineSearchInscription.isChecked());
        if (mLineSearchSold.isChecked()){
            mRadioButtonSold.setChecked(true);
            this.showSoldPanel(true);
        }
        this.configureRecyclerView();
        this.editCalendars();
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
        mTextViewTo.setVisibility(b);
        mEditTextInformationTo.setVisibility(b);
        mImageViewValidationLine.setVisibility(b);
    }

    /**
     *  RECYCLER VIEW
     */

    private void configureRecyclerView(){
        this.mAdapter = new SearchFilterAdapter(mLineSearchList);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView() {
        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.fragment_search_recyclerview_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    List<LineSearch> lineSearchList = mAdapter.getLineSearchList();
                    loadModifierBarManager(lineSearchList, position);
                });
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
        mDateSoldTo.setVisibility(bool);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = mView.findViewById(checkedId);
        switch (radioButton.getId()){
            case R.id.search_radio_button_for_sale:
                showSoldPanel(false);
                mLineSearchSold.setChecked(false);
                break;
            case R.id.search_radio_button_sold:
                showSoldPanel(true);
                mLineSearchSold.setChecked(true);
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
        mLineSearchInscription.setChecked(checked);
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

    DatePickerDialog.OnDateSetListener datePickerInscriptionFrom = (view, year, month, dayOfMonth) -> {
        DateTime calendar = new DateTime();
        calendar = calendar.dayOfMonth().setCopy(dayOfMonth);
        calendar = calendar.monthOfYear().setCopy(month+1);
        calendar = calendar.year().setCopy(year);
        setDateString(calendar,0);
        if (getComparationDates(calendar, mCalendarInscriptionTo)){
            updateLabel(0);
            mCalendarInscriptionFrom = calendar;
            mLineSearchInscription.setInformationFrom(mStringInscriptionFrom);
        }
        else alertDateMessage();
    };

    DatePickerDialog.OnDateSetListener datePickerInscriptionTo = (view, year, month, dayOfMonth) -> {
        DateTime calendar = new DateTime();
        calendar = calendar.dayOfMonth().setCopy(dayOfMonth);
        calendar = calendar.monthOfYear().setCopy(month+1);
        calendar = calendar.year().setCopy(year);
        setDateString(calendar,1);
        if (getComparationDates(mCalendarInscriptionFrom, calendar)){
            updateLabel(1);
            mCalendarInscriptionTo = calendar;
            mLineSearchInscription.setInformationTo(mStringInscriptionTo);
        }
        else alertDateMessage();
    };

    DatePickerDialog.OnDateSetListener datePickerSoldFrom = (view, year, month, dayOfMonth) -> {
        DateTime calendar = new DateTime();
        calendar = calendar.dayOfMonth().setCopy(dayOfMonth);
        calendar = calendar.monthOfYear().setCopy(month+1);
        calendar = calendar.year().setCopy(year);
        setDateString(calendar,2);
        if (getComparationDates(calendar, mCalendarSoldTo)){
            updateLabel(2);
            mCalendarSoldFrom = calendar;
            mLineSearchSold.setInformationFrom(mStringSoldFrom);
        }
        else alertDateMessage();
    };

    DatePickerDialog.OnDateSetListener datePickerSoldTo = (view, year, month, dayOfMonth) -> {
        DateTime calendar = new DateTime();
        calendar = calendar.dayOfMonth().setCopy(dayOfMonth);
        calendar = calendar.monthOfYear().setCopy(month+1);
        calendar = calendar.year().setCopy(year);
        setDateString(calendar,3);
        if (getComparationDates(mCalendarSoldFrom, calendar)){
            updateLabel(3);
            mCalendarSoldTo = calendar;
            mLineSearchSold.setInformationTo(mStringSoldTo);
        }
        else alertDateMessage();
    };

    // Alert user if Start and end dates are reversed
    private void alertDateMessage(){
        Toast.makeText(this.getContext(),"Start and end dates are reversed", Toast.LENGTH_SHORT).show();
    }

    // Define Listeners from Calendars (Begin and End Dates)
    private void editCalendars(){
        mCalendarInscriptionFrom = BusinessApartmentFilters.getDateMemory(mLineSearchInscription.getInformationFrom());
        mCalendarInscriptionTo = BusinessApartmentFilters.getDateMemory(mLineSearchInscription.getInformationTo());
        mCalendarSoldFrom = BusinessApartmentFilters.getDateMemory(mLineSearchSold.getInformationFrom());
        mCalendarSoldTo = BusinessApartmentFilters.getDateMemory(mLineSearchSold.getInformationTo());
        mButtonInscriptionFrom.setText(mCalendarInscriptionFrom.toString("dd/MM/yyyy"));
        mButtonInscriptionTo.setText(mCalendarInscriptionTo.toString("dd/MM/yyyy"));
        mButtonSoldFrom.setText(mCalendarSoldFrom.toString("dd/MM/yyyy"));
        mButtonSoldTo.setText(mCalendarSoldTo.toString("dd/MM/yyyy"));
        mButtonInscriptionFrom.setOnClickListener(v -> new DatePickerDialog(getContext(), datePickerInscriptionFrom,
                mCalendarInscriptionFrom.getYear(), mCalendarInscriptionFrom.getMonthOfYear()-1, mCalendarInscriptionFrom.getDayOfMonth()).show());
        mButtonInscriptionTo.setOnClickListener(v -> new DatePickerDialog(getContext(), datePickerInscriptionTo,
                mCalendarInscriptionTo.getYear(), mCalendarInscriptionTo.getMonthOfYear()-1, mCalendarInscriptionTo.getDayOfMonth()).show());
        mButtonSoldFrom.setOnClickListener(v -> new DatePickerDialog(getContext(), datePickerSoldFrom,
                mCalendarSoldFrom.getYear(), mCalendarSoldFrom.getMonthOfYear()-1, mCalendarSoldFrom.getDayOfMonth()).show());
        mButtonSoldTo.setOnClickListener(v -> new DatePickerDialog(getContext(), datePickerSoldTo,
                mCalendarSoldTo.getYear(), mCalendarSoldTo.getMonthOfYear()-1, mCalendarSoldTo.getDayOfMonth()).show());
    }

    // Subscribe the date String into the the concerned editText (defined with his position)
    private void updateLabel(int pos) {
        switch (pos){
            case 0 : mButtonInscriptionFrom.setText(getDateString(pos)); break;
            case 1 : mButtonInscriptionTo.setText(getDateString(pos)); break;
            case 2 : mButtonSoldFrom.setText(getDateString(pos)); break;
            case 3 : mButtonSoldTo.setText(getDateString(pos)); break;
        }
    }

    // Define the String of the target date
    public void setDateString(DateTime calendar, int position){
        if (position == 0) {
            mStringInscriptionFrom = calendar.toString("dd/MM/yyyy");
        }
        if (position == 1) {
            mStringInscriptionTo = calendar.toString("dd/MM/yyyy");
        }
        if (position == 2) {
            mStringSoldFrom = calendar.toString("dd/MM/yyyy");
        }
        if (position == 3){
            mStringSoldTo = calendar.toString("dd/MM/yyyy");
        }
    }

    // Return the String of the target date
    public String getDateString(int position) {
        if (position == 0) {
            return mStringInscriptionFrom;
        }else if (position == 1) {
            return mStringInscriptionTo;
        }else if (position == 2) {
            return mStringSoldFrom;
        }else {
            return mStringSoldTo;
        }
    }

    // Comparation between the dates
    public boolean getComparationDates(DateTime calendar1, DateTime calendar2){
        return !calendar2.isBefore(calendar1);
    }

    /**
     *  UI CHANGE
     */

    // method from the bottom modifier bar management
    private void loadModifierBarManager(List<LineSearch> lineSearchList, int position){
        if (!lineSearchList.get(position).isATitle()){
            panelChoiceVisibility(false);
            mTextViewTitleBottom.setText(lineSearchList.get(position).getSectionName());
            // INFORMATION FROM AND TO EMPTY
            if (lineSearchList.get(position).getInformationFrom().equals(Objects.requireNonNull(getContext()).getString(R.string.apartment_title_po_single)) ||
                    lineSearchList.get(position).getInformationFrom().equals(LineSearch.EMPTY_CASE)) {
                this.mEditTextInformationFrom.setText("");
            }else {
                this.mEditTextInformationFrom.setText(lineSearchList.get(position).getInformationFrom());
            }
            if (!lineSearchList.get(position).isInformationTo()){
                this.mTextViewTo.setVisibility(View.GONE);
                this.mEditTextInformationTo.setVisibility(View.GONE);
            }else {
                this.mTextViewTo.setVisibility(View.VISIBLE);
                if (lineSearchList.get(position).getInformationTo().equals(getContext().getString(R.string.apartment_title_po_single)) ||
                        lineSearchList.get(position).getInformationTo().equals(LineSearch.EMPTY_CASE)) {
                    this.mEditTextInformationTo.setText("");
                }else {
                    this.mEditTextInformationTo.setText(lineSearchList.get(position).getInformationTo());
                }
            }
            // CheckBox checked or not
            if (lineSearchList.get(position).isChecked()){
                mCheckBoxBottom.setChecked(true);
            }else {
                mCheckBoxBottom.setChecked(false);
            }
            this.mEditTextInformationFrom.setInputType(Utils.getInputType(getContext(), lineSearchList.get(position).getSectionName()));
            this.mEditTextInformationTo.setInputType(Utils.getInputType(getContext(), lineSearchList.get(position).getSectionName()));
            this.validationClick(lineSearchList, position);
        }
    }

    // CHANGE BUTTON CLICK LISTENER
    private void validationClick(List<LineSearch> lineSearchList, int position){
        mImageViewValidationLine.setOnClickListener(v -> {
                if (!mEditTextInformationFrom.getText().toString().equals("")) {
                    if (lineSearchList.get(position).getSectionName().equals(Objects.requireNonNull(getContext()).getString(R.string.apartment_title_postal_code)) && mEditTextInformationFrom.getText().length() != 5) {
                        Toast.makeText(getContext(), getString(R.string.activity_user_modifier_postal_code_advertising), Toast.LENGTH_LONG).show();
                    }else {
                        if (mEditTextInformationTo.getText().toString().equals("")) {
                            mEditTextInformationTo.setText(mEditTextInformationFrom.getText().toString());
                        }
                        if (BusinessApartmentFilters.isTolOk(Integer.valueOf(mEditTextInformationFrom.getText().toString()), Integer.valueOf(mEditTextInformationTo.getText().toString()))) {
                            lineSearchList.get(position).setChecked(mCheckBoxBottom.isChecked());
                            lineSearchList.get(position).setInformationFrom(mEditTextInformationFrom.getText().toString());
                            lineSearchList.get(position).setInformationTo(mEditTextInformationTo.getText().toString());
                        } else {
                            Toast.makeText(getContext(), getString(R.string.search_apartment_tolerances_inverse_warning), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    lineSearchList.get(position).setInformationFrom(LineSearch.EMPTY_CASE);
                    lineSearchList.get(position).setInformationTo(LineSearch.EMPTY_CASE);
                }
            mLineSearchList = lineSearchList;
            mAdapter.setSearchFilterList(mLineSearchList);
            mAdapter.notifyDataSetChanged();
            panelChoiceVisibility(true);
        });
    }

    /**
     *  CALLBACK
     */

    // interface for button clicked
    public interface LineSearchModifierClickedListener{
        void itemClicked(View view, List<LineSearch> lineSearchList, LineSearch lineSearchInscription, LineSearch lineSearchSold);
    }

    //callback for button clicked
    private LineSearchModifierClickedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (LineSearchModifierClickedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemClickedListener");
        }
    }

    @OnClick(R.id.search_load_button)
    public void changeButtonClick(){
        mCallback.itemClicked(mView, mLineSearchList, mLineSearchInscription, mLineSearchSold);
    }
}
