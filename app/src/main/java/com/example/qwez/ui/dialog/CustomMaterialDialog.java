package com.example.qwez.ui.dialog;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;

import java.util.Observable;

/**
 * Wrapper class for https://github.com/afollestad/material-dialogs
 *
 * Builds MaterialDialogs
 */
public class CustomMaterialDialog {

    private static final int COLOR_RED = -500015;
    private static final String LABEL_CANCEL = "cancel";
    private static final int COLOR_GREEN = -500060;
    private static final String LABEL_OK = "ok";

    /**
     * Get a loading dialog
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @return MaterialDialog.Builder of loading dialog
     */
    public static MaterialDialog.Builder loading(String title, Context context){
        return new MaterialDialog.Builder(context)
                .title(title)
                .progress(true, 10)
                .canceledOnTouchOutside(false)
                .negativeText(LABEL_CANCEL)
                .negativeColor(COLOR_RED);
    }

    /**
     * Get an error dialog
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @param errorMessage message to display
     * @return MaterialDialog.Builder of error dialog
     */
    public static MaterialDialog.Builder error(String title, Context context, String errorMessage){
        return new MaterialDialog.Builder(context)
                .canceledOnTouchOutside(true)
                .content(errorMessage)
                .title(title)
                .positiveText(LABEL_OK)
                .negativeColor(COLOR_GREEN)
                .onNegative((dialog, which) -> dialog.dismiss());
    }

    /**
     * Get an ok dialog
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @return MaterialDialog.Builder of ok dialog
     */
    public static MaterialDialog.Builder okDialog(String title, Context context){
        return new MaterialDialog.Builder(context)
                .title(title)
                .positiveText(LABEL_OK);
    }

    /**
     * Get a "Are You Sure" dialog
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @return MaterialDialog.Builder of areyousure dialog
     */
    public static MaterialDialog.Builder areYouSure(String title, Context context){
        return new MaterialDialog.Builder(context)
                .title(title)
                .positiveText(LABEL_OK)
                .negativeText(LABEL_CANCEL);
    }

    /**
     * Get a "Add Question" dialog
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @param layout Custom layout to display in dialog
     * @return MaterialDialog.Builder of addquestion dialog
     */
    public static MaterialDialog.Builder addQuestion(String title, Context context, LinearLayout layout){
        return new MaterialDialog.Builder(context)
                .title(title)
                .customView(layout, true)
                .negativeText(LABEL_CANCEL)
                .positiveText(LABEL_OK);
    }

}
