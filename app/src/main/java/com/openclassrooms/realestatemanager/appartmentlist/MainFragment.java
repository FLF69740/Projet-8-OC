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
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    private View mView;
    private List<Apartment> mApartmentList;
    private ApartmentListAdapter mAdapter;

    @BindView(R.id.recycler_view_listing)RecyclerView mRecyclerView;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, mView);

        mApartmentList = (List<Apartment>) getArguments().getSerializable("list");
        if (mApartmentList != null && !mApartmentList.isEmpty()){
            configureRecyclerView();
            configureOnClickRecyclerView();
        }

        return mView;
    }

    /**
     *  RECYCLERVIEW
     */

    private void configureRecyclerView(){
        this.mAdapter = new ApartmentListAdapter(mApartmentList);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.fragment_main_recyclerview_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Apartment apartment = mAdapter.getApartment(position);
                    mCallback.itemClicked(mView, apartment);
                });
    }

    /**
     *  CALLBACK
     */

    // interface for button clicked
    public interface ItemClickedListener{
        void itemClicked(View view, Apartment apartment);
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
