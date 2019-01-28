package com.openclassrooms.realestatemanager.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.database.dao.SearchFilterDao;
import com.openclassrooms.realestatemanager.models.LineSearch;

@Database(entities = {LineSearch.class}, version = 1, exportSchema = false)
public abstract class SearchFilterDatabase extends RoomDatabase {

    private static final String DEFAULT_AERA = LineSearch.EMPTY_CASE;

    // SINGLETON
    private static volatile SearchFilterDatabase INSTANCE;

    // DAO
    public abstract SearchFilterDao mSearchFilterDao();

    // CONNECTION
    public static SearchFilterDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SearchFilterDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SearchFilterDatabase.class, "SearchFilters.db")
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
                contentValues.put("mSectionName", "DATABASE SEARCH FILTERS");
                contentValues.put("mInformationFrom", DEFAULT_AERA);
                contentValues.put("mInformationTo", DEFAULT_AERA);
                contentValues.put("mIsInformationTo", true);
                contentValues.put("isATitle", false);
                contentValues.put("isChecked", true);

                db.insert("LineSearch", OnConflictStrategy.IGNORE, contentValues);



            }
        };
    }

}
