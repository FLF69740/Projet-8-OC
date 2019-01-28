package com.openclassrooms.realestatemanager.apartmentfilters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.LineSearch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFilterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.section_separator) ImageView mPinkShape;
    @BindView(R.id.search_picture_validation) ImageView mImageViewChecked;
    @BindView(R.id.search_textView_title) TextView mTitle;
    @BindView(R.id.search_editText_information_from) TextView mFrom;
    @BindView(R.id.search_textView_to) TextView mTextViewSeparator;
    @BindView(R.id.search_editText_information_to) TextView mTo;

    public SearchFilterViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithSearchFilterInformation(LineSearch lineSearch){
        mPinkShape.setVisibility(View.INVISIBLE);
        mImageViewChecked.setVisibility(View.INVISIBLE);
        mFrom.setVisibility(View.INVISIBLE);
        mTextViewSeparator.setVisibility(View.INVISIBLE);
        mTo.setVisibility(View.INVISIBLE);

        mTitle.setText(lineSearch.getSectionName());

        if (lineSearch.isATitle()){
            mPinkShape.setVisibility(View.VISIBLE);
        }else {
            mFrom.setVisibility(View.VISIBLE);
            mFrom.setText(getInformationState(lineSearch.getInformationFrom()));
            if (lineSearch.isInformationTo()) {
                mTextViewSeparator.setVisibility(View.VISIBLE);
                mTo.setVisibility(View.VISIBLE);
                mTo.setText(getInformationState(lineSearch.getInformationTo()));
            }
            if (lineSearch.isChecked()){
                mImageViewChecked.setVisibility(View.VISIBLE);
            }
        }
    }

    public String getInformationState(String information){
        if (information.equals(LineSearch.EMPTY_CASE)){
            return LineSearch.BLANK;
        }else {
            return information;
        }
    }
}
