package com.openclassrooms.realestatemanager.appartmentlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;

import java.util.List;

public class ApartmentListAdapter extends RecyclerView.Adapter<ApartmentListViewHolder> {

    private List<Apartment> mApartmentList;
    private int mSelectedApartment;

    public ApartmentListAdapter(List<Apartment> apartmentList, int selectedApartment) {
        this.mApartmentList = apartmentList;
        this.mSelectedApartment = selectedApartment;
    }

    @NonNull
    @Override
    public ApartmentListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_recyclerview_item, viewGroup, false);
        return new ApartmentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartmentListViewHolder apartmentListViewHolder, int i) {
        Boolean selected = mSelectedApartment == i;
        apartmentListViewHolder.updateWithApartmentInformations(this.mApartmentList.get(i), selected);
    }

    @Override
    public int getItemCount() {
        return this.mApartmentList.size();
    }

    public Apartment getApartment(int position){
        return mApartmentList.get(position);
    }

    public void setSelectedApartment(int position){
        mSelectedApartment = position;
    }
}
