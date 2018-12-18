package com.openclassrooms.realestatemanager.appartmentlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ApartmentListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_town) TextView mTextViewTown;
    @BindView(R.id.fragment_main_type) TextView mTextViewType;
    @BindView(R.id.fragment_main_price) TextView mTextViewPrice;
    @BindView(R.id.fragment_main_picture) ImageView mImageViewPicture;
    @BindView(R.id.background_item)ImageView mBackground;

    public ApartmentListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithApartmentInformations(Apartment apartment, boolean selectedApartment){

        this.mTextViewType.setText(apartment.getType());
        this.mTextViewTown.setText(apartment.getTown());
        this.mTextViewPrice.setText(String.valueOf(apartment.getPrice()));
        if (apartment.getUrlPicture().equals(Apartment.EMPTY_CASE)){
            this.mImageViewPicture.setImageResource(R.drawable.image_realestate);
        }

        this.mBackground.setSelected(selectedApartment);
        this.mTextViewPrice.setSelected(selectedApartment);
    }


}
