package gov.peacecorps.medlinkandroid.helpers;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final String DESERIALIZE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DISPLAY_DATE_FORMAT = "dd MMM yyyy, hh:mm:ss a";
    public static SimpleDateFormat DISPLAY_SIMPLE_DATE_FORMAT = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault());
    public static final String EXTRA_REQUEST_LIST_ITEM = "EXTRA_REQUEST_LIST_ITEM";
}
