package com.openclassrooms.realestatemanager.profilemanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.appartmentlist.ApartmentListAdapter;
import com.openclassrooms.realestatemanager.appartmentlist.MainFragment;
import com.openclassrooms.realestatemanager.appartmentlist.RecyclerViewClickSupport;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.photomanager.PhotoModifierActivity;

import java.io.Serializable;
import java.util.List;
import java.util.PrimitiveIterator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileManagerFragment extends Fragment {

    private View mView;
    private List<User> mUserList;
    private UsersListAdapter mAdapter;
    private int mSelectedUser;
    private long mActiveUser;

    @BindView(R.id.recycler_view_users)RecyclerView mRecyclerView;

    public ProfileManagerFragment() {
        // Required empty public constructor
    }

    public static ProfileManagerFragment newInstance() {
        return new ProfileManagerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile_manager, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    /**
     *  RECYCLERVIEW
     */

    private void configureRecyclerView(){
        this.mAdapter = new UsersListAdapter(mUserList, mSelectedUser, (int) mActiveUser);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.fragment_profile_recyclerview_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    User user = mAdapter.getUser(position);
                    mCallback.itemUserClicked(mView, user, String.valueOf(position));
                    setAdapterLocation(position);
                });
    }

    private void setAdapterLocation(int position){
        mSelectedUser = position;
        mAdapter.setSelectedUser(mSelectedUser);
        mAdapter.notifyDataSetChanged();
    }

    public void refresh(User user, List<User> userList) {
        mUserList = userList;
        int position = (int) (user.getId() -1);
        if (mUserList != null) {
            configureRecyclerView();
            configureOnClickRecyclerView();
            setAdapterLocation(position);
        }
    }

    /**
     *  CALLBACK
     */

    // interface for button clicked
    public interface ItemUserClickedListener{
        void itemUserClicked(View view, User user, String adapterPosition);
    }

    //callback for button clicked
    private ItemUserClickedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ItemUserClickedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemUserClickedListener");
        }
    }



}
