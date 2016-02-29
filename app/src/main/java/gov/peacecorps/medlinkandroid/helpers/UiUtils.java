package gov.peacecorps.medlinkandroid.helpers;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

public class UiUtils {
    public static void showAlertDialog(Context context, int titleResId, int positiveTextResId, int negativeTextResId, MaterialDialog.ButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .cancelable(false)
                .title(titleResId)
                .positiveText(positiveTextResId)
                .negativeText(negativeTextResId)
                .callback(callback)
                .build()
                .show();
    }
}
