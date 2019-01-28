package com.openclassrooms.realestatemanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.openclassrooms.realestatemanager.models.LineSearch;
import com.openclassrooms.realestatemanager.repositories.LineSearchDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class SearchFilterViewModel extends ViewModel {

    // REPOSITORY
    private final LineSearchDataRepository mLineSearchDataRepository;
    private final Executor mExecutor;

    @Nullable private LiveData<List<LineSearch>> mCurrentLinesSearch;

    public SearchFilterViewModel(LineSearchDataRepository lineSearchDataRepository, Executor executor) {
        mLineSearchDataRepository = lineSearchDataRepository;
        mExecutor = executor;
    }

    public void init(){
        if (this.mCurrentLinesSearch != null){
            return;
        }
        this.getLinesSearch();
    }

    // GET LINES SEARCH
    public LiveData<List<LineSearch>> getLinesSearch(){
        return mLineSearchDataRepository.getLinesSearch();
    }

    // CREATE
    public void createLineSearch(LineSearch lineSearch){
        mExecutor.execute(()-> mLineSearchDataRepository.createLineSearch(lineSearch));
    }

    // UPDATE
    public void updateLineSearch(LineSearch lineSearch){
        mExecutor.execute(()-> mLineSearchDataRepository.updateLineSearch(lineSearch));
    }
}
