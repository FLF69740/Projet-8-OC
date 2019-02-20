package com.openclassrooms.realestatemanager.models.httprequest;

import com.openclassrooms.realestatemanager.models.Apartment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LocationStreams {

    // request for map location Geocode
    private static Observable<LocationRx> streamLocation(String address, String apiKey){
        GeoCodeService geoCodeService = GeoCodeService.retrofit.create(GeoCodeService.class);
        return geoCodeService.getLocation(address, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(30, TimeUnit.SECONDS);
    }

    public static Observable<List<LocationRx>> streamsLocations(List<String> addresses, String apiKey){
        return Observable.fromIterable(addresses)
                .concatMap((Function<String, ObservableSource<LocationRx>>) s -> streamLocation(s, apiKey))
                .toList()
                .toObservable();
    }
}
