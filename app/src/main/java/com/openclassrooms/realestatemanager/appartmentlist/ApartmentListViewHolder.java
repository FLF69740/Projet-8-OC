package com.openclassrooms.realestatemanager.appartmentlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Apartment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ApartmentListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_town) TextView mTextViewTown;
    @BindView(R.id.fragment_main_type) TextView mTextViewType;
    @BindView(R.id.fragment_main_price) TextView mTextViewPrice;
    @BindView(R.id.fragment_main_picture) ImageView mImageViewPicture;
    @BindView(R.id.background_item)ImageView mBackground;

    private View mItemView;

    public ApartmentListViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void updateWithApartmentInformations(Apartment apartment, boolean selectedApartment, String moneyUnit){

        this.mTextViewType.setText(apartment.getType());
        this.mTextViewTown.setText(apartment.getTown());

        int price = apartment.getPrice();
        if (moneyUnit.equals(mItemView.getContext().getString(R.string.loan_simulation_euro)) && price != 0){
            price = Utils.convertDollarToEuro(price);
        }
        moneyUnit += Utils.getPriceFormat(price);
        this.mTextViewPrice.setText(moneyUnit);

        if (BitmapStorage.isFileExist(mItemView.getContext(), BitmapStorage.getFirstPhotoName(apartment))) {
            this.mImageViewPicture.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), BitmapStorage.getFirstPhotoName(apartment)));
        } else {
            this.mImageViewPicture.setImageResource(R.drawable.image_realestate);
        }

        this.mBackground.setSelected(selectedApartment);
        this.mTextViewPrice.setSelected(selectedApartment);
    }
}
