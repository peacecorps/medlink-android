package gov.peacecorps.medlinkandroid.helpers;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

public class UiUtils {
    public static void showAlertDialog(Context context, Integer titleResId, Integer positiveTextResId, Integer negativeTextResId, MaterialDialog.ButtonCallback callback) {
        if (positiveTextResId == null && negativeTextResId == null) {
            throw new IllegalArgumentException("at least one of positive and negative text is required");
        }

        if (negativeTextResId == null) {
            showAlertDialogWithPositiveText(context, titleResId, positiveTextResId, callback);
        } else if (positiveTextResId == null) {
            showAlertDialogWithNegativeText(context, titleResId, negativeTextResId, callback);
        } else {
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

    private static void showAlertDialogWithPositiveText(Context context, Integer titleResId, Integer positiveTextResId, MaterialDialog.ButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .cancelable(false)
                .title(titleResId)
                .positiveText(positiveTextResId)
                .callback(callback)
                .build()
                .show();
    }

    private static void showAlertDialogWithNegativeText(Context context, Integer titleResId, Integer negativeTextResId, MaterialDialog.ButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .cancelable(false)
                .title(titleResId)
                .negativeText(negativeTextResId)
                .callback(callback)
                .build()
                .show();
    }
}
