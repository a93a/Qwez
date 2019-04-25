package com.example.qwez.ui.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;

import java.util.Observable;

public class CustomMaterialDialog {

    private static final int COLOR_RED = -500015;
    private static final String LABEL_CANCEL = "cancel";
    private static final int COLOR_GREEN = -500060;
    private static final String LABEL_OK = "ok";

    public static MaterialDialog.Builder loading(String title, Context context){
        return new MaterialDialog.Builder(context)
                .title(title)
                .progress(true, 10)
                .canceledOnTouchOutside(false)
                .negativeText(LABEL_CANCEL)
                .negativeColor(COLOR_RED);
    }

    public static MaterialDialog.Builder error(String title, Context context, String errorMessage){
        return new MaterialDialog.Builder(context)
                .canceledOnTouchOutside(true)
                .content(errorMessage)
                .title(title)
                .positiveText(LABEL_OK)
                .negativeColor(COLOR_GREEN)
                .onNegative((dialog, which) -> dialog.dismiss());
    }

    public static MaterialDialog.Builder okDialog(String title, Context context){
        return new MaterialDialog.Builder(context)
                .title(title)
                .positiveText(LABEL_OK);
    }

    public static MaterialDialog.Builder sureLogOut(String title, Context context){
        return new MaterialDialog.Builder(context)
                .title(title)
                .positiveText(LABEL_OK)
                .negativeText(LABEL_CANCEL);
    }

}
