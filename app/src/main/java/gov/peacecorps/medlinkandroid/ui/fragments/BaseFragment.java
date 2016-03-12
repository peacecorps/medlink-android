package gov.peacecorps.medlinkandroid.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import gov.peacecorps.medlinkandroid.ui.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.application.App;
import gov.peacecorps.medlinkandroid.application.AppComponent;
import gov.peacecorps.medlinkandroid.ui.activities.BaseView;
import timber.log.Timber;

public abstract class BaseFragment extends Fragment implements BaseView {
    protected BaseActivity baseActivity;

    protected abstract void setupFragmentComponent(AppComponent appComponent);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            baseActivity = (BaseActivity) context;
        } catch (ClassCastException e) {
            Timber.e("Class should extend %s", BaseActivity.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bootstrapDI();
    }

    private void bootstrapDI() {
        AppComponent appComponent = ((App) getActivity().getApplication()).getAppComponent();
        setupFragmentComponent(appComponent);
    }

    @Override
    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

}
