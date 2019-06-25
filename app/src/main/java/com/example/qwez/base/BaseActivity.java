package com.example.qwez.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.bus.RxBus;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public abstract class BaseActivity extends AppCompatActivity {

    //keep private instance for dismissing etc
    private MaterialDialog dialog;

    /**
     * Return the @LayoutRes id of the View to be used with the Activity extending this Class.
     */
    protected abstract @LayoutRes int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        if(getLayout() != 0){
            setContentView(getLayout());
            ButterKnife.bind(this);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //show fullscreen on each Activity
        if (hasFocus) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Unregister this activity from RxBus to stop receiving events
        RxBus.unregister(this);

        //dismiss current dialog to avoid memory leaks
        dismissDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //case home button clicked
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * Show toolbar in layout
     */
    protected void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());
        }
        enableDisplayHomeAsUp();
    }

    /**
     * Set actionbar title
     * @param title to be displayed
     */
    protected void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    /**
     * Set actionbar subtitle
     * @param subtitle to be displayed
     */
    protected void setSubtitle(@NonNull String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }

    /**
     * Enable back button in actionbar
     */
    protected void enableDisplayHomeAsUp() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Disable back button in actionbar
     */
    protected void dissableDisplayHomeAsUp() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     * Hide toolbar in layout
     */
    protected void hideToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * Show toolbar in layout
     */
    protected void showToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    /**
     * Displays a MaterialDialog dialog (See https://github.com/afollestad/material-dialogs)
     * @param builder MaterialDialog builder to build and show
     */
    protected void showCustomDialog(@NonNull MaterialDialog.Builder builder){
        dismissDialog();
        dialog = builder.show();
    }

    /**
     * Dismisses the current MaterialDialog dialog showing
     */
    private void dismissDialog(){
        if(dialog != null && !dialog.isCancelled()){
            dialog.dismiss();
        }
    }

    /**
     * Shows the keyboard
     */
    protected void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(getCurrentFocus() , InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Hides the keyboard
     */
    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Replaces the current fragment in fragment container {@code id}. Also takes in
     * parameter to decide whether to add fragment to stack over the old.
     * @param fragment to be replaced with
     * @param id fragment container
     * @param addToStack flag determines if fragment added to fragment stack or not. True will add to stack.
     */
    protected void replaceFragment(@NonNull Fragment fragment, @IdRes int id, boolean addToStack){
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
