package com.openclassrooms.realestatemanager;

import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.provider.ApartmentContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ApartmentContentProviderTest {

    // FOR DATA
    private ContentResolver mContentResolver;

    // DATA SET FOR TEST
    private static long USER_ID = 2;

    @Before
    public void setUp(){
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
    }

    @Test
    public void getApartmentWhenNoApartmentInserted(){
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ApartmentContentProvider.URI_ITEM, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        cursor.close();
    }

    @Test
    public void insertAndGetApartment(){
        final Uri userUri = mContentResolver.insert(ApartmentContentProvider.URI_ITEM, generateApartment());
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ApartmentContentProvider.URI_ITEM, USER_ID), null, null, null, null);

        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("mType")), is("Test Content Provider"));
    }

    private ContentValues generateApartment(){
        final ContentValues values = new ContentValues();
        values.put("mType", "Test Content Provider");
        values.put("mPrice", "1");
        values.put("mAdress", "1, Rue de Sèze");
        values.put("mPostalCode", "69006");
        values.put("mTown", "LYON");
        values.put("mDateInscription", "01/01/2019");
        values.put("mUserId", USER_ID);
        values.put("mDimension", 0);
        values.put("mRoomNumber",0);
        values.put("mDescription", Apartment.EMPTY_CASE);
        values.put("mUrlPicture", Apartment.EMPTY_CASE);
        values.put("mPoInterest", Apartment.EMPTY_CASE);
        values.put("mSold", false);
        values.put("mDateSold", Apartment.EMPTY_CASE);
        return values;
    }

}
