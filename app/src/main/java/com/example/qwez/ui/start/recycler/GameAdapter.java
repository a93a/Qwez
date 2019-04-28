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

import timber.log.Timber;

public class GameAdapter extends RecyclerView.Adapter<GameHolder> {

    private final List<Game> games = new ArrayList<>();
    private final Context context;

    public GameAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameHolder(R.layout.layout_item_question, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        holder.bind(games.get(position));
        holder.getItemView().setOnClickListener(v -> {
            Timber.d("Click detect");
            Game game = holder.getData();
            Timber.d("Game is %s", game.getGameId());
            RxBus.publish(RxBus.GAME_TOUCHED, new GameEvent(game));
        });
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


