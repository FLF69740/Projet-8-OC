package com.openclassrooms.realestatemanager.apartmentmodifier;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.appartmentlist.MainFragment;
import com.openclassrooms.realestatemanager.appartmentlist.RecyclerViewClickSupport;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.Item;
import com.openclassrooms.realestatemanager.models.TransformerApartmentItems;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import static android.app.Activity.RESULT_OK;

public class ModifierFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";
    private static final String IMAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 101;
    private static final int RC_CHOOSE_PHOTO = 102;

    private View mView;
    private List<Item> mItemList;
    private ItemsAdapter mAdapter;
    private Apartment mApartment;
    private Uri mUriPicture;

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

    public static ModifierFragment newInstance(Apartment apartment){
        ModifierFragment modifierFragment = new ModifierFragment();
        Bundle args = new Bundle(1);
        args.putParcelable(BUNDLE_KEY_APARTMENT,apartment);
        modifierFragment.setArguments(args);

        return modifierFragment;
    }

    public ModifierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_modifier, container, false);
        ButterKnife.bind(this, mView);

        mApartment = getArguments().getParcelable(BUNDLE_KEY_APARTMENT);
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        mRadioGroupButton.setOnCheckedChangeListener(this);
        this.panelChoiceVisibility(true);

        return mView;
    }

    //PERMISSION


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     *  RECYCLERVIEW
     */

    private void configureRecyclerView(){
        TransformerApartmentItems transformerApartmentItems = new TransformerApartmentItems(mApartment, mView.getContext());
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

    private void updateAdapter(List<Item> itemList, Boolean isNewItem, Boolean isNewPicture, Boolean isRemove, String stringNewPicture, int position){
        if (isRemove){
            itemList.remove(position);
        } else if (isNewItem){
            itemList.add(position+1, new Item(mView.getContext().getString(R.string.fragment_modification_recycler_no_po), mView.getContext().getString(R.string.apartment_title_po_single),
                    TransformerApartmentItems.NO_PICTURE, false, false));
        } else if (isNewPicture){
            itemList.add(position+1, new Item(mView.getContext().getString(R.string.fragment_modification_recycler_no_picture), "",
                    TransformerApartmentItems.NO_PICTURE, false, true));
        }
        mItemList = itemList;
        mAdapter.setItemList(mItemList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = mView.findViewById(checkedId);
        switch (radioButton.getId()){
            case R.id.radio_button_for_sale: Toast.makeText(mView.getContext(), "A", Toast.LENGTH_LONG).show(); break;
            case R.id.radio_button_sold: Toast.makeText(mView.getContext(), "B", Toast.LENGTH_LONG).show(); break;
        }
    }

    /**
     *  UI CHANGE
     */

    private void loadModifierBarManager(List<Item> listItem, int position){
        if (!listItem.get(position).getATitle()) {
            panelChoiceVisibility(false);
            // For EMPTY CASE
            if (listItem.get(position).getInformation().equals(mView.getContext().getString(R.string.fragment_modification_recycler_no_po)) ||
                    listItem.get(position).getInformation().equals(mView.getContext().getString(R.string.fragment_modification_recycler_no_picture)) ||
                    listItem.get(position).getInformation().equals(Apartment.EMPTY_CASE)){
                mEditTextModify.setText("");
                mClearButtonModify.setVisibility(View.INVISIBLE);
            } else {
                mEditTextModify.setText(listItem.get(position).getInformation());
            }
            mTextViewModify.setText(listItem.get(position).getTitle());
            // For Number EditText or Text EditText
            if (listItem.get(position).getTitle().equals(mView.getContext().getString(R.string.apartment_title_price)) ||
                    listItem.get(position).getTitle().equals(mView.getContext().getString(R.string.apartment_title_square)) ||
                    listItem.get(position).getTitle().equals(mView.getContext().getString(R.string.apartment_title_room)) ||
                    listItem.get(position).getTitle().equals(mView.getContext().getString(R.string.apartment_title_postal_code))){
                mEditTextModify.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
                mEditTextModify.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            // For NO PICTURE CASE
            if (!listItem.get(position).getAPicture()){
                mPhotoButtonModify.setVisibility(View.INVISIBLE);
                // For hidden clear button (when it's not point of interest or picture
                if (!listItem.get(position).getTitle().equals(mView.getContext().getString(R.string.apartment_title_po_single))){
                    mClearButtonModify.setVisibility(View.INVISIBLE);
                }
            } else {
                if (listItem.get(position).getUrlPicture().equals(TransformerApartmentItems.NO_PICTURE)) {
                    mPhotoButtonModify.setImageResource(R.mipmap.baseline_insert_photo_black_24);
                } else {
                    mPhotoButtonModify.setImageURI(Uri.parse(listItem.get(position).getUrlPicture()));
                }
            }

            // DONE CLICK
            mValidationButtonModify.setOnClickListener(v -> {
                Boolean isNewPO = false, isNewPicture = false;
                String stringNewPicture = TransformerApartmentItems.NO_PICTURE;
                // for new Point of Interest
                if (listItem.get(position).getInformation().equals(mView.getContext().getString(R.string.fragment_modification_recycler_no_po))){
                    isNewPO = true;
                }
                if (listItem.get(position).getInformation().equals(mView.getContext().getString(R.string.fragment_modification_recycler_no_picture))){
                    isNewPicture = true;
                    stringNewPicture = mUriPicture.toString();
                }

                if (!mEditTextModify.getText().toString().equals("")) {
                    listItem.get(position).setInformation(mEditTextModify.getText().toString());
                    if (isNewPicture){
                        listItem.get(position).setUrlPicture(stringNewPicture);
                    }
                    this.updateAdapter(listItem, isNewPO, isNewPicture, false, stringNewPicture, position);
                }
                panelChoiceVisibility(true);
            });

            // CLEAR CLICK
            mClearButtonModify.setOnClickListener(v -> {
                this.updateAdapter(listItem, false, false, true, null, position);
                panelChoiceVisibility(true);
            });



        }
    }

    private void panelChoiceVisibility(Boolean change){
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

    /**
     *  CHOOSE IMAGE MEDIASTORE
     */

    @OnClick(R.id.photo_button_modify)
    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void onClickAddPicture(){
        if (!EasyPermissions.hasPermissions(mView.getContext(), IMAGE_PERMISSION)){
            EasyPermissions.requestPermissions(this, "OK", RC_IMAGE_PERMS, IMAGE_PERMISSION);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RC_CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CHOOSE_PHOTO){
            if (resultCode == RESULT_OK){
                this.mUriPicture = data.getData();
                Glide.with(this).load(this.mUriPicture).apply(RequestOptions.centerCropTransform()).into(mPhotoButtonModify);
            }
        }
    }

    /**
     *  CALLBACK
     */

    // interface for button clicked
    public interface ItemModifierClickedListener{
        void itemClicked(View view, List<Item> itemList);
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
        mCallback.itemClicked(mView, mItemList);
    }
}
