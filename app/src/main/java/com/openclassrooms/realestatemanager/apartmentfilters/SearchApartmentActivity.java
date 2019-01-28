package com.openclassrooms.realestatemanager.apartmentfilters;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Controller.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelSearchFactory;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.LineSearch;
import com.openclassrooms.realestatemanager.viewmodel.SearchFilterViewModel;

import java.util.List;

public class SearchApartmentActivity extends BaseActivity {

    private SearchFilterViewModel mSearchFilterViewModel;
    private List<LineSearch> mLineSearchList;

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
        this.configureViewModelSearch();
        this.readSearchFilterDB();

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
        mLineSearchList = searchList;
        Toast.makeText(this, "line : " + mLineSearchList.size(), Toast.LENGTH_SHORT).show();
    }




}
