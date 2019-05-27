package com.example.qwez.ui.start.recycler;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.qwez.R;
import com.example.qwez.base.BaseAdapter;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.GameEvent;
import com.example.qwez.repository.local.Game;

public class GameAdapter extends BaseAdapter<Game,GameHolder> {

    private final Context context;
    private boolean isClickable;

    public GameAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameHolder(R.layout.item_question, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        holder.bind(datalist.get(position));
        holder.getItemView().setOnClickListener(v -> {
            if(isClickable){
                Game game = holder.getData();
                RxBus.publish(RxBus.GAME_TOUCHED, new GameEvent(game));
            }
        });
    }

    void deleteItem(int position) {
        RxBus.publish(RxBus.DELETE_GAME, new GameEvent(datalist.get(position)));
        notifyDataSetChanged(); //restore view if not/after deleted
    }

    public Context getContext(){
        return context;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }
}


