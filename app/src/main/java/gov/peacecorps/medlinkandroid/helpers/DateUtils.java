package gov.peacecorps.medlinkandroid.helpers;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gov.peacecorps.medlinkandroid.R;

public class DateUtils {
    private static SimpleDateFormat DISPLAY_SIMPLE_DATE_FORMAT = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT, Locale.getDefault());

    public static String getDisplayStringFromDate(Date date, Context context){
        if(date == null){
            return context.getString(R.string.not_submitted);
        }

        return DISPLAY_SIMPLE_DATE_FORMAT.format(date);
    }
}
