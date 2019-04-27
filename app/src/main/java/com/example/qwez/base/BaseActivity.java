package com.example.qwez.base;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.bus.RxBus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    //keep private instance for dismissing etc
    private MaterialDialog dialog;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //force fullscreen on each Activity
        if (hasFocus) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
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
    protected void setSubtitle(String subtitle) {
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
    protected void showCustomDialog(MaterialDialog.Builder builder){
        dismissDialog();
        dialog = builder.show();
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
     * Dismisses the current MaterialDialog dialog showing
     */
    private void dismissDialog(){
        if(dialog != null && !dialog.isCancelled()){
            dialog.dismiss();
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
}
