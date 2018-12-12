package com.openclassrooms.realestatemanager.appartmentlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment {

    private View mView;
    private List<Apartment> mApartmentList;

    @BindView(R.id.tempEdit)EditText mEditText;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, mView);

        mApartmentList = (List<Apartment>) getArguments().getSerializable("list");
        if (mApartmentList != null && !mApartmentList.isEmpty()){
            mEditText.setText(mApartmentList.get(mApartmentList.size()-1).toString());
        }

        return mView;
    }


}
