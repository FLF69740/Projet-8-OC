package com.openclassrooms.realestatemanager.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.repositories.LineSearchDataRepository;
import com.openclassrooms.realestatemanager.viewmodel.SearchFilterViewModel;

import java.util.concurrent.Executor;

public class ViewModelSearchFactory implements ViewModelProvider.Factory {

    private final LineSearchDataRepository mLineSearchDataRepository;
    private final Executor mExecutor;

    public ViewModelSearchFactory(LineSearchDataRepository lineSearchDataRepository, Executor executor) {
        mLineSearchDataRepository = lineSearchDataRepository;
        mExecutor = executor;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchFilterViewModel.class)) {
            return (T) new SearchFilterViewModel(mLineSearchDataRepository, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
