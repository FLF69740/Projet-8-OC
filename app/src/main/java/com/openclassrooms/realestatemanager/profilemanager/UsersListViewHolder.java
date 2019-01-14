package com.openclassrooms.realestatemanager.profilemanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_profile_picture)ImageView mImageViewPicture;
    @BindView(R.id.fragment_profile_name)TextView mTextViewName;
    @BindView(R.id.fragment_profile_active_user)ImageView mImageViewActiveIndicator;
    @BindView(R.id.background_user_item)ImageView mBackground;

    private View mItemView;

    public UsersListViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, mItemView);
    }

    public void updateWithUserInformations(User user, boolean selectedUser, int activeUser, int position){

        this.mTextViewName.setText(user.getUsername());

        if (!user.getUrlPicture().equals(User.EMPTY_CASE) && BitmapStorage.isFileExist(mItemView.getContext(), user.getUrlPicture())) {
            this.mImageViewPicture.setImageBitmap(BitmapStorage.loadImage(mItemView.getContext(), user.getUrlPicture()));
        } else {
            this.mImageViewPicture.setImageResource(R.drawable.bk_photo);
        }

        this.mBackground.setSelected(selectedUser);
        if (activeUser == position) {
            this.mImageViewActiveIndicator.setVisibility(View.VISIBLE);
        } else {
            this.mImageViewActiveIndicator.setVisibility(View.INVISIBLE);
        }

    }


}
