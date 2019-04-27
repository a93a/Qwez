package com.example.qwez.base;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.qwez.bus.RxBus;

import org.jetbrains.annotations.NotNull;

public class BaseFragment extends Fragment {

    //Keep a reference to Context.
    //Setting Context from onAttach makes sure that context not
    //running a risk of being null
    protected Context context;

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
