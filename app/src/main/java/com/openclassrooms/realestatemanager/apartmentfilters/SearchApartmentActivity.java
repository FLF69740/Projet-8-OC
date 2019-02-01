package com.openclassrooms.realestatemanager.apartmentfilters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelSearchFactory;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.LineSearch;
import com.openclassrooms.realestatemanager.viewmodel.SearchFilterViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchApartmentActivity extends BaseActivity implements SearchApartmentFragment.LineSearchModifierClickedListener {

    private SearchFilterViewModel mSearchFilterViewModel;
    private List<LineSearch> mLineSearchList;
    private String mMoney;
    private String mDimension;

    @Override
    protected Fragment getFirstFragment() {
        return SearchApartmentFragment.newInstance();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search_apartment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.frame_layout_search_apartment;
    }

    @Override
    protected Fragment getSecondFragment() {
        return null;
    }

    @Override
    protected int getSecondFragmentLayout() {
        return 0;
    }

    @Override
    protected boolean isAChildActivity() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApartmentList = (List<Apartment>) getIntent().getSerializableExtra(BaseActivity.BUNDLE_APARTMENTLIST_TO_SEARCH_ACTIVITY);
        mLineSearchList = new ArrayList<>();
        this.configureViewModelSearch();
        if (savedInstanceState == null) {
            this.readSearchFilterDB();
        }
    }

    // load the search rules book
    private void configureViewModelSearch(){
        ViewModelSearchFactory viewModelSearchFactory = Injection.provideViewModelSearchFactory(this);
        this.mSearchFilterViewModel = ViewModelProviders.of(this, viewModelSearchFactory).get(SearchFilterViewModel.class);
        this.mSearchFilterViewModel.init();
    }

    // Write on search rules book
    private void readSearchFilterDB(){
        this.mSearchFilterViewModel.getLinesSearch().observe(this, this::configureList);
    }

    // Configure Filters list
    private void configureList(List<LineSearch> searchList){
        mMoney = this.getSharedPreferences(SHARED_MONEY, MODE_PRIVATE).getString(BUNDLE_KEY_ACTIVE_MONEY, getString(R.string.loan_simulation_dollar));
        mDimension = this.getSharedPreferences(SHARED_DIMENSION, Context.MODE_PRIVATE).getString(BUNDLE_KEY_ACTIVE_DIMENSION, getString(R.string.units_square));
        if (searchList.size() == 1){
            List<LineSearch> firstList = new ArrayList<>(BusinessApartmentFilters.createFirstSearchFilterDB(this, mMoney, mDimension));
            for (int i = 0; i < firstList.size(); i++){
                createLineSearch(firstList.get(i));
            }
        } else {
            mLineSearchList = loadUnits(this, searchList, mMoney, mDimension);
            mLineSearchList.remove(0);
        }
        LineSearch lineSearchInscription = mLineSearchList.get(0);
        LineSearch lineSearchSold = mLineSearchList.get(1);
        mLineSearchList.remove(0);
        mLineSearchList.remove(0);
        ((SearchApartmentFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(mLineSearchList, lineSearchInscription, lineSearchSold);
    }

    /**
     *  DATA FILTER
     */

    // create Filter
    private void createLineSearch(LineSearch lineSearch){
        this.mSearchFilterViewModel.createLineSearch(lineSearch);
        mLineSearchList.add(lineSearch);
    }

    public static final String BUNDLE_APARTMENT_LIST_SEARCH = "BUNDLE_APARTMENT_LIST_SEARCH";

    @Override
    public void itemClicked(View view, List<LineSearch> lineSearchList, LineSearch lineSearchInscription, LineSearch lineSearchSold) {
        mSearchFilterViewModel.updateLineSearch(lineSearchInscription);
        mSearchFilterViewModel.updateLineSearch(lineSearchSold);
        for (int i = 0; i < lineSearchList.size(); i++){
            mSearchFilterViewModel.updateLineSearch(lineSearchList.get(i));
        }
        ApartmentSelector apartmentSelector = new ApartmentSelector(mMoney, mDimension);
        mApartmentList = apartmentSelector.getSelectedApartments(mApartmentList, lineSearchList, lineSearchInscription, lineSearchSold);
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_APARTMENT_LIST_SEARCH, (Serializable) mApartmentList);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     *  UNITS TRANSITION
     */

    private List<LineSearch> loadUnits(Context context, List<LineSearch> searchList, String money, String dimension){
        for (int i = 0 ; i < searchList.size() ; i++){
            if (searchList.get(i).getSectionName().contains(context.getString(R.string.apartment_title_price))){
                searchList.get(i).setSectionName(context.getString(R.string.apartment_title_price) + " " + money + " ");
            }
            if (searchList.get(i).getSectionName().contains(context.getString(R.string.apartment_title_square))){
                searchList.get(i).setSectionName(context.getString(R.string.apartment_title_square) + " " + dimension + " ");
            }
        }
        return searchList;
    }


}
