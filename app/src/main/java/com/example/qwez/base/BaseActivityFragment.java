package com.example.qwez.base;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

public class BaseActivityFragment extends BaseActivity {

    protected void replaceFragment(Fragment fragment, @IdRes int id, boolean addToStack){
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
