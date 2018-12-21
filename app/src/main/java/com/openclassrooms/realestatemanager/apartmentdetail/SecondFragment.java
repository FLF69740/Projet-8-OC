package com.openclassrooms.realestatemanager.apartmentdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Apartment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondFragment extends Fragment {

    @BindView(R.id.photo_presentation)ImageView mPhotoPresentation;
    @BindView(R.id.description_body)TextView mDescriptionBody;
    @BindView(R.id.surface_information)TextView mSurfaceInformation;
    @BindView(R.id.number_of_rooms_information)TextView mNumberOfRoomsInformation;
    @BindView(R.id.contact_information)TextView mContactInformation;
    @BindView(R.id.point_interest_information)TextView mPointOfInterestInformation;
    @BindView(R.id.localisation_information)TextView mLocalisationInformation;
    @BindView(R.id.type_information)TextView mTypeInformation;
    @BindView(R.id.sold_information)TextView mSoldInformation;
    @BindView(R.id.date_inscription)TextView mTextViewDateInscription;

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    private Apartment mApartment;
    private View mView;

    public SecondFragment() {}

    public static SecondFragment newInstance(Apartment apartment){
        SecondFragment secondFragment = new SecondFragment();
        Bundle args = new Bundle(1);
        args.putParcelable(BUNDLE_KEY_APARTMENT,apartment);
        secondFragment.setArguments(args);

        return secondFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.bind(this, mView);

        if (getArguments() != null) {
            mApartment = getArguments().getParcelable(BUNDLE_KEY_APARTMENT);
            if (mApartment != null) {
                configureScreen();
            }
        }

        return mView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_KEY_APARTMENT, mApartment);
    }

    public void updateDoubleScreen(Apartment apartment){
        mApartment = apartment;
        configureScreen();
    }

    public void configureScreen(){
        if (mApartment.getUrlPicture().equals(Apartment.EMPTY_CASE)){
            mPhotoPresentation.setImageResource(R.drawable.image_realestate);
        }
        mTextViewDateInscription.setText(mApartment.getDateInscription());
        mDescriptionBody.setText(mApartment.getDescription());
        mSurfaceInformation.setText(Utils.getDimension(mApartment.getDimension(), this.mView.getContext().getString(R.string.METER), this.mView));
        mNumberOfRoomsInformation.setText(Utils.getRooms(mApartment.getRoomNumber(), this.mView));
        mPointOfInterestInformation.setText(mApartment.getPoInterest());
        mLocalisationInformation.setText(Utils.getFullAdress(mApartment.getAdress(), String.valueOf(mApartment.getPostalCode()), mApartment.getTown()));
        mContactInformation.setText(String.valueOf(mApartment.getUserId()));
        mTypeInformation.setText(mApartment.getType());
        mSoldInformation.setText(Utils.getStringSold(mApartment.getSold(), this.mView));
        mSoldInformation.setTextColor(Utils.getColorSold(mApartment.getSold(), this.mView));
    }

}
