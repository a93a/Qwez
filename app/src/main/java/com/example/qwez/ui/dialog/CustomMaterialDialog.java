package com.example.qwez.ui.dialog;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;

/**
 * Wrapper class for https://github.com/afollestad/material-dialogs
 *
 * Builds MaterialDialogs
 */
public final class CustomMaterialDialog {

    private static final int COLOR_RED = -500015;
    private static final String LABEL_CANCEL = "cancel";
    private static final int COLOR_GREEN = -500060;
    private static final String LABEL_OK = "ok";


    private CustomMaterialDialog(){
        //private constructor to avoid instantiation
    }

    /**
     * Get a loading dialog
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @return MaterialDialog.Builder of loading dialog
     */
    public static MaterialDialog.Builder loading(String title, Context context){
        return addNegBtn(context, LABEL_CANCEL)
                .title(title)
                .progress(true, 10)
                .canceledOnTouchOutside(false);
    }

    /**
     * Get an error dialog
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @param errorMessage message to display
     * @return MaterialDialog.Builder of error dialog
     */
    public static MaterialDialog.Builder error(String title, Context context, String errorMessage){
        return addNegBtn(context, LABEL_OK)
                .canceledOnTouchOutside(true)
                .content(errorMessage)
                .title(title)
                .onNegative((dialog, which) -> dialog.dismiss());
    }

    /**
     * Get an ok dialog
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @return MaterialDialog.Builder of ok dialog
     */
    public static MaterialDialog.Builder okDialog(String title, Context context){
        return addPosBtn(context, LABEL_OK)
                .title(title);
    }

    /**
     * Get a "Are You Sure" dialog
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @return MaterialDialog.Builder of areyousure dialog
     */
    public static MaterialDialog.Builder areYouSure(String title, Context context){
        return addNegPosBtn(context, LABEL_CANCEL, LABEL_OK)
                .title(title);
    }

    /**
     * Get a custom dialog dialog with a positive button and a negative button
     * @param title dialog title
     * @param context of Activity/Fragment/Application
     * @param layout Custom layout to display in dialog
     * @return MaterialDialog.Builder of custom dialog
     */
    public static MaterialDialog.Builder customDialog(String title, Context context, LinearLayout layout){
        return addNegPosBtn(context, LABEL_CANCEL, LABEL_OK)
                .title(title)
                .customView(layout, true);
    }

    /**
     * private Class method to set positive text and color
     */
    private static MaterialDialog.Builder addPosBtn(Context context, String buttonLabel){
        return new MaterialDialog.Builder(context)
                .positiveText(buttonLabel)
                .positiveColor(ContextCompat.getColor(context, R.color.colorProgressGreenDark));
    }

    /**
     * private Class method that returns new MaterialDialog.Builder and sets negative text and color
     */
    private static MaterialDialog.Builder addNegBtn(Context context, String buttonLabel){
        return new MaterialDialog.Builder(context)
                .negativeText(buttonLabel)
                .negativeColor(ContextCompat.getColor(context, R.color.colorProgressGreenDark));
    }

    /**
     * private Class method that returns new MaterialDialog.Builder and sets both positive and negative text and color
     */
    private static MaterialDialog.Builder addNegPosBtn(Context context, String negBtnLabel, String posBtnLabel){
        return new MaterialDialog.Builder(context)
                .negativeText(negBtnLabel)
                .negativeColor(ContextCompat.getColor(context, R.color.colorProgressRed))
                .positiveText(posBtnLabel)
                .positiveColor(ContextCompat.getColor(context, R.color.colorProgressGreenDark));
    }

    /**
     * Get a dialog with a custom adapter
     * @param title to set
     * @param context of Activity/Fragment/Application
     * @param adapter listadapter
     * @param manager layoutmanager
     * @return MaterialDialog.Builder of custom adapter dialog
     */
    public static MaterialDialog.Builder addDiff(String title, Context context, RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager){
        return addNegPosBtn(context, LABEL_CANCEL, LABEL_OK)
                .adapter(adapter,manager);
    }


    /**
     * Empty dialog with title, positive button and negative button
     * @param title to set
     * @param context of Activity/Fragment/Application
     * @return MaterialDialog.Builder of empty dialog
     */
    public static MaterialDialog.Builder emptyDialog(String title, Context context){
        return addNegPosBtn(context, LABEL_CANCEL, LABEL_OK)
                .title(title);
    }

    public static MaterialDialog.Builder animatedLogin(Context context){
        return null;
    }




}
