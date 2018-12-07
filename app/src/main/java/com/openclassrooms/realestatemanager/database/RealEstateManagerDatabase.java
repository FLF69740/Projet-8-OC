package com.openclassrooms.realestatemanager.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.database.dao.ApartmentDao;
import com.openclassrooms.realestatemanager.database.dao.UserDao;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;

@Database(entities = {Apartment.class, User.class}, version = 1, exportSchema = false)
public abstract class RealEstateManagerDatabase extends RoomDatabase {

    // SINGLETON
    private static volatile RealEstateManagerDatabase INSTANCE;

    // DAO
    public abstract UserDao mUserDao();
    public abstract ApartmentDao mApartmentDao();

    // CONNECTION
    public static RealEstateManagerDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (RealEstateManagerDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RealEstateManagerDatabase.class, "MyDatabse.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // INITIALISATION
    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("mId", 1);
                contentValues.put("mUsername", "Black Kisama");
                contentValues.put("mUrlPicture", "http://www.leo-asso.com/uploads/equipes/logos/tournoi-leo-10-urban-laser-vichy-logo-black-kisama.jpg");
                contentValues.put("mUserDescription", "Big Boss de la société, employé de l'année cinq ans consécutifs");

                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }

}
