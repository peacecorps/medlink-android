package gov.peacecorps.medlinkandroid.rest;

import android.util.Log;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import retrofit.Callback;

public abstract class GlobalRestCallback<T> implements Callback<T> {

    private static final String TAG = GlobalRestCallback.class.getSimpleName();
    private BaseActivity baseActivity;

    public GlobalRestCallback(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public void onFailure(Throwable t) {
        baseActivity.dismissProgressDialog();
        Log.e(TAG, t.getMessage());

        if(t instanceof NoNetworkException){
            baseActivity.showInfoDialog(R.string.no_network_available);
        } else {
            baseActivity.showInfoDialog(R.string.we_are_having_technical_issues);
        }
    }
}
