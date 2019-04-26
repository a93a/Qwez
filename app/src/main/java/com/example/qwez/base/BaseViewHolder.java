package com.example.qwez.base;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qwez.R;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {


    public BaseViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false));
    }

    public abstract void bind(@Nullable T data);

}
