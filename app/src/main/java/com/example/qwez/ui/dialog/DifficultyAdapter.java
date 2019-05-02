package com.example.qwez.ui.dialog;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qwez.R;
import com.example.qwez.util.Difficulty;

import java.util.List;

public class DifficultyAdapter extends RecyclerView.Adapter<DifficultyItem> {

    private final List<String> diffList = Difficulty.getList();

    @NonNull
    @Override
    public DifficultyItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DifficultyItem(R.layout.item_difficulty,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DifficultyItem holder, int position) {
        holder.bind(diffList.get(position));
    }

    @Override
    public int getItemCount() {
        return diffList.size();
    }
}
