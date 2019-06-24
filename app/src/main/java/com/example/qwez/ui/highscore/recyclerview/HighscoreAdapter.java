package com.example.qwez.ui.highscore.recyclerview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.qwez.R;
import com.example.qwez.base.BaseAdapter;
import com.example.qwez.entity.Highscore;

public class HighscoreAdapter extends BaseAdapter<Highscore,HighscoreHolder> {

    public HighscoreAdapter() {
    }

    @NonNull
    @Override
    public HighscoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HighscoreHolder(R.layout.item_highscore, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull HighscoreHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



}
