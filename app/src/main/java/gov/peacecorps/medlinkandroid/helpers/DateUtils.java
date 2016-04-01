package gov.peacecorps.medlinkandroid.helpers;

import android.content.Context;

import java.util.Date;

public class DateUtils {

    public static String getDisplayStringFromDate(Date date, Context context){
        return Constants.DISPLAY_SIMPLE_DATE_FORMAT.format(date);
    }
}
