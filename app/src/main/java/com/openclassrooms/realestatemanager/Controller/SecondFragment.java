package com.openclassrooms.realestatemanager.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondFragment extends Fragment {

    @BindView(R.id.temp_textView)TextView mTextView;

    private Apartment mApartment;
    private View mView;

    public SecondFragment() {}

    public static SecondFragment newInstance(Apartment apartment){
        SecondFragment secondFragment = new SecondFragment();
        Bundle args = new Bundle(1);
        args.putParcelable("apartment",apartment);
        secondFragment.setArguments(args);

        return secondFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.bind(this, mView);

        if (getArguments() != null) {
            mApartment = getArguments().getParcelable("apartment");
            if (mApartment != null) {
                updateSimpleScreen();
            }
        }

        return mView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("apartment", mApartment);
    }

    public void updateSimpleScreen(){
        mTextView.setText(mApartment.toString());
    }

    public void updateDoubleScreen(Apartment apartment){
        mApartment = apartment;
        mTextView.setText(mApartment.toString());
    }

}
