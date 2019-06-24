package com.example.qwez.router;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.qwez.ui.start.StartActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Opens StartActivity.class
 */
public class StartRouter {

    /**
     * Open StartActivity.class
     * @param context of current Activity
     * @param clearStack clear Activity stack. true clears stack
     */
    public void open(@NonNull Context context, boolean clearStack){
        Intent intent = new Intent(context, StartActivity.class);
        if (clearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
    }

}
