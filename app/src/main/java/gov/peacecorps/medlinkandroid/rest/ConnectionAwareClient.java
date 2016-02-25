package gov.peacecorps.medlinkandroid.rest;

import android.content.Context;
import android.net.ConnectivityManager;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public class ConnectionAwareClient extends OkHttpClient {

    private OkHttpClient client;
    private Context context;

    public ConnectionAwareClient(Context context, OkHttpClient client) {
        this.client = client;
        this.context = context;
    }

    @Override
    public Call newCall(Request request) {
        if(isConnectionPresent()){
            return client.newCall(request);
        } else {
            throw new NoNetworkException();
        }
    }

    private boolean isConnectionPresent() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
