package com.openclassrooms.realestatemanager.appartmentlist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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

    public void updateWithApartmentInformations(Apartment apartment, boolean selectedApartment, Context context){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        @ColorInt int color = typedValue.data;
        String colorAccent = "#" + String.valueOf(color);

        this.mTextViewType.setText(apartment.getType());
        this.mTextViewTown.setText(apartment.getTown());
        this.mTextViewPrice.setText(String.valueOf(apartment.getPrice()));
        if (apartment.getUrlPicture().equals("EMPTY")){
            this.mImageViewPicture.setImageResource(R.drawable.image_realestate);
        }

        if (selectedApartment){
            mTextViewPrice.setTextColor(Color.parseColor("#FFFFFF"));
            mBackground.setBackgroundColor(Color.parseColor(colorAccent));

        } else {
            mTextViewPrice.setTextColor(Color.parseColor(colorAccent));
            mBackground.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }


}
