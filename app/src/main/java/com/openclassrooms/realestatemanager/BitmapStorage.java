package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class BitmapStorage {

    public static void saveImage(Context context, String imageName, Uri uri){
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

}
