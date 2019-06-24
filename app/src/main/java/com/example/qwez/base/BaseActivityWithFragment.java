package com.example.qwez.base;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class BaseActivityWithFragment extends BaseActivity {

    /**
     * Replaces the current fragment in fragment container {@code id}. Also takes in
     * parameter to decide whether to add fragment to stack over the old.
     * @param fragment to be replaced with
     * @param id fragment container
     * @param addToStack flag determines if fragment added to fragment stack or not. True will add to stack.
     */
    protected void replaceFragment(@NonNull Fragment fragment,@IdRes int id, boolean addToStack){
        if(addToStack){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, fragment)
                    .addToBackStack(null)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, fragment)
                    .commit();
        }
    }

}
