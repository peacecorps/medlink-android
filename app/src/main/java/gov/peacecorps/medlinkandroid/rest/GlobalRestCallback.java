package gov.peacecorps.medlinkandroid.rest;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import retrofit.Callback;

public abstract class GlobalRestCallback<T> implements Callback<T> {

    private BaseActivity baseActivity;

    public GlobalRestCallback(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public void onFailure(Throwable t) {
        baseActivity.dismissProgressDialog();
        if(t instanceof NoNetworkException){
            baseActivity.showMaterialDialog(R.string.no_network_available);
        } else {
            baseActivity.showMaterialDialog(R.string.we_are_having_technical_issues);
        }
    }
}
