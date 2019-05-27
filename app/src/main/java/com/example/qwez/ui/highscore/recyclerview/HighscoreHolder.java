package com.example.qwez.ui.highscore.recyclerview;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.example.qwez.R;
import com.example.qwez.base.BaseViewHolder;
import com.example.qwez.entity.Highscore;

import butterknife.BindView;

public class HighscoreHolder extends BaseViewHolder<Highscore> {

    @BindView(R.id.highscore_nick)
    TextView nick;
    @BindView(R.id.highscore_score)
    TextView score;

    /**
     * Create a BaseViewHolder with {@code layoutRes} layout
     *
     * @param layoutRes
     * @param parent
     */
    HighscoreHolder(@LayoutRes int layoutRes, ViewGroup parent) {
        super(layoutRes, parent);
    }

    @Override
    public void bind(@Nullable Highscore data) {
        if(data != null){
            this.data = data;
            nick.setText(data.getNick());
            score.setText(String.format("%s points", data.getScore()));
        }
    }

}
