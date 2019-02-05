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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String BUNDLE_KEY_APARTMENT_LIST = "BUNDLE_KEY_APARTMENT_LIST";

    private List<Apartment> mApartmentList = new ArrayList<>();
    private MapView mMapView;
    private View mView;
    private GoogleMap mMap;
    private Location mLocation;
    private double mLatitude, mLongitude;
    private ProgressBar mProgressBar;

    public MapFragment() {}

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void refresh(List<Apartment> apartmentList) {
        mApartmentList = apartmentList;
        if (mApartmentList != null){
            Toast.makeText(getContext(), "size : " + mApartmentList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        
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
        mCallback.executePlacesCallback(this.mView,String.valueOf(mLatitude)+","+String.valueOf(mLongitude));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = mView.findViewById(R.id.map_progressbar);
        GPSTracker GPSTracker = new GPSTracker();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) mLocation = GPSTracker.getLocation(this.getContext());
        if (mLocation != null) {
            mLatitude = mLocation.getLatitude();
            mLongitude = mLocation.getLongitude();
        }
        mMapView = mView.findViewById(R.id.map);
        if (mMapView != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mMapView.setVisibility(View.VISIBLE);
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }


    /**
     *  MARKER
     */

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     *  CALLBACK
     */

    private OnClickedResultMarker mCallback;

    public interface OnClickedResultMarker{
        void onResultMarkerTransmission(View view, String title);
        void executePlacesCallback(View view, String coordinates);
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
