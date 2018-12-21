package com.openclassrooms.realestatemanager.apartmentmodifier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Item;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

    private List<Item> mItemList;

    public ItemsAdapter(List<Item> itemList){
        this.mItemList = itemList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_modifier_recyclerciew_item, viewGroup, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int i) {
        itemsViewHolder.setIsRecyclable(false);
        itemsViewHolder.updateWithItemInformations(mItemList.get(i));
    }

    @Override
    public int getItemCount() {
        return this.mItemList.size();
    }

    public List<Item> getItemList(){
        return mItemList;
    }

    public void setItemList(List<Item> itemList){
        this.mItemList = itemList;
    }
}
