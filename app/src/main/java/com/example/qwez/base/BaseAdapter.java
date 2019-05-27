package com.example.qwez.base;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final List<T> datalist = new ArrayList<>();

    public void setData(List<T> newDataList){
        datalist.clear();
        datalist.addAll(newDataList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}
