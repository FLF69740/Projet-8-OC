package com.openclassrooms.realestatemanager.apartmentmap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.httprequest.LocationRx;
import com.openclassrooms.realestatemanager.models.httprequest.LocationStreams;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private Disposable mDisposable;
    private List<Apartment> mApartmentList = new ArrayList<>();
    private MapView mMapView;
    private View mView;
    private GoogleMap mMap;
    private Location mLocation;
    private double mLatitude, mLongitude;
    private ProgressBar mProgressBar;
    private ImageView mLogoMapNoConnection;
    private TextView mTextNoConnection;
    private Button mButtonReload;
    private boolean mNoCycle;

    public MapFragment() {}

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNoCycle = false;
    }

    public void refresh(List<Apartment> apartmentList) {
        mApartmentList = apartmentList;
        if (mApartmentList != null & Utils.isNetworkAvailable(mView.getContext())){
            this.executeGeocodeLocationRequest();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        mLogoMapNoConnection = mView.findViewById(R.id.map_logo_no_connection);
        mTextNoConnection = mView.findViewById(R.id.map_text_no_connection);
        mButtonReload = mView.findViewById(R.id.map_reload_btn);
        mProgressBar = mView.findViewById(R.id.map_progressbar);
        mMapView = mView.findViewById(R.id.map);
        mButtonReload.setOnClickListener(this);
        return mView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;}
        mMap.setMyLocationEnabled(true);
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 10, 30);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLatitude, mLongitude)));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapBuilder(Utils.isInternetAvailable(view.getContext()));
    }

    private void mapBuilder(boolean buid){
        if (buid) {
            mLogoMapNoConnection.setVisibility(View.INVISIBLE);
            mTextNoConnection.setVisibility(View.INVISIBLE);
            mButtonReload.setVisibility(View.INVISIBLE);

            GPSTracker GPSTracker = new GPSTracker();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                mLocation = GPSTracker.getLocation(this.getContext());
            if (mLocation != null) {
                mLatitude = mLocation.getLatitude();
                mLongitude = mLocation.getLongitude();
            }
            if (mMapView != null) {
                mProgressBar.setVisibility(View.VISIBLE);
                mMapView.setVisibility(View.INVISIBLE);
                mMapView.onCreate(null);
                mMapView.onResume();
                mMapView.getMapAsync(this);
            }
        } else {
            mLogoMapNoConnection.setVisibility(View.VISIBLE);
            mTextNoConnection.setVisibility(View.VISIBLE);
            mButtonReload.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mMapView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     *  RxJava
     */

    private void executeGeocodeLocationRequest(){
        if (!mNoCycle) {
            List<String> addresses = new ArrayList<>();
            for (int i = 0; i < mApartmentList.size(); i++) {
                addresses.add(mApartmentList.get(i).getAdress() + " " + mApartmentList.get(0).getTown());
            }
            this.mDisposable = LocationStreams.streamsLocations(addresses, getString(R.string.api_key))
                    .subscribeWith(new DisposableObserver<List<LocationRx>>() {
                        @Override
                        public void onNext(List<LocationRx> locationRxes) {
                            markersCreation(locationRxes);
                        }
                        @Override
                        public void onError(Throwable e) {}
                        @Override
                        public void onComplete() {}
                    });
            mNoCycle = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    /**
     *  MARKER
     */

    // Create Marker from Observable
    private void markersCreation(List<LocationRx> locationRxList){
        Marker[] listMarker = new Marker[locationRxList.size()];
        mMap.clear();
        for (int i = 0 ; i < locationRxList.size() ; i++){

            listMarker[i] = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(locationRxList.get(i).getResults().get(0).getGeometry().getLocation().getLat(),
                            locationRxList.get(i).getResults().get(0).getGeometry().getLocation().getLng()))
                    .title(locationRxList.get(i).getResults().get(0).getFormattedAddress()));
            listMarker[i].setTag(i);
        }
        mMap.setOnMarkerClickListener(this);
        mProgressBar.setVisibility(View.INVISIBLE);
        mMapView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer markerTag = (Integer) marker.getTag();
        if (markerTag != null) mCallback.onResultMarkerTransmission(this.mView, mApartmentList.get(markerTag));
        return false;
    }

    /**
     *  CALLBACK
     */

    private OnClickedResultMarker mCallback;

    @Override
    public void onClick(View v) {
        mapBuilder(Utils.isInternetAvailable(v.getContext()));
        mCallback.executeReloadCallback();
    }

    public interface OnClickedResultMarker{
        void onResultMarkerTransmission(View view, Apartment apartment);
        void executeReloadCallback();
    }

    //Parent activity will automatically subscribe to callback
    private void createCallbackToParentActivity(){
        try {mCallback = (OnClickedResultMarker) getActivity();}
        catch (ClassCastException e) {throw new ClassCastException(e.toString()+ " must implement OnClickedResultMarker");}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

}
