package gov.peacecorps.medlinkandroid.rest;

import java.net.UnknownHostException;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import retrofit.Callback;
import timber.log.Timber;

public abstract class GlobalRestCallback<T> implements Callback<T> {

    private BaseActivity baseActivity;
    private NetworkFailureCallback networkFailureCallback;

    public GlobalRestCallback(BaseActivity baseActivity, NetworkFailureCallback networkFailureCallback) {
        this(baseActivity);
        this.networkFailureCallback = networkFailureCallback;
    }

    public GlobalRestCallback(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public void onFailure(Throwable t) {
        baseActivity.dismissProgressDialog();
        Timber.e(t, "network error");
        if (networkFailureCallback != null) {
            networkFailureCallback.onNetworkFailure();
        }

        if (t instanceof NoNetworkException || t instanceof UnknownHostException) {
            baseActivity.showInfoDialog(R.string.no_network_available);
        } else {
            baseActivity.showInfoDialog(R.string.we_are_having_technical_issues);
        }
    }

    public static abstract class NetworkFailureCallback {
        public void onNetworkFailure() {
        }
    }
}
