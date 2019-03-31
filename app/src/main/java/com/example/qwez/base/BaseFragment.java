package com.example.qwez.base;

import android.content.Context;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    //Keep a reference to Context.
    //Setting Context from onAttach makes sure that context not
    //running a risk of being null
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

}