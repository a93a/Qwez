package com.example.qwez.router;

import android.content.Context;
import android.content.Intent;

import com.example.qwez.ui.settings.SettingsActivity;

/**
 * Opens SettingsActivity.class
 */
public class SettingsRouter {

    /**
     * Open SettingsActivity.class
     * @param context of current Activity
     * @param clearStack clear Activity stack. true clears stack
     */
    public void open(Context context, boolean clearStack){
        Intent intent = new Intent(context, SettingsActivity.class);
        if (clearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
    }

}
