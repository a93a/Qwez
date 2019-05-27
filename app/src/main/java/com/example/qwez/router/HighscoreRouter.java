package com.example.qwez.router;

import android.content.Context;
import android.content.Intent;

import com.example.qwez.ui.highscore.HighscoreActivity;

public class HighscoreRouter {

    public void open(Context context, boolean clearStack){
        Intent intent = new Intent(context, HighscoreActivity.class);
        if (clearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
    }

}
