package com.openclassrooms.realestatemanager.models;

import android.content.Context;

import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.List;

public class TransformerApartmentItems {

    private List<Item> mItemList;

    private static final String NO_PICTURE = "NO_PICTURE";
    private static final String ENTITY_SEPARATOR = "/+/";
    private static final String PICTURE_SEP_TI_URL = "/-/";

    public TransformerApartmentItems(Apartment apartment, Context context) {
        mItemList = new ArrayList<>();
        createItemList(apartment, context);
    }

    private void createItemList(Apartment apartment, Context context){
        mItemList.add(new Item(apartment.getType(), context.getString(R.string.apartment_title_type), NO_PICTURE, false, false));
        mItemList.add(new Item(String.valueOf(apartment.getPrice()), context.getString(R.string.apartment_title_price), NO_PICTURE, false, false));
        mItemList.add(new Item(apartment.getDescription(), context.getString(R.string.apartment_description), NO_PICTURE, false, false));
        mItemList.add(new Item(String.valueOf(apartment.getDimension()), context.getString(R.string.apartment_title_square), NO_PICTURE, false, false));
        mItemList.add(new Item(String.valueOf(apartment.getRoomNumber()), context.getString(R.string.apartment_title_room), NO_PICTURE, false, false));
        mItemList.add(new Item("", context.getString(R.string.apartment_title_adress), NO_PICTURE, true, false));
        mItemList.add(new Item(apartment.getAdress(), context.getString(R.string.apartment_title_street), NO_PICTURE, false, false));
        mItemList.add(new Item(String.valueOf(apartment.getPostalCode()), context.getString(R.string.apartment_title_postal_code), NO_PICTURE, false, false));
        mItemList.add(new Item(apartment.getTown(), context.getString(R.string.apartment_title_town), NO_PICTURE, false, false));
        mItemList.add(new Item("", context.getString(R.string.apartment_title_po), NO_PICTURE, true, false));

        if (!apartment.getPoInterest().equals(Apartment.EMPTY_CASE)){
            String[] parts = apartment.getPoInterest().split(ENTITY_SEPARATOR);
            for (String part : parts) {
                mItemList.add(new Item(part, context.getString(R.string.apartment_title_po_single), NO_PICTURE, false, false));
            }
        }
        mItemList.add(new Item(context.getString(R.string.fragment_modification_recycler_no_po), context.getString(R.string.apartment_title_po_single), NO_PICTURE, false, false));

        mItemList.add(new Item("", context.getString(R.string.apartment_title_picture), NO_PICTURE, true, false));
        if (!apartment.getUrlPicture().equals(Apartment.EMPTY_CASE)){
            String[] parts = apartment.getUrlPicture().split(ENTITY_SEPARATOR);
            for (String part : parts){
                String[] subParts = part.split(PICTURE_SEP_TI_URL);
                mItemList.add(new Item(subParts[0], "", subParts[1], false, true));
            }
        }
        mItemList.add(new Item(context.getString(R.string.fragment_modification_recycler_no_picture), "", NO_PICTURE, false, true));



    }



    public List<Item> getListItems(){
        return mItemList;
    }
}
