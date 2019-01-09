package com.openclassrooms.realestatemanager;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DaoTest {

    private RealEstateManagerDatabase mDatabase;
    private static long USER_ID_FIRST = 1;
    private static long USER_ID_SECOND = 2;
    private static User USER_DEMO  = new User("Jane", "https://www.google.fr", "main test bot");
    private static User USER_DEMO_SECOND  = new User("John", "https://www.google.fr", "main test bot");
    private static Apartment APARTMENT_DEMO = new Apartment("Loft", 350000, "13, Rue du paradis",
            69001, "Lyon", "13/01/2018", USER_ID_FIRST);
    private static Apartment APARTMENT_DEMO_SECOND = new Apartment("Duplex", 435000, "7b, Avenue du canon",
            69003, "Lyon", "09/10/2018", USER_ID_SECOND);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception{
        this.mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception{
        this.mDatabase.close();
    }

    /**
     *  TEST USER
     */

    @Test
    public void insertAndGetUser() throws Exception{
        this.mDatabase.mUserDao().createUser(USER_DEMO);
        this.mDatabase.mUserDao().createUser(USER_DEMO_SECOND);

        User user = LiveDataTestUtil.getValue(this.mDatabase.mUserDao().getUser(USER_ID_FIRST));
        User user2 = LiveDataTestUtil.getValue(this.mDatabase.mUserDao().getUser(USER_ID_SECOND));

        assertEquals(user.getUsername(), USER_DEMO.getUsername());
        assertEquals(user.getUsername(), "Jane");
        assertEquals((long) user.getId(), USER_ID_FIRST);

        assertEquals(user2.getUsername(), USER_DEMO_SECOND.getUsername());
        assertEquals(user2.getUsername(), "John");
        assertEquals((long) user2.getId(), USER_ID_SECOND);
    }

    @Test
    public void insertAndUpdateUser() throws Exception{
        this.mDatabase.mUserDao().createUser(USER_DEMO);

        User user = LiveDataTestUtil.getValue(this.mDatabase.mUserDao().getUser(USER_ID_FIRST));
        assertEquals(user.getUsername(), "Jane");

        user.setUsername("Jane Do");
        this.mDatabase.mUserDao().updateUser(user);
        User userVerif = LiveDataTestUtil.getValue(this.mDatabase.mUserDao().getUser(USER_ID_FIRST));

        assertEquals(userVerif.getUsername(), "Jane Do");
    }

    /**
     *  TEST APARTMENT
     */

    @Test
    public void insertAndGetApartment() throws Exception{
        this.mDatabase.mUserDao().createUser(USER_DEMO);
        this.mDatabase.mUserDao().createUser(USER_DEMO_SECOND);
        this.mDatabase.mApartmentDao().insertApartment(APARTMENT_DEMO);
        this.mDatabase.mApartmentDao().insertApartment(APARTMENT_DEMO_SECOND);

        List<Apartment> apartments = LiveDataTestUtil.getValue(this.mDatabase.mApartmentDao().getApartments(USER_ID_FIRST));
        List<Apartment> apartments2 = LiveDataTestUtil.getValue(this.mDatabase.mApartmentDao().getApartments(USER_ID_SECOND));

        assertEquals(apartments.size(), 1);
        assertEquals(apartments2.size(), 1);

        long APARTMENT_ID_FIRST = 1;
        assertEquals(apartments.get(0).getId(), APARTMENT_ID_FIRST);
        long APARTMENT_ID_SECOND = 2;
        assertEquals(apartments2.get(0).getId(), APARTMENT_ID_SECOND);

        assertEquals(apartments.get(0).getUserId(), USER_ID_FIRST);
        assertEquals(apartments.get(0).getPrice(), 350000);
        assertEquals(apartments2.get(0).getUserId(), USER_ID_SECOND);
        assertEquals(apartments2.get(0).getPrice(), 435000);
    }

    @Test
    public void insertAndUpdateApartment() throws Exception{
        this.mDatabase.mUserDao().createUser(USER_DEMO);
        this.mDatabase.mApartmentDao().insertApartment(APARTMENT_DEMO);

        List<Apartment> apartments = LiveDataTestUtil.getValue(this.mDatabase.mApartmentDao().getApartments(USER_ID_FIRST));

        assertEquals(apartments.get(0).getSold(), false);

        apartments.get(0).setSold(true);
        this.mDatabase.mApartmentDao().updateApartment(apartments.get(0));
        List<Apartment> apartments_updated = LiveDataTestUtil.getValue(this.mDatabase.mApartmentDao().getApartments(USER_ID_FIRST));

        assertEquals(apartments_updated.get(0).getSold(), true);
    }



}
