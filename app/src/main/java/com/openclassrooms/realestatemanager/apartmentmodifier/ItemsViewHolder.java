package com.openclassrooms.realestatemanager.apartmentmodifier;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.Item;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;

    @BindView(R.id.picture_title)ImageView mImageViewPicture;
    @BindView(R.id.clear_button_item)ImageView mClearButton;
    @BindView(R.id.textView_title)TextView mTitle;
    @BindView(R.id.editText_information)TextView mInformation;
    @BindView(R.id.section_separator)ImageView mSectionSeparator;

    public ItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void updateWithItemInformations(Item item){

        mTitle.setText(item.getTitle());
        mInformation.setText(item.getInformation());
        mSectionSeparator.setVisibility(View.INVISIBLE);


        // It's not a picture
        if (!item.getAPicture()){
            mImageViewPicture.setVisibility(View.INVISIBLE);
            mClearButton.setVisibility(View.GONE);

            // it's a title
            if (item.getATitle()){
                mSectionSeparator.setVisibility(View.VISIBLE);
                mTitle.setTextSize(20);
            } else {
                // it's not a title and not a picture
                mImageViewPicture.setVisibility(View.GONE);
            }
        } else if (item.getAPicture() && item.getInformation().equals(itemView.getContext().getString(R.string.fragment_modification_recycler_no_picture))){
            mClearButton.setVisibility(View.GONE);
        } else {
            Glide.with(this.mItemView).load(item.getUrlPicture()).apply(RequestOptions.centerCropTransform()).into(this.mImageViewPicture);
        }


    }

}
