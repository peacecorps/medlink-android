package gov.peacecorps.medlinkandroid.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static SimpleDateFormat DISPLAY_SIMPLE_DATE_FORMAT = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT, Locale.getDefault());

    public static String getDisplayStringFromDate(Date date){
        return DISPLAY_SIMPLE_DATE_FORMAT.format(date);
    }
}
