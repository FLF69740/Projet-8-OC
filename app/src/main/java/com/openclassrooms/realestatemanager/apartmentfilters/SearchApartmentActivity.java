package com.openclassrooms.realestatemanager.apartmentfilters;

import android.arch.lifecycle.ViewModelProviders;
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
import java.util.ArrayList;
import java.util.List;

public class SearchApartmentActivity extends BaseActivity implements SearchApartmentFragment.LineSearchModifierClickedListener {

    private SearchFilterViewModel mSearchFilterViewModel;
    private List<LineSearch> mLineSearchList;
    private LineSearch mLineSearchInscription, mLineSearchSold;

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
        if (searchList.size() == 1){
            List<LineSearch> firstList = new ArrayList<>(BusinessApartmentFilters.createFirstSearchFilterDB(this));
            for (int i = 0; i < firstList.size(); i++){
                createLineSearch(firstList.get(i));
            }
        } else {
            mLineSearchList = searchList;
            mLineSearchList.remove(0);
        }
        mLineSearchInscription = mLineSearchList.get(0);
        mLineSearchSold = mLineSearchList.get(1);
        mLineSearchList.remove(0);
        mLineSearchList.remove(0);
        ((SearchApartmentFragment) getSupportFragmentManager().findFragmentById(getFragmentLayout())).refresh(mLineSearchList, mLineSearchInscription, mLineSearchSold);
    }

    /**
     *  DATA FILTER
     */

    // create Filter
    private void createLineSearch(LineSearch lineSearch){
        this.mSearchFilterViewModel.createLineSearch(lineSearch);
        mLineSearchList.add(lineSearch);
    }


    @Override
    public void itemClicked(View view, List<LineSearch> lineSearchList, LineSearch lineSearchInscription, LineSearch lineSearchSold) {
        mSearchFilterViewModel.updateLineSearch(lineSearchInscription);
        mSearchFilterViewModel.updateLineSearch(lineSearchSold);
        for (int i = 0; i < lineSearchList.size(); i++){
            mSearchFilterViewModel.updateLineSearch(lineSearchList.get(i));
        }
        finish();
    }
}
