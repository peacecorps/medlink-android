package gov.peacecorps.medlinkandroid.rest;

import android.content.Context;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import gov.peacecorps.medlinkandroid.helpers.NetworkManager;

public class ConnectionAwareClient extends OkHttpClient {

    private OkHttpClient client;
    private Context context;

    public ConnectionAwareClient(Context context, OkHttpClient client) {
        this.client = client;
        this.context = context;
    }

    @Override
    public Call newCall(Request request) {
        if (NetworkManager.isNetworkConnected(context)) {
            return client.newCall(request);
        } else {
            throw new NoNetworkException();
        }
    }
}
