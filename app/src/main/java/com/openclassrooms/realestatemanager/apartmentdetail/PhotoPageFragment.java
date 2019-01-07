package com.openclassrooms.realestatemanager.apartmentdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.BitmapStorage;
import com.openclassrooms.realestatemanager.R;
import static com.openclassrooms.realestatemanager.models.TransformerApartmentItems.ENTITY_SEPARATOR;
import static com.openclassrooms.realestatemanager.models.TransformerApartmentItems.PICTURE_SEP_TI_URL;

public class PhotoPageFragment extends Fragment {

    private static final String KEY_POSITION = "KEY_POSITION";
    private static final String KEY_PHOTO_LIST  = "KEY_PHOTO_LIST";

    public PhotoPageFragment() {}

    public static PhotoPageFragment newInstance(String stringList, int position){
        PhotoPageFragment photoPageFragment = new PhotoPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PHOTO_LIST, stringList);
        bundle.putInt(KEY_POSITION, position);
        photoPageFragment.setArguments(bundle);

        return photoPageFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_page, container, false);

        ImageView imageView = view.findViewById(R.id.photo_viewpager);
        TextView textView = view.findViewById(R.id.title_photo_viewpager);

        assert getArguments() != null;
        int position = getArguments().getInt(KEY_POSITION, 0);
        String oneStringList = getArguments().getString(KEY_PHOTO_LIST, null);

        String[] parts = oneStringList.split(ENTITY_SEPARATOR);
        String[] subParts = parts[position].split(PICTURE_SEP_TI_URL);

        textView.setText(subParts[0]);
        if (BitmapStorage.isFileExist(getContext(), subParts[1])) {
            imageView.setImageBitmap(BitmapStorage.loadImage(getContext(), subParts[1]));
        }else {
            imageView.setImageResource(R.drawable.image_realestate);
        }

        return view;
    }

}
