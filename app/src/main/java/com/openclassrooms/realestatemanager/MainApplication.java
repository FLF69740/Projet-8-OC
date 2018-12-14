package com.openclassrooms.realestatemanager;

import android.app.Application;
import android.support.v4.app.FragmentManager;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FragmentManager.enableDebugLogging(true);
    }
}
