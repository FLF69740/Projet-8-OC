package com.openclassrooms.realestatemanager.Controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondFragment extends Fragment {

    @BindView(R.id.temp_textView)TextView mTextView;

    View mView;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    public void changeText(){
        String text = "Button clicked";
        mTextView.setText(text);
    }

}
