package com.openclassrooms.realestatemanager.apartmentfilters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.LineSearch;

import java.util.List;

public class SearchFilterAdapter extends RecyclerView.Adapter<SearchFilterViewHolder> {

    private List<LineSearch> mLineSearchList;

    public SearchFilterAdapter(List<LineSearch> lineSearchList) {
        mLineSearchList = lineSearchList;
    }

    @NonNull
    @Override
    public SearchFilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_search_recyclerview_item, viewGroup, false);
        return new SearchFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFilterViewHolder searchFilterViewHolder, int i) {
        searchFilterViewHolder.setIsRecyclable(false);
        searchFilterViewHolder.updateWithSearchFilterInformation(mLineSearchList.get(i));
    }

    @Override
    public int getItemCount() {
        return this.mLineSearchList.size();
    }

    public List<LineSearch> getLineSearchList(){
        return mLineSearchList;
    }

    public void setSearchFilterList(List<LineSearch> lineSearchList){
        this.mLineSearchList = lineSearchList;
    }
}
