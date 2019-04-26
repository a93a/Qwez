package com.example.qwez.ui.start.recycler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qwez.R;
import com.example.qwez.base.BaseViewHolder;
import com.example.qwez.repository.local.Game;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionHolder extends BaseViewHolder<Game> implements View.OnClickListener, View.OnLongClickListener{

    @BindView(R.id.tcq)
    TextView cat;
    @BindView(R.id.tdq)
    TextView diff;

    public QuestionHolder(@LayoutRes int layoutRes, ViewGroup parent) {
        super(layoutRes,parent);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(@Nullable Game data) {
        if(data != null){
            cat.setText(data.getCategory());
            diff.setText(data.getDifficulty());
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

}
