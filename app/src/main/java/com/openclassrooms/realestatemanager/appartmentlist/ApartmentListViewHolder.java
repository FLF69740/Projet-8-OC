package com.openclassrooms.realestatemanager.appartmentlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApartmentListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_town) private TextView mTextViewTown;
    @BindView(R.id.fragment_main_type) private TextView mTextViewType;
    @BindView(R.id.fragment_main_price) private TextView mTextViewPrice;

    public ApartmentListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithApartmentInformations(Apartment apartment){
        this.mTextViewType.setText(apartment.getType());
        this.mTextViewTown.setText(apartment.getTown());
        this.mTextViewPrice.setText(apartment.getPrice());
    }


}
