package gov.peacecorps.medlinkandroid.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManager {
    private final ConnectivityManager cm;

    public NetworkManager(Context context) {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isNetworkConnected(){
        final android.net.NetworkInfo mobile = cm.getActiveNetworkInfo();
        return mobile != null && isEitherWifiOrMobileData(mobile);
    }

    private boolean isEitherWifiOrMobileData(NetworkInfo mobile) {
        return mobile.getType() == ConnectivityManager.TYPE_MOBILE || mobile.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
