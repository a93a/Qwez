package com.example.qwez.ui.dialog;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.qwez.R;
import com.example.qwez.base.BaseViewHolder;

import butterknife.BindView;

public class DifficultyItem extends BaseViewHolder<String> {

    @BindView(R.id.diff_text) TextView textView;

    DifficultyItem(int layoutRes, ViewGroup parent) {
        super(layoutRes, parent);
    }

    @Override
    public void bind(@Nullable String data) {
        if(data != null){
            textView.setText(data);
            switch (data){
                case "Easy":
                    textView.setBackgroundColor(itemView.getResources().getColor(R.color.colorProgressGreenLight));
                    break;
                case "Medium":
                    textView.setBackgroundColor(itemView.getResources().getColor(R.color.colorProgressYellow));
                    break;
                case "Hard":
                    textView.setBackgroundColor(itemView.getResources().getColor(R.color.colorProgressRed));
                    break;
                default:
                    break;
            }
        }
    }

}
