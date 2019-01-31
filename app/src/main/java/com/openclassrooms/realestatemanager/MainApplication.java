package com.openclassrooms.realestatemanager;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.FragmentManager;

public class MainApplication extends Application {

    public static final String CHANNEL = "CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();
        FragmentManager.enableDebugLogging(true);
        createNotificationChannel();
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.fragment_creation_notification_title);
            String description = getString(R.string.fragment_creation_notification_description);
            NotificationChannel channel = new NotificationChannel(CHANNEL, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
