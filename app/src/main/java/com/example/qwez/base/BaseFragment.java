package com.example.qwez.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qwez.R;
import com.example.qwez.bus.RxBus;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    //Keep a reference to Context.
    //Setting Context from onAttach makes sure that context not
    //running a risk of being null
    protected Context context;

    protected abstract int getLayout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(getLayout() ,container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);

        //setting context here to avoid null pointers
        this.context = context;
    }

    @Override
    public void onStop() {
        super.onStop();

        //Stop this fragment from receiving events
        RxBus.unregister(this);
    }
}
