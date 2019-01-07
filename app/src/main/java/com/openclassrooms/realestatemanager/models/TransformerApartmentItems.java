package com.openclassrooms.realestatemanager.models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.List;

public class TransformerApartmentItems {

    private List<Item> mItemList;
    private Apartment mApartment;

    public static final String NO_PICTURE = "NO_PICTURE";
    public static final String ENTITY_SEPARATOR = "/!!/";
    public static final String PICTURE_SEP_TI_URL = "/-/";
    public static final String PICTURE_TITLE_CHARACTERE = "__";

    public TransformerApartmentItems() {
        mItemList = new ArrayList<>();
        mApartment = new Apartment(Apartment.EMPTY_CASE, 0, Apartment.EMPTY_CASE, 0, Apartment.EMPTY_CASE, Apartment.EMPTY_CASE, 0);
    }

    /**
     *  APARTMENT TO LIST ITEMS
     */

    public void createItemList(Apartment apartment, Context context){
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
                mItemList.add(new Item(subParts[0], context.getString(R.string.apartment_title_picture_single), subParts[1], false, true));
            }
        }
        mItemList.add(new Item(context.getString(R.string.fragment_modification_recycler_no_picture), context.getString(R.string.apartment_title_picture_single), NO_PICTURE, false, true));
    }

    public List<Item> getListItems(){
        return mItemList;
    }

    /**
     *  LIST ITEMS TO APARTMENT
     */

    public void createApartment(List<Item> itemList, Context context, long id, long userId){
        mApartment.setUserId(userId);
        mApartment.setId(id);
        for (int i = 0 ; i < itemList.size() ; i++){
            if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_title_type))){
                mApartment.setType(itemList.get(i).getInformation());
            } else if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_title_price))) {
                mApartment.setPrice(Integer.valueOf(itemList.get(i).getInformation()));
            } else if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_description))) {
                mApartment.setDescription(itemList.get(i).getInformation());
            } else if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_title_square))) {
                mApartment.setDimension(Integer.valueOf(itemList.get(i).getInformation()));
            } else if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_title_room))) {
                mApartment.setRoomNumber(Integer.valueOf(itemList.get(i).getInformation()));
            } else if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_title_street))) {
                mApartment.setAdress(itemList.get(i).getInformation());
            } else if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_title_postal_code))) {
                mApartment.setPostalCode(Integer.valueOf(itemList.get(i).getInformation()));
            } else if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_title_town))) {
                mApartment.setTown(itemList.get(i).getInformation());
            } else if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_title_po_single)) &&
                    !itemList.get(i).getInformation().equals(context.getString(R.string.fragment_modification_recycler_no_po))) {

                String iterationPO = mApartment.getPoInterest();
                if (iterationPO.equals(Apartment.EMPTY_CASE)){
                    iterationPO = itemList.get(i).getInformation();
                } else {
                    iterationPO += ENTITY_SEPARATOR + itemList.get(i).getInformation();
                }

                mApartment.setPoInterest(iterationPO);

            }  else if (itemList.get(i).getTitle().equals(context.getString(R.string.apartment_title_picture_single)) &&
                    !itemList.get(i).getInformation().equals(context.getString(R.string.fragment_modification_recycler_no_picture))) {

                String fileName = String.valueOf(mApartment.getId()) + PICTURE_TITLE_CHARACTERE + itemList.get(i).getInformation();
                String iterationPicture = mApartment.getUrlPicture();
                if (iterationPicture.equals(Apartment.EMPTY_CASE)){
                    iterationPicture = itemList.get(i).getInformation() + PICTURE_SEP_TI_URL + fileName;
                } else {
                    iterationPicture += ENTITY_SEPARATOR + itemList.get(i).getInformation() + PICTURE_SEP_TI_URL + fileName;
                }
                mApartment.setUrlPicture(iterationPicture);
            }
        }
        BitmapStorage.deleteTempCameraCapture(context);
        Log.i("TAG", "resume : " + mApartment.getUrlPicture());
    }

    public Apartment getApartment(){
        return mApartment;
    }

}
