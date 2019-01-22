package com.openclassrooms.realestatemanager.apartmentdetail;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.TransformerApartmentItems;
import com.openclassrooms.realestatemanager.models.User;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.Controller.BaseActivity.BUNDLE_KEY_ACTIVE_DIMENSION;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.BUNDLE_KEY_ACTIVE_MONEY;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.SHARED_DIMENSION;
import static com.openclassrooms.realestatemanager.Controller.BaseActivity.SHARED_MONEY;


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
    @BindView(R.id.dateSold)TextView mTextViewDateSold;
    @BindView(R.id.photo_number_indicator)TextView mPhotoNumberIndicator;
    @BindView(R.id.layout_second_price)TextView mTextViewSecondLayoutPrice;

    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";
    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    public static final String BUNDLE_KEY_LIST_PHOTO = "BUNDLE_KEY_LIST_PHOTO";

    private Apartment mApartment;
    private User mUser;
    private View mView;
    private boolean mIsPhotoExist;

    public SecondFragment() {}

    public static SecondFragment newInstance(Apartment apartment, User user){
        SecondFragment secondFragment = new SecondFragment();
        Bundle args = new Bundle(2);
        args.putParcelable(BUNDLE_KEY_APARTMENT,apartment);
        args.putParcelable(BUNDLE_KEY_USER, user);
        secondFragment.setArguments(args);

        return secondFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.bind(this, mView);
        if (getArguments() != null) {
            if (getArguments().getParcelable(BUNDLE_KEY_APARTMENT) != null && getArguments().getParcelable(BUNDLE_KEY_USER) != null){
                updateFragmentScreen(getArguments().getParcelable(BUNDLE_KEY_APARTMENT), getArguments().getParcelable(BUNDLE_KEY_USER));
            }
        }
        return mView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_KEY_APARTMENT, mApartment);
        outState.putParcelable(BUNDLE_KEY_USER, mUser);
    }

    public void updateFragmentScreen(Apartment apartment, User user){
        mApartment = apartment;
        mUser = user;
        if (BitmapStorage.isFileExist(Objects.requireNonNull(getContext()), BitmapStorage.getFirstPhotoName(mApartment))) {
            this.mIsPhotoExist = true;
            this.mPhotoPresentation.setImageBitmap(BitmapStorage.loadImage(getContext(), BitmapStorage.getFirstPhotoName(mApartment)));
            this.mPhotoNumberIndicator.setText(String.valueOf(BitmapStorage.getPhotoNumber(mApartment)));
        } else {
            this.mIsPhotoExist = false;
            this.mPhotoPresentation.setImageResource(R.drawable.image_realestate);
            this.mPhotoNumberIndicator.setVisibility(View.INVISIBLE);
        }

        mTextViewSecondLayoutPrice.setText(getFinalPrice(mApartment.getPrice()));
        mTextViewDateInscription.setText(mApartment.getDateInscription());
        mDescriptionBody.setText(mApartment.getDescription());
        mSurfaceInformation.setText(getFinalDimension(mApartment.getDimension()));
        mNumberOfRoomsInformation.setText(Utils.getRooms(mApartment.getRoomNumber(), this.mView));
        mPointOfInterestInformation.setText(getPOString(mApartment.getPoInterest()));
        mLocalisationInformation.setText(Utils.getFullAdress(mApartment.getAdress(), String.valueOf(mApartment.getPostalCode()), mApartment.getTown()));
        mContactInformation.setText(mUser.getUsername());
        mTypeInformation.setText(mApartment.getType());
        mSoldInformation.setText(Utils.getStringSold(mApartment.getSold(), this.mView));
        mSoldInformation.setTextColor(Utils.getColorSold(mApartment.getSold(), this.mView));
        if (!mApartment.getDateSold().equals(Apartment.EMPTY_CASE)) {
            mTextViewDateSold.setText(mApartment.getDateSold());
        } else {
            mTextViewDateSold.setText("-");
        }
    }

    // points of interest string construction
    private String getPOString(String string){
        string = "- " + string;
        if (string.contains(TransformerApartmentItems.ENTITY_SEPARATOR)){
            string = string.replace(TransformerApartmentItems.ENTITY_SEPARATOR, "\n- ");
        }
        return string;
    }

    // price transition
    private String getFinalPrice(int price){
        String moneyUnit = getContext().getSharedPreferences(SHARED_MONEY, Context.MODE_PRIVATE).getString(BUNDLE_KEY_ACTIVE_MONEY, getContext().getString(R.string.loan_simulation_dollar));
        if (moneyUnit.equals(getContext().getString(R.string.loan_simulation_euro))){
            price = Utils.convertDollarToEuro(price);
        }
        return moneyUnit + " " + Utils.getPriceFormat(price);
    }

    // dimension transition
    private String getFinalDimension(int dimension){
        String dimensionUnit = getContext().getSharedPreferences(SHARED_DIMENSION, Context.MODE_PRIVATE).getString(BUNDLE_KEY_ACTIVE_DIMENSION, getContext().getString(R.string.units_square));
        String unity = getContext().getString(R.string.FEET);
        if (dimensionUnit.equals(getContext().getString(R.string.units_meters))){
            dimension = Utils.getSquareMeter(dimension);
            unity = getContext().getString(R.string.METER);
        }
        return Utils.getDimension(dimension, unity, this.mView);
    }

    @OnClick(R.id.photo_presentation)
    public void ShowViewPagerPhoto(){
        if (mIsPhotoExist) {
            Intent intent = new Intent(getActivity(), ViewPagerPhotoActivity.class);
            intent.putExtra(BUNDLE_KEY_LIST_PHOTO, mApartment.getUrlPicture());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), mPhotoPresentation, getString(R.string.animation_second_fragment_to_viewpager));
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }
}
