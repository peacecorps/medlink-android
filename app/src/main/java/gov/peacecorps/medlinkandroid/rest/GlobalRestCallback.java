package gov.peacecorps.medlinkandroid.rest;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import retrofit.Callback;
import timber.log.Timber;

public abstract class GlobalRestCallback<T> implements Callback<T> {

    private BaseActivity baseActivity;

    public GlobalRestCallback(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public void onFailure(Throwable t) {
        baseActivity.dismissProgressDialog();
        Timber.e(t.getMessage());

        if(t instanceof NoNetworkException){
            baseActivity.showInfoDialog(R.string.no_network_available);
        } else {
            baseActivity.showInfoDialog(R.string.we_are_having_technical_issues);
        }
    }
}
