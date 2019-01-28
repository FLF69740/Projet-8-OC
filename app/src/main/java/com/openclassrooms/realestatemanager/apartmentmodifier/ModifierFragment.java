package com.openclassrooms.realestatemanager.apartmentmodifier;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.photomanager.PhotoModifierActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.appartmentlist.RecyclerViewClickSupport;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.Item;
import com.openclassrooms.realestatemanager.models.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ModifierFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";
    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    public static final String BUNDLE_ITEM_LIST = "BUNDLE_ITEM_LIST";
    public static final String BUNDLE_APARTMENT_ID = "BUNDLE_APARTMENT_ID";
    private static final int RC_PHOTO_UPLOAD = 10;
    private static final int RC_MANAGER_UPLOAD = 20;

    private View mView;
    private List<Item> mItemList;
    private ItemsAdapter mAdapter;
    private Apartment mApartment;
    private User mUser;
    private Uri mUriPicture;
    private String mDateInscription;
    private boolean mIsSold;
    private Calendar mCalendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private boolean mLaunchCalendarAutoriation;

    @BindView(R.id.recycler_view_modifier)RecyclerView mRecyclerView;
    @BindView(R.id.radioGroup)RadioGroup mRadioGroupButton;
    @BindView(R.id.bottom_background_change)ImageView mBottomBackgroundChange;
    @BindView(R.id.change_button)Button mButtonChange;
    @BindView(R.id.bottom_background_modify)ImageView mBottomBackgroundModify;
    @BindView(R.id.photo_button_modify)ImageView mPhotoButtonModify;
    @BindView(R.id.textView_title_modify)TextView mTextViewModify;
    @BindView(R.id.editText_information_modify)EditText mEditTextModify;
    @BindView(R.id.button_validation_modify)ImageView mValidationButtonModify;
    @BindView(R.id.clear_button_modify)ImageView mClearButtonModify;
    @BindView(R.id.radio_button_sold)RadioButton mRadioButtonSold;
    @BindView(R.id.fragment_modifier_dateSold)TextView mDateSold;
    @BindView(R.id.fragment_modifier_title_dateSold)TextView mTitleDateSold;
    @BindView(R.id.manager_id)TextView mTextViewNameManager;

    public static ModifierFragment newInstance(Apartment apartment, User user){
        ModifierFragment modifierFragment = new ModifierFragment();
        Bundle args = new Bundle(1);
        args.putParcelable(BUNDLE_KEY_APARTMENT,apartment);
        args.putParcelable(BUNDLE_KEY_USER, user);
        modifierFragment.setArguments(args);
        return modifierFragment;
    }

    public ModifierFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_modifier, container, false);
        ButterKnife.bind(this, mView);

        mLaunchCalendarAutoriation = false;
        mApartment = getArguments().getParcelable(BUNDLE_KEY_APARTMENT);
        mUser = getArguments().getParcelable(BUNDLE_KEY_USER);
        mTextViewNameManager.setText(mUser.getUsername());
        mDateInscription = mApartment.getDateInscription();
        mIsSold = mApartment.getSold();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.editCalendar();
        mRadioGroupButton.setOnCheckedChangeListener(this);
        showDateSoldTextView(false);
        if (mIsSold) {
            mRadioButtonSold.setChecked(true);
            showDateSoldTextView(true);
            mCalendar.set(Calendar.DAY_OF_MONTH, Utils.getDayOfMonth(mApartment.getDateSold()));
            mCalendar.set(Calendar.MONTH, Utils.getMonth(mApartment.getDateSold())-1);
            mCalendar.set(Calendar.YEAR, Utils.getYear(mApartment.getDateSold()));
        } else {
            mCalendar = Calendar.getInstance();
        }
        mDateSold.setText(sdf.format(mCalendar.getTime()));
        this.panelChoiceVisibility(true);
        mLaunchCalendarAutoriation = true;

        return mView;
    }

    /**
     *  RECYCLERVIEW
     */

    private void configureRecyclerView(){
        TransformerApartmentItems transformerApartmentItems = new TransformerApartmentItems();
        transformerApartmentItems.createItemList(mApartment, mView.getContext());
        mItemList = new ArrayList<>(transformerApartmentItems.getListItems());
        this.mAdapter = new ItemsAdapter(transformerApartmentItems.getListItems());
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.fragment_modifier_recyclerciew_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    List<Item> itemList = mAdapter.getItemList();
                    loadModifierBarManager(itemList, position);
                });
    }

    // Actualisation of list items
    private void createOrChangeItem(List<Item> itemList, Boolean isNewItem, Boolean isNewPicture, Boolean isRemove, int position){
        if (isRemove){
            BitmapStorage.deleteImage(getContext(), mApartment.getId() + TransformerApartmentItems.PICTURE_TITLE_CHARACTERE + itemList.get(position).getInformation());
            itemList.remove(position);
        } else if (isNewItem){
            itemList.add(position+1, new Item(mView.getContext().getString(R.string.fragment_modification_recycler_no_po), mView.getContext().getString(R.string.apartment_title_po_single),
                    TransformerApartmentItems.NO_PICTURE, false, false));
        } else if (isNewPicture){
            itemList.add(position+1, new Item(mView.getContext().getString(R.string.fragment_modification_recycler_no_picture), mView.getContext().getString(R.string.apartment_title_picture_single),
                    TransformerApartmentItems.NO_PICTURE, false, true));
            mUriPicture = Uri.parse("");
        }
        mItemList = itemList;
        mAdapter.setItemList(mItemList);
        mAdapter.notifyDataSetChanged();
    }

    /**
     *  DATE SOLD DEFINITION
     */

    // RadioGroupButton listener
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = mView.findViewById(checkedId);
        switch (radioButton.getId()){
            case R.id.radio_button_for_sale: mIsSold = false; showDateSoldTextView(false); break;
            case R.id.radio_button_sold: mIsSold = true; showDateSoldTextView(true);
                if (mLaunchCalendarAutoriation) {
                    new DatePickerDialog(getContext(), dateSold, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                break;
        }
    }

    // Show and Gone date sold panel
    private void showDateSoldTextView(Boolean show){
        int bool = 8;
        if (show) bool = 0;
        mDateSold.setVisibility(bool);
        mTitleDateSold.setVisibility(bool);
    }

    DatePickerDialog.OnDateSetListener dateSold = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mCalendar = calendar;
            mDateSold.setText(sdf.format(calendar.getTime()));
        }
    };

    //Define Listener from calendar
    private void editCalendar(){
        mDateSold.setOnClickListener(v -> new DatePickerDialog(getContext(), dateSold, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    /**
     *  UI CHANGE
     */

    public static final String MANAGER_NAME = "MANAGER_NAME";

    @OnClick(R.id.manager_id)
    public void chooseManager(){
        Intent intent = new Intent(getActivity(), ModifierUserActivity.class);
        intent.putExtra(MANAGER_NAME, mUser.getUsername());
        startActivityForResult(intent, RC_MANAGER_UPLOAD);
    }

    private void loadModifierBarManager(List<Item> listItem, int position){
        if (!listItem.get(position).getATitle()) {
            panelChoiceVisibility(false);

            // EMPTY CASE PROCESS
            if (listItem.get(position).getInformation().equals(mView.getContext().getString(R.string.fragment_modification_recycler_no_po)) || listItem.get(position).getInformation().equals(Apartment.EMPTY_CASE)) {
                this.modifierBarManagerParameters(View.GONE, true, "", View.INVISIBLE);
            }
            // NEW PICTURE PROCESS
            else if (listItem.get(position).getInformation().equals(mView.getContext().getString(R.string.fragment_modification_recycler_no_picture))) {
                this.modifierBarManagerParameters(View.INVISIBLE, false, "", View.INVISIBLE);
                panelChoiceVisibility(true);
                Intent intent = new Intent(getActivity(), PhotoModifierActivity.class);
                ArrayList<String> stringList = new ArrayList<>();
                for (int i = 0; i < listItem.size(); i++) {
                    stringList.add(i, listItem.get(i).getInformation());
                }
                intent.putExtra(BUNDLE_APARTMENT_ID, mApartment.getId());
                intent.putStringArrayListExtra(BUNDLE_ITEM_LIST, stringList);
                startActivityForResult(intent, RC_PHOTO_UPLOAD);
            }
            // EXISTENT PICTURE PROCESS
            else if (listItem.get(position).getTitle().equals(mView.getContext().getString(R.string.apartment_title_picture_single)) &&
                    !listItem.get(position).getInformation().equals(mView.getContext().getString(R.string.fragment_modification_recycler_no_picture))) {
                this.modifierBarManagerParameters(View.VISIBLE, false, listItem.get(position).getInformation(), View.VISIBLE);
                if (listItem.get(position).getUrlPicture().equals(TransformerApartmentItems.NO_PICTURE)) {
                    mPhotoButtonModify.setImageResource(R.mipmap.baseline_insert_photo_black_24);
                } else {
                    if (BitmapStorage.isFileExist(Objects.requireNonNull(getContext()), listItem.get(position).getUrlPicture())) {
                        mPhotoButtonModify.setImageBitmap(BitmapStorage.loadImage(getContext(), listItem.get(position).getUrlPicture()));
                    }
                }
            }
            // OTHERS ITEMS PROCESS
            else {
                this.modifierBarManagerParameters(View.GONE, true, listItem.get(position).getInformation(), View.INVISIBLE);
            }
            mTextViewModify.setText(listItem.get(position).getTitle());
            mEditTextModify.setInputType(Utils.getInputType(getContext(), listItem.get(position).getTitle()));
        }
        this.validationClick(listItem, position);
        this.clearClick(listItem, position);
    }

    // CHANGE BUTTON CLICK LISTENER
    private void validationClick(List<Item> listItem, int position){
        mValidationButtonModify.setOnClickListener(v -> {
            if (!mEditTextModify.getText().toString().equals("")) {
                boolean isNewPO = false;
                if (listItem.get(position).getTitle().equals(mView.getContext().getString(R.string.apartment_title_po_single)) &&
                        listItem.get(position).getInformation().equals(mView.getContext().getString(R.string.fragment_modification_recycler_no_po))){
                    isNewPO = true;
                }
                listItem.get(position).setInformation(mEditTextModify.getText().toString());
                this.createOrChangeItem(listItem, isNewPO, false, false, position);
            }
            panelChoiceVisibility(true);
        });
    }

    // CLEAR BUTTON CLICK LISTENER
    private void clearClick(List<Item> listItem, int position){
        mClearButtonModify.setOnClickListener(v -> {
            this.createOrChangeItem(listItem, false, false, true, position);
            panelChoiceVisibility(true);
        });
    }

    // define to show or hide elements of ModifierBarManager
    private void modifierBarManagerParameters(int statePhotoButton, boolean isEditTextEnabled, String editTextString, int stateClearButton){
        mPhotoButtonModify.setVisibility(statePhotoButton);
        mEditTextModify.setEnabled(isEditTextEnabled);
        mEditTextModify.setText(editTextString);
        mClearButtonModify.setVisibility(stateClearButton);
    }

    private void panelChoiceVisibility(boolean change){
        int a = 4, b = 0;
        if (change){
            a = 0;
            b = 4;
        }
        mBottomBackgroundChange.setVisibility(a);
        mButtonChange.setVisibility(a);
        mBottomBackgroundModify.setVisibility(b);
        mPhotoButtonModify.setVisibility(b);
        mTextViewModify.setVisibility(b);
        mEditTextModify.setVisibility(b);
        mValidationButtonModify.setVisibility(b);
        mClearButtonModify.setVisibility(b);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_UPLOAD){
            if (resultCode == RESULT_OK){
                this.mUriPicture = data.getParcelableExtra(PhotoModifierActivity.BUNDLE_PHOTO_UPDATE);
                String photoName = data.getStringExtra(PhotoModifierActivity.BUNDLE_NAME_UPDATE);
                Log.i("TAG", "Code uri : " + mUriPicture.toString());
                mItemList.get(mItemList.size()-1).setInformation(photoName);
                mItemList.get(mItemList.size()-1).setUrlPicture(mUriPicture.toString());
                this.createOrChangeItem(mItemList,false, true, false, mItemList.size()-1);
            } else if (resultCode == RESULT_CANCELED){
                Log.i("TAG", "Code uri : canceled");
            }
        }
        if (requestCode == RC_MANAGER_UPLOAD){
            if (resultCode == RESULT_OK){
                mApartment.setUserId(data.getIntExtra(ModifierUserActivity.BUNDLE_CHANGE_USER_TAG, (int) mApartment.getUserId()));
                mTextViewNameManager.setText(data.getStringExtra(ModifierUserActivity.BUNDLE_CHANGE_USER_NAME));
            }
        }
    }

    /**
     *  CALLBACK
     */

    // interface for button clicked
    public interface ItemModifierClickedListener{
        void itemClicked(View view, List<Item> itemList, String dateInscription, long id, long userId, Boolean sold, String dateSold);
    }

    //callback for button clicked
    private ItemModifierClickedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ItemModifierClickedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemClickedListener");
        }
    }

    @OnClick(R.id.change_button)
    public void changeButtonClick(){
        mCallback.itemClicked(mView, mItemList, mDateInscription, mApartment.getId(), mApartment.getUserId(), mIsSold, mDateSold.getText().toString());
    }
}
