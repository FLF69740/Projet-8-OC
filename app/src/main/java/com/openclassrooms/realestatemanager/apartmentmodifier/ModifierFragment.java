package com.openclassrooms.realestatemanager.apartmentmodifier;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.appartmentlist.RecyclerViewClickSupport;
import com.openclassrooms.realestatemanager.models.Apartment;
import com.openclassrooms.realestatemanager.models.Item;
import com.openclassrooms.realestatemanager.models.TransformerApartmentItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifierFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private static final String BUNDLE_KEY_APARTMENT = "BUNDLE_KEY_APARTMENT";

    private View mView;
    private ItemsAdapter mAdapter;
    private Apartment mApartment;

    @BindView(R.id.recycler_view_modifier)RecyclerView mRecyclerView;
    @BindView(R.id.radioGroup)RadioGroup mRadioGroupButton;

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

        return mView;
    }

    /**
     *  RECYCLERVIEW
     */

    private void configureRecyclerView(){
        TransformerApartmentItems transformerApartmentItems = new TransformerApartmentItems(mApartment, mView.getContext());
        this.mAdapter = new ItemsAdapter(transformerApartmentItems.getListItems());
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.fragment_modifier_recyclerciew_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Item item = mAdapter.getItem(position);
                    loadModifierBarManager(item, position);
                });
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

    private void loadModifierBarManager(Item item, int position){

    }

}
