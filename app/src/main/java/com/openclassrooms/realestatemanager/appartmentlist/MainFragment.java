package com.openclassrooms.realestatemanager.appartmentlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;
import java.io.Serializable;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    private static final String BUNDLE_KEY_ADAPTER_POSITION = "BUNDLE_KEY_ADAPTER_POSITION";
    private static final String BUNDLE_KEY_APARTMENT_LIST = "BUNDLE_KEY_APARTMENT_LIST";
    private View mView;
    private List<Apartment> mApartmentList;
    private ApartmentListAdapter mAdapter;
    private int mSelectedApartment;

    @BindView(R.id.recycler_view_listing)RecyclerView mRecyclerView;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(List<Apartment> apartmentList, String position){
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle(2);
        args.putSerializable(BUNDLE_KEY_APARTMENT_LIST, (Serializable) apartmentList);
        args.putString(BUNDLE_KEY_ADAPTER_POSITION, position);
        mainFragment.setArguments(args);

        return mainFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, mView);

        mApartmentList = (List<Apartment>) getArguments().getSerializable(BUNDLE_KEY_APARTMENT_LIST);
        String stringAdapterPosition = getArguments().getString(BUNDLE_KEY_ADAPTER_POSITION);
        if (mApartmentList != null && !mApartmentList.isEmpty()){
            if (stringAdapterPosition != null) {
                mSelectedApartment = Integer.valueOf(stringAdapterPosition);
            } else {
                mSelectedApartment = mApartmentList.size();
            }
            configureRecyclerView();
            configureOnClickRecyclerView();
        }

        return mView;
    }

    /**
     *  RECYCLERVIEW
     */

    private void configureRecyclerView(){
        this.mAdapter = new ApartmentListAdapter(mApartmentList, mSelectedApartment);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.fragment_main_recyclerview_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Apartment apartment = mAdapter.getApartment(position);
                    mCallback.itemClicked(mView, apartment, String.valueOf(position));
                    setAdapterLocation(position);
                });
    }

    private void setAdapterLocation(int position){
        mSelectedApartment = position;
        mAdapter.setSelectedApartment(mSelectedApartment);
        mAdapter.notifyDataSetChanged();
    }

    public void refresh(List<Apartment> apartmentList, long apartmentId) {
        mApartmentList = apartmentList;
        mSelectedApartment = (int) apartmentId - 1;
        configureRecyclerView();
        configureOnClickRecyclerView();
    }

    /**
     *  CALLBACK
     */

    // interface for button clicked
    public interface ItemClickedListener{
        void itemClicked(View view, Apartment apartment, String adapterPosition);
    }

    //callback for button clicked
    private ItemClickedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ItemClickedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemClickedListener");
        }
    }

}
