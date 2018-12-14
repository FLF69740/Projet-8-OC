package com.openclassrooms.realestatemanager.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Apartment;
import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

public class SecondFragment extends Fragment {

    @BindView(R.id.temp_textView)TextView mTextView;

    private Apartment mApartment;
    private View mView;

    public SecondFragment() {}

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

    public void updateSimpleScreen(){
        mTextView.setText(mApartment.toString());
    }

    public void updateDoubleScreen(Apartment apartment){
        mApartment = apartment;
        mTextView.setText(mApartment.toString());
    }

}
