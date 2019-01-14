package com.openclassrooms.realestatemanager.profilemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.User;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListViewHolder> {

    private List<User> mUserList;
    private int mSelectedUser;
    private int mActiveUser;

    public UsersListAdapter(List<User> userList, int selectedUser, int activeUser){
        this.mUserList = userList;
        this.mSelectedUser = selectedUser;
        this.mActiveUser = activeUser;
    }

    @NonNull
    @Override
    public UsersListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_profile_recyclerview_item, viewGroup, false);
        return new UsersListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersListViewHolder usersListViewHolder, int i) {
        Boolean selected = mSelectedUser == i;
        usersListViewHolder.updateWithUserInformations(this.mUserList.get(i), selected, mActiveUser, i);
    }

    @Override
    public int getItemCount() {
        return this.mUserList.size();
    }

    public User getUser(int position){
        return mUserList.get(position);
    }

    public void setSelectedUser(int position){
        mSelectedUser = position;
    }

}
