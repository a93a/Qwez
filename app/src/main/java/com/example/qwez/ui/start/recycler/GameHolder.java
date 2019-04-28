package com.example.qwez.ui.start.recycler;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.qwez.R;
import com.example.qwez.base.BaseViewHolder;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.GameEvent;
import com.example.qwez.repository.local.Game;
import com.example.qwez.util.Category;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class GameHolder extends BaseViewHolder<Game> implements View.OnLongClickListener{

    @BindView(R.id.tcq)
    TextView cat;
    @BindView(R.id.tdq)
    TextView diff;
    @BindView(R.id.question_icon)
    ImageView icon;

    public GameHolder(@LayoutRes int layoutRes, ViewGroup parent) {
        super(layoutRes,parent);
    }

    @Override
    public void bind(@Nullable Game data) {
        if(data != null){
            this.data = data;
            cat.setText(data.getCategory());
            diff.setText(data.getDifficulty());
            Glide
                    .with(icon.getContext())
                    .asBitmap()
                    .load(
                            Uri.parse(
                                    String.format(
                                            "file:///android_asset/categories/%s.png",
                                            Category.getMap().get(data.getCategory()))))
                    .into(icon);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

}
