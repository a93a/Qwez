package com.example.qwez.ui.start.recycler;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qwez.R;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.GameEvent;
import com.example.qwez.repository.local.Game;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {

    private final List<Game> games = new ArrayList<>();
    private final Context context;

    public QuestionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionHolder questionHolder = new QuestionHolder(R.layout.layout_item_question, parent);
        return questionHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        holder.bind(games.get(position));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void setData(List<Game> newGames){
        games.clear();
        games.addAll(newGames);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        RxBus.publish(RxBus.DELETE_GAME, new GameEvent(games.get(position)));
        notifyDataSetChanged(); //restore view if not/after deleted
    }

    public Context getContext(){
        return context;
    }

}


