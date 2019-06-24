package com.example.qwez.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final List<T> dataList = new ArrayList<>();

    public void setData(@NonNull List<T> newDataList){
        dataList.clear();
        dataList.addAll(newDataList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
