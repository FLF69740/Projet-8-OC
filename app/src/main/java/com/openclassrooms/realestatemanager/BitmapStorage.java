package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.apartmentmodifier.TransformerApartmentItems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class BitmapStorage {

    public static final String CAMERA_CAPTURE = "TEMP_CAMERA_CAPTURE_";

    // save Image into internal memory
    public static void saveImageInternalStorage(Context context, String imageName, Uri uri){
        Bitmap bitmap = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // load Image from internal memory
    public static Bitmap loadImage(Context context, String imageName){
        Bitmap bitmap = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = context.openFileInput(imageName);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // check if image exist into internal memory
    public static boolean isFileExist(Context context, String imageName){
        File file = context.getFileStreamPath(imageName);
        return file.exists();
    }

    // show if bitmpap exist and his path into the logcat
    public static void showImageInformations(Context context, String imageName){
        File file = context.getFileStreamPath(imageName);
        Log.i("TAG", "Exist : "+ String.valueOf(BitmapStorage.isFileExist(context, imageName)) + " - chemin : " + file.getAbsolutePath());
    }

    // delete a bitmap
    public static void deleteImage(Context context, String imageName){
        if (BitmapStorage.isFileExist(context, imageName)){
            File file = context.getFileStreamPath(imageName);
            file.delete();
            Log.i("TAG", "bitmap deleted");
        }else {
            Log.i("TAG", "bitmap didn't exist");
        }
    }

    //Get an Uri with a Bitmap object
    public static Uri getImageUri(Context context, Bitmap bitmap){
        String imageName = BitmapStorage.findCameraCaptureName(context);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BitmapStorage.showImageInformations(context, imageName);

        return Uri.fromFile(new File(context.getFilesDir() + "/" + imageName));
    }

    // define a free name for a new camera capture file
    private static String findCameraCaptureName(Context context){
        int number = 0;
        String result = CAMERA_CAPTURE + number;

        while (BitmapStorage.isFileExist(context, result)){
            number++;
            result = CAMERA_CAPTURE + number;
        }

        return result;
    }

    //delete TEMP_CAMERA_CAPTURE
    public static void deleteTempCameraCapture(Context context){
        int number = 0;
        String name = CAMERA_CAPTURE + number;

        while (BitmapStorage.isFileExist(context, name)){
            File file = context.getFileStreamPath(name);
            file.delete();
            Log.i("TAG", name +" deleted");
            number++;
            name = CAMERA_CAPTURE + number;
        }
    }

    //first photo name return
    public static String getFirstPhotoName(Apartment apartment){
        String result = apartment.getUrlPicture();
        if (result.contains(TransformerApartmentItems.ENTITY_SEPARATOR)) {
            String[] list = result.split(TransformerApartmentItems.ENTITY_SEPARATOR);
            result = list[0];
        } else if (result.equals(Apartment.EMPTY_CASE)){
            result = Apartment.EMPTY_CASE + TransformerApartmentItems.PICTURE_SEP_TI_URL + Apartment.EMPTY_CASE;
        }
        String[] divider = result.split(TransformerApartmentItems.PICTURE_SEP_TI_URL);
        return divider[1];
    }

    //get number of photo for a collection
    public static int getPhotoNumber(Apartment apartment){
        String result = apartment.getUrlPicture();
        int number = 1;
        if (result.contains(TransformerApartmentItems.ENTITY_SEPARATOR)) {
            String[] list = result.split(TransformerApartmentItems.ENTITY_SEPARATOR);
            number += list.length -1;
        }
        return number;
    }
}
